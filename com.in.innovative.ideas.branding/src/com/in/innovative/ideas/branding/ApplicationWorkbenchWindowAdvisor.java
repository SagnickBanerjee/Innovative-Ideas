package com.in.innovative.ideas.branding;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {
	public static IStatusLineManager statusline = null;
	private IWorkbenchWindow window;
	private TrayItem trayItem;
	private Image trayImage;

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	@Override
	public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	@Override
	public void preWindowOpen() {
		PlatformUI.getPreferenceStore().setValue(IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS, false);
		Platform.getLogFileLocation().toFile().delete();

		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(true);
		configurer.setShowProgressIndicator(true);
		configurer.setShowPerspectiveBar(true);
	}

	@Override
	public void postWindowOpen() {
		super.postWindowOpen();
		getWindowConfigurer().getWindow().getShell().setMaximized(true);
		statusline = getWindowConfigurer().getActionBarConfigurer().getStatusLineManager();
		ApplicationWorkbenchWindowAdvisor.statusline.update(true);
		statusline.update(true);
	}

	@Override
	public void postWindowCreate() {
		super.postWindowOpen();
		getWindowConfigurer().getWindow().getShell().setMaximized(true);
		statusline = getWindowConfigurer().getActionBarConfigurer().getStatusLineManager();

		List<String> unwantedItems = Arrays.asList("org.eclipse.ui.run");

		IMenuManager menuManager = getWindowConfigurer().getActionBarConfigurer().getMenuManager();
		removeUnwantedItems(unwantedItems, menuManager);

		PreferenceManager pm = PlatformUI.getWorkbench().getPreferenceManager();
		pm.remove("org.eclipse.ui.preferencePages.Workbench");
		pm.remove("org.eclipse.equinox.security.ui.storage");
		pm.remove("org.eclipse.equinox.security.ui.category");
		pm.remove("org.eclipse.debug.ui.DebugPreferencePage");
		pm.remove("org.eclipse.team.ui.TeamPreferences");

		window = getWindowConfigurer().getWindow();
		trayItem = initTaskItem(window);
		if (trayItem != null) {
			minimizeBehavior();
			hookPopupMenu();
		}
	}

	private void removeUnwantedItems(final List<String> unwantedItems, final IMenuManager menuManager) {
		IContributionItem[] items = menuManager.getItems();
		for (IContributionItem item : items) {
			if (item instanceof IMenuManager) {
				removeUnwantedItems(unwantedItems, (IMenuManager) item);
			}
			if (unwantedItems.contains(item.getId())) {
				menuManager.remove(item);
			}
		}
	}

	private void minimizeBehavior() {
		window.getShell().addShellListener(new ShellAdapter() {
			@Override
			public void shellIconified(ShellEvent e) {
				window.getShell().setVisible(false);
			}
		});
		trayItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell shell = window.getShell();
				if (!shell.isVisible()) {
					window.getShell().setMinimized(false);
					shell.setVisible(true);
				}
			}
		});
	}

	private void hookPopupMenu() {
		trayItem.addMenuDetectListener(new MenuDetectListener() {
			@Override
			public void menuDetected(MenuDetectEvent e) {
				Menu menu = new Menu(window.getShell(), SWT.POP_UP);
				MenuItem exit = new MenuItem(menu, SWT.NONE);
				exit.setText("Goodbye!");
				exit.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						window.getWorkbench().close();
					}
				});
				menu.setVisible(true);
			}
		});
	}

	private TrayItem initTaskItem(IWorkbenchWindow window) {
		final Tray tray = window.getShell().getDisplay().getSystemTray();
		TrayItem trayItem = new TrayItem(tray, SWT.NONE);
		trayImage = Activator.getImageDescriptor("/icons/idea.png").createImage();
		trayItem.setImage(trayImage);
		trayItem.setToolTipText("Innovative Ideas");
		return trayItem;

	}

	@Override
	public void dispose() {
		if (trayImage != null) {
			trayImage.dispose();
		}
		if (trayItem != null) {
			trayItem.dispose();
		}
	}
}
