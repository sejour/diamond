package tech.sejour.diamond.dialog.template;

import com.linecorp.bot.model.event.postback.PostbackContent;
import tech.sejour.diamond.dialog.reply.support.ReplyHandlerMethod;
import tech.sejour.diamond.dialog.reply.support.TransitionReturningMethod;
import tech.sejour.diamond.transition.PostbackTransition;
import tech.sejour.diamond.transition.TransitionRequest;

import java.lang.reflect.InvocationTargetException;

/**
 * Postbackイベントを処理するメソッド
 */
class PostbackHandlerMethod implements ReplyHandlerMethod {

    private final TransitionReturningMethod method;
    private final String targetData;
    private final String postbackActionText;

    public PostbackHandlerMethod(TransitionReturningMethod method, String targetData, String postbackActionText) {
        this.method = method;
        this.targetData = targetData;
        this.postbackActionText = postbackActionText;
    }

    @Override
    public TransitionRequest tryCall(Object methodOwner, Object receivingObject) throws InvocationTargetException, IllegalAccessException {
        String data = ((PostbackContent) receivingObject).getData();
        if (data.equals(targetData)) {
            TransitionRequest request = method.invoke(methodOwner);
             return postbackActionText == null ? request : PostbackTransition.request(postbackActionText, request);
        }

        return null;
    }

    @Override
    public Class receivingObjectType() {
        return PostbackContent.class;
    }
}
