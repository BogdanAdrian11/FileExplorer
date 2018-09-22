package com.example.e4.rcp.fileexplorer.views;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.fileexplorer.models.FileProperties;
import com.example.e4.rcp.fileexplorer.services.FileSizeService;

public class PropertiesTable implements Runnable{
	
	private File file;
	private String fileName;
	
	public PropertiesTable(File file, String fileName) {
		this.file = file;
		this.fileName = fileName;
	}
	
	public void createPropertiesTable() {
		
		Display display = new Display();
		Shell shell = new Shell(display);
		
		shell.setSize(500, 300);
		shell.setLayout(new FillLayout());
		shell.setText(fileName);
		
		BasicFileAttributes attr;
		try {
			attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
		} catch (IOException e1) {
			MessageDialog.openError(shell, "error", "Access denied");
			return;
		}
		
		Long size = Long.valueOf(0);
		FileSizeService fileSizeService = new FileSizeService(file, size);
		fileSizeService.setName(file.toString());
		if (attr.isDirectory()) {
			fileSizeService.start();
		} else {
			size = attr.size();
		}

		
		TableViewer propertiesViewer = new TableViewer(shell, 
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		propertiesViewer.setContentProvider(ArrayContentProvider.getInstance());
		propertiesViewer.getTable().setLayoutData(new FillLayout());
		propertiesViewer.getTable().setHeaderVisible(true);
		
		TableViewerColumn labelColumn = new TableViewerColumn(propertiesViewer, SWT.NONE);
		labelColumn.getColumn().setText("Label");
		labelColumn.getColumn().setWidth(150);
		labelColumn.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				FileProperties prop = (FileProperties) element;
				return prop.getLabel();
			}
			
		});
		
		TableViewerColumn infoColumn = new TableViewerColumn(propertiesViewer, SWT.NONE);
		infoColumn.getColumn().setText("Info");
		infoColumn.getColumn().setWidth(340);
		infoColumn.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				FileProperties prop = (FileProperties) element;
				return prop.getInfo();
			}
			
		});
		
		List<FileProperties> properties = new ArrayList<FileProperties>();
		properties.add(
				new FileProperties("creationTime", attr.creationTime().toString()));
		properties.add(
				new FileProperties("lastAccessTime", attr.lastAccessTime().toString()));
		properties.add(
				new FileProperties("lastModifiedTime", attr.lastModifiedTime().toString()));
		properties.add(
				new FileProperties("isDirectory", String.valueOf(attr.isDirectory())));
		properties.add(
				new FileProperties("isOther", String.valueOf(attr.isOther())));
		properties.add(
				new FileProperties("isRegularFile", String.valueOf(attr.isRegularFile())));
		properties.add(
				new FileProperties("isSymbolicLink", String.valueOf(attr.isSymbolicLink())));
		
		FileProperties itemProp = new FileProperties("size", size.toString());
		properties.add(itemProp);		

		propertiesViewer.setInput(properties);
		shell.open ();
		
		if (attr.isDirectory()) {
			while (fileSizeService.isAlive()) {
				itemProp.setInfo(fileSizeService.getlSize().toString());
				propertiesViewer.refresh();
				shell.update();
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			itemProp.setInfo(fileSizeService.getlSize().toString());
			propertiesViewer.refresh();
			shell.update();
		}
		
	    while (!shell.isDisposed()) {
	        if (!display.readAndDispatch())
	          display.sleep();
	      }
	    display.dispose();
	}

	@Override
	public void run() {
		createPropertiesTable();
	}
}