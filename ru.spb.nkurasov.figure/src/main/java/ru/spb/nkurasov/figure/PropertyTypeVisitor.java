package ru.spb.nkurasov.figure;

public interface PropertyTypeVisitor {

    void visit(BooleanPropertyType propertyType);

    void visit(StringPropertyType propertyType);

    void visit(IntegerPropertyType propertyType);

}
