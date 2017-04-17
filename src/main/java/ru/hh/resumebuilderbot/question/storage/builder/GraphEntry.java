package ru.hh.resumebuilderbot.question.storage.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.builder.xml.parser.XMLEntry;
import ru.hh.resumebuilderbot.question.storage.builder.xml.parser.XMLValidator;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNodeForking;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNodeLinear;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNodeTerminal;

import java.io.IOException;
import java.util.Map;

public class GraphEntry {

    private final static Logger log = LoggerFactory.getLogger(XMLValidator.class);

    private QuestionNode node;
    private int nextIndex;
    private int nextIndexYes;
    private int nextIndexNo;

    private GraphEntry(QuestionNode node, int nextIndex) {
        this.node = node;
        this.nextIndex = nextIndex;
    }

    private GraphEntry(QuestionNode node, int nextIndexYes, int nextIndexNo) {
        this.node = node;
        this.nextIndexYes = nextIndexYes;
        this.nextIndexNo = nextIndexNo;
    }

    private GraphEntry(QuestionNode node, int nextIndex, int nextIndexYes, int nextIndexNo) {
        this.node = node;
        this.nextIndex = nextIndex;
        this.nextIndexYes = nextIndexYes;
        this.nextIndexNo = nextIndexNo;
    }

    static GraphEntry fromXMLEntry(XMLEntry xmlEntry) throws IOException {
        Map<String, String> classData = xmlEntry.getClassData();
        if (xmlEntry.getType().equals("terminal")) {
            return makeTerminalEntry(xmlEntry, classData);
        }
        if (xmlEntry.getType().equals("linear")) {
            return makeLinearEntry(xmlEntry, classData);
        }
        if (xmlEntry.getType().equals("forking")) {
            return makeForkingEntry(xmlEntry, classData);
        }
        log.error("Error parsing XML: unknown node type");
        throw new IOException("Error parsing XML: node type is invalid");
    }

    private static GraphEntry makeTerminalEntry(XMLEntry xmlEntry, Map<String, String> classData) {
        int index = xmlEntry.getIndex();
        QuestionNode terminalNode = new QuestionNodeTerminal();
        return new GraphEntry(terminalNode, index);
    }

    private static GraphEntry makeLinearEntry(XMLEntry xmlEntry, Map<String, String> classData) {
        boolean isSkippable = Boolean.parseBoolean(classData.get("skippable"));
        Question question = xmlEntry.getQuestion();
        QuestionNodeLinear linearNode = new QuestionNodeLinear(question, isSkippable);
        int nextIndex = xmlEntry.getNextIndex();
        return new GraphEntry(linearNode, nextIndex);
    }

    private static GraphEntry makeForkingEntry(XMLEntry xmlEntry, Map<String, String> classData) {
        boolean isSkippable = Boolean.parseBoolean(classData.get("skippable"));
        Question question = xmlEntry.getQuestion();
        String pattern = classData.get("pattern");
        int nextIndexYes = xmlEntry.getNextYes();
        int nextIndexNo = xmlEntry.getNextNo();
        QuestionNodeForking forkingNode = new QuestionNodeForking(question, pattern, isSkippable);
        return new GraphEntry(forkingNode, nextIndexYes, nextIndexNo);
    }

    GraphEntry cloneContent() {
        return new GraphEntry(node.cloneContent(), nextIndex, nextIndexYes, nextIndexNo);
    }

    public QuestionNode getNode() {
        return node;
    }

    void setLinks(Map<Integer, QuestionNode> nodesMap) {
        if (node instanceof QuestionNodeLinear) {
            QuestionNodeLinear linearNode = (QuestionNodeLinear) node;
            linearNode.setNext(nodesMap.get(nextIndex));
        }
        if (node instanceof QuestionNodeForking) {
            QuestionNodeForking forkingNode = (QuestionNodeForking) node;
            forkingNode.setNextYes(nodesMap.get(nextIndexYes));
            forkingNode.setNextNo(nodesMap.get(nextIndexNo));
        }
    }


}
