package tech.sejour.diamond.transition;

import com.linecorp.bot.model.message.Message;
import tech.sejour.diamond.scene.SceneObject;

import java.util.List;

/**
 * トランジションの結果
 */
public class TransitionResult {

    public final SceneObject scene;

    public final List<Message> messages;

    public TransitionResult(SceneObject scene, List<Message> messages) {
        this.scene = scene;
        this.messages = messages;
    }

}
