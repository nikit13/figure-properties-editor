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

import ru.spb.nkurasov.figure.BooleanPropertyType;
import ru.spb.nkurasov.figure.FigurePropertyTypeVisitor;
import ru.spb.nkurasov.figure.GroupPropertyType;
import ru.spb.nkurasov.figure.IntegerPropertyType;
import ru.spb.nkurasov.figure.StringPropertyType;
import ru.spb.nkurasov.figure.editor.BooleanProperty;
import ru.spb.nkurasov.figure.editor.FigureProperty;
import ru.spb.nkurasov.figure.editor.IntegerProperty;
import ru.spb.nkurasov.figure.editor.StringProperty;
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
        propertyNameColumn.getColumn().setWidth(100);
        propertyNameColumn.setLabelProvider(new PropertyNameLabelProvider());

        TableViewerColumn propertyValueColumn = new TableViewerColumn(propertiesTable, SWT.LEFT);
        propertyValueColumn.getColumn().setText("Value");
        propertyValueColumn.getColumn().setToolTipText("Property Value");
        propertyValueColumn.getColumn().setWidth(100);
        propertyValueColumn.setLabelProvider(new PropertyValueLabelProvider());

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

    private static class PropertyValueLabelProvider extends ColumnLabelProvider {

        @Override
        public String getText(Object element) {
            if (element instanceof FigureProperty) {
                FigureProperty property = (FigureProperty) element;
                FigurePropertyValueExtractor valueExtractor = new FigurePropertyValueExtractor(property);
                property.getType().accept(valueExtractor);
                return valueExtractor.getPropertyValue();
            }
            return super.getText(element);
        }
    }

    private static class FigurePropertyValueExtractor implements FigurePropertyTypeVisitor {

        private final FigureProperty property;

        private String propertyValue;

        FigurePropertyValueExtractor(FigureProperty property) {
            this.property = property;
        }

        @Override
        public void visit(BooleanPropertyType propertyType) {
            ((BooleanProperty) property).getValue().ifPresent(this::setBooleanValue);
        }

        private void setBooleanValue(Boolean value) {
            propertyValue = value.toString();
        }

        @Override
        public void visit(StringPropertyType propertyType) {
            ((StringProperty) property).getValue().ifPresent(this::setStringValue);
        }

        private void setStringValue(String value) {
            propertyValue = value;
        }

        @Override
        public void visit(IntegerPropertyType propertyType) {
            ((IntegerProperty) property).getValue().ifPresent(this::setIntegerValue);
        }

        private void setIntegerValue(Integer value) {
            propertyValue = value.toString();
        }

        @Override
        public void visit(GroupPropertyType propertyType) {
            // FIXME
            throw new UnsupportedOperationException();
        }

        public String getPropertyValue() {
            return propertyValue;
        }
    }
}
