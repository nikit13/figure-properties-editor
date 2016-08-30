package ru.spb.nkurasov.figure.editor.service;

import java.util.Collection;
import java.util.List;

import ru.spb.nkurasov.figure.FigureType;
import ru.spb.nkurasov.figure.editor.Figure;

/**
 * Определяет основные действия, доступные в редакторе свойств фигур
 * 
 * @author nkurasov
 *
 */
public interface FigureService {

    void addFigure(Figure figure);

    boolean removeFigures(Collection<? extends Figure> figures);

    Collection<Figure> getFigures();

    void setSelectedFigures(Collection<? extends Figure> figures);

    List<Figure> getSelectedFigures();

    List<FigureType> getAvailableTypes();

    boolean isFigureSelected();

    void addFigureAddedListener(AddFigureListener l);

    boolean removeFigureAddedListener(AddFigureListener l);

    void addFigureRemovedListener(RemoveFigureListener l);

    boolean removeFigureRemovedListener(RemoveFigureListener l);

    void addFigureActivationChangedListener(FigureSelectionChangedListener l);

    boolean removeFigureActivationChangedListener(FigureSelectionChangedListener l);

}
