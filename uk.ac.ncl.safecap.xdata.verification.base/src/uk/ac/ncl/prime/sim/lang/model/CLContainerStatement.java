package uk.ac.ncl.prime.sim.lang.model;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.prime.sim.lang.CLElement;
import uk.ac.ncl.prime.sim.lang.ICLContainer;
import uk.ac.ncl.prime.sim.lang.semantics.SymbolicExecutionContext;
import uk.ac.ncl.prime.sim.parser.SourceLocation;

public abstract class CLContainerStatement extends CLStatement implements ICLContainer {
	private final List<CLElement> children;

	protected CLContainerStatement(int tag, SourceLocation location) {
		super(tag, location);
		children = new ArrayList<>();
	}

	public CLContainerStatement(int tag) {
		super(tag);
		children = new ArrayList<>();
	}

	void addChild(CLElement child) {
		children.add(child);
	}

	@Override
	public List<CLElement> getChildren() {
		return children;
	}

	@Override
	public void doExecuteSymbolically(SymbolicExecutionContext context) {
		for (final CLElement element : children) {
			if (element instanceof CLStatement) {
				final CLStatement s = (CLStatement) element;
				s.doExecuteSymbolically(context);
			}
		}
	}

	@Override
	public CLStatement locateChildStatement(int offset) {
		if (getLocation().contains(offset)) {
			for (final CLElement el : children) {
				if (el instanceof CLStatement) {
					final CLStatement st = (CLStatement) el;
					final CLStatement c = st.locateChildStatement(offset);
					if (c != null) {
						return c;
					}
				}
			}
			return this;
		} else {
			return null;
		}
	}
}
