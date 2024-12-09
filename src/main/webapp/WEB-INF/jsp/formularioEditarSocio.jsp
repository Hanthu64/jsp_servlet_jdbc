<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h2>Modifique los datos del socio dado:</h2>
<form method="post" action="EditarSociosServlet">
    <!-- Vamos a dejar el código guardado aquí para poder pasarlo al doPost. CUIDADO: La variable se llama socioId, no codigo-->
    <input type="hidden" name="codigo" value="${socio.socioId}" />

    <!-- Utilizando el símbolo del dolar y las llaves, podemos pasar los valores como valores predeterminados de los inputs -->
    Nombre <input type="text" name="nombre" value="${socio.nombre}"/></br>
    Estatura <input type="text" name="estatura" value="${socio.estatura}"/></br>
    Edad <input type="text" name="edad" value="${socio.edad}"/></br>
    Localidad <input type="text" name="localidad" value="${socio.localidad}"/></br>
    <input type="submit" value="Aceptar">
</form>

<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
<div style="color: red"><%=error%></div>
<%
    }
%>
</body>
</html>