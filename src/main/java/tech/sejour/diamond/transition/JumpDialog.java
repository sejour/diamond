package tech.sejour.diamond.transition;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import tech.sejour.diamond.scene.SceneObject;
import tech.sejour.diamond.scene.SceneLoader;

import java.util.Arrays;
import java.util.List;

/**
 * 同一シーン内の別ダイアログへ順序を無視して指定IDのダイアログへジャンプする
 */
public class JumpDialog implements TransitionRequest {

    final int targetDialogId;
    final List<Message> messages;
    final Object[] args;

    private JumpDialog(List<Message> messages, int targetDialogId, Object... args) {
        this.targetDialogId = targetDialogId;
        this.messages = messages;
        this.args = args;
    }

    public static JumpDialog request(int targetDialogId, Object... args) {
        return new JumpDialog(null, targetDialogId,  args);
    }

    public static JumpDialog requestWithMessage(String message, int targetDialogId, Object... args) {
        return new JumpDialog(Arrays.asList(new TextMessage(message)), targetDialogId, args);
    }

    public static JumpDialog requestWithMessages(List<Message> messages, int targetDialogId, Object... args) {
        return new JumpDialog(messages, targetDialogId, args);
    }

    @Override
    public TransitionResult execute(SceneLoader sceneLoader, SceneObject currentScene) throws NoSuchFieldException, IllegalAccessException {
        return new TransitionResult(currentScene.jumpDialog(targetDialogId, args), messages);
    }

}
