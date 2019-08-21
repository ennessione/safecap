package uk.ac.ncl.safecap.analytics.ctextract.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uk.ac.ncl.safecap.analytics.ctextract.core.CTEToken;
import uk.ac.ncl.safecap.analytics.ctextract.core.CTETokenList;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTBin;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTProject;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTProjectPart;
import uk.ac.ncl.safecap.analytics.ctextract.main.CTRule;

public class CTUtils {
	public static JAXBContext jaxbContext;

	static {
		try {
			jaxbContext = JAXBContext.newInstance(CTProject.class, CTProjectPart.class, CTRule.class, CTBin.class, CTEToken.class,
					CTETokenList.class);
		} catch (final JAXBException e) {
			e.printStackTrace();
			jaxbContext = null;
		}
	}

	public static Element xmlTextElement(Document document, String key, String value) {
		final Element element = document.createElement(key);
		element.setTextContent(value);
		return element;
	}

	public static CTProject getProjectFromFile(File file) {
		try {
			final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			final CTProject prj = (CTProject) jaxbUnmarshaller.unmarshal(file);
			prj.normalise();
			return prj;
		} catch (final JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static CTProject getProjectFromStream(InputStream is) {
		try {
			final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			final CTProject prj = (CTProject) jaxbUnmarshaller.unmarshal(is);
			prj.normalise();
			return prj;
		} catch (final JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void saveProjectToFile(CTProject project, IFile file) throws JAXBException, CoreException, IOException {
		final ByteArrayOutputStream os = new ByteArrayOutputStream(64192);

		saveProjectToFile(project, os);

		file.setContents(new ByteArrayInputStream(os.toByteArray()), IResource.FORCE, null);
	}

	public static void saveProjectToFile(CTProject project, OutputStream os) throws JAXBException, CoreException, IOException {
		final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "http://www.w3.org/2001/XMLSchema-instance");
		jaxbMarshaller.marshal(project, os);
		os.flush();
	}
}
