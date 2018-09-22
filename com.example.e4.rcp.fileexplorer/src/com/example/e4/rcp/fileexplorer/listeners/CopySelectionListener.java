package com.example.e4.rcp.fileexplorer.listeners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

public class CopySelectionListener implements SelectionListener {

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
	public CopySelectionListener(
			TableViewer tableViewer, List<String> history, Shell shell, 
			MenuItem pasteItem, List<String> source) {
		
		this.tableViewer = tableViewer;
		this.history = history;
		this.shell = shell;
		this.pasteItem = pasteItem;
		this.source = source;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		source.clear();
		String path = "";
		if (history.size() > 0) {
			path = history.get(history.size() - 1);
		}
		int indices[] = tableViewer.getTable().getSelectionIndices();
		if (indices.length == 0) {
			MessageDialog.openWarning(shell, "Warning", "No selections made");
			return;
		}
		
		TableItem items[] = tableViewer.getTable().getItems();
		for (TableItem item : items) {
			item.setFont(JFaceResources.getFontRegistry().get(JFaceResources.DEFAULT_FONT));
		}
		
		ArrayList<String> itemList = new ArrayList<String>();
		for (int index : indices) {
			String item = tableViewer.getTable().getItem(index).toString();
			item = item.substring(item.indexOf('{') + 1, item.indexOf('}'));
			itemList.add(item);
		}
		for (String item : itemList) {
			source.add(path + "\\" + item);
		}
		pasteItem.setEnabled(true);
		pasteItem.setToolTipText("copy");
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {

	}

}
