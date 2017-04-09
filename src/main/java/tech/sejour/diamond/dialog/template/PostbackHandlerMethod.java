package tech.sejour.diamond.dialog.template;

import com.linecorp.bot.model.event.postback.PostbackContent;
import tech.sejour.diamond.dialog.reply.support.ReplyHandlerMethod;
import tech.sejour.diamond.dialog.reply.support.TransitionReturningMethod;
import tech.sejour.diamond.transition.TransitionRequest;

import java.lang.reflect.InvocationTargetException;

/**
 * Postbackイベントを処理するメソッド
 */
class PostbackHandlerMethod implements ReplyHandlerMethod {

    private final TransitionReturningMethod method;
    private final String targetData;

    public PostbackHandlerMethod(TransitionReturningMethod method, String targetData) {
        this.method = method;
        this.targetData = targetData;
    }

    @Override
    public TransitionRequest tryCall(Object methodOwner, Object receivingObject) throws InvocationTargetException, IllegalAccessException {
        String data = ((PostbackContent) receivingObject).getData();
        if (data.equals(targetData)) {
            return method.invoke(methodOwner);
        }

        return null;
    }

    @Override
    public Class receivingObjectType() {
        return PostbackContent.class;
    }
}
