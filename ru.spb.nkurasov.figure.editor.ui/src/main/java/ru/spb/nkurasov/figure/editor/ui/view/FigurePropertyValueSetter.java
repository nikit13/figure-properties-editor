package ru.spb.nkurasov.figure.editor.ui.view;

import ru.spb.nkurasov.figure.editor.BooleanProperty;
import ru.spb.nkurasov.figure.editor.FigurePropertyVisitor;
import ru.spb.nkurasov.figure.editor.GroupProperty;
import ru.spb.nkurasov.figure.editor.IntegerProperty;
import ru.spb.nkurasov.figure.editor.StringProperty;

/**
 * Мутатор для значения свойства
 * 
 * @author nkurasov
 *
 */
public class FigurePropertyValueSetter implements FigurePropertyVisitor {

    private final Object value;

    public FigurePropertyValueSetter(Object value) {
        this.value = value;
    }

    @Override
    public void visit(BooleanProperty property) {
        if (value == null || value instanceof Boolean) {
            property.setValue((Boolean) value);
        }
    }

    @Override
    public void visit(IntegerProperty property) {
        if (value == null) {
            property.setValue(null);
        } else if (value instanceof Integer) {
            property.setValue((Integer) value);
        } else if (value instanceof String) {
            property.setValue(Integer.valueOf(value.toString()));
        }
    }

    @Override
    public void visit(StringProperty property) {
        if (value == null) {
            property.setValue(null);
        } else {
            property.setValue(value.toString());
        }
    }

    @Override
    public void visit(GroupProperty property) {
        if (value == null) {
            property.setEnabled(false);
        } else if (value instanceof Boolean) {
            property.setEnabled((Boolean) value);
        }
    }
}