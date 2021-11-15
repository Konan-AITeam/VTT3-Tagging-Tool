<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>


                        <c:forEach var="item" items="${ listResponse.list }" varStatus="index" >
                            <tr>
                                <input type="hidden" name="idx" value="${ item.idx }" />
                                <td class="a-center" style="width: 39px;">
                                    <input id="item_${ item.idx }" type="checkbox" class="flat" name="table_records"/>
                                    <label for="item_${ item.idx }"></label>
                                </td>

                                <td class="list-num" style="width: 10%;">
                                        ${ listResponse.total - index.count + 1 }
                                </td>
                                <td style="width: 10%;">${ item.idx }</td>
                                    <%--<td><c:import url="content_status.jsp"><c:param name="status" value="${item.transcodingstatus}"/></c:import></td>--%>
                                <td style="width: 20%;"><c:import url="content_status.jsp"><c:param name="status" value="${item.catalogstatus}"/></c:import></td>
                                <td class="text" >${ fn:replace(item.title, query.keyword, highlight ) }</td>

                            </tr>
                        </c:forEach>
<script>



    var offset = parseInt("${listResponse.offset}");
    var limit = parseInt("${listResponse.limit}");
    var total_count = parseInt("${listResponse.total}");
    var current_page = parseInt((offset > 0 && limit > 0) ?  offset / limit : 0);
    var total_page = paginationTotalPage(total_count, limit);

    var params = {};
    params.offset = offset;
    params.limit = limit;


    $("#dt_basic_info").text("Showing " + offset + " to " + (offset + limit) + " of " + total_count + " entrie");
    $(".pagination").bootpag({
        total: total_page,
        page: current_page + 1,
        maxVisible: 10,
        leaps: true,
        firstLastUse: true,
        first: '<i class="fa fa-angle-double-left" aria-hidden="true"></i>',
        prev: '<i class="fa fa-angle-left" aria-hidden="true"></i>',
        next: '<i class="fa fa-angle-right" aria-hidden="true"></i>',
        last: '<i class="fa fa-angle-double-right" aria-hidden="true"></i>'
    }).on("page", function(event, num) {
        params.offset = (num - 1) * params.limit;
        location.href = "<c:url value="/content" />?" + $.param(params);
    });
    $('.pagination li').addClass('page-item');
    $('.pagination a').addClass('page-link');


    $(".table tbody").selectable({
        filter: 'tr',
        distance: 1
    });

    $(".table tbody tr").on("click", "td:gt(0)", function (e) {
        var $T = $(this).parents("tr");
        var single = true;
        if (e.shiftKey) {
            var pos = $(".table tbody tr").index($T);
            var curr = $(".table tbody tr").index($(".ui-selected"));
            if (pos != curr) {
                $('.table tbody tr').removeClass('ui-selected');
                $('.table tbody tr').slice(Math.min(pos, curr), 1 + Math.max(pos, curr)).addClass('ui-selected');
                single = false;
            }
        }
        if (e.ctrlKey) {
            $T.addClass('ui-selected');
            single = false;
        }
        if (single) {
            $('.table tbody tr').removeClass('ui-selected');
            $T.addClass('ui-selected');
        }
    });

    $(".table tbody tr").on("dblclick", "td:gt(0)", function (e) {
        console.log(".table tbody tr");
        $("#btn_vtt_visual_edit").click();
    });


</script>
