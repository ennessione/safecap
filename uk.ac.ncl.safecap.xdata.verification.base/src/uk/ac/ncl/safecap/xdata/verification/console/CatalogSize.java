package uk.ac.ncl.safecap.xdata.verification.console;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.sets.BSet;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;

public class CatalogSize extends CatalogBaseCommand {
	public static CatalogSize INSTANCE = new CatalogSize();

	private CatalogSize() {
	}

	@Override
	public String getName() {
		return "size";
	}

	@Override
	public int getArguments() {
		return 1;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(ISafeCapConsole console, String[] arguments) {
		try {
			final SDAContext dataContext = SDAUtils.getDataContext(console.getRootCatalog());
			final SDARuntimeExecutionContext context = dataContext.getRootRuntimeContext();

			final CLExpression expr = SDAUtils.parseString(context, arguments[0]);
			if (expr == null) {
				console.err("parse failed");
				return;
			}
			context.getRootContext().getValidation().reset();
			expr.type(context.getRootContext());
			if (expr.getType() == null || context.getRootContext().getValidation().hasErrors()) {
				console.err("type failed: " + context.getRootContext().getValidation().getErrors());
				return;
			}
			if (!expr.getType().isSet()) {
				console.err("not a set type");
				return;
			}
			final BSet result = (BSet) expr.getValue(context);
			console.out("" + result.card());
		} catch (final Exception e) {
			console.err(e.getMessage());
		}
		return;
	}

}
