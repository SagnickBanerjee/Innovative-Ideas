package com.in.innovative.ideas.log.config;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public class Activator extends Plugin {

	private static BundleContext context;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;

		// This is to force slf4j to use the logback.xml from this plugin. Use
		// the command line variable to change the default level of the root
		// logger.

		ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
		LoggerContext context = (LoggerContext) loggerFactory;
		// StatusPrinter.print(context);

		URL logbackConfigFileUrl = FileLocator.find(Activator.context.getBundle(), new Path("logback.xml"), null);
		logbackConfigFileUrl = FileLocator.toFileURL(logbackConfigFileUrl);

		// TODO Make this as an optional setup. We can use this variable to
		// provide another configuration to debug the application.
		String urlAsString = System.getProperty("logback.configurationFile");
		logbackConfigFileUrl = new URL(urlAsString);

		try {
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(context);
			context.reset();
			configurator.doConfigure(logbackConfigFileUrl);
		} catch (JoranException je) {
			// Ignore; StatusPrinter will handle this
		}

		// Use this statement to check the initial logback configuration lookup
		// StatusPrinter.print(context);

		StatusPrinter.printInCaseOfErrorsOrWarnings(context);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
