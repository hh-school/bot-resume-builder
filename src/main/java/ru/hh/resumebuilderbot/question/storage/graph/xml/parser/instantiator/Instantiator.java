package ru.hh.resumebuilderbot.question.storage.graph.xml.parser.instantiator;

import org.w3c.dom.Node;

public interface Instantiator {
    Object makeInstance(Node node);
}
