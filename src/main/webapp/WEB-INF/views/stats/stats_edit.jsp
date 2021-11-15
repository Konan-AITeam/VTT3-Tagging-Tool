<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>
<c:import url="../includes/header.jsp"/>
<style type="text/css">
    .container.stats .section-list-Wrap:not(:first-child){
        padding-left:10px;
    }
</style>
<!-- top navigation -->
<div class="top_nav">
    <div class="nav_menu nav_menu-j">
        <nav>
            <div class="col-8 align-self-center">
                <div class="title-txt text-themecolor">
                    CONTENT
                </div>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item">
                        <a href="javascript:void(0)"> 콘텐츠</a>
                    </li>
                    <li class="breadcrumb-item ">
                        <a href="<c:url value="/content"/>"> 콘텐츠 리스트</a>
                    </li>
                    <li class="breadcrumb-item strong active">
                        통계
                    </li>
                </ol>
            </div>
        </nav>
    </div>
</div>
<!-- /top navigation -->
<!-- page content -->
<div class="container stats fix" role="main">
        <div class="section-list-Wrap" style="width:250px;">
            <div id="section-list" class="x_panel section-list">
                <div class="x_title">
                    <h2> 통계항목  <small> Statistics list </small></h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div class="tab-content" role="tabpanel" data-example-id="togglable-tabs"  style="padding-bottom:10px;">
                        <ul id="statsTabs" class="nav nav-tabs stats-tabs" role="tablist">
                            <li role="presentation" class="active">
                                <a href="#viewInfoDiv" id="viewInfo" role="tab" data-toggle="tab" aria-expanded="true"> 시각정보 </a>
                            </li>
                            <li role="presentation" class="">
                                <a href="#qaInfoDiv" role="tab" id="qaInfo" data-toggle="tab" aria-expanded="false"> QA정보 </a>
                            </li>
                        </ul>
                        <div id="nav-objTabContent" class="layer-edit-wrap tab-content">
                            <div role="tabpanel" class="tab-pane fade active in" id="viewInfoDiv" aria-labelledby="viewInfo">
                                <table class="table table-striped">
                                    <tbody>
                                    <tr class="section_row" id="btn_place" style="display:table-row;" onclick="getStats('viewInfo','place');">
                                        <th name="secTdNum" scope="row" class="section-num">
                                            place
                                        </th>
                                    </tr>
                                    <tr class="section_row" id="btn_behavior" style="display:table-row;" onclick="getStats('viewInfo','behavior');">
                                        <th name="secTdNum" scope="row" class="section-num">
                                            behavior
                                        </th>
                                    </tr>
                                    <tr class="section_row" id="btn_predicate" style="display:table-row;" onclick="getStats('viewInfo','predicate');">
                                        <th name="secTdNum" scope="row" class="section-num">
                                            predicate
                                        </th>
                                    </tr>
                                    <tr class="section_row" id="btn_emotion" style="display:table-row;" onclick="getStats('viewInfo','emotion');">
                                        <th name="secTdNum" scope="row" class="section-num">
                                            emotion
                                        </th>
                                    </tr>
                                    <tr class="section_row" id="btn_person" style="display:table-row;" onclick="getStats('viewInfo','person');">
                                        <th name="secTdNum" scope="row" class="section-num">
                                            person
                                        </th>
                                    </tr>
                                    <tr class="section_row" id="btn_object" style="display:table-row;" onclick="getStats('viewInfo','object');">
                                        <th name="secTdNum" scope="row" class="section-num">
                                            object
                                        </th>
                                    </tr>
                                    <tr class="section_row" id="btn_relatedobject" style="display:table-row;" onclick="getStats('viewInfo','related object');">
                                        <th name="secTdNum" scope="row" class="section-num">
                                            related object
                                        </th>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div role="tabpanel" class="tab-pane fade" id="qaInfoDiv" aria-labelledby="qaInfo">
                                <table class="table table-striped">
                                    <tbody>
                                    <tr class="section_row" id="btn_qaTotal" style="display:table-row;" onclick="getStats('qaInfo','qaTotal');">
                                        <th name="secTdNum" scope="row" class="section-num">
                                            전체QA개수
                                        </th>
                                    </tr>
                                    <tr class="section_row" id="btn_qaInterrogative" style="display:table-row;" onclick="getStats('qaInfo','qaInterrogative');">
                                        <th name="secTdNum" scope="row" class="section-num">
                                            전체QA 의문사별 개수
                                        </th>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


    <div class="section-list-Wrap" style="width:calc(100% - 250px);height:100%;">
        <div class="row" style="height:100%;">
            <div class="x_panel" style="height:100%;">
                <div class="x_title">
                    <h2>통계 정보<small>Statistics</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li >
                            <a class="table-btn" onclick="help_hotkey()">
                                <i class="fas fa-keyboard"></i> 단축키
                            </a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div id="statsData" style="height:100%;">
                </div>
            </div>
        </div>
    </div>

</div>
<!-- /page content -->
<!-- END #MAIN PANEL -->
<c:import url="../includes/script.jsp"/>
<c:import url="./_stats_script.jsp"/>
<script>
   /* $('a[data-toggle="tab"]').on('show.bs.tab',function(e) {

        var futureTab = $(e.relatedTarget).text();
        alert(futureTab);
    });*/
</script>
<c:import url="../includes/footer.jsp"/>