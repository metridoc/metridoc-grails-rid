<%@ page import="metridoc.rid.RidTransaction" %>
<g:set var="entityName" value="${message(code: 'ridTransaction.label', default: 'RidTransaction')}"/>

<md:report>
    <div class="md-application-content">
        <r:external dir="datepicker/css" file="datepicker.css" plugin="metridoc-rid"/>
        <r:external dir="datepicker/js" file="bootstrap-datepicker.js" plugin="metridoc-rid"/>
        <r:external dir="css" file="ridtrans.css" plugin="metridoc-rid"/>
        <r:external dir="js" file="RidTransaction.js" plugin="metridoc-rid"/>

        <g:render template="tabs" plugin="metridocRid"/>

        <div id="search-ridTransaction" class="content scaffold-search" role="main">
            <h1><g:message code="RidTransaction Search"/></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

            <g:form class="form-horizontal" action="query" method="GET">
                <fieldset class="form">
                    <div class="control-group fieldcontain">
                        <label class="control-label" for="dateOfConsultation">
                            <g:message code="ridTransaction.dateOfConsultation.label"
                                       default="Consultation Date Between"/>
                        </label>
                        <%
                            Calendar dateOfConsultation1 = Calendar.getInstance()
                            Calendar dateOfConsultation2 = (Calendar) dateOfConsultation1.clone()
                            dateOfConsultation2.add(Calendar.DAY_OF_YEAR, -(365 * 2))
                        %>
                        <div class="controls">
                            <input type="text" name="dateOfConsultation_start" style="width: 150px" id="dpd1"/>
                            <span style="font-size: 12px; color: #666666">&nbsp;&nbsp;and&nbsp;&nbsp;</span>
                            <input type="text" name="dateOfConsultation_end" style="width: 150px" id="dpd2"/>
                        </div>
                    </div>

                    <div class="control-group fieldcontain">
                        <label class="control-label" for="ridReportType">
                            <g:message code="ridTransaction.ridReportType.label" default="Report Type"/>
                        </label>

                        <div class="controls">
                            <g:select id="ridReportTypeSearch" style="width:150px" name="ridReportTypeSearch"
                                      noSelection="${['0': 'All Types']}" optionKey="id" multiple="true" value="0"
                                      from="${metridoc.rid.RidReportType.list()}"/>
                        </div>
                    </div>

                    <div class="control-group fieldcontain">
                        <label class="control-label" for="staffPennkey">
                            <g:message code="ridTransaction.staffPennkey.label" default="Staff Pennkey"/>
                        </label>

                        <div class="controls">
                            <g:textField id="staffPennkey" style="width:150px" class="userInput" name="staffPennkey"
                                         maxlength="100" value=""/>
                        </div>
                    </div>

                    <div class="control-group fieldcontain">
                        <label class="control-label" for="userQuestion">
                            <g:message code="ridTransaction.userQuestion.label" default="User Question"/>
                        </label>

                        <div class="controls">
                            <g:textField id="userQuestion" style="width:350px" class="userInput" name="userQuestion"
                                         maxlength="100" value=""/>
                        </div>
                    </div>

                    <div class="control-group fieldcontain">
                        <label class="control-label" for="notes">
                            <g:message code="ridTransaction.notes.label" default="Notes"/>
                        </label>

                        <div class="controls">
                            <g:textField id="notes" style="width:350px" class="userInput" name="notes" maxlength="100"
                                         value=""/>
                        </div>
                    </div>
                </fieldset>
                <fieldset class="buttons">
                    <input id="resetButton" class="btn btn-success" type="reset" value="Reset"/>
                    <g:submitButton name="search" class="btn btn-primary"
                                    value="${message(code: 'default.button.search.label', default: 'Search')}"/>
                </fieldset>
            </g:form>
        </div>
    </div>
</md:report>