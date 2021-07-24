/**
 *
 */
$(document).ready(function () {

    $('#typeRecurrentRd').click(function (e) {
        if (this.checked) {
            $('#dureeDiv').css("display", "block");
        }
    });

    $('#typeRecurrentRd2').click(function (e) {
        if (this.checked) {
            $('#dureeDiv').css("display", "none");
        }
    });

    $("select#typeBon").change(
        function (e) {
            e.getDefaultPrevented;
            if ($(this).val() === "NN") {
                var data = "codeMembre=" + $("#codeMembre").val() + "&typeBon="
                    + $("#typeBon").val();
                console.log("Données : " + data);
                $("select#catBon").load('typeba', data);
            }
        });
    $("input#montantBaSous").blur(
        function (e) {
            e.getDefaultPrevented;
            var mont = parseFloat($(this).val());
            if (mont > 0) {
                var dataToSend = "catBonConso=" + $("input[name='catBonConso']:checked").val()
                + "&typeRecurrent=" + $("input[name='typeRecurrent']:checked").val()
                + "&duree=" + $("input[name='duree']:checked").val()
                + "&montant=" + $("input#montantBaSous").val();
                console.log("Données : " + dataToSend);
                $.getJSON('calBc', dataToSend, function success(data) {
                    $("input#montBonconso").val(data);
                });
            }
        });

    $("input#montantSous").blur(
        function (e) {
            e.getDefaultPrevented;
            var mont = parseFloat($(this).val());
            if (mont > 0) {
                var dataToSend = "catBonConso=" + $("input[name='catBonConso']:checked").val()
                + "&typeRecurrent=" + $("input[name='typeRecurrent']:checked").val()
                + "&duree=" + $("input[name='duree']:checked").val()
                + "&montant=" + $("input#montantSous").val();
                console.log("Données : " + dataToSend);
                $.getJSON('calBc', dataToSend, function success(data) {
                    $("input#montBonconso").val(data);
                });
            }
        });

    $("input#codeBonBa").blur(
        function (e) {
            e.getDefaultPrevented;
            if ($(this).val() !== "") {
                var dataToSend = "codeBon=" + $("#codeBonBa").val();
                console.log("Données : " + dataToSend);
                $.getJSON('getbon', dataToSend, function success(data) {
                    $("input#montantBaSous").val(data);
                    dataToSend = "catBonConso=" + $("input[name='catBonConso']:checked").val()
                    + "&typeRecurrent=" + $("input[name='typeRecurrent']:checked").val()
                    + "&duree=" + $("input[name='duree']:checked").val()
                    + "&montant=" + $("input#montantBaSous").val();
                    $.getJSON('calBc', dataToSend, function success(data) {
                        $("input#montBonconso").val(data);
                    });
                });

            }
        });
    $("input#codeBon").blur(
        function (e) {
            e.getDefaultPrevented;
            if ($(this).val() !== "") {
                var dataToSend = "codeBon=" + $("input#codeBon").val();
                console.log("Données : " + dataToSend);
                $.getJSON('bonNeutre', dataToSend, function success(data) {
                    $("input#montantSous").val(data);
                    dataToSend = "catBonConso=" + $("input[name='catBonConso']:checked").val()
                        + "&typeRecurrent=" + $("input[name='typeRecurrent']:checked").val()
                        + "&duree=" + $("input[name='duree']:checked").val()
                        + "&montant=" + $("input#montantSous").val();
                    $.getJSON('calBc', dataToSend, function success(data) {
                        $("input#montBonconso").val(data);
                    });
                });

            }
        });
});