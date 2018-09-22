package com.example.e4.rcp.fileexplorer.listeners;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TreeItem;

public class TreeSelectionListener extends SelectionAdapter {
	
	private TreeViewer treeViewer;
	
	  /**
	 * @param master
	 */
	public TreeSelectionListener(TreeViewer master) {
		super();
		this.treeViewer = master;
	}

	@Override
  public void widgetSelected(SelectionEvent e) {
      TreeItem item = (TreeItem) e.item;
        if (item.getItemCount() > 0) {
            item.setExpanded(!item.getExpanded());
            treeViewer.refresh();
        }
    }
}
