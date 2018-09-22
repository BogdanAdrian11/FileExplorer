package com.example.e4.rcp.fileexplorer.listeners;

import java.io.File;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Shell;

public class EnterKeyListener implements KeyListener {

	private CCombo pathCombo;
	private TableViewer tableViewer;
	private List<String> history;
	private Shell shell;
	
	public EnterKeyListener(CCombo pathCombo, TableViewer tableViewer, List<String> history,
			Shell shell) {
		super();
		this.pathCombo = pathCombo;
		this.tableViewer = tableViewer;
		this.history = history;
		this.shell = shell;
	}
	
	/**
	 * Sent when a key is pressed on the system keyboard.
	 *
	 * @param e an event containing information about the key press
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.character == SWT.CR) {
			String path = pathCombo.getText();
			if (path.isEmpty()) {
				return;
			}
			File[] listFiles = new File(path).listFiles();
			if (listFiles == null) {
				MessageDialog.openError(shell, "Error",
						"Incorect path. Check spelling and try again.");
				return;
			}
			if (history.size() > 0) { 
				pathCombo.add(history.get(history.size() - 1), 0);
			}
			tableViewer.setInput(listFiles);
			history.add(path);
		}
	}
	
	/**
	 * Sent when a key is released on the system keyboard.
	 *
	 * @param e an event containing information about the key release
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}