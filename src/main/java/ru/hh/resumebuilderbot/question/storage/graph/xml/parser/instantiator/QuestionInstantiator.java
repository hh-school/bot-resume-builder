package ru.hh.resumebuilderbot.question.storage.graph.xml.parser.instantiator;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.ReplyKeyboardEnum;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.XMLAsStream;
import ru.hh.resumebuilderbot.telegram.handler.suggest.SuggestType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionInstantiator implements Instantiator {
    @Override
    public Object makeInstance(Node questionNode) {
        NamedNodeMap attributes = questionNode.getAttributes();

        String text = attributes.getNamedItem("text").getNodeValue();
        SuggestType suggestType = Question.DEFAULT_SUGGEST_TYPE;
        ReplyKeyboardEnum replyKeyboardEnum = Question.DEFAULT_KEYBOARD;

        Optional<Node> suggestTypeNode = XMLAsStream.getFirstChildByName(questionNode, "suggestType");
        if (suggestTypeNode.isPresent()) {
            suggestType = SuggestType.valueOf(suggestTypeNode.get().getTextContent());
        }

        Optional<Node> keyboardTypeNode = XMLAsStream.getFirstChildByName(questionNode, "keyboardType");
        if (keyboardTypeNode.isPresent()) {
            replyKeyboardEnum = ReplyKeyboardEnum.valueOf(keyboardTypeNode.get().getTextContent());
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
            return new Question(text, variantsOfAnswer, otherVariantsAllowed, suggestType, replyKeyboardEnum);
        }
        return new Question(text, suggestType, replyKeyboardEnum);
    }
}
