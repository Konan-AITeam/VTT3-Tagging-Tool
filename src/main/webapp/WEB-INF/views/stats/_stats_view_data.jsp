<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>
<script src="<c:url value="/resources/js/selectareas/jquery.selectareas.js"/>"></script>
<div class="col-md-6" style="overflow-y: auto;height: calc(100% - 50px);">
    <div style="float: left;">
        <sec:authorize access="hasAnyRole('ROLE_ADMIN')" var="u">
            <div class="img-shot-select m-right5">
                <input type="hidden" id="statstype" value="${param.statstype}"/>
                <input type="hidden" id="statsitem" value="${param.statsitem}"/>
                <select id="workerId" name="workerId" class="form-control" style="height:30px; font-size:12px;" onchange="statsWorkerChange();">
                    <option value>
                        작업자
                    </option>
                         <c:if test="${ userList.size() != 0 }">
                             <c:forEach var="list" items="${userList}" varStatus="i">
                                 <option value="${list.userid}" <c:if test="${workerId eq list.userid}">selected</c:if> >
                                         ${list.userid}
                                 </option>
                             </c:forEach>
                         </c:if>
                </select>
            </div>
        </sec:authorize>
    </div>
    <table class="table table-striped">
        <thead>
        <tr>
            <th width="10%">No </th>
            <th width="40%">${param.statsitem}</th>
            <th width="25%">count</th>
            <th width="25%">ratio(%)</th>
        </tr>
        </thead>
        <tbody>
    <c:if test="${ statsList.size() != 0 }">
        <c:forEach var="result" items="${statsList}" varStatus="i">
            <tr class=""  name ="statsTr">
                <th scope="row" ><c:out value="${(i.index)+1}"/></th scope="row" >
                <td>${result.statsitem}</td>
                <td>${result.cnt}</td>
                <td>${Math.round((result.cnt / totalcnt * 100.00 )*100)/100.00}</td>
            </tr>
        </c:forEach>
    </c:if>
    <c:if test="${ statsList.size() < 1 }">
    <tr class=""  name ="statsTr">
        <td colspan="4">데이터가 존재 하지 않습니다.</td>
    </tr>
    </c:if>
        </tbody>
    </table>

</div>
<div class="col-md-6" id="statsChart">
    <script>
        google.charts.load('current', {'packages':['corechart']});
        google.charts.setOnLoadCallback(drawChart);
        function drawChart() {

            var data = google.visualization.arrayToDataTable([
                ['${result.statsitem}', 'count']
            <c:if test="${ statsList.size() != 0 }">
                <c:forEach var="result" items="${statsList}" varStatus="i">
                    ,["${result.statsitem}",     ${result.cnt}]
                </c:forEach>
            </c:if>
            ]);
            var options = {
            title: '${param.statsitem}',
           legend: { position: 'bottom', maxLines: 3 }
            };

            var chart = new google.visualization.PieChart(document.getElementById('piechart'));

            chart.draw(data, options);
        }
    </script>
    <div id="piechart" style="width: 700px; height: 700px;"></div>
</div>
