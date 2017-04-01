package tech.sejour.diamond.event.support;

import com.linecorp.bot.model.event.*;
import com.linecorp.bot.model.event.beacon.BeaconContent;
import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.model.event.postback.PostbackContent;

import java.lang.reflect.InvocationTargetException;

/**
 * イベント処理用のクラスを実装するためのジェネリクス型
 * @param <R> ハンドリングメソッドのの戻り値
 */
public abstract class GenericEventHandler<R> {

    /**
     * イベントの型からイベントを処理するメソッドを選択し、実行します。
     * @param methodOwner
     * @param event
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public R handle(Object methodOwner, Event event) throws InvocationTargetException, IllegalAccessException {
        if (event instanceof MessageEvent) {
            MessageContent messageContent = ((MessageEvent) event).getMessage();

            if (messageContent instanceof TextMessageContent) {
                TextMessageContent textMessageContent = (TextMessageContent) messageContent;

                R stringResult = tryCall(String.class, methodOwner, textMessageContent.getText());
                if (stringResult != null) return stringResult;

                R textMessageContentResult = tryCall(TextMessageContent.class, methodOwner, textMessageContent);
                if (textMessageContentResult != null) return textMessageContentResult;
            }
            else if (messageContent instanceof ImageMessageContent) {
                R imageMessageContentResult = tryCall(ImageMessageContent.class, methodOwner, messageContent);
                if (imageMessageContentResult != null) return imageMessageContentResult;
            }
            else if (messageContent instanceof VideoMessageContent) {
                R videoMessageContentResult = tryCall(VideoMessageContent.class, methodOwner, messageContent);
                if (videoMessageContentResult != null) return videoMessageContentResult;
            }
            else if (messageContent instanceof AudioMessageContent) {
                R audioMessageContentResult = tryCall(AudioMessageContent.class, methodOwner, messageContent);
                if (audioMessageContentResult != null) return audioMessageContentResult;
            }
            else if (messageContent instanceof LocationMessageContent) {
                R locationMessageContentResult = tryCall(LocationMessageContent.class, methodOwner, messageContent);
                if (locationMessageContentResult != null) return locationMessageContentResult;
            }
            else if (messageContent instanceof StickerMessageContent) {
                R stickerMessageContentResult = tryCall(StickerMessageContent.class, methodOwner, messageContent);
                if (stickerMessageContentResult != null) return stickerMessageContentResult;
            }

            R messageContentResult = tryCall(MessageContent.class, methodOwner, messageContent);
            if (messageContentResult != null) return messageContentResult;

            R messageEventResult = tryCall(MessageEvent.class, methodOwner, event);
            if (messageEventResult != null) return messageEventResult;
        }
        else if (event instanceof FollowEvent) {
            R followEventResult = tryCall(FollowEvent.class, methodOwner, event);
            if (followEventResult != null) return followEventResult;
        }
        else if (event instanceof UnfollowEvent) {
            R unfollowEventResult = tryCall(UnfollowEvent.class, methodOwner, event);
            if (unfollowEventResult != null) return unfollowEventResult;
        }
        else if (event instanceof JoinEvent) {
            R joinEventResult = tryCall(JoinEvent.class, methodOwner, event);
            if (joinEventResult != null) return joinEventResult;
        }
        else if (event instanceof LeaveEvent) {
            R leaveEventResult = tryCall(LeaveEvent.class, methodOwner, event);
            if (leaveEventResult != null) return leaveEventResult;
        }
        else if (event instanceof PostbackEvent) {
            PostbackContent postbackContent = ((PostbackEvent) event).getPostbackContent();

            R postbackContentResult = tryCall(PostbackContent.class, methodOwner, postbackContent);
            if (postbackContentResult != null) return postbackContentResult;

            R postbackEventResult = tryCall(PostbackEvent.class, methodOwner, event);
            if (postbackEventResult != null) return postbackEventResult;
        }
        else if (event instanceof BeaconEvent) {
            BeaconContent beaconContent = ((BeaconEvent) event).getBeacon();

            R beaconContentResult = tryCall(BeaconContent.class, methodOwner, beaconContent);
            if (beaconContentResult != null) return beaconContentResult;

            R beaconEventResult = tryCall(BeaconEvent.class, methodOwner, event);
            if (beaconEventResult != null) return beaconEventResult;
        }

        R eventResult = tryCall(Event.class, methodOwner, event);
        if (eventResult != null) {
            return eventResult;
        }

        return null;
    }

    public abstract R tryCall(Class eventType, Object methodOwner, Object arg) throws InvocationTargetException, IllegalAccessException;

}
