package uk.ac.ncl.safecap.xdata.verification.console;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;

public class CatalogIsConst extends CatalogBaseCommand {
	public static CatalogIsConst INSTANCE = new CatalogIsConst();

	private CatalogIsConst() {
	}

	@Override
	public String getName() {
		return "const";
	}

	@Override
	public int getArguments() {
		return 1;
	}

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

			final Boolean result = expr.isConstant(context.getRootContext());
			console.out(result.toString());
		} catch (final Exception e) {
			console.err(e.getMessage());
		}
		return;
	}

}
