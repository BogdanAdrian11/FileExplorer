package com.example.e4.rcp.fileexplorer.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.fileexplorer.services.ThreadService;

public class DeleteSelectionListener implements SelectionListener{

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
	public DeleteSelectionListener(TreeViewer treeViewer, TableViewer tableViewer,
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
		
		int indices[] = tableViewer.getTable().getSelectionIndices();
		if (indices.length == 0) {
			MessageDialog.openWarning(shell, "Warning", "No selections made");
			return;
		}
		ArrayList<String> itemList = new ArrayList<String>();
		for (int index : indices) {
			String item = tableViewer.getTable().getItem(index).toString();
			item = item.substring(item.indexOf('{') + 1, item.indexOf('}'));
			itemList.add(item);
		}
		boolean confirm = MessageDialog.openQuestion(shell, "Delete",
				"Are you sure you want to delete " + itemList.toString() + " ?");
		if (!confirm)
			return;
		try {
			for (String item : itemList) {
				File file = new File(path + "\\" + item);
				if (file.isDirectory()) {
					deleteFolder(file);
				} else {
					file.delete();
				}
				treeViewer.refresh();
				tableViewer.setInput(new File(history.get(history.size() - 1)).listFiles());
			}
		} catch (Exception e1) {
			MessageDialog.openError(shell, "error", "access denied");
		}
	}
	
	private void deleteFolder(File folder) {
		File[] files = folder.listFiles();
	    if(files!=null) {
	        for(File f: files) {
	            if(f.isDirectory()) {
	                deleteFolder(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	    folder.delete();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
	}

}
