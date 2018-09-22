
package com.example.e4.rcp.fileexplorer.parts;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.util.List;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.example.e4.rcp.fileexplorer.listeners.ComboSelectionListener;
import com.example.e4.rcp.fileexplorer.listeners.CopySelectionListener;
import com.example.e4.rcp.fileexplorer.listeners.CutSelectionListener;
import com.example.e4.rcp.fileexplorer.listeners.DeleteSelectionListener;
import com.example.e4.rcp.fileexplorer.listeners.DoubleClickListener;
import com.example.e4.rcp.fileexplorer.listeners.EnterKeyListener;
import com.example.e4.rcp.fileexplorer.listeners.MouseDownListener;
import com.example.e4.rcp.fileexplorer.listeners.NewFolderSelectionListener;
import com.example.e4.rcp.fileexplorer.listeners.PasteSelectionListener;
import com.example.e4.rcp.fileexplorer.listeners.PropertiesSelectionListener;
import com.example.e4.rcp.fileexplorer.listeners.RefreshSelectionListener;
import com.example.e4.rcp.fileexplorer.listeners.RenameSelectionListener;
import com.example.e4.rcp.fileexplorer.listeners.TreeSelectionListener;
import com.example.e4.rcp.fileexplorer.listeners.UpSelectionListener;
import com.example.e4.rcp.fileexplorer.services.FileModifiedLabelProvider;
import com.example.e4.rcp.fileexplorer.services.FileSizeLabelProvider;
import com.example.e4.rcp.fileexplorer.services.ViewContentProvider;
import com.example.e4.rcp.fileexplorer.services.ViewLabelProvider;

public class FileExplorerPart {
	
	private TableViewer tableViewer;
	private TreeViewer treeViewer;
	private CCombo pathCombo;
	private Button upButton;
	private List<String> history;
	private Button newFolder;
	private Button refresh;
	private MenuItem deleteItem;
	private MenuItem copyItem;
	private MenuItem cutItem;
	private MenuItem pasteItem;
	private MenuItem renameItem;
	private List<String> source;
		
	private static final DateFormat dateFormat = DateFormat.getDateInstance();
		
	@PostConstruct
	public void postConstruct(Composite parent) {
		parent.setLayout(new GridLayout(5, false));
		
		history = new ArrayList<String>();
    	source = new ArrayList<String>();
		
		pathCombo = new CCombo(parent, SWT.BORDER);
		pathCombo.setText("D:\\");
		pathCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		history.add(pathCombo.getText().toString());
		
		refresh = new Button(parent, SWT.PUSH);
		refresh.setToolTipText("refresh");
		refresh.setImage(createImageDescriptor("icons/refresh.png").createImage());
		
		upButton = new Button(parent, SWT.PUSH);
		upButton.setToolTipText("up");
		upButton.setImage(createImageDescriptor("icons/up.png").createImage());
		
		newFolder = new Button(parent, SWT.PUSH);
		newFolder.setToolTipText("create new folder");
		newFolder.setImage(createImageDescriptor("icons/folder.png").createImage());

		treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		treeViewer.setContentProvider(new ViewContentProvider());
		treeViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(
                new ViewLabelProvider(createImageDescriptor("icons/folder.png"),
                		createImageDescriptor("icons/file.png"))));
		treeViewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		treeViewer.setInput(File.listRoots());
		
		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tableViewer.setContentProvider(
				new ViewContentProvider());
		tableViewer.getTable().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		tableViewer.getTable().setHeaderVisible(true);
		
		TableViewerColumn mainColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		mainColumn.getColumn().setText("Name");
		mainColumn.getColumn().setWidth(300);
		mainColumn.setLabelProvider(
				new DelegatingStyledCellLabelProvider(
						new ViewLabelProvider(createImageDescriptor("icons/folder.png"),
								createImageDescriptor("icons/file.png"))));
		
		TableViewerColumn modifiedColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		modifiedColumn.getColumn().setText("Last modified");
		modifiedColumn.getColumn().setWidth(100);
		modifiedColumn.getColumn().setAlignment(SWT.RIGHT);
		modifiedColumn.setLabelProvider(
				new DelegatingStyledCellLabelProvider(
						new FileModifiedLabelProvider(dateFormat)));
		
		TableViewerColumn fileSizeColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		fileSizeColumn.getColumn().setText("Size");
		fileSizeColumn.getColumn().setWidth(100);
		fileSizeColumn.getColumn().setAlignment(SWT.RIGHT);
		fileSizeColumn.setLabelProvider(
				new DelegatingStyledCellLabelProvider(
						new FileSizeLabelProvider()));
		
		tableViewer.setInput(new File("D:\\").listFiles());

		createContextMenu(treeViewer, tableViewer, history, parent.getShell());
		
		pathCombo.addKeyListener(
				new EnterKeyListener(pathCombo, tableViewer, history, parent.getShell()));
		pathCombo.addSelectionListener(
				new ComboSelectionListener(pathCombo, tableViewer, history, parent.getShell()));
		
