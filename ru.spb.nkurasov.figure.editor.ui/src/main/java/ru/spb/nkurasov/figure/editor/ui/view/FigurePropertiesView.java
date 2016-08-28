package ru.spb.nkurasov.figure.editor.ui.view;

import java.util.Collections;

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

import ru.spb.nkurasov.figure.editor.FigureProperty;
import ru.spb.nkurasov.figure.editor.service.FigureActivationChangedListener;
import ru.spb.nkurasov.figure.editor.service.FigureService;

public class FigurePropertiesView extends ViewPart {

    public static final String ID = "ru.spb.nkurasov.figure.editor.ui.view.properties";

    private final WritableList<FigureProperty> selectedFigureProperties = WritableList.withElementType(FigureProperty.class);

    private final FigureActivationChangedListener activationListener = f -> {
        selectedFigureProperties.clear();
        selectedFigureProperties.addAll(f.isEmpty() ? Collections.emptyList() : f.get(0).getProperties());
    };

    private FigureService figureService;

    @Override
    public void init(IViewSite site) throws PartInitException {
        super.init(site);

        figureService = (FigureService) site.getService(FigureService.class);
        figureService.addFigureActivationChangedListener(activationListener);
    }

    @Override
    public void createPartControl(Composite parent) {
        TableViewer propertiesTable = new TableViewer(parent);
        propertiesTable.getTable().setLinesVisible(true);
        propertiesTable.getTable().setHeaderVisible(true);
        propertiesTable.setContentProvider(new ObservableListContentProvider<>(FigureProperty.class));

        TableViewerColumn propertyNameColumn = new TableViewerColumn(propertiesTable, SWT.LEFT);
        propertyNameColumn.getColumn().setText("Name");
        propertyNameColumn.getColumn().setToolTipText("Property Name");
        propertyNameColumn.getColumn().setWidth(30);
        propertyNameColumn.setLabelProvider(new PropertyNameLabelProvider());

        propertiesTable.setInput(selectedFigureProperties);
    }

    @Override
    public void dispose() {
        super.dispose();

        figureService.removeFigureActivationChangedListener(activationListener);
    }

    @Override
    public void setFocus() {
        // do nothing
    }

    private static class PropertyNameLabelProvider extends ColumnLabelProvider {

        @Override
        public String getText(Object element) {
            if (element instanceof FigureProperty) {
                return ((FigureProperty) element).getName();
            }
            return super.getText(element);
        }
    }
}
