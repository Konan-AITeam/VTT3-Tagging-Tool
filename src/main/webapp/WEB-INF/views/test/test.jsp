<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2018-07-12
  Time: 오후 1:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../includes/taglib.jsp" %>
<c:import url="../includes/header.jsp"/>
<c:import url="../includes/script.jsp"/>
<script type="text/javascript">
    $(document).ready(function () {
        $('img#example').selectAreas({
            minSize: [10, 10],
            onChanged: debugQtyAreas,
            width: 500,
            areas: [
                {
                    x: 10,
                    y: 20,
                    width: 60,
                    height: 100,
                }
            ]
        });
        $('#btnView').click(function () {
            var areas = $('img#example').selectAreas('areas');
            displayAreas(areas);
        });
        $('#btnViewRel').click(function () {
            var areas = $('img#example').selectAreas('relativeAreas');
            displayAreas(areas);
        });
        $('#btnReset').click(function () {
            output("reset")
            $('img#example').selectAreas('reset');
        });
        $('#btnDestroy').click(function () {
            $('img#example').selectAreas('destroy');

            output("destroyed")
            $('.actionOn').attr("disabled", "disabled");
            $('.actionOff').removeAttr("disabled")
        });
        $('#btnCreate').attr("disabled", "disabled").click(function () {
            $('img#example').selectAreas({
                minSize: [10, 10],
                onChanged : debugQtyAreas,
                width: 500,
            });

            output("created")
            $('.actionOff').attr("disabled", "disabled");
            $('.actionOn').removeAttr("disabled")
        });
        $('#btnNew').click(function () {
            var areaOptions = {
                x: Math.floor((Math.random() * 200)),
                y: Math.floor((Math.random() * 200)),
                width: Math.floor((Math.random() * 100)) + 50,
                height: Math.floor((Math.random() * 100)) + 20,
            };
            output("Add a new area: " + areaToString(areaOptions))
            $('img#example').selectAreas('add', areaOptions);
        });
        $('#btnNews').click(function () {
            var areaOption1 = {
                x: Math.floor((Math.random() * 200)),
                y: Math.floor((Math.random() * 200)),
                width: Math.floor((Math.random() * 100)) + 50,
                height: Math.floor((Math.random() * 100)) + 20,
            }, areaOption2 = {
                x: areaOption1.x + areaOption1.width + 10,
                y: areaOption1.y + areaOption1.height - 20,
                width: 50,
                height: 20,
            };
            output("Add a new area: " + areaToString(areaOption1) + " and " + areaToString(areaOption2))
            $('img#example').selectAreas('add', [areaOption1, areaOption2]);
        });
    });

    var selectionExists;

    function areaToString (area) {
        return (typeof area.id === "undefined" ? "" : (area.id + ": ")) + area.x + ':' + area.y  + ' ' + area.width + 'x' + area.height + '<br />'
    }

    function output (text) {
        $('#output').html(text);
    }

    // Log the quantity of selections
    function debugQtyAreas (event, id, areas) {
        console.log(areas.length + " areas", arguments);
    };

    // Display areas coordinates in a div
    function displayAreas (areas) {
        var text = "";
        $.each(areas, function (id, area) {
            text += areaToString(area);
        });
        output(text);
    };

</script>

<div class="right_col" role="main">
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <form>
                <input type="file" />
            </form>
        </div>
    </div>
</div>


<c:import url="../includes/footer.jsp"/>