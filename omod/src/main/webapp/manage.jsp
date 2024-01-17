<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<p>Hello ${user.systemId}!</p>
<h2>Appointments</h2>
<table>
    <tr>
        <th>Names</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Gender</th>
    </tr>

    <c:forEach items="${appointments}" var="appointment">
        <tr>
            <td>${appointment.names}</td>
            <td>${appointment.startDate}</td>
            <td>${appointment.endDate}</td>
            <td>${appointment.gender}</td>
        <tr>
    </c:forEach>

</table>


<%@ include file="/WEB-INF/template/footer.jsp"%>