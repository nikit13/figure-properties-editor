package ru.spb.nkurasov.figure.editor;

public interface GroupedFigureProperty extends FigureProperty {

    GroupProperty getGroup();

    boolean isActiveOnGroupEnabled();

    default boolean isEnabled() {
        if (isActiveOnGroupEnabled()) {
            return getGroup().isEnabled();
        } else {
            return !getGroup().isEnabled();
        }
    }
}
