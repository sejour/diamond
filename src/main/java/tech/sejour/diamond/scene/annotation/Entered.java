package tech.sejour.diamond.scene.annotation;

import java.lang.annotation.*;

/**
 * シーンに入った時に呼び出すメソッドに付与する
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Entered {
}
