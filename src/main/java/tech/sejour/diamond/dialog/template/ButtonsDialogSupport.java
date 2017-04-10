package tech.sejour.diamond.dialog.template;

import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import tech.sejour.diamond.error.DiamondRuntimeException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Collectors;

/**
 * ButtonsDialogのサポートクラス
 */
public class ButtonsDialogSupport extends SimpleTemplateDialogSupport {

    @Override
    public ButtonsDialogSupport initialize(Field[] fields, Method[] methods) {
        super.initialize(fields, methods);

        if (postbackActions.size() < 1 || postbackActions.size() > 4) {
            throw new DiamondRuntimeException("Buttons dialog needs 1 to 4 postback actions.");
        }

        return this;
    }

    @Override
    public TemplateMessage generateTemplateMessage(Object dialogInstance) {
        ButtonsDialog buttonsDialog = (ButtonsDialog) dialogInstance;
        // TODO: altTextの作成
        String altText = "Sorry, can not be displayed.";
        // TODO: URLActionへの対応
        return new TemplateMessage(altText, new ButtonsTemplate(buttonsDialog.thumbnailImageUrl(), buttonsDialog.title(), buttonsDialog.text(), postbackActions.stream().map(postbackAction -> postbackAction).collect(Collectors.toList())));
    }

}
