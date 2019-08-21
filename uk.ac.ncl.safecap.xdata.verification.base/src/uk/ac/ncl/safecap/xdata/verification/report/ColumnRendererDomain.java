package uk.ac.ncl.safecap.xdata.verification.report;

import java.util.List;

import uk.ac.ncl.safecap.common.report.SRBlock;
import uk.ac.ncl.safecap.common.report.SRPart;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.ColumnModel;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.base.ISDADataProvider;

public class ColumnRendererDomain extends STReportColumnContents {
	private final ISDADataProvider context;

	public ColumnRendererDomain(ISDADataProvider context) {
		this.context = context;
	}

	@Override
	public SRPart getColumnBody(ColumnModel column, Object domain) {
		final SRBlock block = new SRBlock();

		for (final String cfn : column.getConceptPaths()) {
			final IXFunction cf = CTReportUtils.getFunctionByPath(context, cfn);
			if (cf != null) {
				final List<Object> values = cf.get(domain);
				if (values != null && !values.isEmpty()) {
					block.add(CTReportUtils.formatHTMLCell(values));
				}
			}
		}

		return block;
	}

}