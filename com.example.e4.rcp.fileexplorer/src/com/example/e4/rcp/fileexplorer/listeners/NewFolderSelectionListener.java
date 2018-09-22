package com.example.e4.rcp.fileexplorer.listeners;

import java.io.File;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.fileexplorer.services.ThreadService;
import com.example.e4.rcp.fileexplorer.views.NewFolderDialog;

public class NewFolderSelectionListener implements SelectionListener {
	
	private TreeViewer treeViewer;
	private TableViewer tableViewer;
	private List<String> history;
	private Shell shell;
	
	/**
	 * @param treeViewer
	 * @param tableViewer
	 * @param history
	 */
	public NewFolderSelectionListener(TreeViewer treeViewer, TableViewer tableViewer,
			List<String> history, Shell shell) {
		super();
		this.treeViewer = treeViewer;
		this.tableViewer = tableViewer;
		this.history = history;
		this.shell = shell;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		
		String path = "";
		if (history.size() > 0) {
			path = history.get(history.size() - 1);
		}
		
		ThreadService.threadCheckDialog(shell, path);
		
		NewFolderDialog newFolderDialog = new NewFolderDialog(shell);
		newFolderDialog.create();
		if (newFolderDialog.open() == Window.OK) {
			try {
				boolean valid = new File(path + "\\" + newFolderDialog.getFolderName()).mkdir();
				if (!valid) {
					MessageDialog.openError(shell, "Error", "Wrong folder name");
				}
			} catch (NullPointerException e1){
				MessageDialog.openError(shell, "Error", "Wrong folder name");
			}
			treeViewer.refresh();
			tableViewer.setInput(new File(path).listFiles());
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
	}
	
	
}
