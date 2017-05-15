package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.database.model.User;

public class PhoneNumberValidator extends Validator {
    public static final Logger log = LoggerFactory.getLogger(PhoneNumberValidator.class);
    public static final String DEFAULT_REGION = "RU";
    private static final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
    public PhoneNumberValidator() {
        super("Хм, это точно номер телефона? Как же расстроится работодатель, " +
                "когда не сможет с вами связаться! Давайте попробуем еще раз!");
    }

    @Override
    public Validator clone() {
        return new PhoneNumberValidator();
    }

    @Override
    public boolean answerIsValid(Answer answer) {
        String answerAsString = (String) answer.getAnswerBody();
        Phonenumber.PhoneNumber testPhoneNumber;
        try {
            testPhoneNumber = phoneUtil.parse(answerAsString, DEFAULT_REGION);
        } catch (NumberParseException e) {
            log.error("Phone parsing error", e);
            return false;
        }
        return phoneUtil.isValidNumber(testPhoneNumber) && answerAsString.length() <= User.PHONE_LENGTH;
    }
}
