<%--
  Created by IntelliJ IDEA.
  User: jeunghakmoon
  Date: 26/11/2018
  Time: 8:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../includes/taglib.jsp" %>
<script>
      /*
      --- 시각정보 통계 조회 ----
      getStats('viewInfo','place');
      getStats('viewInfo','behavior');
      getStats('viewInfo','predicate');
      getStats('viewInfo','emotion');
      getStats('viewInfo','person');
      getStats('viewInfo','object');
      getStats('viewInfo','related object');
      getStats('qaInfo','qaInterrogative');
      getStats('qaInfo','qaTotal');
      * */

    function getStats(statstype,statsitem) {
        getStatsData(statstype,statsitem,'');
    }
    function statsWorkerChange(statstype,statsitem) {
        var statstype = $("#statstype").val();
        var statsitem = $("#statsitem").val();
        var workerId = $("#workerId").val();
        getStatsData(statstype,statsitem,workerId);
    }
    function getStatsData(statstype,statsitem,workerId) {
        $('.table tbody tr').removeClass('ui-selected');
        $("#btn_"+statsitem.replace(" ","")).addClass('ui-selected');
          var $div='';
          var url = "";
          if (statstype != "" && statstype == "viewInfo"){
              url = '<c:url value="/stats/getStatsViewInfoList"/>';
          }else{
              url = '<c:url value="/stats/getStatsQaInfoList"/>';
          }
          //alert($("#workerId").val());
          $.ajax({
              url: url,
              type: 'POST',
              data: {'idx': <c:out value="${idx}"/>,'statstype': statstype, 'statsitem': statsitem,'workerId':workerId},
              async: false,
              dataType: 'html',
              // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
              success: function (html) {
                  $div = html;
                  $("#statsData").html($div);
              },
              error: function (xhr, ajaxOptions, thrownError) {
                  MSG.alert("getStats </br> status:"+xhr.status+"</br> message:"+xhr.responseText);
              }
          });
      }
    var param = {};
    param.idx = ${idx};
</script>

