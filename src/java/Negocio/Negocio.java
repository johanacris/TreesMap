/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import DAO.CodigoRecuperacionDAO;
import DAO.CultivoDAO;
import DAO.FertilizanteCultivoDAO;
import DAO.FertilizanteDAO;
import DAO.PlagaCultivoDAO;
import DAO.PlagaDAO;
import DAO.UsuarioDAO;
import DAO.VenenoCultivoDAO;
import DAO.VenenoDAO;
import Modelo.CodigoRecuperacion;
import Modelo.Cultivo;
import Modelo.Fertilizante;
import Modelo.FertilizanteCultivo;
import Modelo.FertilizanteCultivoPK;
import Modelo.Plaga;
import Modelo.PlagaCultivo;
import Modelo.PlagaCultivoPK;
import Modelo.Usuario;
import Modelo.Veneno;
import Modelo.VenenoCultivo;
import Modelo.VenenoCultivoPK;
import Util.ServicioEmail;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Romario
 */
public class Negocio {
    
    CultivoDAO cultivoDAO;
    UsuarioDAO usuarioDAO;
    FertilizanteDAO fertilizanteDAO;
    FertilizanteCultivoDAO fertilizanteCultivoDAO;
    VenenoDAO venenoDAO;
    VenenoCultivoDAO venenoCultivoDAO;
    PlagaDAO plagaDAO;
    PlagaCultivoDAO plagaCultivoDAO;
    CodigoRecuperacionDAO codigoRecuperacionDAO;
    
    public Negocio(){
        cultivoDAO = new CultivoDAO();
        usuarioDAO = new UsuarioDAO();
        fertilizanteCultivoDAO = new FertilizanteCultivoDAO();
        fertilizanteDAO = new FertilizanteDAO();
        venenoCultivoDAO = new VenenoCultivoDAO();
        venenoDAO = new VenenoDAO();
        plagaDAO= new  PlagaDAO();
        plagaCultivoDAO = new PlagaCultivoDAO();
        codigoRecuperacionDAO = new CodigoRecuperacionDAO();
    }
    
    public boolean insertarUsuario(Usuario usuario){
         try{
            usuarioDAO.create(usuario);
            System.out.println("true");
            return true;
            
        }catch(Exception e){
            System.out.println("false");
            return false;
        }
    }
    
