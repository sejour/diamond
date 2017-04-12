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

    public DiscussionRequest(List<Message> messages, Class<? extends Scene> scene, Integer dialogId, Object... args) {
        super(messages);
        this.scene = scene;
        this.args = args;
        this.dialogId = dialogId;
    }

    public DiscussionRequest(Class<? extends Scene> scene, Integer dialogId,  Object... args) {
        this(null, scene, dialogId, args);
    }

}
