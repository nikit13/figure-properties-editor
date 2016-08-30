package ru.spb.nkurasov.figure.editor.ui.view;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import ru.spb.nkurasov.figure.editor.BooleanProperty;
import ru.spb.nkurasov.figure.editor.FigureProperty;
import ru.spb.nkurasov.figure.editor.FigurePropertyVisitor;
import ru.spb.nkurasov.figure.editor.IntegerProperty;
import ru.spb.nkurasov.figure.editor.StringProperty;

public class FigurePropertyEditingSupport extends EditingSupport {

    public FigurePropertyEditingSupport(ColumnViewer viewer) {
        super(viewer);
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        if (element instanceof FigureProperty) {
            CellEditorCreator cellEditorCreator = new CellEditorCreator((Composite) getViewer().getControl());
            ((FigureProperty) element).accept(cellEditorCreator);
            return cellEditorCreator.getCellEditor();
        }
        return null;
    }

    @Override
    protected boolean canEdit(Object element) {
        return element instanceof FigureProperty;
    }

    @Override
    protected Object getValue(Object element) {
        if (element instanceof FigureProperty) {
            FigureProperty property = (FigureProperty) element;
            FigurePropertyValueGetter valueGetter = new FigurePropertyValueGetter();
            property.accept(valueGetter);
            Object propertyValue = valueGetter.getPropertyValue();
            return propertyValue;
        }
        return null;
    }

    @Override
    protected void setValue(Object element, Object value) {
        if (element instanceof FigureProperty) {
            FigureProperty property = (FigureProperty) element;
            FigurePropertyValueSetter valueSetter = new FigurePropertyValueSetter(value);
            property.accept(valueSetter);

            getViewer().refresh(true);
        }
    }

    private static class CellEditorCreator implements FigurePropertyVisitor {

        private final Composite parent;

        private CellEditor cellEditor;

        public CellEditorCreator(Composite parent) {
            this.parent = parent;
        }

        @Override
        public void visit(BooleanProperty property) {
            CheckboxCellEditor editor = new CheckboxCellEditor(parent);
            editor.setValidator(value -> value != null ? null : "value must be specified");
            cellEditor = editor;
        }

        @Override
        public void visit(IntegerProperty property) {
            TextCellEditor editor = new TextCellEditor(parent);
            editor.setValidator(this::validateIntegerValue);
            cellEditor = editor;
        }

        private String validateIntegerValue(Object value) {
            if (value instanceof String) {
                try {
                    Integer.valueOf(value.toString());
                    return null;
                } catch (NumberFormatException e) {
                    return "value is not a number";
                }
            } else {
                return "null value";
            }
        }

        @Override
        public void visit(StringProperty property) {
            cellEditor = new TextCellEditor(parent);
        }

        public CellEditor getCellEditor() {
            return cellEditor;
        }
    }

    private static class FigurePropertyValueGetter implements FigurePropertyVisitor {

        private Object propertyValue;

        @Override
        public void visit(BooleanProperty property) {
            property.getValue().ifPresent(this::setBooleanValue);
        }

        private void setBooleanValue(Boolean value) {
            propertyValue = value;
        }

        @Override
        public void visit(StringProperty property) {
            property.getValue().ifPresent(this::setStringValue);
        }

        private void setStringValue(String value) {
            propertyValue = value;
        }

        @Override
        public void visit(IntegerProperty property) {
            property.getValue().ifPresent(this::setIntegerValue);
        }

        private void setIntegerValue(Integer value) {
            propertyValue = value.toString();
        }

        public Object getPropertyValue() {
            return propertyValue;
        }
    }
}
