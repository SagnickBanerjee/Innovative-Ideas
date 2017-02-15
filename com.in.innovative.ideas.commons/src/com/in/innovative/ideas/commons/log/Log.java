package com.in.innovative.ideas.commons.log;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

public class Log {
	private static Log instanceObj;
	private Logger log;

	public void log(String pluginId, int status, String msg, Throwable e) {
		BasicConfigurator.configure();
		setLog(Logger.getLogger(pluginId));

		Status statusLog = new Status(status, pluginId, msg, e);
		StatusManager.getManager().handle(statusLog, StatusManager.LOG);
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
