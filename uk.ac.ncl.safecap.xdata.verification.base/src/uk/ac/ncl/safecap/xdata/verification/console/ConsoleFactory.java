package uk.ac.ncl.safecap.xdata.verification.console;

import java.util.ArrayList;
import java.util.List;

public class ConsoleFactory implements ISafeCapConsoleCommand {
	public static ConsoleFactory INSTANCE = new ConsoleFactory();
	private static List<ISafeCapConsoleCommand> commands;

	private static void build() {
		commands = new ArrayList<>();
		commands.add(CatalogEval.INSTANCE);
		commands.add(CatalogType.INSTANCE);
		commands.add(CatalogSize.INSTANCE);
		commands.add(CatalogSimp.INSTANCE);
		commands.add(CatalogCompile.INSTANCE);
		commands.add(CatalogIsConst.INSTANCE);
		commands.add(SchemaFixOverlaps.INSTANCE);
		commands.add(SchemaSubRoutePath.INSTANCE);
		commands.add(SchemaRoutePath.INSTANCE);
		commands.add(BuildOG.INSTANCE);
		commands.add(SchemaObfuscate.INSTANCE);
		commands.add(ReadOG.INSTANCE);
		commands.add(PlaceSignals.INSTANCE);
		commands.add(SchemaFixNodes.INSTANCE);
		commands.add(ExportSchemaToCSV.INSTANCE);
		commands.add(ExportDataToB.INSTANCE);
		commands.add(CatalogCommitResults.INSTANCE);
		commands.add(CatalogDataFrame.INSTANCE);
	}

	public String complete(String text) {
		List<String> candidates = new ArrayList<>();
		for (final ISafeCapConsoleCommand cmd : commands) {
			if (cmd instanceof BaseCommand) {
				BaseCommand bc = (BaseCommand) cmd;
				if (bc.getName().startsWith(text))
					candidates.add(text);
			}
		}

		if (candidates.isEmpty()) {
			return "";
		} else if (candidates.size() == 1) {
			return candidates.get(0).substring(text.length());
		} else {
			return commonPrefix(candidates).substring(text.length());
		}
	}

	private String commonPrefix(List<String> strs) {
		int i = 0;
		while (true) {
			boolean flag = true;
			for (int j = 1; j < strs.size(); j++) {
				if (strs.get(i).length() <= i || strs.get(j - 1).length() <= i || strs.get(j).charAt(i) != strs.get(j - 1).charAt(i)) {
					flag = false;
					break;
				}
			}

			if (flag) {
				i++;
			} else {
				break;
			}
		}

		return strs.get(0).substring(0, i);
	}

	private ConsoleFactory() {
		build();
	}

	@Override
	public boolean handle(ISafeCapConsole console, String command, String[] arguments) {
		for (final ISafeCapConsoleCommand cmd : commands) {
			if (cmd.handle(console, command, arguments)) {
				return true;
			}
		}

		if ("help".equals(command) && arguments.length == 0) {
			final StringBuilder sb = new StringBuilder();
			for (final ISafeCapConsoleCommand cmd : commands) {
				if (cmd instanceof BaseCommand) {
					final BaseCommand bcmd = (BaseCommand) cmd;
					sb.append(bcmd.getName());
					sb.append("[" + bcmd.getArguments() + "]");
					sb.append('\n');
				}
			}
			console.out(sb.toString());
			return true;
		}

		if (commands.size() > 0 && commands.get(0) instanceof BaseCommand) {
			final BaseCommand bcmd = (BaseCommand) commands.get(0);
			if (bcmd.getArguments() == 1 && arguments.length == 0) {
				bcmd.execute(console, new String[] { command });
				return true;
			} else if (bcmd.getArguments() == 1 && arguments.length == 1) {
				bcmd.execute(console, new String[] { command + "(" + arguments[0] + ")" });
				return true;
			}
		}

		return false;
	}

}
