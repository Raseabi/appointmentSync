<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<p>Hello ${user.systemId}!</p>
<h2>Appointments</h2>
<table>
    <thead>
        <tr>
            <th>N<sub>o</sub></th>
            <th>Names</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Gender</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${appointments}" var="appointment" varStatus="num">
            <tr>
                <td>${num.count}</td>
                <td>${appointment.names}</td>
                <td>${appointment.startDate}</td>
                <td>${appointment.endDate}</td>
                <td>${appointment.gender}</td>
            <tr>
        </c:forEach>
    </tbody>

</table>


<%@ include file="/WEB-INF/template/footer.jsp"%>