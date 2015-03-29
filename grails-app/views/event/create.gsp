<%@ page import="nbtest.Event" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'event.label', default: 'Event')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
    <style>
    .addPadding {
        padding: 10px;
    }
    </style>
</head>

<body>
<form method="post">
    <div class="addPadding">
        <input name="date" value="${new Date().format("yyyy-MM-dd'T'hh:mm:ss'Z'")}">
        <label>Date Created</label>
    </div>
    <div class="addPadding">
        <input name="user" value="Sherry">
        <label>User</label>
    </div>
    <div class="addPadding">
        <input name="type" value="comment">
        <label>Event Type</label>
    </div>
    <div class="addPadding">
        <input name="message" value="I'm a software engineer">
        <label>Message</label>
    </div>
    <div class="addPadding">
        <input name="otherUser">
        <label>Other User</label>
    </div>

    <div class="addPadding">
        <g:actionSubmit type="submit" value="POST this event" action="event"/>
    </div>
</form>

</body>
</html>
