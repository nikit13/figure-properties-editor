package ru.spb.nkurasov.figure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class GroupPropertyDefinition extends AbstractFigureProperty {

    private final List<FigurePropertyDefinition> propertyTypes = new ArrayList<>();

    GroupPropertyDefinition(String name, Collection<? extends FigurePropertyDefinition> childTypes) {
        super(name);
        if (childTypes == null || childTypes.isEmpty()) {
            throw new IllegalArgumentException("group must contain at least one child property");
        }

        this.propertyTypes.addAll(childTypes);
    }

    @Override
    public void accept(FigurePropertyDefinitionVisitor visitor) {
        visitor.visit(this);

        for (FigurePropertyDefinition propertyType : propertyTypes) {
            propertyType.accept(visitor);
        }
    }
}
