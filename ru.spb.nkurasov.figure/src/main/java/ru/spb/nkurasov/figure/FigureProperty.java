package ru.spb.nkurasov.figure;

public interface FigureProperty {

    // TODO maybe add optionality?
    
    String getName();
    
    void accept(FigurePropertyVisitor visitor);

}
