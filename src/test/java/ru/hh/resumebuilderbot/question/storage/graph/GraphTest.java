package ru.hh.resumebuilderbot.question.storage.graph;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GraphTest {

    private final static String allFeaturesFilename = "src/test/resources/XMLLoaderTest/AllFeatures.xml";

    private static Graph expectedGraph;

    @BeforeClass
    private void createExpectedGraph() throws Exception {
        try {
            expectedGraph = TestGraphBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException("Exception during graph building");
        }
    }

    @Test(priority = 0, enabled = true)
    void fromXMLFileTest() throws Exception {
        Graph graph = Graph.fromXMLFile(allFeaturesFilename);
        assertEquals(graph, expectedGraph);
    }

    @Test(priority = 1, enabled = true)
    void cloneContentTest() throws Exception {
        Graph graph = Graph.fromXMLFile(allFeaturesFilename);
        Graph clonedGraph = graph.cloneContent();
        assertEquals(graph, clonedGraph);
    }

}
