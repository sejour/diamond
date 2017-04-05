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
     * text
     * @return
     */
    String value();

    /**
     * data
     * @return
     */
    String data() default "";

}
