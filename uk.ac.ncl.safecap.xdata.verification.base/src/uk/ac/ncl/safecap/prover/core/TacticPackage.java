package uk.ac.ncl.safecap.prover.core;

import uk.ac.ncl.safecap.xdata.provers.ConstantFoldingTopDown;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;

public class TacticPackage {
	// private List<IProofEventListener> listeners;
	private final SDAContext context;
	private final ConstantFoldingTopDown folding;

	public TacticPackage(SDAContext context) {
		// this.listeners = new ArrayList<IProofEventListener>();
		this.context = context;
		folding = new ConstantFoldingTopDown(context.getRootRuntimeContext());
		context.getCatalog().resource();
	}

	public ConstantFoldingTopDown getFolding() {
		return folding;
	}

	// public List<IProofEventListener> getListener() {
	// return listeners;
	// }
	//
	// public void addListener(IProofEventListener listener) {
	// listeners.add(listener);
	// }

	public SDAContext getContext() {
		return context;
	}
	//
	// public void fireChange(IProofBranch element) {
	// for (IProofEventListener listener : listeners)
	// listener.update(element);
	// }

}
