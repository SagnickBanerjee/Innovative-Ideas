package com.in.innovative.ideas.branding;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	// Actions - important to allocate these only in makeActions, and then use
	// them
	// in the fill methods. This ensures that the actions aren't recreated
	// when fillActionBars is called with FILL_PROXY.
	private IWorkbenchAction showHelpAction;
	private IWorkbenchAction searchHelpAction;
	private IWorkbenchAction dynamicHelpAction;
	private IWorkbenchAction resetPerspectiveAction;
	private IWorkbenchAction savePerspectiveAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.application.ActionBarAdvisor#fillStatusLine(org.eclipse.
	 * jface.action.IStatusLineManager)
	 */
	@Override
	protected void fillStatusLine(IStatusLineManager statusLine) {
		super.fillStatusLine(statusLine);
	}

	@Override
	protected void makeActions(IWorkbenchWindow window) {
		showHelpAction = ActionFactory.HELP_CONTENTS.create(window);
		register(showHelpAction);

		searchHelpAction = ActionFactory.HELP_SEARCH.create(window);
		register(searchHelpAction);

		dynamicHelpAction = ActionFactory.DYNAMIC_HELP.create(window);
		register(dynamicHelpAction);

		resetPerspectiveAction = ActionFactory.RESET_PERSPECTIVE.create(window);
		register(resetPerspectiveAction);

		savePerspectiveAction = ActionFactory.SAVE_PERSPECTIVE.create(window);
		register(savePerspectiveAction);

	}

	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		ToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.BOTTOM);
		ToolBarContributionItem toolbarcontib = new ToolBarContributionItem(toolbar,
				"com.in.innovative.ideas.toggletoolbar.main");
		coolBar.add(toolbarcontib);
	}
}
