package tech.sejour.diamond.dialog.extension;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.message.Message;
import tech.sejour.diamond.dialog.message.support.MessageEntry;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ExtendedDialogMessageEntry implements MessageEntry {

    private final ExtendedDialogSupport extendedDialogSupport;

    public ExtendedDialogMessageEntry(ExtendedDialogSupport extendedDialogSupport) {
        this.extendedDialogSupport = extendedDialogSupport;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public List<Message> getContent(Object dialog, Event event) throws IllegalAccessException, InvocationTargetException {
        return extendedDialogSupport.generateMessages(dialog);
    }

}
