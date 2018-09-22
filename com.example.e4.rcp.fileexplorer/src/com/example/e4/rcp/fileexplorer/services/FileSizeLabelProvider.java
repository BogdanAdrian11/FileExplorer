package com.example.e4.rcp.fileexplorer.services;

import java.io.File;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;

public class FileSizeLabelProvider extends LabelProvider implements IStyledLabelProvider {

    @Override
    public StyledString getStyledText(Object element) {
        if (element instanceof File) {
            File file = (File) element;
            if (file.isDirectory()) {
                return new StyledString("");
            }
            return new StyledString(String.valueOf(file.length()));
        }
        return null;
    }
}
