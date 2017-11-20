<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Productos</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <jsp:include page="/WEB-INF/jspf/header.jsp" />
    </head>
    <body>
        <div class="container">
            <jsp:include page="/WEB-INF/jspf/menu.jsp" />

            <!-- formulario de búsqueda -->
            <form method="get" action="catalogo">
                <input type="hidden" name="op" value="buscar" />
                <div class="form-row align-items-center">
                    <div class="col-6">
                        <label class="sr-only" for="producto">Producto</label>
                        <input type="text" name="producto" id="producto" value="${fn:escapeXml(productoBuscado)}" class="form-control form-control-lg mb-2 mb-sm-0" placeholder="Ingrese el nombre del producto a buscar">
                    </div>
                    <div class="col-auto">
                        <label class="sr-only" for="categoria">Categoría</label>
                        <select name="categoria" id="categoria" class="custom-select mb-2 mr-sm-2 mb-sm-0">
                            <option selected>Escoja una categoría</option>
                            <c:forEach items="${categorias}" var="c">
                                <option value="${c.id}" ${c.id == categoriaBuscada?'selected="true"':''}>${c.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-warning">Buscar</button>
                    </div>
                </div>
            </form>
            <!-- END formulario de búsqueda -->


            <jsp:include page="/WEB-INF/jspf/mensajes.jsp" />

            <div class="row">
                <div class="col">
                    <a href="catalogo?op=crear" class="btn btn-success fa fa-plus"> Crear</a>
                </div>
            </div>
            
            <c:if test="${empty productos}">
                No hay productos para mostrar.
            </c:if>            

            <c:if test="${!empty productos}">
                <!-- tabla con productos -->
                <table class="table table-striped">
                    <thead class="thead-inverse">
                        <tr>
                            <th>ID</th>
                            <th>Imagen</th>
                            <th>Producto</th>
                            <th>Precio</th>
                            <th>Categoría</th>
                            <th>Fecha Creación</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${productos}" var="p">
                            <tr>
                                <th>${p.id}</th>
                                <td>
                                    <img src="${p.imagen}" alt="${p.nombre}" style="height: 100px; width: auto;" />
                                </td>
                                <td>${p.nombre}</td>
                                <td>
                                    $<fmt:formatNumber value="${p.precio}" />
                                </td>
                                <td>${p.categoria.nombre}</td>
                                <td>
                                    <fmt:formatDate value="${p.fechaCreacion.time}" pattern="dd MMMM yyyy HH:mm'hrs'" />
                                </td>
                                <td>
                                    <!-- agregar al carro -->
                                    <form method="post" action="carrito" class="form-inline">
                                        <div class="form-group">
                                            <input name="cantidad" value="1" type="number" min="1" max="10" />
                                            <input type="hidden" name="productoId" value="${p.id}" />
                                        </div>
                                        <button type="submit" class="btn btn-primary">Add</button>
                                    </form>
                                    <!-- eliminar form> -->
                                    <form method="get" action="catalogo">
                                        <input type="hidden" name="id" value="${p.id}" />
                                        <input type="hidden" name="op" value="eliminar" />
                                        <button type="submit" class="btn btn-danger">Eliminar</button>
                                    </form>
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