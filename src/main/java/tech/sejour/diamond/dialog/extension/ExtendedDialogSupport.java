package tech.sejour.diamond.dialog.extension;

import com.linecorp.bot.model.message.Message;
import tech.sejour.diamond.dialog.reply.support.ReplyHandlerMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 拡張ダイアログのサポートインタフェース
 */
public interface ExtendedDialogSupport {

    ExtendedDialogSupport initialize(Field[] fields, Method[] method);

    List<Message> generateMessages(Object dialogInstance);

    List<ReplyHandlerMethod> replyMappingMethods();

}
