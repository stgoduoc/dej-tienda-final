<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Comprobante de Compra</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <jsp:include page="/WEB-INF/jspf/header.jsp" />
        <style type="text/css">
            @media print {
                #logo
                , nav.navbar
                , #btn-imprimir
                {
                    display: none;
                }
                
                
            }
        </style>
    </head>
    <body>
        <div class="container">
            <jsp:include page="/WEB-INF/jspf/menu.jsp" />
            <jsp:include page="/WEB-INF/jspf/mensajes.jsp" />

            <c:if test="${empty pedido.lineasPedido}">
                No se ha realizado ninguna compra.
            </c:if>            

            <c:if test="${!empty pedido.lineasPedido}">
                <div id="comprobante">
                    <a id="btn-imprimir" class="btn btn-primary" href="javascript:window.print();">Imprimir</a>
                    <br /><br />
                    <div class="row">
                        <div class="col">
                            <img src="img/logo.png" alt="logo" style="width: 200px;" />
                        </div>
                        <div class="col">
                            Store SpA<br />
                            GIRO: Importadora<br />
                            EMAIL: contacto@store.cl<br />
                            TELÉFONO: 226436699<br />
                        </div>
                        <div class="col">
                            R.U.T.: 76.342.222-3<br />
                            BOLETA ELECTRÓNICA<br />
                            Nº ${pedido.id}<br />
                            S.I.I. - SANTIAGO PONIENTE<br />
                            Fecha Emisión: <fmt:formatDate value="${pedido.fecha.time}" pattern="dd 'de' MMMM YYYY" />
                        </div>
                    </div>

                    <br /><br />
                    <table border="1" style="width: 100%;">
                        <thead>
                            <tr>
                                <td>Código</td>
                                <td>Descripción</td>
                                <td>Cantidad</td>
                                <td>Precio</td>
                                <td>Valor</td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${pedido.lineasPedido}" var="lp">
                                <tr>
                                    <td>-</td>
                                    <td>${lp.producto.nombre}</td>
                                    <td>${lp.cantidad}</td>
                                    <td><fmt:formatNumber value="${lp.precio}" /></td>
                                    <td><fmt:formatNumber value="${lp.subtotal}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="row">
                        <div class="col"></div>
                        <div class="col"></div>
                        <div class="col text-right">
                            <br /><br />
                            <table>
                                <tr>
                                    <td>MONTO NETO $</td>
                                    <td><fmt:formatNumber value="${pedido.total}" /></td>
                                </tr>
                                <tr>
                                    <td>I.V.A. 19% $</td>
                                    <td><fmt:formatNumber value="${pedido.iva}" /></td>
                                </tr>
                                <tr>
                                    <td>IMPUESTO ADICIONAL $</td>
                                    <td>0</td>
                                </tr>
                                <tr>
                                    <td>TOTAL $</td>
                                    <td><fmt:formatNumber value="${pedido.totalConIva}" /></td>
                                </tr> 
                            </table>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>

        <jsp:include page="/WEB-INF/jspf/footer.jsp" />
    </body>
</html>