<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<p>Hello ${user.systemId}!</p>
<h2>Appointments</h2>
<table border="1">
    <thead>
        <tr>
            <th>N<sub>o</sub></th>
            <th>Identifier</th>
            <th>Phone</th>
            <th>Names</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Gender</th>
            <th>Facility</th>
            <th>Location</th>
            <th>Status</th>
            <th>Comments</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${appointments}" var="appointment" varStatus="num">
            <tr>
                <td>${num.count}</td>
                <td>${appointment.identifier}</td>
                <td>${appointment.phone}</td>
                <td>${appointment.names}</td>
                <td>${appointment.startDate}</td>
                <td>${appointment.endDate}</td>
                <td>${appointment.gender}</td>
                <td>${appointment.location}</td>
                <td>${appointment.parentLocation}</td>
                <td>${appointment.status}</td>
                <td>${appointment.comment}</td>
            <tr>
        </c:forEach>
    </tbody>

</table>


<%@ include file="/WEB-INF/template/footer.jsp"%>