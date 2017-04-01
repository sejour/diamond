package tech.sejour.diamond.event.support;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import tech.sejour.diamond.discussion.DiscussionRequest;
import tech.sejour.diamond.error.DiamondRuntimeException;
import tech.sejour.diamond.event.matcher.support.MethodEventMatcherSupport;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * イベントを処理するメソッドを表す
 */
public class EventMappingMethod {

    private final Method method;
    private final MethodEventMatcherSupport methodEventMatcherSupport;

    public EventMappingMethod(Method method) {
        this.method = method;

        if (this.method.getParameterCount() != 1) {
            throw new DiamondRuntimeException("Method for reply mapping must has one parameter.");
        }

        this.methodEventMatcherSupport = new MethodEventMatcherSupport(this.method);
    }

    public EventMappingMethodResult tryCall(Object dialogInstance, Object arg) throws InvocationTargetException, IllegalAccessException {
        if (methodEventMatcherSupport.matching(arg)) {
            Object result = method.invoke(dialogInstance, arg);

            if (result instanceof DiscussionRequest) {
                return (DiscussionRequest) result;
            }
            else if (result instanceof Message) {
                return new EventMappingMethodResult(Arrays.asList((Message) result));
            }
            else if (result instanceof String) {
                return new EventMappingMethodResult(Arrays.asList(new TextMessage((String) result)));
            }
            else if (result instanceof List<?>) {
                List<?> messages = (List<?>) result;
                return new EventMappingMethodResult(messages.stream()
                                .map(message -> message instanceof Message ? (Message) message : (message instanceof String) ? new TextMessage((String) message) : null)
                                .filter(message -> message != null)
                                .collect(Collectors.toList())
                );
            }

            return new EventMappingMethodResult(null);
        }

        return null;
    }

    public Class getEventType() {
        return method.getParameterTypes()[0];
    }

}
