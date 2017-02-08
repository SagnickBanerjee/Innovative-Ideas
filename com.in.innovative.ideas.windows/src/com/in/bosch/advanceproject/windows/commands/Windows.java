package com.in.bosch.advanceproject.windows.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.in.innovative.ideas.commons.log.Log;
import com.in.innovative.ideas.windows.Activator;

public class Windows extends AbstractHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.showView("com.in.innovative.ideas.windows");
			String infoMsg = "Windows View Test... ";
			Log.getInstance().log(Activator.PLUGIN_ID, Status.INFO, infoMsg, null);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}
}
