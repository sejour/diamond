package tech.sejour.diamond.transition;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import tech.sejour.diamond.scene.SceneObject;
import tech.sejour.diamond.scene.SceneLoader;

import java.util.Arrays;
import java.util.List;

/**
 * 現在実行中のダイアログを再度メッセージの送信から実行し直す
 */
public class ContinueDialog implements TransitionRequest {

    final List<Message> messages;

    private ContinueDialog(List<Message> messages) {
        this.messages = messages;
    }

    public static ContinueDialog request() {
        return new ContinueDialog(null);
    }

    public static ContinueDialog requestWithMessages(List<Message> messages) {
        return new ContinueDialog(messages);
    }

    public static ContinueDialog requestWithMessage(String message) {
        return new ContinueDialog(Arrays.asList(new TextMessage(message)));
    }

    @Override
    public TransitionResult execute(SceneLoader sceneLoader, SceneObject currentScene) throws NoSuchFieldException, IllegalAccessException {
        return new TransitionResult(currentScene, messages);
    }

}
