package uk.ac.ncl.safecap.xdata.verification.core;

import java.util.Collection;

import uk.ac.ncl.prime.sim.lang.CLElement;
import uk.ac.ncl.prime.sim.lang.CLExecutionException;
import uk.ac.ncl.prime.sim.lang.XTagContext;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.prime.sim.parser.CLValuationContext;
import uk.ac.ncl.safecap.xdata.verification.dictionary.TermDefinition;

public class RuntimeExecutionContext {
	protected XTagContext xtags;
	protected CLValuationContext stateContext;
	protected RuntimeExecutionContext parent;

	public RuntimeExecutionContext(RuntimeExecutionContext parent) {
		stateContext = new CLValuationContext(parent == null ? null : parent.stateContext);
		xtags = parent == null ? new XTagContext() : parent.xtags;
		this.parent = parent;
	}

	public RuntimeExecutionContext() {
		xtags = new XTagContext();
		stateContext = new CLValuationContext(null);
		// this.console = this;
	}

	public Collection<String> getTerms() {
		return parent.getTerms();
	}

	public TermDefinition getTerm(String name) {
		return parent.getTerm(name);
	}

	public TypingContext getRootContext() {
		return parent.getRootContext();
	}

	public CLValuationContext getStateContext() {
		return stateContext;
	}

//	public synchronized  void pushContext() {
//		stateContext = new ScriptStateContext(stateContext);
//	}
//
//	public synchronized void popContext() throws CLExecutionException {
//
//		if (stateContext.getParent() != null) {
//			stateContext = stateContext.getParent();
//		} else {
//			System.err.println("Popping the root frame");
//		}
//		if (stateContext == null) {
//			throw new CLExecutionException("Popping the root frame");
//		}
//	}

	public synchronized void defineNew(String id, Object value) {
		stateContext.defineNew(id, value);
	}

	public synchronized Object getValue(String id) throws CLExecutionException {
		if (id == null) {
			return null;
		}
		final Object v = stateContext.getValue(id);
		if (v == null && parent != null) {
			return parent.getValue(id);
		} else {
			return v;
		}
	}

	public void setValue(String id, Object newvalue) throws CLExecutionException {
		stateContext.setValue(id, newvalue);
	}

	public boolean isImmutable(String id) {
		return parent.isImmutable(null);
	}

	public void addXTag(String tag, CLElement element) {
		xtags.addXTag(tag, element);
	}

	public void getXTag(String tag) {
		xtags.getXTag(tag);
	}

}
