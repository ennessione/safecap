package uk.ac.ncl.safecap.xdata.verification;

import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.Transient;
import org.eclipse.sapphire.TransientProperty;
import org.eclipse.sapphire.Type;

import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.model.CLStatement;

public interface IFormulaSource extends IParseable {
	ElementType TYPE = new ElementType(IFormulaSource.class);

	@Type(base = CLExpression.class)
	TransientProperty PROP_PARSED = new TransientProperty(TYPE, "Parsed");

	Transient<CLExpression> getParsed();

	void setParsed(CLExpression value);

	@Type(base = CLStatement.class)
	TransientProperty PROP_STATEMENT = new TransientProperty(TYPE, "Statement");

	Transient<CLStatement> getStatement();

	void setStatement(CLStatement value);

	@Type(base = Integer.class)
	TransientProperty PROP_CARETOFFSET = new TransientProperty(TYPE, "CaretOffset");

	Transient<Integer> getCaretOffset();

	void setCaretOffset(Integer value);
}
