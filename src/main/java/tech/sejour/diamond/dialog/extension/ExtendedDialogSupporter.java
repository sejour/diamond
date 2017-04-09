package tech.sejour.diamond.dialog.extension;

import java.lang.annotation.*;

/**
 * 拡張のダイアログサポートクラスが必要な場合に付与する。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ExtendedDialogSupporter {

    Class<? extends ExtendedDialogSupport> value();

}
