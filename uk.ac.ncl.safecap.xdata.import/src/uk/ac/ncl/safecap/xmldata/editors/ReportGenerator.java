package uk.ac.ncl.safecap.xmldata.editors;

import java.io.File;

import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataNamespace;
import uk.ac.ncl.safecap.xmldata.FileUtil;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.types.XRelationType;
import uk.ac.ncl.safecap.xmldata.types.XType;

public class ReportGenerator {
	private final StringBuffer sb;
	private final DataContext context;

	public ReportGenerator(DataContext context) {
		sb = new StringBuffer();
		this.context = context;
		header();

//		bodySection("Template generated on " + new Date(System.currentTimeMillis()));
//		bodySection("This document defines " + context.getTypeRegistry().getEnums().size() + " types, " +
//				context.getNamespaces().size() + " namespaces, and " +
//				context.getFunctions().size() + " functions."
//		);
//
//		headerSection("1. Types");
		int i = 1;
//		for(String ns: context.getTypeRegistry().getEnums()) {
//			document(i++, context.getTypeRegistry().getEnum(ns));
//		}

		i = 1;
		headerSection("2. Structures");
		for (final DataNamespace ns : context.getNamespaces()) {
			document(i++, ns);
		}

		footer();
	}

	public void saveReport(File file) throws Exception {
		FileUtil.setFileContents(sb.toString(), file);
	}

	public String getReport() {
		return sb.toString();
	}

	private void header() {
		sb.append("<html>");
		sb.append("<header>");
		sb.append("<title>Documentation report</title>");
		styles();
		sb.append("</header>");
		sb.append("<body>");
	}

	private void footer() {
		sb.append("</body>");
		sb.append("</html>");
	}

	private void styles() {
	}

//	private void document(int index, XEnumType en) {
//		headerSubSection("1." + index + " Type " + type(en.getName()));
//		sb.append("<a name=\"type___"+ en.getName() +"\"></a>");
//		styleStart("set");
//		sb.append(context.getEnumMembers(en));
//		styleEnd();
//
//		Set<IXFunction> from = funFrom(en);
//		Set<IXFunction> to = funTo(en);
//
//		if (!from.isEmpty()) {
//			sb.append("Relations mapping from this type:");
//			sb.append("<ul>");
//			for(IXFunction f: from)
//				sb.append("<li><a href=\"funct___"+ f.getName() +"\">" + f.getName() + "</a></li>");
//			sb.append("</ul>");
//		}
//
//		if (!from.isEmpty()) {
//			sb.append("Relations mapping to this type:");
//			sb.append("<ul>");
//			for(IXFunction f: to)
//				sb.append("<li><a href=\"funct___"+ f.getName() +"\">" + f.getName() + "</a></li>");
//			sb.append("</ul>");
//		}
//
//		textMissing();
//	}
//
//	private Set<IXFunction> funFrom(XType type) {
//		Set<IXFunction> r = new HashSet<IXFunction>();
//		for(IXFunction f: context.getFunctions()) {
//			if (f.getFunctionType() instanceof XRelationType) {
//				XRelationType rt = (XRelationType) f.getFunctionType();
//				if (rt.getDomain().equals(type))
//					r.add(f);
//			}
//		}
//
//		return r;
//	}
//
//	private Set<IXFunction> funTo(XType type) {
//		Set<IXFunction> r = new HashSet<IXFunction>();
//		for(IXFunction f: context.getFunctions()) {
//			if (f.getFunctionType() instanceof XRelationType) {
//				XRelationType rt = (XRelationType) f.getFunctionType();
//				if (rt.getRange().equals(type))
//					r.add(f);
//			}
//		}
//
//		return r;
//	}

	private void document(int index, DataNamespace ns) {
		headerSubSection("2." + index + " Namespace " + ns.getId());
		sb.append("<a name=\"namespace___" + ns.getId() + "\"></a>");

		final String prefix = ns.getId() + ".";
		int j = 1;
		for (final IXFunction f : context.getFunctions()) {
			if (f.getName().startsWith(prefix)) {
				document(index, j++, f);
			}
		}
	}

	private void document(int nsIndex, int funIndex, IXFunction f) {
		String fname = f.getName();
		final int c = '.';
		final int index = f.getName().indexOf(c);
		if (index > 0) {
			fname = fname.substring(index + 1);
		}

		if (f.getFunctionType() != null) {
			final XRelationType rt = (XRelationType) f.getFunctionType();
			headerFunction(
					"2." + nsIndex + "." + funIndex + " " + (rt.getKind().isFunction() ? "Function " : "Relation ") + function(fname));
			sb.append("<a name=\"funct___" + f.getName() + "\"></a>");
			bodySection("Path: " + f.getCanonicalName());
			bodySection("From " + type(rt.getDomain()) + " to " + type(rt.getRange()));

//			XFunctionBasic ff = (XFunctionBasic) f;
//			styleStart("map");
//			sb.append(ff.getParts());
//			styleEnd();
			styleEnd();

		} else {
			headerFunction("2." + nsIndex + "." + funIndex + " Entity " + function(fname));
			sb.append("<a name=\"funct___" + f.getName() + "\"></a>");
			bodySection("Path: " + f.getCanonicalName());
			bodySection("Is not well-typed.");
		}

		textMissing();
	}

	private void textMissing() {
		sb.append("<br><br><br>");
	}

	private void headerFunction(String text) {
		formatTag(text, "h3");
	}

	private void headerSection(String text) {
		formatTag(text, "h1");
	}

	private void headerSubSection(String text) {
		formatTag(text, "h2");
	}

	private void bodySection(String text) {
		formatTag(text, "p");
	}

//	private void keyword(String text) {
//		formatTag(text, "b");
//	}

//	private void styleStart(String text) {
//		sb.append("<p class=\"" + text + "\">\n");
//	}

	private void styleEnd() {
		sb.append("\n</p>\n");
	}

	private String type(XType text) {
		return "<a href=\"#type___" + text.toString() + "\">" + formatColour("<b>" + text + "</b>", "green") + "</a>";
	}

//	private String type(String text) {
//		return formatColour("<b>" + text + "</b>", "green");
//	}		

	private String function(String text) {
		return formatColour("<b>" + text + "</b>", "blue");
	}

	private String formatColour(String text, String colour) {
		return "<font color=\"" + colour + "\">" + text + "</font>";
	}

	private void formatTag(String text, String tag) {
		sb.append("<" + tag + ">");
		sb.append(text);
		sb.append("</" + tag + ">");
	}

//	private void formatClass(String text, String css_class) {
//		sb.append("<p class=\"" + css_class +"\">");
//		sb.append(text);
//		sb.append("</p>");
//	}
}
