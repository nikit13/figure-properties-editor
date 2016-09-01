package ru.spb.nkurasov.figure.editor;

import ru.spb.nkurasov.figure.IntegerPropertyType;

public class GroupedIntegerProperty extends IntegerProperty implements GroupedFigureProperty {

    private final GroupProperty group;

    public GroupedIntegerProperty(IntegerPropertyType type, GroupProperty group) {
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
    
    @Override
    public IntegerPropertyType getType() {
        return (IntegerPropertyType) super.getType();
    }
    
    @Override
    public boolean isActiveOnGroupEnabled() {
        return getType().isActiveOnGroupEnabled();
    }
}
