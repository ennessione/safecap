package uk.ac.ncl.safecap.xdata.verification.transitions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import uk.ac.ncl.safecap.prover.core.ManagedProofObligation;
import uk.ac.ncl.safecap.xdata.verification.core.VerificationBasePlugin;

public class TransitionUtil {
	public static JAXBContext jaxbContext;

	static {
		try {
			jaxbContext = JAXBContext.newInstance(TransitionContainer.class, TransitionConstant.class);
		} catch (final JAXBException e) {
			e.printStackTrace();
			jaxbContext = null;
		}
	}

	public static String ppTransitionKind(ITransitionPathSource transition) {
		String template = transition.getKind()[0];
		for (int i = 2; i < transition.getKind().length; i++) {
			template = template.replace("$" + (i - 1), transition.getKind()[i]);
		}
		return template;
	}

	public static TransitionContainer getTransitionContainerFromFile(File file) {
		try {
			final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (TransitionContainer) jaxbUnmarshaller.unmarshal(file);
		} catch (final JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void saveTransitionContainerToFile(TransitionContainer transitionContainer, IFile file, boolean invalidate)
			throws JAXBException, CoreException, IOException {
		final ByteArrayOutputStream os = new ByteArrayOutputStream();

		saveTransitionContainerToStream(transitionContainer, os);

		file.setContents(new ByteArrayInputStream(os.toByteArray()), IResource.FORCE, null);

		if (invalidate) {
			VerificationBasePlugin.getTransitionCache().ivalidate(new File(file.getLocationURI().getPath()));
		} else {
			VerificationBasePlugin.getTransitionCache().fireChange(new File(file.getLocationURI().getPath()));
		}
	}

	public static void saveTransitionContainerToStream(TransitionContainer transitionContainer, OutputStream os)
			throws JAXBException, CoreException, IOException {
		final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "http://www.w3.org/2001/XMLSchema-instance");
		jaxbMarshaller.marshal(transitionContainer, os);
		os.flush();
	}

	public static boolean isProven(ITransitionPOs transition) {
		if (transition.getPos() == null) {
			return true;
		}

		for (final ManagedProofObligation po : transition.getPos()) {
			if (!isProven(po)) {
				return false;
			}
		}

		return true;
	}

	public static boolean isProven(ManagedProofObligation tpo) {
		return tpo.isValid();
	}

	public static boolean isValid(ITransition t) {
		if (t.getParsed() == null) {
			return false;
		}

		for (int i = 0; i < t.getParsed().getFsPreconditions().size(); i++) {
			if (t.getParsed().getFsPreconditions().get(i).hasErrors()) {
				return false;
			}
		}

		for (int i = 0; i < t.getParsed().getFsPostconditions().size(); i++) {
			if (t.getParsed().getFsPostconditions().get(i).hasErrors()) {
				return false;
			}
		}

		return true;
	}

}
