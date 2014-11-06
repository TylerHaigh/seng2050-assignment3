<%@page import="rgms.infrastructure.Session"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h2>Discussion Thread: ${thread.threadName}</h2>
<c:if test="${ !empty documents }">
    <c:set var="document" value="${ documents.get(0) }" />
    <p>
        Document: ${ document.documentName }<br />
        Version: ${ document.versionNumber }
    </p>
</c:if>
<c:if test="${ empty thread.posts }">
    <p>
        No comments yet...
    </p>
</c:if>
<c:forEach var="post" items="${ thread.posts }" >
    <div class="row post">
        <div class="col-md-12">
            <img src="${ pageContext.request.contextPath }/Uploads/images/${ post.user.imageReference }" alt="${ post.user.fullName } Profile Image" data-src="holder.js/100x100/sky/text:${post.user.fullName}" class="pull-left profile-image" />
            <h4>
                <c:out value="${ post.user.fullName }"></c:out>
            </h4>
            <p><c:out value="${ post.message }"></c:out>   
        </div>
    </div>
</c:forEach>

<br />
<div class="row">
    <form action="${pageContext.request.contextPath}/group/createpost/?threadId=${thread.id}" method="post" class="col-md-12" onsubmit="return validateComment()">
        <div class="form-group">
          <label for="comment">Make A Comment: </label><br />
          <textarea class="col-md-12" id="comment" name="comment" style="height: 100px;"></textarea>
        </div>

        <button class="btn btn-default" type="submit">Submit Comment</button>
    </form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/formvalidation.js"/></script>
