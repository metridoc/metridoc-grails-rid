<%@ page import="metridoc.rid.RidLibraryUnit" %>

<div class="control-group fieldcontain required">
    <label class="control-label" for="name">
        <g:message code="ridLibraryUnit.name.label" default="Name"/>
        <span class="required-indicator">*</span>
    </label>
    <div class="controls">
        <g:textField class="userInput" name="name" required="" value="${ridLibraryUnitInstance?.name}"/>
    </div>
</div>