package uk.ac.ncl.safecap.xdata.verification.console;

import java.io.File;
import java.io.FileNotFoundException;

import uk.ac.ncl.safecap.xdata.dataframe.PropertySDADataFrame;
import uk.ac.ncl.safecap.xdata.dataframe.SDADataFrame;
import uk.ac.ncl.safecap.xdata.provers.ConditionConjecture;
import uk.ac.ncl.safecap.xdata.provers.ICondition;
import uk.ac.ncl.safecap.xdata.verification.Conjecture;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDAUtils;
import uk.ac.ncl.safecap.xdata.verification.core.VisitorUtils;

public class CatalogDataFrame extends CatalogBaseCommand {
	public static CatalogDataFrame INSTANCE = new CatalogDataFrame();

	private CatalogDataFrame() {
	}
	
	@Override
	public String getName() {
		return "df:export";
	}

	@Override
	public int getArguments() {
		return -1;
	}

	@Override
	public void execute(ISafeCapConsole console, String[] arguments) {
		if (arguments.length == 0) {
			console.err("Require domain type");
			return;
		}
		
		SDADataFrame frame;
		SDAContext context = SDAUtils.getDataContext(console.getRootCatalog());
		String domainType = arguments[0];
		if (context.getEnum(domainType) == null) {
			console.err("Invalid domain type - " + domainType);
			return;
		}
		if (arguments.length > 1) {
			ICondition[] conditions = new ICondition[arguments.length-1];
			for(int i = 1; i < arguments.length; i++) {
				String p = arguments[i];
				Conjecture c = VisitorUtils.getConjectureByKey(console.getRootCatalog(), p);
				if (c == null) {
					console.err("Invalid property key - " + p);
					return;
				}
				conditions[i-1] = new ConditionConjecture(c);
			}
			frame = new PropertySDADataFrame(context, domainType, conditions);
		} else {
			frame = new SDADataFrame(context, domainType);
		}

		
		File file = new File("/Users/cmdadmin/out.csv");
		try {
			frame.toCSV(file);
		} catch (FileNotFoundException e) {
			console.err("Failed saving data frame - " + e.getMessage());
			return;
		}
		console.err("Saved data frame " + frame.getRowNumber() + " x " + frame.getColumnNumber() + " into " + file.getAbsolutePath());
	}

}
