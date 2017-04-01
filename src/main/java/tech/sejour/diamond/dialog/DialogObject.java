package tech.sejour.diamond.dialog;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.message.Message;
import org.springframework.context.ApplicationContext;
import tech.sejour.diamond.scene.Scene;
import tech.sejour.diamond.transition.TransitionRequest;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * ダイアログクラスのインスタンス
 */
public class DialogObject {

    public final DialogClass dialogClass;
    final Object instance;

    private DialogObject(DialogClass dialogClass, Object instance) {
        this.dialogClass = dialogClass;
        this.instance = instance;
    }

    public static DialogObject newInstance(ApplicationContext applicationContext, Scene scene, DialogClass dialogClass, Object... args) {
        return new DialogObject(
                dialogClass,
                dialogClass.newInstance(applicationContext, scene, args)
        );
    }

    public int getDialogId() {
        return dialogClass.id;
    }

    public List<Message> generateMessages(Event event) {
        return dialogClass.generateMessages(instance, event);
    }

    public TransitionRequest handleReply(Event event) throws InvocationTargetException, IllegalAccessException {
        return dialogClass.handleReply(instance, event);
    }

}
