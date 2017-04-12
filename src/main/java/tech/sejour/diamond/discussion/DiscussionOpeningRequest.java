package tech.sejour.diamond.discussion;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import tech.sejour.diamond.scene.Scene;

import java.util.Arrays;
import java.util.List;

/**
 * Discussionを開くためのリクエスト (最初のダイアログから実行する)
 */
public class DiscussionOpeningRequest extends DiscussionRequest {

    public DiscussionOpeningRequest(List<Message> messages, Class<? extends Scene> scene, Object... args) {
        super(messages, scene, null, args);
    }

    public DiscussionOpeningRequest(Class<? extends Scene> scene, Object... args) {
        this(null, scene, args);
    }

}
