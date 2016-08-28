package ru.spb.nkurasov.figure.editor.service;

import java.util.List;

import ru.spb.nkurasov.figure.editor.Figure;

@FunctionalInterface
public interface FigureActivationChangedListener {

    void onFigureActivationChanged(List<? extends Figure> activeFigures);
    
}
