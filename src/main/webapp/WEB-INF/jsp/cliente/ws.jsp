<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="application/json" pageEncoding="UTF-8"%>
{
    "id": ${cliente.id}
    , "rut": ${cliente.rut}
    , "dv": "${cliente.dv}"
    , "nombre": "${cliente.nombre}"
    , "fechaNacimiento": "<fmt:formatDate value="${cliente.fechaNacimiento.time}" pattern="yyyy-MM-dd" />"
    , "comuna": "${cliente.comuna}"
    , "direccion": "${cliente.direccion}"
}
                       
                                