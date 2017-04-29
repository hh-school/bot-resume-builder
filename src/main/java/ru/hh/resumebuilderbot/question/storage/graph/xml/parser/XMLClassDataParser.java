package ru.hh.resumebuilderbot.question.storage.graph.xml.parser;

import org.w3c.dom.Node;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.instantiator.BooleanInstantiator;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.instantiator.Instantiator;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.instantiator.IntegerInstantiator;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.instantiator.PatternInstantiator;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.instantiator.QuestionInstantiator;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.instantiator.StringInstantiator;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class XMLClassDataParser {
    private static final Map<String, Set<String>> requiredElements;
    private static final Map<String, Map<String, Object>> defaultValues;
    private static final Map<String, Instantiator> instantiators;

    static {
        Set<String> requiredForQuestionNodeLinear = new HashSet<>();
        requiredForQuestionNodeLinear.add("question");
        Set<String> requiredForQuestionNodeForking = new HashSet<>();
        requiredForQuestionNodeForking.add("question");
        requiredForQuestionNodeForking.add("pattern");
        Set<String> requiredForDatabaseSaver = new HashSet<>();
        requiredForDatabaseSaver.add("databaseField");
        requiredElements = new HashMap<>();
        requiredElements.put("QuestionNodeLinear", requiredForQuestionNodeLinear);
        requiredElements.put("QuestionNodeForking", requiredForQuestionNodeForking);
        requiredElements.put("DatabaseSaver", requiredForDatabaseSaver);

        instantiators = new HashMap<>();
        instantiators.put("question", new QuestionInstantiator());
        instantiators.put("skippable", new BooleanInstantiator());
        instantiators.put("answerPattern", new PatternInstantiator());
        instantiators.put("databaseField", new StringInstantiator());
        instantiators.put("index", new IntegerInstantiator());

        Map<String, Object> defaultValuesForLinearNode = new HashMap<>();
        defaultValuesForLinearNode.put("skippable", Boolean.TRUE);
        Map<String, Object> defaultValuesForForkingNode = defaultValuesForLinearNode;
        defaultValues = new HashMap<>();
        defaultValues.put("QuestionNodeLinear", defaultValuesForLinearNode);
        defaultValues.put("QuestionNodeForking", defaultValuesForForkingNode);
    }

    public static Map<String, Object> parseClassData(Node parent) throws IOException {

        String parentNodeName = parent.getNodeName();

        try {
            Map<String, Object> result = buildResultMap(parent);
            checkRequiredElements(parentNodeName, result);
            return result;
        } catch (IOException e) {
            throw new IOException("Failed to parse class data of class: " + parentNodeName, e);
        }
    }

    private static void checkRequiredElements(String name, Map<String, Object> result) throws IOException {
        if (requiredElements.containsKey(name) && !result.keySet().containsAll(requiredElements.get(name))) {
            throw new IOException("At least one of required attributes of class data absent for class: " + name);
        }
    }

    private static Map<String, Object> buildResultMap(Node parent) throws IOException {

        Map<String, Object> defaultValuesOriginal = defaultValues.getOrDefault(
                parent.getAttributes().getNamedItem("classpath").getNodeValue(), new HashMap<>());

        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : defaultValuesOriginal.entrySet()) {
            result.put(entry.getKey(), entry.getValue());
        }

        Optional<Node> optionalClassDataNode = XMLAsStream.getFirstChildByName(parent, "classData");
        if (!optionalClassDataNode.isPresent()) {
            return result;
        }
        Node classDataNode = optionalClassDataNode.get();
        for (Node classDataElementNode : XMLAsStream
                .fromParentNode(classDataNode)
                .collect(Collectors.toSet())) {
            result.put(classDataElementNode.getNodeName(), parseClassDataElement(classDataElementNode));
        }
        return result;
    }

    private static Object parseClassDataElement(Node classDataElementNode) throws IOException {
        String nodeName = classDataElementNode.getNodeName();
        if (!instantiators.containsKey(nodeName)) {
            throw new IOException("Cannot find instantiator for classData element: " + nodeName);
        }
        return instantiators.get(nodeName).makeInstance(classDataElementNode);
    }
}
