//$(function() {
//    $("#foo").change(function() {
//            var strSel = $("#foo").val().join(",");
//            $("#p1").html(strSel);
//    })
//})

$(function() {
    var nowTemp = new Date();
    var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);

    var dateOfConsultation = $('#dp1').datepicker({
//        onRender: function(date) {
//            return date.valueOf() < now.valueOf() ? 'disabled' : '';
//        }
    }).on('changeDate', function(ev) {
            dateOfConsultation.hide();
            $('#dp1').blur();
        }).data('datepicker');


    $('#dpd1').datepicker('setValue', now);
    $('#dpd2').datepicker('setValue', now);
    var checkin = $('#dpd1').datepicker().on('changeDate', function(ev) {
            if (ev.date.valueOf() > checkout.date.valueOf()) {
                var newDate = new Date(ev.date)
                newDate.setDate(newDate.getDate());
                checkout.setValue(newDate);
            }
            checkin.hide();
            $('#dpd2')[0].focus();
        }).data('datepicker');
    var checkout = $('#dpd2').datepicker()
        .on('changeDate', function(ev) {
            if (ev.date.valueOf() < checkin.date.valueOf()) {
                var newDate = new Date(ev.date)
                newDate.setDate(newDate.getDate());
                checkin.setValue(newDate);
            }
            checkout.hide();
        }).data('datepicker');

});

$(function() {
    var choiceUser = $("#user").val();
    if (choiceUser == "9") {
        $("#otherUserDiv").show();
        $("#otherUser").val("");
    }
    var choiceService = $("#serviceProvided").val();
    if (choiceService == "21") {
        $("#otherServiceDiv").show();
        $("#otherService").val("");
    }
    var choiceUserAffiliation = $("#userAffiliation").val();
    if (choiceUserAffiliation == "15" || choiceUserAffiliation == "16") {
        $("#otherUserAffiliationDiv").show();
        $("#otherUserAffiliation").val("");
    }
    var choiceCourseSponsor = $("#courseSponsor").val();
    if (choiceCourseSponsor == "15") {
        $("#otherCourseSponsorDiv").show();
        $("#otherCourseSponsor").val("");
    }
});

$(function() {
    $("#user").change(function() {
        var choice = $("#user").val();
        //alert(choice)
        if (choice == "9") {
            $("#otherUserDiv").show();
        }
        else {
            $("#otherUserDiv").hide();
            $("#otherUser").val("");
        }
    })
});

$(function() {
    $("#serviceProvided").change(function() {
        var choice = $("#serviceProvided").val();
        if (choice == "21") {
            $("#otherServiceDiv").show();
        }
        else {
            $("#otherServiceDiv").hide();
            $("#otherService").val("");
        }
    })
});

$(function() {
    $("#userAffiliation").change(function() {
        var choice = $("#userAffiliation").val();
        if (choice == "15" || choice == "16") {
            $("#otherUserAffiliationDiv").show();
        }
        else {
            $("#otherUserAffiliationDiv").hide();
            $("#otherUserAffiliation").val("");
        }
    })
});

$(function() {
    $("#courseSponsor").change(function() {
        var choice = $("#courseSponsor").val();
        if (choice == "15") {
            $("#otherCourseSponsorDiv").show();
        }
        else {
            $("#otherCourseSponsorDiv").hide();
            $("#otherCourseSponsor").val("");
        }
    })
});

$(function() {
    $("#resetButton").click(function() {
        $("#otherUserDiv").hide();
        $("#otherUser").val("");
        $("#otherServiceDiv").hide();
        $("#otherService").val("");
        $("#otherUserAffiliationDiv").hide();
        $("#otherUserAffiliation").val("");
        $("#otherCourseSponsorDiv").hide();
        $("#otherCourseSponsor").val("");
    })
});

$(function() {
    $("#ridReportType").change(function() {
        var choice = $("#ridReportType").val();
        $.ajax({
            //url: '${g.createLink(controller: 'RidTransaction', action: 'ajaxChooseType')}',
            url: 'ajaxChooseType',
            type: 'POST',
            dataType: 'json',
            data: {
                typeId: choice
            },
            success: function(data) {
                $.each(data, function(index, itemList) {
                    $('#'+index+' > option').remove();
                    $.each(itemList, function(id, element) {
                        $('#'+index).append($("<option>", {
                            text: element.name
                        }).attr('value', element.id));
                    });
                });
            },
            error: function(request, status, error) {
                alert(error);
            },
            complete: function() {
            }
        });
    })
});
