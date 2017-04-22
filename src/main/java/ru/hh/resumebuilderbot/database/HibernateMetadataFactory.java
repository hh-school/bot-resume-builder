package ru.hh.resumebuilderbot.database;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.hh.resumebuilderbot.database.model.Area;
import ru.hh.resumebuilderbot.database.model.Node;
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
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .applySetting(
                        "hibernate.connection.url",
                        String.format(
                                "jdbc:postgresql://%s:%s/%s",
                                System.getenv("DB_HOST"),
                                System.getenv("DB_PORT"),
                                System.getenv("DB_NAME")
                        )
                )
                .applySetting(
                        "hibernate.connection.username", System.getenv("DB_USER")

                )
                .applySetting(
                        "hibernate.connection.password", System.getenv("DB_PASSWORD")

                )
                .build();
        return new MetadataSources(standardRegistry)
                .addAnnotatedClass(Contact.class)
                .addAnnotatedClass(ContactType.class)
                .addAnnotatedClass(Education.class)
                .addAnnotatedClass(Node.class)
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
