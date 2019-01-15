/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.barberia66Server.bean.specificImplementation.ReplyBean;
import net.barberia66Server.constants.ConfigurationConstants;
import net.barberia66Server.constants.ConfigurationConstants.EnvironmentConstants;
import net.barberia66Server.factory.ServiceFactory;
import net.barberia66Server.helper.JsonHelper;

/**
 *
 * @author a073597589g
 */
public class Barberia66 extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        response.setHeader("Access-Control-Max-Age", "86400");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, x-requested-with, Content-Type");
        String strJson;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                ReplyBean oReplyBean = ServiceFactory.executeService(request);
                strJson = JsonHelper.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
            } catch (Exception e) {
                response.setStatus(500);
                strJson = JsonHelper.strJson(500, "Server Error");
                if (ConfigurationConstants.environment == EnvironmentConstants.Debug) {
                    PrintWriter out = response.getWriter();
                    out.println(e.getMessage());
                    e.printStackTrace(out);
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            strJson = "{\"status\":500,\"msg\":\"jdbc driver not found\"}";
        }
        response.getWriter().append(strJson).close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
