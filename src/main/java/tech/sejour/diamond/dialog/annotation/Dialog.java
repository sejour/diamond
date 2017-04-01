package tech.sejour.diamond.dialog.annotation;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.sejour.diamond.dialog.EventUnhandledTransition;

import java.lang.annotation.*;

/**
 * ダイアログクラスに付与する
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Scope("prototype")
public @interface Dialog {

    /**
     * ダイアログID
     * @return
     */
    int value();

    /**
     * イベントがハンドルされなかったときのトランジション
     * @return
     */
    EventUnhandledTransition eventUnhandledTransition() default EventUnhandledTransition.CONTINUE_DIALOG;

}
