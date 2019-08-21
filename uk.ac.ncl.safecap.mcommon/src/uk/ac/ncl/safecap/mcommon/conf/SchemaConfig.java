package uk.ac.ncl.safecap.mcommon.conf;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.dialogs.MessageDialog;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

import safecap.Labeled;
import safecap.Project;
import safecap.config.AutoOverlapsConfiguration;
import safecap.config.ConfigFactory;
import safecap.config.MergedAmbitsConfiguration;
import safecap.config.MergedPointsConfiguration;
import safecap.config.NormalisationRule;
import safecap.config.SchemaConfiguration;
import safecap.derived.DerivedElement;
import safecap.derived.MergedAmbit;
import safecap.derived.MergedPoint;
import safecap.model.Ambit;
import safecap.model.Route;
import safecap.schema.Segment;
import safecap.trackside.Equipment;
import safecap.trackside.Signal;
import uk.ac.ncl.safecap.misc.core.AmbitUtil;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.PointUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.misc.core.SignalUtil;
import uk.ac.ncl.safecap.misc.core.TextDialog;

public class SchemaConfig implements IConfigError {
	private static final boolean WN_SIGNAL = true;
	private static final boolean WN_TRACK = true;
	public static final int OVERLAP_LEN_MAX = 193;
	public static final int OVERLAP_LEN_MIN = 173;

	private final StringBuffer errors;
	private final Project project;

	public SchemaConfig(Project project) {
		this.project = project;
		errors = new StringBuffer();
	}

