package ru.hh.resumebuilderbot.question.storage.graph.xml.parser.instantiator;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.QuestionSuggest;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.XMLAsStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionInstantiator implements Instantiator {
    @Override
    public Object makeInstance(Node questionNode) {
        NamedNodeMap attributes = questionNode.getAttributes();

        String text = attributes.getNamedItem("text").getNodeValue();
        QuestionSuggest suggest = Question.getDefaultSuggest();

        Optional<Node> suggestTypeNode = XMLAsStream.getFirstChildByName(questionNode, "suggestType");
        if (suggestTypeNode.isPresent()) {
            suggest = QuestionSuggest.valueOf(suggestTypeNode.get().getTextContent());
        }

        Optional<Node> variantsOfAnswerNode = XMLAsStream.getFirstChildByName(questionNode, "variantsOfAnswer");
        if (variantsOfAnswerNode.isPresent()) {
            boolean otherVariantsAllowed = Boolean.parseBoolean(variantsOfAnswerNode.get()
                    .getAttributes()
                    .getNamedItem("othersAllowed")
                    .getNodeValue());
            List<String> variantsOfAnswer = new ArrayList<>();
            XMLAsStream.fromParentNode(variantsOfAnswerNode.get()).forEach((x) ->
                    variantsOfAnswer.add(x.getAttributes().getNamedItem("text").getNodeValue()));
            return new Question(text, variantsOfAnswer, otherVariantsAllowed, suggest);
        }
        return new Question(text, suggest);
    }
}
