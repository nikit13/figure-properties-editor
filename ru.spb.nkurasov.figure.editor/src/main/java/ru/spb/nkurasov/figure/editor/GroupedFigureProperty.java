package ru.spb.nkurasov.figure.editor;

public interface GroupedFigureProperty extends FigureProperty {

    GroupProperty getGroup();

    boolean isActiveOnGroupEnabled();

    default boolean isEnabled() {
    	return !(isActiveOnGroupEnabled() ^ getGroup().isEnabled());
    }
}
