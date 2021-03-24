<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>The Burn Book</title>
<jsp:include page="head.jsp" />
</head>
<body>
	<jsp:include page="nav.jsp" />
	<div class="container">
		<c:choose>
			<%-- USER LOGGED IN --%>
			<c:when test="${user.role=='user' }">
			
			TEST -- ${addBurnCommentStudentID}

				<h3 style="color: #b71c1c;">DEBUG: This displays when USER is
					logged in</h3>

				<div class="row">
					<c:forEach var="student" items="${students}">

						<div class="card" style="width: 18rem;">
							<img src="${student.imageUrl}" class="card-img-top" alt="...">
							<div class="card-body">
								<h5 class="card-title">${student.firstName}</h5>
								 <c:forEach var="comment" items="${student.burnBookCommentsAboutMe}">
								
									 <div class="card"> 
										${comment.content}
									</div> 
								</c:forEach> 
								<form action="addBurnComment.do" method="POST">
									<input type="text" hidden="true" name="userIdString" value="${student.id}">
									<input type="submit" value="Add Burn Entry">
								</form>
								
								
								<c:if test="${addBurnCommentStudentID == student.id}">
								Add comment here
								</c:if>
			
							</div>
						</div>

					</c:forEach>
				</div>

			</c:when>
			<%--------------------%>


			<%-- ADMIN LOGGED IN: --%>
			<c:when test="${user.role=='admin' }">

				<h3 style="color: #b71c1c;">DEBUG: This displays when ADMIN is
					logged in</h3>

			</c:when>
			<%----------------------%>


			<%-- NO ONE LOGGED IN: --%>
			<c:otherwise>

				<h3 style="color: #b71c1c;">DEBUG: This displays when NO ONE is
					logged in</h3>

			</c:otherwise>
			<%-----------------------%>

		</c:choose>
	</div>
	<jsp:include page="foot.jsp" />
</body>
</html>
