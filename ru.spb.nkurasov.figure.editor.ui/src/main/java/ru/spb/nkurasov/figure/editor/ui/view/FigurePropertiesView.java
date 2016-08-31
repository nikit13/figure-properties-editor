package ru.spb.nkurasov.figure.editor.ui.view;

import java.util.Collections;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.IStatusLineManager;
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
import ru.spb.nkurasov.figure.editor.service.FigureSelectionChangedListener;
import ru.spb.nkurasov.figure.editor.service.FigureService;

/**
 * Окно отображения свойств выбранной фигуры. Если фигура не выбрана, окно будет
 * пустым
 * 
 * @author nkurasov
 *
 */
public class FigurePropertiesView extends ViewPart {

    public static final String ID = "ru.spb.nkurasov.figure.editor.ui.view.properties";

    private final WritableList<FigureProperty> selectedFigureProperties = WritableList.withElementType(FigureProperty.class);

    private final FigureSelectionChangedListener activationListener = f -> {
        selectedFigureProperties.clear();
        selectedFigureProperties.addAll(f.isEmpty() ? Collections.emptyList() : f.get(0).getFlatProperties());
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
        propertyNameColumn.getColumn().setWidth(100);
        propertyNameColumn.setLabelProvider(new PropertyNameLabelProvider());

        TableViewerColumn propertyValueColumn = new TableViewerColumn(propertiesTable, SWT.LEFT);
        propertyValueColumn.getColumn().setText("Value");
        propertyValueColumn.getColumn().setToolTipText("Property Value");
        propertyValueColumn.getColumn().setWidth(100);
        propertyValueColumn.setLabelProvider(new PropertyValueLabelProvider());

        propertyValueColumn.setEditingSupport(new FigurePropertyEditingSupport(propertiesTable, this::onEditProperty));

        propertiesTable.setInput(selectedFigureProperties);
    }

    private void onEditProperty(FigureProperty property, IStatus editingStatus) {
        IStatusLineManager statusLineManager = getStatusLineManager();
        statusLineManager.setErrorMessage(null);
        switch (editingStatus.getSeverity()) {
        case IStatus.OK:
            statusLineManager.setMessage(editingStatus.getMessage());
            break;
        case IStatus.CANCEL:
            statusLineManager.setMessage(editingStatus.getMessage());
            break;
        case IStatus.ERROR:
            statusLineManager.setErrorMessage(editingStatus.getMessage());
            break;
        default:
            break;
        }
    }

    private IStatusLineManager getStatusLineManager() {
        return getViewSite().getActionBars().getStatusLineManager();
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

    private static class PropertyValueLabelProvider extends ColumnLabelProvider {

        @Override
        public String getText(Object element) {
            if (element instanceof FigureProperty) {
                FigureProperty property = (FigureProperty) element;
                FigurePropertyStringValueGetter valueExtractor = new FigurePropertyStringValueGetter();
                property.accept(valueExtractor);
                return valueExtractor.getPropertyValue();
            }
            return super.getText(element);
        }
    }
}
