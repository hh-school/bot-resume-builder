package ru.hh.resumebuilderbot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TextsStorage {
    private static final TextsStorage instance = new TextsStorage();

    ;
    private static final String instructions = "��� ������� ����� ������� ������� /clear." +
            " ����� ����������� ���� ������, ������� /show";

    static {
        instance.textsMap.put(TextId.ALREADY_STARTED, "����� ��� �������. ��� �������� ����������� ����������� �������" +
                "������� /clear");
        instance.textsMap.put(TextId.UNKNOWN, "����� �� �������");
        instance.textsMap.put(TextId.OOPS_TRY_RESTART, "���! ���-�� ����� �� ���. ������� ������ � ������� /start");
        instance.textsMap.put(TextId.HELLO,
                "����� ���������� � Resume Builder Bot. " + instructions);
        instance.textsMap.put(TextId.CLEARED,
                "���� ������ ���� �������. ����� �������� �������");
        instance.textsMap.put(TextId.FINISHED,
                "����� ��������. " + instructions);
        instance.textsMap.put(TextId.EMPTY,
                "���� ������ ���� �����. ");
    }

    private Map<TextId, String> textsMap = new ConcurrentHashMap<>();

    //hardcode

    private TextsStorage() {
    }

    //end hardcode

    public static String getText(TextId textId) {
        return instance.textsMap.get(textId);
    }

    public enum TextId {
        ALREADY_STARTED, UNKNOWN, OOPS_TRY_RESTART, HELLO, CLEARED, FINISHED, EMPTY;
    }
}