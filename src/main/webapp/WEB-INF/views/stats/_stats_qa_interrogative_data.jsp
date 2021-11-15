<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>

<div style="float: left;">

    <h6 style="float: left;">전체QA 의문사별 개수</h6>
    <sec:authorize access="hasAnyRole('ROLE_ADMIN')" var="u">
        <div class="img-shot-select m-right5" style="float: right;">
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
<c:set var="sumVal" value="0"/>
<c:set var="sumLevel" value="0"/>
<div style="float: left;position: fixed;margin-top: 40px;width:100%;">
    <div class="col-md-2" style="padding-right: 10px;">
        <table class="table table-striped" >
            <thead>
            <tr>
                <th width="20%">video </th>
                <th width="20%">level</th>
                <th width="20%">question</th>
                <th width="20%">count</th>
                <th width="20%">sum</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="result" items="${shotStatsList}" varStatus="i">
            <tr class=""  name ="statsTr">
                <c:choose>
                    <c:when test="${sumLevel == result.questiontype}">
                        <c:set var="sumVal" value="${sumVal + result.cnt}"/>
                        <td>${result.question}</td>
                        <td>${result.cnt}</td>
                    </c:when>
                    <c:otherwise>
                        <script>
                            $("#shotSum_"+"${sumLevel}").text(${sumVal});
                        </script>
                        <c:set var="sumLevel" value="${result.questiontype}"/>
                        <c:set var="sumVal" value="${result.cnt}"/>
                        <th scope="row" rowspan="6" style="vertical-align: middle;">Shot</th>
                        <td rowspan="6" style="vertical-align: middle;">${result.questiontype}</td>
                        <td>${result.question}</td>
                        <td>${result.cnt}</td>
                        <td rowspan="6"  style="vertical-align: middle;" id="shotSum_${sumLevel}"></td>
                    </c:otherwise>
                </c:choose>
            </tr>
            </c:forEach>
            <script>
                $("#shotSum_"+"${sumLevel}").text(${sumVal});
            </script>
            </tbody>
        </table>
    </div>
    <div class="col-md-2" style="padding-left: 10px;">
        <table class="table table-striped">
            <thead>
            <tr>
                <th width="20%">video </th>
                <th width="20%">level</th>
                <th width="20%">question</th>
                <th width="20%">count</th>
                <th width="20%">sum</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="sumVal" value="0"/>
            <c:set var="sumLevel" value="0"/>
            <c:forEach var="result" items="${sceneStatsList}" varStatus="i">
                <tr name ="statsTr">
                    <c:choose>
                        <c:when test="${sumLevel == result.questiontype}">
                            <c:set var="sumVal" value="${sumVal + result.cnt}"/>
                            <td>${result.question}</td>
                            <td>${result.cnt}</td>
                        </c:when>
                        <c:otherwise>
                            <script>
                                $("#sceneSum_"+"${sumLevel}").text(${sumVal});
                            </script>
                            <c:set var="sumLevel" value="${result.questiontype}"/>
                            <c:set var="sumVal" value="${result.cnt}"/>
                            <th scope="row" rowspan="6" style="vertical-align: middle;">Scene</th>
                            <td rowspan="6" style="vertical-align: middle;">${result.questiontype}</td>
                            <td>${result.question}</td>
                            <td>${result.cnt}</td>
                            <td rowspan="6"  style="vertical-align: middle;" id="sceneSum_${sumLevel}"></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            <script>
                $("#sceneSum_"+"${sumLevel}").text(${sumVal});
            </script>
            </tbody>
        </table>
    </div>

    <div class="col-md-8" style="padding-left: 10px; overflow-x: auto;">
        <div id="chart_div" style=""></div>
        <script>
            google.charts.load('current', {packages: ['corechart', 'bar']});
            google.charts.setOnLoadCallback(drawStacked);
            function drawStacked() {
                var data = google.visualization.arrayToDataTable([
                    ['Level', 'Who','When', 'Where', 'What','How','Why']
                    <c:forEach var="result" items="${chartStatsList}" varStatus="i">
                    ,['${result.questiontype}',${result.qwho},${result.qwhen},${result.qwhere},${result.qwhat},${result.qhow},${result.qwhy}]
                    </c:forEach>
                ]);

                var options = {
                    title: '전체QA 의문사별 개수',
                    width:window.innerWidth * 0.4,
                    chartArea: {width: '65%'},
                    isStacked: true,
                    hAxis: {
                        minValue: 0,
                    },
                    vAxis: {
                        title: 'Level'
                    }
                };
                var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
                chart.draw(data, options);
            }
        </script>
    </div>
</div>

    </tbody>
</table>

