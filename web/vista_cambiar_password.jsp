<%-- 
    Document   : vista_cambiar_contraseña
    Created on : Apr 27, 2020, 9:49:17 AM
    Author     : Romario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <%
        String email = (String)request.getSession().getAttribute("email");
        request.getSession().setAttribute("email", email);
    %>
    
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <script type="text/JavaScript" src="js/funcionalidad.js"></script>
        <link href="css/estilo_cambio_password.css" rel="stylesheet" type="text/css">
        <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
    </head>
    <body>
        <div class="contenido">
            <form action="leer_formulario_cambio_password.do" method="POST" id="form-cambio-password">
                <img src="./imagenes/treemap.jpeg" id="logo">
                <h4 style="font-weight: bolder;">Cambiar contraseña</h4>
                <h4 style="font-weight: bolder;"><%=email%></h4>
                <input type="password" placeholder="Nueva contraseña" name="password" id="password" required/>
                <br>
                <input type="password"  oninput="validarContraseña()" placeholder="Repetir nueva contraseña" name="password-repetida" id="password-repetida"  required/>
                <button type="submit" class="button-cambiar">Cambiar contraseña</button>
            </form>
        </div>
    </body>
</html>
