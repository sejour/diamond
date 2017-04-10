package tech.sejour.diamond.dialog.template;

import com.linecorp.bot.model.action.PostbackAction;
import tech.sejour.diamond.dialog.reply.support.ReplyHandlerMethod;
import tech.sejour.diamond.dialog.reply.support.TransitionReturningMethod;
import tech.sejour.diamond.dialog.template.annotation.Postback;
import tech.sejour.diamond.error.DiamondRuntimeException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Postbackアノテーションが付与されたメソッドのエンティティ
 */
class PostbackEntity {

    public final int order;
    public final PostbackAction action;
    public final List<ReplyHandlerMethod> handlerMethods;

    public PostbackEntity(Method method, Postback annotation) {
        this(method, annotation, null, null);
    }

    public PostbackEntity(Method method, Postback annotation, Integer order, Boolean displayAction) {
        this.order = order == null ? annotation.order() : order;

        String data = annotation.data();
        if (data.isEmpty()) throw new DiamondRuntimeException("Postback data must not be empty.");

        String label = annotation.label();
        if (label.isEmpty()) throw new DiamondRuntimeException("Postback label must not be empty.");

        String theActionText = annotation.actionText();
        String actionText = displayAction == null ? (theActionText.isEmpty() ? null : theActionText)
                : (displayAction.booleanValue() ? (theActionText.isEmpty() ? label : theActionText) : null);

        List<String> targetTexts = new ArrayList<>();
        targetTexts.add(label);
        if (actionText != null && !actionText.isEmpty()) targetTexts.add(actionText);
        targetTexts.addAll(Arrays.asList(annotation.targetTexts()));

        // PostbackAction
        this.action = new PostbackAction(label, data, actionText);

        // PostbackHandlerMethods
        TransitionReturningMethod transitionReturningMethod = new TransitionReturningMethod(method);
        this.handlerMethods = Arrays.asList(new PostbackHandlerMethod(transitionReturningMethod, data, actionText), new TemplateTextHandlerMethod(transitionReturningMethod, targetTexts));
    }

}
