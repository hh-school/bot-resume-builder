package ru.hh.resumebuilderbot.question.storage.graph.node.constructor;

import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNode;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class QuestionNodeBuilder {
    private static final String NODE_CONSTRUCTOR_PACKAGE =
            "ru.hh.resumebuilderbot.question.storage.graph.node.constructor.";
    private static final Map<String, String> RELATIVE_PACKAGES;

    static {
        RELATIVE_PACKAGES = new HashMap<>();
        RELATIVE_PACKAGES.put("validator", "validator.");
        RELATIVE_PACKAGES.put("saver", "saver.");
        RELATIVE_PACKAGES.put("base", "base.");
    }

    public static QuestionNode build(Map<String, Map<String, Object>> constructorData)
            throws IOException {

        Map<String, Object> instances = buildAllConstructorElements(constructorData);
        return buildResult(instances);
    }

    private static QuestionNode buildResult(Map<String, Object> instances) throws IOException {
        QuestionNode baseInstance = (QuestionNode) instances.get("base");
        for (Map.Entry<String, Object> entry : instances.entrySet()) {
            if (!entry.getKey().equals("base")) {
                setField(baseInstance, entry.getKey(), entry.getValue());
            }
        }
        return baseInstance;
    }

    private static Map<String, Object> buildAllConstructorElements(Map<String, Map<String, Object>> constructorData)
            throws IOException {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, Map<String, Object>> entry : constructorData.entrySet()) {
            String fullClasspath = NODE_CONSTRUCTOR_PACKAGE + RELATIVE_PACKAGES.get(entry.getKey());
            result.put(entry.getKey(), buildConstructorElement(
                    fullClasspath,
                    entry.getValue()));
        }
        return result;
    }

    private static Object buildConstructorElement(String fullClasspath, Map<String, Object> constructorDataElement)
            throws IOException {
        try {
            Class resultClass = Class.forName(fullClasspath + constructorDataElement.get("classpath"));
            Object result = resultClass.newInstance();
            for (Map.Entry<String, Object> entry : constructorDataElement.entrySet()) {
                if (!entry.getKey().equals("classpath")) {
                    setField(result, entry.getKey(), entry.getValue());
                }
            }
            return result;
        } catch (IllegalAccessException e) {
            throw new IOException("Failed to access no-arg constructor of class: " +
                    constructorDataElement.get("classpath"));
        } catch (InstantiationException e) {
            throw new IOException("Failed to instantiate class: " + constructorDataElement.get("classpath"));
        } catch (IOException e) {
            throw new IOException("Cannot make instance of: " + constructorDataElement.get("classpath"), e);
        } catch (ClassNotFoundException e) {
            throw new IOException("Cannot find class: " + constructorDataElement.get("classpath"));
        }
    }

    private static void setField(Object object, String fieldName, Object fieldValue) throws IOException {
        try {
            Class objectClass = getProperClass(object, fieldName);
            Field field = null;
            field = objectClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, fieldValue);
        } catch (NoSuchFieldException e) {
            throw new IOException("Cannot find field: " + fieldName);
        } catch (IllegalAccessException e) {
            throw new IOException("Cannot set field: " + fieldName);
        }
    }

    private static Class getProperClass(Object object, String fieldName) throws IOException {
        Class currentClass = object.getClass();
        while (currentClass != Object.class) {
            Field[] fields = currentClass.getDeclaredFields();
            Optional<Field> optionalField = Arrays.stream(fields)
                    .filter(x -> x.getName().equals(fieldName))
                    .findFirst();
            if (optionalField.isPresent()) {
                return currentClass;
            }
            currentClass = currentClass.getSuperclass();
        }
        throw new IOException("Cannot find field: " + fieldName);
    }
}
