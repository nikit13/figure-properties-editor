package ru.spb.nkurasov.figure;

public class BooleanPropertyType extends AbstractFigurePropertyType {

    private final Boolean defaultValue;

    private final boolean activeOnGroupEnabled;

    BooleanPropertyType(String name, Boolean defaultValue, boolean activeOnGroupEnabled) {
        super(name);

        this.defaultValue = defaultValue;
        this.activeOnGroupEnabled = activeOnGroupEnabled;
    }

    @Override
    public void accept(FigurePropertyTypeVisitor visitor) {
        visitor.visit(this);
    }

    public Boolean getDefaultValue() {
        return defaultValue;
    }

    public boolean hasDefaultValue() {
        return defaultValue != null;
    }
    
    public boolean isActiveOnGroupEnabled() {
        return activeOnGroupEnabled;
    }
}
