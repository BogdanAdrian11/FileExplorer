package com.example.e4.rcp.fileexplorer.listeners;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;

public class MouseDownListener implements Listener{

	private TableViewer tableViewer;
	private MenuItem copyItem;
	private MenuItem cutItem;
	private MenuItem renameItem;
	private MenuItem deleteItem;
	
	/**
	 * @param tableViewer
	 * @param copyItem
	 * @param cutItem
	 * @param renameItem
	 * @param deleteItem
	 */
	public MouseDownListener(TableViewer tableViewer, MenuItem copyItem, MenuItem cutItem,
			MenuItem renameItem, MenuItem deleteItem) {

		this.tableViewer = tableViewer;
		this.copyItem = copyItem;
		this.cutItem = cutItem;
		this.renameItem = renameItem;
		this.deleteItem = deleteItem;
	}


	@Override
	public void handleEvent(Event event) {
		Point point = new Point(event.x, event.y);
		if (tableViewer.getTable().getItem(point) == null) {
			deleteItem.setEnabled(false);
			copyItem.setEnabled(false);
			cutItem.setEnabled(false);
			renameItem.setEnabled(false);
			tableViewer.getTable().deselectAll();
		}
		else {
			deleteItem.setEnabled(true);
			copyItem.setEnabled(true);
			cutItem.setEnabled(true);
			renameItem.setEnabled(true);
		}
	}
}
