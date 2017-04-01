package tech.sejour.diamond.dialog.message.annotation;

import java.lang.annotation.*;

/**
 * メッセージを表すフィールドまたはメッセージを生成するメソッドに付与する
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Message {

    /**
     * メッセージの送信順序
     * @return
     */
    int order() default 0;

}
