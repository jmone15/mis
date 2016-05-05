<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<content tag="title">View all users</content>

<content tag="content">
    <section class="content-header">
        <h1>
            View all users
        </h1>
    </section>
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <div class="box box-info">
                    <div class="box-header with-border">
                        <h3 class="box-title">Scheduled Visits List</h3>
                    </div><!-- /.box-header -->
                    <div class="box-body">
                        <div class="table-responsive">
                            <c:if test="${not empty users}">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Medical Visitor</th>
                                        <th>Username</th>
                                        <th>Password</th>
                                        <th>Role</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="user" items="${users}">
                                        <tr>
                                            <td><c:out value="${user.id}"/></td>
                                            <td><c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/></td>
                                            <td><c:out value="${user.username}"/></td>
                                            <td><c:out value="${user.password}"/></td>
                                            <c:forEach var="role" items="${user.roles}">
                                                <td><c:out value="${role.name}"/></td>
                                            </c:forEach>
                                            <td>
                                                <a href="<c:url value="/users/edit/${user.id}"/>" class="btn btn-info">
                                                    <i class="fa fa-edit"></i>
                                                </a>
                                                <button typ e="button" class="btn btn-danger deleteUser" data-id="${user.id}"><i class="fa fa-trash"></i></button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            </c:if> 
                            <c:if test="${empty users}">  
                            <div class="alert alert-warning">The are no available records to view!</div>
                          </c:if>
                        </div>
                    </div><!-- /.box-body -->
                </div>
            </div>
        </div>
    </section>

</content>

<content tag="footerScripts">
    <script src="<c:url value='/resources/js/users.js'/>" type="text/javascript"></script>
</content>