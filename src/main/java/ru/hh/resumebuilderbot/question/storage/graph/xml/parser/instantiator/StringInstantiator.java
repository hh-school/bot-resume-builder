package ru.hh.resumebuilderbot.question.storage.graph.xml.parser.instantiator;

import org.w3c.dom.Node;

public class StringInstantiator implements Instantiator {
    @Override
    public Object makeInstance(Node stringNode) {
        return stringNode.getTextContent();
    }
}
