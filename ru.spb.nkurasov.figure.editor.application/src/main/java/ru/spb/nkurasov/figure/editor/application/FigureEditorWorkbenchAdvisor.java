package ru.spb.nkurasov.figure.editor.application;

import org.eclipse.ui.application.WorkbenchAdvisor;

import ru.spb.nkurasov.figure.editor.ui.view.FigureEditorPerspectiveFactory;

public class FigureEditorWorkbenchAdvisor extends WorkbenchAdvisor {

    @Override
    public String getInitialWindowPerspectiveId() {
        return FigureEditorPerspectiveFactory.ID;
    }
}
