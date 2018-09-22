package com.example.e4.rcp.fileexplorer.listeners;

import java.io.File;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class RefreshSelectionListener implements SelectionListener{

	private CCombo pathCombo;
	private TreeViewer treeViewer;
	private TableViewer tableViewer;
	private List<String> history;
	
	/**
	 * @param pathCombo
	 * @param treeViewer
	 * @param tableViewer
	 * @param history
	 */
	public RefreshSelectionListener(CCombo pathCombo, TreeViewer treeViewer, 
			TableViewer tableViewer, List<String> history) {
		this.pathCombo = pathCombo;
		this.treeViewer = treeViewer;
		this.tableViewer = tableViewer;
		this.history = history;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {

		treeViewer.refresh();
		if (history.size() > 0) {
			String path = history.get(history.size() - 1);
			tableViewer.setInput(new File(path).listFiles());
			pathCombo.setText(path);
			history.add(history.get(history.size() - 1));
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
	}

}
