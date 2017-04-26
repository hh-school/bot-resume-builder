package ru.hh.resumebuilderbot.question.storage;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.question.storage.graph.node.QuestionNode;

@Singleton
public class QuestionsStorage {
    private Graph graphSample;

    @Inject
    public QuestionsStorage(Graph graphSample) {
        this.graphSample = graphSample;
    }

    public QuestionNode getClonedRoot() {
        return graphSample.cloneContent().getRoot();
    }
}
