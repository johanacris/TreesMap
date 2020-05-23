/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.Cultivo;
import Modelo.Plaga;
import Modelo.PlagaCultivo;
import Negocio.Negocio;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Romario
 */
public class Leer_Formulario_Crear_Plaga extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String nombre = request.getParameter("nombre-plaga");
        String tipo = request.getParameter("tipo-plaga");
        String fechaString = request.getParameter("fecha-plaga");
        String notas = request.getParameter("anotacion");
        LocalDate fechaAplicacion = LocalDate.parse(fechaString);
        Date fecha = java.sql.Date.valueOf(fechaAplicacion);
        String user=(String)request.getSession().getAttribute("user");
        Negocio n = new Negocio();
        String cultivoId = (String)request.getSession().getAttribute("cultivo");
        Cultivo cultivo=n.getCultivo(Integer.parseInt(cultivoId));
        
        Plaga plaga = new Plaga(nombre, tipo);
        PlagaCultivo plagaCultivo = new PlagaCultivo(fecha, notas, cultivo, plaga);
        if(n.insertarPlaga(plaga)){
            if(n.insertarPlagaCultivo(plagaCultivo)){ 
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("cultivo", cultivoId);
                response.sendRedirect("vista_detalle_cultivo.jsp");
            }
            
        }
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
