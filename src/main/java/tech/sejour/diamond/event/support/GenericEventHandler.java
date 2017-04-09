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

                R stringResult = tryCall(methodOwner, String.class, textMessageContent.getText());
                if (stringResult != null) return stringResult;

                R textMessageContentResult = tryCall(methodOwner, TextMessageContent.class, textMessageContent);
                if (textMessageContentResult != null) return textMessageContentResult;
            }
            else if (messageContent instanceof ImageMessageContent) {
                R imageMessageContentResult = tryCall(methodOwner, ImageMessageContent.class, messageContent);
                if (imageMessageContentResult != null) return imageMessageContentResult;
            }
            else if (messageContent instanceof VideoMessageContent) {
                R videoMessageContentResult = tryCall(methodOwner, VideoMessageContent.class, messageContent);
                if (videoMessageContentResult != null) return videoMessageContentResult;
            }
            else if (messageContent instanceof AudioMessageContent) {
                R audioMessageContentResult = tryCall(methodOwner, AudioMessageContent.class, messageContent);
                if (audioMessageContentResult != null) return audioMessageContentResult;
            }
            else if (messageContent instanceof LocationMessageContent) {
                R locationMessageContentResult = tryCall(methodOwner, LocationMessageContent.class, messageContent);
                if (locationMessageContentResult != null) return locationMessageContentResult;
            }
            else if (messageContent instanceof StickerMessageContent) {
                R stickerMessageContentResult = tryCall(methodOwner, StickerMessageContent.class, messageContent);
                if (stickerMessageContentResult != null) return stickerMessageContentResult;
            }

            R messageContentResult = tryCall(methodOwner, MessageContent.class, messageContent);
            if (messageContentResult != null) return messageContentResult;

            R messageEventResult = tryCall(methodOwner, MessageEvent.class, event);
            if (messageEventResult != null) return messageEventResult;
        }
        else if (event instanceof FollowEvent) {
            R followEventResult = tryCall(methodOwner, FollowEvent.class, event);
            if (followEventResult != null) return followEventResult;
        }
        else if (event instanceof UnfollowEvent) {
            R unfollowEventResult = tryCall(methodOwner, UnfollowEvent.class, event);
            if (unfollowEventResult != null) return unfollowEventResult;
        }
        else if (event instanceof JoinEvent) {
            R joinEventResult = tryCall(methodOwner, JoinEvent.class, event);
            if (joinEventResult != null) return joinEventResult;
        }
        else if (event instanceof LeaveEvent) {
            R leaveEventResult = tryCall(methodOwner, LeaveEvent.class, event);
            if (leaveEventResult != null) return leaveEventResult;
        }
        else if (event instanceof PostbackEvent) {
            PostbackContent postbackContent = ((PostbackEvent) event).getPostbackContent();

            R postbackContentResult = tryCall(methodOwner, PostbackContent.class, postbackContent);
            if (postbackContentResult != null) return postbackContentResult;

            R postbackEventResult = tryCall(methodOwner, PostbackEvent.class, event);
            if (postbackEventResult != null) return postbackEventResult;
        }
        else if (event instanceof BeaconEvent) {
            BeaconContent beaconContent = ((BeaconEvent) event).getBeacon();

            R beaconContentResult = tryCall(methodOwner, BeaconContent.class, beaconContent);
            if (beaconContentResult != null) return beaconContentResult;

            R beaconEventResult = tryCall(methodOwner, BeaconEvent.class, event);
            if (beaconEventResult != null) return beaconEventResult;
        }

        R eventResult = tryCall(methodOwner, Event.class, event);
        if (eventResult != null) {
            return eventResult;
        }

        return null;
    }

    public abstract R tryCall(Object methodOwner, Class receivingObjectType, Object receivingObject) throws InvocationTargetException, IllegalAccessException;

}
