package com.example.e4.rcp.fileexplorer.listeners;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.example.e4.rcp.fileexplorer.views.PropertiesTable;

public class PropertiesSelectionListener extends SelectionAdapter{

	private TableViewer tableViewer;
	private List<String> history;
	
	/**
	 * @param tableViewer
	 * @param history
	 * @param shell
	 */
	public PropertiesSelectionListener(TableViewer tableViewer, List<String> history) {
		
		this.tableViewer = tableViewer;
		this.history = history;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		String item = null;
		int indices[] = tableViewer.getTable().getSelectionIndices();
		
		String path = "";
		Path file;
		if (history.size() > 0) {
			path = history.get(history.size() - 1);
		}
		if (indices.length == 0) {
			file = Paths.get(path);
			showDialog(file);
		} else {
			for (int index : indices) {
				item = tableViewer.getTable().getItem(index).toString();
				item = item.substring(item.indexOf('{') + 1, item.indexOf('}'));
				file = Paths.get(path + "\\" + item);
				showDialog(file);
			}
		}
	}
	
	private void showDialog(Path file) {
		String fileName = ((file.toFile().getName() != null && !file.toFile().getName().isEmpty()) ? 
				file.toFile().getName() : history.get(history.size() - 1));
		PropertiesTable propertiesTable = new PropertiesTable(file.toFile(), fileName);
		Thread propertiesThread = new Thread(propertiesTable);
		propertiesThread.setName("propertiesThread");
		propertiesThread.start();
	}
}
