package uk.ac.ncl.safecap.xdata.verification.configsave;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import uk.ac.ncl.safecap.xdata.verification.report.ReportConfig;
import uk.ac.ncl.safecap.xmldata.DataContext;

public class ProjectConfigZip implements IProjectConfig {
	private ProjectInfo info;
	private final List<IVerificationStep> steps;
	private ReportConfig reportConfig;
	private File file;

	public ProjectConfigZip(String name, String profile, String author, String description, ReportConfig reportConfig) {
		info = new ProjectInfo(name, profile, author, description);
		this.reportConfig = reportConfig;
		steps = new ArrayList<>();
	}

	public File getFile() {
		return file;
	}

	public ProjectConfigZip(File file) throws IOException, ClassNotFoundException {
		this.file = file;
		steps = new ArrayList<>();
		final FileInputStream fis = new FileInputStream(file);
		final ZipInputStream zin = new ZipInputStream(new BufferedInputStream(fis));
		ZipEntry entry = zin.getNextEntry();
		final ObjectInputStream ois = new ObjectInputStream(zin);

		if (entry != null && entry.getName().equals("safecapdataanalytics")) {
			entry = zin.getNextEntry();
		}

		if (entry != null && entry.getName().equals("header")) {
			info = (ProjectInfo) ois.readObject();
			entry = zin.getNextEntry();
		}

		if (entry != null && entry.getName().equals("config")) {
			reportConfig = (ReportConfig) ois.readObject();
			entry = zin.getNextEntry();
		}

		int index = 0;
		while (entry != null) {
			if (entry.getName().equals("step_" + index)) {
				final VerificationStep step = (VerificationStep) ois.readObject();
				steps.add(step);
				index++;
			}
			entry = zin.getNextEntry();
		}
		ois.close();
		zin.close();
		fis.close();
	}

	public void save(File file) throws FileNotFoundException, IOException {
		if (this.file == null) {
			this.file = file;
		}
		for (final IVerificationStep step : steps) {
			step.prepareToSave();
		}
		write(new FileOutputStream(this.file));
	}

	public void save() throws FileNotFoundException, IOException {
		if (file != null) {
			save(file);
		}
	}

	private void write(FileOutputStream fs) throws IOException {
		final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(fs));
		out.putNextEntry(new ZipEntry("safecapdataanalytics"));
		final ObjectOutputStream oos = new ObjectOutputStream(out);
		out.putNextEntry(new ZipEntry("header"));
		oos.writeObject(info);

		out.putNextEntry(new ZipEntry("config"));
		oos.writeObject(reportConfig);

		int index = 0;
		for (final IVerificationStep step : steps) {
			out.putNextEntry(new ZipEntry("step_" + index++));
			oos.writeObject(step);
		}

		oos.flush();
		oos.close();
		out.close();
		fs.close();
	}

	@Override
	public void addVerificationAttempt(String schemaFileName, String schemaFileHash, DataContext schema, String ctFileName,
			String ctFileHash, DataContext ct, String interlocking, ReportResult result, File reportFolder) {
		final VerificationStep step = new VerificationStep(schemaFileName, schemaFileHash, schema, ctFileName, ctFileHash, ct, interlocking,
				result, reportFolder);
		steps.add(step);
	}

	@Override
	public String getName() {
		return info.getName();
	}

	@Override
	public String getAuthor() {
		return info.getAuthor();
	}

	@Override
	public String getDescription() {
		return info.getDescripton();
	}

	@Override
	public ReportConfig getReportConfig() {
		return reportConfig;
	}

	@Override
	public List<IVerificationStep> getHistoricReports() {
		return steps;
	}

	@Override
	public String getProfile() {
		return info.getProfile();
	}

	@Override
	public Object[] getExistingPropertyReview(String key) {
		if (steps.size() > 0) {
			for (int i = steps.size() - 1; i >= 0; i--) {
				final IVerificationStep pstep = steps.get(i);
				final PropertyReview pr = pstep.getResult().getReviews(key);
				if (pr != null && pr.getCommentary() != null) {
					return new Object[] { pstep, pr };
				}

			}
		}
		return null;
	}

	@Override
	public Map<String, List<PackagedPropertyReview>> getAllPropertyReviews() {
		final Map<String, List<PackagedPropertyReview>> result = new HashMap<>();
		for (final IVerificationStep vs : steps) {
			getAllPropertyReviews(vs, result);
		}

		return result;
	}

	private void getAllPropertyReviews(IVerificationStep vs, Map<String, List<PackagedPropertyReview>> result) {
		for (final String key : vs.getResult().getReviewKeys()) {
			final PackagedPropertyReview ppr = new PackagedPropertyReview(vs.getDate(), vs.getResult().getReviews(key));
			List<PackagedPropertyReview> list;
			if (result.containsKey(key)) {
				list = result.get(key);
			} else {
				list = new ArrayList<>();
				result.put(key, list);
			}
			list.add(ppr);
		}
	}

	public PropertyViolation getHistoricPropertyViolation(String key, String hash) {
		if (steps.size() > 0) {
			for (int i = steps.size() - 1; i >= 0; i--) {
				final IVerificationStep step = steps.get(i);
				final PropertyViolation pv = step.getResult().getViolation(key, hash);
				if (pv != null) {
					return pv;
				}
			}
		}
		return null;
	}

	public List<PropertyViolation> getHistoricPropertyViolations(String key, String hash, IVerificationStep step) {
		final List<PropertyViolation> result = new ArrayList<>();
		int startIndex = steps.size() - 1;
		if (step != null && steps.indexOf(step) >= 0) {
			startIndex = steps.indexOf(step);
		}

		if (steps.size() > 0) {
			for (int i = startIndex; i >= 0; i--) {
				final IVerificationStep zstep = steps.get(i);
				final PropertyViolation pv = zstep.getResult().getViolation(key, hash);
				if (pv != null && pv.getCommentary() != null) {
					result.add(pv);
				}
			}
		}
		return result;
	}

}
