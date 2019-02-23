<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Login
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/signup/"/>">Registrieren</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="main">
        <div class="container">
            <h1>Login</h1>
            <form action="j_security_check" method="post" class="stacked">
                <div class="column">
                    <%-- Eingabefelder --%>
                    <div class="form-group">
                        <label for="j_username">Benutzername:<span class="required">*</span></label>
                        <input type="text" class="form-control" name="j_username" placeholder="Benutzername">
                    </div>

                    <div class="form-group">
                        <label for="j_password">Passwort:<span class="required">*</span></label>
                        <input type="password" class="form-control" name="j_password" placeholder="Passwort">
                    </div>
                    <%-- Button zum Abschicken --%>
                    <input class="btn btn-primary" type="submit" value="Einloggen">
                </div>
            </form>
        </div>
    </jsp:attribute>
</template:base>