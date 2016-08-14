package com.kpi.magazines.utils.telegram.app.commands;

import com.kpi.magazines.utils.telegram.api.entities.MessageToSend;
import com.kpi.magazines.utils.telegram.api.entities.Update;

/**
 * Created by Konstantin on 19.07.2016.
 *
 * Command, that should be executed depending on Update instance.
 */
public interface TelegramCommand {

    /**
     * @param update - Update from Telegram API.
     * @return message, that should be send as a response to the user.
     */
    MessageToSend execute(Update update);
}
