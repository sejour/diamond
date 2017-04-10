package tech.sejour.diamond.event.extension;

import com.linecorp.bot.model.event.Event;
import tech.sejour.diamond.transition.TransitionRequest;

/**
 * ユーザからのイベントを一回だけスキップ（無視）するリクエスト
 */
public interface EventOneSkipRequest {

    /**
     * イベントハンドリングをスキップしたい場合にtrueを返す
     * @param event
     * @return
     */
    boolean skip(Event event);

}
