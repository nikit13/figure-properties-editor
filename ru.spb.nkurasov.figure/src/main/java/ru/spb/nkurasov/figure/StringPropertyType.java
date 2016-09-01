package ru.spb.nkurasov.figure;

public class StringPropertyType extends AbstractFigurePropertyType {

    private final String defaultValue;

    private final boolean activeOnGroupEnabled;

    StringPropertyType(String name, String defaultValue, boolean activeOnGroupEnabled) {
        super(name);

        this.defaultValue = defaultValue;
        this.activeOnGroupEnabled = activeOnGroupEnabled;
    }

    @Override
    public void accept(FigurePropertyTypeVisitor visitor) {
        visitor.visit(this);
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean hasDefaultValue() {
        return defaultValue != null;
    }

    public boolean isActiveOnGroupEnabled() {
        return activeOnGroupEnabled;
    }
}
