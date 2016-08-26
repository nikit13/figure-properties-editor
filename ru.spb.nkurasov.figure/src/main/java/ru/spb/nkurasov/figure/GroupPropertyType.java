package ru.spb.nkurasov.figure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class GroupPropertyType extends AbstractFigureProperty {

    private final List<FigureProperty> propertyTypes = new ArrayList<>();

    GroupPropertyType(String name, Collection<? extends FigureProperty> childTypes) {
        super(name);
        if (childTypes == null || childTypes.isEmpty()) {
            throw new IllegalArgumentException("group must contain at least one child property");
        }

        this.propertyTypes.addAll(childTypes);
    }

    @Override
    public void accept(FigurePropertyVisitor visitor) {
        visitor.visit(this);

        for (FigureProperty propertyType : propertyTypes) {
            propertyType.accept(visitor);
        }
    }
}
