package ru.spb.nkurasov.figure;

public class IntegerPropertyType extends AbstractFigurePropertyType {

    private final Integer defaultValue;

    private final Integer minValue;

    private final Integer maxValue;

    IntegerPropertyType(String name, Integer defaultValue, Integer minValue, Integer maxValue) {
        super(name);

        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public void accept(FigurePropertyTypeVisitor visitor) {
        visitor.visit(this);
    }

    public Integer getDefaultValue() {
        return defaultValue;
    }
    
    public Integer getMinValue() {
        return minValue;
    }
    
    public Integer getMaxValue() {
        return maxValue;
    }
}
