package com.in.innovative.ideas.branding.splash;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.splash.AbstractSplashHandler;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.Version;

import com.in.innovative.ideas.branding.Activator;

/**
 * 
 * @author SAGNICK
 * 
 *         Customizing Splash Screen
 * 
 */
public class SplashHandler extends AbstractSplashHandler {

	private Composite textPanel = null;
	private Label progressLabel;
	private ProgressBar progressBar;

	public SplashHandler() {
		// TODO Auto-generated constructor stub
	}

	private String getSplashText() {
		Version v = FrameworkUtil.getBundle(Activator.class).getVersion();
		String splashText = "Version : " + (String.format("%d.%d.%d", v.getMajor(), v.getMinor(), v.getMicro()));
		return splashText;
	}

	@Override
	public void init(final Shell splash) {
		// Store the shell
		super.init(splash);

		// Configure the shell layout
		configureUISplash();
		// Create UI
		createUI();
		// Force the UI to layout
		splash.layout(true);

	}

	private void createUI() {

		Shell splash = getSplash();
		// set background for Label text so it is transparent
		splash.setBackgroundMode(SWT.INHERIT_DEFAULT);

		// create a text panel positioned at the bottom center
		// of the splash screen graphic.
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalAlignment = SWT.LEFT;
		gd.verticalAlignment = SWT.END;
		gd.horizontalIndent = -5;

		textPanel = new Composite(splash, SWT.NONE);
		textPanel.setLayoutData(gd);
		textPanel.setLayout(new GridLayout(1, true));

		createProgressInfo(splash);
		toggelCheckProgress(splash, true);

		IProgressMonitor prog = this.getBundleProgressMonitor();
		prog.beginTask("Advance Project", 100);
	}

	private void configureUISplash() {
		GridLayout layout = new GridLayout(1, true);
		getSplash().setLayout(layout);
	}

	private void createProgressInfo(final Shell splash) {
		progressLabel = new Label(splash, SWT.NONE);
		progressLabel.setText("Innovative Ideas\n" + getSplashText());
		GridData data = new GridData();
		data.horizontalIndent = 0;
		progressLabel.setLayoutData(data);
		progressLabel.setVisible(false);

		progressBar = new ProgressBar(splash, SWT.NONE | SWT.SMOOTH);
		data = new GridData(SWT.NONE, SWT.NONE, false, false);
		data.widthHint = 475;
		data.horizontalSpan = 1;
		progressBar.setLayoutData(data);
		progressBar.setVisible(false);

	}

	private void toggelCheckProgress(final Shell splash, final boolean state) {
		progressLabel.setVisible(state);
		progressBar.setVisible(state);
		splash.layout();
	}

	/**
	 * @see org.eclipse.ui.splash.AbstractSplashHandler#dispose()
	 */
	@Override
	public void dispose() {
		textPanel.dispose();

		super.dispose();
	}

	/*
	 * @see
	 * org.eclipse.ui.splash.AbstractSplashHandler#getBundleProgressMonitor()
	 */
	@Override
	public IProgressMonitor getBundleProgressMonitor() {
		return new NullProgressMonitor() {

			@Override
			public void beginTask(final String name, final int totalWork) {
				getSplash().getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						progressBar.setSelection(50);
					}
				});
			}
		};
	}
}
