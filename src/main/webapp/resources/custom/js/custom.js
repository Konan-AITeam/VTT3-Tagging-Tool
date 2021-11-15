
function toast(heading, text, icon, hideAfter) {
    $.toast({
        heading: heading,
        text: text,
        icon: icon,
        showHideTransition: "slide",
        hideAfter: hideAfter
    });
}


function returnmsg(param) {
    if (param == "cancel") {
        return "취소";
    } else if (param == "cancel.msg") {
        return "취소 되었습니다";
    } else if (param == "approve") {
        return "승인";
    } else if (param == "ok") {
        return "확인";
    } else if (param == "add") {
        return "등록";
    } else if (param == "caution") {
        return "주의";
    } else if (param == "error") {
        return "오류";
    }
}

var MSG = {
    alert: function (text, callback) {
        bootbox.alert({
            title:'확인',
            message: text,
            callback: function () {
                if(callback) {
                    callback.apply();
                }
            }
        })
    },
    confirm: function (text, callback) {
        bootbox.confirm({
            title:'확인',
            message: text,
            buttons: {
                confirm: {
                    label: '확인',
                    className: 'btn-success'
                },
                cancel: {
                    label: '취소',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                if(result && callback) {
                    callback.apply();
                }
            }
        });
    }
};



var myPlayer1 = null;
var player = {
    init: function(id, type, streamUrl, imageUrl) {
        if (myPlayer1 != null) {
            try {
                myPlayer1.dispose();
            } catch (e){

            }
        }
        var jsplayer;
        try {
                var options = { controlBar: { volumePanel: {inline: false} } };
                jsplayer = videojs(id, options, function onPlayerReady() {
                myPlayer1 = this;
                if(type == 'video') {
                    myPlayer1.src({ type : "video/mp4", src : streamUrl });
                    $("#" + id + " video").attr("poster", imageUrl);
                }
                if(type == 'audio') {
                    myPlayer1.src({ type : "audio/mp3", src : streamUrl });
                    $("#" + id + " audio").attr("poster", imageUrl);
                }
                    var tracks = jsplayer.textTracks();
                    for (var i = 0; i < tracks.length; i++) {
                        var track = tracks[i];
                        // Find the English captions track and mark it as "showing".
                        if (track.kind === 'subtitles') {
                            track.mode = 'showing';
                        }
                    }
            });

            jsplayer.on('timeupdate', function(){
                // var currentTime = $("#" + id + " .vjs-duration").text(time2Code("video", jsplayer.currentTime(), 29.97));
                // $("#currentTime").text(currentTime.text());
                var nowsec = jsplayer.currentTime();
                $("#currentTime").text(nowsec);
                $("#currentTime").val(nowsec);
            });


        } catch(e) {};
        return jsplayer;
    }
}

var video = {
    setup: function (id, streamUrl ) {
        if (myPlayer1 != null) {
            try {
                myPlayer1.dispose();
            } catch (e){

            }
        }
        try {
            var jsplayer = videojs(id, '',function onPlayerReady() {
                myPlayer1 = this;
                techOrder : ["flash", "other supported tech"];
                myPlayer1.src({type: "video/mp4", src: streamUrl });
            });

            // jsplayer.on('timeupdate', function(){
            //     var currentTime = $("#" + id + " .vjs-duration").text(time2Code("video", jsplayer.currentTime(), 29.97));
            //     var durationTime = $("#" + id + " .vjs-remaining-time").text(time2Code("video", jsplayer.duration(), 29.97));
            //     $("#" + id + " .vjs-remaining-time").text(currentTime.text().replace("Current Time ", "") + " / " + durationTime.text().replace("Duration Time ", ""));
            // });

        } catch(e) {};
    },
    stop: function (id) {
        try {
            myPlayer1.pause();
        } catch (e) {
        }
    },
    seek: function (startframeindex) {
        var starttimesec = code2frame(startframeindex, 29.97);

        try {
            myPlayer1.currentTime(starttimesec);
        } catch (e) {
            console.log("@error_while_seek" + e.message);
        }
    }
};

var audio = {
    setup: function (id, streamUrl ) {
        if (myPlayer1 != null) {
            try {
                myPlayer1.dispose();
            } catch (e){

            }
        }
        try {
            var jsplayer = videojs(id).ready(function () {
                myPlayer1 = this;
                techOrder : ["flash", "other supported tech"];
                myPlayer1.src({type: "audio/mp3", src: streamUrl });
                // myPlayer1.controlBar.show();
                // myPlayer1.load();
                // myPlayer1.play();
            });
            var type = "audio";
            jsplayer.on('timeupdate', function(){
                var currentTime = $("#" + id + " .vjs-current-time").text(time2Code(type, jsplayer.currentTime(), 999));
                $("#" + id + " .vjs-duration").text(time2Code(type, jsplayer.duration(), 999));
                $("#" + id + " .vjs-remaining-time").text(currentTime.text());
            });

        } catch(e) {};
    },
    stop: function () {
        try {
            myPlayer1.pause();
        } catch (e) {
        }
    }
};

var calendar = {
    init: function (target_body) {
        $("input[icon_type][icon_type='calendar']").datepicker({
            dateFormat: 'yy.mm.dd',
            showOn: "button",
            buttonText: "날짜선택",
            buttonImage: "resources/css/jquery/calendar.gif",
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true,
            showButtonPanel: true,
            currentText: $.i18n.prop("calendar.today"),
            closeText: $.i18n.prop("menu.close"),
            prevText: $.i18n.prop("calendar.month.previous"),
            nextText: $.i18n.prop("calendar.month.next"),
            monthNamesShort: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
            dayNamesMin: [$.i18n.prop("calendar.day.sunday"), $.i18n.prop("calendar.day.monday"), $.i18n.prop("calendar.day.tuesday"), $.i18n.prop("calendar.day.wednesday"), $.i18n.prop("calendar.day.thursday"), $.i18n.prop("calendar.day.friday"), $.i18n.prop("calendar.day.saturday")],
            targetBody: target_body
        });
    },

    compareDate: function (start, end, split) {
        if (start && end) {
            var start_arr = start.split(split);
            var start_date = new Date(start_arr[0], start_arr[1] - 1, start_arr[2], 0, 0, 0);
            var end_arr = end.split(split);
            var end_date = new Date(end_arr[0], end_arr[1] - 1, end_arr[2], 23, 59, 59);
            if (start_date.getTime() > end_date.getTime()) {
                return false;
            }
        }
        return true;
    },
    betweenDay: function (start, end, split) {
        if (start && end) {
            var start_arr = start.split(split);
            var start_date = new Date(start_arr[0], start_arr[1] - 1, start_arr[2], 0, 0, 0);
            var end_arr = end.split(split);
            var end_date = new Date(end_arr[0], end_arr[1] - 1, end_arr[2], 0, 0, 0);

            return (end_date.getTime() - start_date.getTime()) / 1000 / 60 / 60 / 24;
        }
        return -1;
    },
    convertDateToString: function (date) {
        return date.getFullYear() + "." + this.fillBlanks(parseInt(date.getMonth() + 1)) + "." + this.fillBlanks(date.getDate());
    },

    fillBlanks: function (number) {
        if (number > 0 && number < 10)
            return "0" + number;
        else
            return number;
    }
};


function time2Code(type, time, fps) {
    var h = Math.floor(time/3600)%24;
    var m = Math.floor(time/60)%60;
    var s = Math.floor(time%60);
    var f = Math.floor(((time%1)*fps).toFixed(3));
    if(type == "audio"){
        return (h<10?"0"+h:h) +":"+(m<10?

                "0"+m:m)+":"+(s<10?"0"+s:s) +"."+(f<10?"0"+f:f);
    }else{
        return (h<10?"0"+h:h)+":"+(m<10?"0"+m:m)+":"+(s<10?"0"+s:s) +";"+(f<10?"0"+f:f);
    }
}

function code2frame( code, fps ) {
    var t = code.replace(";", ":").split(":");
    var h = parseInt(t[0],10);
    var m = parseInt(t[1],10);
    var s = parseInt(t[2],10);
    var f = parseInt(t[3],10);
    return (h*3600)+(m*60)+s+(f/fps);
}

function code2time( code ) {
    var t = code.split(":");
    var h = parseInt(t[0],10);
    var m = parseInt(t[1],10);
    var s = parseFloat(t[2]);
    return (h*3600)+(m*60)+s;
}

function paginationTotalPage(itemTotal, limit) {
    var total_page = 0;
    if (itemTotal > 0 && limit > 0) {
        if ((itemTotal % limit) == 0) {
            total_page = (itemTotal / limit);
        } else {
            total_page = ( itemTotal / limit) + 1;
        }
    } else {
        total_page = 0;
    }
    return total_page;
}

function close() {
    $.toast().reset('all');
}

$(document).ready(function() {
    $("#tree_parent").height($(window).height());
});

$(window).resize(function() {
    $("#tree_parent").height($(window).height());
});


/* 플레이어 단축키 */
var _videoframerate = 29.97;
function proceedHotkey($T, rate) {
    if(typeof $T == "undefined") {
        $T = $(document);
    }
    if(rate=='undefined'){
        rate = _videoframerate;
    }
    $T.bind('keydown.alt_comma',function (e) {  /* alt + < = 1초 전 */
        var curTime = myPlayer1.currentTime() - 1;
        if (myPlayer1.currentTime() <= 1) {
            curTime = 0;
        }
        myPlayer1.currentTime(curTime);
        return false;
    });
    $T.bind('keydown.alt_dot',function (e) {  /* alt + > = 1초 후  */
        myPlayer1.currentTime(myPlayer1.currentTime() + 1);
        return false;
    });
    $T.bind('keydown.ctrl_comma',function (e) {  /* ctrl + < = 5초 전 */
        var curTime = myPlayer1.currentTime() - 5;
        if (myPlayer1.currentTime() <= 5) {
            curTime = 0;
        }
        myPlayer1.currentTime(curTime);
        return false;
    });
    $T.bind('keydown.ctrl_dot',function (e) {  /* ctrl + > = 5초 후  */
        myPlayer1.currentTime(myPlayer1.currentTime() + 5);
        return false;
    });


    $T.bind('keydown.alt_pageup',function (e){  /* alt + page up = 배속재생 빠르게 */
        customPlaybackRate("up");
        return false;
    });
    $T.bind('keydown.alt_pagedown',function (e){  /* alt + page down = 배속재생 느리게 */
        customPlaybackRate("down");
        return false;
    });
    $T.bind('keydown.space',function (e){  /* Space  = 일시정지/재생 */
        if(myPlayer1.paused()) {
            myPlayer1.play();
        } else {
            myPlayer1.pause();
        }
        return false;
    });
    $T.bind('keydown.down',function (e){ /* Down Arrow = 볼륨다운 */
        myPlayer1.volume(myPlayer1.volume() - 0.1);
        return false;
    });
    $T.bind('keydown.up',function (e){  /* Up Arrow = 볼륨업 */
        myPlayer1.volume(myPlayer1.volume() + 0.1);
        return false;
    });
}

/* markIn */
function markIn(rate) {
    try {
        var startframeindex = parseInt(myPlayer1.currentTime() * rate);
        if(startframeindex > max) {
            MSG.toast("","시작범위가 너무 큽니다. 다른 샷을 선택하세요");
            return false;
        }
        if(startframeindex < min) {
            MSG.toast("","시작범위가 너무 작습니다. 다른 샷을 선택하세요");
            return false;
        }
    } catch (e) {}
}
/* 배속재생 */
function customPlaybackRate(rate) {
    var currentRate = $("#videojs .vjs-playback-rate li.vjs-menu-item.vjs-selected");
    if(rate == 'up') {
        if(currentRate.prev("li").length > 0) {
            var prevRate = currentRate.prev("li");
            currentRate.attr("aria-selected", false).removeClass("vjs-selected");
            prevRate.attr("aria-selected", true).addClass("vjs-selected");
            $(".vjs-playback-rate-value").text(prevRate.text());
            myPlayer1.playbackRate(prevRate.text().replace(/[x]/g, ''));
        }
    }

    if(rate == 'down') {
        if(currentRate.next("li").length > 0) {
            var nextRate = currentRate.next("li");
            currentRate.attr("aria-selected", false).removeClass("vjs-selected");
            nextRate.attr("aria-selected", true).addClass("vjs-selected");
            $(".vjs-playback-rate-value").text(nextRate.text());
            myPlayer1.playbackRate(nextRate.text().replace(/[x]/g, ''));
        }
    }
}


function help_hotkey() {
    $.ajax({
        url: _home+"section/video/hotkey",
        async: false,
        type: "GET",
        dataType: "html",
        success: function (response) {
            $("#videoHotkeyModal .modal-content").html(response);
            $('#videoHotkeyModal').modal();
        },
        error: function(xhr, textStatus, error) {
            var msg = "오류가 발생했습니다(code:" + xhr.status + ")\n";
            msg += xhr.responseText;
            MSG.error(msg);
            return false;
        }
    });
}
function custModalPopup(url,objid) {
    $.ajax({
        url: _home+url,
        async: false,
        type: "GET",
        dataType: "html",
        success: function (response) {
            $("#"+objid+" .modal-content").html(response);
            $("#"+objid).modal();
        },
        error: function(xhr, textStatus, error) {
            var msg = "오류가 발생했습니다(code:" + xhr.status + ")\n";
            msg += xhr.responseText;
            MSG.error(msg);
            return false;
        }
    });
}