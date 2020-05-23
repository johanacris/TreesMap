<%-- 
    Document   : vista_detalle_cultivo
    Created on : 26-nov-2019, 19:33:05
    Author     : Romario
--%>

<%@page import="Negocio.Negocio"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.ZoneId"%>
<%@page import="Modelo.PlagaCultivo"%>
<%@page import="Modelo.VenenoCultivo"%>
<%@page import="Modelo.FertilizanteCultivo"%>
<%@page import="Modelo.Fertilizante"%>
<%@page import="Modelo.Usuario"%>
<%@page import="Modelo.Cultivo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<%
    String user = (String)request.getSession().getAttribute("user");
    String culti = (String)request.getSession().getAttribute("cultivo");
    request.getSession().setAttribute("user", user);
    request.getSession().setAttribute("cultivo", culti);
    Negocio n = new Negocio();
    Cultivo cultivo = n.getCultivo(Integer.parseInt(culti));
    String lat=cultivo.getLatitud();
    String lon=cultivo.getLongitud();
    String nombreCultivo = cultivo.getNombreCultivo();
    SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat("dd/MM/yyyy");
    String fechaSembrado = DATE_FORMAT2.format(cultivo.getFechaSembtado());
    String fertizanteTabla = "<table class='tabla' id='tabla-fertilizante'><tr><th>Tipo</th><th>Nombre</th><th>Fecha Aplicacion</th><th>Notas</th></tr>";
    if(cultivo.getFertilizanteCultivoList().isEmpty()){
        fertizanteTabla="";
    }else{
        for(FertilizanteCultivo fc: cultivo.getFertilizanteCultivoList()){
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
            String date = DATE_FORMAT.format(fc.getFechaAplicacion());
        fertizanteTabla += "<tr><td>"+fc.getFertilizante().getTipoFertilizante()+"</td>"
                +"<td>"+fc.getFertilizante().getNombreFertilizante()+"</td>"
                +"<td>"+date+"</td>"
                + "<td>"+fc.getAnotacion()+"</td></tr>";
        }
        fertizanteTabla+= "</table>";
    }
    
    
    String venenoTabla = "<table class='tabla' id='tabla-veneno'><tr><th>Tipo</th><th>Nombre</th><th>Fecha Aplicacion</th><th>Notas</th></tr>";
    if(cultivo.getVenenoCultivoList().isEmpty()){
        venenoTabla="";
    }else{
        for(VenenoCultivo vc: cultivo.getVenenoCultivoList()){
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
            String date = DATE_FORMAT.format(vc.getFechaAplicacion());
        venenoTabla += "<tr><td>"+vc.getVeneno().getTipoVeneno()+"</td>"
                +"<td>"+vc.getVeneno().getNombreVeneno()+"</td>"
                +"<td>"+date+"</td>"
                + "<td>"+vc.getAnotacion()+"</td></tr>";
        }
        venenoTabla+= "</table>";
    }
    
    String plagaTabla = "<table class='tabla' id='tabla-veneno'><tr><th>Tipo</th><th>Nombre</th><th>Fecha Aplicacion</th><th>Notas</th></tr>";
    if(cultivo.getPlagaCultivoList().isEmpty()){
        plagaTabla="";
    }else{
        for(PlagaCultivo pc: cultivo.getPlagaCultivoList()){
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
            String date = DATE_FORMAT.format(pc.getFechaAplicacion());
            plagaTabla += "<tr><td>"+pc.getPlaga().getTipoPlaga()+"</td>"
                    +"<td>"+pc.getPlaga().getNombrePlaga()+"</td>"
                    +"<td>"+date+"</td>"
                    + "<td>"+pc.getAnotacion()+"</td></tr>";
        }
        plagaTabla+= "</table>";
    }
            
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TODO supply a title</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <!--my css-->
        <link href="css/estilo_pagina_detalle_cultivo.css" rel="stylesheet" type="text/css">
        <!--my javascirpt-->
        <script type="text/JavaScript" src="js/funcionalidad.js"></script>
        <!--google icons-->
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <!--awesome icons-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.5.0/css/all.css' integrity='sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU' crossorigin='anonymous'>
        <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCj_y-zNrFjyiZdhyIBLW8jbHORlOApOGw&callback=initMap" type="text/javascript"></script>
        <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
    </head>
    
    
    <body>
        <nav class="navbar">
            <ul class="navbar-nav">
                <li>
                    <img src="./imagenes/just-logo.png" id="logo">
                </li>
                
                <li>
                    <a href="vista_perfil_usuario_normal.jsp" class="nav-link">
                        <svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="user-circle" class="svg-inline--fa fa-user-circle fa-w-16" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 496 512"><path fill="currentColor" d="M248 8C111 8 0 119 0 256s111 248 248 248 248-111 248-248S385 8 248 8zm0 96c48.6 0 88 39.4 88 88s-39.4 88-88 88-88-39.4-88-88 39.4-88 88-88zm0 344c-58.7 0-111.3-26.6-146.5-68.2 18.8-35.4 55.6-59.8 98.5-59.8 2.4 0 4.8.4 7.1 1.1 13 4.2 26.6 6.9 40.9 6.9 14.3 0 28-2.7 40.9-6.9 2.3-.7 4.7-1.1 7.1-1.1 42.9 0 79.7 24.4 98.5 59.8C359.3 421.4 306.7 448 248 448z"></path></svg>
                        <span class="link-text">Perfil</span>
                    </a>
                </li>

                <li>
                    <a href="vista_usuario_normal.jsp" class="nav-link">
                        <svg aria-hidden="true" focusable="false" data-prefix="fab" data-icon="pagelines" class="svg-inline--fa fa-pagelines fa-w-12" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 512"><path fill="currentColor" d="M384 312.7c-55.1 136.7-187.1 54-187.1 54-40.5 81.8-107.4 134.4-184.6 134.7-16.1 0-16.6-24.4 0-24.4 64.4-.3 120.5-42.7 157.2-110.1-41.1 15.9-118.6 27.9-161.6-82.2 109-44.9 159.1 11.2 178.3 45.5 9.9-24.4 17-50.9 21.6-79.7 0 0-139.7 21.9-149.5-98.1 119.1-47.9 152.6 76.7 152.6 76.7 1.6-16.7 3.3-52.6 3.3-53.4 0 0-106.3-73.7-38.1-165.2 124.6 43 61.4 162.4 61.4 162.4.5 1.6.5 23.8 0 33.4 0 0 45.2-89 136.4-57.5-4.2 134-141.9 106.4-141.9 106.4-4.4 27.4-11.2 53.4-20 77.5 0 0 83-91.8 172-20z"></path></svg>
                        <span class="link-text">Cultivos</span>
                    </a>
                </li>

                <li id="salir-button">
                    <a href="salir.do" class="nav-link">
                        <svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="sign-out-alt" class="svg-inline--fa fa-sign-out-alt fa-w-16" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path fill="currentColor" d="M497 273L329 441c-15 15-41 4.5-41-17v-96H152c-13.3 0-24-10.7-24-24v-96c0-13.3 10.7-24 24-24h136V88c0-21.4 25.9-32 41-17l168 168c9.3 9.4 9.3 24.6 0 34zM192 436v-40c0-6.6-5.4-12-12-12H96c-17.7 0-32-14.3-32-32V160c0-17.7 14.3-32 32-32h84c6.6 0 12-5.4 12-12V76c0-6.6-5.4-12-12-12H96c-53 0-96 43-96 96v192c0 53 43 96 96 96h84c6.6 0 12-5.4 12-12z"></path></svg>
                        <span class="link-text">Salir</span>
                    </a>
                </li>
            </ul>
        </nav>
        
        <div class="contenido">
            
            <div class="floating-button-placeholder">
                
                <a href="vista_usuario_normal.jsp" id="button-back" class="floating-button">
                    <i class="fas fa-arrow-left"></i>
                </a>
                
                <a id="button-menu" class="floating-button">
                    <i class="fas fa-bars"></i>
                </a>
                
                <div class="floating-options">
                    
                    <div class="floating-option">
                        <a href="vista_editar_cultivo.jsp" id="button-editar" class="floating-button">
                            <i class="fas fa-pen"></i>
                        </a>
                        <span class="fab-label">Editar</span>
                    </div>

                    <div class="floating-option">
                        <a href="vista_registrar_fertilizante.jsp" id="button-fertilizante" class="floating-button">
                            <i class="fas fa-seedling"></i>
                        </a>
                        <span class="fab-label">Registrar Fertilizante</span>
                    </div>

                    <div class="floating-option">
                        <a href="vista_registrar_veneno.jsp" id="button-plaga" class="floating-button">
                            <i class="fas fa-skull-crossbones"></i>
                        </a>
                        <span class="fab-label">Registrar Veneno</span>
                    </div>

                    <div class="floating-option">
                        <a href="vista_registrar_plaga.jsp" id="button-plaga" class="floating-button">
                            <i class="fas fa-bug"></i>
                        </a>
                        <span class="fab-label">Registrar Plaga</span>
                    </div>
                    
                </div>
            </div>
           
            <div class="card" id="info-cultivo">
                <div class="">
                    <h3><%=nombreCultivo%> sembrado el <%=fechaSembrado%></h3>
                </div>
                <div class="row">
                    <div class="col">
                        <span><i style="color:rgb(255,170,88)" class="material-icons">fiber_manual_record</i><b>Especie: <%=cultivo.getEspecie()%></b></span>
                        <span><i style="color:rgb(176,255,88);" class="material-icons">fiber_manual_record</i><b>Orden: <%=cultivo.getOrden()%></b></span>
                    </div>
                    <div class="col">
                        <span><i style="color:rgb(255,202,149);" class="material-icons">fiber_manual_record</i><b>Genero: <%=cultivo.getGenero()%></b></span>
                        <span><i style="color:rgb(93,255,88);" class="material-icons">fiber_manual_record</i><b>Clase: <%=cultivo.getClase()%></b></span>
                        
                    </div>
                    <div class="col">
                        <span><i style="color:rgb(255,223,45);" class="material-icons">fiber_manual_record</i><b>Familia: <%=cultivo.getFamilia()%></b></span>
                        <span><i style="color:rgb(88,255,247);" class="material-icons">fiber_manual_record</i><b>Division: <%=cultivo.getDivision()%></b></span>
                    </div>
                </div>
            </div>
             
            <div class="card">
                <button class="accordion">Fertilzantes</button>
                <div id="fertilizante" class="panel">
                    <%=fertizanteTabla%>
                </div>
            </div>
                
            <div class="card">
                <button class="accordion">Plagas</button>
                <div id="palga" class="panel">
                    <%=plagaTabla%>
                </div>
            </div>
                
            <div class="card">  
                <button class="accordion">Venenos</button>
                <div id="veneno" class="panel">
                    <%=venenoTabla%>
                </div>
            </div>
                
            <div class="card">    
                <button class="accordion" onclick="showPosition(<%=lat%>, <%=lon%>)">Geolocalizacion</button>
                <div id="mapholder" class="panel">
                </div>
            </div>
                
            <script>
                var acc = document.getElementsByClassName("accordion");
                var i;

                for (i = 0; i < acc.length; i++) {
                  acc[i].addEventListener("click", function() {
                    this.classList.toggle("active");
                    var panel = this.nextElementSibling;
                    if (panel.style.display === "block") {
                      panel.style.display = "none";
                    } else {
                      panel.style.display = "block";
                    }
                  });
                }
            </script>
        </div>
        
    </body>
</html>
