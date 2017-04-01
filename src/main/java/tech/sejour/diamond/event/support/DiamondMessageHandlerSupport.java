package tech.sejour.diamond.event.support;

import com.linecorp.bot.model.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import tech.sejour.diamond.service.DiamondMessagingService;
import tech.sejour.diamond.discussion.DiscussionRequest;
import tech.sejour.diamond.error.DiamondRuntimeException;
import tech.sejour.diamond.event.annotation.DiamondMessageHandler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Created by Shuka on 2017/03/31.
 */
@Component
public class DiamondMessageHandlerSupport {

    private Object eventGateway;
    private EventHandler eventHandler;
    private final DiamondMessagingService diamondMessagingService;

    @Autowired
    public DiamondMessageHandlerSupport(ApplicationContext applicationContext, DiamondMessagingService diamondMessagingService) {
        this.diamondMessagingService = diamondMessagingService;

        Collection<Object> beans = applicationContext.getBeansWithAnnotation(DiamondMessageHandler.class).values();
        if (beans.size() != 1) {
            throw new DiamondRuntimeException("Unique class that is supplied @DiamondMessageHandler is not found.");
        }

        eventGateway = beans.stream().findFirst().get();
        eventHandler = new EventHandler(eventGateway.getClass().getMethods());
    }

    public DiscussionRequest handle(Event event) throws InvocationTargetException, IllegalAccessException, IOException {
        EventMappingMethodResult result = eventHandler.handle(eventGateway, event);

        if (result != null) {
            if (result instanceof DiscussionRequest) {
                return (DiscussionRequest) result;
            }

            this.diamondMessagingService.sendReplyMessages(result.messages, event);
        }

        return null;
    }

}
