package tech.sejour.diamond.dialog.template;

import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import tech.sejour.diamond.dialog.extension.ExtendedDialogSupport;
import tech.sejour.diamond.dialog.reply.support.ReplyHandlerMethod;
import tech.sejour.diamond.dialog.template.annotation.Postback;
import tech.sejour.diamond.error.DiamondRuntimeException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 単一のテンプレートダイアログをサポートする抽象クラス
 */
public abstract class SimpleTemplateDialogSupport implements ExtendedDialogSupport {

    protected List<PostbackAction> postbackActions;
    private List<ReplyHandlerMethod> replyHandlerMethods;

    @Override
    public ExtendedDialogSupport initialize(Field[] fields, Method[] methods) {
        // @Postbackが付与されたメソッドをPostbackEntityに変換して取り出す
        List<PostbackEntity> postbackEntities = Arrays.stream(methods)
                .map(method -> {
                    return Arrays.stream(method.getAnnotations())
                            .map(annotation -> {
                                // Annotaion自体が@Postbackであったとき
                                if (annotation instanceof Postback) {
                                    return new PostbackEntity(method, (Postback) annotation);
                                }

                                // Annotationに付与されたアノテーションが@Postbackであったとき
                                Postback postback = annotation.annotationType().getAnnotation(Postback.class);
                                if (postback != null) {
                                    // orderを取得
                                    Integer order = null;
                                    try {
                                        Method orderMethod = annotation.annotationType().getMethod("order");
                                        order = (int) orderMethod.invoke(annotation);
                                    }
                                    catch (NoSuchMethodException e) {}
                                    catch (Throwable e) {
                                        throw new DiamondRuntimeException(e);
                                    }

                                    // displayActionを取得
                                    Boolean displayAction = null;
                                    try {
                                        Method orderMethod = annotation.annotationType().getMethod("displayAction");
                                        displayAction = (boolean) orderMethod.invoke(annotation);
                                    }
                                    catch (NoSuchMethodException e) {}
                                    catch (Throwable e) {
                                        throw new DiamondRuntimeException(e);
                                    }

                                    return new PostbackEntity(method, postback, order, displayAction);
                                }

                                // @Postbackアノテーションではなかったとき
                                return null;
                            })
                            .filter(postbackEntity -> postbackEntity != null)
                            .findFirst();
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        // PostbackActionをorderでソートして取り出す
        this.postbackActions = postbackEntities.stream()
                .sorted((entity1, entity2) -> Integer.compare(entity1.order, entity2.order))
                .map(postbackEntity -> postbackEntity.action)
                .collect(Collectors.toList());

        // ReplyHandlerMethodsを取り出す
        this.replyHandlerMethods = postbackEntities.stream()
                .flatMap(postbackEntity -> postbackEntity.handlerMethods.stream())
                .collect(Collectors.toList());

        return this;
    }

    public abstract TemplateMessage generateTemplateMessage(Object dialogInstance);

    @Override
    public List<Message> generateMessages(Object dialogInstance) {
        return Arrays.asList(generateTemplateMessage(dialogInstance));
    }

    @Override
    public List<ReplyHandlerMethod> replyMappingMethods() {
        return replyHandlerMethods;
    }

}
