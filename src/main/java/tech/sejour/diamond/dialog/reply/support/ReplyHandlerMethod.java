package tech.sejour.diamond.dialog.reply.support;

import tech.sejour.diamond.transition.TransitionRequest;

import java.lang.reflect.InvocationTargetException;

/**
 * ユーザからのリプライを処理するメソッドエンティティ
 */
public interface ReplyHandlerMethod {

    TransitionRequest tryCall(Object methodOwner, Object receivingObject) throws InvocationTargetException, IllegalAccessException;

    Class receivingObjectType();

}
