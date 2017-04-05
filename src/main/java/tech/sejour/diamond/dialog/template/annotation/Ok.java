package tech.sejour.diamond.dialog.template.annotation;

import java.lang.annotation.*;

/**
 * OKを表すPostbackイベント
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Postback(value = "OK", data = "ok")
public @interface Ok {
}
