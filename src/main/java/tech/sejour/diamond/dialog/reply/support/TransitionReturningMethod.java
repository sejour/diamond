package tech.sejour.diamond.dialog.reply.support;

import tech.sejour.diamond.transition.NextDialog;
import tech.sejour.diamond.transition.TransitionRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * TransitionRequestを返すメソッド
 */
public class TransitionReturningMethod {

    public final Method method;

    public TransitionReturningMethod(Method method) {
        this.method = method;
    }

    public TransitionRequest invoke(Object methodOwner, Object... args) throws InvocationTargetException, IllegalAccessException {
        Object result = method.invoke(methodOwner, args);
        if (result instanceof TransitionRequest) {
            return (TransitionRequest) result;
        }
        // 戻り値がvoidまたは不明な型であればNextDialogとする
        return NextDialog.request();
    }

    public int getParameterCount() {
        return method.getParameterCount();
    }

    public Class[] getParameterTypes() {
        return method.getParameterTypes();
    }

}
