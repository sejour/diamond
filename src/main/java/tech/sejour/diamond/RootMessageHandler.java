package tech.sejour.diamond;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import tech.sejour.diamond.event.gateway.EventGateway;

@LineMessageHandler
public class RootMessageHandler {

    @Autowired
    private EventGateway eventGateway;

    @EventMapping
    public void handle(Event event) throws Throwable {
        eventGateway.handle(event.getSource().getSenderId(), event);
    }

}
