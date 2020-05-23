/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.Cultivo;
import Modelo.Fertilizante;
import Modelo.FertilizanteCultivo;
import Modelo.PlagaCultivo;
import Modelo.Usuario;
import Modelo.VenenoCultivo;
import Negocio.Negocio;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Romario
 */
public class Eliminar_Cultivo extends HttpServlet {

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
        String user=(String)request.getSession().getAttribute("user");
        String codigoCultivo = request.getParameter("cultivo-a-eliminar");
        int cultivo = Integer.parseInt(codigoCultivo);
        Negocio n = new Negocio();
        Cultivo c = n.getCultivo(cultivo);
        if(!c.getFertilizanteCultivoList().isEmpty()){
            for(FertilizanteCultivo fc:c.getFertilizanteCultivoList()){
                n.borraFertilizanteCultivo(fc.getFertilizanteCultivoPK());
            }
        }
        
        if(!c.getPlagaCultivoList().isEmpty()){
            for(PlagaCultivo pc:c.getPlagaCultivoList()){
                n.borraPlagaCultivo(pc.getPlagaCultivoPK());
            }
        }
        
        if(!c.getVenenoCultivoList().isEmpty()){
            for(VenenoCultivo vc:c.getVenenoCultivoList()){
                n.borraVenenoCultivo(vc.getVenenoCultivoPK());
            }
        }
            
            
        
        if(n.borrarCultivo(cultivo)){
            request.getSession().setAttribute("user", user);
            response.sendRedirect("vista_usuario_normal.jsp");
        }else{
            
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
