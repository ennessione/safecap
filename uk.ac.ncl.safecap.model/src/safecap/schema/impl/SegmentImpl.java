/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.schema.impl;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import safecap.Extensible;
import safecap.HighlightedInterval;
import safecap.Orientation;
import safecap.SafecapPackage;
import safecap.Style;
import safecap.TransientValues;
import safecap.Visual;
import safecap.VisualMarker;
import safecap.extension.ExtAttribute;
import safecap.impl.LabeledImpl;
import safecap.schema.HeightMap;
import safecap.schema.Node;
import safecap.schema.PointRole;
import safecap.schema.RouteSpeed;
import safecap.schema.SchemaPackage;
import safecap.schema.Segment;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Segment</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.schema.impl.SegmentImpl#isVisible <em>Visible</em>}</li>
 * <li>{@link safecap.schema.impl.SegmentImpl#getStyle <em>Style</em>}</li>
 * <li>{@link safecap.schema.impl.SegmentImpl#getIntervals
 * <em>Intervals</em>}</li>
 * <li>{@link safecap.schema.impl.SegmentImpl#getMarkers <em>Markers</em>}</li>
 * <li>{@link safecap.schema.impl.SegmentImpl#getRuntimeAttributes <em>Runtime
 * Attributes</em>}</li>
 * <li>{@link safecap.schema.impl.SegmentImpl#getAttributes
 * <em>Attributes</em>}</li>
 * <li>{@link safecap.schema.impl.SegmentImpl#getFrom <em>From</em>}</li>
 * <li>{@link safecap.schema.impl.SegmentImpl#getTo <em>To</em>}</li>
 * <li>{@link safecap.schema.impl.SegmentImpl#getHeightmap
 * <em>Heightmap</em>}</li>
 * <li>{@link safecap.schema.impl.SegmentImpl#getRole <em>Role</em>}</li>
 * <li>{@link safecap.schema.impl.SegmentImpl#getPointrole
 * <em>Pointrole</em>}</li>
 * <li>{@link safecap.schema.impl.SegmentImpl#getLength <em>Length</em>}</li>
 * <li>{@link safecap.schema.impl.SegmentImpl#getSpeed <em>Speed</em>}</li>
 * <li>{@link safecap.schema.impl.SegmentImpl#getGradient
 * <em>Gradient</em>}</li>
 * <li>{@link safecap.schema.impl.SegmentImpl#getSpeedlimit
 * <em>Speedlimit</em>}</li>
 * <li>{@link safecap.schema.impl.SegmentImpl#getOrientation
 * <em>Orientation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SegmentImpl extends LabeledImpl implements Segment {
	/**
	 * The default value of the '{@link #isVisible() <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isVisible()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VISIBLE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isVisible() <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isVisible()
	 * @generated
	 * @ordered
	 */
	protected boolean visible = VISIBLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStyle() <em>Style</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStyle()
	 * @generated
	 * @ordered
	 */
	protected Style style;

	/**
	 * The cached value of the '{@link #getIntervals() <em>Intervals</em>}'
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getIntervals()
	 * @generated
	 * @ordered
	 */
	protected EList<HighlightedInterval> intervals;

	/**
	 * The cached value of the '{@link #getMarkers() <em>Markers</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMarkers()
	 * @generated
	 * @ordered
	 */
	protected EList<VisualMarker> markers;

	/**
	 * The cached value of the '{@link #getRuntimeAttributes() <em>Runtime
	 * Attributes</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRuntimeAttributes()
	 * @generated
	 * @ordered
	 */
	protected Map<String, Object> runtimeAttributes;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<ExtAttribute> attributes;

	/**
	 * The cached value of the '{@link #getFrom() <em>From</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected Node from;

	/**
	 * The cached value of the '{@link #getTo() <em>To</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected Node to;

	/**
	 * The cached value of the '{@link #getHeightmap() <em>Heightmap</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getHeightmap()
	 * @generated
	 * @ordered
	 */
	protected HeightMap heightmap;

	/**
	 * The default value of the '{@link #getRole() <em>Role</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRole()
	 * @generated
	 * @ordered
	 */
	protected static final int ROLE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getRole() <em>Role</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRole()
	 * @generated
	 * @ordered
	 */
	protected int role = ROLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPointrole() <em>Pointrole</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPointrole()
	 * @generated
	 * @ordered
	 */
	protected static final PointRole POINTROLE_EDEFAULT = PointRole.NONE;

	/**
	 * The cached value of the '{@link #getPointrole() <em>Pointrole</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPointrole()
	 * @generated
	 * @ordered
	 */
	protected PointRole pointrole = POINTROLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected static final int LENGTH_EDEFAULT = 500;

	/**
	 * The cached value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected int length = LENGTH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSpeed() <em>Speed</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSpeed()
	 * @generated
	 * @ordered
	 */
	protected EList<RouteSpeed> speed;

	/**
	 * The default value of the '{@link #getGradient() <em>Gradient</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getGradient()
	 * @generated
	 * @ordered
	 */
	protected static final String GRADIENT_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getGradient() <em>Gradient</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getGradient()
	 * @generated
	 * @ordered
	 */
	protected String gradient = GRADIENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getSpeedlimit() <em>Speedlimit</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSpeedlimit()
	 * @generated
	 * @ordered
	 */
	protected static final String SPEEDLIMIT_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getSpeedlimit() <em>Speedlimit</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSpeedlimit()
	 * @generated
	 * @ordered
	 */
	protected String speedlimit = SPEEDLIMIT_EDEFAULT;

	/**
	 * The default value of the '{@link #getOrientation() <em>Orientation</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOrientation()
	 * @generated
	 * @ordered
	 */
	protected static final Orientation ORIENTATION_EDEFAULT = Orientation.ANY;

	/**
	 * The cached value of the '{@link #getOrientation() <em>Orientation</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOrientation()
	 * @generated
	 * @ordered
	 */
	protected Orientation orientation = ORIENTATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SegmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SchemaPackage.Literals.SEGMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isVisible() {
		return visible;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setVisible(boolean newVisible) {
		final boolean oldVisible = visible;
		visible = newVisible;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.SEGMENT__VISIBLE, oldVisible, visible));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Style getStyle() {
		if (style != null && ((EObject) style).eIsProxy()) {
			final InternalEObject oldStyle = (InternalEObject) style;
			style = (Style) eResolveProxy(oldStyle);
			if (style != oldStyle) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SchemaPackage.SEGMENT__STYLE, oldStyle, style));
				}
			}
		}
		return style;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Style basicGetStyle() {
		return style;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setStyle(Style newStyle) {
		final Style oldStyle = style;
		style = newStyle;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.SEGMENT__STYLE, oldStyle, style));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<HighlightedInterval> getIntervals() {
		if (intervals == null) {
			intervals = new EObjectResolvingEList<>(HighlightedInterval.class, this, SchemaPackage.SEGMENT__INTERVALS);
		}
		return intervals;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<VisualMarker> getMarkers() {
		if (markers == null) {
			markers = new EObjectContainmentEList<>(VisualMarker.class, this, SchemaPackage.SEGMENT__MARKERS);
		}
		return markers;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Map<String, Object> getRuntimeAttributes() {
		return runtimeAttributes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setRuntimeAttributes(Map<String, Object> newRuntimeAttributes) {
		final Map<String, Object> oldRuntimeAttributes = runtimeAttributes;
		runtimeAttributes = newRuntimeAttributes;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.SEGMENT__RUNTIME_ATTRIBUTES, oldRuntimeAttributes,
					runtimeAttributes));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<ExtAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList<>(ExtAttribute.class, this, SchemaPackage.SEGMENT__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Node getFrom() {
		if (from != null && ((EObject) from).eIsProxy()) {
			final InternalEObject oldFrom = (InternalEObject) from;
			from = (Node) eResolveProxy(oldFrom);
			if (from != oldFrom) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SchemaPackage.SEGMENT__FROM, oldFrom, from));
				}
			}
		}
		return from;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Node basicGetFrom() {
		return from;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setFrom(Node newFrom) {
		final Node oldFrom = from;
		from = newFrom;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.SEGMENT__FROM, oldFrom, from));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Node getTo() {
		if (to != null && ((EObject) to).eIsProxy()) {
			final InternalEObject oldTo = (InternalEObject) to;
			to = (Node) eResolveProxy(oldTo);
			if (to != oldTo) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SchemaPackage.SEGMENT__TO, oldTo, to));
				}
			}
		}
		return to;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Node basicGetTo() {
		return to;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setTo(Node newTo) {
		final Node oldTo = to;
		to = newTo;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.SEGMENT__TO, oldTo, to));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public HeightMap getHeightmap() {
		return heightmap;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetHeightmap(HeightMap newHeightmap, NotificationChain msgs) {
		final HeightMap oldHeightmap = heightmap;
		heightmap = newHeightmap;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SchemaPackage.SEGMENT__HEIGHTMAP,
					oldHeightmap, newHeightmap);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setHeightmap(HeightMap newHeightmap) {
		if (newHeightmap != heightmap) {
			NotificationChain msgs = null;
			if (heightmap != null) {
				msgs = ((InternalEObject) heightmap).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SchemaPackage.SEGMENT__HEIGHTMAP, null,
						msgs);
			}
			if (newHeightmap != null) {
				msgs = ((InternalEObject) newHeightmap).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SchemaPackage.SEGMENT__HEIGHTMAP, null,
						msgs);
			}
			msgs = basicSetHeightmap(newHeightmap, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.SEGMENT__HEIGHTMAP, newHeightmap, newHeightmap));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getRole() {
		return role;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setRole(int newRole) {
		final int oldRole = role;
		role = newRole;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.SEGMENT__ROLE, oldRole, role));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public PointRole getPointrole() {
		return pointrole;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setPointrole(PointRole newPointrole) {
		final PointRole oldPointrole = pointrole;
		pointrole = newPointrole == null ? POINTROLE_EDEFAULT : newPointrole;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.SEGMENT__POINTROLE, oldPointrole, pointrole));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getLength() {
		return length;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setLength(int newLength) {
		final int oldLength = length;
		length = newLength;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.SEGMENT__LENGTH, oldLength, length));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<RouteSpeed> getSpeed() {
		if (speed == null) {
			speed = new EObjectContainmentEList<>(RouteSpeed.class, this, SchemaPackage.SEGMENT__SPEED);
		}
		return speed;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getGradient() {
		return gradient;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setGradient(String newGradient) {
		final String oldGradient = gradient;
		gradient = newGradient;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.SEGMENT__GRADIENT, oldGradient, gradient));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getSpeedlimit() {
		return speedlimit;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setSpeedlimit(String newSpeedlimit) {
		final String oldSpeedlimit = speedlimit;
		speedlimit = newSpeedlimit;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.SEGMENT__SPEEDLIMIT, oldSpeedlimit, speedlimit));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setOrientation(Orientation newOrientation) {
		final Orientation oldOrientation = orientation;
		orientation = newOrientation == null ? ORIENTATION_EDEFAULT : newOrientation;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.SEGMENT__ORIENTATION, oldOrientation, orientation));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case SchemaPackage.SEGMENT__MARKERS:
			return ((InternalEList<?>) getMarkers()).basicRemove(otherEnd, msgs);
		case SchemaPackage.SEGMENT__ATTRIBUTES:
			return ((InternalEList<?>) getAttributes()).basicRemove(otherEnd, msgs);
		case SchemaPackage.SEGMENT__HEIGHTMAP:
			return basicSetHeightmap(null, msgs);
		case SchemaPackage.SEGMENT__SPEED:
			return ((InternalEList<?>) getSpeed()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case SchemaPackage.SEGMENT__VISIBLE:
			return isVisible();
		case SchemaPackage.SEGMENT__STYLE:
			if (resolve) {
				return getStyle();
			}
			return basicGetStyle();
		case SchemaPackage.SEGMENT__INTERVALS:
			return getIntervals();
		case SchemaPackage.SEGMENT__MARKERS:
			return getMarkers();
		case SchemaPackage.SEGMENT__RUNTIME_ATTRIBUTES:
			return getRuntimeAttributes();
		case SchemaPackage.SEGMENT__ATTRIBUTES:
			return getAttributes();
		case SchemaPackage.SEGMENT__FROM:
			if (resolve) {
				return getFrom();
			}
			return basicGetFrom();
		case SchemaPackage.SEGMENT__TO:
			if (resolve) {
				return getTo();
			}
			return basicGetTo();
		case SchemaPackage.SEGMENT__HEIGHTMAP:
			return getHeightmap();
		case SchemaPackage.SEGMENT__ROLE:
			return getRole();
		case SchemaPackage.SEGMENT__POINTROLE:
			return getPointrole();
		case SchemaPackage.SEGMENT__LENGTH:
			return getLength();
		case SchemaPackage.SEGMENT__SPEED:
			return getSpeed();
		case SchemaPackage.SEGMENT__GRADIENT:
			return getGradient();
		case SchemaPackage.SEGMENT__SPEEDLIMIT:
			return getSpeedlimit();
		case SchemaPackage.SEGMENT__ORIENTATION:
			return getOrientation();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case SchemaPackage.SEGMENT__VISIBLE:
			setVisible((Boolean) newValue);
			return;
		case SchemaPackage.SEGMENT__STYLE:
			setStyle((Style) newValue);
			return;
		case SchemaPackage.SEGMENT__INTERVALS:
			getIntervals().clear();
			getIntervals().addAll((Collection<? extends HighlightedInterval>) newValue);
			return;
		case SchemaPackage.SEGMENT__MARKERS:
			getMarkers().clear();
			getMarkers().addAll((Collection<? extends VisualMarker>) newValue);
			return;
		case SchemaPackage.SEGMENT__RUNTIME_ATTRIBUTES:
			setRuntimeAttributes((Map<String, Object>) newValue);
			return;
		case SchemaPackage.SEGMENT__ATTRIBUTES:
			getAttributes().clear();
			getAttributes().addAll((Collection<? extends ExtAttribute>) newValue);
			return;
		case SchemaPackage.SEGMENT__FROM:
			setFrom((Node) newValue);
			return;
		case SchemaPackage.SEGMENT__TO:
			setTo((Node) newValue);
			return;
		case SchemaPackage.SEGMENT__HEIGHTMAP:
			setHeightmap((HeightMap) newValue);
			return;
		case SchemaPackage.SEGMENT__ROLE:
			setRole((Integer) newValue);
			return;
		case SchemaPackage.SEGMENT__POINTROLE:
			setPointrole((PointRole) newValue);
			return;
		case SchemaPackage.SEGMENT__LENGTH:
			setLength((Integer) newValue);
			return;
		case SchemaPackage.SEGMENT__SPEED:
			getSpeed().clear();
			getSpeed().addAll((Collection<? extends RouteSpeed>) newValue);
			return;
		case SchemaPackage.SEGMENT__GRADIENT:
			setGradient((String) newValue);
			return;
		case SchemaPackage.SEGMENT__SPEEDLIMIT:
			setSpeedlimit((String) newValue);
			return;
		case SchemaPackage.SEGMENT__ORIENTATION:
			setOrientation((Orientation) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case SchemaPackage.SEGMENT__VISIBLE:
			setVisible(VISIBLE_EDEFAULT);
			return;
		case SchemaPackage.SEGMENT__STYLE:
			setStyle((Style) null);
			return;
		case SchemaPackage.SEGMENT__INTERVALS:
			getIntervals().clear();
			return;
		case SchemaPackage.SEGMENT__MARKERS:
			getMarkers().clear();
			return;
		case SchemaPackage.SEGMENT__RUNTIME_ATTRIBUTES:
			setRuntimeAttributes((Map<String, Object>) null);
			return;
		case SchemaPackage.SEGMENT__ATTRIBUTES:
			getAttributes().clear();
			return;
		case SchemaPackage.SEGMENT__FROM:
			setFrom((Node) null);
			return;
		case SchemaPackage.SEGMENT__TO:
			setTo((Node) null);
			return;
		case SchemaPackage.SEGMENT__HEIGHTMAP:
			setHeightmap((HeightMap) null);
			return;
		case SchemaPackage.SEGMENT__ROLE:
			setRole(ROLE_EDEFAULT);
			return;
		case SchemaPackage.SEGMENT__POINTROLE:
			setPointrole(POINTROLE_EDEFAULT);
			return;
		case SchemaPackage.SEGMENT__LENGTH:
			setLength(LENGTH_EDEFAULT);
			return;
		case SchemaPackage.SEGMENT__SPEED:
			getSpeed().clear();
			return;
		case SchemaPackage.SEGMENT__GRADIENT:
			setGradient(GRADIENT_EDEFAULT);
			return;
		case SchemaPackage.SEGMENT__SPEEDLIMIT:
			setSpeedlimit(SPEEDLIMIT_EDEFAULT);
			return;
		case SchemaPackage.SEGMENT__ORIENTATION:
			setOrientation(ORIENTATION_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case SchemaPackage.SEGMENT__VISIBLE:
			return visible != VISIBLE_EDEFAULT;
		case SchemaPackage.SEGMENT__STYLE:
			return style != null;
		case SchemaPackage.SEGMENT__INTERVALS:
			return intervals != null && !intervals.isEmpty();
		case SchemaPackage.SEGMENT__MARKERS:
			return markers != null && !markers.isEmpty();
		case SchemaPackage.SEGMENT__RUNTIME_ATTRIBUTES:
			return runtimeAttributes != null;
		case SchemaPackage.SEGMENT__ATTRIBUTES:
			return attributes != null && !attributes.isEmpty();
		case SchemaPackage.SEGMENT__FROM:
			return from != null;
		case SchemaPackage.SEGMENT__TO:
			return to != null;
		case SchemaPackage.SEGMENT__HEIGHTMAP:
			return heightmap != null;
		case SchemaPackage.SEGMENT__ROLE:
			return role != ROLE_EDEFAULT;
		case SchemaPackage.SEGMENT__POINTROLE:
			return pointrole != POINTROLE_EDEFAULT;
		case SchemaPackage.SEGMENT__LENGTH:
			return length != LENGTH_EDEFAULT;
		case SchemaPackage.SEGMENT__SPEED:
			return speed != null && !speed.isEmpty();
		case SchemaPackage.SEGMENT__GRADIENT:
			return GRADIENT_EDEFAULT == null ? gradient != null : !GRADIENT_EDEFAULT.equals(gradient);
		case SchemaPackage.SEGMENT__SPEEDLIMIT:
			return SPEEDLIMIT_EDEFAULT == null ? speedlimit != null : !SPEEDLIMIT_EDEFAULT.equals(speedlimit);
		case SchemaPackage.SEGMENT__ORIENTATION:
			return orientation != ORIENTATION_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == Visual.class) {
			switch (derivedFeatureID) {
			case SchemaPackage.SEGMENT__VISIBLE:
				return SafecapPackage.VISUAL__VISIBLE;
			case SchemaPackage.SEGMENT__STYLE:
				return SafecapPackage.VISUAL__STYLE;
			case SchemaPackage.SEGMENT__INTERVALS:
				return SafecapPackage.VISUAL__INTERVALS;
			case SchemaPackage.SEGMENT__MARKERS:
				return SafecapPackage.VISUAL__MARKERS;
			default:
				return -1;
			}
		}
		if (baseClass == TransientValues.class) {
			switch (derivedFeatureID) {
			case SchemaPackage.SEGMENT__RUNTIME_ATTRIBUTES:
				return SafecapPackage.TRANSIENT_VALUES__RUNTIME_ATTRIBUTES;
			default:
				return -1;
			}
		}
		if (baseClass == Extensible.class) {
			switch (derivedFeatureID) {
			case SchemaPackage.SEGMENT__ATTRIBUTES:
				return SafecapPackage.EXTENSIBLE__ATTRIBUTES;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == Visual.class) {
			switch (baseFeatureID) {
			case SafecapPackage.VISUAL__VISIBLE:
				return SchemaPackage.SEGMENT__VISIBLE;
			case SafecapPackage.VISUAL__STYLE:
				return SchemaPackage.SEGMENT__STYLE;
			case SafecapPackage.VISUAL__INTERVALS:
				return SchemaPackage.SEGMENT__INTERVALS;
			case SafecapPackage.VISUAL__MARKERS:
				return SchemaPackage.SEGMENT__MARKERS;
			default:
				return -1;
			}
		}
		if (baseClass == TransientValues.class) {
			switch (baseFeatureID) {
			case SafecapPackage.TRANSIENT_VALUES__RUNTIME_ATTRIBUTES:
				return SchemaPackage.SEGMENT__RUNTIME_ATTRIBUTES;
			default:
				return -1;
			}
		}
		if (baseClass == Extensible.class) {
			switch (baseFeatureID) {
			case SafecapPackage.EXTENSIBLE__ATTRIBUTES:
				return SchemaPackage.SEGMENT__ATTRIBUTES;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public String toString() {
		return getLabel();
	}

} // SegmentImpl
