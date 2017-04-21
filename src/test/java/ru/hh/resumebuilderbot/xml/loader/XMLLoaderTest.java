package ru.hh.resumebuilderbot.xml.loader;

import org.testng.annotations.Test;
import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.question.storage.graph.GraphEntry;
import ru.hh.resumebuilderbot.question.storage.graph.node.QuestionNode;
import ru.hh.resumebuilderbot.question.storage.graph.node.QuestionNodeForking;
import ru.hh.resumebuilderbot.question.storage.graph.node.QuestionNodeLinear;
import ru.hh.resumebuilderbot.question.storage.graph.node.QuestionNodeTerminal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class XMLLoaderTest {

    private final static String allFeaturesFilename = "src/test/resources/XMLLoaderTest/AllFeatures.xml";

    private static Graph graph;
    private static Map<Integer, GraphEntry> entriesMap;
    private static QuestionNode root;

    private static Graph clonedGraph;
    private static Map<Integer, GraphEntry> clonedEntriesMap;
    private static QuestionNode clonedRoot;

    private static Graph sampleGraph;
    private static Map<Integer, GraphEntry> sampleEntriesMap;
    private static QuestionNode sampleRoot;

    private static Set<Integer> sampleIndexSet = new HashSet<>();

    static {
        try {
            graph = Graph.fromXMLFile(allFeaturesFilename);
            clonedGraph = graph.cloneContent();


            root = graph.getRoot();
            clonedRoot = clonedGraph.getRoot();

            Field entriesMapField = Graph.class.getDeclaredField("entriesMap");
            entriesMapField.setAccessible(true);
            entriesMap = (Map<Integer, GraphEntry>) entriesMapField.get(graph);
            clonedEntriesMap = (Map<Integer, GraphEntry>) entriesMapField.get(clonedGraph);



            makeSamples();
        } catch (Exception e) {
            throw new RuntimeException("Exception during graph building");
        }

    }


    private static void makeSamples() throws Exception{

        Map<Integer, GraphEntry> entriesMap = new HashMap<>();

        Question question = new Question("Это первый вопрос");
        QuestionNode questionNode = new QuestionNodeLinear(question, true);
        Map<String, Integer> links = new HashMap<>();
        links.put("next", 2);
        GraphEntry graphEntry = createGraphEntry(questionNode, links);
        entriesMap.put(1, graphEntry);

        QuestionNode root = questionNode;

        List<String> variantsOfAnswer = new ArrayList<>();
        variantsOfAnswer.add("Первый вариант");
        variantsOfAnswer.add("Второй вариант");
        question = new Question("Это третий вопрос", variantsOfAnswer, true);
        questionNode = new QuestionNodeForking(question, "Первый вариант", true);
        links = new HashMap<>();
        links.put("nextYes", 4);
        links.put("nextNo", 5);
        graphEntry = createGraphEntry(questionNode, links);
        entriesMap.put(3, graphEntry);

        question = new Question("Этот вопрос Вы видите, если выбрали первый вариант ответа");
        questionNode = new QuestionNodeLinear(question, true);
        links = new HashMap<>();
        links.put("next", 6);
        graphEntry = createGraphEntry(questionNode, links);
        entriesMap.put(4, graphEntry);

        question = new Question("Этот вопрос Вы видите, если выбрали второй вариант ответа");
        questionNode = new QuestionNodeLinear(question, false);
        links = new HashMap<>();
        links.put("next", 7);
        graphEntry = createGraphEntry(questionNode, links);
        entriesMap.put(5, graphEntry);

        question = new Question("Это продолжение ветки первого варианта ответа");
        questionNode = new QuestionNodeLinear(question, true);
        links = new HashMap<>();
        links.put("next", 7);
        graphEntry = createGraphEntry(questionNode, links);
        entriesMap.put(6, graphEntry);

        question = new Question("Здесь ветки вопросов сливаются");
        questionNode = new QuestionNodeLinear(question, true);
        links = new HashMap<>();
        links.put("next", 8);
        graphEntry = createGraphEntry(questionNode, links);
        entriesMap.put(7, graphEntry);

        variantsOfAnswer = new ArrayList<>();
        variantsOfAnswer.add("Первый вариант");
        variantsOfAnswer.add("Второй вариант");
        question = new Question("Начало цикла", variantsOfAnswer, true);
        questionNode = new QuestionNodeLinear(question, true);
        links = new HashMap<>();
        links.put("next", 9);
        graphEntry = createGraphEntry(questionNode, links);
        entriesMap.put(8, graphEntry);

        question = new Question("Тело цикла");
        questionNode = new QuestionNodeLinear(question, true);
        links = new HashMap<>();
        links.put("next", 9);
        graphEntry = createGraphEntry(questionNode, links);
        entriesMap.put(10, graphEntry);

        variantsOfAnswer = new ArrayList<>();
        variantsOfAnswer.add("Да");
        variantsOfAnswer.add("Нет");
        question = new Question("Пройти цикл еще раз?", variantsOfAnswer, false);
        questionNode = new QuestionNodeForking(question, "Да", true);
        links = new HashMap<>();
        links.put("nextYes", 8);
        links.put("nextNo", 11);
        graphEntry = createGraphEntry(questionNode, links);
        entriesMap.put(10, graphEntry);

        questionNode = new QuestionNodeTerminal();
        links = new HashMap<>();
        graphEntry = createGraphEntry(questionNode, links);
        entriesMap.put(11, graphEntry);

        sampleGraph = createGraph(root, entriesMap);
        sampleEntriesMap = entriesMap;
        sampleRoot = root;
    }

    private static Graph createGraph(QuestionNode root, Map<Integer, GraphEntry> entriesMap) throws Exception
    {
        Class[] args = new Class[2];
        args[0] = QuestionNode.class;
        args[1] = Map.class;
        return Graph.class.getDeclaredConstructor(args).newInstance(root, entriesMap);

    }

    private static GraphEntry createGraphEntry(QuestionNode questionNode, Map<String, Integer> links) throws Exception
    {
        Class[] args = new Class[2];
        args[0] = QuestionNode.class;
        args[1] = Map.class;
        return GraphEntry.class.getDeclaredConstructor(args).newInstance(questionNode, links);

    }

    @Test
    void checkIndexSets() {
        checkIndexSet(entriesMap.keySet());
        checkIndexSet(clonedEntriesMap.keySet());
    }

    @Test
    void checkNodeTypes() {
        checkNodeTypes(entriesMap);
        checkNodeTypes(clonedEntriesMap);
    }

    @Test
    void checkRoot() {
        checkRoot(entriesMap, root);
        checkRoot(clonedEntriesMap, clonedRoot);
    }

    @Test
    void checkQuestionTexts() {
        checkQuestionTexts(entriesMap);
        checkQuestionTexts(clonedEntriesMap);
    }

    @Test
    void checkVariantsOfAnswerTexts() throws Exception {
        checkVariantsOfAnswerTexts(entriesMap);
        checkVariantsOfAnswerTexts(clonedEntriesMap);
    }

    @Test
    void checkAllowedAnswers() {
        checkAllowedAnswers(entriesMap);
        checkAllowedAnswers(clonedEntriesMap);
    }

    @Test
    void checkSkippable() {
        checkSkippable(entriesMap);
        checkSkippable(clonedEntriesMap);
    }

    @Test
    void checkPattern() throws Exception {
        checkPattern(entriesMap);
        checkPattern(clonedEntriesMap);
    }

    @Test
    void checkLinks() throws Exception {
        checkLinks(entriesMap);
        checkLinks(clonedEntriesMap);
    }

    void checkLinks(Map<Integer, GraphEntry> mapToCheck) throws Exception {
        Field fieldNext = QuestionNodeLinear.class.getDeclaredField("next");
        fieldNext.setAccessible(true);
        QuestionNode next = (QuestionNode) fieldNext.get(mapToCheck.get(8).getNode());

        assertTrue(next == mapToCheck.get(9).getNode());

        Field fieldNextYes = QuestionNodeForking.class.getDeclaredField("nextYes");
        Field fieldNextNo = QuestionNodeForking.class.getDeclaredField("nextNo");
        fieldNextYes.setAccessible(true);
        fieldNextNo.setAccessible(true);
        QuestionNode nextYes = (QuestionNode) fieldNextYes.get(mapToCheck.get(10).getNode());
        QuestionNode nextNo = (QuestionNode) fieldNextNo.get(mapToCheck.get(10).getNode());

        assertTrue(nextYes == mapToCheck.get(8).getNode());
        assertTrue(nextNo == mapToCheck.get(11).getNode());
    }

    void checkPattern(Map<Integer, GraphEntry> mapToCheck) throws Exception {
        Field fieldPattern = QuestionNodeForking.class.getDeclaredField("answerPattern");
        fieldPattern.setAccessible(true);
        Pattern pattern = (Pattern) fieldPattern.get(mapToCheck.get(10).getNode());
        assertTrue(pattern.matcher("Да").matches());
        assertFalse(pattern.matcher("Нет").matches());
        assertFalse(pattern.matcher("random").matches());
    }

    void checkSkippable(Map<Integer, GraphEntry> mapToCheck) {
        assertTrue(mapToCheck.get(6).getNode().isSkippable());
        assertTrue(mapToCheck.get(7).getNode().isSkippable());
        assertFalse(mapToCheck.get(5).getNode().isSkippable());
    }

    void checkAllowedAnswers(Map<Integer, GraphEntry> mapToCheck) {
        assertTrue(mapToCheck.get(5).getNode().getQuestion().answerIsAllowed(new Answer("randomAnswer")));
        assertTrue(mapToCheck.get(8).getNode().getQuestion().answerIsAllowed(new Answer("randomAnswer")));
        assertTrue(mapToCheck.get(8).getNode().getQuestion().answerIsAllowed(new Answer("Первый вариант")));
        assertFalse(mapToCheck.get(10).getNode().getQuestion().answerIsAllowed(new Answer("Первый вариант")));
        assertTrue(mapToCheck.get(10).getNode().getQuestion().answerIsAllowed(new Answer("Нет")));
    }

    void checkVariantsOfAnswerTexts(Map<Integer, GraphEntry> mapToCheck) throws Exception {
        Field fieldVariantsOfAnswer = Question.class.getDeclaredField("variantsOfAnswer");
        fieldVariantsOfAnswer.setAccessible(true);
        List<String> variants = (List<String>) fieldVariantsOfAnswer.get(mapToCheck.get(8).getNode().getQuestion());
        assertTrue(variants.size() == 2);
        assertTrue(variants.get(0).equals("Первый вариант"));
        assertTrue(variants.get(1).equals("Второй вариант"));
    }

    void checkQuestionTexts(Map<Integer, GraphEntry> mapToCheck) {
        assertTrue(mapToCheck.get(4).getNode().getQuestion().getText()
                .equals("Этот вопрос Вы видите, если выбрали первый вариант ответа"));
        assertTrue(mapToCheck.get(10).getNode().getQuestion().getText()
                .equals("Пройти цикл еще раз?"));
    }

    void checkRoot(Map<Integer, GraphEntry> mapToCheck, QuestionNode root) {
        assertTrue(mapToCheck.get(1).getNode() == root);
    }

    void checkNodeTypes(Map<Integer, GraphEntry> mapToCheck) {
        assertTrue(mapToCheck.get(1).getNode() instanceof QuestionNodeLinear);
        assertTrue(mapToCheck.get(3).getNode() instanceof QuestionNodeForking);
        assertTrue(mapToCheck.get(11).getNode() instanceof QuestionNodeTerminal);
    }

    void checkIndexSet(Set<Integer> setToCheck) {
        assertTrue(setToCheck.containsAll(sampleIndexSet));
        assertTrue(sampleIndexSet.containsAll(setToCheck));
    }

}
