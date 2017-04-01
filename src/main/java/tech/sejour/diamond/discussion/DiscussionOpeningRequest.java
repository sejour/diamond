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

    private DiscussionOpeningRequest(List<Message> messages, Class<? extends Scene> scene, Object... args) {
        super(messages, scene, null, args);
    }

    public static DiscussionOpeningRequest request(Class<? extends Scene> scene, Object... args) {
        return new DiscussionOpeningRequest(null, scene, args);
    }

    public static DiscussionOpeningRequest requestWithMessage(String message, Class<? extends Scene> scene, Object... args) {
        return new DiscussionOpeningRequest(Arrays.asList(new TextMessage(message)), scene, args);
    }

    public static DiscussionOpeningRequest requestWithMessages(List<Message> messages, Class<? extends Scene> scene, Object... args) {
        return new DiscussionOpeningRequest(messages, scene, args);
    }

}
