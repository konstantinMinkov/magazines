package com.kpi.magazines.utils.telegram.app.commands;

import com.kpi.magazines.utils.telegram.api.entities.MessageToSend;
import com.kpi.magazines.utils.telegram.api.entities.Update;

/**
 * Created by Konstantin Minkov on 08.08.2016.
 *
 * Command for sending an error message if no other command can be executed.
 */
public class DefaultCommand implements TelegramCommand {

    private static final String ERROR_MESSAGE_TEXT = "I don't understand what you are trying to say to me :(";

    @Override
    public MessageToSend execute(Update update) {
        final MessageToSend messageToSend = new MessageToSend();
        messageToSend.setText(ERROR_MESSAGE_TEXT);
        messageToSend.setChatId(update.getMessage().getChat().getId());
        return messageToSend;
    }
}
