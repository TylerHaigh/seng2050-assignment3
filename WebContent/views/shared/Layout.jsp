<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <title>${title} | RGMS</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/References/Styles.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/References/jquery-ui.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/References/bootstrap/css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/References/bootstrap/css/bootstrap-theme.css" />
        ${styles}
    </head>
    <body>
        <div class="wrapper">
            <div class="header">
                <div class="headerNav">
                    <c:if test="${empty partialViewNavbar}">
                        <c:set var="partialViewNavbar" value="/views/shared/Navigation.jsp" />
                    </c:if>
                    <c:import url="${partialViewNavbar}" />
                </div>
            </div>
            
            <div class="container">
                <c:import url="${partialViewMain}"></c:import>
            </div>

            <div class="footer">
                 <p>Copyright The Aggregates 3.0 (2014)</p>
            </div>
            
        </div>
        <script src="${pageContext.request.contextPath}/References/jquery.js"></script> 
        <script src="${pageContext.request.contextPath}/References/jquery-ui.js"></script>
        <script src="${pageContext.request.contextPath}/References/Scripts.js"></script>
        <script src="${pageContext.request.contextPath}/References/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/References/holder.js" type="text/javascript"></script>
        ${scripts}
    </body>
</html>
