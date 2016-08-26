package ru.spb.nkurasov.figure;

public interface FigurePropertyDefinition {

    // TODO maybe add optionality?
    
    String getName();
    
    void accept(FigurePropertyDefinitionVisitor visitor);

}
