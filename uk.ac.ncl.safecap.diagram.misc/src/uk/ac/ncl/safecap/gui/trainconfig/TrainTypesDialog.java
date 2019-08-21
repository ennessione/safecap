package uk.ac.ncl.safecap.gui.trainconfig;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class TrainTypesDialog extends Dialog {
	private class MyLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			final TrainType t = (TrainType) element;
			switch (columnIndex) {
			case 0:
				return t.getName();
			case 1:
				return t.getSpeed() + "";
			case 2:
				return t.getAcceleration() + "";
			case 3:
				return t.getDeceleration() + "";
			case 4:
				return t.getLength() + "";
			default:
				return "";
			}
		}
	}

	private static class ContentProvider implements IStructuredContentProvider {
		@Override
		public Object[] getElements(Object inputElement) {
			return new Object[0];
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	private Table table;
	private TableColumn column_1;
	private TableColumn column_2;
	private TableColumn column_3;
	private TableColumn column_4;

	private static List<String> columnDescriptions = Arrays.asList("Name", "Speed, max", "Acceleration, max", "Deceleration, max",
			"Length");
	private static List<String> columnNames = Arrays.asList("name", "speed", "acceleration", "deceleration", "length");
	private Composite container_1;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public TrainTypesDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.BORDER | SWT.RESIZE | SWT.TITLE | SWT.PRIMARY_MODAL);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite container = (Composite) super.createDialogArea(parent);
		container_1 = new Composite(container, SWT.FILL);
		container_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		final GridLayout gl_container_1 = new GridLayout(2, false);
		container_1.setLayout(gl_container_1);

		final TableViewer tableViewer = new TableViewer(container_1, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.setUseHashlookup(true);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

//        CellEditor[] cellEditors = new CellEditor[5];
//        for (int i=0; i<5; i++)
//        {
//            TableColumn column = new TableColumn(table, SWT.LEFT, i);
//            column.setWidth(80);
//            column.setText(columnDescriptions.get(i));
//
//            cellEditors[i] = new TextCellEditor(table);
//        }
//        tableViewer.setCellEditors(cellEditors);
//        tableViewer.setColumnProperties(columnNames.toArray(new String[columnNames.size()]));
//
//        tableViewer.setCellModifier(new ICellModifier()
//        {
//            @Override
//            public void modify(Object element, String property, Object value)
//            {
//                TableItem item = (TableItem) element;
//                TrainType t = (TrainType) item.getData();
//                int columnIndex = columnNames.indexOf(property);
//                String val = (String) value;
//                switch (columnIndex)
//                {
//                    case 0:
//                        t.setName(val);
//                        break;
//                    case 1:
//                        t.setSpeed(Integer.valueOf(val).intValue());
//                        break;
//                    case 2:
//                        t.setAcceleration(Float.valueOf(val).floatValue());
//                        break;
//                    case 3:
//                        t.setDeceleration(Float.valueOf(val).floatValue());
//                        break;
//                    case 4:
//                        t.setLength(Integer.valueOf(val).intValue());
//                        break;
//                }
//            }
//            @Override
//            public Object getValue(Object element, String property)
//            {
//                TrainType t = (TrainType) element;
//                int columnIndex = columnNames.indexOf(property);
//                Object result = null;
//                switch (columnIndex)
//                {
//                    case 0:
//                        result = t.getName();
//                        break;
//                    case 1:
//                        result = t.getSpeed();
//                        break;
//                    case 2:
//                        result = t.getAcceleration();
//                        break;
//                    case 3:
//                        result = t.getDeceleration();
//                        break;
//                    case 4:
//                        result = t.getLength();
//                        break;
//                    default:
//                        result = "";
//                }
//                return result;
//            }
//            @Override
//            public boolean canModify(Object element, String property)
//            {
//                return true;
//            }
//        });

		final Button btnNewButton = new Button(container_1, SWT.NONE);
		btnNewButton.setText("New Button");

		final Button btnNewButton_1 = new Button(container_1, SWT.NONE);
		btnNewButton_1.setText("New Button");

		tableViewer.setLabelProvider(new MyLabelProvider());

		tableViewer.add(new TrainType());

		return container_1;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(476, 336);
	}

}
