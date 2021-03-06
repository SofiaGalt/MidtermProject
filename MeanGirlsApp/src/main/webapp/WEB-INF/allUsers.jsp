<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Users</title>
<jsp:include page="head.jsp" />

</head>
<body>
	<jsp:include page="nav.jsp" />
		<c:out value="${loggedInUser }" />

		<c:choose>
			<c:when test="${user.role=='admin' }">

				<c:choose>

					<c:when test="${empty users}">
						<h3>No users were found.</h3>
					</c:when>

					<c:otherwise>


						<h3>Users</h3>
						<table>
							<thead>
								<tr>
									<th>ID</th>
									<th>Name</th>
								</tr>

							</thead>

							<tbody>

							</tbody>
							<c:forEach var="users" items="${users}">

								<tr>
									<td>${users.id}</td>
									<td><a href="getUser.do?id=${users.id}">${users.firstName}
											${users.lastName}</a></td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</c:when>

			<c:otherwise>
				<h3 style="color: #ee4498; text-align: center;" >Only admins are allow to see this page</h3>
				   <center><img src="resources/images/gohere.png" alt="" height="700" width="900"> </center>
			</c:otherwise>

		</c:choose>
	<jsp:include page="foot.jsp" />
</body>
</html>