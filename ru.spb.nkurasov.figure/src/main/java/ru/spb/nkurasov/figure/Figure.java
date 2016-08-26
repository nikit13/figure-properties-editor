package ru.spb.nkurasov.figure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class Figure {

    private final String name;

    private final List<FigureProperty> properties = new ArrayList<>();

    Figure(String name, Collection<? extends FigureProperty> properties) {
        if (name == null) {
            throw new IllegalArgumentException("figure name is null");
        }

        if (properties == null || properties.isEmpty()) {
            throw new IllegalArgumentException("figure properties must contain at least one value");
        }

        this.name = name;
        this.properties.addAll(properties);
    }

    public String getName() {
        return name;
    }

    public Collection<FigureProperty> getProperties() {
        return Collections.unmodifiableCollection(properties);
    }

    public Optional<FigureProperty> getProperty(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }

        return properties.stream().filter(p -> p.getName().equals(name)).findFirst();
    }
}
