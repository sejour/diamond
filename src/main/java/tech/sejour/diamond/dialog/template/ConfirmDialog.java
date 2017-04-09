package tech.sejour.diamond.dialog.template;

import tech.sejour.diamond.dialog.extension.ExtendedDialogSupporter;

/**
 * Confirmテンプレートを利用したダイアログ
 */
@ExtendedDialogSupporter(ConfirmDialogSupport.class)
public abstract class ConfirmDialog {

    /**
     * ConfirmTemplateのテキストメッセージ
     * @return
     */
    public abstract String text();

}
