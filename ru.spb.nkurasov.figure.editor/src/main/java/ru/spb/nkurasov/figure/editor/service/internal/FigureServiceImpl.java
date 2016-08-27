package ru.spb.nkurasov.figure.editor.service.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ru.spb.nkurasov.figure.editor.Figure;
import ru.spb.nkurasov.figure.editor.service.FigureService;

class FigureServiceImpl implements FigureService {

    private final List<Figure> figures = new ArrayList<Figure>();
    
    FigureServiceImpl() {
    }

    @Override
    public void addFigure(Figure figure) {
        if (figure == null) {
            throw new IllegalArgumentException("figure is null");
        }
        this.figures.add(figure);
    }

    @Override
    public boolean removeFigure(Figure figure) {
        if (figure == null) {
            throw new IllegalArgumentException("figure is null");
        }
        return figures.remove(figure);
    }

    @Override
    public Collection<Figure> getFigures() {
        return Collections.unmodifiableList(figures);
    }
    
}
