package uk.ac.ncl.safecap.analytics.ctextract.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uk.ac.ncl.safecap.analytics.ctextract.core.CTEPlugin;
import uk.ac.ncl.safecap.analytics.ctextract.ui.CTUtils;
import uk.ac.ncl.safecap.textentry.core.TEBuilder;
import uk.ac.ncl.safecap.textentry.core.TEPart;
import uk.ac.ncl.safecap.textentry.core.TEPlugin;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.ColumnModel;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.TableModel;
import uk.ac.ncl.safecap.xdata.base.tablesmodel.TablesModel;
import uk.ac.ncl.safecap.xmldata.DataContext;
import uk.ac.ncl.safecap.xmldata.DataUtils;
import uk.ac.ncl.safecap.xmldata.parsing.IDataParser;
import uk.ac.ncl.safecap.xmldata.parsing.ParserCatalog;

@XmlRootElement
public class CTProject extends CTRuleContainer {
	private String tablesModelName;
	private String resourceName;
	private String parserName;
	private List<CTProjectPart> parts;
	private String internalisedTablesModel;

	private transient TablesModel model;
	private transient DataContext data;

	public void extract(String file) throws Exception {
		setResourceName(file);
		normalise();
		xmlStringExport(file);
	}

	public void xmlStringExport(String file) throws Exception {
		final Document document = xmlExport();
		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
		final Transformer transformer = transformerFactory.newTransformer();
		final DOMSource domSource = new DOMSource(document);
		final StreamResult streamResult = new StreamResult(new File(file));
		transformer.transform(domSource, streamResult);
	}

	public Document xmlExport() throws Exception {
		final DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
		final Document document = documentBuilder.newDocument();

		// root element
		final Element root = document.createElement("dataset");
		document.appendChild(root);

		final Element info = document.createElement("info");
		info.appendChild(CTUtils.xmlTextElement(document, "builder", "safecap.sda.ctextract"));
		info.appendChild(CTUtils.xmlTextElement(document, "model", tablesModelName));
		info.appendChild(CTUtils.xmlTextElement(document, "source", resourceName));
		info.appendChild(CTUtils.xmlTextElement(document, "parser", parserName));
		info.appendChild(CTUtils.xmlTextElement(document, "date", new Date().toString()));
		root.appendChild(info);

		final DatasetModel output = process();

		output.toXML(document, root);

		return document;
	}

	public DatasetModel process() {
		final DatasetModel output = new DatasetModel();

		for (final CTProjectPart p : parts) 
			p.dsExport(output);
		
		return output;
	}

	public DatasetModel process(CTProjectPart p) {
		final DatasetModel output = new DatasetModel();

		p.dsExport(output);
		
		return output;
	}	
	
	@XmlElement(name = "modelbody")
	public String getInternalisedTablesModel() {
		return internalisedTablesModel;
	}

	public void setInternalisedTablesModel(String internalisedTablesModel) {
		this.internalisedTablesModel = internalisedTablesModel;
	}

	public void addCommonRules() {
		addCommonOperators();
		addCommonRewrites();
	}

	private void addCommonRewrites() {
		rwrule("E[\"??word\"]", "N");
		rwrule("N N", "N");
		rwrule("(N)", "N");
	}

	private CTRewriteRule rwrule(String pattern, String template) {
		final CTRewriteRule rwrule = new CTRewriteRule();
		rwrule.setPattern(pattern);
		rwrule.setTemplate(template);
		rwrule.setContainer(this);
		addRewriteRule(rwrule);
		return rwrule;
	}

	private void addCommonOperators() {
		try {
			final List<String> lines = CTEPlugin.getLibFileLineContents("dict/operators");
			for (final String operator : lines) {
				rwrule("E[\"" + operator + "\"]", "O[\"" + operator + "\"]");
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public void normalise() {
		if (internalisedTablesModel != null) {
			final TEPart p = TEBuilder.build(internalisedTablesModel);
			model = TablesModel.build(p);
			if (model != null) {
				tablesModelName = p.getName();
			} else if (tablesModelName != null) {
				fetchModel();
			}
		} else if (tablesModelName != null) {
			fetchModel();
		}

		fetchData();

		if (parts != null) {
			for (final CTProjectPart p : parts) {
				p.setParent(this);
				p.normalise();
			}
		}

		for (final CTRewriteRule r : getRewriteRules()) {
			r.setContainer(this);
		}
	}

	public ColumnModel resolvePath(String path) {
		return model.findColumnByPath(path);
	}

	public TableModel getTable(ColumnModel column) {
		TableModel model = column.getParent();

		while (model.getParent() != null) {
			model = model.getParent();
		}

		return model;
	}

	public CTProjectPart fetchPart(String path) {
		if (parts == null) {
			parts = new ArrayList<>();
		}

		for (final CTProjectPart p : parts) {
			if (path.equals(p.getPath())) {
				return p;
			}
		}

		final CTProjectPart np = new CTProjectPart(this, path);
		parts.add(np);
		return np;
	}

	@XmlElement(name = "parser")
	public String getParserName() {
		return parserName;
	}

	public void setParserName(String parserName) {
		this.parserName = parserName;
	}

	@XmlElement(name = "model")
	public String getTablesModelName() {
		return tablesModelName;
	}

	public void setTablesModelName(String tablesModelName) {
		this.tablesModelName = tablesModelName;
	}

	@XmlElement(name = "resource")
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String name) {
		resourceName = name;
	}

	private void fetchModel() {
		try {
			final List<TEPart> parts = TEPlugin.getTERegistry().getAllElements("conceptmap");
			for (final TEPart p : parts) {
				if (p.getName().contentEquals(tablesModelName)) {
					internalisedTablesModel = p.pp();
					model = TablesModel.build(p);
					break;
				}
			}
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	private void fetchData() {
		try {
			IDataParser parser = ParserCatalog.INSTANCE.getDefault();
			if (parserName != null) {
				parser = ParserCatalog.INSTANCE.getByName(parserName);
			}
			data = DataUtils.importDataContext(parser, resourceName);
			// System.out.println("Loaded " + resourceName);
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	@XmlElement(name = "part")
	public List<CTProjectPart> getParts() {
		return parts;
	}

	public void setParts(List<CTProjectPart> parts) {
		this.parts = parts;
	}

	@XmlTransient
	public TablesModel getModel() {
		return model;
	}

	@XmlTransient
	public DataContext getData() {
		return data;
	}

	public CTProjectPart resolveProjectPartByPath(String path) {
		for (final CTProjectPart p : parts) {
			if (p.getPath().equals(path)) {
				return p;
			}
		}

		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (parserName == null ? 0 : parserName.hashCode());
		result = prime * result + (parts == null ? 0 : parts.hashCode());
		result = prime * result + (resourceName == null ? 0 : resourceName.hashCode());
		result = prime * result + (tablesModelName == null ? 0 : tablesModelName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CTProject other = (CTProject) obj;
		if (parserName == null) {
			if (other.parserName != null) {
				return false;
			}
		} else if (!parserName.equals(other.parserName)) {
			return false;
		}
		if (parts == null) {
			if (other.parts != null) {
				return false;
			}
		} else if (!parts.equals(other.parts)) {
			return false;
		}
		if (resourceName == null) {
			if (other.resourceName != null) {
				return false;
			}
		} else if (!resourceName.equals(other.resourceName)) {
			return false;
		}
		if (tablesModelName == null) {
			if (other.tablesModelName != null) {
				return false;
			}
		} else if (!tablesModelName.equals(other.tablesModelName)) {
			return false;
		}
		return true;
	}

	public int code() {
		return System.identityHashCode(this);
	}

}
