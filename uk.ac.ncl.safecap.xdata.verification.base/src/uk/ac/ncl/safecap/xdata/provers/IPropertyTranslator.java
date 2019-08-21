package uk.ac.ncl.safecap.xdata.provers;

public interface IPropertyTranslator {

	String translateProperty(ICondition condition, boolean negated) throws TranslationException;

	INameMapper getNameMapper();
}
