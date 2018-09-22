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

public class RenameDialog extends TitleAreaDialog{
	
	private Text renameText;
	private String rename;
	
	public RenameDialog(Shell parentShell) {
		super(parentShell);
	}
	@Override
    public void create() {
        super.create();
        setTitle("Rename");
    }
	
	@Override
    protected Control createDialogArea(Composite parent) {
        Composite area = (Composite) super.createDialogArea(parent);
        Composite container = new Composite(area, SWT.NONE);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);

        rename(container);

        return area;
    }
	private void rename(Composite container) {
		Label lbtFirstName = new Label(container, SWT.NONE);
        lbtFirstName.setText("Name:");

        GridData dataFirstName = new GridData();
        dataFirstName.grabExcessHorizontalSpace = true;
        dataFirstName.horizontalAlignment = GridData.FILL;

        renameText = new Text(container, SWT.BORDER);
        renameText.setLayoutData(dataFirstName);
        renameText.setText(rename);
    }
   
    @Override
    protected boolean isResizable() {
        return true;
    }
    
    private void saveInput() {
    	rename = renameText.getText();
    }
    
    @Override
    protected void okPressed() {
        saveInput();
        super.okPressed();
    }

    public String getRename() {
        return rename;
    }

	public void setRename(String rename) {
		this.rename = rename;
	}

}
