package com.kpi.magazines.utils.telegram.app;

import com.kpi.magazines.utils.telegram.api.entities.Update;
import com.kpi.magazines.utils.telegram.app.commands.TelegramCommand;
import com.kpi.magazines.utils.telegram.app.commands.DefaultCommand;
import com.kpi.magazines.utils.telegram.app.commands.LatestEditionsCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Konstantin on 19.07.2016.
 *
 * Factory for TelegramCommand.
 */
public class TelegramCommandProvider {

    private static final Map<String, TelegramCommand> commands = new HashMap<>();
    private static final TelegramCommand DEFAULT_COMMAND = new DefaultCommand();

    static {
        commands.put("/latest", new LatestEditionsCommand());
    }

    /**
     *
     * @param update - Update instance received from Telegram API.
     * @return TelegramCommand instance depending on what update contains.
     */
    public static TelegramCommand getCommand(Update update) {
        final String text;
        final TelegramCommand command;
        if (update.getMessage() != null) {
            text = update.getMessage().getText();
            command = commands.get(text);
            if (command != null) {
                return command;
            }
        }
        return DEFAULT_COMMAND;
    }
}
