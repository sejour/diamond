package tech.sejour.diamond.transition;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import tech.sejour.diamond.scene.SceneObject;
import tech.sejour.diamond.scene.SceneLoader;

import java.util.Arrays;
import java.util.List;

/**
 * 現在実行中のダイアログをキープする。
 * ContinueDialogはダイアログメッセージを再度送信するが、KeepDialogは送信しない。
 */
public class KeepDialog implements TransitionRequest {

    final List<Message> messages;

    private KeepDialog(List<Message> messages) {
        this.messages = messages;
    }

    public static KeepDialog request() {
        return new KeepDialog(null);
    }

    public static KeepDialog requestWithMessages(List<Message> messages) {
        return new KeepDialog(messages);
    }

    public static KeepDialog requestWithMessage(String message) {
        return new KeepDialog(Arrays.asList(new TextMessage(message)));
    }

    @Override
    public TransitionResult execute(SceneLoader sceneLoader, SceneObject currentScene) throws NoSuchFieldException, IllegalAccessException {
        return new TransitionResult(currentScene, messages);
    }
    
}
