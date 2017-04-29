package ru.hh.resumebuilderbot.question.storage.graph.xml.parser.instantiator;

import org.w3c.dom.Node;

public class IntegerInstantiator implements Instantiator {
    @Override
    public Object makeInstance(Node integerNode) {
        return Integer.parseInt(integerNode.getTextContent());
    }
}
