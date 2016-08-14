package com.kpi.magazines.utils.telegram.api;

import com.kpi.magazines.utils.http.Http;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kpi.magazines.utils.telegram.api.entities.Message;
import com.kpi.magazines.utils.telegram.api.entities.MessageToSend;
import com.kpi.magazines.utils.telegram.api.entities.Update;
import com.kpi.magazines.utils.telegram.api.utils.Method;
import lombok.extern.log4j.Log4j2;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Konstantin on 18.07.2016.
 */

/**
 * Class that provides basic features for communication with Telegram API.
 */
@Log4j2
public class Telegram {

    private static final Gson gson = new Gson();
    private static final String TELEGRAM_URL = "https://api.telegram.org/bot";
    private final String TELEGRAM_BOT_URL;

    /**
     * @param apiToken - private API token of application.
     */
    public Telegram(String apiToken) {
        TELEGRAM_BOT_URL = TELEGRAM_URL + apiToken;
    }

    /**
     * Parses Update instance from input stream, that provides JSON String.
     * @param inputStream - InputStream instance with Update JSON.
     * @return
     */
    public Update parseUpdate(InputStream inputStream) {
        final StringBuilder builder = new StringBuilder();
        int c;
        try {
            while ((c = inputStream.read()) > -1) {
                builder.append((char) c);
            }
            return gson.fromJson(builder.toString(), Update.class);
        } catch (Exception e) {
            log.catching(e);
        }
        return null;
    }

    /**
     * Sends a request to Telegram API.
     * @param offset - offset from the first Update, pending on Telegram server.
     * @param limit - Quantity of Update to be loaded at once.
     * @return List of Update objects.
     */
    public List<Update> getUpdates(int offset, int limit) {
        final Map<String, Object> data = new HashMap<>();
        final JsonObject responseJson;
        final Type type;
        data.put("offset", offset);
        data.put("limit", limit);
        responseJson = Http.post(TELEGRAM_BOT_URL + Method.GET_UPDATES, data);
        type = new TypeToken<List<Update>>(){}.getType();
        return gson.fromJson(responseJson.get("result"), type);
    }

    /**
     * Send message to the
     * @param messageToSend - MessageToSend instance, that contains text or data to be send to
     *                      the specific user or chat.
     * @return Message instance with the info of messageToSend if message was sent, else null.
     */
    public Message sendMessage(MessageToSend messageToSend) {
        final JsonObject responseJson =
                Http.post(TELEGRAM_BOT_URL + Method.SEND_MESSAGE, messageToSend);
        if (responseJson != null) {
            return gson.fromJson(responseJson, Message.class);
        }
        return null;
    }
}
