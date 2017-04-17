package ru.hh.resumebuilderbot.question.storage.graph.xml.parser.instantiator;

import org.w3c.dom.Node;

import java.util.regex.Pattern;

public class PatternInstantiator implements Instantiator {
    @Override
    public Object makeInstance(Node patternNode) {
        return Pattern.compile(patternNode.getTextContent());
    }
}
