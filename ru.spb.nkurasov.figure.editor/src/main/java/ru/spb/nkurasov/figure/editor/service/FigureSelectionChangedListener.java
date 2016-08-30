package ru.spb.nkurasov.figure.editor.service;

import java.util.List;

import ru.spb.nkurasov.figure.editor.Figure;

@FunctionalInterface
public interface FigureSelectionChangedListener {

    void onFigureActivationChanged(List<? extends Figure> activeFigures);
    
}
