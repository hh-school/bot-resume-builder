package ru.hh.resumebuilderbot.question.storage.graph.xml.parser.instantiator;

import org.w3c.dom.Node;

public class BooleanInstantiator implements Instantiator {
    @Override
    public Object makeInstance(Node booleanNode) {
        return Boolean.parseBoolean(booleanNode.getTextContent());
    }
}
