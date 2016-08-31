package ru.spb.nkurasov.figure.editor.ui.view;

import ru.spb.nkurasov.figure.editor.BooleanProperty;
import ru.spb.nkurasov.figure.editor.FigurePropertyVisitor;
import ru.spb.nkurasov.figure.editor.GroupProperty;
import ru.spb.nkurasov.figure.editor.IntegerProperty;
import ru.spb.nkurasov.figure.editor.StringProperty;

/**
 * Акцессор значения свойства. Преобразует значение свойства в строку
 * 
 * @author nkurasov
 *
 */
public class FigurePropertyStringValueGetter implements FigurePropertyVisitor {

    private String propertyValue;

    @Override
    public void visit(BooleanProperty property) {
        property.getValue().ifPresent(this::setBooleanValue);
    }

    private void setBooleanValue(Boolean value) {
        propertyValue = value.toString();
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

    @Override
    public void visit(GroupProperty property) {
        propertyValue = Boolean.valueOf(property.isEnabled()).toString();
    }

    public String getPropertyValue() {
        return propertyValue == null ? "" : propertyValue.toString();
    }
}
