package tech.sejour.diamond.dialog.template.annotation;

import java.lang.annotation.*;

/**
 * キャンセルを表すPostbackイベント
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Postback(order = -1, label = "キャンセル", data = "cancel")
public @interface Cancel {

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
