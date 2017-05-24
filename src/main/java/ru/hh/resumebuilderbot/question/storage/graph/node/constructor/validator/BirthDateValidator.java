package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import ru.hh.resumebuilderbot.Answer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BirthDateValidator extends Validator {
    private static final DateFormat birthDateFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static final Integer MIN_YEARS_LIMIT = 14;

    private static Date minDate;

    static {
        try {
            minDate = birthDateFormat.parse("1900.01.01");
        } catch (ParseException e) {
            throw new RuntimeException("Invalid min date");
        }
    }

    public BirthDateValidator() {
        super(String.format("К сожалению работать можно только с %d лет.", MIN_YEARS_LIMIT));
    }

    @Override
    public boolean answerIsValid(Answer answer) {
        Date birthDate;
        try {
            birthDate = birthDateFormat.parse((String) answer.getAnswerBody());
        } catch (ParseException e) {
            return false;
        }
        return !(birthDate.before(minDate) || birthDate.after(getMaxDate()));
    }

    public Date getMaxDate() {
        Date date;
        try {
            date = birthDateFormat.parse(birthDateFormat.format(new Date()));
        } catch (ParseException e) {
            throw new RuntimeException("Invalid max date");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -MIN_YEARS_LIMIT);
        return calendar.getTime();
    }

    @Override
    public Validator clone() {
        return new BirthDateValidator();
    }
}
