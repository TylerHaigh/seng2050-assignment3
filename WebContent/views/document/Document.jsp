<%@page import="rgms.infrastructure.Session"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h2>Discussion Thread: <a href="${pageContext.request.contextPath}/group/discussion?threadId=${thread.id}">${thread.threadName}</a></h2>
<h3>File Name: ${ title }</h3>

<div class="list-group">
  <c:forEach var="document" items="${documents}">
    <a class="list-group-item" href="${pageContext.request.contextPath}/document/downloadDocument?documentId=${document.id}">Download: Version ${document.versionNumber} - Uploaded on ${document.uploadDate}</a>
  </c:forEach> 
</div>

<h3>Upload New Version</h3>
<form action="${pageContext.request.contextPath}/document/uploaddocument/?threadId=${thread.id}&groupId=${thread.groupId}&versionNumber=${documents.get(0).versionNumber + 1}" enctype="multipart/form-data" method="post">
  <p>
    <input type="file" name="document" id="document" />
  </p>
  <div class="form-actions">
    <button type="submit" class="btn btn-primary">Upload</button>
  </div>  
</form>

<c:if test="${ userSession.user.id == thread.group.coordinatorId }">
	<h3>Access Summary for Version ${documents.get(0).versionNumber}</h3>
	
	<table class="table table-bordered table-hover">
	  <tr><th>User</th><th>Date Accessed</th></tr>
	  <c:forEach var="ar" items="${accessRecords}" >
	    <tr><td>${ar.user.userName } </td><td> ${ar.dateAccessed }</td> </tr> 
	  </c:forEach>
	</table>
</c:if>

