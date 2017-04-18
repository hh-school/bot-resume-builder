package ru.hh.resumebuilderbot.question.storage.builder.xml.parser;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import ru.hh.resumebuilderbot.question.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class XMLEntry {

    private static final boolean isRootByDefault = false;
    private static final String isSkippableByDefault = "true";

    private int index;
    private String type;
    private int nextIndex;
    private Question question;
    private int nextYes;
    private int nextNo;
    private boolean isRoot;
    private Map<String, String> classData;

    private XMLEntry(int index, int nextIndex, Question question, Map<String, String> classData) {
        this.index = index;
        this.type = "linear";
        this.nextIndex = nextIndex;
        this.question = question;
        this.classData = classData;
        setDefaultValues();
    }

    private XMLEntry(String type, int index, Question question, int nextYes, int nextNo,
                     Map<String, String> classData) {
        this.index = index;
        this.type = type;
        this.question = question;
        this.nextYes = nextYes;
        this.nextNo = nextNo;
        this.classData = classData;
        setDefaultValues();
    }

    private XMLEntry(int index) {
        this.type = "terminal";
        this.index = index;
    }

    static XMLEntry fromGraphNode(Node graphNode) throws IOException {
        NamedNodeMap graphNodeAttributes = graphNode.getAttributes();

        Optional<Node> questionNode = XMLNodeListStream.getFirstChildByName(graphNode, "question");
        Optional<Node> classDataNode = XMLNodeListStream.getFirstChildByName(graphNode, "classData");
        Map<String, String> classData = classDataNode.map(XMLEntry::parseClassData).orElse(new HashMap<>());
        int id = Integer.parseInt(graphNodeAttributes.getNamedItem("id").getNodeValue());

        Map<String, Integer> links = parseLinks(XMLNodeListStream.getFirstChildByName(graphNode, "links"));

        Optional<Node> attributeRoot = Optional.ofNullable(graphNodeAttributes.getNamedItem("root"));
        boolean isRoot = attributeRoot
                .map((x) -> Boolean.parseBoolean(x.getNodeValue()))
                .orElse(isRootByDefault);

        String type = graphNodeAttributes.getNamedItem("type").getNodeValue();

        if (type.equals("terminal")) {
            XMLEntry entry = new XMLEntry(id);
            entry.setRoot(isRoot);
            return entry;
        }

        if (!questionNode.isPresent()) {
            throw new IOException("<question> not found inside non-terminal <node>");
        }
        Question question = makeQuestion(questionNode.get());

        if (type.equals("linear")) {
            int nextId = links.get("next");
            XMLEntry entry = new XMLEntry(id, nextId, question, classData);
            entry.setRoot(isRoot);
            return entry;
        }
        int nextYes = links.get("nextYes");
        int nextNo = links.get("nextNo");
        XMLEntry entry = new XMLEntry(type, id, question, nextYes, nextNo, classData);
        entry.setRoot(isRoot);
        return entry;
    }

    private static Map<String, String> parseClassData(Node classDataNode) {
        Map<String, String> result = new HashMap<>();
        XMLNodeListStream.fromParentNode(classDataNode)
                .forEach(x -> result.put(x.getNodeName(), x.getTextContent()));
        return result;
    }

    private static Question makeQuestion(Node questionNode) {
        NamedNodeMap attributes = questionNode.getAttributes();

        String text = attributes.getNamedItem("text").getNodeValue();

        Optional<Node> variantsOfAnswerNode = XMLNodeListStream.fromParentNode(questionNode).findFirst();
        if (variantsOfAnswerNode.isPresent()) {
            boolean otherVariantsAllowed = Boolean.parseBoolean(variantsOfAnswerNode.get()
                    .getAttributes()
                    .getNamedItem("othersAllowed")
                    .getNodeValue());
            List<String> variantsOfAnswer = new ArrayList<>();
            XMLNodeListStream.fromParentNode(variantsOfAnswerNode.get()).forEach((x) ->
                    variantsOfAnswer.add(x.getAttributes().getNamedItem("text").getNodeValue()));
            return new Question(text, variantsOfAnswer, otherVariantsAllowed);
        }
        return new Question(text);
    }

    private static Map<String, Integer> parseLinks(Optional<Node> optionalLinksNode) {
        Map<String, Integer> result = new HashMap<>();
        if (!optionalLinksNode.isPresent()) {
            return result;
        }
        Stream<Node> linksStream = XMLNodeListStream.fromParentNode(optionalLinksNode.get());
        linksStream.forEach(x -> parseLink(x, result));
        return result;
    }

    private static void parseLink(Node src, Map<String, Integer> dest) {
        NamedNodeMap attributes = src.getAttributes();
        String name = attributes.getNamedItem("name").getNodeValue();
        int value = Integer.parseInt(attributes.getNamedItem("value").getNodeValue());
        dest.put(name, value);

    }

    public Map<String, String> getClassData() {
        return classData;
    }

    private void setDefaultValues() {
        classData.putIfAbsent("skippable", isSkippableByDefault);
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

    public String getType() {
        return type;
    }

    public int getNextYes() {
        return nextYes;
    }

    public int getNextNo() {
        return nextNo;
    }

    public Question getQuestion() {
        return question;
    }
}
