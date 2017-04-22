package ru.hh.resumebuilderbot.question.storage.builder;

import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.builder.xml.parser.XMLEntry;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;
import ru.hh.resumebuilderbot.question.storage.node.basic.QuestionNodeForking;
import ru.hh.resumebuilderbot.question.storage.node.basic.QuestionNodeLinear;
import ru.hh.resumebuilderbot.question.storage.node.basic.QuestionNodeTerminal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphEntry {
    private QuestionNode node;
    private Map<String, Integer> indexLinks;

    private GraphEntry(QuestionNode node, Map<String, Integer> indexLinks) {
        this.node = node;
        this.indexLinks = indexLinks;
    }

    static GraphEntry fromXMLEntry(XMLEntry xmlEntry) throws IOException {
        Map<String, Integer> indexLinks = xmlEntry.getLinks();
        Map<String, String> classData = xmlEntry.getClassData();
        Question question = xmlEntry.getQuestion();
        if (xmlEntry.getType().equals("terminal")) {
            return makeTerminalEntry(indexLinks, classData, question);
        }
        if (xmlEntry.getType().equals("linear")) {
            return makeLinearEntry(indexLinks, classData, question);
        }
        if (xmlEntry.getType().equals("forking")) {
            return makeForkingEntry(indexLinks, classData, question);
        }
        throw new IOException("Error parsing XML: node type is invalid");
    }

    private static GraphEntry makeTerminalEntry(Map<String, Integer> indexLinks,
                                                Map<String, String> classData,
                                                Question question) {
        QuestionNode terminalNode = new QuestionNodeTerminal();
        return new GraphEntry(terminalNode, indexLinks);
    }

    private static GraphEntry makeLinearEntry(Map<String, Integer> indexLinks,
                                              Map<String, String> classData,
                                              Question question) {
        boolean isSkippable = Boolean.parseBoolean(classData.get("skippable"));
        QuestionNodeLinear linearNode = new QuestionNodeLinear(question, isSkippable);
        return new GraphEntry(linearNode, indexLinks);
    }

    private static GraphEntry makeForkingEntry(Map<String, Integer> indexLinks,
                                               Map<String, String> classData,
                                               Question question) {
        boolean isSkippable = Boolean.parseBoolean(classData.get("skippable"));
        String pattern = classData.get("pattern");
        QuestionNodeForking forkingNode = new QuestionNodeForking(question, pattern, isSkippable);
        return new GraphEntry(forkingNode, indexLinks);
    }

    GraphEntry cloneContent() {
        return new GraphEntry(node.cloneContent(), indexLinks);
    }

    public QuestionNode getNode() {
        return node;
    }

    void setLinks(Map<Integer, QuestionNode> nodesMap) {
        Map<String, QuestionNode> objectLinks = new HashMap<>();
        indexLinks.forEach((linkName, linkIndex) -> objectLinks.put(linkName, nodesMap.get(linkIndex)));
        node.setLinks(objectLinks);
    }


}
