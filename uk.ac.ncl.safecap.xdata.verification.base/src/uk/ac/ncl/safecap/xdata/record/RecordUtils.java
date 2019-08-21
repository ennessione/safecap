package uk.ac.ncl.safecap.xdata.record;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

public class RecordUtils {
	private static JAXBContext jaxbContext;

	static {
		try {
			jaxbContext = JAXBContext.newInstance(VerificationEntry.class, VerificationRecord.class, VerificationRun.class);
		} catch (final JAXBException e) {
			e.printStackTrace();
			jaxbContext = null;
		}
	}

	private static VerificationRecord getVerificationRecordFromFile(InputStream stream) {
		try {
			final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (VerificationRecord) jaxbUnmarshaller.unmarshal(stream);
		} catch (final JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void saveVerificationRecordToFile(VerificationRecord record, IFile file)
			throws JAXBException, CoreException, IOException {
		final ByteArrayOutputStream os = new ByteArrayOutputStream(64192);

		saveVerificationRecordToFile(record, os);

		file.setContents(new ByteArrayInputStream(os.toByteArray()), IResource.FORCE, null);
	}

	private static void saveVerificationRecordToFile(VerificationRecord dataContext, OutputStream os)
			throws JAXBException, CoreException, IOException {
		final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		jaxbMarshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "http://www.w3.org/2001/XMLSchema-instance");

		jaxbMarshaller.marshal(dataContext, os);
		os.flush();
	}

	public static synchronized VerificationRecord load() {
		try {
			final IProject verProject = ResourcesPlugin.getWorkspace().getRoot().getProject("Verification");
			if (verProject.exists()) {
				final IFile file = verProject.getFile("verification.xml");
				if (file.exists()) {
					return getVerificationRecordFromFile(file.getContents());
				}
			}
		} catch (final CoreException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static synchronized void save(VerificationRecord record) {
		try {
			final IProject verProject = ResourcesPlugin.getWorkspace().getRoot().getProject("Verification");
			if (verProject.exists()) {
				final IFile file = verProject.getFile("verification.xml");
				if (!file.exists()) {
					final InputStream empty = new ByteArrayInputStream(new byte[0]);
					file.create(empty, true, new NullProgressMonitor());
				}
				saveVerificationRecordToFile(record, file);
			}
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}
}
