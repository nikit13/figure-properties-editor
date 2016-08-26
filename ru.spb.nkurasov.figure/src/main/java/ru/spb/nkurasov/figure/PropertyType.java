package ru.spb.nkurasov.figure;

public interface PropertyType<T> {

    // TODO maybe add optionality?
    
    T getDefaultValue();
    
    void accept(PropertyTypeVisitor visitor);

}
