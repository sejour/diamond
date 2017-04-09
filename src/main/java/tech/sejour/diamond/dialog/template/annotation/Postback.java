package tech.sejour.diamond.dialog.template.annotation;

import java.lang.annotation.*;

/**
 * Postbackイベントを表す
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Postback {

    /**
     * 表示順
     * @return
     */
    int order();

    /**
     * label
     * @return
     */
    String label();

    /**
     * ユーザがボタンをクリックした際に送信されるテキスト（指定しなければテキストは送信しない）
     * @return
     */
    String actionText() default "";

    /**
     * アクションテキスト以外の受信対象のテキスト（labelは自動的に含まれる）
     * @return
     */
    String[] targetTexts() default "";

    /**
     * data
     * @return
     */
    String data();

}
