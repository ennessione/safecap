package uk.ac.ncl.safecap.xdata.provers;

import uk.ac.ncl.safecap.xdata.verification.errorinjection.IErrorInjector;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.types.XType;

public interface IElementTranslator {
	void translate(StringBuffer sb, XType type) throws TranslationException;

	void translateDeclaration(StringBuffer sb, XType type) throws TranslationException;

	void translate(StringBuffer sb, IXFunction function, IErrorInjector errorInjector) throws TranslationException;

	String mapTypeId(String identifier) throws TranslationException;

	String mapTypeLiteral(String identifier) throws TranslationException;
}
