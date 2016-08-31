package ru.spb.nkurasov.figure.editor;

import ru.spb.nkurasov.figure.StringPropertyType;

public class GroupedStringProperty extends StringProperty implements GroupedFigureProperty {

    private final GroupProperty group;
    
    public GroupedStringProperty(StringPropertyType type, GroupProperty group) {
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
