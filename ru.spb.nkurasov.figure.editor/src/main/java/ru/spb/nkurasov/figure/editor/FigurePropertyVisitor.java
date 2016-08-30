package ru.spb.nkurasov.figure.editor;

public interface FigurePropertyVisitor {

    void visit(BooleanProperty property);

    void visit(IntegerProperty property);

    void visit(StringProperty property);

}
