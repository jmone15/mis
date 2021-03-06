<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<content tag="title">View scheduled visits</content>

<content tag="content">
    <section class="content-header">
        <h1>
            View Scheduled Visits
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
                        <form role="form" action="<c:url value="/scheduledVisits/byCycle"/>" method="POST">                                          <div class="row" style="margin-bottom:20px;">
                                <div class="col-md-3">
                                    <select name="cycleId" id="cycleId" class="form-control">
                                        <option value="0">[-- Select Cycle --]</option>
                                        <c:forEach var="cycle" items="${cyclesList}">
                                            <option value="${cycle.id}">Cycle: ${cycle.startDate} - ${cycle.endDate}</option>
                                        </c:forEach>
                                    </select>     
                                </div>
                                <div class="col-md-3">                     
                                    <button type="submit" class="btn btn-default">Select</button>
                                </div>
                            </div>
                        </form>
                        <div class="tab-content">
                            <div class="table-responsive">                                                                             
                                <c:if test="${not empty newVisits}">                                                       
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Visitor/s</th>
                                                <th>Cycle</th>
                                                <th>Doctor</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="visit" items="${newVisits}">
                                                <tr>
                                                    <td><c:out value="${visit.id}"/></td>
                                                    <c:forEach var="visitor" items="${visit.medicalVisitors}">
                                                        <td><c:out value="${visitor.firstName}"/> <c:out value="${visitor.lastName}"/></td>
                                                    </c:forEach>
                                                    <c:forEach var="group" items="${visit.groups}">
                                                        <td><c:out value="${group.name}"/></td>
                                                    </c:forEach>    
                                                    <td><c:out value="${visit.cycle.startDate}"/> - <c:out value="${visit.cycle.endDate}"/></td>
                                                    <td><c:out value="${visit.doctor.firstName}"/> <c:out value="${visit.doctor.lastName}"/></td>                          
                                                    <td><c:out value="${visit.status}"/></td>                                        
                                                    <td>   
                                                        <button type="button" class="btn btn-danger deleteVisit" data-id="${visit.id}"><i class="fa fa-trash"></i></button>                               
                                                    </td>      
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:if> 
                                <c:if test="${empty newVisits}">  
                                    <div class="alert alert-warning">The are no available records to view!</div>
                                </c:if>                  
                            </div>     
                        </div>      
                    </div><!-- /.box-body -->
                </div>
            </div>
        </div>
    </section>
</content>

<content tag="footerScripts">
    <script src="<c:url value='/resources/js/scheduledVisit.js'/>" type="text/javascript"></script>
</content>


