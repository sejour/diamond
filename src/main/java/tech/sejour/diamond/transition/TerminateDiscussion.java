package tech.sejour.diamond.transition;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import tech.sejour.diamond.scene.SceneObject;
import tech.sejour.diamond.scene.SceneLoader;

import java.util.Arrays;
import java.util.List;

/**
 * ディスカッションを終了する
 */
public class TerminateDiscussion implements TransitionRequest {

    final List<Message> messages;

    private TerminateDiscussion(List<Message> messages) {
        this.messages = messages;
    }

    public static TerminateDiscussion request() {
        return new TerminateDiscussion(null);
    }

    public static TerminateDiscussion requestWithMessage(String message) {
        return new TerminateDiscussion(Arrays.asList(new TextMessage(message)));
    }

    public static TerminateDiscussion requestWithMessages(List<Message> messages) {
        return new TerminateDiscussion(messages);
    }

    @Override
    public TransitionResult execute(SceneLoader sceneLoader, SceneObject currentScene) throws NoSuchFieldException, IllegalAccessException {
        return new TransitionResult(null, messages);
    }

}
