package tech.sejour.diamond.transition;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import tech.sejour.diamond.scene.SceneObject;
import tech.sejour.diamond.scene.SceneLoader;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 * 現在のシーンの次のダイアログに遷移する(デフォルトのトランジション)
 */
public class NextDialog implements TransitionRequest {

    final List<Message> messages;
    final Object[] args;

    private NextDialog(List<Message> messages, Object... args) {
        this.messages = messages;
        this.args = args;
    }

    public static NextDialog request(Object... args) {
        return new NextDialog(null, args);
    }

    public static NextDialog requestWithMessage(String message, Object... args) {
        // TODO: messageの内容がnullまたはemptyであればrequest()を呼び出すように修正する
        return new NextDialog(Arrays.asList(new TextMessage(message)), args);
    }

    public static NextDialog requestWithMessages(List<Message> messages, Object... args) {
        return new NextDialog(messages, args);
    }

    @Override
    public TransitionResult execute(SceneLoader sceneLoader, SceneObject currentScene) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        return new TransitionResult(currentScene.nextDialog(args), messages);
    }
}
