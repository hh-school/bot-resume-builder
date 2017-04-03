package ru.hh.resumebuilderbot.question.storage.builder;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
    public List<Entry> parse(String filename) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(filename);
            Node root = document.getDocumentElement();
            NodeList questions = root.getChildNodes();

            List<Entry> result = new ArrayList<>();

            for (int i = 0; i < questions.getLength(); i++) {
                Node question = questions.item(i);
                if (question.getNodeType() != Node.TEXT_NODE) {
                    NamedNodeMap attributes = question.getAttributes();
                    int id = Integer.parseInt(attributes.getNamedItem("id").getNodeValue());
                    String text = attributes.getNamedItem("text").getNodeValue();
                    Node nextIdNode = attributes.getNamedItem("next");
                    boolean hasNext = (nextIdNode != null);
                    int nextId = 0;
                    if (hasNext) {
                        nextId = Integer.parseInt(nextIdNode.getNodeValue());
                    }

                    List<String> allowedAnswers = new ArrayList<>();
                    NodeList allowedAnswerNodes = question.getChildNodes();
                    for (int j = 0; j < allowedAnswerNodes.getLength(); j++) {
                        Node allowedAnswer = allowedAnswerNodes.item(j);
                        if (allowedAnswer.getNodeType() != Node.TEXT_NODE) {
                            NamedNodeMap allowedAnswerAttributes = allowedAnswer.getAttributes();
                            allowedAnswers.add(allowedAnswerAttributes.getNamedItem("text").getNodeValue());
                        }
                    }
                    result.add(new Entry(id, hasNext, nextId, text, allowedAnswers));
                }
            }
            return result;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public class Entry {
        private int index;
        private boolean hasNextIndex;
        private int nextIndex;
        private String text;
        private List<String> allowedAnswers;

        public Entry(int index, boolean hasNextIndex, int nextIndex, String text, List<String> allowedAnswers) {
            this.index = index;
            this.hasNextIndex = hasNextIndex;
            this.nextIndex = nextIndex;
            this.text = text;
            this.allowedAnswers = allowedAnswers;
        }

        public int getIndex() {
            return index;
        }

        public boolean hasNextIndex() {
            return hasNextIndex;
        }

        public int getNextIndex() {
            return nextIndex;
        }

        public String getText() {
            return text;
        }

        public List<String> getAllowedAnswers() {
            return allowedAnswers;
        }
    }
}
