package tech.sejour.diamond.dialog.message.support;

import com.linecorp.bot.model.event.Event;
import tech.sejour.diamond.dialog.message.annotation.Message;
import tech.sejour.diamond.error.DiamondRuntimeException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ダイアログメッセージを生成するクラス
 */
public class MessageGenerator {

    private List<MessageEntry> messageEntries = new ArrayList<>();

    public MessageGenerator(Field[] fields, Method[] methods, Class dialogClass) {
        // @Message on field
        messageEntries.addAll(Arrays.stream(fields)
                .map(field -> {
                    Message messageAno = field.getAnnotation(Message.class);
                    if (messageAno == null) return null;
                    return new MessageField(field, messageAno.order());
                })
                .filter(message -> message != null)
                .collect(Collectors.toList())
        );

        // @Message on method
        messageEntries.addAll(Arrays.stream(methods)
                .map(method -> {
                    Message messageAno = method.getAnnotation(Message.class);
                    if (messageAno == null) return null;
                    return new MessageMethod(method, messageAno.order());
                })
                .filter(message -> message != null)
                .collect(Collectors.toList())
        );

        // メッセージが1つも無ければ例外スロー
        if (messageEntries.isEmpty()) {
            throw new DiamondRuntimeException(String.format("Dialog class must contain at least one field or method given @Message (dialogClass=%s)", dialogClass.getName()));
        }

        // sort message order
        messageEntries.sort((entry1, entry2) -> Integer.compare(entry1.getOrder(), entry2.getOrder()));
    }

    public List<com.linecorp.bot.model.message.Message> generateMessages(Object dialogInstance, Event event) {
        return messageEntries.stream()
                .flatMap(messageEntry -> {
                    try {
                        return messageEntry.getContent(dialogInstance, event).stream();
                    } catch (Throwable e) {
                        throw new DiamondRuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

}
