package tech.sejour.diamond.dialog.template.annotation;

import java.lang.annotation.*;

/**
 * Yesを表すPostbackイベント
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Postback(order = -1, label = "はい", data = "yes")
public @interface Yes {

    /**
     * 表示順
     * @return
     */
    int order();

    /**
     * ボタンを押した際にラベルテキストを送信するかどうか
     * @return
     */
    boolean displayAction() default true;

}