		refresh.addSelectionListener(
				new RefreshSelectionListener(pathCombo, treeViewer, tableViewer, history));
		upButton.addSelectionListener(
				new UpSelectionListener(pathCombo, tableViewer, history));
		newFolder.addSelectionListener(
				new NewFolderSelectionListener(treeViewer, tableViewer, history, parent.getShell()));
		
		treeViewer.getTree().addSelectionListener(new TreeSelectionListener(treeViewer));
		treeViewer.addDoubleClickListener(
				new DoubleClickListener(tableViewer, pathCombo, history, parent.getShell()));
		
		tableViewer.addDoubleClickListener(new DoubleClickListener(tableViewer, pathCombo,
				history, parent.getShell()));
		tableViewer.getTable().addListener(SWT.MouseDown, 
				new MouseDownListener(tableViewer, copyItem, cutItem, renameItem, deleteItem));
	}

    private ImageDescriptor createImageDescriptor(String path) {
        Bundle bundle = FrameworkUtil.getBundle(ViewLabelProvider.class);
        URL url = FileLocator.find(bundle, new Path(path), null);
        return ImageDescriptor.createFromURL(url);
    }
    
    public void createContextMenu(TreeViewer treeViewer, TableViewer tableViewer, 
    		List<String> history, Shell shell) {
    	Menu tableViewerMenu = new Menu(tableViewer.getTable());
    	
    	MenuItem refreshItem = new MenuItem(tableViewerMenu, SWT.PUSH);
    	refreshItem.setText("refresh");
    	refreshItem.setImage(createImageDescriptor("icons/refresh.png").createImage());
    	refreshItem.addSelectionListener(
    			new RefreshSelectionListener(pathCombo, treeViewer, tableViewer, history));
    	
    	MenuItem upItem = new MenuItem(tableViewerMenu, SWT.PUSH);
    	upItem.setText("go up");
    	upItem.setImage(createImageDescriptor("icons/up.png").createImage());
    	upItem.addSelectionListener(
    			new UpSelectionListener(pathCombo, tableViewer, history));
    	
    	Separator separator1 = new Separator();
    	separator1.fill(tableViewerMenu, 2);
    	
    	MenuItem newFolderItem = new MenuItem(tableViewerMenu, SWT.PUSH);
    	newFolderItem.setText("new folder");
    	newFolderItem.setImage(createImageDescriptor("icons/folder.png").createImage());
    	newFolderItem.addSelectionListener(
    			new NewFolderSelectionListener(treeViewer, tableViewer, history, shell));
    	
    	Separator separator2 = new Separator();
    	separator2.fill(tableViewerMenu, 4);

    	copyItem = new MenuItem(tableViewerMenu, SWT.PUSH);
    	copyItem.setText("copy");
    	copyItem.setImage(createImageDescriptor("icons/copy.png").createImage());
    	copyItem.setEnabled(false);
    	
    	cutItem = new MenuItem(tableViewerMenu, SWT.PUSH);
    	cutItem.setText("cut");
    	cutItem.setImage(createImageDescriptor("icons/cut.png").createImage());
    	cutItem.setEnabled(false);
    	
    	pasteItem = new MenuItem(tableViewerMenu, SWT.PUSH);
    	pasteItem.setText("paste");
    	pasteItem.setImage(createImageDescriptor("icons/paste.png").createImage());
    	pasteItem.setEnabled(false);
    	
    	Separator separator3 = new Separator();
    	separator3.fill(tableViewerMenu, 8);
    	
    	renameItem = new MenuItem(tableViewerMenu, SWT.PUSH);
    	renameItem.setText("rename");
    	renameItem.setImage(createImageDescriptor("icons/rename.png").createImage());
    	renameItem.setEnabled(false);
    	renameItem.addSelectionListener(
    			new RenameSelectionListener(treeViewer, tableViewer, history, shell));
    	
		deleteItem = new MenuItem(tableViewerMenu, SWT.PUSH);
		deleteItem.setText("delete");
		deleteItem.setImage(createImageDescriptor("icons/delete.png").createImage());
		deleteItem.setEnabled(false);
		deleteItem.addSelectionListener(
				new DeleteSelectionListener(treeViewer, tableViewer, history, shell));

    	copyItem.addSelectionListener(
    			new CopySelectionListener(tableViewer, history, shell, pasteItem, source));
    	cutItem.addSelectionListener(
    			new CutSelectionListener(tableViewer, history, shell, pasteItem, source));
    	pasteItem.addSelectionListener(
    			new PasteSelectionListener(treeViewer, tableViewer, history, shell, 
    					pasteItem, source));
		
    	Separator separator4 = new Separator();
    	separator4.fill(tableViewerMenu, 11);
    	
		MenuItem propertiesItem = new MenuItem(tableViewerMenu, SWT.PUSH);
		propertiesItem.setText("properties");
		propertiesItem.setImage(createImageDescriptor("icons/properties.png").createImage());
		propertiesItem.addSelectionListener(
				new PropertiesSelectionListener(tableViewer, history));
		
		tableViewer.getTable().setMenu(tableViewerMenu);
    }
	
    @Focus
    public void setFocus() {
        tableViewer.getControl().setFocus();
    }
}