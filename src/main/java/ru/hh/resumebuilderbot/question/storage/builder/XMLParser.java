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
                    String type = attributes.getNamedItem("type").getNodeValue();

                    if (type.equals("terminal")) {
                        result.add(new Entry(id));
                        continue;
                    }

                    String text = attributes.getNamedItem("text").getNodeValue();

                    List<String> allowedAnswers = new ArrayList<>();
                    NodeList allowedAnswerNodes = question.getChildNodes();
                    for (int j = 0; j < allowedAnswerNodes.getLength(); j++) {
                        Node allowedAnswer = allowedAnswerNodes.item(j);
                        if (allowedAnswer.getNodeType() != Node.TEXT_NODE) {
                            NamedNodeMap allowedAnswerAttributes = allowedAnswer.getAttributes();
                            allowedAnswers.add(allowedAnswerAttributes.getNamedItem("text").getNodeValue());
                        }
                    }

                    if (type.equals("linear")) {
                        int nextId = Integer.parseInt(attributes.getNamedItem("next").getNodeValue());
                        result.add(new Entry(id, nextId, text, allowedAnswers));
                        continue;
                    }
                    String pattern = attributes.getNamedItem("pattern").getNodeValue();
                    int nextYes = 0;
                    int nextNo = 0;
                    if (type.equals("forking")) {
                        nextYes = Integer.parseInt(attributes.getNamedItem("nextYes").getNodeValue());
                        nextNo = Integer.parseInt(attributes.getNamedItem("nextNo").getNodeValue());
                    } else {
                        nextYes = Integer.parseInt(attributes.getNamedItem("nextIn").getNodeValue());
                        nextNo = Integer.parseInt(attributes.getNamedItem("nextOut").getNodeValue());
                    }

                    result.add(new Entry(type, id, text, allowedAnswers, pattern, nextYes, nextNo));
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
        private String type;
        private int nextIndex;
        private String text;
        private List<String> allowedAnswers;
        private String pattern;
        private int nextYes;
        private int nextNo;

        public Entry(int index, int nextIndex, String text, List<String> allowedAnswers) {
            this.index = index;
            this.type = "linear";
            this.nextIndex = nextIndex;
            this.text = text;
            this.allowedAnswers = allowedAnswers;
        }

        public Entry(String type, int index, String text, List<String> allowedAnswers, String pattern, int nextYes, int nextNo) {
            this.index = index;
            this.type = type;
            this.text = text;
            this.allowedAnswers = allowedAnswers;
            this.pattern = pattern;
            this.nextYes = nextYes;
            this.nextNo = nextNo;
        }

        public Entry(int index) {
            this.type = "terminal";
            this.index = index;
        }

        public int getIndex() {
            return index;
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

        public String getType() {
            return type;
        }

        public String getPattern() {
            return pattern;
        }

        public int getNextYes() {
            return nextYes;
        }

        public int getNextNo() {
            return nextNo;
        }
    }
}
