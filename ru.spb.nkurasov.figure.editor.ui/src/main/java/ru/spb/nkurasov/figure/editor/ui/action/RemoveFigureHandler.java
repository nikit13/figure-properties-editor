package ru.spb.nkurasov.figure.editor.ui.action;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;

import ru.spb.nkurasov.figure.editor.Figure;
import ru.spb.nkurasov.figure.editor.service.FigureService;

public class RemoveFigureHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        FigureService figureService = (FigureService) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getService(FigureService.class);
        List<Figure> activeFigures = figureService.getActiveFigures();
        figureService.removeFigures(activeFigures);
        return null;
    }
}
