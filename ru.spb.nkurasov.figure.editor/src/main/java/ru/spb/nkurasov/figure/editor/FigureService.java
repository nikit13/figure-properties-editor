package ru.spb.nkurasov.figure.editor;

import java.util.Collection;

public interface FigureService {
    
    void addFigure(Figure figure);
    
    boolean removeFigure(Figure figure);
    
    Collection<Figure> getFigures();

}
