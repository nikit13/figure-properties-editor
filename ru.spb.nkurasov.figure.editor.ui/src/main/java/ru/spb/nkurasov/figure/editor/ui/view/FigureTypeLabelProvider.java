package ru.spb.nkurasov.figure.editor.ui.view;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import ru.spb.nkurasov.figure.editor.Figure;

public class FigureTypeLabelProvider extends ColumnLabelProvider {

    @Override
    public String getText(Object element) {
        if (element instanceof Figure) {
            return ((Figure) element).getType().getName();
        }
        return super.getText(element);
    }
}