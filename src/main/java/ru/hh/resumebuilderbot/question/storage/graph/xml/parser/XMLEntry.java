package ru.hh.resumebuilderbot.question.storage.graph.xml.parser;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import ru.hh.resumebuilderbot.question.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XMLEntry {

    private static final boolean isRootByDefault = false;
    private static final String isSkippableByDefault = "true";

    private int index;
    private String type;
    private Question question;
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

    static XMLEntry fromGraphNode(Node graphNode) {
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


        Question question = optionalQuestionNode.map(x -> makeQuestion(x)).orElse(null);

        XMLEntry entry = new XMLEntry(type, id, question, links, classData);
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
        return linksStream.map(Node::getAttributes)
                .collect(Collectors.toMap(
                        x -> x.getNamedItem("name").getNodeValue(),
                        x -> Integer.parseInt(x.getNamedItem("value").getNodeValue())));
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
