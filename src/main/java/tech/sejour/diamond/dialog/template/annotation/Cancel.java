package tech.sejour.diamond.dialog.template.annotation;

import java.lang.annotation.*;

/**
 * キャンセルを表すPostbackイベント
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Postback(value = "キャンセル", data = "cancel")
public @interface Cancel {
}
