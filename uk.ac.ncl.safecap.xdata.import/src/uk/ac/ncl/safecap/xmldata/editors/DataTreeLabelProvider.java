package uk.ac.ncl.safecap.xmldata.editors;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;

import uk.ac.ncl.safecap.xmldata.Block;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataNamespace;
import uk.ac.ncl.safecap.xmldata.Pair;
import uk.ac.ncl.safecap.xmldata.editors.DataTreeContentProvider.Description;

class DataTreeLabelProvider extends StyledCellLabelProvider {
	@Override
	public void update(ViewerCell cell) {
		final Object element = cell.getElement();
		final StyledString text = new StyledString();

		if (element instanceof DataContext) {
			// DataContext dataContext = (DataContext) element;
			text.append("[DataContext]", StyledString.DECORATIONS_STYLER);
		} else if (element instanceof DataNamespace) {
			final DataNamespace nmsp = (DataNamespace) element;
			text.append(nmsp.getId(), StyledString.COUNTER_STYLER);
		} else if (element instanceof Block) {
			final Block block = (Block) element;

			final String id = block.getId().toString();
			if (id != null) {
				text.append(id);
			} else {
				text.append("block." + block.hashCode());
			}
		} else if (element instanceof Pair) {
			final Pair pair = (Pair) element;
			text.append(pair.toString());
		} else if (element instanceof Description) {
			final Description d = (Description) element;
			text.append(d.source.getDescription() != null ? d.source.getDescription() : "...", StyleColours.commentStyler);
		} else {
			text.append(element.toString());
		}

		cell.setText(text.toString());
		cell.setStyleRanges(text.getStyleRanges());
		super.update(cell);

	}
}
