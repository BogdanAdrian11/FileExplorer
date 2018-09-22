package com.example.e4.rcp.fileexplorer.listeners;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Shell;
public class DoubleClickListener implements IDoubleClickListener{

	private TableViewer tableViewer;
	private CCombo pathCombo;
	private List<String> history;
	private Shell shell;
	
	/**
	 * @param tableViewer
	 */
	public DoubleClickListener(TableViewer tableViewer, CCombo pathCombo, 
			List<String> history, Shell shell) {
		super();
		this.tableViewer = tableViewer;
		this.pathCombo = pathCombo;
		this.history = history;
		this.shell = shell;
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
        IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
        Object selectedNode = thisSelection.getFirstElement();
        
        File file = new File(selectedNode.toString());
        if (!file.isDirectory()) {
            try {
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				MessageDialog.openError(shell, "error", "Access denied");
			}
        } else {
			if (history.size() > 0) { 
				pathCombo.add(history.get(history.size() - 1), 0);
			}
			tableViewer.setInput(new File(selectedNode.toString()).listFiles());
			history.add(selectedNode.toString());
        	pathCombo.setText(selectedNode.toString());
        }
	}

}
