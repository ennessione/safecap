package uk.ac.ncl.safecap.xdata.verification.transitions;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.parser.FormulaSource;

public class TransitionClusterPart implements ITransition {
	private List<LocatedString> preconditions;
	private List<LocatedString> postconditions;
	private String name;

	transient private TransitionParsed parsed;

	public TransitionClusterPart() {
	}

	public TransitionClusterPart(String name, List<CLExpression> pres, List<CLExpression> post) {
		assert name != null;
		this.name = name;
		preconditions = new ArrayList<>(pres.size());
		for (final CLExpression e : pres) {
			preconditions.add(LocatedString.make(e));
		}
		postconditions = new ArrayList<>(post.size());
		for (final CLExpression e : post) {
			postconditions.add(LocatedString.make(e));
		}
	}

	protected static TransitionClusterPart make(TransitionClusterPart source, List<CLExpression> pres, List<CLExpression> post) {
		final TransitionClusterPart tcp = new TransitionClusterPart(source.name, pres, post);
		final TransitionParsed parsed = new TransitionParsed(source.name);

		final List<FormulaSource> fsPreconditions = new ArrayList<>();
		final List<FormulaSource> fsPostconditions = new ArrayList<>();

		for (final CLExpression pre : pres) {
			final int index = source.getParsed().getPreconditions().indexOf(pre);
			fsPreconditions.add(source.getParsed().getFsPreconditions().get(index));
		}

		for (final CLExpression pst : post) {
			final int index = source.getParsed().getPostconditions().indexOf(pst);
			fsPostconditions.add(source.getParsed().getFsPostconditions().get(index));
		}

		parsed.setFsPreconditions(fsPreconditions);
		parsed.setFsPostconditions(fsPostconditions);
		parsed.setPreconditions(pres);
		parsed.setPostconditions(post);
		tcp.setParsed(parsed);

		return tcp;
	}

	@Override
	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String kind) {
		name = kind;
	}

	@Override
	public List<LocatedString> getPreconditions() {
		return preconditions;
	}

	public void setPreconditions(List<LocatedString> preconditions) {
		this.preconditions = preconditions;
	}

	@Override
	public List<LocatedString> getPostconditions() {
		return postconditions;
	}

	public void setPostconditions(List<LocatedString> postconditions) {
		this.postconditions = postconditions;
	}

	@Override
	@XmlTransient
	public void setParsed(TransitionParsed parsed) {
		this.parsed = parsed;
	}

	@Override
	public TransitionParsed getParsed() {
		return parsed;
	}

	@Override
	public boolean isValid() {
		if (parsed != null) {

			if (preconditions != null) {
				if (parsed.getFsPreconditions() == null || preconditions.size() != parsed.getPreconditions().size()
						|| parsed.getPreconditions().size() != parsed.getFsPreconditions().size()) {
					return false;
				}
				for (int i = 0; i < parsed.getFsPreconditions().size(); i++) {
					if (parsed.getFsPreconditions().get(i).hasErrors() || parsed.getPreconditions().get(i) == null) {
						return false;
					}
				}
			}

			if (postconditions != null) {
				if (parsed.getFsPostconditions() == null || postconditions.size() != parsed.getPostconditions().size()
						|| parsed.getPostconditions().size() != parsed.getFsPostconditions().size()) {
					return false;
				}
				for (int i = 0; i < parsed.getFsPostconditions().size(); i++) {
					if (parsed.getFsPostconditions().get(i).hasErrors() || parsed.getPostconditions().get(i) == null) {
						return false;
					}
				}
			}

			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (postconditions == null ? 0 : postconditions.hashCode());
		result = prime * result + (preconditions == null ? 0 : preconditions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TransitionClusterPart other = (TransitionClusterPart) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (postconditions == null) {
			if (other.postconditions != null) {
				return false;
			}
		} else if (!postconditions.equals(other.postconditions)) {
			return false;
		}
		if (preconditions == null) {
			if (other.preconditions != null) {
				return false;
			}
		} else if (!preconditions.equals(other.preconditions)) {
			return false;
		}
		return true;
	}

}
