
function validarContraseña(){
    var password, password2;
    password = document.getElementById('password');
    password2 = document.getElementById('password-repetida');
    password.onchange = password2.onkeyup = passwordMatch;

    function passwordMatch() {
        if(password.value !== password2.value){
            password2.setCustomValidity('Las contraseñas no coinciden');
        }else{
            password2.setCustomValidity('');
        } 
    }
}

function generarMensajeEliminarCultivo(cultivo, nombre){
    var form_div=document.getElementById("overlay");
    var form="<form id='mensaje-eliminar-cultivo' action='eliminar_cultivo.do' method='POST'>";
    form+="<p><b>¿Esta seguro de que desea eliminar este cultivo?<b><p>";
    form+="<input type='text' name='cultivo-a-eliminar' style='display:none;' value='"+cultivo+"' required/>";
    form+="<button type='submit' id='button-eliminar'>Eliminar</button>";
    form+="<button type='button' id='button-cancelar' onclick='mensajeEliminarCultivoOverlayOff()'>Cancelar</button>";
    form+="</form>";
    form_div.innerHTML=form;
    document.getElementById("overlay").style.display = "block";
}

function mensajeEliminarCultivoOverlayOff() {
  document.getElementById("overlay").style.display = "none";
}


function openPage(pageName,elmnt,color) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablink");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].style.backgroundColor = "";
    }
    document.getElementById(pageName).style.display = "block";
    elmnt.style.backgroundColor = color;
}

function showPosition(lat, lon) {
    var latlon = new google.maps.LatLng(lat,lon);
    var mapholder = document.getElementById('mapholder');
    mapholder.style.height = '300px';

    var myOptions = {
        center:latlon,zoom:14,
        mapTypeId:google.maps.MapTypeId.ROADMAP,
        mapTypeControl:false,
        navigationControlOptions:{style:google.maps.NavigationControlStyle.SMALL}
    }

    var map = new google.maps.Map(document.getElementById("mapholder"), myOptions);
    var marker = new google.maps.Marker({position:latlon,map:map,title:"You are here!"});
}

function showError(error) {
    switch(error.code) {
        case error.PERMISSION_DENIED:
            x.innerHTML = "User denied the request for Geolocation."
            break;
        case error.POSITION_UNAVAILABLE:
            x.innerHTML = "Location information is unavailable."
            break;
        case error.TIMEOUT:
            x.innerHTML = "The request to get user location timed out."
            break;
        case error.UNKNOWN_ERROR:
            x.innerHTML = "An unknown error occurred."
            break;
    }
}




