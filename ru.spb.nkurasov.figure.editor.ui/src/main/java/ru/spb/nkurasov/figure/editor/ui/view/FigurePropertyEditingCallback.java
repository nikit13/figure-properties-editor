package ru.spb.nkurasov.figure.editor.ui.view;

import org.eclipse.core.runtime.IStatus;

import ru.spb.nkurasov.figure.editor.FigureProperty;

@FunctionalInterface
public interface FigurePropertyEditingCallback {

    void onEditProperty(FigureProperty property, IStatus editingStatus);

}
