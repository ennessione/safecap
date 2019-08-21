package uk.ac.ncl.safecap.xdata.verification.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.ncl.safecap.common.HashBag;
import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.prover.core.ProofGoal;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.ElementVisitor;
import uk.ac.ncl.safecap.xdata.verification.core.InvariantInfo;
import uk.ac.ncl.safecap.xdata.verification.core.VisitorUtils;
import uk.ac.ncl.safecap.xdata.verification.transitions.ITransitionPOs;
import uk.ac.ncl.safecap.xdata.verification.transitions.ITransitionPathSource;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionCluster;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionClusterPart;
import uk.ac.ncl.safecap.xdata.verification.transitions.TransitionContainer;

public class SubInvariantReportResult {
	private static final int OPTION_CONDENSE_REPORT = 2;
	private RootCatalog project;
	private final Collection<InvariantInfo> allInvariants;
	private final Collection<InvariantInfo> provedInvariants;

	private final Map<String, List<SubInvariantReport>> subreports;
	private final Map<InvariantInfo, List<PositiveCaseInfo>> positiveCaseInfo;

	public SubInvariantReportResult(RootCatalog project) {
		super();
		this.project = project;
		allInvariants = VisitorUtils.collectInvariants(project);
		provedInvariants = new ArrayList<>();
		subreports = new HashMap<>();
		positiveCaseInfo = new HashMap<>();
		process();
	}

	class PositiveCaseInfo {
		private ITransitionPathSource source;
		private ManagedProofObligation po;
		public PositiveCaseInfo(ITransitionPathSource source, ManagedProofObligation po) {
			super();
			this.source = source;
			this.po = po;
		}
		
		public ITransitionPathSource getSource() {
			return source;
		}
		
		public ManagedProofObligation getPo() {
			return po;
		}
		
		public boolean isContradiction() {
			return po.getResult().isProperty("contradiction");
		}
		
		public int getSteps() {
			return po.getStage();
		}
		
		public HashBag<String> getClosingTactics() {
			HashBag<String> tactics = new HashBag<String>();
			
			for(ProofGoal pg: po.getGoals()) 
				tactics.add(pg.getGoal().getTransform());
			
			return tactics;
		}
	}
	
	
	
	public Map<InvariantInfo, List<PositiveCaseInfo>> getContradictions() {
		return positiveCaseInfo;
	}

	public Collection<InvariantInfo> getAllInvariants() {
		return allInvariants;
	}

	public Collection<InvariantInfo> getProvedInvariants() {
		return provedInvariants;
	}

	public Map<String, List<SubInvariantReport>> getSubreports() {
		return subreports;
	}

	private void process() {

		VisitorUtils.visitAllConditionsInImportedTransitions(project, new ElementVisitor<ITransitionPOs>() {

			@Override
			public Object visit(ITransitionPOs element, Object userData) {
				if (element.getPos() != null) {
					for (final ManagedProofObligation tpo : element.getPos()) {
						populateSubReports(element, tpo, (TransitionContainer) userData);
					}
				}

				return null;
			}

		});

		// buildOverallPOStepsGoals();

		for (final String inv : subreports.keySet()) {
			compactSubReports(inv);
		}
	}

	private void compactSubReports(String inv) {
		final List<SubInvariantReport> toRemove = new ArrayList<>();
		final List<SubInvariantReport> list = subreports.get(inv);
		for (int i = 0; i < list.size(); i++) {
			final SubInvariantReport report0 = list.get(i);
			if (!toRemove.contains(report0)) {
				for (int j = i + 1; j < list.size(); j++) {
					final SubInvariantReport report1 = list.get(j);
					if (!toRemove.contains(report1) && !report0.equals(report1)) {
						if (matches(report0, report1)) {
							toRemove.add(report1);
							report0.addOtherSource(new SubSubInvariantReport(report1.element, report1.source, report1.tpo));
						}
					}
				}
			}
		}

		subreports.get(inv).removeAll(toRemove);

		// remove duplicates
		final Set<SubInvariantReport> set = new HashSet<>(subreports.get(inv));
		subreports.get(inv).clear();
		subreports.get(inv).addAll(set);
	}

	private boolean matches(SubInvariantReport a, SubInvariantReport b) {
		return a.tc == b.tc && issuesSame(a.issues, b.issues);
	}

	private boolean issuesSame(Collection<SubInvariantReportIssue> a, Collection<SubInvariantReportIssue> b) {
		// return false; // DEBUG

		if (OPTION_CONDENSE_REPORT >= 2 && a.size() == b.size()) {

			for (final SubInvariantReportIssue x : a) {
				boolean found = false;
				for (final SubInvariantReportIssue y : b) {
					if (x.equals(y)) {
						found = true;
						break;
					}
				}
				if (!found) {
					return false;
				}
			}
			return true;
		}
		return false;

	}

	private void addSubReport(String invariant, SubInvariantReport subreport) {
		List<SubInvariantReport> list = subreports.get(invariant);
		if (list == null) {
			list = new ArrayList<>();
			subreports.put(invariant, list);
		}

		list.add(subreport);
	}

	private void populateSubReports(ITransitionPOs element, ManagedProofObligation tpo, TransitionContainer tc) {
		final String[] parts = tpo.getName().split("/");
		if (parts.length == 3 && parts[0].equals("INV")) {
			final String invariant = parts[1];
			final InvariantInfo info = getInvariant(invariant);
			if (info != null && element instanceof TransitionCluster) {
				if (!provedInvariants.contains(info)) {
					provedInvariants.add(info);
				}
				final TransitionCluster cl = (TransitionCluster) element;
				final int lastSlash = tpo.getName().lastIndexOf('/');
				if (lastSlash > 0) {
					final String partName = tpo.getName().substring(lastSlash + 1);
					if (tpo.isValid()) {
						addPositiveCase(info, tpo, cl);
						return;
					}
					for (final TransitionClusterPart p : cl.getParts()) {
						if (p.getName().equals(partName)) {
							addSubReport(invariant, new SubInvariantReport(tc, p, cl, tpo));
							break;
						}
					}
				}
			}
		}
	}

	private void addPositiveCase(InvariantInfo info, ManagedProofObligation tpo, ITransitionPathSource source) {
		List<PositiveCaseInfo> list = positiveCaseInfo.get(info);
		if (list == null) {
			list = new ArrayList<>();
			positiveCaseInfo.put(info, list);
		}
		
		list.add(new PositiveCaseInfo(source, tpo));
		
	}

	private InvariantInfo getInvariant(String name) {
		for (final InvariantInfo inv : allInvariants) {
			if (inv.name.equals(name)) {
				return inv;
			}
		}

		return null;
	}
}
