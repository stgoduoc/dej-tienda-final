<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Bienvenido a la Plataforma de Gestión de Productos</title>        
        <jsp:include page="/WEB-INF/jspf/header.jsp" />
    </head>
    <body>
        <div class="container">
            <jsp:include page="/WEB-INF/jspf/menu.jsp" />

            <p>
                Bienvenido a la plataforma de gestión de productos
            </p>
            <img class="img-fluid" src="img/compras.jpg" alt="Sistema de Gestión de Productos" />
        </div>

        <jsp:include page="/WEB-INF/jspf/footer.jsp" />
    </body>
</html>