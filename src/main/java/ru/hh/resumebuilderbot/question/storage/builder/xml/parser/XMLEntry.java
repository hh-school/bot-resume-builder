package ru.hh.resumebuilderbot.question.storage.builder.xml.parser;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class XMLEntry {
    private int index;
    private String type;
    private int nextIndex;
    private String text;
    private List<String> allowedAnswers;
    private String pattern;
    private int nextYes;
    private int nextNo;
    private boolean isRoot;

    private XMLEntry(int index, int nextIndex, String text, List<String> allowedAnswers) {
        this.index = index;
        this.type = "linear";
        this.nextIndex = nextIndex;
        this.text = text;
        this.allowedAnswers = allowedAnswers;
    }

    private XMLEntry(String type, int index, String text, List<String> allowedAnswers, String pattern, int nextYes,
                     int nextNo) {
        this.index = index;
        this.type = type;
        this.text = text;
        this.allowedAnswers = allowedAnswers;
        this.pattern = pattern;
        this.nextYes = nextYes;
        this.nextNo = nextNo;
    }

    private XMLEntry(int index) {
        this.type = "terminal";
        this.index = index;
    }

    static XMLEntry fromGraphNode(Node graphNode) throws IOException {
        Optional<Node> question = XMLNodeListStream.fromParentNode(graphNode).findFirst();
        NamedNodeMap graphNodeAttributes = graphNode.getAttributes();
        int id = Integer.parseInt(graphNodeAttributes.getNamedItem("id").getNodeValue());

        Optional<Node> attributeRoot = Optional.ofNullable(graphNodeAttributes.getNamedItem("root"));
        boolean isRoot = attributeRoot.isPresent() && Boolean.parseBoolean(attributeRoot.get().getNodeValue());

        String type = graphNodeAttributes.getNamedItem("type").getNodeValue();

        if (type.equals("terminal")) {
            XMLEntry entry = new XMLEntry(id);
            entry.setRoot(isRoot);
            return entry;
        }

        if (!question.isPresent()) {
            throw new IOException("<question> not found inside non-terminal <node>");
        }
        NamedNodeMap attributes = question.get().getAttributes();

        String text = attributes.getNamedItem("text").getNodeValue();

        List<String> allowedAnswers = new ArrayList<>();

        XMLNodeListStream.fromParentNode(question.get()).forEach((x) ->
                allowedAnswers.add(x.getAttributes().getNamedItem("text").getNodeValue()));

        if (type.equals("linear")) {
            int nextId = Integer.parseInt(graphNodeAttributes.getNamedItem("next").getNodeValue());
            XMLEntry entry = new XMLEntry(id, nextId, text, allowedAnswers);
            entry.setRoot(isRoot);
            return entry;
        }
        String pattern = attributes.getNamedItem("pattern").getNodeValue();
        int nextYes = 0;
        int nextNo = 0;
        if (type.equals("forking")) {
            nextYes = Integer.parseInt(attributes.getNamedItem("nextYes").getNodeValue());
            nextNo = Integer.parseInt(attributes.getNamedItem("nextNo").getNodeValue());
        } else {
            nextYes = Integer.parseInt(attributes.getNamedItem("nextIn").getNodeValue());
            nextNo = Integer.parseInt(attributes.getNamedItem("nextOut").getNodeValue());
        }
        XMLEntry entry = new XMLEntry(type, id, text, allowedAnswers, pattern, nextYes, nextNo);
        entry.setRoot(isRoot);
        return entry;
    }

    public boolean isRoot() {
        return isRoot;
    }

    private void setRoot(boolean root) {
        isRoot = root;
    }

    public int getIndex() {
        return index;
    }

    public int getNextIndex() {
        return nextIndex;
    }

    public String getText() {
        return text;
    }

    public List<String> getAllowedAnswers() {
        return allowedAnswers;
    }

    public String getType() {
        return type;
    }

    public String getPattern() {
        return pattern;
    }

    public int getNextYes() {
        return nextYes;
    }

    public int getNextNo() {
        return nextNo;
    }
}
