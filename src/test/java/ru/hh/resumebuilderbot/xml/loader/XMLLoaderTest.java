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

import java.lang.reflect.Field;
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
            e.printStackTrace();
        }

    }


    private static void makeSamples() {
        sampleIndexSet.add(1);
        sampleIndexSet.add(3);
        sampleIndexSet.add(4);
        sampleIndexSet.add(5);
        sampleIndexSet.add(6);
        sampleIndexSet.add(7);
        sampleIndexSet.add(8);
        sampleIndexSet.add(9);
        sampleIndexSet.add(10);
        sampleIndexSet.add(11);
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
