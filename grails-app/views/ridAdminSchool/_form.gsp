<%@ page import="metridoc.rid.RidSchool" %>

<div class="control-group fieldcontain required">
    <label class="control-label" for="name">
        <g:message code="ridSchool.name.label" default="Name"/>
        <span class="required-indicator">*</span>
    </label>

    <div class="controls">
        <g:textField class="userInput" name="name" required="" value="${ridSchoolInstance?.name}"/>
    </div>
</div>

<div class="control-group fieldcontain required">
    <label class="control-label" for="inForm">
        <g:message code="ridSchool.inForm.label" default="In Form"/>
        <span class="required-indicator">*</span>
    </label>

    <div class="controls">
        <% def choices = ['NO', 'YES, and no indication needed', 'YES, and indication required'] %>
        <g:select name="inForm" from="${choices}" value="${ridSchoolInstance?.inForm}"
                  keys="${ridSchoolInstance.constraints.inForm.inList}"/>
    </div>
</div>