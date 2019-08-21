package safecap.diagram.navigator;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

import safecap.diagram.part.SafecapDiagramEditorPlugin;

/**
 * @generated
 */
public class SafecapDomainNavigatorLabelProvider implements ICommonLabelProvider {

	/**
	 * @generated
	 */
	private final AdapterFactoryLabelProvider myAdapterFactoryLabelProvider = new AdapterFactoryLabelProvider(
			SafecapDiagramEditorPlugin.getInstance().getItemProvidersAdapterFactory());

	/**
	 * @generated
	 */
	@Override
	public void init(ICommonContentExtensionSite aConfig) {
	}

	/**
	 * @generated
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof SafecapDomainNavigatorItem) {
			return myAdapterFactoryLabelProvider.getImage(((SafecapDomainNavigatorItem) element).getEObject());
		}
		return null;
	}

	/**
	 * @generated
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof SafecapDomainNavigatorItem) {
			return myAdapterFactoryLabelProvider.getText(((SafecapDomainNavigatorItem) element).getEObject());
		}
		return null;
	}

	/**
	 * @generated
	 */
	@Override
	public void addListener(ILabelProviderListener listener) {
		myAdapterFactoryLabelProvider.addListener(listener);
	}

	/**
	 * @generated
	 */
	@Override
	public void dispose() {
		myAdapterFactoryLabelProvider.dispose();
	}

	/**
	 * @generated
	 */
	@Override
	public boolean isLabelProperty(Object element, String property) {
		return myAdapterFactoryLabelProvider.isLabelProperty(element, property);
	}

	/**
	 * @generated
	 */
	@Override
	public void removeListener(ILabelProviderListener listener) {
		myAdapterFactoryLabelProvider.removeListener(listener);
	}

	/**
	 * @generated
	 */
	@Override
	public void restoreState(IMemento aMemento) {
	}

	/**
	 * @generated
	 */
	@Override
	public void saveState(IMemento aMemento) {
	}

	/**
	 * @generated
	 */
	@Override
	public String getDescription(Object anElement) {
		return null;
	}

}
