package ru.spb.nkurasov.figure.editor.service.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import ru.spb.nkurasov.figure.editor.Figure;
import ru.spb.nkurasov.figure.editor.service.AddFigureListener;
import ru.spb.nkurasov.figure.editor.service.FigureActivationChangedListener;
import ru.spb.nkurasov.figure.editor.service.FigureService;
import ru.spb.nkurasov.figure.editor.service.RemoveFigureListener;

class FigureServiceImpl implements FigureService {

    private final List<Figure> figures = new ArrayList<Figure>();
    
    private Optional<Figure> activeFigure = Optional.empty();
    
    private final Set<AddFigureListener> addListeners = new HashSet<>();
    
    private final Set<RemoveFigureListener> removeListeners = new HashSet<>();
    
    private final Set<FigureActivationChangedListener> activationListeners = new HashSet<>();
    
    FigureServiceImpl() {
    }

    @Override
    public void addFigure(Figure figure) {
        if (figure == null) {
            throw new IllegalArgumentException("figure is null");
        }
        this.figures.add(figure);
        fireFigureAdded(figure);
    }

    @Override
    public boolean removeFigure(Figure figure) {
        if (figure == null) {
            throw new IllegalArgumentException("figure is null");
        }
        
        final boolean result = figures.remove(figure);
        if (result) {
            fireFigureRemoved(figure);
        }
        return result;
    }

    @Override
    public Collection<Figure> getFigures() {
        return Collections.unmodifiableList(figures);
    }

    @Override
    public void setActiveFigure(Figure figure) {
        if (figure != null && !figures.contains(figure)) {
            throw new IllegalStateException("figure not registered");
        }
        
        activeFigure = Optional.ofNullable(figure);
        fireFigureActivated(figure);
    }

    @Override
    public Optional<Figure> getActiveFigure() {
        return activeFigure;
    }

    @Override
    public boolean isFigureActivated() {
        return activeFigure.isPresent();
    }

    @Override
    public void addFigureAddedListener(AddFigureListener l) {
        if (l != null) {
            addListeners.add(l);
        }
    }

    @Override
    public boolean removeFigureAddedListener(AddFigureListener l) {
        return l != null && addListeners.remove(l);
    }

    @Override
    public void addFigureRemovedListener(RemoveFigureListener l) {
        if (l != null) {
            removeListeners.add(l);
        }
    }

    @Override
    public boolean removeFigureRemovedListener(RemoveFigureListener l) {
        return l != null && removeListeners.remove(l);
    }

    @Override
    public void addFigureActivationChangedListener(FigureActivationChangedListener l) {
        if (l != null) {
            activationListeners.add(l);
        }
    }

    @Override
    public boolean removeFigureActivationChangedListener(FigureActivationChangedListener l) {
        return l != null && activationListeners.remove(l);
    }
    
    private void fireFigureAdded(Figure figure) {
        for (AddFigureListener l : addListeners) {
            l.onFigureAdded(figure);
        }
    }
    
    private void fireFigureRemoved(Figure figure) {
        for (RemoveFigureListener l : removeListeners) {
            l.onFigureRemoved(figure);
        }
    }
    
    private void fireFigureActivated(Figure figure) {
        for (FigureActivationChangedListener l : activationListeners) {
            l.onFigureActivated(figure);
        }
    }
}
