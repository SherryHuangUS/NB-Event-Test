<%@ page import="nbtest.Event" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'event.label', default: 'Event')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
    <style>
        .addPadding {
            padding: 15px;
        }
        .changeFont {
            font-size: 16px;
            font-weight: bold;
        }
    </style>
</head>

<body>
    <div class="addPadding changeFont">
        Total Events:
    </div>
    <div class="addPadding">
        <g:each in="${events}" var="event">
            <div>
                ${event.date}
                ${event.user?.userName}
                ${event.type?.value()}
                ${event.message}
                ${event.otherUser?.userName}
            </div>
        </g:each>
    </div>

    <div class="addPadding changeFont">
        Total Users:
    </div>
    <div class="addPadding">
        <g:each in="${users}" var="user">
            <div>
                ${user.userName}
            </div>
        </g:each>
    </div>

    <div class="addPadding changeFont">
        Example URLs:
    </div>
    <div class="addPadding">
        /event
    </div>
    <div class="addPadding">
        /events?from=2014-02-28T13:00:00Z&to=2014-02-28T14:00:00Z
    </div>
    <div class="addPadding">
        /summary?from=2014-02-28T13:00:00Z&to=2014-02-28T14:00:00Z&by=hour
    </div>

</body>
</html>
