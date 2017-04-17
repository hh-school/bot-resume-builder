package ru.hh.resumebuilderbot.question.storage.builder.xml.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import ru.hh.resumebuilderbot.question.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class XMLEntry {

    private static final boolean isRootByDefault = false;
    private static final String isSkippableByDefault = "true";

    private final static Logger log = LoggerFactory.getLogger(XMLEntry.class);

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
            log.error("XML schema error : <question> is not found inside non-terminal <node>");
            throw new IOException("<question> is not found inside non-terminal <node>");
        }
        Question question = makeQuestion(questionNode.get());

        if (type.equals("linear")) {
            int nextId = Integer.parseInt(graphNodeAttributes.getNamedItem("next").getNodeValue());
            XMLEntry entry = new XMLEntry(id, nextId, question, classData);
            entry.setRoot(isRoot);
            return entry;
        }
        int nextYes = Integer.parseInt(graphNodeAttributes.getNamedItem("nextYes").getNodeValue());
        int nextNo = Integer.parseInt(graphNodeAttributes.getNamedItem("nextNo").getNodeValue());
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
