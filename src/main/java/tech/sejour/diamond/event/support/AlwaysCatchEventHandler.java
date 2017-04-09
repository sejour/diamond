package tech.sejour.diamond.event.support;

import com.linecorp.bot.spring.boot.annotation.EventMapping;
import tech.sejour.diamond.discussion.DiscussionRequest;
import tech.sejour.diamond.error.DiamondRuntimeException;
import tech.sejour.diamond.event.annotation.AlwaysCatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @AlwaysCatchのイベントを処理するクラス
 */
public class AlwaysCatchEventHandler extends GenericEventHandler<EventMappingMethodResult> {

    private final Map<Class, List<EventMappingMethod>> alwaysCatchMethods;

    public AlwaysCatchEventHandler(Method[] methods) {
        alwaysCatchMethods = Arrays.stream(methods)
                .filter(method -> method.getAnnotation(EventMapping.class) != null && method.getAnnotation(AlwaysCatch.class) != null)
                .map(EventMappingMethod::new)
                .collect(Collectors.groupingBy(EventMappingMethod::getEventType));
    }

    @Override
    public EventMappingMethodResult tryCall(Object methodOwner, Class receivingObjectType, Object receivingObject) throws InvocationTargetException, IllegalAccessException {
        List<EventMappingMethod> methods = alwaysCatchMethods.get(receivingObjectType);

        if (methods != null) {
            for (EventMappingMethod method : methods) {
                EventMappingMethodResult result = method.tryCall(methodOwner, receivingObject);
                if (result instanceof DiscussionRequest) throw new DiamondRuntimeException("Method that is given @AlwaysCatch can not return DiscussionRequest.");
                if (result != null) return result;
            }
        }

        return null;
    }

}
