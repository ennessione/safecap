package uk.ac.ncl.safecap.common.report;

import java.util.Stack;

public abstract class BaseRenderer implements ISRDocumentRenderer {
	private final Stack<SectionId> sectionStack;
	private SectionId current;
	protected StringBuilder sb;
	private int sectionDepth;

	public BaseRenderer() {
		sectionStack = new Stack<>();
		current = new SectionId();
		sb = new StringBuilder();
	}
	
	@Override
	public String renderPart(SRPart part) {
		sb.setLength(0);
		_render(part);
		return sb.toString();
	}

	@Override
	public String render(SRDocument document) {
		sb.setLength(0);
		documentStart(document.get(SRPart.TITLE, "untitled"));

		for (final SRPart child : document.getChildren()) {
			_render(child);
		}

		documentEnd();

		return sb.toString();
	}

	protected void print(String text) {
		sb.append(text);
		sb.append("\n");
	}

	private void _render(SRPart child) {
		if (child instanceof SRContainer) {
			render((SRContainer) child);
		} else if (child instanceof SRContent) {
			render((SRContent) child);
		} else if (child instanceof SRFormatted) {
			render((SRFormatted) child);
		} else {
			throw new IllegalArgumentException();
		}
	}

	private void render(SRFormatted child) {
		if (child instanceof SFElement) {
			renderFormatted((SFElement) child);
		} else if (child instanceof SFBold) {
			renderFormatted((SFBold) child);
		} else if (child instanceof SFEmph) {
			renderFormatted((SFEmph) child);
		} else if (child instanceof SFType) {
			renderFormatted((SFType) child);
		} else if (child instanceof SFPath) {
			renderFormatted((SFPath) child);
		} else if (child instanceof SFLink) {
			renderFormatted((SFLink) child);
		} else if (child instanceof SFPlain) {
			renderFormatted((SFPlain) child);
		} else if (child instanceof SFSource) {
			renderFormatted((SFSource) child);
		} else if (child instanceof SMError) {
			renderMarker((SMError) child);
		} else if (child instanceof SMWarning) {
			renderMarker((SMWarning) child);
		} else if (child instanceof SMNotice) {
			renderMarker((SMNotice) child);
		} else if (child instanceof SFExternalImage) {
			renderFormatted((SFExternalImage) child);
		} else {
			throw new IllegalArgumentException();
		}
	}

	private void render(SRContent child) {
		if (child instanceof SRBlock) {
			renderContent((SRBlock) child);
		} else if (child instanceof SRCode) {
			renderContent((SRCode) child);
		} else if (child instanceof SRComment) {
			renderContent((SRComment) child);
		} else if (child instanceof SRFigure) {
			renderContent((SRFigure) child);
		} else if (child instanceof SRFormula) {
			renderContent((SRFormula) child);
		} else if (child instanceof SRRelated) {
			renderContent((SRRelated) child);
		} else {
			throw new IllegalArgumentException();
		}
	}

	private void render(SRContainer child) {

		if (child instanceof SRSection) {
			renderSection((SRSection) child);
		} else if (child instanceof SRFoldable) {
			renderFoldable((SRFoldable) child);
		} else if (child instanceof SRGrid) {
			renderGrid((SRGrid) child);
		} else if (child instanceof SRList) {
			renderList((SRList) child);
		} else {
			throw new IllegalArgumentException();
		}
	}

	protected abstract void renderFormatted(SFExternalImage child);

	protected abstract void renderFormatted(SFBold child);

	protected abstract void renderFormatted(SFEmph child);

	protected abstract void renderFormatted(SFElement child);

	protected abstract void renderFormatted(SFType child);

	protected abstract void renderFormatted(SFPath child);

	protected abstract void renderFormatted(SFLink child);
	
	protected abstract void renderFormatted(SFPlain child);

	protected abstract void renderFormatted(SFSource child);

	protected abstract void renderMarker(SMError error);

	protected abstract void renderMarker(SMWarning error);

	protected abstract void renderMarker(SMNotice error);

	protected void renderContent(SRBlock child) {
		renderContentStart(child);

		for (final SRFormatted sub : child.getChildren()) {
			render(sub);
		}

		renderContentEnd(child);
	}

