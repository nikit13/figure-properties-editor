package ru.spb.nkurasov.figure;

import java.util.Optional;

public class IntegerPropertyType extends AbstractFigurePropertyType {

    private final Optional<Integer> defaultValue;

    private final Optional<Integer> minValue;

    private final Optional<Integer> maxValue;

    private final boolean activeOnGroupEnabled;

    IntegerPropertyType(String name, Integer defaultValue, Integer minValue, Integer maxValue, boolean activeOnGroupEnabled) {
        super(name);

        if (minValue != null && maxValue != null && minValue > maxValue) {
            throw new IllegalStateException("error set range: min value '" + minValue + "' greater than max value '" + maxValue + "'");
        }

        // TODO add range checks

        this.defaultValue = Optional.ofNullable(defaultValue);
        this.minValue = Optional.ofNullable(minValue);
        this.maxValue = Optional.ofNullable(maxValue);
        this.activeOnGroupEnabled = activeOnGroupEnabled;
    }

    @Override
    public void accept(FigurePropertyTypeVisitor visitor) {
        visitor.visit(this);
    }

    public Integer getDefaultValue() {
        return defaultValue.orElse(null);
    }

    public boolean hasDefaultValue() {
        return defaultValue != null;
    }

    public Integer getMinValue() {
        return minValue.orElse(null);
    }

    public boolean hasMinValue() {
        return minValue.isPresent();
    }

    public Integer getMaxValue() {
        return maxValue.orElse(null);
    }

    public boolean hasMaxValue() {
        return maxValue.isPresent();
    }

    public boolean isActiveOnGroupEnabled() {
        return activeOnGroupEnabled;
    }
}
