package ru.spb.nkurasov.figure.editor.ui.view;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import ru.spb.nkurasov.figure.editor.Figure;
import ru.spb.nkurasov.figure.editor.service.AddFigureListener;
import ru.spb.nkurasov.figure.editor.service.FigureService;
import ru.spb.nkurasov.figure.editor.service.RemoveFigureListener;

public class FiguresView extends ViewPart {

    public static final String ID = "ru.spb.nkurasov.figure.editor.ui.view.figures";

    private final WritableList<Figure> figures = WritableList.withElementType(Figure.class);

    private final AddFigureListener addFigureListener = f -> figures.add(f);

    private final RemoveFigureListener removeFigureListener = f -> figures.remove(f);

    @Override
    public void init(IViewSite site) throws PartInitException {
        super.init(site);

        FigureService figureService = (FigureService) site.getService(FigureService.class);
        figureService.addFigureAddedListener(addFigureListener);
        figureService.addFigureRemovedListener(removeFigureListener);
    }

    @Override
    public void createPartControl(Composite parent) {
        TableViewer figuresTable = new TableViewer(parent);
        figuresTable.setContentProvider(new ObservableListContentProvider<Figure>(Figure.class));
        figuresTable.getTable().setHeaderVisible(true);
        figuresTable.getTable().setLinesVisible(true);

        TableViewerColumn figureNameColumn = new TableViewerColumn(figuresTable, SWT.LEFT);
        figureNameColumn.getColumn().setText("Name");
        figureNameColumn.getColumn().setToolTipText("Figure Name");
        figureNameColumn.getColumn().setWidth(100);
        figureNameColumn.getColumn().setMoveable(false);
        figureNameColumn.setLabelProvider(new FigureNameLabelProvider());

        TableViewerColumn figureTypeColumn = new TableViewerColumn(figuresTable, SWT.LEFT);
        figureTypeColumn.getColumn().setText("Type");
        figureTypeColumn.getColumn().setToolTipText("Figure Type");
        figureTypeColumn.getColumn().setWidth(100);
        figureTypeColumn.getColumn().setMoveable(false);
        figureTypeColumn.setLabelProvider(new FigureTypeLabelProvider());

        figuresTable.setInput(figures);
    }

    @Override
    public void setFocus() {
        // do nothing
    }

    @Override
    public void dispose() {
        super.dispose();

        FigureService figureService = (FigureService) getViewSite().getService(FigureService.class);
        figureService.removeFigureAddedListener(addFigureListener);
        figureService.removeFigureRemovedListener(removeFigureListener);
    }

    private static class FigureNameLabelProvider extends ColumnLabelProvider {

        @Override
        public String getText(Object element) {
            if (element instanceof Figure) {
                return ((Figure) element).getName();
            }
            return super.getText(element);
        }
    }

    private static class FigureTypeLabelProvider extends ColumnLabelProvider {

        @Override
        public String getText(Object element) {
            if (element instanceof Figure) {
                return ((Figure) element).getType().getName();
            }
            return super.getText(element);
        }
    }
}
