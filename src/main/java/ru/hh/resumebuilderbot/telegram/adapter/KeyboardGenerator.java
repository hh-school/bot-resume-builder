package ru.hh.resumebuilderbot.telegram.adapter;

import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.ReplyKeyboardEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyboardGenerator {
    private static Map<Integer, String> months = new HashMap<>(12);

    static {
        months.put(1, "Январь");
        months.put(2, "Февраль");
        months.put(3, "Март");
        months.put(4, "Апрель");
        months.put(5, "Май");
        months.put(6, "Июнь");
        months.put(7, "Июль");
        months.put(8, "Август");
        months.put(9, "Сентябрь");
        months.put(10, "Октябрь");
        months.put(11, "Ноябрь");
        months.put(12, "Декабрь");
    }

    public static ReplyKeyboard getReplyKeyboard(Question question) {
        ReplyKeyboard keyboard;
        switch (question.getReplyKeyboardEnum()) {
            case PHONE_NUMBER:
                keyboard = generatePhoneNumberKeyboard();
                break;
            case VARIANTS_OF_ANSWER:
                keyboard = generateVariantsOfAnswerKeyboard(question.getVariantsOfAnswer());
                break;
            case WEAK_SUGGEST:
            case STRONG_SUGGEST:
                keyboard = generateSuggestKeyboard();
                break;
            case DATE_WITH_DAY:
                keyboard = generateYearsKeyboard(1980, "Day");
                break;
            case DATE_WITHOUT_DAY:
                keyboard = generateYearsKeyboard(1998, "Month");
                break;
            case YEAR:
                keyboard = generateYearsKeyboard(2001, "Year");
                break;
            case NEGOTIATION:
                keyboard = generateNegotiationKeyboard(question.getCallbackData());
                break;
            default:
                keyboard = new ReplyKeyboardRemove();
                break;
        }
        return keyboard;
    }

    public static InlineKeyboardMarkup getUpdatedKeyboard(ReplyKeyboardEnum keyboardEnum, String dateInfo) {
        InlineKeyboardMarkup inlineKeyboard;
        switch (keyboardEnum) {
            case UPDATE_YEAR_WITH_DAY:
                inlineKeyboard = generateYearsKeyboard(Integer.valueOf(dateInfo), "Day");
                break;
            case UPDATE_YEAR_WITHOUT_DAY:
                inlineKeyboard = generateYearsKeyboard(Integer.valueOf(dateInfo), "Month");
                break;
            case UPDATE_YEAR_WITHOUT_MONTH:
                inlineKeyboard = generateYearsKeyboard(Integer.valueOf(dateInfo), "Year");
                break;
            case UPDATE_MONTH_WITH_DAY:
                inlineKeyboard = generateMonthsKeyboard(dateInfo, true);
                break;
            case UPDATE_MONTH_WITHOUT_DAY:
                inlineKeyboard = generateMonthsKeyboard(dateInfo, false);
                break;
            case UPDATE_DAY:
                inlineKeyboard = generateDaysKeyboard(dateInfo);
                break;
            default:
                inlineKeyboard = new InlineKeyboardMarkup();
                break;
        }
        return inlineKeyboard;
    }

    private static InlineKeyboardMarkup generateSuggestKeyboard() {
        InlineKeyboardMarkup result = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton()
                .setSwitchInlineQueryCurrentChat("")
                .setText("Для появления подсказки нажмите на эту кнопку"));
        rowsInline.add(rowInline);
        result.setKeyboard(rowsInline);
        return result;
    }

    private static InlineKeyboardMarkup generateNegotiationKeyboard(String callbackData) {
        InlineKeyboardMarkup result = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton()
                .setText("Откликнуться на вакансию")
                .setCallbackData(callbackData));
        rowsInline.add(rowInline);
        result.setKeyboard(rowsInline);
        return result;
    }

    private static ReplyKeyboardMarkup generateVariantsOfAnswerKeyboard(List<String> variantsOfAnswer) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        for (String variant : variantsOfAnswer) {
            KeyboardRow keyboardRow = new KeyboardRow();
            StringBuffer sb = new StringBuffer();
            for (String word : variant.split(" ")) {
                sb.append(isNumber(word) ? Character.toChars(Integer.parseInt(word)) : word.toCharArray());
                sb.append(" ".toCharArray());
            }
            KeyboardButton button = new KeyboardButton(sb.toString());
            keyboardRow.add(button);
            keyboardRows.add(keyboardRow);
        }
        keyboardMarkup.setKeyboard(keyboardRows);
        keyboardMarkup.setOneTimeKeyboad(true);
        return keyboardMarkup;
    }

    private static ReplyKeyboardMarkup generatePhoneNumberKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        KeyboardButton currentPhone = new KeyboardButton("Ввести текущий номер телефона");
        currentPhone.setRequestContact(true);
        firstRow.add(currentPhone);
        keyboardRows.add(firstRow);

        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add("Ввести другой номер телефона");
        keyboardRows.add(secondRow);

        keyboardMarkup.setKeyboard(keyboardRows);
        keyboardMarkup.setOneTimeKeyboad(true);
        return keyboardMarkup;
    }

    private static InlineKeyboardMarkup generateYearsKeyboard(Integer beginYear, String type) {
        int rowsAmount = 5;
        int colsAmount = 4;
        InlineKeyboardMarkup resultKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> yearButtons = new ArrayList<>(rowsAmount + 1);
        String keyboardEnum = "";
        switch (type) {
            case "Year":
                keyboardEnum = "status:READY";
                break;
            case "Month":
                keyboardEnum = "keyEnum:UPDATE_MONTH_WITHOUT_DAY";
                break;
            case "Day":
                keyboardEnum = "keyEnum:UPDATE_MONTH_WITH_DAY";
                break;
        }
        for (int i = 0; i < rowsAmount; i++) {
            int rowBeginYear = beginYear + i * colsAmount;
            List<InlineKeyboardButton> row = new ArrayList<>(colsAmount);
            for (int year = rowBeginYear; year < rowBeginYear + colsAmount; year++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(String.valueOf(year));
                button.setCallbackData(String.format("keyData:%d,%s,text:1",
                        year, keyboardEnum));
                row.add(button);
            }
            yearButtons.add(row);
        }

        yearButtons.add(getYearNavigateButtons(beginYear, rowsAmount * colsAmount, type));
        return resultKeyboard.setKeyboard(yearButtons);
    }

    private static List<InlineKeyboardButton> getYearNavigateButtons(int beginYear, int diff, String type) {
        List<InlineKeyboardButton> navigateRow = new ArrayList<>(2);
        String keyboardEnum = "";
        switch (type) {
            case "Year":
                keyboardEnum = "keyEnum:UPDATE_YEAR_WITHOUT_MONTH";
                break;
            case "Month":
                keyboardEnum = "keyEnum:UPDATE_YEAR_WITHOUT_DAY";
                break;
            case "Day":
                keyboardEnum = "keyEnum:UPDATE_YEAR_WITH_DAY";
                break;
        }
        InlineKeyboardButton backButton = new InlineKeyboardButton()
                .setText("<<  Назад")
                .setCallbackData(String.format("keyData:%d,%s", beginYear - diff, keyboardEnum));
        navigateRow.add(backButton);
        InlineKeyboardButton nextButton = new InlineKeyboardButton()
                .setText("Далее  >>")
                .setCallbackData(String.format("keyData:%d,%s", beginYear + diff, keyboardEnum));
        navigateRow.add(nextButton);
        return navigateRow;
    }

    private static InlineKeyboardMarkup generateMonthsKeyboard(String year, boolean withDay) {
        int rowsAmount = 4;
        int colsAmount = 3;
        String keyboardEnum = withDay ? "keyEnum:UPDATE_DAY" : "status:READY";
        InlineKeyboardMarkup resultKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> monthButtons = new ArrayList<>(rowsAmount + 1);

        for (int i = 0; i < rowsAmount; i++) {
            List<InlineKeyboardButton> row = new ArrayList<>(colsAmount);
            int rowBeginMonth = 1 + i * colsAmount;

            for (int monthIndex = rowBeginMonth; monthIndex < rowBeginMonth + colsAmount; monthIndex++) {
                String monthName = months.get(monthIndex);
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(monthName);

                String text = "text:1";
                String keyboardData = String.format("keyData:%s.%02d", year, monthIndex);
                String callbackData = String.format("%s,%s,%s", keyboardData, keyboardEnum, text);

                button.setCallbackData(callbackData);
                row.add(button);
            }
            monthButtons.add(row);
        }

        monthButtons.add(getMonthNavigateButtons(withDay, Integer.valueOf(year)));

        return resultKeyboard.setKeyboard(monthButtons);
    }

    private static List<InlineKeyboardButton> getMonthNavigateButtons(boolean withDay, Integer year) {
        List<InlineKeyboardButton> backToYearRow = new ArrayList<>();

        String keyboardEnum = withDay ? "keyEnum:UPDATE_YEAR_WITH_DAY"
                : "keyEnum:UPDATE_YEAR_WITHOUT_DAY";
        String keyboardData = String.format("keyData:%d", year - 10);
        InlineKeyboardButton backButton = new InlineKeyboardButton()
                .setText("<< Вернуться к выбору года")
                .setCallbackData(String.format("%s,%s,text:2", keyboardData, keyboardEnum));
        backToYearRow.add(backButton);
        return backToYearRow;
    }

    private static InlineKeyboardMarkup generateDaysKeyboard(String previousData) {
        int rowsAmount = 5;
        int colsAmount = 7;
        int year = Integer.valueOf(previousData.split("\\.")[0]);
        int month = Integer.valueOf(previousData.split("\\.")[1]);
        int maxDays = getDaysAmount(month, year);

        InlineKeyboardMarkup resultKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> daysButtons = new ArrayList<>(rowsAmount + 1);
        for (int i = 0; i < rowsAmount; i++) {
            List<InlineKeyboardButton> row = new ArrayList<>(colsAmount);
            int rowBeginDay = 1 + i * colsAmount;
            if (rowBeginDay > maxDays) {
                break;
            }
            for (int day = rowBeginDay; day < rowBeginDay + colsAmount; day++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                if (day <= maxDays) {
                    button.setText(String.format("%02d", day));
                    button.setCallbackData(String.format("keyData:%s.%02d,status:READY", previousData, day));
                } else {
                    button.setText("--");
                    button.setCallbackData("--");
                }
                row.add(button);
            }
            daysButtons.add(row);
        }

        List<InlineKeyboardButton> backToMonthRow = new ArrayList<>();

        String keyboardEnum = "keyEnum:UPDATE_MONTH_WITH_DAY";
        String keyboardData = String.format("keyData:%d", year);
        InlineKeyboardButton backButton = new InlineKeyboardButton()
                .setText("<< Вернуться к выбору месяца")
                .setCallbackData(String.format("%s,%s,text:1", keyboardData, keyboardEnum));
        backToMonthRow.add(backButton);
        daysButtons.add(backToMonthRow);

        return resultKeyboard.setKeyboard(daysButtons);
    }

    private static int getDaysAmount(int month, int year) {
        if (month == 2 && (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)) {
            return 29;
        }
        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return days[month - 1];
    }

    private static boolean isNumber(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
