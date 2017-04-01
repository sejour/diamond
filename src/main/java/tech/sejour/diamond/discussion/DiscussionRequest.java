package tech.sejour.diamond.discussion;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import tech.sejour.diamond.event.support.EventMappingMethodResult;
import tech.sejour.diamond.scene.Scene;

import java.util.Arrays;
import java.util.List;

/**
 * Discussionを開くためのリクエスト
 */
public class DiscussionRequest extends EventMappingMethodResult {

    public final Class<? extends Scene> scene;

    public final Integer dialogId;

    public final Object[] args;

    protected DiscussionRequest(List<Message> messages, Class<? extends Scene> scene, Integer dialogId, Object... args) {
        super(messages);
        this.scene = scene;
        this.args = args;
        this.dialogId = dialogId;
    }

    public static DiscussionRequest request(Class<? extends Scene> scene, Integer dialogId, Object... args) {
        return new DiscussionRequest(null, scene, dialogId, args);
    }

    public static DiscussionRequest requestWithMessage(String message, Class<? extends Scene> scene, Integer dialogId, Object... args) {
        return new DiscussionRequest(Arrays.asList(new TextMessage(message)), scene, dialogId, args);
    }

    public static DiscussionRequest requestWithMessages(List<Message> messages, Class<? extends Scene> scene, Integer dialogId, Object... args) {
        return new DiscussionRequest(messages, scene, dialogId, args);
    }

}
