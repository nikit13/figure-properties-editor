package ru.spb.nkurasov.figure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class GroupPropertyType extends AbstractFigurePropertyType {

    private final List<FigurePropertyType> propertyTypes = new ArrayList<>();

    GroupPropertyType(String name, Collection<? extends FigurePropertyType> childTypes) {
        super(name);
        if (childTypes == null || childTypes.isEmpty()) {
            throw new IllegalArgumentException("group must contain at least one child property");
        }

        this.propertyTypes.addAll(childTypes);
    }

    @Override
    public void accept(FigurePropertyTypeVisitor visitor) {
        visitor.visit(this);

        for (FigurePropertyType propertyType : propertyTypes) {
            propertyType.accept(visitor);
        }
    }
}
