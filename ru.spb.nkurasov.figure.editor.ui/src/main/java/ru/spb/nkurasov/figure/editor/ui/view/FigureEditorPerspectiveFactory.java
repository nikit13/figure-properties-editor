package ru.spb.nkurasov.figure.editor.ui.view;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * Отвечает за состав и расположение окон в редакторе свойств фигур
 * 
 * @author nkurasov
 *
 */
public class FigureEditorPerspectiveFactory implements IPerspectiveFactory {

    public static final String ID = "ru.spb.nkurasov.figure.editor.ui.perspective";

    @Override
    public void createInitialLayout(IPageLayout layout) {
        layout.setEditorAreaVisible(false);

        IFolderLayout leftFolder = layout.createFolder("left", IPageLayout.LEFT, 0.5f, layout.getEditorArea());
        leftFolder.addView(FiguresView.ID);
        IFolderLayout rightFolder = layout.createFolder("right", IPageLayout.RIGHT, 0.5f, layout.getEditorArea());
        rightFolder.addView(FigurePropertiesView.ID);
    }
}
