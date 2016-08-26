package ru.spb.nkurasov.figure;

public interface FigurePropertyVisitor {

    void visit(BooleanPropertyType propertyType);

    void visit(StringPropertyType propertyType);

    void visit(IntegerPropertyType propertyType);

    void visit(GroupPropertyType propertyType);

}
