package uk.ac.ncl.safecap.xdata.verification.console;

import java.util.Collection;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.typing.CLUserType;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.CLValuationContext;
import uk.ac.ncl.safecap.xdata.verification.core.FastRuntimeContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.dictionary.TermDefinition;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.IErrorInjector;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.IErrorInjectorManager;

public class SlottableSDARuntimeExecutionContext extends SDARuntimeExecutionContext {
	private SDARuntimeExecutionContext parent;
	private final TypingContext modelContext;
	private final CLValuationContext stateContext;

	public SlottableSDARuntimeExecutionContext() {
		stateContext = new CLValuationContext(null);
		modelContext = new TypingContext();
	}

	public void setParent(SDARuntimeExecutionContext parent) {
		this.parent = parent;
		modelContext.setParent(parent.getRootContext());
	}

	@Override
	public FastRuntimeContext getFastRuntimeContext() {
		return parent.getFastRuntimeContext();
	}

	@Override
	public FastRuntimeContext getFastRuntimeContext(CLExpression expr) throws CLExecutionException {
		return parent.getFastRuntimeContext(expr);
	}

	@Override
	public FastRuntimeContext getFastRuntimeContext(Collection<String> names) throws CLExecutionException {
		return parent.getFastRuntimeContext(names);
	}

	@Override
	public SDAContext getSdaContext() {
		return parent.getSdaContext();
	}

	@Override
	public IErrorInjector getErrorInjector() {
		return parent.getErrorInjector();
	}

	@Override
	public void setErrorInjector(IErrorInjectorManager errorInjector) {
		parent.setErrorInjector(errorInjector);
	}

	@Override
	public void dropValueCaches() {
		parent.dropValueCaches();
	}

	@Override
	public List<String> getTerms() {
		return parent.getTerms();
	}

	@Override
	public TermDefinition getTerm(String name) {
		return parent.getTerm(name);
	}

	@Override
	public TypingContext getDataContext() {
		return parent.getDataContext();
	}

	@Override
	public TypingContext getModelContext() {
		return modelContext;
	}

	@Override
	public TypingContext getRootContext() {
		return modelContext;
	}

	@Override
	public CLUserType resolveType(String name) {
		return getRootContext().resolveType(name);
	}

	/**
	 * Sets cached value. This value sits on top of state context and overwrites
	 * value (if any) imported from data context. Use with care as has complex life
	 * span issue and interaction with the static builder.
	 */
	@Override
	public void setValue(String id, Object value) {
		parent.setValue(id, value);
	}

	@Override
	public synchronized Object getValue(String id) throws CLExecutionException {
		final Object value = stateContext.getValue(id);
		if (value == null) {
			return parent.getValue(id);
		} else {
			return value;
		}
	}

}