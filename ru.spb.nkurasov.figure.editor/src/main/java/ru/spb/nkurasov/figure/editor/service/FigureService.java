package ru.spb.nkurasov.figure.editor.service;

import java.util.Collection;

import ru.spb.nkurasov.figure.editor.Figure;

public interface FigureService {
    
    void addFigure(Figure figure);
    
    boolean removeFigure(Figure figure);
    
    Collection<Figure> getFigures();

}
