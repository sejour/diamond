package tech.sejour.diamond.dialog;

import com.linecorp.bot.model.event.Event;
import org.springframework.context.ApplicationContext;
import tech.sejour.diamond.dialog.annotation.Dialog;
import tech.sejour.diamond.dialog.message.support.MessageGenerator;
import tech.sejour.diamond.dialog.reply.support.ReplyHandler;
import tech.sejour.diamond.error.DiamondRuntimeException;
import tech.sejour.diamond.scene.Scene;
import tech.sejour.diamond.transition.TransitionRequest;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ダイアログクラスを表す
 */
public class DialogClass {

    final Class dialogClass;

    public final int id;
    public final EventUnhandledTransition eventUnhandledTransition;

    private final MessageGenerator messageGenerator;
    private final ReplyHandler replyHandler;

    public DialogClass(Class dialogClass) {
        this.dialogClass = dialogClass;

        Dialog annotation = (Dialog) this.dialogClass.getAnnotation(Dialog.class);
        if (annotation == null) throw new DiamondRuntimeException("Dialog class must be given @Dialog annotation.");
        this.id = annotation.value();
        this.eventUnhandledTransition = annotation.eventUnhandledTransition();

        // analyze fields
        Field[] fields = dialogClass.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> field.setAccessible(true));

        // analyze methods
        Method[] methods = dialogClass.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> method.setAccessible(true));

        messageGenerator = new MessageGenerator(fields, methods, dialogClass);
        replyHandler = new ReplyHandler(methods);
    }

    public Object newInstance(ApplicationContext applicationContext, Scene scene, Object... args) {
        List<Object> argsList = new ArrayList<>();
        argsList.add(scene);
        argsList.addAll(Arrays.asList(args));

        return applicationContext.getBean(dialogClass, argsList.toArray());
    }

    public List<com.linecorp.bot.model.message.Message> generateMessages(Object dialogInstance, Event event) {
        return messageGenerator.generateMessages(dialogInstance, event);
    }

    public TransitionRequest handleReply(Object dialogInstance, Event event) throws InvocationTargetException, IllegalAccessException {
        return replyHandler.handle(dialogInstance, event);
    }

}
