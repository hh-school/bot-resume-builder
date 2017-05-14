package ru.hh.resumebuilderbot.telegram.handler.suggest;

import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;

import java.util.ArrayList;
import java.util.List;

public class NotificationResults {
    public static List<Notification> getShortQueryErrorResult() {
        Notification notification = new Notification();
        notification.setDescription(TextsStorage.getText(TextId.NEED_MORE_ONE_SYMBOL));
        notification.setText(TextsStorage.getText(TextId.NOTHING_SELECTED));
        notification.setTitle(TextsStorage.getText(TextId.CONTINUE_INPUT));
        List<Notification> notifications = new ArrayList<>(1);
        notifications.add(notification);
        return notifications;
    }

    public static List<Notification> getNotFoundErrorResult(String query) {
        Notification notification = new Notification();
        notification.setDescription(TextsStorage.getText(TextId.CHECK_INPUT_DATA));
        notification.setText(TextsStorage.getText(TextId.NOTHING_SELECTED));
        notification.setTitle(String.format("По запросу '%s' ничего не найдено", query));
        List<Notification> notifications = new ArrayList<>(1);
        notifications.add(notification);
        return notifications;
    }

    public static List<Notification> getNonFacultiesInstituteResult() {
        Notification notification = new Notification();
        notification.setDescription(TextsStorage.getText(TextId.NO_FACULTIES_FOUND_DESCRIPTION));
        notification.setText(TextsStorage.getText(TextId.NO_FACULTIES_FOUND));
        notification.setTitle(TextsStorage.getText(TextId.NO_FACULTIES_FOUND_DESCRIPTION));
        List<Notification> notifications = new ArrayList<>(1);
        notifications.add(notification);
        return notifications;
    }
}
