package com.kpi.magazines.utils.telegram.app;

import com.kpi.magazines.utils.http.Http;
import com.kpi.magazines.utils.telegram.api.Telegram;
import com.kpi.magazines.utils.telegram.api.entities.Update;

import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Konstantin on 18.07.2016.
 *
 * Class, that emulates Telegram Webhook service. Is needed before application will be loaded on
 * web server with static IP, where Telegram Webhook can be installed.
 */
public class WebhookEmulationRunner {

    private static final Telegram telegram;
    private static final String url;

    static {
        final String token = ResourceBundle.getBundle("telegram").getString("token");
        telegram = new Telegram(token);
        url = "http://localhost:8080/" + token;
    }

    /**
     * Retrieves updates from Telegram API and sends them to TelegramController.
     * @param args
     */
    public static void main(String[] args) {
        final int limit = 1;
        int offset = 0;
        List<Update> updates;

        while (true) {
            updates = telegram.getUpdates(offset, limit);
            if ( !updates.isEmpty()) {
                final Update update = updates.get(0);
                Http.post(url, update);
                offset = update.getId() + 1;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
