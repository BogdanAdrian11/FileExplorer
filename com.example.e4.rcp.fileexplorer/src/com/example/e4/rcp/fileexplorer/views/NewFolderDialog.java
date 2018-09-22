package com.example.e4.rcp.fileexplorer.views;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewFolderDialog extends TitleAreaDialog{
	
	private Text folderNameText;
	private String folderName;
	
	public NewFolderDialog(Shell parentShell) {
		super(parentShell);
	}
	@Override
    public void create() {
        super.create();
        setTitle("Create new folder");
    }
	
	@Override
    protected Control createDialogArea(Composite parent) {
        Composite area = (Composite) super.createDialogArea(parent);
        Composite container = new Composite(area, SWT.NONE);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);

        createFolderName(container);

        return area;
    }
	private void createFolderName(Composite container) {
		Label lbtFirstName = new Label(container, SWT.NONE);
        lbtFirstName.setText("Folder name:");

        GridData dataFirstName = new GridData();
        dataFirstName.grabExcessHorizontalSpace = true;
        dataFirstName.horizontalAlignment = GridData.FILL;

        folderNameText = new Text(container, SWT.BORDER);
        folderNameText.setLayoutData(dataFirstName);
    }
   
    @Override
    protected boolean isResizable() {
        return true;
    }
    
    private void saveInput() {
    	folderName = folderNameText.getText();
    }
    
    @Override
    protected void okPressed() {
        saveInput();
        super.okPressed();
    }

    public String getFolderName() {
        return folderName;
    }

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
}
