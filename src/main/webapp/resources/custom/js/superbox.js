! function(a) {
    var shoid_befor;
    var sel_item;
    a.fn.SuperBox = function(b) {
        var c = a('<div class="superbox-show"></div>'),
            g = a('<div class="superbox_image" ></div>'),
            i = a('<div class="superbox_image_div" ></div>'),
            h = a('<img src="" class="img-responsive">'),
            d = a('<div id="imgInfoBox" class="superbox-imageinfo inline-block"> <textarea class="shot_description" onkeyup="setTextShotDescription($(this))"></textarea> </div>'),
            e = a('');
        c.append(g.append(i.append(h))).append(d).append(e);
        a(".superbox-imageinfo");
        return this.each(function() {
            a(".superbox-list").click(function() {
                $this= a(this);

                var shoid = $this.find("input[name=shotid]").val();

                if($(".superbox-show .superbox_image_div").svg('get') !== undefined) {
                    $(".superbox-show .superbox_image_div").svg('get').clear();
                }

                if($(".superbox-list").hasClass("active")){ // close
                    if(shoid_befor == shoid || undefined == shoid){
                        $this.find("input[name=object]").val($(".shot_description").val());
                    }else{
                        sel_item.find("input[name=object]").val($(".shot_description").val());
                        $(".shot_description").val($this.find("input[name=object]").val());
                    }
                }

                sel_item = $this;
                shoid_befor = shoid;

                var b = $this.find(".superbox-img"),
                    e = b.data("img"),
                    f = b.attr("alt") || "No description",
                    g = e;

                h.attr("src", e), a(".superbox-list").removeClass("active"), $this.addClass("active"), d.find("em").text(g), d.find(".superbox-img-description").text(f), 0 == a(".superbox-current-img").css("opacity") && a(".superbox-current-img").animate({
                    "opacity": 1
                }), a(this).next().hasClass("superbox-show") ? (c.is(":visible") && a(".superbox-list").removeClass("active"), c.toggle()) : (c.insertAfter(this).css("display", "block"), $this.addClass("active")), a("html, body").animate({
                    "scrollTop": c.position().top - b.width()
                }, "medium");

                if($(".shot_description") != undefined){
                    var tmpData = JSON.parse(sel_item.find("input[name=object]").val());
                    var formattedData = JSON.stringify(tmpData, null, '\t');
                    $(".shot_description").val(formattedData);

                    var detectType = $("input:radio[name=btn_show_target]:checked").val();
                    var detectLocation = "";
                    var detectAccuracy = "";
                    var detectObject = "";

                    if(detectType == "object") {
                        detectLocation = sel_item.find("input[name=oLocation]").val();
                        detectAccuracy = sel_item.find("input[name=oAccuracy]").val();
                        detectObject = sel_item.find("input[name=oObject]").val();

                    } else if(detectType == "face") {
                        detectLocation = sel_item.find("input[name=fLocation]").val();
                        detectAccuracy = sel_item.find("input[name=fAccuracy]").val();
                        detectObject = sel_item.find("input[name=fObject]").val();
                    } else if(detectType == "place") {
                        detectLocation = sel_item.find("input[name=pLocation]").val();
                        detectAccuracy = sel_item.find("input[name=pAccuracy]").val();
                        detectObject = sel_item.find("input[name=pObject]").val();
                    }
                    var arrLocation = detectLocation.split(',');
                    var arrAccuracy = detectAccuracy.split(',');
                    var arrObject = detectObject.split(',');

                    var target = $(".superbox-show .superbox_image_div");
                    var colors1 = ["#F5B7B1", "#D7BDE2", "#A9CCE3", "#A3E4D7", "#A9DFBF","#F9E79F", "#F5CBA7" ];
                    var colors2 = ["#CD6155", "#9B59B6", "#2980B9", "#1ABC9C", "#27AE60","#F1C40F", "#E67E22" ];

                    var r = 680.0 / shot_width;

                    var keyword = $("#keyword").val();
                    keyword = keyword.toLowerCase();

                    target.svg();
                    var svg = target.svg("get");
                    svg.clear();


                    if(detectType == "place") {
                        var count = 0;
                        $.each(arrObject, function(index, val) {
                            var found = true;
                            if (keyword != null && keyword.length > 0) {
                                if(arrObject[index].toLowerCase().indexOf(keyword) > -1) {
                                    found = true;
                                } else {
                                    found = false;
                                }
                            }
                            if(val != null && val != '' && val.length != 0 && found) {
                                var color = colors2[index % colors2.length];
                                svg.text(5.0, 20 + (count * 20.0), val + " [" + arrAccuracy[index] + "]",{id: 'Text', fontFamily: 'Nanum Gothic', fontSize: 20, fill: color, 'font-weight': 'bold', textShadow: '2px 2px 2px #000' });
                                count++;
                            }
                        });

                    } else {

                        $.each(arrLocation, function(index, val) {
                            var found = true;
                            if (keyword != null && keyword.length > 0) {
                                if(arrObject[index].toLowerCase().indexOf(keyword) > -1) {
                                    found = true;
                                } else {
                                    found = false;
                                }
                            }
                            if(val != null && val != '' && val.length != 0 && found) {
                                console.log(val);
                                var arr  = val.split(' ');
                                var color1 = colors1[index % colors1.length];
                                var color2 = colors2[index % colors2.length];
                                svg.rect(parseFloat(arr[0]) * r, parseFloat(arr[1]) * r, parseFloat(arr[2]) * r, parseFloat(arr[3]) * r , { fill: 'none', stroke: color1, strokeWidth: 2 });
                                svg.text(parseFloat(arr[0]) * r, parseFloat(arr[1]) * r + 12, arrObject[index] + " [" + arrAccuracy[index] + "]",{id: 'Text', fontFamily: 'Nanum Gothic', fontSize: 20, fill: color2, 'font-weight': 'bold' });
                            }

                            $(".superbox-show .superbox_image_div svg").css("overflow", "visible");
                        });
                    }
                }
            }), a(".superbox").on("click", ".superbox-close", function() {
                if($(".shot_description").val() != $this.find("input[name=object]").val()){
                    var result = confirm("입력된 값을 취소 하시겠습니까?");
                    if(!result){
                        return;
                    }
                }
                a(".superbox-list").removeClass("active"), a(".superbox-current-img").animate({
                    "opacity": 0
                }, 200, function() {
                    a(".superbox-show").slideUp()
                })
            })
        })
    }
}(jQuery);