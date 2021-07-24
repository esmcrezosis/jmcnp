$(document).ready(
    function () {
        // switch element when editing inline
        function aceSwitch(cellvalue, options, cell) {
            setTimeout(function () {
                $(cell).find('input[type=checkbox]').addClass(
                    'ace ace-switch ace-switch-5').after(
                    '<span class="lbl"></span>');
            }, 0);
        }

        function style_search_filters(form) {
            form.find('.delete-rule').val('X');
            form.find('.add-rule').addClass(
                'btn btn-xs btn-primary');
            form.find('.add-group').addClass(
                'btn btn-xs btn-success');
            form.find('.delete-group').addClass(
                'btn btn-xs btn-danger');
        }

        function style_search_form(form) {
            var dialog = form.closest('.ui-jqdialog');
            var buttons = dialog.find('.EditTable')
            buttons.find('.EditButton a[id*="_reset"]')
                .addClass('btn btn-sm btn-info').find(
                '.ui-icon').attr('class',
                'ace-icon fa fa-retweet');
            buttons.find('.EditButton a[id*="_query"]')
                .addClass('btn btn-sm btn-inverse').find(
                '.ui-icon').attr('class',
                'ace-icon fa fa-comment-o');
            buttons.find('.EditButton a[id*="_search"]')
                .addClass('btn btn-sm btn-purple').find(
                '.ui-icon').attr('class',
                'ace-icon fa fa-search');
        }

        // it causes some flicker when reloading or navigating
        // grid
        // it may be possible to have some custom formatter to
        // do this as the grid
        // is being created to prevent this
        // or go back to default browser checkbox styles for the
        // grid
        function styleCheckbox(table) {
            /**
             * $(table).find('input:checkbox').addClass('ace')
             * .wrap('<label />') .after('<span class="lbl
             * align-top" />')
             *
             *
             * $('.ui-jqgrid-labels th[id*="_cb"]:first-child')
             * .find('input.cbox[type=checkbox]').addClass('ace')
             * .wrap('<label />').after('<span class="lbl
             * align-top" />');
             */
        }

        // unlike navButtons icons, action icons in rows seem to
        // be hard-coded
        // you can change them like this in here if you want
        function updateActionIcons(table) {
            /**
             * var replacement = { 'ui-ace-icon fa fa-pencil' :
             * 'ace-icon fa fa-pencil blue', 'ui-ace-icon fa
             * fa-trash-o' : 'ace-icon fa fa-trash-o red',
             * 'ui-icon-disk' : 'ace-icon fa fa-check green',
             * 'ui-icon-cancel' : 'ace-icon fa fa-times red' };
             * $(table).find('.ui-pg-div
             * span.ui-icon').each(function(){ var icon =
             * $(this); var $class =
             * $.trim(icon.attr('class').replace('ui-icon',
             * '')); if($class in replacement)
             * icon.attr('class', 'ui-icon
             * '+replacement[$class]); })
             */
        }

        // replace icons with FontAwesome icons like above
        function updatePagerIcons(table) {
            var replacement = {
                'ui-icon-seek-first': 'ace-icon fa fa-angle-double-left bigger-140',
                'ui-icon-seek-prev': 'ace-icon fa fa-angle-left bigger-140',
                'ui-icon-seek-next': 'ace-icon fa fa-angle-right bigger-140',
                'ui-icon-seek-end': 'ace-icon fa fa-angle-double-right bigger-140'
            }
            $(
                '.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon')
                .each(
                    function () {
                        var icon = $(this);
                        var $class = $.trim(icon.attr(
                            'class').replace(
                            'ui-icon', ''));

                        if ($class in replacement)
                            icon
                                .attr(
                                    'class',
                                    'ui-icon '
                                    + replacement[$class]);
                    });
        }

        function enableTooltips(table) {
            $('.navtable .ui-pg-button').tooltip({
                container: 'body'
            });
            $(table).find('.ui-pg-div').tooltip({
                container: 'body'
            });
        }


        $('#typeCodeBonNeutre').click(function (e) {
            if (this.checked) {
                $('#bnCodeBa').css("display", "block");
            } else {
                $('#bnCodeBa').css("display", "none");
            }
        });

        $("select#typeBon").change(
            function (e) {
                e.getDefaultPrevented;
                if ($(this).val() === "NN") {
                    var data = "codeMembre=" + $("#codeMembre").val()
                        + "&typeBon=" + $("#typeBon").val();
                    console.log("Données : " + data);
                    $("select#catBon").load('typeba', data);
                }
            });

        $("input#montantBaSous").blur(
            function (e) {
                e.getDefaultPrevented;
                var mont = parseFloat($(this).val());
                if (mont > 0) {
                    var dataToSend = "codeMembre="
                        + $("#codeMembreBa").val() + "&typeCredit="
                        + $("#typeCreditBa").val() + "&catBonConso="
                        + $("#catBonConsoBa").val() + "&montant="
                        + mont;
                    console.log("Données : " + dataToSend);
                    $.getJSON('calBc', dataToSend, function success(
                        data) {
                        $("input#montBonconso").val(data);
                    });
                }
            });

        $("input#montantSous").blur(
            function (e) {
                e.getDefaultPrevented;
                var mont = parseFloat($(this).val());
                if (mont > 0) {
                    var dataToSend = "codeMembre="
                        + $("#codeMembre").val() + "&typeCredit="
                        + $("#typeCredit").val() + "&catBonConso="
                        + $("#catBonConso").val() + "&montant="
                        + mont;
                    console.log("Données : " + dataToSend);
                    $.getJSON('calBc', dataToSend, function success(
                        data) {
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
                    $.getJSON('getbon', dataToSend, function success(
                        data) {
                        $("input#montantBaSous").val(data);
                        dataToSend = "codeMembre="
                            + $("#codeMembreBa").val()
                            + "&typeCredit="
                            + $("#typeCreditBa").val()
                            + "&catBonConso="
                            + $("#catBonConsoBa").val() + "&montant="
                            + $("input#montantBaSous").val();
                        $.getJSON('calBc', dataToSend,
                            function success(data) {
                                $("input#montBonconso").val(data);
                            });
                    });

                }
            });

        var opigrid = "#topis";
        var opipager = "#dovis";
        var selArr = [];
        var items = [];
        $(opigrid).jqGrid(
            {
                datatype: 'json',
                mtype: 'GET',
                colNames: ['ID', 'Date', 'Code Membre', 'Montant',
                    'Tranche', 'Echu', 'Solde', 'Nombre'],
                colModel: [{
                    name: 'idTpagcp',
                    index: 'idTpagcp',
                    width: 70,
                    formatter: 'number',
                    hidden: true
                }, {
                    name: 'dateFin',
                    index: 'dateFin',
                    width: 100,
                    formatter: 'date',
                    formatOptions: {
                        newformat: 'dd/mm/yyyy'
                    }
                }, {
                    name: 'codeMembre',
                    index: 'codeMembre',
                    width: 200
                }, {
                    name: 'montGcp',
                    index: 'montGcp',
                    width: 150
                }, {
                    name: 'montTranche',
                    index: 'montTranche',
                    width: 150
                }, {
                    name: 'montEchu',
                    index: 'montEchu',
                    width: 150
                }, {
                    name: 'solde',
                    index: 'solde',
                    width: 150,
                    hidden: true
                }, {
                    name: 'resteNtf',
                    index: 'resteNtf',
                    width: 70
                }],
                viewrecords: true,
                sortname: 'idTpagcp',
                sortorder: 'asc',
                rowNum: 10,
                rowList: [10, 20, 30],
                pager: opipager,
                altRows: true,
                height: 200,
                width: 790,
                multiselect: true,
                multiboxonly: true,
                onSelectRow: function (id, status) {
                    selectionManager(id, status);
                },
                onSelectAll: function (ids, status) {
                    selectionManager(ids, status);
                },
                loadComplete: function () {
                    var table = this;
                    setTimeout(function () {
                        styleCheckbox(table);
                        updateActionIcons(table);
                        updatePagerIcons(table);
                        enableTooltips(table);
                    }, 0);
                }
            });


        $(window).triggerHandler('resize.jqGrid');
        var opi_grid_parent_column = $(opigrid).closest('[class*="widget-main"]');
        $(window).on(
            'resize.jqGrid',
            function () {
                $(opigrid).jqGrid('setGridWidth',
                    opi_grid_parent_column.width());
            });
        // optional: resize on sidebar collapse/expand and container
        // fixed/unfixed
        $(document).on(
            'settings.ace.jqGrid',
            function (ev, event_name, collapsed) {
                if (event_name === 'sidebar_collapsed'
                    || event_name === 'main_container_fixed') {
                    $(opigrid).jqGrid('setGridWidth',
                        opi_grid_parent_column.width());
                }
            });

        $('#btFetchOpi').click(function (event) {
            event.preventDefault();
            var codeMembre = $('#codeMembreOpi').val();
            var modeReg = $('select#typeOpi').val();
            if (codeMembre !== '' && modeReg !== '') {
                $(opigrid).jqGrid('setGridParam', {
                    url: 'tpagcp/' + modeReg + "/" + codeMembre,
                    datatype: 'json'
                }).trigger('reloadGrid');
            } else {
                alert("Renseignez le Code Membre et le Moyen de Paiement");
            }
        });

        $('#modeRegOpi').change(function (event) {
            event.DefaultPrevented;
            if ($(this).val() === 'ENC') {
                $('select#typeEscOpi').attr('disabled', 'true');
            } else {
                $('select#typeEscOpi').removeAttr('disabled');
            }
        });

        $('#typeEscOpi').change(function (event) {
            event.DefaultPrevented;
            if ($(this).val() === 'T') {
                $('input#nbreEscOpi').attr('disabled', 'true');
                $('input#montantOpi').val(0);
            } else {
                $('input#nbreEscOpi').removeAttr('disabled');
                $('input#montantOpi').val(0);
            }
        });

        function selectionManager(id, status) {
            // was it checked (true) or unchecked (false)
            if (status) {
                // if it's just one id (not array)
                if (!$.isArray(id)) {
                    // if it's not already in the array, then add it
                    if ($.inArray(id, selArr) < 0) {
                        selArr.push(id)
                    }
                } else {
                    // which id's aren't already in the 'selected' array
                    var tmp = $.grep(id, function (item, ind) {
                        return $.inArray(item, selArr) < 0;
                    });
                    // add only those unique id's to the 'selected' array
                    $.merge(selArr, tmp);
                }
            } else {
                // if it's just one id (not array)
                if (!$.isArray(id)) {
                    // remove that one id
                    selArr.splice($.inArray(id, selArr), 1);
                } else {
                    // give me an array without the 'id's passed
                    // (resetting the 'selected' array)
                    selArr = $.grep(selArr, function (item, ind) {
                        return $.inArray(item, id) > -1;
                    }, true);
                }
            }
            if (selArr.length > 0) {
                var somme = 0;
                $.each(selArr, function (i, val) {
                    console.log(i);
                    var rowData = $(opigrid).jqGrid('getRowData', val);
                    items[i] = parseInt(rowData.idTpagcp);
                    console.log(items);
                    if ($('select#modeRegOpi').val() === "ENC") {
                        somme += parseFloat(rowData.montEchu);
                    } else {
                        if ($('select#typeEscOpi').val() === "T") {
                            somme += parseFloat(rowData.solde);
                        } else {
                            var nbre = parseInt($('input#nbreEscOpi').val());
                            if (nbre !== NaN && nbre > 0) {
                                somme += parseFloat(rowData.montTranche) * nbre;
                            } else {
                                alert("renseigner le nombre d'OPI à escompter");
                            }
                        }
                    }
                });
                $('#btnValOpi').removeAttr('disabled');
            } else {
                $('#btnValOpi').attr('disabled', 'true');
            }
            $('input#montantOpi').val(somme);
        }


        $('#btnValOpi')
            .click(
                function (event) {
                    event.preventDefault();
                    if (parseFloat($('#montantOpi').val()) > 0) {
                        var codeMembre = $('#codeMembreOpi').val();
                        var typeOpi = $('#typeOpi').val();
                        var modeRegOpi = $('#modeRegOpi').val();
                        var typeEscOpi = $('#typeEscOpi').val();
                        var nbreEscOpi = $('#nbreEscOpi').val();
                        var montantOpi = $('#montantOpi').val();
                        var codeBanque = $('select#codeBanque').val();
                        var csrf = $('input[name="_csrf"]').val();
                        var params = "codeBanque="
                            + codeBanque
                            + "&codeMembre="
                            + codeMembre + "&type="
                            + typeOpi + "&modeReg="
                            + modeRegOpi + "&typeEsc="
                            + typeEscOpi + "&nbre="
                            + nbreEscOpi + "&montant="
                            + montantOpi + "&opis="
                            + items;

                        $.ajax({
                                type: "post",
                                url: "escompte/",
                                data: params,
                                dataType: 'json',
                                headers: {
                                    'X-CSRF-Token': csrf
                                },
                                success: function (data) {
                                    if (typeof data == 'string') {
                                        alert(data);
                                    } else {
                                        $('#codeMembre').val(data.codeMembre);
                                        $('#modeReg').val(data.modeReg);
                                        $('#typeEsc').val(data.typeEsc);
                                        $('#nbre').val(data.nbre);
                                        $('#montant').val(data.montant);
                                        $('#codeMembrePbf').val(data.codeMembrePbf);
                                        $("#valideOpi_modal")
                                            .modal(
                                                {
                                                    keyboard: false,
                                                    backdrop: 'static',
                                                    width: 770
                                                });
                                    }
                                }
                            });
                    }
                });

        $('#btn-display').click(function (e) {
            e.preventDefault();
            if ($('#codeBon').val() !== '') {
                var params = "numeroBon=" + $('#numeroBonOp').val();
                $.ajax({
                    type: "get",
                    url: "payerbonopi/",
                    data: params,
                    dataType: 'json',
                    success: function (data) {
                        if (data !== undefined && data !== NaN) {
                            $('#codeMembre').val(data.codeMembre);
                            $('#modeReg').val(data.modeReg);
                            $('#typeEsc').val(data.typeEsc);
                            $('#nbre').val(data.nbre);
                            $('#montant').val(data.montant);
                            $('#codeMembrePbf').val(data.codeMembrePbf);
                            $("#valideOpi_modal").modal(
                                {
                                    keyboard: false,
                                    backdrop: 'static',
                                    width: 770
                                });
                        } else {
                            alert('Echec de Récupération du détail du Bon');
                        }
                    }
                });
            }
        });

        $('button#vopi_btn_show').click(function (e) {
            e.preventDefault();
            if ($('#vopi_numeroBon').val() !== '') {
                var params = "numeroBon=" + $('#vopi_numeroBon').val();
                $.ajax({
                    type: "get",
                    url: "bonopi/",
                    data: params,
                    dataType: 'json',
                    success: function (data) {
                        if (data !== undefined && data !== NaN) {
                            if (typeof data == 'string') {
                                $('#vopi_message').html(data);
                            } else {
                                $('#vopi_codeMembre').val(data.codeMembre);
                                $('#vopi_codeBanque').val(data.codeBanque);
                                $('#vopi_montant').val(data.montant);
                            }
                        } else {
                            $('#vopi_message').html('Echec de Récupération du détail du Bon');
                        }
                    }
                });
            } else {
                $('#vopi_message').html('Veuillez renseigner le numéro du bon');
            }
        });

        $('#btn-valOpi').click(function (e) {
            var csrf = $('input[name="_csrf"]').val();
            var params = "codeMembre=" + $('#codeMembre').val()
                + "&numeroBon=" + $('#numeroBonOp').val()
                + "&modeReg=" + $('#modeReg').val()
                + "&typeEsc=" + $('#typeEsc').val() + "&nbre="
                + $('#nbre').val() + "&montant="
                + $('#montant').val() + "&codeMembrePbf="
                + $('#codeMembrePbf').val();
            $.ajax({
                type: "post",
                url: "valideropi/",
                data: params,
                headers: {
                    'X-CSRF-Token': csrf
                },
                success: function (data) {
                    if (data == 'OK') {
                        alert("L'opération a été bien effectuée");
                        $('#valideOpi_modal').modal('hide');
                    } else {
                        alert(data);
                    }
                }
            });
        });

        $('#btn-val-payeropi').click(function (e) {
            e.preventDefault();
            var csrf = $('input[name="_csrf"]').val();
            var params = "codeMembre=" + $('#codeMembre').val()
                + "&numeroBon=" + $('#numeroBonOp').val()
                + "&modeReg=" + $('#modeReg').val()
                + "&typeEsc=" + $('#typeEsc').val() + "&nbre="
                + $('#nbre').val() + "&montant="
                + $('#montant').val() + "&codeMembrePbf="
                + $('#codeMembrePbf').val();
            $.ajax({
                type: "post",
                url: "valideropi/",
                data: params,
                headers: {
                    'X-CSRF-Token': csrf
                },
                success: function (data) {
                    if (data == 'OK') {
                        alert("Opération bien effectuée");
                        $('#codeMembre').val('');
                        $('#codeMembrePbf').val('');
                        $('#codeBon').val('');
                        $('#modeReg').val('');
                        $('#typeEsc').val('');
                        $('#nbre').val(0);
                        $('#montant').val(0);
                    } else {
                        alert("Echec de l'opération");
                    }
                }
            });
        });
    });