	public AbstractTransactionalCommand getCommandReadConfigNonInteractive(final Reader in) {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(project);
		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "ReadConfigNonInteractive", null) {
			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
				read(in);
				return CommandResult.newOKCommandResult();
			}
		};
		return command;
	}

	public AbstractTransactionalCommand getCommandReadConfigInteractive(final Reader in) {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(project);
		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "ReadConfigInteractive", null) {
			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
				read(in);
				if (errors.length() > 0) {
					final TextDialog td = new TextDialog(null, errors.toString());
					td.open();
					final boolean commit = MessageDialog.openConfirm(null, "Configuration import",
							"Commit these configuration despite errors?");
					if (!commit) {
						return CommandResult.newErrorCommandResult("Import aborted due to errors");
					}
				}
				// System.out.println("conf:" + project.getConfiguration());
				return CommandResult.newOKCommandResult();
			}
		};
		return command;
	}

	public AbstractTransactionalCommand getCommandMergingNonInteractive() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(project);
		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "MergeNonInteractive", null) {
			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
				execMerging();
				return CommandResult.newOKCommandResult();
			}
		};
		return command;
	}

	public AbstractTransactionalCommand getCommandMergingInteractive() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(project);
		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "MergeInteractive", null) {
			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
				execMerging();
				if (errors.length() > 0) {
					final TextDialog td = new TextDialog(null, errors.toString());
					td.open();
					final boolean commit = MessageDialog.openConfirm(null, "Track and point merged detection",
							"Commit these configuration despite errors?");
					if (!commit) {
						return CommandResult.newErrorCommandResult("Merging aborted due to errors");
					}
				}
				return CommandResult.newOKCommandResult();
			}
		};
		return command;
	}

	public AbstractTransactionalCommand getCommandNormalisationNonInteractive() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(project);
		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "NormalisationNonInteractive", null) {
			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
				execWeakNormalise();
				execStrongNormalisation();
				return CommandResult.newOKCommandResult();
			}
		};
		return command;
	}

	public AbstractTransactionalCommand getCommandNormalisationInteractive() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(project);
		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "NormalisationInteractive", null) {
			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
				execWeakNormalise();
				execStrongNormalisation();
				if (errors.length() > 0) {
					final TextDialog td = new TextDialog(null, errors.toString());
					td.open();
					final boolean commit = MessageDialog.openConfirm(null, "Strong normalisation",
							"Commit these configuration despite errors?");
					if (!commit) {
						return CommandResult.newErrorCommandResult("Normalisation aborted due to errors");
					}
				}
				return CommandResult.newOKCommandResult();
			}
		};
		return command;
	}

	public AbstractTransactionalCommand getCommandReset() {
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(project);
		final AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "NormalisationNonInteractive", null) {
			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
				reset();
				project.getAmbits().clear();
				project.getLines().clear();
				project.getRoutes().clear();
				return CommandResult.newOKCommandResult();
			}
		};
		return command;
	}

	public void read(Reader in) {
		int line = 0;
		final SchemaConfiguration config = ConfigFactory.eINSTANCE.createSchemaConfiguration();
		config.setWnSignal(false);
		config.setWnTrack(false);
		project.setConfiguration(config);
		try {
			String[] parts;
			final CSVParser parser = new CSVParser(CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, '\0',
					CSVParser.DEFAULT_STRICT_QUOTES);
			final CSVReader reader = new CSVReader(in, 0, parser);
			while ((parts = reader.readNext()) != null) {
				try {
					parts = CSVUtil.trim(parts);
					processLine(line, parts);
				} catch (final Throwable e) {
					error("line " + line + ": " + e.getMessage());
				}
				line++;
			}

			reader.close();
			// EmfUtil.saveProject(project);
		} catch (final Throwable e) {
			error("I/O error: " + e.getMessage());
		}
	}

	private void processLine(int line, String[] parts) {
		if (parts.length > 0) {
			final String kind = CSVUtil.unquote(parts[0]);
			if ("comment".equals(kind)) {
			} else if ("detectmergedpoints".equals(kind)) {
				final MergedPointsConfiguration mpc = ConfigFactory.eINSTANCE.createMergedPointsConfiguration();
				mpc.setName(CSVUtil.unquote(parts[1]));
				mpc.setDelimiter(CSVUtil.unquote(parts[2]));
				mpc.setCode(CSVUtil.unquote(parts[3]));
				project.getConfiguration().setMergedpoints(mpc);
				return;
			} else if ("detectmergedambits".equals(kind)) {
				final MergedAmbitsConfiguration mpc = ConfigFactory.eINSTANCE.createMergedAmbitsConfiguration();
				mpc.setName(CSVUtil.unquote(parts[1]));
				mpc.setDelimiter(CSVUtil.unquote(parts[2]));
				mpc.setCode(CSVUtil.unquote(parts[3]));
				project.getConfiguration().setMergedambits(mpc);
				return;
			} else if ("pointmap".equals(kind)) {
				final NormalisationRule nr = ConfigFactory.eINSTANCE.createNormalisationRule();
				nr.setPattern(CSVUtil.unquote(parts[1]));
				nr.setTemplate(CSVUtil.unquote(parts[2]));
				project.getConfiguration().setNormpoint(nr);
				return;
			} else if ("trackmap".equals(kind)) {
				final NormalisationRule nr = ConfigFactory.eINSTANCE.createNormalisationRule();
				nr.setPattern(CSVUtil.unquote(parts[1]));
				nr.setTemplate(CSVUtil.unquote(parts[2]));
				project.getConfiguration().setNormtrack(nr);
				return;
			} else if ("signalmap".equals(kind)) {
				final NormalisationRule nr = ConfigFactory.eINSTANCE.createNormalisationRule();
				nr.setPattern(CSVUtil.unquote(parts[1]));
				nr.setTemplate(CSVUtil.unquote(parts[2]));
				project.getConfiguration().setNormsignal(nr);
				return;
			} else if ("detectoverlaps".equals(kind)) {
				AutoOverlapsConfiguration ovlconf = project.getConfiguration().getOverlaps();
				if (ovlconf == null) {
					ovlconf = ConfigFactory.eINSTANCE.createAutoOverlapsConfiguration();
					project.getConfiguration().setOverlaps(ovlconf);
				}
				ovlconf.setMinLength(CSVUtil.getInt(parts[1]));
				ovlconf.setMaxLength(CSVUtil.getInt(parts[2]));
				return;
			} else if ("trackwn".equals(kind)) {
				project.getConfiguration().setWnTrack(true);
			} else if ("signalwn".equals(kind)) {
				project.getConfiguration().setWnSignal(true);
			} else if (kind.length() > 0) {
				error("line " + line + ": unknown command " + kind);
			}
		}
	}

	public void reset() {
		project.setConfiguration(null);
	}

	public void rebuildRouteAmbits() {
		for (final Route route : project.getRoutes()) {
			rebuildRouteAmbits(route);
		}
	}

	private void rebuildRouteAmbits(Route route) {
		final List<Ambit> ambits = new ArrayList<>();
		final Map<Segment, Ambit> map = AmbitUtil.getSegmentAmbitMap(project);

		for (final Segment s : route.getSegments()) {
			final Ambit a = map.get(s);
			assert a != null;
			if (ambits.isEmpty() || ambits.get(ambits.size() - 1) != a) {
				ambits.add(a);
			}
		}

		route.getAmbits().clear();
		route.getAmbits().addAll(ambits);
	}

	public void execMerging() {
		if (getMergedAmbitConfiguration() != null) {
			final String name = getMergedAmbitConfiguration().getName();
			final String delimiter = getMergedAmbitConfiguration().getDelimiter();
			final String code = getMergedAmbitConfiguration().getCode();
			AmbitUtil.buildMergedAmbits(project, name, delimiter, code, false, this);
			final String ss = checkMergedAmbits(project);
			if (ss.length() > 0) {
				error("Detecting merged track:\n" + ss);
			}
		}

		if (getMergedPointConfiguration() != null) {
			final String name = getMergedPointConfiguration().getName();
			final String delimiter = getMergedPointConfiguration().getDelimiter();
			final String code = getMergedPointConfiguration().getCode();
			PointUtil.buildMergedPoints(project, name, delimiter, code);
			final String ss = checkMergedPoints(project);
			if (ss.length() > 0) {
				error("Detecting merged points:\n" + ss);
			}
		}

	}

	public void execStrongNormalisation() {
		if (getSignalNormalisation() != null) {
			final String pattern = getSignalNormalisation().getPattern();
			final String template = getSignalNormalisation().getTemplate();
			if (pattern == null || pattern.length() < 2 || template == null || template.length() < 2) {
				error("No pattern or template in signal normalisation");
			} else {
				try {
					SignalUtil.renameSignals(project, pattern, template);
				} catch (final Throwable e) {
					error("Signal normalisation failed: " + e);
				}
			}
		}

		if (getTrackNormalisation() != null) {
			final String pattern = getTrackNormalisation().getPattern();
			final String template = getTrackNormalisation().getTemplate();
			if (pattern == null || pattern.length() < 2 || template == null || template.length() < 2) {
				error("No pattern or template in track normalisation");
			} else {
				try {
					AmbitUtil.renameAmbits(project, pattern, template);
				} catch (final Throwable e) {
					error("Track normalisation failed: " + e);
				}
			}
		}

		if (getPointNormalisation() != null) {
			final String pattern = getPointNormalisation().getPattern();
			final String template = getPointNormalisation().getTemplate();
			if (pattern == null || pattern.length() < 2 || template == null || template.length() < 2) {
				error("No pattern or template in point normalisation");
			} else {
				try {
					PointUtil.renamePoints(project, pattern, template);
				} catch (final Throwable e) {
					error("Point normalisation failed: " + e);
				}
			}
		}
	}

	public static boolean doWeakNormaliseSignals(Project project) {
		if (project.getConfiguration() != null) {
			return project.getConfiguration().isWnSignal();
		} else {
			return WN_SIGNAL;
		}
	}

	public static boolean doWeakNormaliseTracks(Project project) {
		if (project.getConfiguration() != null) {
			return project.getConfiguration().isWnTrack();
		} else {
			return WN_TRACK;
		}
	}

	public MergedAmbitsConfiguration getMergedAmbitConfiguration() {
		if (project.getConfiguration() != null) {
			return project.getConfiguration().getMergedambits();
		} else {
			return null;
		}
	}

	public MergedPointsConfiguration getMergedPointConfiguration() {
		if (project.getConfiguration() != null) {
			return project.getConfiguration().getMergedpoints();
		} else {
			return null;
		}
	}

	public NormalisationRule getPointNormalisation() {
		if (project.getConfiguration() != null) {
			return project.getConfiguration().getNormpoint();
		} else {
			return null;
		}
	}

	public NormalisationRule getTrackNormalisation() {
		if (project.getConfiguration() != null) {
			return project.getConfiguration().getNormtrack();
		} else {
			return null;
		}
	}

	public NormalisationRule getSignalNormalisation() {
		if (project.getConfiguration() != null) {
			return project.getConfiguration().getNormsignal();
		} else {
			return null;
		}
	}

	public AutoOverlapsConfiguration getOverlapConfig() {
		if (project.getConfiguration() != null && project.getConfiguration().getOverlaps() != null) {
			return project.getConfiguration().getOverlaps();
		} else {
			return null;
		}
	}

	public static String checkMergedAmbits(Project root) {
		final StringBuffer sb = new StringBuffer();
		for (final MergedAmbit x : AmbitUtil.getMergedAmbits(root)) {
			checkMergedAmbits(x, sb);
		}

		return sb.toString();
	}

	private static void checkMergedAmbits(MergedAmbit x, StringBuffer sb) {
		checkDerivedSize(x, sb);
		checkDerivedDuplicates(x, sb);
		checkNumericGaps(x, sb);
	}

	public static String checkMergedPoints(Project root) {
		final StringBuffer sb = new StringBuffer();
		for (final MergedPoint x : root.getMergedpoints()) {
			checkMergedPoints(x, sb);
		}

		return sb.toString();
	}

	private static void checkMergedPoints(MergedPoint x, StringBuffer sb) {
		checkDerivedSize(x, sb);
		checkDerivedDuplicates(x, sb);
		checkAlphaGaps(x, sb);
	}

	private static void checkDerivedSize(DerivedElement x, StringBuffer sb) {
		if (x.getCodes().size() < 2) {
			sb.append(x.getLabel() + ": too few combined elements (" + x.getCodes().size() + ")\n");
		}
	}

	private static void checkDerivedDuplicates(DerivedElement x, StringBuffer sb) {
		for (int i = 0; i < x.getCodes().size(); i++) {
			for (int j = 0; j < x.getCodes().size(); j++) {
				if (i != j && x.getCodes().get(i).equals(x.getCodes().get(j))) {
					sb.append(x.getLabel() + ": duplicate elements - " + x.getCodes().get(i) + " and " + x.getCodes().get(j) + "\n");
					return;
				}
			}
		}
	}

	private static void checkNumericGaps(DerivedElement x, StringBuffer sb) {
		final List<Integer> set = new ArrayList<>();
		int max = 0;

		for (final String code : x.getCodes()) {
			final int n = Integer.parseInt(code);
			if (n > max) {
				max = n;
			}

			set.add(n);
		}

		for (int i = 1; i <= max; i++) {
			if (!set.contains(i)) {
				sb.append(x.getLabel() + ": missing element " + x.getLabel() + "-" + i + "\n");
				return;
			}
		}
	}

	private static void checkAlphaGaps(DerivedElement x, StringBuffer sb) {
		final List<Integer> set = new ArrayList<>();
		int max = 0;

		for (final String code : x.getCodes()) {
			if (code.length() > 0) {
				final int n = code.toCharArray()[0];
				if (n > max) {
					max = n;
				}

				set.add(n);
			}
		}

		for (int i = 'A'; i <= max; i++) {
			if (!set.contains(i)) {
				sb.append(x.getLabel() + ": missing element " + x.getLabel() + (char) i + "\n");
				return;
			}
		}
	}

	public void execWeakNormalise() {
		if (doWeakNormaliseSignals(project)) {
			for (final Equipment eqp : project.getEquipment()) {
				if (eqp instanceof Signal) {
					final Signal s = (Signal) eqp;
					_weakNormalise(s);
				}
			}
		}

		if (doWeakNormaliseTracks(project)) {
			for (final Ambit amb : project.getAmbits()) {
				_weakNormalise(amb);
			}
		}

	}

	public static void _weakNormalise(Labeled element) {
		String id = element.getLabel();
		if (id != null) {
			final int bracket_open = id.indexOf('(');
			final int bracket_close = id.indexOf(')');
			if (bracket_open > 0 && bracket_open < bracket_close) {
				final String flag = id.substring(bracket_open + 1, bracket_close);
				id = id.substring(0, bracket_open);
				element.setLabel(id);
				if (element instanceof Ambit && flag.equals("X")) {
					final Ambit ambit = (Ambit) element;
					ExtensionUtil.delete(ambit, SafecapConstants.EXT_AMBIT, SafecapConstants.TRACK_AXLE_COUNTER);
					ambit.getAttributes().add(ExtensionUtil.mkBool(SafecapConstants.EXT_AMBIT, SafecapConstants.TRACK_AXLE_COUNTER, true));
				} else if (element instanceof Signal && flag.equals("W")) {
					final Signal signal = (Signal) element;
					ExtensionUtil.delete(signal, SafecapConstants.EXT_SIGNAL, SafecapConstants.SIGNAL_WARNER);
					signal.getAttributes().add(ExtensionUtil.mkBool(SafecapConstants.EXT_SIGNAL, SafecapConstants.SIGNAL_WARNER, true));
				}
			}
		}
	}

	static boolean isApplicable(Project root, Labeled element) {
		if (element instanceof Signal) {
			return SchemaConfig.doWeakNormaliseSignals(root);
		} else if (element instanceof Ambit || element instanceof Segment) {
			return SchemaConfig.doWeakNormaliseTracks(root);
		}
		return false;
	}

	@Override
	public void error(String message) {
		errors.append(message);
		errors.append("\n");
	}
}
