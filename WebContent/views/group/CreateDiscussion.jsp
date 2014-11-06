<%@page import="rgms.infrastructure.Session"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Create a Discussion</h1>
<div class="row">
  <form method="post" onsubmit="return validateDiscussion();" class="col-md-4" role="form" enctype="multipart/form-data">
    <input type="hidden" name="groupId" value="${groupId}" />

    <div class="form-group">
      <label for="threadName">Thread Name: </label>
      <input type="text" name="threadName" id="threadName" class="form-control" />
    </div>

    <div class="form-group">
      <label for="document">Document (optional):</label>
      <input type="file" name="document" id="document" />
    </div>

    <button class="btn btn-default" type="submit">Create Discussion</button>
  </form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/formvalidation.js"/></script>