package be.vinci.services;

import jakarta.json.*;

import java.lang.reflect.*;

/**
 * Class analyzer. It saves a class into attribute, from a constructor, and
 * gives a lot of convenient methods to transform this into a JSON object
 * to print the UML diagram.
 */
public class ClassAnalyzer {

    private Class aClass;

    public ClassAnalyzer(Class aClass) {
        this.aClass = aClass;
    }

    /**
     * Create a JSON Object with all the info of the class.
     * @return
     */
    public JsonObject getFullInfo() {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("name", aClass.getSimpleName());
        objectBuilder.add("fields", getFields());
        objectBuilder.add("methods", getMethods());
        return objectBuilder.build();
    }

    /**
     * Get fields, and create a Json Array with all fields data.
     * Example :
     * [ {}, {} ]
     */
    public JsonArray getFields() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Field f : aClass.getDeclaredFields()) {
            arrayBuilder.add(getField(f));
        }
        return arrayBuilder.build();
    }

    /**
     * Get a field, and create a Json Object with all field data.
     * Example :
     * {
     * name: "firstname",
     * type: "String",
     * visibility : "private"  // public, private, protected, package
     * isStatic: false,
     * }
     */
    public JsonObject getField(Field f) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("name", f.getName());
        if (f.getGenericType() instanceof ParameterizedType) {
            objectBuilder.add("type", f.getGenericType().toString());
        } else {
            objectBuilder.add("type", f.getType().getSimpleName());
        }
        objectBuilder.add("visibility", getFieldVisibility(f));
        objectBuilder.add("isStatic", isFieldStatic(f));
        return objectBuilder.build();
    }

    /**
     * Return whether a field is static or not
     *
     * @param f the field to check
     * @return true if the field is static, false else
     */
    private boolean isFieldStatic(Field f) {
        return Modifier.isStatic(f.getModifiers());
    }

    /**
     * Get field visibility in a string form
     *
     * @param f the field to check
     * @return the visibility (public, private, protected, package)
     */
    private String getFieldVisibility(Field f) {
        if (Modifier.isPrivate(f.getModifiers())) return "private";
        if (Modifier.isPublic(f.getModifiers())) return "public";
        if (Modifier.isProtected(f.getModifiers())) return "protected";
        return "package";
    }

    /**
     * Get methods, and create a Json Array with all method data.
     * Example :
     * [ {}, {} ]
     */
    public JsonArray getMethods() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Method m : aClass.getDeclaredMethods()) {
            arrayBuilder.add(getMethod(m));
        }
        return arrayBuilder.build();
    }

    /**
     * Get a method, and create a Json Object with all method data.
     * Example :
     *      {
     *          name: "setFirstName",
     *          returnType: null,
     *          parameters: ["String"]
     *          visibility : "public"  // public, private, protected, package
     *          isStatic: false,
     *          isAbstract: false
     *      }
     */
    public JsonObject getMethod(Method m) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("name", m.getName());
        objectBuilder.add("returnType", m.getReturnType().getName());
        objectBuilder.add("parameters", getParameters(m));
        objectBuilder.add("visibility", getMethodVisibility(m));
        objectBuilder.add("isStatic", isMethodStatic(m));
        objectBuilder.add("isAbstract", isMethodAbstract(m));
        return objectBuilder.build();
    }

    /**
     * Return whether a method is abstract or not
     *
     * @param m the method to check
     * @return true if the method is abstract, false else
     */
    private boolean isMethodAbstract(Method m) {
        return Modifier.isAbstract(m.getModifiers());
    }

    /**
     * Return whether a method is static or not
     *
     * @param m the method to check
     * @return true if the method is static, false else
     */
    private boolean isMethodStatic(Method m) {
        return Modifier.isStatic(m.getModifiers());
    }

    /**
     * Get method visibility in a string form
     *
     * @param m the method to check
     * @return the visibility (public, private, protected, package)
     */
    private String getMethodVisibility(Method m) {
        if (Modifier.isPrivate(m.getModifiers())) return "private";
        if (Modifier.isPublic(m.getModifiers())) return "public";
        if (Modifier.isProtected(m.getModifiers())) return "protected";
        return "package";
    }

    /**
     * Get method parameters, and create a Json Array with the method parameters types.
     * Example :
     * [ {}, {} ]
     */
    public JsonArray getParameters(Method m) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Parameter p : m.getParameters()) {
            arrayBuilder.add(p.getType().getSimpleName());
        }
        return arrayBuilder.build();
    }

}
