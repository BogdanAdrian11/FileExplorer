package com.example.e4.rcp.fileexplorer.listeners;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import com.example.e4.rcp.fileexplorer.services.FileService;
import com.example.e4.rcp.fileexplorer.services.ThreadService;

public class PasteSelectionListener implements SelectionListener {

	private TreeViewer treeViewer;
	private TableViewer tableViewer;
	private List<String> history;
	private Shell shell;
	private MenuItem pasteItem;
	private List<String> source;
	
	/**
	 * @param treeViewer
	 * @param tableViewer
	 * @param history
	 * @param shell
	 * @param pasteItem
	 */
	public PasteSelectionListener(TreeViewer treeViewer, TableViewer tableViewer, 
			List<String> history, Shell shell, MenuItem pasteItem, List<String> source) {

		this.treeViewer = treeViewer;
		this.tableViewer = tableViewer;
		this.history = history;
		this.shell = shell;
		this.pasteItem = pasteItem;
		this.source = source;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
				
		String path = "";
		if (history.size() > 0) {
			path = history.get(history.size() - 1);
		}

		ThreadService.threadCheckDialog(shell, path);
		
		for (String item : source) {
			String fileName = item.substring(item.lastIndexOf('\\'));
			if ((path).contains(item.toString())) {
				MessageDialog.openError(shell, "Error", "Cannot copy into the source path");
				continue;
			}
			if (item.equals(path + fileName)) {
				continue;
			}
			try {
				File source = new File(item);
				File dest = new File(path + fileName);
				if (pasteItem.getToolTipText().equals("copy")) {
					FileService.copy(source, dest, shell);
				} else if (pasteItem.getToolTipText().equals("move")) {
					FileService.move(source, dest, shell);
				}
				
			} catch (IOException e1) {
				MessageDialog.openError(shell, "error", "access denied");
			} catch (InvalidPathException e2) {
				MessageDialog.openError(shell, "error", "invalid path");

			}
		}
		
		pasteItem.setEnabled(false);
		pasteItem.setToolTipText("paste");
		treeViewer.refresh();
		tableViewer.setInput(new File(history.get(history.size() - 1)).listFiles());
		
		source.clear();
		
		TableItem items[] = tableViewer.getTable().getItems();
		for (TableItem item : items) {
			item.setFont(JFaceResources.getFontRegistry().get(JFaceResources.DEFAULT_FONT));
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {

	}

}