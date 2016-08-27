package ru.spb.nkurasov.figure.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import ru.spb.nkurasov.figure.BooleanPropertyType;
import ru.spb.nkurasov.figure.FigurePropertyType;
import ru.spb.nkurasov.figure.FigurePropertyTypeVisitor;
import ru.spb.nkurasov.figure.FigureType;
import ru.spb.nkurasov.figure.GroupPropertyType;
import ru.spb.nkurasov.figure.IntegerPropertyType;
import ru.spb.nkurasov.figure.StringPropertyType;

public class Figure {

    private final String name;

    private final FigureType type;

    private final List<FigureProperty> properties = new ArrayList<>();

    public Figure(String name, FigureType type) {
        if (name == null) {
            throw new IllegalArgumentException("figure name is null");
        }
        if (type == null) {
            throw new IllegalArgumentException("figure type is null");
        }

        this.name = name;
        this.type = type;
        this.properties.addAll(type.getProperties().stream().map(Figure::createPropertyForType).collect(Collectors.toList()));
    }

    public String getName() {
        return name;
    }

    public FigureType getType() {
        return type;
    }

    public List<FigureProperty> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    private static FigureProperty createPropertyForType(FigurePropertyType type) {
        PropertyCreator propertyCreator = new PropertyCreator();
        type.accept(propertyCreator);
        return propertyCreator.getProperty();
    }

    private static class PropertyCreator implements FigurePropertyTypeVisitor {

        private FigureProperty property;

        @Override
        public void visit(BooleanPropertyType propertyType) {
            property = new BooleanProperty(propertyType);
        }

        @Override
        public void visit(StringPropertyType propertyType) {
            property = new StringProperty(propertyType);
        }

        @Override
        public void visit(IntegerPropertyType propertyType) {
            property = new IntegerProperty(propertyType);
        }

        @Override
        public void visit(GroupPropertyType propertyType) {
            // FIXME
            throw new UnsupportedOperationException();
        }

        public FigureProperty getProperty() {
            return property;
        }
    }
}