    public boolean actualizarContraseñaUsuario(String email, String contraseña){
        try{
            Usuario usuarioExistente = getUsuario(email);
            usuarioExistente.setPass(contraseña);
            usuarioDAO.edit(usuarioExistente);
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    public Usuario getUsuario(String usuario){
        return usuarioDAO.findUsuario(usuario);
    }
    
    public boolean insertarCodigo(CodigoRecuperacion codigoRecuperacion){
        try{
            codigoRecuperacionDAO.create(codigoRecuperacion);
            System.out.println("true");
            return true;
            
        }catch(Exception e){
            System.out.println("false");
            return false;
        }
    }
    
    public boolean actualizarCodigo(String codigo){
        try{
            CodigoRecuperacion codigoExistente = getCodigoRecuperacion(codigo);
            codigoExistente.setUsado(true);
            codigoRecuperacionDAO.edit(codigoExistente);
        }catch(Exception e){
            return false;
        }
        return true;
    }
      
    
    public CodigoRecuperacion getCodigoRecuperacion(String codigo){
        return codigoRecuperacionDAO.findCodigoRecuperacion(codigo);
    }
    
    public String generarCodigoRecuperacion(){
        String codigo = "";
        Random rnd = new Random();
        char [] letras = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        for (int i = 0; i < 7; i++){
            if(rnd.nextInt(2)==1){
                codigo += rnd.nextInt(10);
            }else{
                codigo += letras[rnd.nextInt(26)];
            }
        }
        return codigo;
    }
    
    public boolean codigoValido(String codigo, String email){
        boolean isValido = false;
        CodigoRecuperacion codigoRecuperacion = this.getCodigoRecuperacion(codigo);
        if(codigoRecuperacion!=null){
            LocalDate fechaCodigo = codigoRecuperacion.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalTime horaCodigo = codigoRecuperacion.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            LocalDate fechaActual = new Date(System.currentTimeMillis()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalTime horaActual = new Date(System.currentTimeMillis()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            if(codigoRecuperacion.getUsuario().equals(email) && !codigoRecuperacion.getUsado() && fechaCodigo.isEqual(fechaActual) && horaActual.isBefore(horaCodigo.plusHours(1))){
                isValido = true;
            }
        }
        return isValido;
    }
    
    public void enviarEmailRecuperacion(String codigo, Usuario usuario){
        String emailUsuarioEmisor="treesmapnoreply@gmail.com";
        String clave="treesmapPass42";
        ServicioEmail servicioEmail=new ServicioEmail(emailUsuarioEmisor, clave);
        String emailReceptor=usuario.getEmail();
        String mensaje = "<span>Su codigo de recuperacion Tree's Map es: <b>"+codigo+"</b>, este codigo es valido durante una hora.</span>";
        servicioEmail.enviarEmail(emailReceptor, "Codigo de recuperacion Tree's Map", mensaje);
    }
    
    public boolean validarUsuario(String user, String password){
        Usuario usuario = usuarioDAO.findUsuario(user);
        if(usuario!=null){
            if(usuario.getPass().equals(password)){
                return true;
            }
        }
        return false;
    }
    
    public String getTipoUsuario(String user){
        return usuarioDAO.findUsuario(user).getTipo();
    }
    
    public boolean insertarCultivo(Cultivo cultivo) {
        try{
            cultivoDAO.create(cultivo);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public boolean borrarCultivo(int id){
        try{
            cultivoDAO.destroy(id);
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    public Cultivo getCultivo(int id){
        return cultivoDAO.findCultivo(id);
    }
    
    public boolean insertarFertilizante(Fertilizante fertilizante){
         try{
            fertilizanteDAO.create(fertilizante);
            System.out.println("true");
            return true;
            
        }catch(Exception e){
             System.err.println(e.getMessage());
            System.out.println("false");
            return false;
        }
    }
    
    public boolean insertarFertilizanteCultivo(FertilizanteCultivo fertilizanteCultivo){
         try{
            fertilizanteCultivoDAO.create(fertilizanteCultivo);
            System.out.println("true");
            return true;
            
        }catch(Exception e){
            System.out.println("false");
            return false;
        }
    }
    
    public boolean borraFertilizanteCultivo(FertilizanteCultivoPK fcpk){
        try{
            fertilizanteCultivoDAO.destroy(fcpk);
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    public boolean borraPlagaCultivo(PlagaCultivoPK cultivoPK){
        try{
            plagaCultivoDAO.destroy(cultivoPK);
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    public boolean borraVenenoCultivo(VenenoCultivoPK cultivoPK){
        try{
            venenoCultivoDAO.destroy(cultivoPK);
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    public boolean actualizarCultivo(Cultivo cultivo, String cultivoId){
        try{
            Cultivo cultivoExistente = getCultivo(Integer.parseInt(cultivoId));
            cultivoExistente.setClase(cultivo.getClase());
            cultivoExistente.setDivision(cultivo.getDivision());
            cultivoExistente.setEspecie(cultivo.getEspecie());
            cultivoExistente.setFamilia(cultivo.getFamilia());
            cultivoExistente.setFechaSembtado(cultivo.getFechaSembtado());
            cultivoExistente.setGenero(cultivo.getGenero());
            cultivoExistente.setNombreCultivo(cultivo.getNombreCultivo());
            cultivoExistente.setOrden(cultivo.getOrden());
            cultivoExistente.setLatitud(cultivo.getLatitud());
            cultivoExistente.setLongitud(cultivo.getLongitud());
            cultivoExistente.setFechaSembtado(cultivo.getFechaSembtado());
            cultivoDAO.edit(cultivoExistente);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    public boolean insertarVeneno(Veneno veneno) {
        try{
            venenoDAO.create(veneno);
            System.out.println("true");
            return true;
            
        }catch(Exception e){
             System.err.println(e.getMessage());
            System.out.println("false");
            return false;
        }
    }
    
    public boolean insertarVenenoCultivo(VenenoCultivo venenoCultivo){
         try{
            venenoCultivoDAO.create(venenoCultivo);
            System.out.println("true");
            return true;
            
        }catch(Exception e){
            System.out.println("false");
            return false;
        }
    }
    
    public boolean insertarPlaga(Plaga plaga) {
        try{
            plagaDAO.create(plaga);
            System.out.println("true");
            return true;
            
        }catch(Exception e){
             System.err.println(e.getMessage());
            System.out.println("false");
            return false;
        }
    }
    
    public boolean insertarPlagaCultivo(PlagaCultivo plagaCultivo){
         try{
            plagaCultivoDAO.create(plagaCultivo);
            System.out.println("true");
            return true;
            
        }catch(Exception e){
            System.out.println("false");
            return false;
        }
    }
}
