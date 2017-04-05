package tech.sejour.diamond.dialog.template.annotation;

import java.lang.annotation.*;

/**
 * Noを表すPostbackイベント
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Postback(value = "いいえ", data = "no")
public @interface No {
}
