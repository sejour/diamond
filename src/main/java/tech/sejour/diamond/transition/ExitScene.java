package tech.sejour.diamond.transition;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import tech.sejour.diamond.scene.SceneObject;
import tech.sejour.diamond.scene.SceneLoader;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 * シーンを終了する。親シーンが存在すれば親に戻る
 */
public class ExitScene implements TransitionRequest {

    final List<Message> messages;

    public ExitScene() {
        this.messages = null;
    }

    public ExitScene(String message) {
        this(Arrays.asList(new TextMessage(message)));
    }

    public ExitScene(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public TransitionResult execute(SceneLoader sceneLoader, SceneObject currentScene) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        return new TransitionResult(currentScene.exit(), messages);
    }
}
