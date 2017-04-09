package tech.sejour.diamond.event.annotation;

import java.lang.annotation.*;

/**
 * ディスカッションに入らず、常にMessageHandlerクラス内で処理するイベントのハンドラメソッドに付与する。
 * 例：BeaconEventのハンドリングは常にMessageHandlerクラス内で行うといったとき
 *   @AlwaysCatch @EventMapping
 *   public String receiveBeacon(BeaconEvent beacon) {
 *      ...
 *   }
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AlwaysCatch {
}
