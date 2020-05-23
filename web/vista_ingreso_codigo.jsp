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
        <link href="css/estilo_ingreso_codigo.css" rel="stylesheet" type="text/css">
        <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
    </head>
    <body>
        <div class="contenido">
            <form action="leer_formulario_ingreso_codigo.do" method="POST" id="form-recuperar-password">
                <img src="./imagenes/treemap.jpeg" id="logo">
                <h4 style="font-weight: bolder;">Ingrese el codigo de recuperacion que se ha enviado a su correo electronico</h4>
                <input placeholder="Ingresa codigo" name="codigo" id="codigo" required/>
                <button type="submit" class="button-recuperar">Aceptar</button>
            </form>
        </div>
    </body>
</html>

