package ru.hh.resumebuilderbot.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import ru.hh.resumebuilderbot.database.HibernateMetadataFactory;

public class GuiceCommonModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    public SessionFactory provideSessionFactory() {
        Metadata metadata = HibernateMetadataFactory.prod();
        return metadata.getSessionFactoryBuilder().build();
    }
}
