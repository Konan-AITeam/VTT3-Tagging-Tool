<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
    <h3 class="modal-title" id="popModalLabel">
        <i class="fa fa-keyboard-o"></i>&nbsp;가이드 라인
    </h3>
</div>

<div class="modal-body" style="height:450px;font-size:13px;">
    <div class="col-lg-12 col-md-12 col-xs-12">
        <div class="" role="tabpanel" data-example-id="togglable-tabs" style="height:100%;">
            <ul id="objTabs" class="nav nav-tabs" role="tablist" style="margin-bottom: 15px;">
                <li role="presentation" class="active col-lg-3 col-md-3 col-xs-3">
                    <a href="#descGuideDiv" id="descTab" role="tab" data-toggle="tab" aria-expanded="true">Scene 묘사</a>
                </li>
                <li role="presentation" class="col-lg-3 col-md-3 col-xs-3">
                    <a href="#QA1GuideDiv" role="tab" id="QA1Tab" data-toggle="tab" aria-expanded="false">Q & A - Level 3</a>
                </li>
                <li role="presentation" class="col-lg-3 col-md-3 col-xs-3">
                    <a href="#QA2GuideDiv" role="tab" id="QA2Tab" data-toggle="tab" aria-expanded="false">Q & A - Level 4</a>
                </li>
            </ul>

            <div id="nav-objTabContent" class="layer-edit-wrap tab-content">
                <div role="tabpanel" class="tab-pane fade active in" id="descGuideDiv" aria-labelledby="descTab">
                    <h3>Scene 묘사 가이드라인</h3>
                    <ul class="list-group list-group-flush" style="font-size: 1.2em;">
                        <li class="list-group-item">You must summarize the whole video content of the video in <b>three sentences</b>.</li>
                        <li class="list-group-item">Please summarize above video in your own words.</li>
                        <li class="list-group-item">Please write <b>complete sentences</b>, ending with full stops.</li>
                        <li class="list-group-item">Please <b>don't use personal pronouns like "they, he, she, …"</b>. Use person's name instead.v
                        <li class="list-group-item">Please use only the names specified in the character guide, such as Maru, Eungi and etc.</li>
                    </ul>
                </div>

                <div role="tabpanel" class="tab-pane fade" id="QA1GuideDiv" aria-labelledby="QA1Tab">
                    <h3>Scene Q&A (Level 3) 가이드라인</h3>
                    <ul class="list-group list-group-flush" style="font-size: 1.2em;">
                        <li class="list-group-item">Now consider multiple supporting facts with <b>time factor*</b> from the video (scene) and create a QA sets. <br>* <b>Time factor</b>: A sequence of the situations/actions in the video.<br>According to this, QA sets about how situations have changed and subjects have acted can be created <br>(e.g. Q: How did Rachel figure out the truth of prom in high school? A: Rachel watched the video that shoot at the day.)</li>
                        <li class="list-group-item"><b>Your question must start with 5W1H</b>.(How (preferred), What)</li>
                        <li class="list-group-item">Your question and answers <b>must be a complete sentence with correct grammar</b>. If either question or answer consists of a single word, you cannot submit.</li>
                        <li class="list-group-item">The questions you <b>write must be answered within a video clip</b>.</li>
                        <li class="list-group-item">Please avoid completely unrelated questions</b>. ex) How old is the earth? (X)</li>
                        <li class="list-group-item">Please <b>avoid vague terms</b>. Phrase the question in such a way that there is only one possible answer to the question.</li>
                        <li class="list-group-item">Please enter <b>case sensitive</b>.<br>ex) WHY IS ROSS UPSET? (X)<br>ex) Why is Ross upset? (O)</li>
                        <li class="list-group-item">Please <b>don't use personal pronouns like "they, he, she, …"</b>. Use person's name instead.</li>
                        <li class="list-group-item">Please use only the names specified in the character guide, such as Maru, Eungi and etc.</li>
                        <li class="list-group-item">Please write fake answers as similar as the length of true answer that you created.</li>
                    </ul>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="QA2GuideDiv" aria-labelledby="QA2Tab">
                    <h3>Scene Q&A (Level 4) 가이드라인</h3>
                    <ul class="list-group list-group-flush" style="font-size: 1.2em;">
                        <li class="list-group-item">At this level, consider <b>reason for causality*</b> from the video (scene) and create a QA sets. <br>* <b>Reasoning for causality</b>: The process of identifying causality: the relationship between a cause and effect from actions or situations. Question can start with “WHY”, and Answer should be reasoning for causality. <br>(e.g. Q: Why does Rachel storm out of the office? A: She thinks the interviewer is trying to sleep with her)</li>
                        <li class="list-group-item"><b>Your question must start with 5W1H</b>.(Why)</li>
                        <li class="list-group-item">Your question and answers <b>must be a complete sentence with correct grammar</b>. If either question or answer consists of a single word, you cannot submit.</li>
                        <li class="list-group-item">The questions you <b>write must be answered within a video clip</b>.</li>
                        <li class="list-group-item">Please avoid completely unrelated questions</b>. ex) How old is the earth? (X)</li>
                        <li class="list-group-item">Please <b>avoid vague terms</b>. Phrase the question in such a way that there is only one possible answer to the question.</li>
                        <li class="list-group-item">Please enter <b>case sensitive</b>.<br>ex) WHY IS ROSS UPSET? (X)<br>ex) Why is Ross upset? (O)</li>
                        <li class="list-group-item">Please <b>don't use personal pronouns like "they, he, she, …"</b>. Use person's name instead.</li>
                        <li class="list-group-item">Please use only the names specified in the character guide, such as Maru, Eungi and etc.</li>
                        <li class="list-group-item">Please write fake answers as similar as the length of true answer that you created.</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal-footer">
    <div>
        <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i> 닫기</button>
    </div>
</div>