package ru.spb.nkurasov.figure.editor.ui.view;

import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import ru.spb.nkurasov.figure.editor.BooleanProperty;
import ru.spb.nkurasov.figure.editor.FigureProperty;
import ru.spb.nkurasov.figure.editor.FigurePropertyVisitor;
import ru.spb.nkurasov.figure.editor.GroupProperty;
import ru.spb.nkurasov.figure.editor.GroupedFigureProperty;
import ru.spb.nkurasov.figure.editor.IntegerProperty;
import ru.spb.nkurasov.figure.editor.StringProperty;

/**
 * Поддержка редактирования свойств фигур
 * 
 * <ul>
 * <li>Для числовых и строковых свойств используются текстовые поля</li>
 * <li>Для свойств-флагов используются checkbox'ы</li>
 * </ul>
 * 
 * @author nkurasov
 *
 */
public class FigurePropertyEditingSupport extends EditingSupport {

    private final FigurePropertyEditingCallback errorHandler;

    public FigurePropertyEditingSupport(ColumnViewer viewer, FigurePropertyEditingCallback errorHandler) {
        super(viewer);

        if (errorHandler == null) {
            throw new IllegalArgumentException();
        }
        this.errorHandler = errorHandler;
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        if (element instanceof FigureProperty) {
            CellEditorBuilder cellEditorCreator = new CellEditorBuilder((Composite) getViewer().getControl(), errorHandler);
            ((FigureProperty) element).accept(cellEditorCreator);
            return cellEditorCreator.getCellEditor();
        }
        return null;
    }

    @Override
    protected boolean canEdit(Object element) {
        // свойства из группы можно редактировать только тогда, когда группа
        // доступна для редактирования
        if (element instanceof GroupedFigureProperty) {
            GroupedFigureProperty groupedProperty = (GroupedFigureProperty) element;
            return groupedProperty.getGroup().isEnabled();
        }

        // все остальные свойства можно редактировать
        return element instanceof FigureProperty;
    }

    @Override
    protected Object getValue(Object element) {
        if (element instanceof FigureProperty) {
            FigureProperty property = (FigureProperty) element;
            FigurePropertyValueGetter valueGetter = new FigurePropertyValueGetter();
            property.accept(valueGetter);
            return valueGetter.getPropertyValue();
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

    /**
     * Конструктор редакторов ячеек таблицы в зависимости от типа свойства
     * 
     * @author nkurasov
     *
     */
    private static class CellEditorBuilder implements FigurePropertyVisitor {

        private final Composite parent;

        private final FigurePropertyEditingCallback editingCallback;

        private CellEditor cellEditor;

        public CellEditorBuilder(Composite parent, FigurePropertyEditingCallback editingCallback) {
            this.parent = parent;
            this.editingCallback = editingCallback;
        }

        @Override
        public void visit(BooleanProperty property) {
            CheckboxCellEditor editor = new CheckboxCellEditor(parent);
            editor.setValidator(value -> value != null ? null : "value must be specified");
            editor.addListener(new ReportErrorMessagesCellEditorListener(editor, property, editingCallback));
            cellEditor = editor;
        }

        @Override
        public void visit(IntegerProperty property) {
            TextCellEditor editor = new TextCellEditor(parent);
            editor.setValidator(v -> validateIntegerValue(property, v));
            editor.addListener(new ReportErrorMessagesCellEditorListener(editor, property, editingCallback));
            cellEditor = editor;
        }

        private String validateIntegerValue(IntegerProperty property, Object value) {
            if (value instanceof String) {
                try {
                    final int parsed = Integer.valueOf(value.toString());
                    if (property.hasMinValue()) {
                        final int minValue = property.getMinValue();
                        if (parsed < minValue) {
                            return "value should be greater than or equal " + minValue;
                        }
                    }

                    if (property.hasMaxValue()) {
                        final int maxValue = property.getMaxValue();
                        if (parsed > maxValue) {
                            return "value should be less than or equal " + maxValue;
                        }
                    }

                    return null;
                } catch (NumberFormatException e) {
                    return "value " + value + " is not a number";
                }
            } else {
                return "null value";
            }
        }

        @Override
        public void visit(StringProperty property) {
            TextCellEditor editor = new TextCellEditor(parent);
            editor.addListener(new ReportErrorMessagesCellEditorListener(editor, property, editingCallback));
            cellEditor = editor;
        }

        public CellEditor getCellEditor() {
            return cellEditor;
        }

        @Override
        public void visit(GroupProperty property) {
            CheckboxCellEditor editor = new CheckboxCellEditor(parent);
            editor.setValidator(value -> value != null ? null : "value must be specified");
            editor.addListener(new ReportErrorMessagesCellEditorListener(editor, property, editingCallback));
            cellEditor = editor;
        }
    }

    private static class ReportErrorMessagesCellEditorListener implements ICellEditorListener {

        private final FigurePropertyEditingCallback editingCallback;

        private final CellEditor editor;

        private final FigureProperty property;

        public ReportErrorMessagesCellEditorListener(CellEditor editor, FigureProperty property, FigurePropertyEditingCallback editingCallback) {
            this.editor = editor;
            this.property = property;
            this.editingCallback = editingCallback;
        }

        @Override
        public void applyEditorValue() {
            if (editor.isValueValid()) {
                editingCallback.onEditProperty(property, ValidationStatus.ok());
            } else {
                editingCallback.onEditProperty(property, ValidationStatus.error(editor.getErrorMessage()));
            }
        }

        @Override
        public void cancelEditor() {
            editingCallback.onEditProperty(property, ValidationStatus.cancel("editing of property " + property.getName() + " cancelled"));
        }

        @Override
        public void editorValueChanged(boolean oldValidState, boolean newValidState) {
            // do nothing
        }
    }

    /**
     * Акцессор значения свойства.
     * 
     * <p>
     * Note: числовые свойства преобразуются в текст для отображения в текстовом
     * поле, и потом обратно в число
     * 
     * @author nkurasov
     *
     */
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
            propertyValue = property.getValue().orElse(0).toString();
        }

        public Object getPropertyValue() {
            return propertyValue;
        }

        @Override
        public void visit(GroupProperty property) {
            propertyValue = property.isEnabled();
        }
    }
}
