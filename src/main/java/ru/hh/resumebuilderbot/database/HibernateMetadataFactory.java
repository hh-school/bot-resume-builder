package ru.hh.resumebuilderbot.database;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.hh.resumebuilderbot.database.model.Area;
import ru.hh.resumebuilderbot.database.model.SalaryCurrency;
import ru.hh.resumebuilderbot.database.model.Specialization;
import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.database.model.contact.Contact;
import ru.hh.resumebuilderbot.database.model.contact.ContactType;
import ru.hh.resumebuilderbot.database.model.education.Education;
import ru.hh.resumebuilderbot.database.model.experience.Company;
import ru.hh.resumebuilderbot.database.model.experience.Experience;
import ru.hh.resumebuilderbot.database.model.experience.Industry;
import ru.hh.resumebuilderbot.database.model.gender.Gender;

public class HibernateMetadataFactory {
    public static Metadata prod() {
        String dbHost = System.getenv("DB_HOST");
        String dbPort = System.getenv("DB_PORT");
        String dbName = System.getenv("DB_NAME");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");
        if (dbHost == null) {
            throw new RuntimeException("\"DB_HOST\" env not set");
        }
        if (dbPort == null) {
            throw new RuntimeException("\"DB_PORT\" env not set");
        }
        if (dbName == null) {
            throw new RuntimeException("\"DB_NAME\" env not set");
        }
        if (dbUser == null) {
            throw new RuntimeException("\"DB_USER\" env not set");
        }
        if (dbPassword == null) {
            throw new RuntimeException("\"DB_PASSWORD\" env not set");
        }
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .applySetting(
                        "hibernate.connection.url",
                        String.format("jdbc:postgresql://%s:%s/%s", dbHost, dbPort, dbName)
                )
                .applySetting("hibernate.connection.username", dbUser)
                .applySetting("hibernate.connection.password", dbPassword)
                .build();
        return new MetadataSources(standardRegistry)
                .addAnnotatedClass(Contact.class)
                .addAnnotatedClass(ContactType.class)
                .addAnnotatedClass(Education.class)
                .addAnnotatedClass(Company.class)
                .addAnnotatedClass(Experience.class)
                .addAnnotatedClass(Industry.class)
                .addAnnotatedClass(Area.class)
                .addAnnotatedClass(Gender.class)
                .addAnnotatedClass(SalaryCurrency.class)
                .addAnnotatedClass(Specialization.class)
                .addAnnotatedClass(User.class)
                .getMetadataBuilder()
                .build();
    }
}
