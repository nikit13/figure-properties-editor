package ru.spb.nkurasov.figure.editor.application;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class FigureEditorApplication implements IApplication {

    @Override
    public Object start(IApplicationContext context) throws Exception {
        try (CloseableDisplay display = new CloseableDisplay(PlatformUI.createDisplay())) {
            final int returnCode = PlatformUI.createAndRunWorkbench(display.getDisplay(), new FigureEditorWorkbenchAdvisor());
            return returnCode == PlatformUI.RETURN_RESTART ? IApplication.EXIT_RESTART : IApplication.EXIT_OK;
        }
    }

    @Override
    public void stop() {
        if (PlatformUI.isWorkbenchRunning()) {
            final IWorkbench workbench = PlatformUI.getWorkbench();
            final Display display = workbench.getDisplay();
            display.syncExec(() -> {
                if (!display.isDisposed()) {
                    workbench.close();
                }
            });
        }
    }

    private static class CloseableDisplay implements AutoCloseable {

        private final Display display;

        CloseableDisplay(Display display) {
            if (display == null) {
                throw new IllegalArgumentException();
            }
            this.display = display;
        }

        @Override
        public void close() throws Exception {
            display.dispose();
        }

        Display getDisplay() {
            return display;
        }
    }
}
