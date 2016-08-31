package ru.spb.nkurasov.figure.editor;

import ru.spb.nkurasov.figure.BooleanPropertyType;

public class GroupedBooleanProperty extends BooleanProperty implements GroupedFigureProperty {

    private final GroupProperty group;

    public GroupedBooleanProperty(BooleanPropertyType type, GroupProperty group) {
        super(type);

        if (group == null) {
            throw new IllegalArgumentException("group is null");
        }
        this.group = group;
    }

    @Override
    public GroupProperty getGroup() {
        return group;
    }
    
    @Override
    public void accept(FigurePropertyVisitor visitor) {
       visitor.visit(this);
    }
}
