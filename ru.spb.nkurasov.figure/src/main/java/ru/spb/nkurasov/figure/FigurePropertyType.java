package ru.spb.nkurasov.figure;

public interface FigurePropertyType {

    // TODO maybe add optionality?
    
    String getName();
    
    void accept(FigurePropertyTypeVisitor visitor);

}
