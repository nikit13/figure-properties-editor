package ru.spb.nkurasov.figure.editor.service;

import java.util.Collection;

import ru.spb.nkurasov.figure.editor.Figure;

@FunctionalInterface
public interface RemoveFigureListener {

    void onFigureRemoved(Collection<? extends Figure> removedFigure);
    
}
