package com.kpi.magazines.servlets;

import com.kpi.magazines.utils.telegram.api.Telegram;
import com.kpi.magazines.utils.telegram.api.entities.MessageToSend;
import com.kpi.magazines.utils.telegram.api.entities.Update;
import com.kpi.magazines.utils.telegram.app.TelegramCommandProvider;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by Konstantin Minkov on 06.08.2016.
 *
 * Front servlet for Telegram bot application.
 */

@Log4j2
public class TelegramController extends HttpServlet {

    private static final Telegram telegram;

    static {
        final ResourceBundle resourceBundle = ResourceBundle.getBundle("telegram");
        telegram = new Telegram(resourceBundle.getString("token"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        final Update update;
        final MessageToSend messageToSend;
        try {
            update = telegram.parseUpdate(new BufferedInputStream(request.getInputStream()));
        } catch (IOException e) {
            log.catching(e);
            return;
        }
        messageToSend = TelegramCommandProvider.getCommand(update).execute(update);
        telegram.sendMessage(messageToSend);
    }
}
