package ru.spb.nkurasov.figure.editor;

/**
 * Определяет действие над конкретным свойством фигуры
 * 
 * @author nkurasov
 *
 */
public interface FigurePropertyVisitor {

    void visit(BooleanProperty property);

    void visit(IntegerProperty property);

    void visit(StringProperty property);

}
