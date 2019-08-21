package uk.ac.ncl.safecap.xdata.verification.transitions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlTransient;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.model.CLStatement;
import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.xdata.verification.core.InvariantInfo;

public class TransitionCluster implements ITransitionPathSource, ITransitionPOs, ITransitionCodeFragment {
	private static final int MAX_CYCLES = 1;
	private String path;
	private String[] kind;

	private List<TransitionClusterPart> parts;

	private int offsetStart = -1;
	private int offsetEnd = -1;
	private String source;
	private String codeFragment;

	transient private Collection<ManagedProofObligation> pos;

	public TransitionCluster() {

	}

	public TransitionCluster(String path, String[] kind) {
		this.path = path;
		this.kind = kind;
		parts = new ArrayList<>();
	}

	/**
	 * Name-based filtering
	 * 
	 * @param parameters
	 * @return
	 */
	public boolean isIncluded(InvariantInfo info) {
		final boolean hasFilterIn = info.hasAttribute("filter in");
		final boolean hasFilterOut = info.hasAttribute("filter out");

		final String[] parts = path.split("\\.");
		final String module = parts[0];
		final String request = parts[parts.length - 1];

		final String overall = module + "." + request;
		try {
			if (hasFilterIn) {
				boolean matched = false;
				for (final String fin : info.getAttribute("filter in")) {
					final Pattern pattern = Pattern.compile(fin);
					if (pattern.matcher(overall).matches()) {
						matched = true;
						break;
					}
				}

				if (!matched) {
					return false;
				}
			}

			if (hasFilterOut) {
				for (final String fin : info.getAttribute("filter out")) {
					final Pattern pattern = Pattern.compile(fin);
					if (pattern.matcher(overall).matches()) {
						return false;
					}
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
			return true;
		}
		return true;
	}

	/**
	 * Cluster filtering given primed identifiers
	 * 
	 * @param primed
	 * @return
	 */
	public TransitionCluster filter(Collection<String> primed) {
		TransitionCluster tc = _filter(primed);
		if (tc != null) {
			tc = compress(tc);
		}
		return tc;
	}

	private TransitionCluster _filter(Collection<String> primed) {
		// Make unprimed collection
		final Collection<String> unprimed = new ArrayList<>(primed.size());
		for (final String s : primed) {
			unprimed.add(CLUtils.unPrimed(s));
		}

		// 1. Make a copy of every cluster part and remove unnecessary postconditions
		final List<TransitionClusterPart> cparts = new ArrayList<>(parts.size());
		boolean changed = false;
		for (final TransitionClusterPart tcp : parts) {
			final TransitionClusterPart rtcp = filter(tcp, primed, unprimed);
			changed |= rtcp != tcp;
			if (rtcp != null && !cparts.contains(rtcp)) {
				cparts.add(rtcp);
			}
		}

		if (changed) {
			if (cparts.size() == 0) {
				return null;
			}
			final TransitionCluster tc = new TransitionCluster(path, kind);
			tc.parts = cparts;
			tc.codeFragment = codeFragment;
			tc.offsetEnd = offsetEnd;
			tc.offsetStart = offsetStart;
			tc.source = source;
			return tc;
		} else {
			return this;
		}
	}

	private TransitionClusterPart filter(TransitionClusterPart tcp, Collection<String> primed, Collection<String> unprimed) {
		final List<CLExpression> preconditions = new ArrayList<>();
		final List<CLExpression> postconditions = new ArrayList<>();
		final Collection<String> preidents = new HashSet<>(unprimed);

		int cycles = 0;
		int oldsize;
		do {
			oldsize = preconditions.size();
			for (final CLExpression pre : tcp.getParsed().getPreconditions()) {
				if (pre != null && !preconditions.contains(pre) && CLUtils.isRelevant(preidents, pre)) {
					preconditions.add(pre);
					preidents.addAll(pre.getFreeIdentifiers());
				}
			}
		} while (preconditions.size() != oldsize && ++cycles < MAX_CYCLES);

		for (final CLExpression post : tcp.getParsed().getPostconditions()) {
			if (post == null) {
				return null; // filter out unparsed parts
			}
			if (CLUtils.isRelevant(primed, post)) {
				postconditions.add(post);
			}
		}

		if (preconditions.size() != tcp.getParsed().getPreconditions().size()
				|| postconditions.size() != tcp.getParsed().getPostconditions().size()) {
			if (postconditions.size() == 0) {
				return null;
			} else {
				return TransitionClusterPart.make(tcp, preconditions, postconditions);
			}
		} else {
			return tcp;
		}
	}

	private TransitionCluster compress(TransitionCluster cluster) {
		if (cluster.getParts().size() < 2) {
			return cluster;
		}

		final List<List<TransitionClusterPart>> subdivisions = new ArrayList<>();

		// System.out.println("Compressing " + cluster.getParts().size() + " parts");

		// computing equivalence classes based on postconditions
		for (final TransitionClusterPart tp : cluster.getParts()) {
			classify(tp, subdivisions);
		}

		// System.out.println("\tIn " + subdivisions.size() + " equivalence classes");

		boolean changed = false;
		// try to minimise each class
		for (final List<TransitionClusterPart> eqvc : subdivisions) {
			// System.out.println("\tConsidering eqv class with " + eqvc.size() + "
			// members");
			final List<TransitionClusterPart> toRemove = new ArrayList<>();
			for (int i = 0; i < eqvc.size(); i++) {
				for (int j = i + 1; j < eqvc.size(); j++) {
					final TransitionClusterPart left = eqvc.get(i);
					final TransitionClusterPart right = eqvc.get(j);
					if (!toRemove.contains(left) && !toRemove.contains(right)) {
						final int code = compress(left.getParsed().getPreconditions(), right.getParsed().getPreconditions());
						if (code == -1) {
							toRemove.add(right);
						} else if (code == 1) {
							toRemove.add(left);
							break;
						}
					}
				}
			}
			eqvc.removeAll(toRemove);
			changed |= !toRemove.isEmpty();
//			if (toRemove.isEmpty())
//				System.out.println("\t\tCompress failed");
//			else
//				System.out.println("\t\tCompressed to " + eqvc.size() + " members");
		}

		if (changed) {

			final List<TransitionClusterPart> parts = new ArrayList<>();
			for (final List<TransitionClusterPart> eqvc : subdivisions) {
				for (final TransitionClusterPart tp : eqvc) {
					parts.add(tp);
				}
			}

			// System.out.println("Compression ratio " + ( (double)
			// cluster.getParts().size() / parts.size()));

			final TransitionCluster tc = new TransitionCluster(path, kind);
			tc.parts = parts;
			tc.codeFragment = codeFragment;
			tc.offsetEnd = offsetEnd;
			tc.offsetStart = offsetStart;
			tc.source = source;
			return tc;
		} else {
			return this;
		}
	}

	private void classify(TransitionClusterPart tp, List<List<TransitionClusterPart>> subdivisions) {
		for (final List<TransitionClusterPart> ss : subdivisions) {
			final TransitionClusterPart tp2 = ss.get(0);
			if (samePostconditions(tp, tp2)) {
				ss.add(tp);
				return;
			}
		}

		final List<TransitionClusterPart> nsd = new ArrayList<>();
		nsd.add(tp);
		subdivisions.add(nsd);
	}

	private boolean samePostconditions(TransitionClusterPart a, TransitionClusterPart b) {
		return a.getPostconditions().equals(b.getPostconditions());
	}

	private int compress(List<CLExpression> a, List<CLExpression> b) {
		final Collection<CLExpression> left = setminus(a, b);
		final Collection<CLExpression> right = setminus(b, a);

		if (left.size() == 0 && right.size() == 0) {
			return -1;
		} else if (left.size() == 0 && right.size() != 0) {
			return -1;
		} else if (left.size() != 0 && right.size() == 0) {
			return 1;
		} else {
			final CLExpression[] matching = findNegatedPair(left, right);
			if (matching != null) {
				final List<CLExpression> ac = new ArrayList<>(a);
				final List<CLExpression> bc = new ArrayList<>(b);
				ac.remove(matching[0]);
				bc.remove(matching[1]);
				return _compress(ac, bc);
			}
		}

		return 0;
	}

	private int _compress(List<CLExpression> a, List<CLExpression> b) {
		final Collection<CLExpression> left = setminus(a, b);
		final Collection<CLExpression> right = setminus(b, a);

		if (left.size() == 0 && right.size() == 0) {
			return -1;
		} else if (left.size() == 0 && right.size() != 0) {
			return -1;
		} else if (left.size() != 0 && right.size() == 0) {
			return 1;
		} else {
			final CLExpression[] matching = findNegatedPair(left, right);
			if (matching != null) {
				a.remove(matching[0]);
				b.remove(matching[1]);
				return _compress(a, b);
			}
		}

		return 0;
	}

	private CLExpression[] findNegatedPair(Collection<CLExpression> left, Collection<CLExpression> right) {
		for (final CLExpression e : left) {
			for (final CLExpression z : right) {
				if (e.equals(CLUtils.negate(z))) {
					return new CLExpression[] { e, z };
				}
			}
		}

		return null;
	}

	/*
	 * private Collection<CLExpression> setminus(List<CLExpression> a,
	 * List<CLExpression> b) { Collection<CLExpression> result = new
	 * HashSet<CLExpression>(); for (CLExpression e : a) if (!b.contains(e))
	 * result.add(e);
	 * 
	 * return result; }
	 */

	private Collection<CLExpression> setminus(List<CLExpression> a, List<CLExpression> b) {
		final List<CLExpression> result = new ArrayList<>(a);
		result.removeAll(b);
		return result;
	}

	public void addPart(TransitionClusterPart part) {
		parts.add(part);
	}

	@Override
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String[] getKind() {
		return kind;
	}

	public void setKind(String[] kind) {
		this.kind = kind;
	}

	public List<TransitionClusterPart> getParts() {
		return parts;
	}

	public void setParts(List<TransitionClusterPart> parts) {
		this.parts = parts;
	}

	public int getOffsetStart() {
		return offsetStart;
	}

	public void setOffsetStart(int offsetStart) {
		this.offsetStart = offsetStart;
	}

	public int getOffsetEnd() {
		return offsetEnd;
	}

	public void setOffsetEnd(int offsetEnd) {
		this.offsetEnd = offsetEnd;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String getCodeFragment() {
		return codeFragment;
	}

	public void setCodeFragment(String codeFragment) {
		this.codeFragment = codeFragment;
	}

	public void setStatement(CLStatement statement) {
		codeFragment = statement.asString();
	}

	@Override
	@XmlTransient
	public Collection<ManagedProofObligation> getPos() {
		return pos;
	}

	public void setPos(Collection<ManagedProofObligation> pos) {
		this.pos = pos;
	}
}
