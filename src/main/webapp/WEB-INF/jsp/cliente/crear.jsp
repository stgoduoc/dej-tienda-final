<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Creación y Edición de Categorías</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <jsp:include page="/WEB-INF/jspf/header.jsp" />
    </head>
    <body>
        <div class="container">
            <jsp:include page="/WEB-INF/jspf/menu.jsp" />
            <jsp:include page="/WEB-INF/jspf/mensajes.jsp" />
            <div class="row">
                <div class="col col-lg-6">
                    <h1>Cree y Edite Categorías</h1>
                    <form method="post" action="categorias">
                        <c:if test="${!empty categoria}">
                            <div class="form-group">
                                <label for="id">ID</label>
                                <input value="${categoria.id}" type="number" class="form-control" id="id" name="id" readonly="readonly" aria-describedby="id-help">
                                <small id="id-help" class="form-text text-muted">El ID de la categoría que se autogenera, solo se cargará cuando se edita una categoría de manera informativa</small>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label for="categoria">Categoría (*)</label>
                            <input value="${!empty categoria?categoria.nombre:''}" type="text" class="form-control" id="categoria" name="categoria" placeholder="Ingrese el nombre de su categoría" aria-describedby="categoria-help">
                            <small id="categoria-help" class="form-text text-muted">Ejemplo: Ropa Hombre</small>
                        </div>
                        <div class="form-group">
                            <label for="descripcion">Descripción</label>
                            <textarea name="descripcion" id="descripcion" class="form-control" aria-describedby="descripcion-help">${!empty categoria?categoria.descripcion:''}</textarea>
                            <small id="descripcion-help" class="form-text text-muted">Descripción larga de la categoría, es opcional.</small>
                        </div>

                        <button type="submit" class="btn btn-primary">Guardar</button>
                    </form>
                </div>
            </div><!-- end col-->
        </div><!-- end row-->

        <jsp:include page="/WEB-INF/jspf/footer.jsp" />
    </body>
</html>