package uk.ac.ncl.safecap.xdata.dataframe;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.CLUtils;
import uk.ac.ncl.safecap.xdata.provers.ICondition;
import uk.ac.ncl.safecap.xdata.provers.NativeEvaluationVerifier;
import uk.ac.ncl.safecap.xdata.provers.VerificationResult;
import uk.ac.ncl.safecap.xdata.provers.VerificationTool;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.errorinjection.ContextProxyCondition;
import uk.ac.ncl.safecap.xmldata.base.SDAImportException;

public class PropertySDADataFrame extends SDADataFrame {
	public PropertySDADataFrame(SDAContext context, String domainType, ICondition ... conditions) {
		super(context, domainType);
			
		for(ICondition condition: conditions)
			extend(condition);
	}

	private void extend(ICondition c)  {
		addColumn(c.getShortName());
		System.out.print("Processing " + c.getShortName() + " ");
		System.out.flush();
		for(DataFrameRow r: getRows()) {
			try {
				r.addValue(eval(c, r));
				System.out.print("." + c.getShortName());
				System.out.flush();
			} catch (SDAImportException e) {
				r.addValue(-1);
			}
		}
		System.out.println();
	}

	private int eval(ICondition c, DataFrameRow r) throws SDAImportException {
		SDAContext ctx = r.getRowContext(this);
		CLExpression n_c = CLUtils.flattenTermDependencies(getContext().getRootRuntimeContext(), c.getGoal());
		ContextProxyCondition c2 = new ContextProxyCondition(c, ctx, n_c);
		VerificationResult result = VerificationTool.proveExt(NativeEvaluationVerifier.INSTANCE, c2);
		if (result.isDefinite()) {
			if (result.isValid())
				return 2;
			else
				return 1;
		} else {
			return 0;
		}
	}

}
