package tech.sejour.diamond.dialog.template;

import tech.sejour.diamond.dialog.extension.ExtendedDialogSupporter;

/**
 * Buttonsテンプレートを利用したダイアログ
 */
@ExtendedDialogSupporter(ButtonsDialogSupport.class)
public abstract class ButtonsDialog {

    /**
     * サムネイル画像のURL
     * @return
     */
    public abstract String thumbnailImageUrl();

    /**
     * タイトル
     * @return
     */
    public abstract String title();

    /**
     * 説明文
     * @return
     */
    public abstract String text();

}
