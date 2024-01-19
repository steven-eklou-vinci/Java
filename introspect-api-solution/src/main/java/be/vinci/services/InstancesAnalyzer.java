package be.vinci.services;

import jakarta.json.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Instances analyzer. It saves an instance into attribute, from a constructor, and
 * gives a lot of convenient methods to transform this into a JSON object
 * to print the UML diagram.
 */
public class InstancesAnalyzer {

    private Object anInstance;

    public InstancesAnalyzer(Object anInstance) {
        this.anInstance = anInstance;
    }

    /**
     * Create a Json Object with all instance data.
     * Example :
     * {
     * classname: "User",
     * fields: [{}, {}],
     * }
     */
    public JsonObject getFullInfo() {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("classname", anInstance.getClass().getSimpleName());
        objectBuilder.add("fields", getFields());
        return objectBuilder.build();
    }

    /**
     * Get a field, and create a Json Object with all field data.
     * Example :
     * {
     * name: "firstname",
     * type: "String",
     * value: "Laurent",
     * isStatic: false
     * }
     * If the type is an object, the value will be null
     */
    public JsonObject getField(Field f) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        f.setAccessible(true);
        objectBuilder.add("name", f.getName());
        objectBuilder.add("type", f.getType().getSimpleName());
        objectBuilder.add("isStatic", Modifier.isStatic(f.getModifiers()));
        if (f.getType().isPrimitive() || f.getType() == String.class) {
            try {
                objectBuilder.add("value", String.valueOf(f.get(anInstance)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // TODO add missing info
        // TODO if type is an object, ignore the value and send null
        return objectBuilder.build();
    }

    /**
     * Get fields, and create a Json Array with all fields data.
     * Example :
     * [ {}, {} ]
     */
    public JsonArray getFields() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Field f : anInstance.getClass().getDeclaredFields()) {
            arrayBuilder.add(getField(f));
        }
        return arrayBuilder.build();
    }

}