	protected void renderContent(SRComment child) {
		renderContentStart(child);

		for (final SRFormatted sub : child.getChildren()) {
			render(sub);
		}

		renderContentEnd(child);
	}

	protected void renderContent(SRCode child) {
		renderContentStart(child);

		for (final SRFormatted sub : child.getChildren()) {
			render(sub);
		}

		renderContentEnd(child);
	}

	protected void renderContent(SRFigure child) {
		renderContentStart(child);

		for (final SRFormatted sub : child.getChildren()) {
			render(sub);
		}

		renderContentEnd(child);
	}

	protected void renderContent(SRFormula child) {
		renderContentStart(child);

		for (final SRFormatted sub : child.getChildren()) {
			render(sub);
		}

		renderContentEnd(child);
	}

	protected void renderContent(SRRelated child) {
		renderContentStart(child);

		for (final SRFormatted sub : child.getChildren()) {
			render(sub);
		}

		renderContentEnd(child);
	}

	protected abstract void renderContentStart(SRBlock child);

	protected abstract void renderContentStart(SRComment child);

	protected abstract void renderContentStart(SRCode child);

	protected abstract void renderContentStart(SRFigure child);

	protected abstract void renderContentStart(SRFormula child);

	protected abstract void renderContentStart(SRRelated child);

	protected abstract void renderContentEnd(SRBlock child);

	protected abstract void renderContentEnd(SRComment child);

	protected abstract void renderContentEnd(SRCode child);

	protected abstract void renderContentEnd(SRFigure child);

	protected abstract void renderContentEnd(SRFormula child);

	protected abstract void renderContentEnd(SRRelated child);

	protected abstract void documentStart(String title);

	protected abstract void documentEnd();

	protected abstract void embeddedStyles();

	protected void renderList(SRList list) {
		renderListStart(list, list.get(SRPart.TITLE, ""));

		for (final SRPart item : list.getChildren()) {
			renderListItemStart(list, item);
			_render(item);
			renderListItemEnd(list, item);
		}

		renderListEnd(list);
	}

	protected abstract void renderListStart(SRList list, String title);

	protected abstract void renderListItemStart(SRList list, SRPart item);

	protected abstract void renderListItemEnd(SRList list, SRPart item);

	protected abstract void renderListEnd(SRList list);

	protected void renderSection(SRSection section) {
		sectionDepth++;
		if (section.get(SRSection.HAS_INDEX, false)) {
			current.next();
			renderSectionStart(section, getSectionIndex());
		} else {
			renderSectionStart(section, null);
		}

		sectionStack.push(current);
		current = new SectionId();

		for (final SRPart part : section.getChildren()) {
			_render(part);
		}

		current = sectionStack.pop();
		renderSectionEnd(section);
		sectionDepth--;
	}

	protected void renderFoldable(SRFoldable foldable) {
		renderFoldableStart(foldable);

		for (final SRPart part : foldable.getChildren()) {
			_render(part);
		}

		renderFoldableEnd(foldable);
	}

	protected void renderGrid(SRGrid grid) {
		renderGridStart(grid);

		for (final SRPart part : grid.getChildren()) {
			_render(part);
		}

		renderGridEnd(grid);
	}

	protected abstract void renderGridStart(SRGrid grid);

	protected abstract void renderGridEnd(SRGrid grid);

	protected abstract void renderSectionStart(SRSection section, String index);

	protected abstract void renderSectionEnd(SRSection section);

	protected abstract void renderFoldableStart(SRFoldable section);

	protected abstract void renderFoldableEnd(SRFoldable section);

	private String getSectionIndex() {
		final StringBuilder sb = new StringBuilder();

		for (int i = 0; i < sectionStack.size(); i++) {
			sb.append(sectionStack.get(i).getIndex());
			sb.append('.');
		}

		return sb.toString() + current.getIndex();
	}

	public int getSectionDepth() {
		return sectionDepth;
	}

	public static class SectionId {
		private int index;

		public SectionId() {
			index = 0;
		}

		public int getIndex() {
			return index;
		}

		public void next() {
			index++;
		}
	}
}
