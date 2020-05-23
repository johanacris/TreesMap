<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link href="css/estilo_recuperacion_password.css" rel="stylesheet" type="text/css">
        <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
    </head>
    <body>
        <div class="contenido">
            <form action="leer_formulario_recuperar_password.do" method="POST" id="form-recuperar-password">
                <img src="./imagenes/treemap.jpeg" id="logo">
                <h3 style="font-weight: bolder;">Ingrese el email con el que se registro</h3>
                <input type="email" placeholder="Ingresa email" name="email" id="email" value="romariojaimes@gmail.com" required/>
                <button type="submit" class="button-recuperar">Aceptar</button>
            </form>
        </div>
    </body>
</html>

