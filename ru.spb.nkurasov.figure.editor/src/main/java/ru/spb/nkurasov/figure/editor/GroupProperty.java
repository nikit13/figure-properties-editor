package ru.spb.nkurasov.figure.editor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.spb.nkurasov.figure.FigurePropertyType;
import ru.spb.nkurasov.figure.GroupPropertyType;

public class GroupProperty implements FigureProperty {

    private final GroupPropertyType type;

    private final Map<String, GroupedFigureProperty> properties = new HashMap<>();
    
    private boolean enabled = true;

    public GroupProperty(GroupPropertyType type) {
        if (type == null) {
            throw new IllegalArgumentException();
        }

        this.type = type;
    }

    @Override
    public FigurePropertyType getType() {
        return type;
    }

    @Override
    public void accept(FigurePropertyVisitor visitor) {
        visitor.visit(this);
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<GroupedFigureProperty> getProperties() {
        return properties.values().stream().collect(Collectors.toList());
    }

    void addProperty(GroupedFigureProperty property) {
        if (property == null) {
            throw new IllegalArgumentException("property is null");
        }

        if (this.properties.containsKey(property.getName())) {
            throw new IllegalStateException("cannot add property '" + property.getName() + "' - propertywith same name already exists");
        }

        this.properties.put(property.getName(), property);
    }
}
