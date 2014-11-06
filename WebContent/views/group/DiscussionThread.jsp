<%@page import="rgms.infrastructure.Session"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h2>Thread: ${thread.threadName}</h2>

<c:if test="${ empty thread.posts }">
    <p>
        No comments yet...
    </p>
</c:if>
<c:forEach var="post" items="${ thread.posts }" >
    <h4>
        <c:out value="${ post.userId }"></c:out>
    </h4>
    <p><c:out value="${ post.message }"></c:out>
</c:forEach>

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
