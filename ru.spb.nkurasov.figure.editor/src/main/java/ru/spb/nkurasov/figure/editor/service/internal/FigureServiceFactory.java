package ru.spb.nkurasov.figure.editor.service.internal;

import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

import ru.spb.nkurasov.figure.editor.service.FigureService;

public class FigureServiceFactory extends AbstractServiceFactory {

    @Override
    public Object create(@SuppressWarnings("rawtypes") Class serviceInterface, IServiceLocator parentLocator, IServiceLocator locator) {
        if (FigureService.class.isAssignableFrom(serviceInterface)) {
            return FigureServiceHolder.INSTANCE;
        }
        return null;
    }

    private static class FigureServiceHolder {
        static final FigureServiceImpl INSTANCE = new FigureServiceImpl();
    }
}
