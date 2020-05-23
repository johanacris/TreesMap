<%-- 
    Document   : RegistroUsuarioPage
    Created on : 23-nov-2019, 15:12:11
    Author     : Romario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <script type="text/JavaScript" src="js/funcionalidad.js"></script>
        <link href="css/estilo_registro.css" rel="stylesheet" type="text/css">
        <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
    </head>
    <body>
        <div class="contenido">
            <form action="leer_formulario_registro.do" method="POST" id="form-registro-usuario">
                <img src="./imagenes/treemap.jpeg" id="logo">
                <h4>Formulario de registro</h4>
                <input type="text" placeholder="Nombre" name="nombre" required/>
                <br>
                <br>
                <input type="text" placeholder="Apellidos" name="apellido" required/>
                <br>
                <br>
                <input type="text" placeholder="C.C." name="cedula" required/>
                <br>
                <br>
                <input type="email" placeholder="Email" name="email" required/>
                <br>
                <br>
                <input type="password" placeholder="Contraseña" name="password" id="password" required/>
                <br>
                <br>
                <input type="password"  oninput="validarContraseña()" placeholder="Repetir Contraseña" name="password-repetida" id="password-repetida"  required/>
                <br>
                <br>
                <button type="submit" id="button-regitrar">Registrarme</button>
            </form>
            <br>
            <a href="index.jsp" id="link-ir-login">Volver a Login</a>
        </div>
        
    </body>
</html>
