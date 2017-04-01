package tech.sejour.diamond.event.support;

import com.linecorp.bot.model.message.Message;

import java.util.List;

public class EventMappingMethodResult {

    public final List<Message> messages;

    public EventMappingMethodResult(List<Message> messages) {
        this.messages = messages;
    }

}
