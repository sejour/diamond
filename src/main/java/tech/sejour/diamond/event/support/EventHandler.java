package tech.sejour.diamond.event.support;

import com.linecorp.bot.model.message.Message;
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
 * イベントを処理する適切なメソッドをイベントの型をもとに選択し、呼び出し操作を行うクラス
 */
public class EventHandler extends GenericEventHandler<EventMappingMethodResult> {

    private final Map<Class, List<EventMappingMethod>> eventMappingMethods;

    public EventHandler(Method[] methods) {
        eventMappingMethods = Arrays.stream(methods)
                .filter(method -> method.getAnnotation(EventMapping.class) != null && method.getAnnotation(AlwaysCatch.class) == null)
                .map(EventMappingMethod::new)
                .collect(Collectors.groupingBy(EventMappingMethod::getEventType));
    }

    @Override
    public EventMappingMethodResult tryCall(Object methodOwner, Class receivingObjectType, Object receivingObject) throws InvocationTargetException, IllegalAccessException {
        List<EventMappingMethod> methods = eventMappingMethods.get(receivingObjectType);

        if (methods != null) {
            for (EventMappingMethod method : methods) {
                EventMappingMethodResult result = method.tryCall(methodOwner, receivingObject);
                if (result != null) return result;
            }
        }

        return null;
    }

}
