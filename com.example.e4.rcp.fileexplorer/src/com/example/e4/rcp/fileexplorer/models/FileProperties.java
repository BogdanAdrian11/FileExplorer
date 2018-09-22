package com.example.e4.rcp.fileexplorer.models;

public class FileProperties {
	
	private String label;
	private String info;
	    
	/**
	 * @param label
	 * @param info
	 */
	public FileProperties(String label, String info) {
		super();
		this.label = label;
		this.info = info;
	}
	
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}
}
