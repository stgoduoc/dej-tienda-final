<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- mensajes --%>
<c:if test="${!empty mensajes}">
    <div class="alert alert-primary" role="alert">
        <ul>
            <c:forEach items="${mensajes}" var="mensaje">
                <li>${mensaje}</li>
            </c:forEach>
        </ul>
    </div>
</c:if>

<%-- errores --%>
<c:if test="${!empty errores}">
    <div class="alert alert-danger" role="alert">
        <ul>
            <c:forEach items="${errores}" var="error">
                <li>${error}</li>
            </c:forEach>
        </ul>
    </div>
</c:if>