package tech.sejour.diamond.transition;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.MessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import tech.sejour.diamond.event.extension.EventOneSkipRequest;
import tech.sejour.diamond.scene.SceneLoader;
import tech.sejour.diamond.scene.SceneObject;

import java.lang.reflect.InvocationTargetException;

/**
 *
 */
public class PostbackTransition implements TransitionRequest, EventOneSkipRequest {

    private final String postbackActionText;
    private final TransitionRequest originalRequest;

    private PostbackTransition(String postbackActionText, TransitionRequest originalRequest) {
        this.postbackActionText = postbackActionText;
        this.originalRequest = originalRequest;
    }

    public static PostbackTransition request(String postbackActionText, TransitionRequest originalRequest) {
        return new PostbackTransition(postbackActionText, originalRequest);
    }

    @Override
    public TransitionResult execute(SceneLoader sceneLoader, SceneObject currentScene) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        return originalRequest.execute(sceneLoader, currentScene);
    }

    @Override
    public boolean skip(Event event) {
        if (event instanceof MessageEvent) {
            MessageContent content = ((MessageEvent) event).getMessage();
            if (content instanceof TextMessageContent && ((TextMessageContent) content).getText().equals(postbackActionText)) {
                return true;
            }
        }

        return false;
    }

}
