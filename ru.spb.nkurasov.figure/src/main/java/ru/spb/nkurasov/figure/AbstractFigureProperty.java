package ru.spb.nkurasov.figure;

abstract class AbstractFigureProperty implements FigureProperty {

    private final String name;

    AbstractFigureProperty(String name) {
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
