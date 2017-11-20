<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Pedidos</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <jsp:include page="/WEB-INF/jspf/header.jsp" />
    </head>
    <body>
        <div class="container">
            <jsp:include page="/WEB-INF/jspf/menu.jsp" />

            <jsp:include page="/WEB-INF/jspf/mensajes.jsp" />
            
            <c:if test="${empty pedidos}">
                No hay pedidos para mostrar.
            </c:if>            

            <c:if test="${!empty pedidos}">
                <!-- tabla con pedidos -->
                <table class="table table-striped">
                    <thead class="thead-inverse">
                        <tr>
                            <th>Número</th>
                            <th>Cliente</th>
                            <th>Vendedor</th>
                            <th>Monto</th>                            
                            <th>Fecha</th>                            
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${pedidos}" var="p">
                            <tr>
                                <th>${p.id}</th>                                
                                <td>${p.cliente.nombre}</td>
                                <td>${p.vendedor.nombre}</td>                                
                                <td>
                                    $<fmt:formatNumber value="${p.total}" />
                                </td>
                                <td>
                                    <fmt:formatDate value="${p.fecha.time}" pattern="dd MMMM yyyy HH:mm'hrs'" />
                                </td>                                
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>

        <jsp:include page="/WEB-INF/jspf/footer.jsp" />
    </body>
</html>