<%@include file="../includes/taglib.jsp" %>
<c:forEach var="shot" items="${listResponse.list}" varStatus="i">
    <c:set var="detectLocation1" value=""/>
    <c:set var="detectAccuracy1" value=""/>
    <c:set var="detectObject1" value=""/>
    <c:set var="detectLocation2" value=""/>
    <c:set var="detectAccuracy2" value=""/>
    <c:set var="detectObject2" value=""/>
    <c:set var="detectLocation3" value=""/>
    <c:set var="detectAccuracy3" value=""/>
    <c:set var="detectObject3" value=""/>
    <c:set var="detectInfo" value="${shot.detect.objectData}"/>
    <c:forEach var="detect" items="${detectInfo}" varStatus="j">
        <c:set var="detectLocation1" value="${j.first ? '' : detectLocation1}${j.first ? '' : ','}${detect.location}" />
        <c:set var="detectAccuracy1" value="${j.first ? '' : detectAccuracy1}${j.first ? '' : ','}${detect.info.accuracy}" />
        <c:set var="detectObject1" value="${j.first ? '' : detectObject1}${j.first ? '' : ','}${detect.info.objectName}" />
    </c:forEach>
    <c:set var="detectInfo" value="${shot.detect.placeData}"/>
    <c:forEach var="detect" items="${detectInfo}" varStatus="j">
        <c:set var="detectLocation2" value="${j.first ? '' : detectLocation2}${j.first ? '' : ','}${detect.location}" />
        <c:set var="detectAccuracy2" value="${j.first ? '' : detectAccuracy2}${j.first ? '' : ','}${detect.info.accuracy}" />
        <c:set var="detectObject2" value="${j.first ? '' : detectObject2}${j.first ? '' : ','}${detect.info.objectName}" />
    </c:forEach>
    <c:set var="detectInfo" value="${shot.detect.faceData}"/>
    <c:forEach var="detect" items="${detectInfo}" varStatus="j">
        <c:set var="detectLocation3" value="${j.first ? '' : detectLocation3}${j.first ? '' : ','}${detect.location}" />
        <c:set var="detectAccuracy3" value="${j.first ? '' : detectAccuracy3}${j.first ? '' : ','}${detect.info.accuracy}" />
        <c:set var="detectObject3" value="${j.first ? '' : detectObject3}${j.first ? '' : ','}${detect.info.objectName}" />
    </c:forEach>
detectObject["${shot.starttimecode}"] = ["${ detectLocation1 }","${ detectAccuracy1 }","${ detectObject1 }"];
detectPlace["${shot.starttimecode}"] = ["${ detectLocation2 }","${ detectAccuracy2 }","${ detectObject2 }"];
detectFace["${shot.starttimecode}"] = ["${ detectLocation3 }","${ detectAccuracy3 }","${ detectObject3 }"];
</c:forEach>