package tech.sejour.diamond.dialog.template;

import tech.sejour.diamond.dialog.reply.support.ReplyHandlerMethod;
import tech.sejour.diamond.dialog.reply.support.TransitionReturningMethod;
import tech.sejour.diamond.transition.TransitionRequest;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Postbackイベントに相当するテンプレートテキストを処理するメソッド
 */
class TemplateTextHandlerMethod implements ReplyHandlerMethod {

    private final TransitionReturningMethod method;
    private final List<String> targetTexts;

    public TemplateTextHandlerMethod(TransitionReturningMethod method, List<String> targetTexts) {
        this.method = method;
        this.targetTexts = targetTexts;
    }

    @Override
    public TransitionRequest tryCall(Object methodOwner, Object receivingObject) throws InvocationTargetException, IllegalAccessException {
        String receivedText = (String) receivingObject;
        if (targetTexts.stream().anyMatch(text -> text.equals(receivedText))) {
            return method.invoke(methodOwner);
        }

        return null;
    }

    @Override
    public Class receivingObjectType() {
        return String.class;
    }

}
