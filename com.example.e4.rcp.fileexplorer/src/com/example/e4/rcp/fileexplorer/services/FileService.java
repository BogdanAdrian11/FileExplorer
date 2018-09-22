package com.example.e4.rcp.fileexplorer.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class FileService {
	
	public static void copy(File sourceLocation, File targetLocation, Shell shell) 
			throws IOException, InvalidPathException {
		
	    if (sourceLocation.isDirectory()) {
	        copyDirectory(sourceLocation, targetLocation, shell);
	    } else {
	        copyFile(sourceLocation, targetLocation, shell);
	    }
	}

	private static void copyDirectory(File source, File target, Shell shell) 
			throws IOException, InvalidPathException {
		
	    if (!target.exists()) {
	        target.mkdir();
	    }

	    for (String f : source.list()) {
	    	File dest = new File(target, f);
			if (!dest.isDirectory() && dest.exists()) {
				boolean replace = MessageDialog.openConfirm(shell, "Warning" ,
						"There is already a file with the name: " + f + " in this location."
						+ " Do you wish to replace the existing one?");
				if (!replace) {
					continue;
				}
			}
	        copy(new File(source, f), new File(target, f), shell);
	    }
	}

	private static void copyFile(File source, File target, Shell shell) 
			throws IOException, InvalidPathException { 
		
		if (!target.isDirectory() && target.exists()) {
			boolean replace = MessageDialog.openConfirm(shell, "Warning" ,
					"There is already a file with the name: " + target.getName() + 
					" in this location." + " Do you wish to replace the existing one?");
			if (!replace) {
				return;
			}
		}
		Files.copy(Paths.get(source.getPath()), 
				Paths.get(target.getPath()),
				StandardCopyOption.REPLACE_EXISTING,
				StandardCopyOption.COPY_ATTRIBUTES);	
	}
	
	public static void move(File sourceLocation, File targetLocation, Shell shell) 
			throws IOException, InvalidPathException {
		
	    if (sourceLocation.isDirectory()) {
	        moveDirectory(sourceLocation, targetLocation, shell);
	        sourceLocation.delete();
	    } else {
	        moveFile(sourceLocation, targetLocation, shell);
	    }
	}

	private static void moveDirectory(File source, File target, Shell shell) 
			throws IOException, InvalidPathException {
		
	    if (!target.exists()) {
	    	
	        target.mkdir();
	    }

	    for (String f : source.list()) {
	    	File dest = new File(target, f);
			if (!dest.isDirectory() && dest.exists()) {
				boolean replace = MessageDialog.openConfirm(shell, "Warning" ,
						"There is already a file with the name: " + f + " in this location."
						+ " Do you wish to replace the existing one?");
				if (!replace) {
					continue;
				}
			}
	        move(new File(source, f), new File(target, f), shell);
	    }
	}

	private static void moveFile(File source, File target, Shell shell) 
			throws IOException, InvalidPathException { 
		
		if (!target.isDirectory() && target.exists()) {
			boolean replace = MessageDialog.openConfirm(shell, "Warning" ,
					"There is already a file with the name: " + target.getName() + 
					" in this location." + " Do you wish to replace the existing one?");
			if (!replace) {
				return;
			}
		}
		Files.move(Paths.get(source.getPath()), 
				Paths.get(target.getPath()),
				StandardCopyOption.REPLACE_EXISTING);	
	}
}
