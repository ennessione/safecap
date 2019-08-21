package uk.ac.ncl.safecap.xdata.verification.console;

import uk.ac.ncl.safecap.xdata.provers.prob.ProBElementTranslator;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;
import uk.ac.ncl.safecap.xmldata.types.XFunctionBasic;

public class ExportDataToB extends DatasetBaseCommand {
	public static ExportDataToB INSTANCE = new ExportDataToB();

	private ExportDataToB() {
	}

	@Override
	public String getName() {
		return "export:data:b";
	}

	@Override
	public int getArguments() {
		return 0;
	}

	@Override
	public void execute(ISafeCapConsole console, String[] arguments) {
		ProBElementTranslator translator = new ProBElementTranslator(console.getDataContext(), false);

		StringBuffer sb = new StringBuffer();
		sb.append("MACHINE " + console.getDataContext().getName() + "\n");

		int c;
		if (console.getDataContext().getEnums().size() > 0) {
			sb.append("SETS\n");
			c = 0;
			for (String _t : console.getDataContext().getEnums()) {
				XEnumType t = console.getDataContext().getEnum(_t);
				if (c > 0)
					sb.append(";\n");

				translator.translateDeclaration(sb, t);
				c++;
			}
		}

		if (console.getDataContext().getFunctions().size() > 0) {
			sb.append("\nCONCRETE_CONSTANTS\n");
			c = 0;
			for (IXFunction f : console.getDataContext().getFunctions()) {
				XFunctionBasic ff = (XFunctionBasic) f;
				if (ff.getMaps().size() != 0) {
					if (c > 0)
						sb.append(", \n");
					sb.append("\t");
					sb.append(translator.getNameMapper().mapFreeIdentifier(f.getName()));
					c++;
				}
			}
			sb.append("\nPROPERTIES\n");
			c = 0;
			for (IXFunction f : console.getDataContext().getFunctions()) {
				XFunctionBasic ff = (XFunctionBasic) f;
				if (ff.getMaps().size() != 0) {
					if (c > 0)
						sb.append(" & \n");

					translator.translate(sb, f, null);
					c++;
				}
			}
		}

		sb.append("\n");
		sb.append("END\n");
		System.out.println(sb.toString());
	}
}
