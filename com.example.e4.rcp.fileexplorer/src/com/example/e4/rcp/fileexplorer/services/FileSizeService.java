package com.example.e4.rcp.fileexplorer.services;

import java.io.File;

public class FileSizeService extends Thread {

	private File directory;
	private Long lSize;
	
	/**
	 * @param directory
	 */
	public FileSizeService(File directory, Long lSize) {
		super();
		this.directory = directory;
		this.lSize = lSize;
	}

	private Long getDirectorySize(File dir) throws InterruptedException {

		File files[] = dir.listFiles();
		if (files != null) {
			for (File file : files) {
				if (Thread.interrupted()) {
					System.out.println(lSize);
					throw new InterruptedException();
				}
				if (!file.isDirectory()) {
					lSize += file.length();
				} else {
					getDirectorySize(file);
				}
			}
		}
		return lSize;
	}

	@Override
	public void run() {
		try {
			getDirectorySize(directory);
		} catch (InterruptedException e) {
			return;
		}
	}


	/**
	 * @return the lSize
	 */
	public Long getlSize() {
		return lSize;
	}


	/**
	 * @param lSize the lSize to set
	 */
	public void setlSize(Long lSize) {
		this.lSize = lSize;
	}
	
	
}
