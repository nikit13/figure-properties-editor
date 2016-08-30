package ru.spb.nkurasov.figure.editor.service.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.spb.nkurasov.figure.FigureType;
import ru.spb.nkurasov.figure.FigureTypes;
import ru.spb.nkurasov.figure.editor.Figure;
import ru.spb.nkurasov.figure.editor.service.AddFigureListener;
import ru.spb.nkurasov.figure.editor.service.FigureSelectionChangedListener;
import ru.spb.nkurasov.figure.editor.service.FigureService;
import ru.spb.nkurasov.figure.editor.service.RemoveFigureListener;

class FigureServiceImpl implements FigureService {

    private final List<Figure> figures = new ArrayList<Figure>();

    private List<Figure> selectedFigures = new ArrayList<Figure>();

    private final Set<AddFigureListener> addListeners = new HashSet<>();

    private final Set<RemoveFigureListener> removeListeners = new HashSet<>();

    private final Set<FigureSelectionChangedListener> activationListeners = new HashSet<>();

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
    public boolean removeFigures(Collection<? extends Figure> figures) {
        if (figures == null) {
            throw new IllegalArgumentException("figures list is null");
        }

        final boolean result = this.figures.removeAll(figures);
        if (result) {
            fireFiguresRemoved(figures);
        }
        return result;
    }

    @Override
    public Collection<Figure> getFigures() {
        return new ArrayList<>(figures);
    }

    @Override
    public void setSelectedFigures(Collection<? extends Figure> figures) {
        this.selectedFigures.clear();
        this.selectedFigures.addAll(figures);
        fireFigureActivationChanged(Collections.unmodifiableList(selectedFigures));
    }

    @Override
    public List<Figure> getSelectedFigures() {
        return Collections.unmodifiableList(selectedFigures);
    }

    @Override
    public boolean isFigureSelected() {
        return !selectedFigures.isEmpty();
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
    public void addFigureActivationChangedListener(FigureSelectionChangedListener l) {
        if (l != null) {
            activationListeners.add(l);
        }
    }

    @Override
    public boolean removeFigureActivationChangedListener(FigureSelectionChangedListener l) {
        return l != null && activationListeners.remove(l);
    }

    private void fireFigureAdded(Figure figure) {
        for (AddFigureListener l : addListeners) {
            l.onFigureAdded(figure);
        }
    }

    private void fireFiguresRemoved(Collection<? extends Figure> figures) {
        for (RemoveFigureListener l : removeListeners) {
            l.onFigureRemoved(figures);
        }
    }

    private void fireFigureActivationChanged(List<? extends Figure> figures) {
        for (FigureSelectionChangedListener l : activationListeners) {
            l.onFigureActivationChanged(figures);
        }
    }

    @Override
    public List<FigureType> getAvailableTypes() {
        return FigureTypes.read();
    }
}
