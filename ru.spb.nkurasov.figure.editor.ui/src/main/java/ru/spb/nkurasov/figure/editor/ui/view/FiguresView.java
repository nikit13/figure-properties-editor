package ru.spb.nkurasov.figure.editor.ui.view;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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

/**
 * Окно со списком фигур. Не сохраняет созданные фигуры между запусками
 * приложения
 * 
 * @author nkurasov
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
public class FiguresView extends ViewPart {

    public static final String ID = "ru.spb.nkurasov.figure.editor.ui.view.figures";

    private final WritableList figures = WritableList.withElementType(Figure.class);

    private final AddFigureListener addFigureListener = f -> figures.add(f);

    private final RemoveFigureListener removeFigureListener = f -> figures.removeAll(f);

    private FigureService figureService;

    @Override
    public void init(IViewSite site) throws PartInitException {
        super.init(site);

        figureService = (FigureService) site.getService(FigureService.class);
        figureService.addFigureAddedListener(addFigureListener);
        figureService.addFigureRemovedListener(removeFigureListener);
    }

    @Override
    public void createPartControl(Composite parent) {
        TableViewer figuresTable = new TableViewer(parent);
        figuresTable.setContentProvider(new ObservableListContentProvider());
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
        figuresTable.addSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                figureService.setSelectedFigures(selection.toList());
            }
        });
    }

    @Override
    public void setFocus() {
        // do nothing
    }

    @Override
    public void dispose() {
        super.dispose();

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
