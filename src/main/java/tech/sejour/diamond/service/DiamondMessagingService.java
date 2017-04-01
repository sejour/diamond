package tech.sejour.diamond.service;

import com.linecorp.bot.client.LineMessagingService;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.response.BotApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import tech.sejour.diamond.error.DiamondRuntimeException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class DiamondMessagingService {

    final LineMessagingService lineMessagingService;

    @Autowired
    public DiamondMessagingService(LineMessagingService lineMessagingService) {
        this.lineMessagingService = lineMessagingService;
    }

    public void sendReplyMessages(List<Message> messages, Event event) throws IOException, InvocationTargetException, IllegalAccessException {
        if (messages == null || messages.isEmpty()) return;

        Method getReplyTokenMethod = null;
        try {
            getReplyTokenMethod = event.getClass().getMethod("getReplyToken");
        } catch (NoSuchMethodException e) {
            throw new DiamondRuntimeException("The event does not correspond to reply.", e);
        }
        String replyToken = (String) getReplyTokenMethod.invoke(event);

        Response<BotApiResponse> response = lineMessagingService.replyMessage(new ReplyMessage(replyToken, messages)).execute();
        if (!response.isSuccessful()) {
            throw new DiamondRuntimeException(String.format("Line messaging api request is failured. (code=%d) ---> %s", response.code(), response.message()));
        }
    }

}
