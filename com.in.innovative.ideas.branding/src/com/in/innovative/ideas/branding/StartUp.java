package com.in.innovative.ideas.branding;

import java.io.PrintStream;

import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;

public class StartUp implements IStartup {

	@Override
	public void earlyStartup() {
		linkDefaultOutStreamToConsole();
	}

	/**
	 * Reroute default output streams to a new console, added to console view.
	 */
	public static void linkDefaultOutStreamToConsole() {
		MessageConsole console = findConsole("Innovative Ideas Console");
		PrintStream printStream = new PrintStream(console.newMessageStream());

		// Link standard output stream to the console
		System.setOut(printStream);

		// Link error output stream to the console
		System.setErr(printStream);

		Logger logger = Logger.getLogger("default");
		logger.addAppender(new WriterAppender(new SimpleLayout(), console.newMessageStream()));
	}

	private static MessageConsole findConsole(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++) {
			if (name.equals(existing[i].getName())) {
				return (MessageConsole) existing[i];
			}
		}
		// no console found -> create new one
		MessageConsole newConsole = new MessageConsole(name, Activator.getImageDescriptor("icons/idea.png"));
		conMan.addConsoles(new IConsole[] { newConsole });
		return newConsole;
	}
}
