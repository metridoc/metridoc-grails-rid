<%@ page import="metridoc.rid.RidTransaction" %>
<g:set var="entityName" value="${message(code: 'ridTransaction.label', default: 'RidTransaction')}"/>
%{--<!doctype html>--}%
%{--<html>--}%
%{--<head>--}%
%{--<meta name="layout" content="main">--}%
%{--<g:set var="entityName" value="${message(code: 'ridTransaction.label', default: 'RidTransaction')}" />--}%
%{--<title><g:message code="default.list.label" args="[entityName]" /></title>--}%
%{--</head>--}%
%{--<body>--}%

<md:report>
    <r:external dir="css" file="pagination.css"/>
    <r:external dir="css" file="table.css"/>
    <!--[if !IE]><!-->
    <r:external dir="css" file="floating_table.css"/>
    <!--<![endif]-->

    <div class="md-application-content">
        <tmpl:tabs/>

        <div id="list-ridTransaction" class="content scaffold-list" role="main">
            <h1>
                <g:message code="default.list.label" args="[entityName]"/>
                <g:if test="${ridTransactionAllList.size() > 0}">
                    <g:link action="export" params="${params}">
                        <i id="exportToFile"
                           title="Save the current transaction list as an excel file" class="icon-download-alt"></i>
                    </g:link>
                </g:if>
            </h1>


            <table class="table table-striped table-hover">
                <thead>
                <tr>

                    <g:sortableColumn property="userQuestion"
                                      title="${message(code: 'ridTransaction.userQuestion.label', default: 'User Question')}"/>

                    <g:sortableColumn property="staffPennkey"
                                      title="${message(code: 'ridTransaction.staffPennkey.label', default: 'Staff Pennkey')}"/>

                    <g:sortableColumn property="dateOfConsultation"
                                      title="${message(code: 'ridTransaction.dateOfConsultation.label', default: 'Date of Consultation')}"/>

                    <g:sortableColumn property="ridLibraryUnit"
                                      title="${message(code: 'ridTransaction.ridLibraryUnit.label', default: 'Library Unit')}"/>

                    <g:sortableColumn property="notes"
                                      title="${message(code: 'ridTransaction.notes.label', default: 'Notes')}"/>

                </tr>
                </thead>
                <tbody>
                <g:each in="${ridTransactionInstanceList}" status="i" var="ridTransactionInstance">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}"
                        onclick="window.location = 'show/${ridTransactionInstance.id}'"
                        style="cursor: pointer;">
                        <%
                            userQ = ridTransactionInstance.userQuestion
                            if (userQ != null && userQ.length() > 12)
                                userQ = userQ.substring(0, 12) + "..."
                        %>
                        <td>
                            %{--<g:link action="show" id="${ridTransactionInstance.id}"--}%
                            %{--title="${ridTransactionInstance.userQuestion}">--}%
                            ${userQ}
                            %{--</g:link>--}%
                        </td>

                        <td>${fieldValue(bean: ridTransactionInstance, field: "staffPennkey")}</td>

                        <td><g:formatDate format="yyyy-MM-dd"
                                          date="${ridTransactionInstance?.dateOfConsultation}"/></td>
                        %{--<td>${fieldValue(bean: ridTransactionInstance, field: "dateOfConsultation")}</td>--}%

                        <td>${fieldValue(bean: ridTransactionInstance, field: "ridLibraryUnit")}</td>

                        <td>${fieldValue(bean: ridTransactionInstance, field: "notes")}</td>

                    </tr>
                </g:each>
                </tbody>
            </table>
            <g:if test="${ridTransactionInstanceTotal > params.max}">
                <div class="pagination">
                    <g:paginate action="query" total="${ridTransactionInstanceTotal}" params="${params}" next="&gt;&gt;"
                                prev="&lt;&lt;"/>
                </div>
            </g:if>
        </div>
    </div>
</md:report>
%{--</body>--}%
%{--</html>--}%
