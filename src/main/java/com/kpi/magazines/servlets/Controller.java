package com.kpi.magazines.servlets;

import com.kpi.magazines.commands.Command;
import com.kpi.magazines.commands.pools.CommandProvider;
import com.kpi.magazines.config.Page;
import com.kpi.magazines.config.Routes;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Function;

/**
 * Created by Konstantin Minkov on 26.07.2016.
 *
 * Application controller class. Provides basic REST methods.
 */

@MultipartConfig
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp, command -> command.executeGet(req, resp));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processPostRequest(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp, command -> command.executePut(req, resp));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp, command -> command.executeDelete(req, resp));
    }

    /**
     * Because of HTML forms can send only GET or POST request, we should emulate work
     * of PUT and DELETE methods by adding hidden input field "_method" in case we want to
     * use those two methods. This method parse _method field.
     * @param request - HttpServletRequest.
     * @param response - HttpServletResponce.
     * @throws ServletException
     * @throws IOException
     */
    private void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        final String method = request.getParameter("_method");
        switch (method != null ? method : "post") {
            case "put":
                doPut(request, response);
                break;
            case "delete":
                doDelete(request, response);
                break;
            default:
                processRequest(request, response, command -> command.executePost(request, response));
        }
    }

    /**
     * Processes the request from doGet, doPost, doPut and doDelete.
     * @param request - HttpServletRequest.
     * @param response - HttpServletResponse.
     * @param method - lambda, that takes Command and returns Page depending on inner behaviour.
     * @throws ServletException
     * @throws IOException
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response, Function<Command, Page> method)
            throws ServletException, IOException {
        final Command command = CommandProvider.getCommand(request);
        Page page;
        try {
            page = method.apply(command);
        } catch (UnsupportedOperationException e) {
            page = command.executeGet(request, response);
        }
        if (page.isRedirect()) {
            response.sendRedirect(request.getAttribute("userURI") + "/" + page.getCommand());
        } else {
            request.setAttribute("page", page.getPageRoute());
            getServletContext().getRequestDispatcher(Routes.HOLDER_PAGE).forward(request, response);
        }
    }
}
