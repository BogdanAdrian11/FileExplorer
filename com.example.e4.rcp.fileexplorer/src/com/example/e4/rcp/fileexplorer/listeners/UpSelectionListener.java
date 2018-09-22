package com.example.e4.rcp.fileexplorer.listeners;

import java.io.File;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UpSelectionListener extends SelectionAdapter{
	
	private CCombo pathCombo;
	private TableViewer tableViewer;
	private List<String> history;
	
	/**
	 * @param pathCombo
	 * @param tableViewer
	 * @param history
	 */
	public UpSelectionListener(CCombo pathCombo, TableViewer tableViewer, List<String> history) {

		this.pathCombo = pathCombo;
		this.tableViewer = tableViewer;
		this.history = history;
	}

	/**
	 * Sent when selection occurs in the control.
	 * The default behavior is to do nothing.
	 *
	 * @param e an event containing information about the selection
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		if (history.size() > 0) {
			String path = history.get(history.size() - 1);
			File file = new File(path);
			String upPath = file.getParent();
			if (upPath != null) {
				pathCombo.add(path, 0);
				pathCombo.setText(upPath);
				history.add(upPath);
				tableViewer.setInput(new File(upPath).listFiles());
			}
		}
	}
}
