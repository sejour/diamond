package tech.sejour.diamond.dialog.message.support;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.message.Message;

import java.lang.reflect.Field;
import java.util.List;

/**
 * フィールドで定義されたダイアログメッセージ
 */
public class MessageField implements MessageEntry {

    private Field field;
    private int order;

    public MessageField(Field field, int order) {
        this.field = field;
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public List<Message> getContent(Object dialog, Event event) throws IllegalAccessException {
        return MessageEntry.message(field.get(dialog));
    }

}
