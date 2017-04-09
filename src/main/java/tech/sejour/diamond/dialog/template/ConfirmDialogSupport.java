package tech.sejour.diamond.dialog.template;

import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.ConfirmTemplate;
import tech.sejour.diamond.dialog.extension.ExtendedDialogSupport;
import tech.sejour.diamond.error.DiamondRuntimeException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * ConfirmDialogのサポートクラス
 */
public class ConfirmDialogSupport extends SimpleTemplateDialogSupport {

    @Override
    public ExtendedDialogSupport initialize(Field[] fields, Method[] methods) {
        super.initialize(fields, methods);

        if (postbackActions.size() != 2) {
            throw new DiamondRuntimeException("Confirm template dialog needs two postback actions.");
        }

        return this;
    }

    @Override
    public TemplateMessage generateTemplateMessage(Object dialogInstance) {
        String text = ((ConfirmDialog) dialogInstance).text();
        PostbackAction leftAction = postbackActions.get(0);
        PostbackAction rightAction = postbackActions.get(1);
        String altText = String.format("%s¥¥¥n%sまたは%sを入力してください。", text, leftAction.getLabel(), rightAction.getLabel());
        return new TemplateMessage(altText, new ConfirmTemplate(text, leftAction, rightAction));
    }

}
