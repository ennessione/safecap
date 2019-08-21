package uk.ac.ncl.prime.sim.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ncl.prime.sim.lang.CLBuilder;
import uk.ac.ncl.prime.sim.lang.CLCollection;
import uk.ac.ncl.prime.sim.lang.CLElement;
import uk.ac.ncl.prime.sim.lang.CLExpression;
import uk.ac.ncl.prime.sim.lang.model.CLBlockStatement;
import uk.ac.ncl.prime.sim.lang.model.CLStatement;
import uk.ac.ncl.prime.sim.lang.parser.ErrInfo;
import uk.ac.ncl.prime.sim.lang.parser.syntree;
import uk.ac.ncl.prime.sim.lang.typing.CLType;
import uk.ac.ncl.prime.sim.lang.typing.TypingContext;
import uk.ac.ncl.safecap.xdata.verification.core.SDARuntimeExecutionContext;

public class CLFormulaParser {
	public static final String MAGIC_SUB = "%%21%%";
	public static final String MAGIC_TYP = "%%24%%";
	private final SDARuntimeExecutionContext model;

	public CLFormulaParser(SDARuntimeExecutionContext model) {
		this.model = model;
	}

	public SDARuntimeExecutionContext getModel() {
		return model;
	}

	public void processTerms(IProgressMonitor monitor) {
		final ValidationContext validation = new ValidationContext();
		final CLBuilder builder = new CLBuilder(validation, model);
		builder.processTerms(monitor);
	}

	public CLExpression parse(FormulaSource formulaSource) throws Exception {
		try {
			return parse(formulaSource, -1, -1, true, null);
		} catch (final Throwable e) {
			return null;
		}
	}

	public CLExpression parse(FormulaSource formulaSource, int start, int end, boolean clear, TypingContext typingContext)
			throws Exception {

		final ValidationContext validation = new ValidationContext(start);

		CLExpression expression = null;

		if (clear) {
			formulaSource.clear();
		}
		final String text = start == -1 ? formulaSource.getText() : formulaSource.getText().substring(start, end);
		validation.reset();

		final InputStream is = new ByteArrayInputStream(text.getBytes());
		final List<ErrInfo> info = new ArrayList<>(20);

		final syntree result = uk.ac.ncl.prime.sim.lang.parser.Parser.parse(is, info);

		validation.setParseErrors(info);

		if (info.isEmpty()) {
			final CLBuilder builder = new CLBuilder(validation, model);
			final Object x = builder.build(result);

			if (x instanceof CLExpression) {
				expression = (CLExpression) x;

				if (validation.getErrors().isEmpty()) {
					if (typingContext == null) {
						typingContext = model.getRootContext();
					}
					final ValidationContext old = typingContext.getValidation();
					typingContext.setValidation(validation);
					try {
						expression.type(typingContext);
						typingContext.bakeTypes();
						// ctx.debug_print_typeSolutions();
					} catch (final Exception e) {
						// System.out.println(ctx.toString());
						// e.printStackTrace();
						return null;
					} finally {
						typingContext.setValidation(old);
					}
				}
			}
		}

		for (final ErrInfo einfo : validation.getParseErrors()) {
			formulaSource.add(einfo.start + validation.getSourceOffset(), einfo.end + validation.getSourceOffset(), einfo.message);
		}

		for (final CLElement element : validation.getErrors().keySet()) {
			formulaSource.add(element.getLocation().getStart() + validation.getSourceOffset(),
					element.getLocation().getEnd() + validation.getSourceOffset(), validation.getErrors().get(element));
		}

		if (!validation.getErrors().isEmpty()) {
			expression = null;
		}

		return expression;
	}

	public CLExpression parseOnly(FormulaSource formulaSource) throws Exception {
		return parseOnly(formulaSource, -1, -1, true);
	}

	public CLExpression parseOnly(FormulaSource formulaSource, int start, int end, boolean clear) throws Exception {

		final ValidationContext validation = new ValidationContext(start);

		CLExpression expression = null;

		if (clear) {
			formulaSource.clear();
		}

		final String text = start == -1 ? formulaSource.getText() : formulaSource.getText().substring(start, end);
		validation.reset();

		final InputStream is = new ByteArrayInputStream(text.getBytes());
		final List<ErrInfo> info = new ArrayList<>(20);

		final syntree result = uk.ac.ncl.prime.sim.lang.parser.Parser.parse(is, info);

		validation.setParseErrors(info);

		if (info.isEmpty()) {
			final CLBuilder builder = new CLBuilder(validation, model);

			final Object x = builder.build(result);

			if (x instanceof CLExpression) {
				expression = (CLExpression) x;
			}

		}

		for (final ErrInfo einfo : validation.getParseErrors()) {
			formulaSource.add(einfo.start + validation.getSourceOffset(), einfo.end + validation.getSourceOffset(), einfo.message);
		}

		for (final CLElement element : validation.getErrors().keySet()) {
			formulaSource.add(element.getLocation().getStart() + validation.getSourceOffset(),
					element.getLocation().getEnd() + validation.getSourceOffset(), validation.getErrors().get(element));
		}

		if (!validation.getErrors().isEmpty()) {
			expression = null;
		}

		return expression;
	}

