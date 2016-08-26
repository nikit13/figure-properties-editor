package ru.spb.nkurasov.figure;

abstract class AbstractPropertyType<T> implements PropertyType<T> {

    private final T defaultValue;
    
    AbstractPropertyType() {
        defaultValue = null;
    }
    
    AbstractPropertyType(T defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    @Override
    public T getDefaultValue() {
        return defaultValue;
    }
}
