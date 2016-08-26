package ru.spb.nkurasov.figure;

abstract class AbstractFigurePropertyType implements FigurePropertyType {

    private final String name;

    AbstractFigurePropertyType(String name) {
        if (name == null) {
            throw new IllegalArgumentException("property name is null");
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
