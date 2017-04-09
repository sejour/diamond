package tech.sejour.diamond.dialog.reply.support;

import tech.sejour.diamond.error.DiamondRuntimeException;
import tech.sejour.diamond.event.matcher.support.MethodEventMatcherSupport;
import tech.sejour.diamond.transition.TransitionRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ユーザからのリプライを受け取って処理する @ReplyMapping が付与されたメソッドのエンティティ
 */
public class ReplyMappingMethod implements ReplyHandlerMethod {

    private final TransitionReturningMethod method;
    private final MethodEventMatcherSupport methodEventMatcherSupport;

    public ReplyMappingMethod(Method method) {
        this.method = new TransitionReturningMethod(method);

        if (this.method.getParameterCount() != 1) {
            throw new DiamondRuntimeException("Method for reply mapping must has one parameter.");
        }

        this.methodEventMatcherSupport = new MethodEventMatcherSupport(method);
    }

    @Override
    public TransitionRequest tryCall(Object methodOwner, Object receivingObject) throws InvocationTargetException, IllegalAccessException {
        if (methodEventMatcherSupport.matching(receivingObject)) {
            return method.invoke(methodOwner, receivingObject);
        }

        return null;
    }

    @Override
    public Class receivingObjectType() {
        return method.getParameterTypes()[0];
    }

}
