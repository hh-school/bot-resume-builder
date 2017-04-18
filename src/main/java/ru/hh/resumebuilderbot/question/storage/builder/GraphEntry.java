package ru.hh.resumebuilderbot.question.storage.builder;

import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.builder.xml.parser.XMLEntry;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;
import ru.hh.resumebuilderbot.question.storage.node.basic.QuestionNodeForking;
import ru.hh.resumebuilderbot.question.storage.node.basic.QuestionNodeLinear;
import ru.hh.resumebuilderbot.question.storage.node.basic.QuestionNodeTerminal;

import java.io.IOException;
import java.util.Map;

public class GraphEntry {
    private QuestionNode node;
    private Map<String, Integer> links;

    private GraphEntry(QuestionNode node, Map<String, Integer> links) {
        this.node = node;
        this.links = links;
    }

    static GraphEntry fromXMLEntry(XMLEntry xmlEntry) throws IOException {
        Map<String, Integer> links = xmlEntry.getLinks();
        Map<String, String> classData = xmlEntry.getClassData();
        Question question = xmlEntry.getQuestion();
        if (xmlEntry.getType().equals("terminal")) {
            return makeTerminalEntry(links, classData, question);
        }
        if (xmlEntry.getType().equals("linear")) {
            return makeLinearEntry(links, classData, question);
        }
        if (xmlEntry.getType().equals("forking")) {
            return makeForkingEntry(links, classData, question);
        }
        throw new IOException("Error parsing XML: node type is invalid");
    }

    private static GraphEntry makeTerminalEntry(Map<String, Integer> links,
                                                Map<String, String> classData,
                                                Question question) {
        QuestionNode terminalNode = new QuestionNodeTerminal();
        return new GraphEntry(terminalNode, links);
    }

    private static GraphEntry makeLinearEntry(Map<String, Integer> links,
                                              Map<String, String> classData,
                                              Question question) {
        boolean isSkippable = Boolean.parseBoolean(classData.get("skippable"));
        QuestionNodeLinear linearNode = new QuestionNodeLinear(question, isSkippable);
        return new GraphEntry(linearNode, links);
    }

    private static GraphEntry makeForkingEntry(Map<String, Integer> links,
                                               Map<String, String> classData,
                                               Question question) {
        boolean isSkippable = Boolean.parseBoolean(classData.get("skippable"));
        String pattern = classData.get("pattern");
        QuestionNodeForking forkingNode = new QuestionNodeForking(question, pattern, isSkippable);
        return new GraphEntry(forkingNode, links);
    }

    GraphEntry cloneContent() {
        return new GraphEntry(node.cloneContent(), links);
    }

    public QuestionNode getNode() {
        return node;
    }

    void setLinks(Map<Integer, QuestionNode> nodesMap) {
        if (node instanceof QuestionNodeLinear) {
            QuestionNodeLinear linearNode = (QuestionNodeLinear) node;
            linearNode.setNext(nodesMap.get(links.get("next")));
        }
        if (node instanceof QuestionNodeForking) {
            QuestionNodeForking forkingNode = (QuestionNodeForking) node;
            forkingNode.setNextYes(nodesMap.get(links.get("nextYes")));
            forkingNode.setNextNo(nodesMap.get(links.get("nextNo")));
        }
    }


}
