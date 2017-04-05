package tech.sejour.diamond.dialog.template.annotation;

import java.lang.annotation.*;

/**
 * Yesを表すPostbackイベント
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Postback(value = "はい", data = "yes")
public @interface Yes {
}
