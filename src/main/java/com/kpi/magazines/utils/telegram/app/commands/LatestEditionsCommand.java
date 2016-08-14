package com.kpi.magazines.utils.telegram.app.commands;

import com.kpi.magazines.beans.Edition;
import com.kpi.magazines.dao.basic.DaoManager;
import com.kpi.magazines.dao.basic.interfaces.EditionDao;
import com.kpi.magazines.utils.velocity.VelocityService;
import com.kpi.magazines.utils.telegram.api.entities.MessageToSend;
import com.kpi.magazines.utils.telegram.api.entities.Update;
import org.apache.velocity.VelocityContext;

import java.util.List;

/**
 * Created by Konstantin Minkov on 06.08.2016.
 *
 * Returns a message with list of latest Editions in quantity of LIMIT.
 * See editions.vm file in resources folder.
 */
public class LatestEditionsCommand implements TelegramCommand {

    private static final String TEMPLATE_FILE = "editions.vm";
    private static final int LIMIT = 5;
    private static final int OFFSET = 0;

    @Override
    public MessageToSend execute(Update update) {
        final EditionDao editionDao = DaoManager.getEditionDao();
        final List<Edition> editions = editionDao.findAllByUpdateTimeDesc(LIMIT, OFFSET);
        final MessageToSend messageToSend = new MessageToSend();
        final VelocityContext context = new VelocityContext();
        context.internalPut("editions", editions);
        messageToSend.setText(VelocityService.render(TEMPLATE_FILE, context));
        messageToSend.setParseMode("HTML");
        messageToSend.setDisableWebPagePreview(true);
        messageToSend.setChatId(update.getMessage().getChat().getId());
        return messageToSend;
    }
}
