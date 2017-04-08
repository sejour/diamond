package tech.sejour.diamond.dialog.reply.support;

import tech.sejour.diamond.dialog.reply.annotation.ReplyMapping;
import tech.sejour.diamond.event.support.GenericEventHandler;
import tech.sejour.diamond.transition.TransitionRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ユーザからのリプライイベントを処理するクラス
 */
public class ReplyHandler extends GenericEventHandler<TransitionRequest> {

    private Map<Class, List<ReplyHandlerMethod>> replyMappingMethods;

    public ReplyHandler(Method[] methods) {
        replyMappingMethods = Arrays.stream(methods)
                .filter(method -> method.getAnnotation(ReplyMapping.class) != null)
                .map(ReplyMappingMethod::new)
                .collect(Collectors.groupingBy(ReplyHandlerMethod::receivingObjectType));
    }

    @Override
    public TransitionRequest tryCall(Object methodOwner, Class argType, Object receivingObject) throws InvocationTargetException, IllegalAccessException {
        List<ReplyHandlerMethod> methods = replyMappingMethods.get(argType);

        if (methods != null) {
            for (ReplyHandlerMethod method : methods) {
                TransitionRequest request = method.tryCall(methodOwner, receivingObject);
                if (request != null) return request;
            }
        }

        return null;
    }

}
