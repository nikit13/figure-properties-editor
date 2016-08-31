package ru.spb.nkurasov.figure.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ru.spb.nkurasov.figure.BooleanPropertyType;
import ru.spb.nkurasov.figure.FigurePropertyType;
import ru.spb.nkurasov.figure.FigurePropertyTypeVisitor;
import ru.spb.nkurasov.figure.FigureType;
import ru.spb.nkurasov.figure.GroupPropertyType;
import ru.spb.nkurasov.figure.IntegerPropertyType;
import ru.spb.nkurasov.figure.StringPropertyType;

/**
 * Фигура. Имеет имя, тип и список свойств
 * 
 * @author nkurasov
 *
 */
public final class Figure {

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
    
    public List<FigureProperty> getFlatProperties() {
        return properties.stream().flatMap(this::flatProperties).collect(Collectors.toList());
    }

    private Stream<FigureProperty> flatProperties(FigureProperty property) {
        if (property instanceof GroupProperty) {
            GroupProperty groupProperty = (GroupProperty) property;
            List<FigureProperty> properties = new ArrayList<FigureProperty>();
            properties.add(groupProperty);
            properties.addAll(groupProperty.getProperties());
            return properties.stream();
        } else {
            return Collections.singletonList(property).stream();
        }
    }
    
    private static FigureProperty createPropertyForType(FigurePropertyType type) {
        PropertyBuilder propertyBuilder = new PropertyBuilder();
        type.accept(propertyBuilder);
        return propertyBuilder.getProperty();
    }

    private static class PropertyBuilder implements FigurePropertyTypeVisitor {

        private FigureProperty property;

        @Override
        public void visit(BooleanPropertyType propertyType) {
            if (isGroupedProperty()) {
                GroupProperty groupProperty = (GroupProperty) property;
                groupProperty.addProperty(new GroupedBooleanProperty(propertyType, groupProperty));
            } else {
                property = new BooleanProperty(propertyType);
            }
        }

        @Override
        public void visit(StringPropertyType propertyType) {
            if (isGroupedProperty()) {
                GroupProperty groupProperty = (GroupProperty) property;
                groupProperty.addProperty(new GroupedStringProperty(propertyType, groupProperty));
            } else {
                property = new StringProperty(propertyType);
            }
        }

        @Override
        public void visit(IntegerPropertyType propertyType) {
            if (isGroupedProperty()) {
                GroupProperty groupProperty = (GroupProperty) property;
                groupProperty.addProperty(new GroupedIntegerProperty(propertyType, groupProperty));
            } else {
                property = new IntegerProperty(propertyType);
            }
        }

        @Override
        public void visit(GroupPropertyType propertyType) {
            property = new GroupProperty(propertyType);
        }

        public FigureProperty getProperty() {
            return property;
        }

        private boolean isGroupedProperty() {
            return property != null;
        }
    }
}
