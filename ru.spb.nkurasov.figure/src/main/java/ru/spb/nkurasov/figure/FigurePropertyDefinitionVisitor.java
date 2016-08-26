package ru.spb.nkurasov.figure;

public interface FigurePropertyDefinitionVisitor {

    void visit(BooleanPropertyDefinition propertyType);

    void visit(StringPropertyDefinition propertyType);

    void visit(IntegerPropertyDefinition propertyType);

    void visit(GroupPropertyDefinition propertyType);

}
