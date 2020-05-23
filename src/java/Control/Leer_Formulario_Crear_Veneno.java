/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.Cultivo;
import Modelo.Fertilizante;
import Modelo.FertilizanteCultivo;
import Modelo.Veneno;
import Modelo.VenenoCultivo;
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
public class Leer_Formulario_Crear_Veneno extends HttpServlet {

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
        String nombre = request.getParameter("nombre-veneno");
        String tipo = request.getParameter("tipo-veneno");
        String fechaString = request.getParameter("fecha-veneno");
        String notas = request.getParameter("anotacion");
        LocalDate fechaAplicacion = LocalDate.parse(fechaString);
        Date fecha = java.sql.Date.valueOf(fechaAplicacion);
        String user=(String)request.getSession().getAttribute("user");
        Negocio n = new Negocio();
        String cultivoId = (String)request.getSession().getAttribute("cultivo");
        Cultivo cultivo=n.getCultivo(Integer.parseInt(cultivoId));
        
        Veneno veneno = new Veneno(nombre, tipo);
        VenenoCultivo venenoCultivo = new VenenoCultivo(veneno, cultivo, fecha, notas);
        if(n.insertarVeneno(veneno)){
            if(n.insertarVenenoCultivo(venenoCultivo)){ 
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
