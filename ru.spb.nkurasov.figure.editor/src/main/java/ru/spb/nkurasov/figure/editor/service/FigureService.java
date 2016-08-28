package ru.spb.nkurasov.figure.editor.service;

import java.util.Collection;
import java.util.List;

import ru.spb.nkurasov.figure.editor.Figure;

public interface FigureService {

    void addFigure(Figure figure);

    boolean removeFigure(Figure figure);

    Collection<Figure> getFigures();

    void setActiveFigures(Collection<? extends Figure> figures);

    List<Figure> getActiveFigures();

    boolean isFigureActivated();
    
    void addFigureAddedListener(AddFigureListener l);
    
    boolean removeFigureAddedListener(AddFigureListener l);
    
    void addFigureRemovedListener(RemoveFigureListener l);
    
    boolean removeFigureRemovedListener(RemoveFigureListener l);
    
    void addFigureActivationChangedListener(FigureActivationChangedListener l);
    
    boolean removeFigureActivationChangedListener(FigureActivationChangedListener l);

}
