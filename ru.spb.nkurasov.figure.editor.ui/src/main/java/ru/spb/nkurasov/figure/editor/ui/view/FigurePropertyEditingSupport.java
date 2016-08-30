package ru.spb.nkurasov.figure.editor.ui.view;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.LabelProvider;
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
            System.out.println(element);
            FigurePropertyValueGetter valueExtractor = new FigurePropertyValueGetter();
            ((FigureProperty) element).accept(valueExtractor);
            return valueExtractor.getPropertyValue();
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
            ComboBoxViewerCellEditor editor = new ComboBoxViewerCellEditor(parent);
            editor.setLabelProvider(new LabelProvider());
            editor.setContentProvider(ArrayContentProvider.getInstance());
            editor.setInput(new Boolean[] { true, false });
            editor.setValidator(new ICellEditorValidator() {

                @Override
                public String isValid(Object value) {
                    return value != null ? null : "Value must be specified";
                }
            });
            cellEditor = editor;
        }

        @Override
        public void visit(IntegerProperty property) {
            TextCellEditor editor = new TextCellEditor(parent);
            editor.setValidator(new ICellEditorValidator() {

                @Override
                public String isValid(Object value) {
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
            });
            cellEditor = editor;
        }

        @Override
        public void visit(StringProperty property) {
            cellEditor = new TextCellEditor(parent);
        }

        public CellEditor getCellEditor() {
            return cellEditor;
        }
    }
}
