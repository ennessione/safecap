package safecap.diagram.providers.assistants;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import safecap.diagram.providers.SafecapElementTypes;
import safecap.diagram.providers.SafecapModelingAssistantProvider;

/**
 * @generated
 */
public class SafecapModelingAssistantProviderOfProjectEditPart extends SafecapModelingAssistantProvider {

	/**
	 * @generated
	 */
	@Override
	public List<IElementType> getTypesForPopupBar(IAdaptable host) {
		final List<IElementType> types = new ArrayList<>(12);
		types.add(SafecapElementTypes.SubSchemaNode_2015);
		types.add(SafecapElementTypes.Node_2003);
		types.add(SafecapElementTypes.LeftSignal_2004);
		types.add(SafecapElementTypes.RightSignal_2005);
		types.add(SafecapElementTypes.LeftSpeedLimit_2006);
		types.add(SafecapElementTypes.RightSpeedLimit_2007);
		types.add(SafecapElementTypes.RedLeftSignal_2008);
		types.add(SafecapElementTypes.RedRightSignal_2009);
		types.add(SafecapElementTypes.LeftStopAndGo_2010);
		types.add(SafecapElementTypes.RightStopAndGo_2011);
		types.add(SafecapElementTypes.Station_2012);
		types.add(SafecapElementTypes.Bridge_2013);
		return types;
	}

}
