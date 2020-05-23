<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <%
        String user = (String)request.getSession().getAttribute("user");
        if(user!=null){
            request.getSession().setAttribute("user", user);
            response.sendRedirect("vista_usuario_normal.jsp");
        }
    %>
    
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link href="css/estilo_login.css" rel="stylesheet" type="text/css">
        <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
    </head>
    <body>
        <div class="contenido">
            <form action="leer_formulario_login.do" method="POST" id="form-iniciar-sesion">
                <h2 style="font-weight: bolder;">Iniciar sesión</h2>
                <img src="./imagenes/treemap.jpeg" id="logo">
                <input type="text" placeholder="Ingresa Usuario" name="user" id="nombre" value="romariojaimes@gmail.com" required/>
                <br>
                <br>
                <input type="password" placeholder="Ingresa Contraseña" name="password" id="Contraseña" value="54321" required/>
                <br>
                <br>
                <button type="submit" class="button-login" id="button-iniciar-sesion">Iniciar Sesión</button>
            </form>
            <br>
            <form action="ir_recuperar_password.do" method="POST" id="form-ir-recuperar-contraseña">
                <button type="submit" class="button-login" id="button-recuperar-contraseña">Olvide mi contraseña</button>
            </form>
            <p class="message">No estas registrado? <a href="registro_usuario.jsp">Crear cuenta</a></p>
        </div>
    </body>
</html>
