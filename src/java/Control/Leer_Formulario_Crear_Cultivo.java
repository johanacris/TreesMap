/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.Cultivo;
import Modelo.Usuario;
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
public class Leer_Formulario_Crear_Cultivo extends HttpServlet {

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
        String nombre = request.getParameter("nombre-cultivo");
        String especie = request.getParameter("especie-cultivo");
        String genero = request.getParameter("genero-cultivo");
        String familia = request.getParameter("familia-cultivo");
        String orden = request.getParameter("orden-cultivo");
        String clase = request.getParameter("clase-cultivo");
        String division = request.getParameter("division-cultivo");
        String latitud = request.getParameter("latitud-cultivo");
        String longitud = request.getParameter("longitud-cultivo");
        String fechaString = request.getParameter("fecha-sembrado");
        LocalDate fechaAplicacion = LocalDate.parse(fechaString);
        Date fecha = java.sql.Date.valueOf(fechaAplicacion);
        String user=(String)request.getSession().getAttribute("user");
        Negocio n = new Negocio();
        Usuario usuario = n.getUsuario(user);
        
        Cultivo cultivo = new Cultivo(nombre, fecha, latitud, longitud, especie, genero, familia, orden, clase, division, usuario);
        if(n.insertarCultivo(cultivo)){
            request.getSession().setAttribute("user", user);
            response.sendRedirect("vista_usuario_normal.jsp");
//            request.getRequestDispatcher("vista_usuario_normal.jsp").forward(request, response);
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
