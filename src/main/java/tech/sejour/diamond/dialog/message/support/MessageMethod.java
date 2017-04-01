package tech.sejour.diamond.dialog.message.support;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.message.Message;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * メッソッドで動的に生成するメッセージ
 */
public class MessageMethod implements MessageEntry {

    private Method method;
    private int order;

    public MessageMethod(Method method, int order) {
        this.method = method;
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public List<Message> getContent(Object dialog, Event event) throws InvocationTargetException, IllegalAccessException {
        // TODO: Event Type Mapping

        if (method.getParameterCount() == 1) {
            return MessageEntry.message(method.invoke(dialog, event));
        }

        return MessageEntry.message(method.invoke(dialog));
    }

}
