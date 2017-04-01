package tech.sejour.diamond.discussion;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.sejour.diamond.scene.SceneObject;
import tech.sejour.diamond.service.DiamondMessagingService;
import tech.sejour.diamond.scene.SceneLoader;
import tech.sejour.diamond.error.DiamondRuntimeException;
import tech.sejour.diamond.event.support.DiamondMessageHandlerSupport;
import tech.sejour.diamond.transition.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * ディスカッションを展開するクラス
 */
@Component
@Scope("prototype")
public class DiscussionEngine {

    final String sender;

    private SceneObject scene;

    @Autowired
    private SceneLoader sceneLoader;

    @Autowired
    private DiamondMessagingService diamondMessagingService;

    public DiscussionEngine(String sender) {
        this.sender = sender;
    }

    /**
     * ディスカッションを開く
     * @param event ユーザからのイベント
     * @param discussionRequest ディスカッションリクエスト
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws IOException
     * @throws InvocationTargetException
     */
    public void open(Event event, DiscussionRequest discussionRequest) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, IOException, InvocationTargetException {
        scene = sceneLoader.loadFirstScene(sender, discussionRequest.scene, discussionRequest.dialogId, discussionRequest.args);

        // generate messages
        List<Message> messages = new ArrayList<>();
        if (discussionRequest.messages != null) {
            messages.addAll(discussionRequest.messages);
        }
        messages.addAll(scene.getActiveDialog().generateMessages(event));

        diamondMessagingService.sendReplyMessages(messages, event);
    }

    /**
     * ユーザからのイベントをトリガにディスカッションを進める
     * @param event ユーザからのイベント
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws IOException
     * @throws InvocationTargetException
     */
    public boolean advance(Event event) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, IOException, InvocationTargetException {
        if (scene == null) {
            throw new DiamondRuntimeException("DiscussionEngine is not opened.");
        }

        // handle reply
        TransitionRequest transitionRequest = scene.getActiveDialog().handleReply(event);
        if (transitionRequest == null) {
            if (diamondMessageHandlerSupport().handle(event) == null) {
                switch (scene.getActiveDialog().dialogClass.eventUnhandledTransition) {
                    case CONTINUE_DIALOG:
                        transitionRequest = ContinueDialog.request();
                        break;
                    case KEEP_DIALOG:
                        return true;
                    default:
                        return false;
                }
            }
            else {
                return true;
            }
        }

        // do transition
        TransitionResult transitionResult = transitionRequest.execute(sceneLoader, scene);
        if (transitionResult == null) {
            throw new DiamondRuntimeException("Transition result is null.");
        }
        scene = transitionResult.scene;

        // terminate
        if (scene == null) {
            diamondMessagingService.sendReplyMessages(transitionResult.messages, event);
            return false;
        }

        // generate messages
        List<Message> messages = new ArrayList<>();
        if (transitionResult.messages != null) {
            messages.addAll(transitionResult.messages);
        }
        if (!(transitionRequest instanceof KeepDialog)) {
            messages.addAll(scene.getActiveDialog().generateMessages(event));
        }

        // send messages
        diamondMessagingService.sendReplyMessages(messages, event);

        return true;
    }

    @Lookup
    DiamondMessageHandlerSupport diamondMessageHandlerSupport() {
        return null;
    }

}
