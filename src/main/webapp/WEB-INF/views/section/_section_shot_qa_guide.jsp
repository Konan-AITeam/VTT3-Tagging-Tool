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
                    <a href="#descGuideDiv" id="descTab" role="tab" data-toggle="tab" aria-expanded="true">Shot 묘사</a>
                </li>
                <li role="presentation" class="col-lg-3 col-md-3 col-xs-3">
                    <a href="#QA1GuideDiv" role="tab" id="QA1Tab" data-toggle="tab" aria-expanded="false">Q & A - Level 1</a>
                </li>
                <li role="presentation" class="col-lg-3 col-md-3 col-xs-3">
                    <a href="#QA2GuideDiv" role="tab" id="QA2Tab" data-toggle="tab" aria-expanded="false">Q & A - Level 2</a>
                </li>
                <li role="presentation" class="col-lg-3 col-md-3 col-xs-3">
                    <a href="#QAKBGuideDiv" role="tab" id="QAKBTab" data-toggle="tab" aria-expanded="false">Q & A - Level KB</a>
                </li>
            </ul>

            <div id="nav-objTabContent" class="layer-edit-wrap tab-content">
                <div role="tabpanel" class="tab-pane fade active in" id="descGuideDiv" aria-labelledby="descTab">
                    <h3>Shot 묘사 가이드라인</h3>
                    <ul class="list-group list-group-flush" style="font-size: 1.2em;">
                        <li class="list-group-item">You must summarize the whole video content of the video in <b>single sentence</b>.</li>
                        <li class="list-group-item">Please summarize above video in your own words.</li>
                        <li class="list-group-item">Please write <b>complete sentences</b>, ending with full stops.</li>
                        <li class="list-group-item">Please <b>don't use personal pronouns like "they, he, she, …"</b>. Use person's name instead.</li>
                        <li class="list-group-item">Please use only the names specified in the character guide, such as Maru, Eungi and etc.</li>
                    </ul>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="QA1GuideDiv" aria-labelledby="QA1Tab">
                    <h3>Shot Q&A (Level 1) 가이드라인</h3>
                    <ul class="list-group list-group-flush" style="font-size: 1.2em;">
                        <li class="list-group-item">Please consider one <b>supporting fact*</b> from the video (shot or scene) before creating QA sets. Then, create a simple QA pairs. <br>(e.g. Q: Who is drinking water? A: Monica is drinking water) <br><b>supporting fact*</b>: A triplet form of {subject-relationship-object} such as {Monica-drink-water}</li>
                        <li class="list-group-item"><b>Your question must start with 5W1H</b>.(Who, Where and What)</li>
                        <li class="list-group-item">Your question and answers <b>must be a complete sentence with correct grammar</b>. </li>
                        <li class="list-group-item">The questions you <b>write must be answered within a video clip</b>.</li>
                        <li class="list-group-item">Please avoid completely unrelated questions</b>. ex) How old is the earth? (X)</li>
                        <li class="list-group-item">Please <b>avoid vague terms</b>. Phrase the question in such a way that there is only one possible answer to the question.</li>
                        <li class="list-group-item">Please enter <b>case sensitive</b>.<br>ex) WHY IS ROSS UPSET? (X)<br>ex) Why is Ross upset? (O)</li>
                        <li class="list-group-item">Please <b>don't use personal pronouns like "they, he, she, …"</b>. Use person's name instead.</li>
                        <li class="list-group-item">Please use only the names specified in the character guide, such as Maru, Eungi and etc.</li>
                        <li class="list-group-item">Please write fake answers as similar as the length of true answer that you created.</li>
                        <li class="list-group-item">Your question should have a difference between right and wrong answers according to the relevant question word.</li>
                    </ul>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="QA2GuideDiv" aria-labelledby="QA2Tab">
                    <h3>Shot Q&A (Level 2) 가이드라인</h3>
                    <ul class="list-group list-group-flush" style="font-size: 1.2em;">
                        <li class="list-group-item">At this level, consider <b>multiple supporting facts*</b> from the video (shot or scene) and create a QA sets. <br>See below for more information:<br><b>Multiple supporting facts*</b>: If the two supporting facts selected from the video are {Phoebe-in-kitchen} and {Phoebe-grab-tissue}, then a QA set of {Q: “Where does Phoebe grab the tissue?” A: “Phoebe grab the tissue in the kitchen.”} can be created.</li>
                        <li class="list-group-item"><b>Your question must start with 5W1H</b>.(Who, Where and What)</li>
                        <li class="list-group-item">Your question and answers <b>must be a complete sentence with correct grammar</b>. </li>
                        <li class="list-group-item">The questions you <b>write must be answered within a video clip</b>.</li>
                        <li class="list-group-item">Please avoid completely unrelated questions</b>. ex) How old is the earth? (X)</li>
                        <li class="list-group-item">Please <b>avoid vague terms</b>. Phrase the question in such a way that there is only one possible answer to the question.</li>
                        <li class="list-group-item">Please enter <b>case sensitive</b>.<br>ex) WHY IS ROSS UPSET? (X)<br>ex) Why is Ross upset? (O)</li>
                        <li class="list-group-item">Please <b>don't use personal pronouns like "they, he, she, …"</b>. Use person's name instead.</li>
                        <li class="list-group-item">Please use only the names specified in the character guide, such as Maru, Eungi and etc.</li>
                        <li class="list-group-item">Please write fake answers as similar as the length of true answer that you created.</li>
                        <li class="list-group-item">Please write three different words more than question and answers of Level 1.</li>
                        <li class="list-group-item">Your question should have a difference between right and wrong answers according to the relevant question word.</li>
                    </ul>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="QAKBGuideDiv" aria-labelledby="QAKBTab">
                    <h3>Shot Q&A (Level KB) 가이드라인</h3>
                    <ul class="list-group list-group-flush" style="font-size: 1.2em;">
                        <li class="list-group-item">Please consider one <b>supporting fact*</b> from the video (shot or scene) before creating QA sets.<br>Then, create a simple QA pairs.<br>
                        (e.g. Q: Where is the smoke from? A: The smoke is from the cigarette)<br>
                            <b>supporting fact*</b>: A triplet form of {subject-relationship-object} such as {smoke-is from-cigarette}</li>
                        <li class="list-group-item">Please write questions and answers that can be inferred from common-sense knowledge<br>(synonym, relatedTo, atlocation, usedfor, isa, hasproperty) related to visual objects within a video clip.<br>(e.g. Q: Where does whales live? A: Whales live in the ocean -> whale-atlocation-ocean,        Q: What is the guitar used for? A: Guitar is used for making music -> guitar-usedfor-music)</li>
                        <li class="list-group-item">The scope of common-sense does not include the implicit cause, reason, meaning, etc., and is limited to the range that can be intuitively recognized. (e.g. Q: What is the disease caused by cigarette? (X))</li>
                        <li class="list-group-item"><b>Your question must start with 5W1H.</b>(Who, Where, What and How)</li>
                        <li class="list-group-item">Your question and answers <b>must be a complete sentence with correct grammar.</b></li>
                        <li class="list-group-item">Please <b>avoid vague terms.</b> Phrase the question in such a way that there is only one possible answer to the question.</li>
                        <li class="list-group-item">Please enter <b>case sensitive.</b><br>ex) WHY IS ROSS UPSET? (X)<br>ex) Why is Ross upset? (O)</li>
                        <li class="list-group-item">Please <b>don't use personal pronouns like "they, he, she, …".</b> Use person's name instead.</li>
                        <li class="list-group-item">Please use only the names specified in the character guide, such as Maru, Eungi and etc.</li>
                        <li class="list-group-item">Please write fake answers as similar as the length of true answer that you created.</li>
                        <li class="list-group-item">Your question should have a difference between right and wrong answers according to the relevant question word.</li>
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