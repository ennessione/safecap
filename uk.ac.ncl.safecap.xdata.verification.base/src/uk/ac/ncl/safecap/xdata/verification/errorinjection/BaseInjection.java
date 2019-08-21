package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.sets.InvalidSetOpException;
import uk.ac.ncl.safecap.xdata.provers.ICondition;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.types.XRelationType;

public class BaseInjection implements IErrorInjector {
	private final String kind;
	private final IXFunction function;
	private final Object domain;
	private final List<Object> replacement;

	protected BaseInjection(String kind, IXFunction function, Object domain, List<Object> replacement) {
		super();
		this.kind = kind;
		this.function = function;
		this.domain = domain;
		this.replacement = replacement;	
	}
	
	public List<CLExpression> instantiate(SDARuntimeExecutionContext context, CLExpression source) throws InvalidSetOpException {
		CLType domainType = SDAUtils.mapType(((XRelationType) function.getFunctionType()).getDomain(), context);
		CLExpression target = CLUtils.makeLiteral(domain);
		List<CLExpression> result = CLUtils.instantiate(context, source, domainType, target);
		return result;
	}

	public List<ICondition> instantiate(SDARuntimeExecutionContext context, Collection<ICondition> conditions) throws InvalidSetOpException {
		List<ICondition> r = new ArrayList<>();
		
		for(ICondition c: conditions) {
			List<CLExpression> instd = instantiate(context, c.getGoal());
			System.out.println("Instantiated " + c.getGoal() + " into ");
			for(CLExpression e: instd) {
				System.out.println("\t" + e);
				r.add(new ProxyCondition(c, e));
			}
		}
		
		return r;
	}
	
	public String getKind() {
		return kind;
	}

	public IXFunction getFunction() {
		return function;
	}

	public Object getDomain() {
		return domain;
	}

	public List<Object> getReplacement() {
		return replacement;
	}

	@Override
	public List<Object> injectError(IXFunction f, Object domain) {
		if (f == function && domain == this.domain && replacement != null) {
			return replacement;
		} else {
			return null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (domain == null ? 0 : domain.hashCode());
		result = prime * result + (function == null ? 0 : function.hashCode());
		result = prime * result + (replacement == null ? 0 : replacement.hashCode());
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
		final BaseInjection other = (BaseInjection) obj;
		if (domain != other.domain) {
			return false;
		}
		if (function != other.function) {
			return false;
		}
		if (replacement == null) {
			if (other.replacement != null) {
				return false;
			}
		} else if (!replacement.equals(other.replacement)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return function.getName() + "(" + domain + ") := " + replacement + "[" + kind + "]";
	}

	@Override
	public List<Object> getExtraDomain(IXFunction function) {
		return Collections.emptyList();
	}

}
