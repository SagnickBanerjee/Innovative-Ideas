package com.in.innovative.ideas;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	/**
	 * The ID of the perspective as specified in the extension.
	 */
	public static final String ID = "com.in.innovative.ideas.perspective";

	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);

		IFolderLayout main = layout.createFolder("main", IPageLayout.TOP, 0.3f, editorArea);
		main.addView("com.in.innovative.ideas.windows");
		main.addPlaceholder("com.in.innovative.ideas.windows");
	}
}
