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

    void visit(GroupProperty property);

    default void visit(GroupedBooleanProperty property) {
        visit((BooleanProperty) property);
    }

    default void visit(GroupedStringProperty property) {
        visit((StringProperty) property);
    }

    default void visit(GroupedIntegerProperty property) {
        visit((IntegerProperty) property);
    }

}
