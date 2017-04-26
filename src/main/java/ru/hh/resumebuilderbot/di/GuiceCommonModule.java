package ru.hh.resumebuilderbot.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import ru.hh.resumebuilderbot.Config;
import ru.hh.resumebuilderbot.database.HibernateMetadataFactory;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;

import javax.inject.Named;
import java.io.IOException;

public class GuiceCommonModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(String.class).annotatedWith(Names.named("xmlFilename")).toInstance(Config.GRAPH_XML_FILEPATH);
    }

    @Provides
    @Singleton
    public SessionFactory provideSessionFactory() {
        Metadata metadata = HibernateMetadataFactory.prod();
        return metadata.getSessionFactoryBuilder().build();
    }

    @Provides
    @Singleton
    public Graph provideGraph(@Named("xmlFilename") String xmlFilename) {
        try {
            return Graph.fromXMLFile(xmlFilename);
        } catch (IOException e) {
            throw new RuntimeException("Error building graph", e);
        }
    }
}
