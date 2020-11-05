// 정렬 함수
var fnSortList = function(field, reverse, primer){
    var key = primer ?
        function(x) {return primer(x[field])} :
        function(x) {return x[field]};

    reverse = !reverse ? 1 : -1;

    return function (a, b) {
        return a = key(a), b = key(b), reverse * ((a > b) - (b > a));
    }
};

// 콤보 위젯
$.widget("custom.combobox", {
    _create: function() {
        this.wrapper = $("<span>").addClass("custom-combobox").insertAfter(this.element);
        this.element.hide();
        this._createAutocomplete();
        this._createShowAllButton();
    },

    _createAutocomplete: function() {
        var selected = this.element.children(":selected"), value = selected.val() ? selected.text() : "";
        this.input = $("<input>").css({width: 'calc(100% - 25px'}).appendTo(this.wrapper).val(value).attr("title", "");
        this.input.addClass("custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left");
        this.input.autocomplete({
            delay: 0,
            minLength: 0,
            source: $.proxy(this, "_source"),
            // 한글 항목 커서로 선택시 오류방지
            focus: function(event, ui) {
                return false;
            },
            select: function(event, ui) {
                // 선택했을때 처리가 여기에 옵니다.
                //console.log(event);
                //console.log(ui);
            },
            change: function(event, ui){

            }
        });
        this.input.tooltip({
            classes: {"ui-tooltip": "ui-state-highlight"}
        });
        this._on( this.input, {
            autocompleteselect: function( event, ui ) {
                ui.item.option.selected = true;
                this._trigger( "select", event, {item: ui.item.option});
            },
            autocompletechange: "_removeIfInvalid"
        });
    },
    _createShowAllButton: function() {
        var input = this.input, wasOpen = false;
        var btn_a = $("<a>").attr("tabIndex", -1).attr("title", "모두보기").tooltip().appendTo(this.wrapper);
        btn_a.button({
            icons: {primary: "ui-icon-triangle-1-s"},
            text: false
        });
        btn_a.removeClass( "ui-corner-all" ).addClass("custom-combobox-toggle ui-corner-right")
        btn_a.on("mousedown", function() {
            wasOpen = input.autocomplete("widget").is(":visible");
        });
        btn_a.on("click", function() {
            if(input.attr('readonly') == 'readonly'){
                return;
            }
            else{
                input.trigger( "focus" );
                // 이미 보여지고 있다면 닫습니다.
                if(wasOpen) {return;}
                // 모든 결과 출력을 위해서 빈문자열을 보냅니다.
                input.autocomplete("search", "");
            }

        });
    },
    _source: function(request, response) {
        var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
        response( this.element.children("option").map(function() {
            var text = $(this).text();
            if(this.value && (!request.term || matcher.test(text))) {
                return {label: text, value: text, option: this};
            }
        }));
    },
    _removeIfInvalid: function(event, ui) {
        // 아이템을 선택합니다. 아무것도 하지 않습니다.
        if(ui.item) {return;}

        // 일치하는것을 찾습니다. (대소문자를 구분하지 않습니다.)
        var value = this.input.val(), valueLowerCase = value.toLowerCase(), valid = false;
        this.element.children( "option" ).each(function() {
            if($(this).text().toLowerCase() === valueLowerCase) {
                this.selected = valid = true;
                return false;
            }
        });

        // 일치하는것을 찾으면 아무것도 하지 않습니다.
        if(valid) {return;}

        // 빈값 항목을 삭제합니다.
        this.input.val("").attr("title", value + " 일치하는 항목이 없습니다.").tooltip("open");
        this.element.val("");
        this._delay(function() {
            this.input.tooltip("close").attr("title", "");
        }, 2500);
        this.input.autocomplete("instance").term = "";
    },
    _destroy: function() {
        this.wrapper.remove();
        this.element.show();
    }
});

Array.prototype.compare = function(array) {
    if (!array) {
        return false;
    }
    if (this.length !== array.length) {
        return false;
    }
    for (var i=0,l=this.length;i<l;i++) {
        if (this[i] instanceof Array && array[i] instanceof Array) {
            if (!this[i].compare(array[i])) {
                return false;
            }
        }
        else if (this[i] !== array[i]) {
            return false;
        }
    }
    return true;
};


var pad = {
    lpad: function(str, padLen, padStr){
        if (padStr.length > padLen) {
            console.log("오류 : 채우고자 하는 문자열이 요청 길이보다 큽니다");
            return str;
        }
        str += ""; // 문자로
        padStr += ""; // 문자로
        while (str.length < padLen)
            str = padStr + str;
        str = str.length >= padLen ? str.substring(0, padLen) : str;
        return str;
    },
    rpad: function(str, padLen, padStr){
        if (padStr.length > padLen) {
            console.log("오류 : 채우고자 하는 문자열이 요청 길이보다 큽니다");
            return str + "";
        }
        str += ""; // 문자로
        padStr += ""; // 문자로
        while (str.length < padLen)
            str += padStr;
        str = str.length >= padLen ? str.substring(0, padLen) : str;
        return str;
    }
}