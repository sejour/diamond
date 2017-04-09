package tech.sejour.diamond.event.gateway;

import com.linecorp.bot.model.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import tech.sejour.diamond.discussion.DiscussionEngine;
import tech.sejour.diamond.discussion.DiscussionRequest;
import tech.sejour.diamond.event.support.DiamondMessageHandlerSupport;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Component
public class EventGateway {

    final ApplicationContext applicationContext;
    final DiamondMessageHandlerSupport diamondMessageHandlerSupport;
    final Map<String, DiscussionEngine> roomMap = new HashMap<>();

    @Autowired
    public EventGateway(ApplicationContext applicationContext, DiamondMessageHandlerSupport diamondMessageHandlerSupport) {
        this.applicationContext = applicationContext;
        this.diamondMessageHandlerSupport = diamondMessageHandlerSupport;
    }

    private DiscussionEngine getDiscussion(String sender) {
        return roomMap.get(sender);
    }

    private DiscussionEngine openDiscussion(String sender, Event event, DiscussionRequest request) throws InvocationTargetException, NoSuchMethodException, IOException, IllegalAccessException, NoSuchFieldException {
        DiscussionEngine discussionEngine = applicationContext.getBean(DiscussionEngine.class, sender);
        roomMap.put(sender, discussionEngine);
        discussionEngine.open(event, request);
        return discussionEngine;
    }

    private void closeDiscussion(String sender) {
        roomMap.remove(sender);
    }

    public void handle(String sender, Event event) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, IOException, InvocationTargetException {
        // AlwaysCatchなイベントをハンドル
        if (diamondMessageHandlerSupport.handleAlwaysCatch(event)) {
            return;
        }

        // アクティブなディスカッションを取り出す
        DiscussionEngine discussionEngine = getDiscussion(sender);

        if (discussionEngine == null) {
            // 永続化ディスカッションを取り出す
            DiscussionRequest request = getSavedDiscussion(sender);
            if (request == null) {
                // イベントゲートウェイでハンドル
                request = diamondMessageHandlerSupport.handle(event);
                if (request == null) return;
            }

            // ディスカッションをオープンする
            openDiscussion(sender, event, request);
        }
        else if (!discussionEngine.advance(event)) {
            closeDiscussion(sender);
        }
    }

    // TODO: 永続化ディスカッションの取り出し
    private DiscussionRequest getSavedDiscussion(String sender) {
        return null;
    }

}
