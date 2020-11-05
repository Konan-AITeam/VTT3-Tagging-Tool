<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>
<c:import url="../includes/header.jsp"/>
<c:import url="../includes/script.jsp"/>

<!-- #MAIN PANEL -->
<div class="container-fluid">
    <%--row : ribbon--%>
    <div class="row">
        <div class="col-12">
            <!-- RIBBON -->
            <span class="item"></span>
            <span class="ribbon-button-alignment pull-right" style="margin-right:25px">
            <%--<span id="btn_transcoding" class="btn btn-ribbon hidden-xs" data-title="add"><i class="fa fa-video-camera txt-color-yellow"></i> 트랜스코딩</span>--%>
            <span id="btn_catalog" class="btn btn-ribbon hidden-xs" data-target=".multi-collapse" data-toggle="collapse" aria-expanded="true" aria-controls=".multi-collapse"><i class="fa fa-file-picture-o "></i> 시각정보 편집(확대/축소)</span>
            <span id="btn_catalog" class="btn btn-ribbon hidden-xs" data-title="add"><i class="fa fa-file-picture-o "></i> 카탈로깅</span>
            <span id="btn_delete" class="btn btn-ribbon hidden-xs" data-title="add"><i class="fa fa-trash-o txt-color-customRed"></i> 삭제</span>
            <span id="btn_download" class="btn btn-ribbon hidden-xs" data-title="add"><i class="fa fa-download txt-color-customGreen"></i> 다운로드</span>
            <span id="btn_storyboard" class="btn btn-ribbon hidden-xs" data-title="add"><i class="fa fa-picture-o txt-color-orange"></i> 스토리보드</span>
            <span id="btn_play" class="btn btn-ribbon hidden-xs" data-title="add"><i class="fa fa-youtube-play txt-color-orange"></i> 객체인식재생</span>
            </span>
        </div>
    </div>

    <%--row : contents--%>
    <div class="row">

        <%-- card N0.01 --%>
        <div class="collapse multi-collapse show col-sm-12 col-md-2 col-lg-2">
        <div class="card" >
            <div class="card-body">
                <h5 class="card-title">구간 리스트</h5>
                <div class="" style="height:700px; overflow-y: scroll;" id="select-list">
                    <table class="">
                        <thead>
                        <tr>
                            <th scope="col"><small>구간</small></th>
                            <th scope="col"><small>구간범위</small></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach begin="0" end="99" step="1" varStatus="status">
                            <tr>
                                <td>
                                    <small><a href="javascript:void(0);"><c:out value="${status.count}"/>구간</a></small>
                                </td>
                                <td>
                                    <small>00:00 ~ 03:30</small>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        </div>

        <%-- card N0.02 --%>
        <div class="col">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">
                    시각정보 편집
                </h5>
                <div class="row" id="edit">
                    <div class="col-md-7 col-lg-7">
                        <img src="http://183.110.246.21:7070/darc4/proxyshot/2018/04/30/624//S00023.jpg" class="img-fluid" alt="img">
                        <ul class="list-inline padding-10">
                            <li>
                                <i class="fa fa-calendar"></i>
                                <a href="javascript:void(0);"> March 12, 2015 </a>
                            </li>
                            <li>
                                <i class="fa fa-comments"></i>
                                <a href="javascript:void(0);"> 38 Comments </a>
                            </li>
                        </ul>
                    </div>
                    <div class="col-md-5 col-lg-5">
                        <form class="needs-validation">
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="name">이름</label>
                                    <input type="text" class="form-control form-control-sm" id="name" placeholder="이름">
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="faceCoordinate">얼굴 좌표</label>
                                    <input type="password" class="form-control form-control-sm" id="faceCoordinate" placeholder="ex) 100.100.100.100" readonly>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="allCoordinate">전체좌표</label>
                                    <input type="text" class="form-control form-control-sm" id="allCoordinate" placeholder="전체좌표">
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="action">행동</label>
                                    <input type="password" class="form-control form-control-sm" id="action" placeholder="행동">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="allCoordinate">감정</label>
                                    <input type="text" class="form-control form-control-sm" id="id00" placeholder="감정">
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="action">서술어</label>
                                    <input type="password" class="form-control form-control-sm" id="id000" placeholder="서술어">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="allCoordinate">관련 객체</label>
                                    <input type="text" class="form-control form-control-sm" id="id0002" placeholder="관련 객체">
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="action">관련 객체좌표</label>
                                    <input type="password" class="form-control form-control-sm" id="id0003" placeholder="관련 객체좌표">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-4">
                                    <label for="action">상/하의</label>
                                    <input type="text" class="form-control form-control-sm"  placeholder="상/하의">
                                </div>
                                <div class="form-group col-md-4">
                                    <label for="action">좌표</label>
                                    <select id="inputState" class="form-control form-control-sm" placeholder="좌표">
                                        <option selected>Choose...</option>
                                        <option>...</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-4">
                                    <label for="inputZip">색상</label>
                                    <select id="inputZip" class="form-control form-control-sm" placeholder="색상">
                                        <option selected>Choose...</option>
                                        <option>...</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-4">
                                    <label for="action">일반객체</label>
                                    <input type="text" class="form-control form-control-sm"  placeholder="일반객체하의">
                                </div>
                                <div class="form-group col-md-auto">
                                    <label for="action">객체좌표</label>
                                    <div>
                                        <div class="input-group">
                                            <input type="text" class="form-control form-control-sm" placeholder="객체좌표" aria-label="객체좌표">
                                            <span class="input-group-btn input-group-sm"><button class="btn btn-secondary btn-sm" type="button">추가</button></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-sm-12">
                        <form class="needs-validation">
                            <div class="form-row">
                                <div class="form-group col-md-4">
                                    <label for="allCoordinate">관련 인물</label>
                                    <input type="text" class="form-control form-control-sm"  placeholder="관련 인물">
                                </div>
                                <div class="form-group col-md-4">
                                    <label for="action">장소</label>
                                    <input type="text" class="form-control form-control-sm"  placeholder="장소">
                                </div>
                                <div class="form-group col-md-4">
                                    <label for="action">세부장소</label>
                                    <input type="text" class="form-control form-control-sm"  placeholder="세부장소">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-8">
                                    <label for="action">객체간의 관계</label>
                                    <div>
                                        <div class="input-group">
                                            <input type="text" class="form-control form-control-sm" placeholder="객체간의 관계" aria-label="객체간의 관계">
                                            <span class="input-group-btn input-group-sm"><button class="btn btn-secondary btn-sm" type="button">추가</button></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-8">
                                    <label for="action">감정에 따른 행동</label>
                                    <div>
                                        <div class="input-group">
                                            <input type="text" class="form-control form-control-sm" placeholder="감정에 따른 행동" aria-label="감정에 따른 행동">
                                            <span class="input-group-btn input-group-sm"><button class="btn btn-secondary btn-sm" type="button">추가</button></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        </div>

    <%-- card N0.03 --%>
        <div class="collapse multi-collapse show col-12">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">시각정보 리스트</h5>
                <nav class="nav nav-tabs" id="myTab" role="tablist">
                    <a class="nav-item nav-link active" id="nav-tab-01" data-toggle="tab" href="#nav-01" role="tab" aria-controls="nav-01" aria-selected="true">대표프레임1 / 인물</a>
                    <a class="nav-item nav-link" id="nav-tab-02" data-toggle="tab" href="#nav-02" role="tab" aria-controls="nav-02" aria-selected="false">대표프레임1 / 객체</a>
                    <a class="nav-item nav-link" id="nav-tab-03" data-toggle="tab" href="#nav-03" role="tab" aria-controls="nav-03" aria-selected="false">대표프레임2 / 인물</a>
                    <a class="nav-item nav-link" id="nav-tab-04" data-toggle="tab" href="#nav-04" role="tab" aria-controls="nav-04" aria-selected="false">대표프레임2 / 객체</a>
                </nav>
                <div class="tab-content" id="nav-tabContent">
                    <div class="tab-pane fade show active" id="nav-01" role="tabpanel" aria-labelledby="nav-01">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">이름</th>
                                <th scope="col">얼굴좌표</th>
                                <th scope="col">전체좌표</th>
                                <th scope="col">행동</th>
                                <th scope="col">감정</th>
                                <th scope="col">서술어</th>
                                <th scope="col">객체</th>
                                <th scope="col">상의죄표</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">2</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">3</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">3</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">3</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">3</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="tab-pane fade" id="nav-02" role="tabpanel" aria-labelledby="nav-02">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">객체명</th>
                                <th scope="col">좌표</th>
                                <th scope="col">구분</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="tab-pane fade" id="nav-03" role="tabpanel" aria-labelledby="nav-03">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">이름</th>
                                <th scope="col">얼굴좌표</th>
                                <th scope="col">전체좌표</th>
                                <th scope="col">행동</th>
                                <th scope="col">감정</th>
                                <th scope="col">서술어</th>
                                <th scope="col">객체</th>
                                <th scope="col">상의죄표</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">2</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">3</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">3</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">3</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">3</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="tab-pane fade" id="nav-04" role="tabpanel" aria-labelledby="nav-04">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">객체명</th>
                                <th scope="col">좌표</th>
                                <th scope="col">구분</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        </div>

    </div>
</div>
<!-- END #MAIN PANEL -->
<script>
    $(document).ready(function() {
        var maxHeight = -1;

        $('div#edit').each(function() {
            maxHeight = maxHeight > $(this).height() ? maxHeight : $(this).height();
        });

        $('div#select-list').each(function() {
            $(this).height(maxHeight);
        });
    });
</script>

<c:import url="../includes/footer.jsp"/>