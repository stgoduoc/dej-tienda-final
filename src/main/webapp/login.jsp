<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
    <head>
        <title>Ingreso al Sistema de Ventas</title>
        <jsp:include page="/WEB-INF/jspf/header.jsp" />
    </head>

    <body>

        <div class="container">

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

            <form class="form-signin" method="post" action="login">
                <h2 class="form-signin-heading">Sistema de Ventas</h2>
                <label for="usuario" class="sr-only">Usuario</label>
                <input type="text" id="inputUsuario" name="inputUsuario" class="form-control" placeholder="Usuario" required autofocus>
                <label for="inputPassword" class="sr-only">Password</label>
                <input type="password" id="inputPassword" name="inputPassword" class="form-control" placeholder="Password" required>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Ingresar</button>
                <br />
                <jsp:include page="vendedor?op=include" />
                <c:if test="${empty vendedores}">
                    <a class="btn btn-warning" href="setup">Instalar</a>
                </c:if>
            </form>

        </div> <!-- /container -->

        <style type="text/css">
            body {
                padding-top: 40px;
                padding-bottom: 40px;
                background-color: #eee;
            }

            .form-signin {
                max-width: 330px;
                padding: 15px;
                margin: 0 auto;
            }
            .form-signin .form-signin-heading,
            .form-signin .checkbox {
                margin-bottom: 10px;
            }
            .form-signin .checkbox {
                font-weight: 400;
            }
            .form-signin .form-control {
                position: relative;
                box-sizing: border-box;
                height: auto;
                padding: 10px;
                font-size: 16px;
            }
            .form-signin .form-control:focus {
                z-index: 2;
            }
            .form-signin input[type="email"] {
                margin-bottom: -1px;
                border-bottom-right-radius: 0;
                border-bottom-left-radius: 0;
            }
            .form-signin input[type="password"] {
                margin-bottom: 10px;
                border-top-left-radius: 0;
                border-top-right-radius: 0;
            }
        </style>
    </body>
</html>

