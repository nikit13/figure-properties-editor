package ru.spb.nkurasov.figure;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Figure {

    private final String name;

    private final Map<String, PropertyType<?>> properties = new HashMap<>();

    Figure(String name, Map<String, ? extends PropertyType<?>> properties) {
        if (name == null) {
            throw new IllegalArgumentException("figure name is null");
        }

        if (properties == null || properties.isEmpty()) {
            throw new IllegalArgumentException("figure properties must contain at least one value");
        }

        this.name = name;
        this.properties.putAll(properties);
    }

    public String getName() {
        return name;
    }

    public Map<String, PropertyType<?>> getProperties() {
        return Collections.unmodifiableMap(properties);
    }
}
