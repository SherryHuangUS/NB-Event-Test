<%@ page import="nbtest.Event" %>



<div class="fieldcontain ${hasErrors(bean: eventInstance, field: 'message', 'error')} required">
    <label for="message">
        <g:message code="event.message.label" default="Message"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="message" required="" value="${eventInstance?.message}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: eventInstance, field: 'otherUser', 'error')} required">
    <label for="otherUser">
        <g:message code="event.otherUser.label" default="Other User"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="otherUser" name="otherUser.id" from="${nbtest.User.list()}" optionKey="id" required=""
              value="${eventInstance?.otherUser?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: eventInstance, field: 'type', 'error')} required">
    <label for="type">
        <g:message code="event.type.label" default="Type"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select name="type" from="${nbtest.EventType?.values()}" keys="${nbtest.EventType.values()*.name()}" required=""
              value="${eventInstance?.type?.name()}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: eventInstance, field: 'user', 'error')} required">
    <label for="user">
        <g:message code="event.user.label" default="User"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="user" name="user.id" from="${nbtest.User.list()}" optionKey="id" required=""
              value="${eventInstance?.user?.id}" class="many-to-one"/>

</div>

