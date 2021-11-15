<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>
<div class="col-md-6" style="overflow-y: auto;height: calc(100% - 50px);">
    <div style="float: left;">
        <h6 style="float: left;">전체QA개수</h6>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN')" var="u">
        <div class="img-shot-select m-right5" style="float: right;">
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
            <th width="25%">video </th>
            <th width="25%">level</th>
            <th width="25%">count</th>
            <th width="25%">sum</th>
        </tr>
        </thead>
        <tbody>
        <c:set var="shotSum" value="0"/>
        <c:set var="sceneSum" value="0"/>
        <c:set var="lv1Cnt" value="0"/>
        <c:set var="lv2Cnt" value="0"/>
        <c:set var="lv3Cnt" value="0"/>
        <c:set var="lv4Cnt" value="0"/>
        <c:set var="kbCnt" value="0"/>
        <c:forEach var="result" items="${shotStatsList}" varStatus="i">
            <tr class=""  name ="statsTr">
                <c:set var="shotSum" value="${shotSum + result.cnt}"/>
                <c:choose>
                    <c:when test="${result.questiontype=='1'}">
                        <c:set var="lv1Cnt" value="${shotSum + result.cnt}"/>
                    </c:when>
                    <c:when test="${result.questiontype=='2'}">
                        <c:set var="lv2Cnt" value="${lv2Cnt + result.cnt}"/>
                    </c:when>
                    <c:when test="${result.questiontype=='3'}">
                        <c:set var="lv3Cnt" value="${lv3Cnt + result.cnt}"/>
                    </c:when>
                    <c:when test="${result.questiontype=='4'}">
                        <c:set var="lv4Cnt" value="${lv4Cnt + result.cnt}"/>
                    </c:when>
                    <c:when test="${result.questiontype=='kb'}">
                        <c:set var="kbCnt" value="${kbCnt + result.cnt}"/>
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${(i.index) > 0}">
                        <td>${result.questiontype}</td>
                        <td>${result.cnt}</td>
                    </c:when>
                    <c:otherwise>
                        <th scope="row" rowspan="${shotStatsList.size()}" style="vertical-align: middle;">Shot</th>
                        <td>${result.questiontype}</td>
                        <td>${result.cnt}</td>
                        <td rowspan="${shotStatsList.size()}" id="shotSum" style="vertical-align: middle;"></td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
        <c:forEach var="result" items="${sceneStatsList}" varStatus="i">
            <c:set var="sceneSum" value="${sceneSum + result.cnt}"/>
            <tr class=""  name ="statsTr">
                <c:choose>
                    <c:when test="${result.questiontype=='1'}">
                        <c:set var="lv1Cnt" value="${shotSum + result.cnt}"/>
                    </c:when>
                    <c:when test="${result.questiontype=='2'}">
                        <c:set var="lv2Cnt" value="${lv2Cnt + result.cnt}"/>
                    </c:when>
                    <c:when test="${result.questiontype=='3'}">
                        <c:set var="lv3Cnt" value="${lv3Cnt + result.cnt}"/>
                    </c:when>
                    <c:when test="${result.questiontype=='4'}">
                        <c:set var="lv4Cnt" value="${lv4Cnt + result.cnt}"/>
                    </c:when>
                    <c:when test="${result.questiontype=='kb'}">
                        <c:set var="kbCnt" value="${kbCnt + result.cnt}"/>
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${(i.index) > 0}">
                        <td>${result.questiontype}</td>
                        <td>${result.cnt}</td>
                    </c:when>
                    <c:otherwise>
                        <th scope="row" rowspan="${sceneStatsList.size()}" style="vertical-align: middle;">Scene</th>
                        <td>${result.questiontype}</td>
                        <td>${result.cnt}</td>
                        <td rowspan="${sceneStatsList.size()}" id="sceneSum" style="vertical-align: middle;">${result.cnt}</td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>

        <script>
            $("#shotSum").text(${shotSum});
            $("#sceneSum").text(${sceneSum});
        </script>

        </tbody>
    </table>
    </div>
</div>
<div class="col-md-6" style="overflow-y: auto;height: calc(100% - 50px);">
    <div id="chart_div"></div>

    <script>
        google.charts.load('current', {packages: ['corechart', 'bar']});
        google.charts.setOnLoadCallback(drawStacked);

        function drawStacked() {
            var data = google.visualization.arrayToDataTable([
                ['Level', 'Lv 1', 'Lv 2', 'Lv 3','Lv 4','KB' ],
                ['QA', ${lv1Cnt}, ${lv2Cnt}, ${lv3Cnt}, ${lv4Cnt}, ${kbCnt}]
            ]);

            var options = {
                title: '전체QA개수',
                isStacked: true,
                width:window.innerWidth * 0.3,
                height:500,
                bar: { groupWidth: '25%' },
                chartArea: {width: '65%'}
            };

            var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
            chart.draw(data, options);
        }
    </script>
</div>