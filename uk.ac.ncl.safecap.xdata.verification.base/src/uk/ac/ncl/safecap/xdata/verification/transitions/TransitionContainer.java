package uk.ac.ncl.safecap.xdata.verification.transitions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import uk.ac.ncl.safecap.prover.core.IProofBranch;
import uk.ac.ncl.safecap.prover.core.IProofEventListener;
import uk.ac.ncl.safecap.xdata.verification.RootCatalog;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xdata.verification.core.VerificationBasePlugin;
import uk.ac.ncl.safecap.xmldata.FileUtil;

@XmlRootElement
public class TransitionContainer implements IProofEventListener {
	private static final int MAGIC_LENGTH = 10;
	private Map<String, File> files;
	private Map<String, LocatedBase> namedLocations;
	private Collection<TransitionCluster> transitionClusters;
	private List<TransitionConstant> constants;

	private String fingerprint;
	private String name;

	transient private SDAContext sdaContext;
	// private transient TransformFactory transformFactory;
	private transient Map<File, String> fileCache;

	public TransitionContainer() {
		transitionClusters = new ArrayList<>();
		fileCache = new HashMap<>();
		constants = new ArrayList<>();
	}

	public void addConstant(TransitionConstant constant) {
		constants.add(constant);
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TransitionConstant> getConstants() {
		return constants;
	}

	public void setConstants(List<TransitionConstant> constants) {
		this.constants = constants;
	}

	public String getNamedLocationOr(LocatedEntity area, String def) {
		final String nl = getNamedLocation(area);
		if (nl == null) {
			return def;
		} else {
			return nl;
		}
	}

	public String getText(LocatedEntity element, String def) {
		final String contents = getContents(element.getSource());
		if (contents == null) {
			return def;
		}
		final int start = element.start - MAGIC_LENGTH;
		final int end = element.end - MAGIC_LENGTH;
		return contents.substring(start, end);
	}

	public String getTextOr(String location, String def) {
		final String result = getText(location);
		if (result == null) {
			return def;
		} else {
			return result;
		}
	}

	/**
	 * Get text value of a named location
	 * 
	 * @param location name; either absolute (TYPE.name) or relative (name). In the
	 *                 latter case searches for any location with a given relative
	 *                 name
	 * @return location text or null if none found
	 */
	public String getText(String location) {
		LocatedBase lb = namedLocations.get(location);
		if (lb != null) {
			final String source = location.substring(0, location.indexOf('.'));
			return getText(new LocatedEntity(lb.start, lb.end, source), null);
		}
		for (final String l : namedLocations.keySet()) {
			lb = namedLocations.get(l);
			if (l.endsWith("." + location)) {
				final String source = l.substring(0, l.indexOf('.'));
				return getText(new LocatedEntity(lb.start, lb.end, source), null);
			}
		}
		return null;
	}

	public String getNamedLocation(LocatedEntity area) {
		if (area.getSource() == null) {
			return null;
		}
		for (final String l : namedLocations.keySet()) {
			final LocatedBase lb = namedLocations.get(l);
			if (l.startsWith(area.getSource() + ".")) {
				if (lb.start <= area.start && lb.end >= area.end) {
					return l;
				}
			}
		}

		return null;
	}

	public LocatedEntity resolveNamedLocation(String name) {
		final LocatedBase lb = namedLocations.get(name);
		if (lb != null) {
			final int dotindex = name.indexOf('.');
			if (dotindex > 0) {
				name = name.substring(0, dotindex);
			}

			return new LocatedEntity(lb.getStart(), lb.getEnd(), name);
		}
		return null;
	}

	public void clear() {
		transitionClusters.clear();
		constants.clear();

		if (files != null) {
			files.clear();
		}

		if (namedLocations != null) {
			namedLocations.clear();
		}
	}

	public Map<String, LocatedBase> getNamedLocations() {
		return namedLocations;
	}

	public void setNamedLocations(Map<String, LocatedBase> namedLocations) {
		this.namedLocations = namedLocations;
	}

	public Map<String, File> getFiles() {
		return files;
	}

	public void setFiles(Map<String, File> files) {
		this.files = files;
	}

	@XmlTransient
	public SDAContext getSDAContext() {
		return sdaContext;
	}

	public void setSDAContext(RootCatalog root, SDAContext ctx) {
		sdaContext = ctx;
		// transformFactory.getTacticPackage().addListener(this);
	}

	public Collection<TransitionCluster> getClusters() {
		return transitionClusters;
	}

	public void setClusters(Collection<TransitionCluster> clusters) {
		transitionClusters = clusters;
	}

	@Override
	public void update(IProofBranch element) {
		VerificationBasePlugin.getTransitionCache().fireChange(this, element);
	}

	public String getContents(String filetag) {
		if (getFiles().containsKey(filetag)) {
			final File file = getFiles().get(filetag);
			if (fileCache.containsKey(file)) {
				return fileCache.get(file);
			} else {
				if (file.exists()) {
					try {
						final String contents = FileUtil.getFileContents(file);
						if (contents != null) {
							fileCache.put(file, contents);
						}

						return contents;
					} catch (final Exception e) {
						return null;
					}
				}
			}
		}

		return null;
	}

	public SourceInformation[] getSourceInformation(LocatedEntity loc) {
		if (loc instanceof LocatedString) {
			return getSourceInformation((LocatedString) loc);
		} else {
			return new SourceInformation[] { new SourceInformation(loc) };
		}
	}

	public SourceInformation[] getSourceInformation(LocatedString loc) {
		if (loc.getParts() == null) {
			return new SourceInformation[] { new SourceInformation(loc) };
		} else {
			return multiSource(loc.getParts());
		}
	}

	private SourceInformation[] multiSource(LocatedElement[] locations) {
		final Map<LocatedEntity, List<LocatedElement>> clusterMap = new HashMap<>();
		for (final LocatedElement le : locations) {
			if (le.getEnclosure() != null) {
				List<LocatedElement> siblings = clusterMap.get(le.getEnclosure());
				if (siblings == null) {
					siblings = new ArrayList<>();
					clusterMap.put(le.getEnclosure(), siblings);
				}
				if (!siblings.contains(le)) {
					siblings.add(le);
				}
			} else {
				assert false;
			}
		}

		final SourceInformation[] result = new SourceInformation[clusterMap.size()];
		int index = 0;
		for (final LocatedEntity key : clusterMap.keySet()) {
			Collections.sort(clusterMap.get(key));
			result[index++] = new SourceInformation(key, clusterMap.get(key));
		}
		return result;
	}

	public class SourceInformation {

		// public String locationName;
		public String fileName;
		public String filePath;
		public String text;
		public int offset;
		public int[] highlightStart;
		public int[] highlightEnd;
		public String[] trace;

		private int startLine = -1;

		SourceInformation(LocatedEntity loc) {
			makeLoc(loc);
		}

		SourceInformation(LocatedEntity enclosure, Collection<LocatedElement> parts) {

			assert enclosure != null && parts.size() > 0;

			if (parts.size() > 1) {
				// String contents = getContents(source);
				final String contents = getContents(enclosure.getSource());
				fileName = enclosure.getSource();
				filePath = getFiles().get(fileName).getAbsolutePath();
				final int start = enclosure.start - MAGIC_LENGTH;
				final int end = enclosure.end - MAGIC_LENGTH;
				offset = start;
				text = contents.substring(start, end);
				trace = parts.iterator().next().getTrace();

				highlightStart = new int[parts.size()];
				highlightEnd = new int[parts.size()];

				int index = 0;
				for (final LocatedEntity le : parts) {
					highlightStart[index] = Math.max(0, le.getStart() - start - MAGIC_LENGTH);
					highlightEnd[index] = Math.min(end - start, le.getEnd() - start - MAGIC_LENGTH);
					index++;
				}

			} else if (parts.size() == 1) {
				final LocatedEntity loc = parts.iterator().next();
				makeLoc(loc);
			} else {
				assert false;
			}
		}

		public int getStartLine() {
			if (startLine == -1) {
				if (filePath != null) {
					try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
						String line;
						int position = 0;
						int lineno = 0;
						while ((line = br.readLine()) != null && position < offset) {
							position += line.length();
							lineno++;
						}
						startLine = lineno;
					} catch (final Exception e) {
					}
				}
			}

			return startLine;
		}

		private void makeLoc(LocatedEntity loc) {
			if (loc.getSource() != null && loc.getStart() > 0 && loc.getEnd() > loc.getStart()) {
				fileName = loc.getSource();
				filePath = getFiles().get(loc.getSource()).getAbsolutePath();

				if (loc instanceof LocatedElement) {
					trace = ((LocatedElement) loc).getTrace();
				}

				final String contents = getContents(loc.getSource());
				if (contents != null && loc.getEnd() < contents.length()) {
					highlightStart = new int[1];
					highlightEnd = new int[1];
					if (loc instanceof LocatedElement && ((LocatedElement) loc).getEnclosure() != null) {
						final LocatedEntity enc = ((LocatedElement) loc).getEnclosure();
						final int start = enc.getStart() - MAGIC_LENGTH;
						final int end = enc.getEnd() - MAGIC_LENGTH;
						offset = start;
						text = contents.substring(start, end);
						highlightStart[0] = Math.max(0, loc.getStart() - start - MAGIC_LENGTH);
						highlightEnd[0] = Math.min(end - start, loc.getEnd() - start - MAGIC_LENGTH);
					} else {
						final int start = findEmptyLineBackward(contents, loc.getStart() - MAGIC_LENGTH);
						final int end = findEmptyLineForward(contents, loc.getEnd() - MAGIC_LENGTH);
						offset = start;
						text = contents.substring(start, end);
						highlightStart[0] = Math.max(0, loc.getStart() - start - MAGIC_LENGTH);
						highlightEnd[0] = Math.min(end - start, loc.getEnd() - start - MAGIC_LENGTH);
					}
				}
			}
		}

		SourceInformation(LocatedString loc) {
			makeLoc(loc);
		}

		private int findEmptyLineBackward(String text, int pos) {
			int start = Math.max(0, pos - 1024);
			int state = 0;
			outer: for (int i = pos; i >= start; i--) {
				final char c = text.charAt(i);
				if (c == '.') {
					break;
				}
				switch (state) {
				case 0:
					if (c == '\n') {
						state = 1;
					}
					break;
				case 1:
					if (c == '\n') {
						start = i + 1;
						break outer;
					} else if (!Character.isWhitespace(c)) {
						state = 0;
					}
					break;
				}
			}

			assert start <= pos;

			return start;
		}

		private int findEmptyLineForward(String text, int pos) {
			int end = Math.min(text.length(), pos + 1024);
			int state = 0;
			int p0 = 0;
			outer: for (int i = pos; i < end; i++) {
				final char c = text.charAt(i);
				switch (state) {
				case 0:
					if (c == '\n') {
						p0 = i > 0 ? i - 1 : i;
						state = 1;
					}
					break;
				case 1:
					if (c == '\n') {
						end = p0;
						break outer;
					} else if (!Character.isWhitespace(c)) {
						state = 0;
					}
					break;
				}
			}

			return end;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
			result = prime * result + offset;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SourceInformation other = (SourceInformation) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (fileName == null) {
				if (other.fileName != null)
					return false;
			} else if (!fileName.equals(other.fileName))
				return false;
			if (offset != other.offset)
				return false;
			return true;
		}

		private TransitionContainer getOuterType() {
			return TransitionContainer.this;
		}
		
		
	}

}
