package ru.spb.nkurasov.figure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class FigureTypes {

    private static final String FIGURE_EXTENSION_ID = "ru.spb.nkurasov.figure";

    public static List<FigureType> read() {
        IConfigurationElement[] figureElements = Platform.getExtensionRegistry().getConfigurationElementsFor(FIGURE_EXTENSION_ID);
        if (figureElements.length == 0) {
            return Collections.emptyList();
        }

        List<FigureType> figures = new ArrayList<>();
        for (IConfigurationElement element : figureElements) {
            if (isFigure(element)) {
                String figureName = element.getAttribute("name");
                if (figures.stream().map(FigureType::getName).filter(figureName::equals).findAny().isPresent()) {
                    throw new ReadFigureException("name of figure type must be unique - " + figureName);
                }
                Collection<FigurePropertyType> figureProperties = readProperties(element.getChildren());
                figures.add(new FigureType(figureName, figureProperties));
            }
        }

        return figures;
    }

    private static Collection<FigurePropertyType> readProperties(IConfigurationElement[] properties) {
        if (properties.length == 0) {
            return Collections.emptyList();
        }

        Collection<FigurePropertyType> figureProperties = new ArrayList<>(properties.length);
        for (IConfigurationElement property : properties) {
            FigurePropertyType propertyType = readPropertyType(property);
            figureProperties.add(propertyType);
        }
        return figureProperties;
    }

    private static FigurePropertyType readPropertyType(IConfigurationElement property) {
        String propertyName = property.getAttribute("name");
        String defaultValue = property.getAttribute("defaultValue");

        switch (property.getName()) {
        case "booleanProperty":
            return new BooleanPropertyType(propertyName, defaultValue == null || defaultValue.isEmpty() ? null : Boolean.valueOf(defaultValue));
        case "stringProperty":
            return new StringPropertyType(propertyName, defaultValue);
        case "integerProperty":
            boolean boundedBelow = Boolean.valueOf(property.getAttribute("boundedBelow"));
            boolean boundedAbove = Boolean.valueOf(property.getAttribute("boundedAbove"));
            String minValue = property.getAttribute("minValue");
            String maxValue = property.getAttribute("maxValue");
            return new IntegerPropertyType(propertyName, 
                    defaultValue == null || defaultValue.isEmpty() ? null : Integer.valueOf(defaultValue),
                    !boundedBelow || minValue == null || minValue.isEmpty() ? null : Integer.valueOf(minValue), 
                    !boundedAbove || maxValue == null || maxValue.isEmpty() ? null : Integer.valueOf(maxValue));
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
