/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package safecap.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import safecap.Extensible;
import safecap.Labeled;
import safecap.Project;
import safecap.Provenance;
import safecap.SafecapPackage;
import safecap.TransientValues;
import safecap.commentary.Commentary;
import safecap.commentary.CommentaryPackage;
import safecap.commentary.Comments;
import safecap.config.RouteBuilderConfiguration;
import safecap.config.SchemaConfiguration;
import safecap.derived.Derived;
import safecap.derived.DerivedPackage;
import safecap.derived.MergedPoint;
import safecap.extension.ExtAttribute;
import safecap.model.Ambit;
import safecap.model.Line;
import safecap.model.Model;
import safecap.model.ModelPackage;
import safecap.model.Route;
import safecap.schema.Node;
import safecap.schema.Schema;
import safecap.schema.SchemaPackage;
import safecap.schema.Segment;
import safecap.trackside.Equipment;
import safecap.trackside.NodeWire;
import safecap.trackside.SubEquipment;
import safecap.trackside.SubWire;
import safecap.trackside.Trackside;
import safecap.trackside.TracksidePackage;
import safecap.trackside.Wire;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Project</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link safecap.impl.ProjectImpl#getNodes <em>Nodes</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getSegments <em>Segments</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getLines <em>Lines</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getRoutes <em>Routes</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getAmbits <em>Ambits</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getEquipment <em>Equipment</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getWires <em>Wires</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getNodewires <em>Nodewires</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getSubwires <em>Subwires</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getSubequipment
 * <em>Subequipment</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getComments <em>Comments</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getRuntimeAttributes <em>Runtime
 * Attributes</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getAttributes <em>Attributes</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getMergedpoints
 * <em>Mergedpoints</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getOrigin <em>Origin</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getAuthor <em>Author</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getVersion <em>Version</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getDate <em>Date</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getConfiguration
 * <em>Configuration</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getRoutebuilder
 * <em>Routebuilder</em>}</li>
 * <li>{@link safecap.impl.ProjectImpl#getDarkmatter <em>Darkmatter</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProjectImpl extends LabeledImpl implements Project {
	/**
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<Node> nodes;

	/**
	 * The cached value of the '{@link #getSegments() <em>Segments</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSegments()
	 * @generated
	 * @ordered
	 */
	protected EList<Segment> segments;

	/**
	 * The cached value of the '{@link #getLines() <em>Lines</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLines()
	 * @generated
	 * @ordered
	 */
	protected EList<Line> lines;

	/**
	 * The cached value of the '{@link #getRoutes() <em>Routes</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRoutes()
	 * @generated
	 * @ordered
	 */
	protected EList<Route> routes;

	/**
	 * The cached value of the '{@link #getAmbits() <em>Ambits</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAmbits()
	 * @generated
	 * @ordered
	 */
	protected EList<Ambit> ambits;

	/**
	 * The cached value of the '{@link #getEquipment() <em>Equipment</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEquipment()
	 * @generated
	 * @ordered
	 */
	protected EList<Equipment> equipment;

	/**
	 * The cached value of the '{@link #getWires() <em>Wires</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getWires()
	 * @generated
	 * @ordered
	 */
	protected EList<Wire> wires;

	/**
	 * The cached value of the '{@link #getNodewires() <em>Nodewires</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNodewires()
	 * @generated
	 * @ordered
	 */
	protected EList<NodeWire> nodewires;

	/**
	 * The cached value of the '{@link #getSubwires() <em>Subwires</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSubwires()
	 * @generated
	 * @ordered
	 */
	protected EList<SubWire> subwires;

	/**
	 * The cached value of the '{@link #getSubequipment() <em>Subequipment</em>} '
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSubequipment()
	 * @generated
	 * @ordered
	 */
	protected EList<SubEquipment> subequipment;

	/**
	 * The cached value of the '{@link #getComments() <em>Comments</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getComments()
	 * @generated
	 * @ordered
	 */
	protected EList<Commentary> comments;

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
	 * The cached value of the '{@link #getMergedpoints() <em>Mergedpoints</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMergedpoints()
	 * @generated
	 * @ordered
	 */
	protected EList<MergedPoint> mergedpoints;

	/**
	 * The default value of the '{@link #getOrigin() <em>Origin</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOrigin()
	 * @generated
	 * @ordered
	 */
	protected static final String ORIGIN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOrigin() <em>Origin</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOrigin()
	 * @generated
	 * @ordered
	 */
	protected String origin = ORIGIN_EDEFAULT;

	/**
	 * The default value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTHOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected String author = AUTHOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getDate() <em>Date</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDate() <em>Date</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected Date date = DATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConfiguration() <em>Configuration</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getConfiguration()
	 * @generated
	 * @ordered
	 */
	protected SchemaConfiguration configuration;

	/**
	 * The cached value of the '{@link #getRoutebuilder() <em>Routebuilder</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRoutebuilder()
	 * @generated
	 * @ordered
	 */
	protected RouteBuilderConfiguration routebuilder;

	/**
	 * The cached value of the '{@link #getDarkmatter() <em>Darkmatter</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDarkmatter()
	 * @generated
	 * @ordered
	 */
	protected EList<Labeled> darkmatter;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ProjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SafecapPackage.Literals.PROJECT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Node> getNodes() {
		if (nodes == null) {
			nodes = new EObjectContainmentEList<>(Node.class, this, SafecapPackage.PROJECT__NODES);
		}
		return nodes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Segment> getSegments() {
		if (segments == null) {
			segments = new EObjectContainmentEList<>(Segment.class, this, SafecapPackage.PROJECT__SEGMENTS);
		}
		return segments;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Line> getLines() {
		if (lines == null) {
			lines = new EObjectContainmentEList<>(Line.class, this, SafecapPackage.PROJECT__LINES);
		}
		return lines;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Route> getRoutes() {
		if (routes == null) {
			routes = new EObjectContainmentEList<>(Route.class, this, SafecapPackage.PROJECT__ROUTES);
		}
		return routes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Ambit> getAmbits() {
		if (ambits == null) {
			ambits = new EObjectContainmentEList<>(Ambit.class, this, SafecapPackage.PROJECT__AMBITS);
		}
		return ambits;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Equipment> getEquipment() {
		if (equipment == null) {
			equipment = new EObjectContainmentEList<>(Equipment.class, this, SafecapPackage.PROJECT__EQUIPMENT);
		}
		return equipment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Wire> getWires() {
		if (wires == null) {
			wires = new EObjectContainmentEList<>(Wire.class, this, SafecapPackage.PROJECT__WIRES);
		}
		return wires;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<NodeWire> getNodewires() {
		if (nodewires == null) {
			nodewires = new EObjectContainmentEList<>(NodeWire.class, this, SafecapPackage.PROJECT__NODEWIRES);
		}
		return nodewires;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<SubWire> getSubwires() {
		if (subwires == null) {
			subwires = new EObjectContainmentEList<>(SubWire.class, this, SafecapPackage.PROJECT__SUBWIRES);
		}
		return subwires;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<SubEquipment> getSubequipment() {
		if (subequipment == null) {
			subequipment = new EObjectContainmentEList<>(SubEquipment.class, this, SafecapPackage.PROJECT__SUBEQUIPMENT);
		}
		return subequipment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Commentary> getComments() {
		if (comments == null) {
			comments = new EObjectContainmentEList<>(Commentary.class, this, SafecapPackage.PROJECT__COMMENTS);
		}
		return comments;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<ExtAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList<>(ExtAttribute.class, this, SafecapPackage.PROJECT__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<MergedPoint> getMergedpoints() {
		if (mergedpoints == null) {
			mergedpoints = new EObjectContainmentEList<>(MergedPoint.class, this, SafecapPackage.PROJECT__MERGEDPOINTS);
		}
		return mergedpoints;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getOrigin() {
		return origin;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setOrigin(String newOrigin) {
		final String oldOrigin = origin;
		origin = newOrigin;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.PROJECT__ORIGIN, oldOrigin, origin));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getAuthor() {
		return author;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setAuthor(String newAuthor) {
		final String oldAuthor = author;
		author = newAuthor;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.PROJECT__AUTHOR, oldAuthor, author));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setVersion(String newVersion) {
		final String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.PROJECT__VERSION, oldVersion, version));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Date getDate() {
		return date;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDate(Date newDate) {
		final Date oldDate = date;
		date = newDate;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.PROJECT__DATE, oldDate, date));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SchemaConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetConfiguration(SchemaConfiguration newConfiguration, NotificationChain msgs) {
		final SchemaConfiguration oldConfiguration = configuration;
		configuration = newConfiguration;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafecapPackage.PROJECT__CONFIGURATION,
					oldConfiguration, newConfiguration);
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
	public void setConfiguration(SchemaConfiguration newConfiguration) {
		if (newConfiguration != configuration) {
			NotificationChain msgs = null;
			if (configuration != null) {
				msgs = ((InternalEObject) configuration).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - SafecapPackage.PROJECT__CONFIGURATION, null, msgs);
			}
			if (newConfiguration != null) {
				msgs = ((InternalEObject) newConfiguration).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - SafecapPackage.PROJECT__CONFIGURATION, null, msgs);
			}
			msgs = basicSetConfiguration(newConfiguration, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.PROJECT__CONFIGURATION, newConfiguration,
					newConfiguration));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public RouteBuilderConfiguration getRoutebuilder() {
		return routebuilder;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetRoutebuilder(RouteBuilderConfiguration newRoutebuilder, NotificationChain msgs) {
		final RouteBuilderConfiguration oldRoutebuilder = routebuilder;
		routebuilder = newRoutebuilder;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafecapPackage.PROJECT__ROUTEBUILDER,
					oldRoutebuilder, newRoutebuilder);
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
	public void setRoutebuilder(RouteBuilderConfiguration newRoutebuilder) {
		if (newRoutebuilder != routebuilder) {
			NotificationChain msgs = null;
			if (routebuilder != null) {
				msgs = ((InternalEObject) routebuilder).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafecapPackage.PROJECT__ROUTEBUILDER,
						null, msgs);
			}
			if (newRoutebuilder != null) {
				msgs = ((InternalEObject) newRoutebuilder).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafecapPackage.PROJECT__ROUTEBUILDER,
						null, msgs);
			}
			msgs = basicSetRoutebuilder(newRoutebuilder, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.PROJECT__ROUTEBUILDER, newRoutebuilder, newRoutebuilder));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Labeled> getDarkmatter() {
		if (darkmatter == null) {
			darkmatter = new EObjectContainmentEList<>(Labeled.class, this, SafecapPackage.PROJECT__DARKMATTER);
		}
		return darkmatter;
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
			eNotify(new ENotificationImpl(this, Notification.SET, SafecapPackage.PROJECT__RUNTIME_ATTRIBUTES, oldRuntimeAttributes,
					runtimeAttributes));
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
		case SafecapPackage.PROJECT__NODES:
			return ((InternalEList<?>) getNodes()).basicRemove(otherEnd, msgs);
		case SafecapPackage.PROJECT__SEGMENTS:
			return ((InternalEList<?>) getSegments()).basicRemove(otherEnd, msgs);
		case SafecapPackage.PROJECT__LINES:
			return ((InternalEList<?>) getLines()).basicRemove(otherEnd, msgs);
		case SafecapPackage.PROJECT__ROUTES:
			return ((InternalEList<?>) getRoutes()).basicRemove(otherEnd, msgs);
		case SafecapPackage.PROJECT__AMBITS:
			return ((InternalEList<?>) getAmbits()).basicRemove(otherEnd, msgs);
		case SafecapPackage.PROJECT__EQUIPMENT:
			return ((InternalEList<?>) getEquipment()).basicRemove(otherEnd, msgs);
		case SafecapPackage.PROJECT__WIRES:
			return ((InternalEList<?>) getWires()).basicRemove(otherEnd, msgs);
		case SafecapPackage.PROJECT__NODEWIRES:
			return ((InternalEList<?>) getNodewires()).basicRemove(otherEnd, msgs);
		case SafecapPackage.PROJECT__SUBWIRES:
			return ((InternalEList<?>) getSubwires()).basicRemove(otherEnd, msgs);
		case SafecapPackage.PROJECT__SUBEQUIPMENT:
			return ((InternalEList<?>) getSubequipment()).basicRemove(otherEnd, msgs);
		case SafecapPackage.PROJECT__COMMENTS:
			return ((InternalEList<?>) getComments()).basicRemove(otherEnd, msgs);
		case SafecapPackage.PROJECT__ATTRIBUTES:
			return ((InternalEList<?>) getAttributes()).basicRemove(otherEnd, msgs);
		case SafecapPackage.PROJECT__MERGEDPOINTS:
			return ((InternalEList<?>) getMergedpoints()).basicRemove(otherEnd, msgs);
		case SafecapPackage.PROJECT__CONFIGURATION:
			return basicSetConfiguration(null, msgs);
		case SafecapPackage.PROJECT__ROUTEBUILDER:
			return basicSetRoutebuilder(null, msgs);
		case SafecapPackage.PROJECT__DARKMATTER:
			return ((InternalEList<?>) getDarkmatter()).basicRemove(otherEnd, msgs);
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
		case SafecapPackage.PROJECT__NODES:
			return getNodes();
		case SafecapPackage.PROJECT__SEGMENTS:
			return getSegments();
		case SafecapPackage.PROJECT__LINES:
			return getLines();
		case SafecapPackage.PROJECT__ROUTES:
			return getRoutes();
		case SafecapPackage.PROJECT__AMBITS:
			return getAmbits();
		case SafecapPackage.PROJECT__EQUIPMENT:
			return getEquipment();
		case SafecapPackage.PROJECT__WIRES:
			return getWires();
		case SafecapPackage.PROJECT__NODEWIRES:
			return getNodewires();
		case SafecapPackage.PROJECT__SUBWIRES:
			return getSubwires();
		case SafecapPackage.PROJECT__SUBEQUIPMENT:
			return getSubequipment();
		case SafecapPackage.PROJECT__COMMENTS:
			return getComments();
		case SafecapPackage.PROJECT__RUNTIME_ATTRIBUTES:
			return getRuntimeAttributes();
		case SafecapPackage.PROJECT__ATTRIBUTES:
			return getAttributes();
		case SafecapPackage.PROJECT__MERGEDPOINTS:
			return getMergedpoints();
		case SafecapPackage.PROJECT__ORIGIN:
			return getOrigin();
		case SafecapPackage.PROJECT__AUTHOR:
			return getAuthor();
		case SafecapPackage.PROJECT__VERSION:
			return getVersion();
		case SafecapPackage.PROJECT__DATE:
			return getDate();
		case SafecapPackage.PROJECT__CONFIGURATION:
			return getConfiguration();
		case SafecapPackage.PROJECT__ROUTEBUILDER:
			return getRoutebuilder();
		case SafecapPackage.PROJECT__DARKMATTER:
			return getDarkmatter();
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
		case SafecapPackage.PROJECT__NODES:
			getNodes().clear();
			getNodes().addAll((Collection<? extends Node>) newValue);
			return;
		case SafecapPackage.PROJECT__SEGMENTS:
			getSegments().clear();
			getSegments().addAll((Collection<? extends Segment>) newValue);
			return;
		case SafecapPackage.PROJECT__LINES:
			getLines().clear();
			getLines().addAll((Collection<? extends Line>) newValue);
			return;
		case SafecapPackage.PROJECT__ROUTES:
			getRoutes().clear();
			getRoutes().addAll((Collection<? extends Route>) newValue);
			return;
		case SafecapPackage.PROJECT__AMBITS:
			getAmbits().clear();
			getAmbits().addAll((Collection<? extends Ambit>) newValue);
			return;
		case SafecapPackage.PROJECT__EQUIPMENT:
			getEquipment().clear();
			getEquipment().addAll((Collection<? extends Equipment>) newValue);
			return;
		case SafecapPackage.PROJECT__WIRES:
			getWires().clear();
			getWires().addAll((Collection<? extends Wire>) newValue);
			return;
		case SafecapPackage.PROJECT__NODEWIRES:
			getNodewires().clear();
			getNodewires().addAll((Collection<? extends NodeWire>) newValue);
			return;
		case SafecapPackage.PROJECT__SUBWIRES:
			getSubwires().clear();
			getSubwires().addAll((Collection<? extends SubWire>) newValue);
			return;
		case SafecapPackage.PROJECT__SUBEQUIPMENT:
			getSubequipment().clear();
			getSubequipment().addAll((Collection<? extends SubEquipment>) newValue);
			return;
		case SafecapPackage.PROJECT__COMMENTS:
			getComments().clear();
			getComments().addAll((Collection<? extends Commentary>) newValue);
			return;
		case SafecapPackage.PROJECT__RUNTIME_ATTRIBUTES:
			setRuntimeAttributes((Map<String, Object>) newValue);
			return;
		case SafecapPackage.PROJECT__ATTRIBUTES:
			getAttributes().clear();
			getAttributes().addAll((Collection<? extends ExtAttribute>) newValue);
			return;
		case SafecapPackage.PROJECT__MERGEDPOINTS:
			getMergedpoints().clear();
			getMergedpoints().addAll((Collection<? extends MergedPoint>) newValue);
			return;
		case SafecapPackage.PROJECT__ORIGIN:
			setOrigin((String) newValue);
			return;
		case SafecapPackage.PROJECT__AUTHOR:
			setAuthor((String) newValue);
			return;
		case SafecapPackage.PROJECT__VERSION:
			setVersion((String) newValue);
			return;
		case SafecapPackage.PROJECT__DATE:
			setDate((Date) newValue);
			return;
		case SafecapPackage.PROJECT__CONFIGURATION:
			setConfiguration((SchemaConfiguration) newValue);
			return;
		case SafecapPackage.PROJECT__ROUTEBUILDER:
			setRoutebuilder((RouteBuilderConfiguration) newValue);
			return;
		case SafecapPackage.PROJECT__DARKMATTER:
			getDarkmatter().clear();
			getDarkmatter().addAll((Collection<? extends Labeled>) newValue);
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
		case SafecapPackage.PROJECT__NODES:
			getNodes().clear();
			return;
		case SafecapPackage.PROJECT__SEGMENTS:
			getSegments().clear();
			return;
		case SafecapPackage.PROJECT__LINES:
			getLines().clear();
			return;
		case SafecapPackage.PROJECT__ROUTES:
			getRoutes().clear();
			return;
		case SafecapPackage.PROJECT__AMBITS:
			getAmbits().clear();
			return;
		case SafecapPackage.PROJECT__EQUIPMENT:
			getEquipment().clear();
			return;
		case SafecapPackage.PROJECT__WIRES:
			getWires().clear();
			return;
		case SafecapPackage.PROJECT__NODEWIRES:
			getNodewires().clear();
			return;
		case SafecapPackage.PROJECT__SUBWIRES:
			getSubwires().clear();
			return;
		case SafecapPackage.PROJECT__SUBEQUIPMENT:
			getSubequipment().clear();
			return;
		case SafecapPackage.PROJECT__COMMENTS:
			getComments().clear();
			return;
		case SafecapPackage.PROJECT__RUNTIME_ATTRIBUTES:
			setRuntimeAttributes((Map<String, Object>) null);
			return;
		case SafecapPackage.PROJECT__ATTRIBUTES:
			getAttributes().clear();
			return;
		case SafecapPackage.PROJECT__MERGEDPOINTS:
			getMergedpoints().clear();
			return;
		case SafecapPackage.PROJECT__ORIGIN:
			setOrigin(ORIGIN_EDEFAULT);
			return;
		case SafecapPackage.PROJECT__AUTHOR:
			setAuthor(AUTHOR_EDEFAULT);
			return;
		case SafecapPackage.PROJECT__VERSION:
			setVersion(VERSION_EDEFAULT);
			return;
		case SafecapPackage.PROJECT__DATE:
			setDate(DATE_EDEFAULT);
			return;
		case SafecapPackage.PROJECT__CONFIGURATION:
			setConfiguration((SchemaConfiguration) null);
			return;
		case SafecapPackage.PROJECT__ROUTEBUILDER:
			setRoutebuilder((RouteBuilderConfiguration) null);
			return;
		case SafecapPackage.PROJECT__DARKMATTER:
			getDarkmatter().clear();
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
		case SafecapPackage.PROJECT__NODES:
			return nodes != null && !nodes.isEmpty();
		case SafecapPackage.PROJECT__SEGMENTS:
			return segments != null && !segments.isEmpty();
		case SafecapPackage.PROJECT__LINES:
			return lines != null && !lines.isEmpty();
		case SafecapPackage.PROJECT__ROUTES:
			return routes != null && !routes.isEmpty();
		case SafecapPackage.PROJECT__AMBITS:
			return ambits != null && !ambits.isEmpty();
		case SafecapPackage.PROJECT__EQUIPMENT:
			return equipment != null && !equipment.isEmpty();
		case SafecapPackage.PROJECT__WIRES:
			return wires != null && !wires.isEmpty();
		case SafecapPackage.PROJECT__NODEWIRES:
			return nodewires != null && !nodewires.isEmpty();
		case SafecapPackage.PROJECT__SUBWIRES:
			return subwires != null && !subwires.isEmpty();
		case SafecapPackage.PROJECT__SUBEQUIPMENT:
			return subequipment != null && !subequipment.isEmpty();
		case SafecapPackage.PROJECT__COMMENTS:
			return comments != null && !comments.isEmpty();
		case SafecapPackage.PROJECT__RUNTIME_ATTRIBUTES:
			return runtimeAttributes != null;
		case SafecapPackage.PROJECT__ATTRIBUTES:
			return attributes != null && !attributes.isEmpty();
		case SafecapPackage.PROJECT__MERGEDPOINTS:
			return mergedpoints != null && !mergedpoints.isEmpty();
		case SafecapPackage.PROJECT__ORIGIN:
			return ORIGIN_EDEFAULT == null ? origin != null : !ORIGIN_EDEFAULT.equals(origin);
		case SafecapPackage.PROJECT__AUTHOR:
			return AUTHOR_EDEFAULT == null ? author != null : !AUTHOR_EDEFAULT.equals(author);
		case SafecapPackage.PROJECT__VERSION:
			return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
		case SafecapPackage.PROJECT__DATE:
			return DATE_EDEFAULT == null ? date != null : !DATE_EDEFAULT.equals(date);
		case SafecapPackage.PROJECT__CONFIGURATION:
			return configuration != null;
		case SafecapPackage.PROJECT__ROUTEBUILDER:
			return routebuilder != null;
		case SafecapPackage.PROJECT__DARKMATTER:
			return darkmatter != null && !darkmatter.isEmpty();
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
		if (baseClass == Schema.class) {
			switch (derivedFeatureID) {
			case SafecapPackage.PROJECT__NODES:
				return SchemaPackage.SCHEMA__NODES;
			case SafecapPackage.PROJECT__SEGMENTS:
				return SchemaPackage.SCHEMA__SEGMENTS;
			default:
				return -1;
			}
		}
		if (baseClass == Model.class) {
			switch (derivedFeatureID) {
			case SafecapPackage.PROJECT__LINES:
				return ModelPackage.MODEL__LINES;
			case SafecapPackage.PROJECT__ROUTES:
				return ModelPackage.MODEL__ROUTES;
			case SafecapPackage.PROJECT__AMBITS:
				return ModelPackage.MODEL__AMBITS;
			default:
				return -1;
			}
		}
		if (baseClass == Trackside.class) {
			switch (derivedFeatureID) {
			case SafecapPackage.PROJECT__EQUIPMENT:
				return TracksidePackage.TRACKSIDE__EQUIPMENT;
			case SafecapPackage.PROJECT__WIRES:
				return TracksidePackage.TRACKSIDE__WIRES;
			case SafecapPackage.PROJECT__NODEWIRES:
				return TracksidePackage.TRACKSIDE__NODEWIRES;
			case SafecapPackage.PROJECT__SUBWIRES:
				return TracksidePackage.TRACKSIDE__SUBWIRES;
			case SafecapPackage.PROJECT__SUBEQUIPMENT:
				return TracksidePackage.TRACKSIDE__SUBEQUIPMENT;
			default:
				return -1;
			}
		}
		if (baseClass == Comments.class) {
			switch (derivedFeatureID) {
			case SafecapPackage.PROJECT__COMMENTS:
				return CommentaryPackage.COMMENTS__COMMENTS;
			default:
				return -1;
			}
		}
		if (baseClass == TransientValues.class) {
			switch (derivedFeatureID) {
			case SafecapPackage.PROJECT__RUNTIME_ATTRIBUTES:
				return SafecapPackage.TRANSIENT_VALUES__RUNTIME_ATTRIBUTES;
			default:
				return -1;
			}
		}
		if (baseClass == Extensible.class) {
			switch (derivedFeatureID) {
			case SafecapPackage.PROJECT__ATTRIBUTES:
				return SafecapPackage.EXTENSIBLE__ATTRIBUTES;
			default:
				return -1;
			}
		}
		if (baseClass == Derived.class) {
			switch (derivedFeatureID) {
			case SafecapPackage.PROJECT__MERGEDPOINTS:
				return DerivedPackage.DERIVED__MERGEDPOINTS;
			default:
				return -1;
			}
		}
		if (baseClass == Provenance.class) {
			switch (derivedFeatureID) {
			case SafecapPackage.PROJECT__ORIGIN:
				return SafecapPackage.PROVENANCE__ORIGIN;
			case SafecapPackage.PROJECT__AUTHOR:
				return SafecapPackage.PROVENANCE__AUTHOR;
			case SafecapPackage.PROJECT__VERSION:
				return SafecapPackage.PROVENANCE__VERSION;
			case SafecapPackage.PROJECT__DATE:
				return SafecapPackage.PROVENANCE__DATE;
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
		if (baseClass == Schema.class) {
			switch (baseFeatureID) {
			case SchemaPackage.SCHEMA__NODES:
				return SafecapPackage.PROJECT__NODES;
			case SchemaPackage.SCHEMA__SEGMENTS:
				return SafecapPackage.PROJECT__SEGMENTS;
			default:
				return -1;
			}
		}
		if (baseClass == Model.class) {
			switch (baseFeatureID) {
			case ModelPackage.MODEL__LINES:
				return SafecapPackage.PROJECT__LINES;
			case ModelPackage.MODEL__ROUTES:
				return SafecapPackage.PROJECT__ROUTES;
			case ModelPackage.MODEL__AMBITS:
				return SafecapPackage.PROJECT__AMBITS;
			default:
				return -1;
			}
		}
		if (baseClass == Trackside.class) {
			switch (baseFeatureID) {
			case TracksidePackage.TRACKSIDE__EQUIPMENT:
				return SafecapPackage.PROJECT__EQUIPMENT;
			case TracksidePackage.TRACKSIDE__WIRES:
				return SafecapPackage.PROJECT__WIRES;
			case TracksidePackage.TRACKSIDE__NODEWIRES:
				return SafecapPackage.PROJECT__NODEWIRES;
			case TracksidePackage.TRACKSIDE__SUBWIRES:
				return SafecapPackage.PROJECT__SUBWIRES;
			case TracksidePackage.TRACKSIDE__SUBEQUIPMENT:
				return SafecapPackage.PROJECT__SUBEQUIPMENT;
			default:
				return -1;
			}
		}
		if (baseClass == Comments.class) {
			switch (baseFeatureID) {
			case CommentaryPackage.COMMENTS__COMMENTS:
				return SafecapPackage.PROJECT__COMMENTS;
			default:
				return -1;
			}
		}
		if (baseClass == TransientValues.class) {
			switch (baseFeatureID) {
			case SafecapPackage.TRANSIENT_VALUES__RUNTIME_ATTRIBUTES:
				return SafecapPackage.PROJECT__RUNTIME_ATTRIBUTES;
			default:
				return -1;
			}
		}
		if (baseClass == Extensible.class) {
			switch (baseFeatureID) {
			case SafecapPackage.EXTENSIBLE__ATTRIBUTES:
				return SafecapPackage.PROJECT__ATTRIBUTES;
			default:
				return -1;
			}
		}
		if (baseClass == Derived.class) {
			switch (baseFeatureID) {
			case DerivedPackage.DERIVED__MERGEDPOINTS:
				return SafecapPackage.PROJECT__MERGEDPOINTS;
			default:
				return -1;
			}
		}
		if (baseClass == Provenance.class) {
			switch (baseFeatureID) {
			case SafecapPackage.PROVENANCE__ORIGIN:
				return SafecapPackage.PROJECT__ORIGIN;
			case SafecapPackage.PROVENANCE__AUTHOR:
				return SafecapPackage.PROJECT__AUTHOR;
			case SafecapPackage.PROVENANCE__VERSION:
				return SafecapPackage.PROJECT__VERSION;
			case SafecapPackage.PROVENANCE__DATE:
				return SafecapPackage.PROJECT__DATE;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuilder result = new StringBuilder(super.toString());
		result.append(" (runtimeAttributes: ");
		result.append(runtimeAttributes);
		result.append(", origin: ");
		result.append(origin);
		result.append(", author: ");
		result.append(author);
		result.append(", version: ");
		result.append(version);
		result.append(", date: ");
		result.append(date);
		result.append(')');
		return result.toString();
	}

} // ProjectImpl
