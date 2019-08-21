package uk.ac.ncl.safecap.xdata.verification.console;

import java.sql.Connection;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.safecap.common.MD5Checksum;
import uk.ac.ncl.safecap.db.common.DBUtils;
import uk.ac.ncl.safecap.rt.base.RTCondition;
import uk.ac.ncl.safecap.rt.base.RTResult;
import uk.ac.ncl.safecap.rt.base.RTViolation;
import uk.ac.ncl.safecap.rt.base.ViolationQuery;
import uk.ac.ncl.safecap.xdata.verification.ITag;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.InvariantInfo;
import uk.ac.ncl.safecap.xdata.verification.db.InvariantQuery;
import uk.ac.ncl.safecap.xdata.verification.report.SubInvariantReport;
import uk.ac.ncl.safecap.xdata.verification.report.SubInvariantReportIssue;
import uk.ac.ncl.safecap.xdata.verification.report.SubInvariantReportResult;

public class CatalogCommitResults extends CatalogBaseCommand {
	public static CatalogCommitResults INSTANCE = new CatalogCommitResults();

	private CatalogCommitResults() {
	}

	@Override
	public String getName() {
		return "rt:commit";
	}

	@Override
	public int getArguments() {
		return 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(ISafeCapConsole console, String[] arguments) {
		try {
			RootCatalog rc = console.getRootCatalog();
			String wrSchema = null; // weak reference to schema
			String wrData = null; // weak reference to data
			
			for(ITag tag: rc.getTags()) {
				if ("layout".equals(tag.getName().content()))
					wrSchema = tag.getValue().content();
				else if ("data".equals(tag.getName().content()))
					wrData = tag.getValue().content();
			}
			
			if (wrSchema == null) {
				console.err("layout name not defined");
				return;
			}

			if (wrSchema.length() < 3) {
				console.err("layout name too short");
				return;
			}
			
			if (wrData == null) {
				console.err("data set name not defined");
				return;
			}

			if (wrSchema.length() < 3) {
				console.err("layout name too short");
				return;
			}
			
			SubInvariantReportResult result = new SubInvariantReportResult(rc);
			if (result.getProvedInvariants().isEmpty()) {
				console.err("nothing to commit - no properties proved");
				return;
			}
			
			RTResult prj = new RTResult(wrSchema, wrData, rc.getId().content());
			
			for(InvariantInfo ii: result.getProvedInvariants()) {
				RTCondition c = new RTCondition(ii.key, MD5Checksum.getMD5TextChecksum(ii.source.getFormula().content()));
				console.out("Processing " + c.getPropertyKey() + "\n");
				InvariantQuery.save(ii.source);
				prj.addCondition(c);
				final String inv = ii.name;
				if (result.getSubreports().containsKey(inv)) {
					List<SubInvariantReport> ss = result.getSubreports().get(inv);
					int count = 0;
					for(SubInvariantReport sir: ss) {
						for(SubInvariantReportIssue issue: sir.issues) {
							for(String el: issue.elements) {
								RTViolation v = new RTViolation(CLUtils.unquote(el));
								if (!c.getViolations().contains(v)) {
									c.addViolation(v);
									count++;
								}
							}
						}
					}
					console.out(ii.key + ": " + count + " records\n");
				}
			}
						
			try {
				final Connection connection = DBUtils.getConnection();
				ViolationQuery vquery = new ViolationQuery(connection);
				vquery.addResult(prj);
				console.out("success\n");
			} catch (Throwable e) {
				e.printStackTrace();
				console.out("Database commit fail: " + e.getMessage());
			}
		} catch (final Exception e) {
			console.err(e.getMessage());
		}
		return;
	}

	
	
}
