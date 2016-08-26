package ru.spb.nkurasov.figure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class Figures {

    private static final String FIGURE_EXTENSION_ID = "ru.spb.nkurasov.figure";

    public static List<Figure> read() {
        IConfigurationElement[] figureElements = Platform.getExtensionRegistry().getConfigurationElementsFor(FIGURE_EXTENSION_ID);
        if (figureElements.length == 0) {
            return Collections.emptyList();
        }

        List<Figure> figures = new ArrayList<>();
        for (IConfigurationElement element : figureElements) {
            if (isFigure(element)) {
                String figureName = element.getAttribute("name");
                Collection<FigureProperty> figureProperties = readProperties(element.getChildren());
                figures.add(new Figure(figureName, figureProperties));
            }
        }

        return figures;
    }

    private static Collection<FigureProperty> readProperties(IConfigurationElement[] properties) {
        if (properties.length == 0) {
            return Collections.emptyList();
        }

        Collection<FigureProperty> figureProperties = new ArrayList<>(properties.length);
        for (IConfigurationElement property : properties) {
            FigureProperty propertyType = readPropertyType(property);
            figureProperties.add(propertyType);
        }
        return figureProperties;
    }

    private static FigureProperty readPropertyType(IConfigurationElement property) {
        String propertyName = property.getAttribute("name");
        String defaultValue = property.getAttribute("defaultValue");

        switch (property.getName()) {
        case "booleanProperty":
            return new BooleanPropertyType(propertyName, defaultValue == null || defaultValue.isEmpty() ? null : Boolean.valueOf(defaultValue));
        case "stringProperty":
            return new StringPropertyType(propertyName, defaultValue);
        case "integerProperty":
            return new IntegerPropertyType(propertyName, defaultValue == null || defaultValue.isEmpty() ? null : Integer.valueOf(defaultValue));
        case "groupProperty":
            return new GroupPropertyType(propertyName, readProperties(property.getChildren()));
        default:
            throw new ReadFigureException("unknown property type");
        }
    }

    private static boolean isFigure(IConfigurationElement element) {
        return "figure".equals(element.getName());
    }
}
