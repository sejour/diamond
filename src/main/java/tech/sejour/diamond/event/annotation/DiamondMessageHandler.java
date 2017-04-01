package tech.sejour.diamond.event.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * デフォルトのメッセージハンドラクラスに付与する
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@ComponentScan("tech.sejour.diamond")
public @interface DiamondMessageHandler {
}
