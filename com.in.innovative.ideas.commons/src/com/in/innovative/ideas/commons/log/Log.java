package com.in.innovative.ideas.commons.log;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.statushandlers.StatusManager;

public class Log {
	private MessageConsole console = null;
	private String title = "Innovative Ideas Console";

	public static final int MSG_INFORMATION = 1;
	public static final int MSG_WARNING = 2;
	public static final int MSG_ERROR = 4;

	private static Log instanceObj;
	private Logger log;

	public void log(String pluginId, int status, String msg, Throwable e) {
		BasicConfigurator.configure();
		setLog(Logger.getLogger(pluginId));

		Log.instanceObj.println(msg, status);

		Status statusLog = new Status(status, pluginId, msg, e);
		StatusManager.getManager().handle(statusLog, StatusManager.LOG);
	}

	public void println(String msg, int msgKind) {
		if (msg == null) {
			return;
		}
		getNewMessageConsoleStream(msgKind).println(msg);
	}

	private MessageConsoleStream getNewMessageConsoleStream(int msgKind) {
		int swtColorId = SWT.COLOR_DARK_GREEN;
		switch (msgKind) {
		case MSG_INFORMATION:
			swtColorId = SWT.COLOR_DARK_GREEN;
			break;
		case MSG_ERROR:
			swtColorId = SWT.COLOR_DARK_RED;
			break;
		case MSG_WARNING:
			swtColorId = SWT.COLOR_DARK_YELLOW;
			break;
		default:
		}
		MessageConsoleStream msgConsoleStream = getMessageConsole().newMessageStream();
		msgConsoleStream.setColor(Display.getCurrent().getSystemColor(swtColorId));
		return msgConsoleStream;
	}

	private MessageConsole getMessageConsole() {
		if (console == null) {
			console = createMessageConsoleStream(title);
			ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { console });
		}
		return console;
	}

	private MessageConsole createMessageConsoleStream(String title) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++) {
			if (title.equals(existing[i].getName())) {
				return (MessageConsole) existing[i];
			}
		}
		return console;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public static Log getInstance() {
		if (instanceObj == null) {
			instanceObj = new Log();
		}
		return instanceObj;
	}
}
