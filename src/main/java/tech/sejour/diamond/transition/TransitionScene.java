package tech.sejour.diamond.transition;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import tech.sejour.diamond.scene.Scene;
import tech.sejour.diamond.scene.SceneObject;
import tech.sejour.diamond.scene.SceneLoader;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 * シーンの遷移を行う
 */
public class TransitionScene implements TransitionRequest {

    final Class<? extends Scene> distination;

    final Object[] args;

    final List<Message> messages;

    private TransitionScene(List<Message> messages, Class<? extends Scene> distination, Object... args) {
        this.distination = distination;
        this.args = args;
        this.messages = messages;
    }

    public static TransitionScene request(Class<? extends Scene> distination, Object... args) {
        return new TransitionScene(null, distination, args);
    }

    public static TransitionScene requestWithMessage(String message, Class<? extends Scene> distination, Object... args) {
        return new TransitionScene(Arrays.asList(new TextMessage(message)), distination, args);
    }

    public static TransitionScene requestWithMessages(List<Message> messages, Class<? extends Scene> distination, Object... args) {
        return new TransitionScene(messages, distination, args);
    }

    @Override
    public TransitionResult execute(SceneLoader sceneLoader, SceneObject currentScene) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        return new TransitionResult(sceneLoader.loadScene(currentScene, distination, false, args), messages);
    }

}
