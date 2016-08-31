package ru.spb.nkurasov.figure.editor.ui.action;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.ComputedValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.dialog.TitleAreaDialogSupport;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.IViewerObservableValue;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import ru.spb.nkurasov.figure.FigureType;
import ru.spb.nkurasov.figure.editor.Figure;
import ru.spb.nkurasov.figure.editor.service.FigureService;

public class AddFigureHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        FigureService figureService = (FigureService) PlatformUI.getWorkbench().getService(FigureService.class);
        AddFigureDialog dialog = new AddFigureDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                figureService.getAvailableTypes());
        if (dialog.open() == Dialog.OK) {
            figureService.addFigure(dialog.createFigure());
        }

        return null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
    private static class AddFigureDialog extends TitleAreaDialog {

        private final List<FigureType> availableTypes;

        private final WritableValue figureName = WritableValue.withValueType(String.class);

        private final WritableValue figureType = WritableValue.withValueType(FigureType.class);

        private final DataBindingContext context = new DataBindingContext();

        public AddFigureDialog(Shell parentShell, List<? extends FigureType> figureTypes) {
            super(parentShell);

            this.availableTypes = Collections.unmodifiableList(figureTypes);
        }

        @Override
        public void create() {
            super.create();

            getShell().setText("Add New Figure");
            setTitle("Add New Figure");
            setMessage("Enter figure name and select figure type");
        }

        @Override
        protected Control createDialogArea(Composite parent) {
            Composite control = (Composite) super.createDialogArea(parent);

            Composite container = new Composite(control, SWT.NONE);
            container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
            container.setLayout(new GridLayout(2, false));

            Label nameLabel = new Label(container, SWT.NONE);
            nameLabel.setText("Figure Name:");
            nameLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));

            Text nameText = new Text(container, SWT.BORDER);
            nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

            ISWTObservableValue targetName = WidgetProperties.text(SWT.Modify).observe(nameText);
            UpdateValueStrategy figureNameStrategy = new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE);
            figureNameStrategy.setAfterGetValidator(this::validateFigureName);
            context.bindValue(targetName, figureName, figureNameStrategy, new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER));

            Label typeLabel = new Label(container, SWT.NONE);
            typeLabel.setText("Figure Type:");
            typeLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));

            ComboViewer typeCombo = new ComboViewer(container);
            typeCombo.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
            typeCombo.setContentProvider(ArrayContentProvider.getInstance());
            typeCombo.setLabelProvider(new FigureTypeNameLabelProvider());
            typeCombo.setInput(availableTypes);

            IViewerObservableValue targetType = ViewerProperties.singleSelection().observe(typeCombo);
            UpdateValueStrategy figureTypeStrategy = new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE);
            figureTypeStrategy.setAfterConvertValidator(this::validateFigureType);
            context.bindValue(targetType, figureType, figureTypeStrategy, new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER));
            if (!availableTypes.isEmpty()) {
                typeCombo.setSelection(new StructuredSelection(availableTypes.get(0)));
            }

            TitleAreaDialogSupport.create(this, context);

            return control;
        }

        private IStatus validateFigureName(Object e) {
            String figureName = (String) e;
            return figureName != null && !figureName.isEmpty() ? ValidationStatus.ok() : ValidationStatus.error("figure name must not be empty");
        }

        private IStatus validateFigureType(Object e) {
            FigureType type = (FigureType) e;
            return type != null ? ValidationStatus.ok() : ValidationStatus.error("figure type is null");
        }

        @Override
        protected void createButtonsForButtonBar(Composite parent) {
            super.createButtonsForButtonBar(parent);

            Button okButton = getButton(OK);
            AggregateValidationStatus validationStatus = new AggregateValidationStatus(context.getBindings(), AggregateValidationStatus.MAX_SEVERITY);

            IObservableValue validationOk = new ComputedValue() {

                @Override
                protected Boolean calculate() {
                    return ((IStatus) validationStatus.getValue()).isOK();
                }
            };
            ISWTObservableValue buttonEnablement = WidgetProperties.enabled().observe(okButton);
            context.bindValue(validationOk, buttonEnablement, new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE), new UpdateValueStrategy(
                    UpdateValueStrategy.POLICY_NEVER));
        }

        public Figure createFigure() {
            return new Figure((String) figureName.getValue(), (FigureType) figureType.getValue());
        }

        private static class FigureTypeNameLabelProvider extends LabelProvider {

            @Override
            public String getText(Object element) {
                if (element instanceof FigureType) {
                    return ((FigureType) element).getName();
                }
                return super.getText(element);
            }
        }
    }
}
