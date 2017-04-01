package tech.sejour.diamond.error;

/**
 * 実行時エラー例外 (非検査例外)
 */
public class DiamondRuntimeException extends RuntimeException {

    public DiamondRuntimeException() {
        super();
    }

    public DiamondRuntimeException(String message) {
        super(message);
    }

    public DiamondRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiamondRuntimeException(Throwable cause) {
        super(cause);
    }

}
