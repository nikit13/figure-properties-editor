package ru.spb.nkurasov.figure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                IConfigurationElement[] propertiesElements = element.getChildren("properties");
                if (propertiesElements.length != 1) {
                    throw new ReadFigureException("properties element must be specified only once");
                }

                Map<String, PropertyType<?>> figureProperties = readProperties(propertiesElements[0]);

                figures.add(new Figure(figureName, figureProperties));
            }
        }

        return figures;
    }

    private static Map<String, PropertyType<?>> readProperties(IConfigurationElement properties) {
        Map<String, PropertyType<?>> figureProperties = new HashMap<>();
        for (IConfigurationElement property : properties.getChildren("property")) {
            String propertyName = property.getAttribute("name");
            PropertyType<?> propertyType = readPropertyType(property);
            figureProperties.put(propertyName, propertyType);
        }
        return figureProperties;
    }

    private static PropertyType<?> readPropertyType(IConfigurationElement property) {
        String defaultValue = property.getAttribute("defaultValue");
        switch (property.getAttribute("type")) {
        case "boolean":
            return new BooleanPropertyType(defaultValue == null || defaultValue.isEmpty() ? null : Boolean.valueOf(defaultValue));
        case "string":
            return new StringPropertyType(defaultValue);
        case "integer":
            return new IntegerPropertyType(defaultValue == null || defaultValue.isEmpty() ? null : Integer.valueOf(defaultValue));
        default:
            throw new ReadFigureException("unknown property type");
        }
    }

    private static boolean isFigure(IConfigurationElement element) {
        return "figure".equals(element.getName());
    }
}
