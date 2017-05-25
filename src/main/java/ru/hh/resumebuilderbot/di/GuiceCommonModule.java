package ru.hh.resumebuilderbot.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import ru.hh.resumebuilderbot.Config;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.HandlerDispatcher;
import ru.hh.resumebuilderbot.SuggestService;
import ru.hh.resumebuilderbot.cv.builder.PlainTextCVBuilder;
import ru.hh.resumebuilderbot.database.HibernateMetadataFactory;
import ru.hh.resumebuilderbot.http.HHHTTPService;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.telegram.handler.suggest.converter.TelegramConverter;

import javax.inject.Named;
import java.io.IOException;

public class GuiceCommonModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(String.class).annotatedWith(Names.named("GRAPH_XML_FILEPATH")).toInstance(Config.GRAPH_XML_FILEPATH);
    }

    @Provides
    @Singleton
    public SessionFactory provideSessionFactory() {
        Metadata metadata = HibernateMetadataFactory.prod();
        return metadata.getSessionFactoryBuilder().build();
    }

    @Provides
    public TelegramConverter provideTelegramConverter() {
        return TelegramConverter.buildWithEntityConverters();
    }

    @Provides
    @Singleton
    public Graph provideXMLGraph(@Named("GRAPH_XML_FILEPATH") String xmlFilepath) {
        try {
            return Graph.fromXMLFile(xmlFilepath);
        } catch (IOException e) {
            throw new RuntimeException("Error building graph", e);
        }
    }

    @Provides
    @Singleton
    public HandlerDispatcher provideHandlerDispatcher(DBProcessor dbProcessor, Graph graph,
                                                      SuggestService suggestService, HHHTTPService hhhttpService,
                                                      PlainTextCVBuilder plainTextCVBuilder) {
        return HandlerDispatcher.buildWithHandlers(
                dbProcessor,
                graph,
                suggestService,
                hhhttpService,
                plainTextCVBuilder
        );
    }
}
