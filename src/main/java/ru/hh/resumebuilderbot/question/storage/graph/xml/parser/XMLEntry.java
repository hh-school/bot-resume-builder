package ru.hh.resumebuilderbot.question.storage.graph.xml.parser;

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
    private Question question;
    private boolean isRoot;
    private Map<String, String> classData;

    private Map<String, Integer> links;

    private XMLEntry(String type, int index, Question question, Map<String, Integer> links,
                     Map<String, String> classData) {
        this.index = index;
        this.type = type;
        this.question = question;
        this.links = links;
        this.classData = classData;
        setDefaultValues();
    }

    static XMLEntry fromGraphNode(Node graphNode) throws IOException {
        NamedNodeMap graphNodeAttributes = graphNode.getAttributes();

        Optional<Node> optionalQuestionNode = XMLNodeListStream.getFirstChildByName(graphNode, "question");
        Optional<Node> classDataNode = XMLNodeListStream.getFirstChildByName(graphNode, "classData");
        Map<String, String> classData = classDataNode.map(XMLEntry::parseClassData).orElse(new HashMap<>());
        int id = Integer.parseInt(graphNodeAttributes.getNamedItem("id").getNodeValue());

        Map<String, Integer> links = parseLinks(XMLNodeListStream.getFirstChildByName(graphNode, "links"));

        Optional<Node> attributeRoot = Optional.ofNullable(graphNodeAttributes.getNamedItem("root"));
        boolean isRoot = attributeRoot
                .map((x) -> Boolean.parseBoolean(x.getNodeValue()))
                .orElse(isRootByDefault);

        String type = graphNodeAttributes.getNamedItem("type").getNodeValue();

        Question question = makeQuestion(optionalQuestionNode);

        XMLEntry entry = new XMLEntry(type, id, question, links, classData);
        entry.setRoot(isRoot);
        return entry;
    }

    private static Map<String, String> parseClassData(Node classDataNode) {
        Map<String, String> result = new HashMap<>();
        XMLNodeListStream.fromParentNode(classDataNode)
                .forEach(x -> result.put(x.getNodeName(), x.getTextContent()));
        return result;
    }

    private static Question makeQuestion(Optional<Node> optionalQuestionNode) {

        Node questionNode = null;
        if (optionalQuestionNode.isPresent()) {
            questionNode = optionalQuestionNode.get();
        } else {
            return null;
        }

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

    public Map<String, Integer> getLinks() {
        return links;
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

    public String getType() {
        return type;
    }

    public Question getQuestion() {
        return question;
    }
}