	public CLStatement fragmentParseOnly(FormulaSource formulaSource) throws Exception {
		return fragmentParseOnly(formulaSource, -1, -1, true);
	}

	public CLStatement fragmentParse(FormulaSource formulaSource) throws Exception {
		return fragmentParseOnly(formulaSource, -1, -1, true, model.getRootContext());
	}

	public CLStatement fragmentParseOnly(FormulaSource formulaSource, int start, int end, boolean clear) throws Exception {
		return fragmentParseOnly(formulaSource, start, end, clear, null);
	}

	public CLStatement fragmentParseOnly(FormulaSource formulaSource, int start, int end, boolean clear, TypingContext typingContext)
			throws Exception {

		final ValidationContext validation = new ValidationContext(start + MAGIC_SUB.length());

		if (clear) {
			formulaSource.clear();
		}

		validation.reset();
		final String text = start == -1 ? formulaSource.getText() : formulaSource.getText().substring(start, end);

		final CLStatement statement = parseFragment(typingContext, validation, text);

		for (final ErrInfo einfo : validation.getParseErrors()) {
			formulaSource.add(einfo.start + validation.getSourceOffset(), einfo.end + validation.getSourceOffset(), einfo.message);
		}

		for (final CLElement element : validation.getErrors().keySet()) {
			if (element.getLocation() != null) {
				formulaSource.add(element.getLocation().getStart() + validation.getSourceOffset(),
						element.getLocation().getEnd() + validation.getSourceOffset(), validation.getErrors().get(element));
			} else {
				formulaSource.add(0 + validation.getSourceOffset(), text.length() + validation.getSourceOffset(),
						validation.getErrors().get(element));
			}
		}

		return statement;
	}

	@SuppressWarnings("unchecked")
	public CLStatement parseFragment(TypingContext typingContext, ValidationContext validation, String text) throws Exception {
		CLStatement statement = null;
		text = MAGIC_SUB + " " + text;
		final InputStream is = new ByteArrayInputStream(text.getBytes());
		final List<ErrInfo> info = new ArrayList<>(20);

		final syntree result = uk.ac.ncl.prime.sim.lang.parser.Parser.parse(is, info);

		validation.setParseErrors(info);

		if (info.isEmpty()) {
			final CLBuilder builder = new CLBuilder(validation, model);

			final Object x = builder.build(result);

			if (x instanceof CLStatement) {
				statement = (CLStatement) x;
			} else if (x instanceof CLCollection) {
				final CLCollection<CLStatement> stm_list = (CLCollection<CLStatement>) x;
				statement = new CLBlockStatement(stm_list, stm_list.getLocation());
			} else {
				return null;
			}

			if (typingContext != null && validation.getErrors().isEmpty()) {
				final ValidationContext old = typingContext.getValidation();
				typingContext.setValidation(validation);
				try {
					statement.type(typingContext);
					typingContext.bakeTypes();
				} catch (final Exception e) {
					// System.out.println(ctx.toString());
					// e.printStackTrace();
					return null;
				} finally {
					typingContext.setValidation(old);
				}
			}
		}

		if (!validation.getErrors().isEmpty()) {
			statement = null;
		}
		return statement;
	}
	
	
	public CLType typeParseOnly(FormulaSource formulaSource) throws Exception {
		return typeParseOnly(formulaSource, -1, -1, true);
	}

	public CLType typeParseOnly(FormulaSource formulaSource, int start, int end, boolean clear)
			throws Exception {

		final ValidationContext validation = new ValidationContext(start + MAGIC_SUB.length());

		if (clear) {
			formulaSource.clear();
		}

		validation.reset();
		final String text = start == -1 ? formulaSource.getText() : formulaSource.getText().substring(start, end);

		final CLType type = parseType(validation, text);

		for (final ErrInfo einfo : validation.getParseErrors()) {
			formulaSource.add(einfo.start + validation.getSourceOffset(), einfo.end + validation.getSourceOffset(), einfo.message);
		}

		for (final CLElement element : validation.getErrors().keySet()) {
			if (element.getLocation() != null) {
				formulaSource.add(element.getLocation().getStart() + validation.getSourceOffset(),
						element.getLocation().getEnd() + validation.getSourceOffset(), validation.getErrors().get(element));
			} else {
				formulaSource.add(0 + validation.getSourceOffset(), text.length() + validation.getSourceOffset(),
						validation.getErrors().get(element));
			}
		}

		return type;
	}	

	public CLType parseType(ValidationContext validation, String text) throws Exception {
		CLType type = null;
		text = MAGIC_TYP + " " + text;
		final InputStream is = new ByteArrayInputStream(text.getBytes());
		final List<ErrInfo> info = new ArrayList<>(20);

		final syntree result = uk.ac.ncl.prime.sim.lang.parser.Parser.parse(is, info);

		validation.setParseErrors(info);

		if (info.isEmpty()) {
			final CLBuilder builder = new CLBuilder(validation, model);

			final Object x = builder.build(result);

			if (x instanceof CLType) {
				type = (CLType) x;
			} else {
				return null;
			}
		}

		if (!validation.getErrors().isEmpty()) 
			type = null;
		
		return type;
	}	
}
