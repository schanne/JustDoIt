<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Hier koennten Ihre Aufgaben stehen!
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/logout/"/>">Logout</a>
        </div>
        <div class="menuitem">
            <a href="<c:url value="/changepw/"/>">Passwort ändern</a>
        </div>
        <div class="menuitem">
            <a href="<c:url value="/changemail/"/>">E-Mail-Adresse ändern</a>
        </div>
        <div class="menuitem">
            <a href="<c:url value="/categories/"/>">Kategorien bearbeiten</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="main">
        <p>
            Der Login hat erfolgreich funktioniert! :)
        </p>
    </jsp:attribute>
</template:base>