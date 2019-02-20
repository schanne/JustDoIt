<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Register</title>
    </head>
    <body>
            <h1>Login</h1>
            <form method="POST">
                <input type="text" name="name" placeholder="Name" value="${name}"/>
                <input type="text" name="password" placeholder="Password" value="${password}"/>
                <input type="checkbox" name="rememberMe" value="true">
                <button type="submit">Login</button>
            </form>

            <c:if test="${!empty fehler}">
                <p>
                    <c:forEach items="${fehler}" var="item">
                        ${item}<br>
                    </c:forEach>
                </p>
            </c:if>
    </body>
</html>

