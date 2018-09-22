 package com.example.e4.rcp.fileexplorer.listeners;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.fileexplorer.services.FileService;
import com.example.e4.rcp.fileexplorer.services.ThreadService;
import com.example.e4.rcp.fileexplorer.views.RenameDialog;

public class RenameSelectionListener implements SelectionListener{

	private TreeViewer treeViewer;
	private TableViewer tableViewer;
	private List<String> history;
	private Shell shell;
	
	/**
	 * @param treeViewer
	 * @param tableViewer
	 * @param history
	 * @param shell
	 */
	public RenameSelectionListener(TreeViewer treeViewer, TableViewer tableViewer, 
			List<String> history, Shell shell) {

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
		
		int index = tableViewer.getTable().getSelectionIndex();
		if (index < 0) {
			MessageDialog.openError(shell, "error", "no selection has been made");
			return;
		}
		String item = tableViewer.getTable().getItem(index).toString();
		item = item.substring(item.indexOf('{') + 1, item.indexOf('}'));
		
		RenameDialog renameDialog = new RenameDialog(shell);
		renameDialog.setRename(item);
		renameDialog.create();
		if (renameDialog.open() == Window.OK) {
			File dest = new File(path + "\\" + renameDialog.getRename());
			if (renameDialog.getRename().isEmpty()) {
				MessageDialog.openError(shell, "Error", "Empty name");
				return;
			}
			if (dest.isDirectory() && dest.exists()) {
				boolean replace = MessageDialog.openConfirm(shell, "Warning" , 
						"There is a folder with the name: " + dest.getName() +  
						"  in this location." + " Do you wish to merge into the existing one?");
				if (!replace) {
					return;
				}
			}
			try {
				FileService.move(new File(path + "\\" + item), dest, shell);
			} catch (IOException e1) {
				MessageDialog.openError(shell, "error", "access denied");
			} catch (InvalidPathException e2) {
				MessageDialog.openError(shell, "error", "invalid name");
			}
		}
		treeViewer.refresh();
		tableViewer.setInput(new File(history.get(history.size() - 1)).listFiles());
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
	}
	
	
}
