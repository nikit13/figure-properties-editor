package ru.spb.nkurasov.figure.editor.service.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;
import org.eclipse.ui.services.IServiceLocator;

import ru.spb.nkurasov.figure.editor.service.FigureSelectionChangedListener;
import ru.spb.nkurasov.figure.editor.service.FigureService;

public class FigureSourceProvider extends AbstractSourceProvider {

    private static final String ACTIVE_FIGURES = "activeFigures";

    private final FigureSelectionChangedListener activationListener = f -> fireSourceChanged(ISources.WORKBENCH, ACTIVE_FIGURES, f);

    private FigureService figureService;

    @Override
    public void initialize(IServiceLocator locator) {
        super.initialize(locator);

        figureService = (FigureService) locator.getService(FigureService.class);
        figureService.addFigureActivationChangedListener(activationListener);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Map getCurrentState() {
        Map<String, Object> currentState = new HashMap<>();
        currentState.put(ACTIVE_FIGURES, figureService.getSelectedFigures());
        return currentState;
    }

    @Override
    public String[] getProvidedSourceNames() {
        return new String[] { ACTIVE_FIGURES };
    }

    @Override
    public void dispose() {
        figureService.removeFigureActivationChangedListener(activationListener);
        figureService = null;
    }
}
