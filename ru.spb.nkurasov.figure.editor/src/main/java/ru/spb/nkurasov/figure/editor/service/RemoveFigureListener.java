package ru.spb.nkurasov.figure.editor.service;

import ru.spb.nkurasov.figure.editor.Figure;

@FunctionalInterface
public interface RemoveFigureListener {

    void onFigureRemoved(Figure removedFigure);
    
}
