/**
 */
package safecap.extension.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import safecap.extension.CustomColour;
import safecap.extension.CustomLabel;
import safecap.extension.CustomShape;
import safecap.extension.ExtAttributeB;
import safecap.extension.ExtAttributeDbl;
import safecap.extension.ExtAttributeInt;
import safecap.extension.ExtAttributeStr;
import safecap.extension.ExtEquipment;
import safecap.extension.ExtVisual;
import safecap.extension.ExtensionFactory;
import safecap.extension.ExtensionPackage;
import safecap.extension.Point;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class ExtensionFactoryImpl extends EFactoryImpl implements ExtensionFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static ExtensionFactory init() {
		try {
			final ExtensionFactory theExtensionFactory = (ExtensionFactory) EPackage.Registry.INSTANCE
					.getEFactory(ExtensionPackage.eNS_URI);
			if (theExtensionFactory != null) {
				return theExtensionFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ExtensionFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public ExtensionFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case ExtensionPackage.EXT_ATTRIBUTE_INT:
			return createExtAttributeInt();
		case ExtensionPackage.EXT_ATTRIBUTE_DBL:
			return createExtAttributeDbl();
		case ExtensionPackage.EXT_ATTRIBUTE_STR:
			return createExtAttributeStr();
		case ExtensionPackage.EXT_ATTRIBUTE_B:
			return createExtAttributeB();
		case ExtensionPackage.CUSTOM_LABEL:
			return createCustomLabel();
		case ExtensionPackage.POINT:
			return createPoint();
		case ExtensionPackage.CUSTOM_COLOUR:
			return createCustomColour();
		case ExtensionPackage.CUSTOM_SHAPE:
			return createCustomShape();
		case ExtensionPackage.EXT_VISUAL:
			return createExtVisual();
		case ExtensionPackage.EXT_EQUIPMENT:
			return createExtEquipment();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ExtAttributeInt createExtAttributeInt() {
		final ExtAttributeIntImpl extAttributeInt = new ExtAttributeIntImpl();
		return extAttributeInt;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ExtAttributeDbl createExtAttributeDbl() {
		final ExtAttributeDblImpl extAttributeDbl = new ExtAttributeDblImpl();
		return extAttributeDbl;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ExtAttributeStr createExtAttributeStr() {
		final ExtAttributeStrImpl extAttributeStr = new ExtAttributeStrImpl();
		return extAttributeStr;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ExtAttributeB createExtAttributeB() {
		final ExtAttributeBImpl extAttributeB = new ExtAttributeBImpl();
		return extAttributeB;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CustomLabel createCustomLabel() {
		final CustomLabelImpl customLabel = new CustomLabelImpl();
		return customLabel;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Point createPoint() {
		final PointImpl point = new PointImpl();
		return point;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CustomColour createCustomColour() {
		final CustomColourImpl customColour = new CustomColourImpl();
		return customColour;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CustomShape createCustomShape() {
		final CustomShapeImpl customShape = new CustomShapeImpl();
		return customShape;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ExtVisual createExtVisual() {
		final ExtVisualImpl extVisual = new ExtVisualImpl();
		return extVisual;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ExtEquipment createExtEquipment() {
		final ExtEquipmentImpl extEquipment = new ExtEquipmentImpl();
		return extEquipment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ExtensionPackage getExtensionPackage() {
		return (ExtensionPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ExtensionPackage getPackage() {
		return ExtensionPackage.eINSTANCE;
	}

} // ExtensionFactoryImpl
