package com.example.e4.rcp.fileexplorer.listeners;

import java.io.File;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;

public class ComboSelectionListener extends SelectionAdapter{
	
	private CCombo pathCombo;
	private TableViewer tableViewer;
	private List<String> history;
	private Shell shell;
	
	/**
	 * @param pathCombo
	 * @param tableViewer
	 * @param history
	 */
	public ComboSelectionListener(
			CCombo pathCombo, TableViewer tableViewer, List<String> history, Shell shell) {
		this.pathCombo = pathCombo;
		this.tableViewer = tableViewer;
		this.history = history;
		this.shell = shell;
	}
	
	/**
	 * Sent when selection occurs in the control.
	 * The default behavior is to do nothing.
	 *
	 * @param e an event containing information about the selection
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		String path = pathCombo.getItem(pathCombo.getSelectionIndex());
		File[] listFiles = new File(path).listFiles();
		if (listFiles == null) {
			MessageDialog.openError(shell, "Error",
					"Incorect path. Check spelling and try again.");
			return;
		}
		pathCombo.add(history.get(history.size() - 1), 0);
		pathCombo.setText(path);
		history.add(path);
		tableViewer.setInput(listFiles);
	}
}
