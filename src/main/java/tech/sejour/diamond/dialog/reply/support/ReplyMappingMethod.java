package tech.sejour.diamond.dialog.reply.support;

import tech.sejour.diamond.error.DiamondRuntimeException;
import tech.sejour.diamond.event.matcher.support.MethodEventMatcherSupport;
import tech.sejour.diamond.transition.NextDialog;
import tech.sejour.diamond.transition.TransitionRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Shuka on 2017/03/30.
 */
public class ReplyMappingMethod {

    private final Method method;
    private final MethodEventMatcherSupport methodEventMatcherSupport;

    public ReplyMappingMethod(Method method) {
        this.method = method;

        if (this.method.getParameterCount() != 1) {
            throw new DiamondRuntimeException("Method for reply mapping must has one parameter.");
        }

        this.methodEventMatcherSupport = new MethodEventMatcherSupport(this.method);
    }

    public TransitionRequest tryCall(Object dialogInstance, Object arg) throws InvocationTargetException, IllegalAccessException {
        if (methodEventMatcherSupport.matching(arg)) {
            Object result = method.invoke(dialogInstance, arg);
            if (result instanceof TransitionRequest) {
                return (TransitionRequest) result;
            }
            // 戻り値がvoidまたは不明な型であればNextDialogとする
            return NextDialog.request();
        }

        return null;
    }

    public Class getEventType() {
        return method.getParameterTypes()[0];
    }

}
