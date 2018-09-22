package com.example.e4.rcp.fileexplorer.services;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class ThreadService {

	public static void threadCheckDialog(Shell shell, String path) {
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		Iterator<Thread> it = threadSet.iterator();
		while (it.hasNext()) {
			Thread thread = it.next();
			while (path.contains(thread.getName()) && thread.isAlive()) {
				MessageDialog dialog = new MessageDialog(shell, "Warning", null,
						"Another thread is currently working on this...",
						MessageDialog.WARNING,
						new String[] { "wait", "interupt"}, 0);
				int result = dialog.open();
				if (result == 1) {
					thread.interrupt();
				}
				try {
					thread.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
