package uk.ac.ncl.safecap.textentry.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.NumberRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class ProjectPresentationReconciler extends PresentationReconciler {

	private final TextAttribute tagAttribute = new TextAttribute(new Color(Display.getCurrent(), new RGB(0, 0, 255)));
	private final TextAttribute headerAttribute = new TextAttribute(new Color(Display.getCurrent(), new RGB(128, 128, 128)));

	public ProjectPresentationReconciler() {
		// TODO this is logic for .project file to color tags in blue. Replace with your
		// language logic!

		final WordRule wordRule = new WordRule(new MyWordDetector());
		wordRule.addWord("true", new Token(tagAttribute));
		wordRule.addWord("false", new Token(tagAttribute));

		final NumberRule numberRule = new NumberRule(new Token(tagAttribute));

		final RuleBasedScanner scanner = new RuleBasedScanner();
		final IRule[] rules = new IRule[4];
		rules[0] = new SingleLineRule("//", "//", new Token(headerAttribute), '\\', true);
		rules[1] = wordRule;
		rules[2] = numberRule;
		rules[3] = new MultiLineRule("\"", "\"", new Token(tagAttribute), '\\', true);
		scanner.setRules(rules);
		final DefaultDamagerRepairer dr = new DefaultDamagerRepairer(scanner);
		// DefaultDamagerRepairer dr = new DefaultDamagerRepairer(new TestScanner());
		setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
	}
}

class MyWordDetector implements IWordDetector {

	@Override
	public boolean isWordStart(char c) {
		return c != ' ' && Character.isJavaIdentifierStart(c);
	}

	@Override
	public boolean isWordPart(char c) {
		return Character.isJavaIdentifierPart(c);
	}

}

class MyNumberDetector implements IWordDetector {

	@Override
	public boolean isWordStart(char c) {
		return c == '.' && Character.isDigit(c);
	}

	@Override
	public boolean isWordPart(char c) {
		return c == '.' && Character.isDigit(c);
	}

}

class TestScanner implements ITokenScanner {

	@Override
	public void setRange(IDocument document, int offset, int length) {
		System.out.println("Test scanner " + offset + ":" + length + " (" + document.getLength() + ")");
	}

	@Override
	public IToken nextToken() {
		return Token.EOF;
	}

	@Override
	public int getTokenOffset() {
		return 0;
	}

	@Override
	public int getTokenLength() {
		return 0;
	}

}
