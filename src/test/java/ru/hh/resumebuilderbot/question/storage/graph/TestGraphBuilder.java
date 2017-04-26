package ru.hh.resumebuilderbot.question.storage.graph;

import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNode;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNodeForking;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNodeLinear;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNodeNonTerminal;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNodeTerminal;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.saver.DatabaseSaver;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.saver.Saver;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator.PhoneNumberValidator;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator.Validator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestGraphBuilder {
    public static Graph build() throws Exception {
        Map<Integer, GraphEntry> entriesMap = new HashMap<>();

        Question question = new Question("first question");
        QuestionNode questionNode = new QuestionNodeLinear(question, true);
        Map<String, Integer> links = new HashMap<>();
        links.put("next", 3);
        GraphEntry graphEntry = createGraphEntry(questionNode, links);
        entriesMap.put(1, graphEntry);

        List<String> variantsOfAnswer = new ArrayList<>();
        variantsOfAnswer.add("Первый вариант");
        variantsOfAnswer.add("Второй вариант");
        question = new Question("second question", variantsOfAnswer, true);
        questionNode = new QuestionNodeForking(question, "Первый вариант", true);
        links = new HashMap<>();
        links.put("nextYes", 4);
        links.put("nextNo", 5);
        graphEntry = createGraphEntry(questionNode, links);
        entriesMap.put(3, graphEntry);

        question = new Question("Этот вопрос Вы видите, если выбрали первый вариант ответа");
        questionNode = new QuestionNodeLinear(question, true);
        setValidator((QuestionNodeNonTerminal) questionNode, new PhoneNumberValidator());
        setSaver((QuestionNodeNonTerminal) questionNode, new DatabaseSaver("phone"));
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
        links.put("next", 10);
        graphEntry = createGraphEntry(questionNode, links);
        entriesMap.put(9, graphEntry);

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

        return createGraph(1, createGraphEntriesMap(entriesMap));
    }

    private static Graph createGraph(int rootIndex, GraphEntriesMap entriesMap) throws Exception {
        Constructor<Graph> constructor = Graph.class.getDeclaredConstructor(int.class, GraphEntriesMap.class);
        constructor.setAccessible(true);
        return constructor.newInstance(rootIndex, entriesMap);
    }

    private static GraphEntry createGraphEntry(QuestionNode questionNode, Map<String, Integer> links) throws Exception {
        Constructor<GraphEntry> constructor = GraphEntry.class.getDeclaredConstructor(QuestionNode.class, Map.class);
        constructor.setAccessible(true);
        return constructor.newInstance(questionNode, links);
    }

    private static GraphEntriesMap createGraphEntriesMap(Map<Integer, GraphEntry> entriesMap) throws Exception {
        Constructor<GraphEntriesMap> constructor = GraphEntriesMap.class.getDeclaredConstructor(Map.class);
        constructor.setAccessible(true);
        return constructor.newInstance(entriesMap);
    }

    private static void setValidator(QuestionNodeNonTerminal questionNode, Validator validator) throws Exception {
        Field field = questionNode.getClass().getSuperclass().getDeclaredField("validator");
        field.setAccessible(true);
        field.set(questionNode, validator);
    }

    private static void setSaver(QuestionNodeNonTerminal questionNode, Saver saver) throws Exception {
        Field field = questionNode.getClass().getSuperclass().getDeclaredField("saver");
        field.setAccessible(true);
        field.set(questionNode, saver);
    }
}
