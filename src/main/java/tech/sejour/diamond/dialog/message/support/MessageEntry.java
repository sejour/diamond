package tech.sejour.diamond.dialog.message.support;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import tech.sejour.diamond.error.DiamondRuntimeException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ダイアログメッセージを表す
 */
public interface MessageEntry {

    int getOrder();

    List<Message> getContent(Object dialog, Event event) throws IllegalAccessException, InvocationTargetException;

    static List<Message> message(Object messageObject) {
        if (messageObject == null) {
            return new ArrayList<>();
        }
        else if (messageObject instanceof  Message) {
            return Arrays.asList((Message) messageObject);
        }
        else if (messageObject instanceof String) {
            return Arrays.asList(new TextMessage((String) messageObject));
        }
        else if (messageObject instanceof List<?>) {
            return ((List<?>) messageObject).stream()
                    .map(message -> (message instanceof Message) ? (Message) message : ((message instanceof String) ? new TextMessage((String) message) : null))
                    .filter(message -> message != null)
                    .collect(Collectors.toList());
        }

        throw new DiamondRuntimeException("Type of the message object can not use.");
    }

}
