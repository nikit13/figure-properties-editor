package ru.spb.nkurasov.figure;

public class IntegerPropertyType extends AbstractFigurePropertyType {

    private final Integer defaultValue;

    private final Integer minValue;

    private final Integer maxValue;

    IntegerPropertyType(String name, Integer defaultValue, Integer minValue, Integer maxValue) {
        super(name);

        if (minValue != null && maxValue != null && minValue > maxValue) {
            throw new IllegalStateException("error set range: min value '" + minValue + "' greater than max value '" + maxValue + "'");
        }

        // TODO add range checks

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

    public boolean hasDefaultValue() {
        return defaultValue != null;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }
}
