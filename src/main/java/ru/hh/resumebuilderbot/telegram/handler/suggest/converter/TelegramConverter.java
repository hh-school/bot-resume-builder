package ru.hh.resumebuilderbot.telegram.handler.suggest.converter;

import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import ru.hh.resumebuilderbot.http.response.entity.Area;
import ru.hh.resumebuilderbot.http.response.entity.Company;
import ru.hh.resumebuilderbot.http.response.entity.Faculty;
import ru.hh.resumebuilderbot.http.response.entity.Institute;
import ru.hh.resumebuilderbot.http.response.entity.Position;
import ru.hh.resumebuilderbot.http.response.entity.Skill;
import ru.hh.resumebuilderbot.http.response.entity.StudyField;
import ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity.AreaTelegramConverter;
import ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity.CompanyTelegramConverter;
import ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity.EntityTelegramConverter;
import ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity.FacultyTelegramConverter;
import ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity.InstituteTelegramConverter;
import ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity.PositionTelegramConverter;
import ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity.SkillTelegramConverter;
import ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity.SpecializationTelegramConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelegramConverter {
    private final Map<Class<?>, EntityTelegramConverter> map = new HashMap<>();

    public static TelegramConverter buildWithEntityConverters() {
        return new TelegramConverter()
                .register(Area.class, new AreaTelegramConverter())
                .register(Company.class, new CompanyTelegramConverter())
                .register(Faculty.class, new FacultyTelegramConverter())
                .register(Institute.class, new InstituteTelegramConverter())
                .register(Position.class, new PositionTelegramConverter())
                .register(Skill.class, new SkillTelegramConverter())
                .register(StudyField.class, new SpecializationTelegramConverter());
    }

    public <T> TelegramConverter register(Class<T> clazz, EntityTelegramConverter<T> entityTelegramConverter) {
        map.put(clazz, entityTelegramConverter);
        return this;
    }

    private <T> InlineQueryResultArticle convert(T suggestEntity, EntityTelegramConverter<T> telegramConverter) {
        return telegramConverter.convert(suggestEntity);
    }

    public <T> List<InlineQueryResult> convertList(List<T> suggestEntities) {
        List<InlineQueryResult> results = new ArrayList<>();
        if (suggestEntities.size() == 0) {
            return results;
        }

        EntityTelegramConverter entityTelegramConverter = map.get(suggestEntities.get(0).getClass());
        for (Integer i = 0; i < suggestEntities.size(); i++) {
            results.add(convert(suggestEntities.get(i), entityTelegramConverter).setId(i.toString()));
        }
        return results;
    }
}
