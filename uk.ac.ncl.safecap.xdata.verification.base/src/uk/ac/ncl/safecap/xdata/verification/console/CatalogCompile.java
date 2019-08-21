package uk.ac.ncl.safecap.xdata.verification.console;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;

public class CatalogCompile extends BaseCommand {
	public static CatalogCompile INSTANCE = new CatalogCompile();

	private CatalogCompile() {
	}

	@Override
	public String getName() {
		return "comp";
	}

	@Override
	public int getArguments() {
		return 1;
	}

	@Override
	public void execute(ISafeCapConsole console, String[] arguments) {
		try {
			SDARuntimeExecutionContext context;

			if (console.getRootCatalog() != null) {
				final SDAContext dataContext = SDAUtils.getDataContext(console.getRootCatalog());
				context = dataContext.getRootRuntimeContext();
			} else {
				context = new SDARuntimeExecutionContext((SDARuntimeExecutionContext) null);
			}

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
			final String result = expr.compile(context);
			if (result == null) {
				console.err("comp failed");
				return;
			}
			console.out(result.toString());
		} catch (final Exception e) {
			console.err(e.getMessage());
		}
		return;
	}

}
