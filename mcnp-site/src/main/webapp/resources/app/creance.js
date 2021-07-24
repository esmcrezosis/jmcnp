/**
 * http://usejsdoc.org/
 */
$(document)
        .ready(
                function () {
                    jQuery(function ($) {
                        var myTable = $('#dt-utilisateurs')
                                // .wrap("<div class='dataTables_borderWrap' />") //if
                                // you are applying
                                // horizontal scrolling (sScrollX)
                                .DataTable({
                                    bAutoWidth: false,
                                    "aoColumns": [{
                                            "bSortable": false
                                        }, null, null, null, null, null, {
                                            "bSortable": false
                                        }],
                                    "aaSorting": [],

                                    // "bProcessing": true,
                                    // "bServerSide": true,
                                    // "sAjaxSource": "http://127.0.0.1/table.php" ,

                                    // ,
                                    // "sScrollY": "200px",
                                    // "bPaginate": false,

                                    // "sScrollX": "100%",
                                    // "sScrollXInner": "120%",
                                    // "bScrollCollapse": true,
                                    // Note: if you are applying horizontal scrolling
                                    // (sScrollX) on a
                                    // ".table-bordered"
                                    // you may want to wrap the table inside a
                                    // "div.dataTables_borderWrap"
                                    // element

                                    // "iDisplayLength": 50

                                    select: {
                                        style: 'single'
                                    }
                                });

                        var myTable = $('#dt-users')
                                // .wrap("<div class='dataTables_borderWrap' />") //if
                                // you are applying
                                // horizontal scrolling (sScrollX)
                                .DataTable();

                        $('.date-picker').datepicker({
                            autoclose: true,
                            todayHighlight: true,
                            language: 'fr'
                        })
                                // show datepicker when clicking on the icon
                                .next().on(ace.click_event, function () {
                            $(this).prev().focus();
                        });

                        $("#ldialogRef").on('click', function (ev) {
                            ev.preventDefault();
                            $("#list_creance_modal").modal({
                                keyboard: false,
                                backdrop: 'static',
                                width: 770
                            });
                        });

                        var lcgrid = "#lcreance-table";
                        var lcpager = "#lcreance-pager";
                        $(lcgrid).jqGrid(
                                {
                                    url: "listcreance",
                                    datatype: 'json',
                                    mtype: 'GET',
                                    colNames: ['ID', 'Date', 'Debiteur',
                                        'Montant'],
                                    colModel: [{
                                            name: 'idCreance',
                                            index: 'idCreance',
                                            width: 70
                                        }, {
                                            name: 'dateCreance',
                                            index: 'dateCreance',
                                            width: 100,
                                            formatter: 'date',
                                            formatOptions: {
                                                newformat: 'dd/mm/yyyy'
                                            }
                                        }, {
                                            name: 'codeMembreDeb',
                                            index: 'codeMembreDeb',
                                            width: 200
                                        }, {
                                            name: 'montCreance',
                                            index: 'montCreance',
                                            width: 150
                                        }],
                                    viewrecords: true,
                                    sortname: 'idCreance',
                                    sortorder: 'asc',
                                    rowNum: 10,
                                    rowList: [10, 20, 30],
                                    pager: lcpager,
                                    altRows: true,

                                    multiselect: false,
                                    multiboxonly: true,

                                    caption: "Listes des Creances",
                                    height: 200,
                                    width: 870,
                                    onSelectRow: function (id) {
                                        if (id !== null && id > 0) {
                                            row = jQuery(lcgrid).jqGrid(
                                                    'getRowData', id);
                                            $("#idCreance_input").val(
                                                    row.idCreance);
                                            lastsel = id;
                                        }
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

                        $("#ddialogRef").on('click', function (e) {
                            e.preventDefault();
                            $("#list_dette_modal").modal({
                                keyboard: false,
                                backdrop: 'static',
                                width: 770
                            });
                        });

                        var ldgrid = "#ldette-table";
                        var ldpager = "#ldette-pager";
                        $(ldgrid).jqGrid(
                                {
                                    url: "listdette",
                                    datatype: 'json',
                                    mtype: 'GET',
                                    colNames: ['ID', 'Date', 'Creancier',
                                        'Montant'],
                                    colModel: [{
                                            name: 'idCreance',
                                            index: 'idCreance',
                                            width: 70
                                        }, {
                                            name: 'dateCreance',
                                            index: 'dateCreance',
                                            width: 100
                                        }, {
                                            name: 'codeMembreDeb',
                                            index: 'codeMembreDeb',
                                            width: 200
                                        }, {
                                            name: 'montCreance',
                                            index: 'montCreance',
                                            width: 150
                                        }],
                                    viewrecords: true,
                                    sortname: 'idCreance',
                                    sortorder: 'asc',
                                    rowNum: 10,
                                    rowList: [10, 20, 30],
                                    pager: ldpager,
                                    altRows: true,

                                    multiselect: false,
                                    multiboxonly: true,

                                    caption: "Listes des Dettes",
                                    height: 200,
                                    width: 870,
                                    onSelectRow: function (id) {
                                        if (id !== null && id > 0) {
                                            row = jQuery(ldgrid).jqGrid(
                                                    'getRowData', id);
                                            $("#idDette_input").val(
                                                    row.idCreance);
                                            lastsel = id;
                                        }
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

                        var dette_grid = "#dette-table";
                        var dette_pager = "#dette-pager";
                        $(dette_grid)
                                .jqGrid(
                                        {
                                            url: "listdette",
                                            datatype: 'json',
                                            mtype: 'GET',
                                            colNames: ['ID', 'Date',
                                                'Creancier', 'Montant',
                                                'Etat Dette',
                                                'Mode Reglement',
                                                'Date Reglement'],
                                            colModel: [{
                                                    name: 'idCreance',
                                                    index: 'idCreance',
                                                    width: 70
                                                }, {
                                                    name: 'dateCreance',
                                                    index: 'dateCreance',
                                                    width: 120
                                                }, {
                                                    name: 'codeMembreDeb',
                                                    index: 'codeMembreDeb',
                                                    width: 250
                                                }, {
                                                    name: 'montCreance',
                                                    index: 'montCreance',
                                                    width: 150
                                                }, {
                                                    name: 'etatCreance',
                                                    index: 'etatCreance',
                                                    width: 100
                                                }, {
                                                    name: 'modeReglement',
                                                    index: 'modeReglement',
                                                    width: 120
                                                }, {
                                                    name: 'dateReglement',
                                                    index: 'dateReglement',
                                                    width: 120
                                                }],
                                            viewrecords: true,
                                            sortname: 'idCreance',
                                            sortorder: 'asc',
                                            rowNum: 10,
                                            rowList: [10, 20, 30],
                                            pager: dette_pager,
                                            altRows: true,

                                            multiselect: false,
                                            multiboxonly: true,

                                            caption: "Listes des Dettes",
                                            height: 450,

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

                        var grid_selector = "#creance-table";
                        var pager_selector = "#creance-pager";

                        var parent_column = $(grid_selector).closest(
                                '[class*="col-"]');
                        var ld_parent_column = $(dette_grid).closest(
                                '[class*="col-"]');
                        // resize to fit page size
                        $(window).on(
                                'resize.jqGrid',
                                function () {
                                    $(grid_selector).jqGrid('setGridWidth',
                                            parent_column.width());
                                    $(dette_grid).jqGrid('setGridWidth',
                                            ld_parent_column.width());
                                });

                        // resize on sidebar collapse/expand
                        $(document)
                                .on(
                                        'settings.ace.jqGrid',
                                        function (ev, event_name, collapsed) {
                                            if (event_name === 'sidebar_collapsed'
                                                    || event_name === 'main_container_fixed') {
                                                // setTimeout is for webkit only
                                                // to give time for DOM
                                                // changes and then redraw!!!
                                                setTimeout(function () {
                                                    $(grid_selector).jqGrid(
                                                            'setGridWidth',
                                                            parent_column
                                                            .width());
                                                    $(dette_grid).jqGrid(
                                                            'setGridWidth',
                                                            ld_parent_column
                                                            .width());
                                                }, 0);
                                            }
                                        });


                        jQuery(grid_selector)
                                .jqGrid(
                                        {
                                            url: "listcreance",
                                            datatype: "json",
                                            height: 450,
                                            colNames: ['ID ', 'Date',
                                                'Debiteur', 'Montant',
                                                'Etat', 'Mode Reglement',
                                                'Date Reglement'],
                                            colModel: [{
                                                    name: 'idCreance',
                                                    index: 'idCreance',
                                                    width: 60,
                                                    sorttype: "int"
                                                }, {
                                                    name: 'dateCreance',
                                                    index: 'dateCreance',
                                                    width: 90,
                                                    formatter: 'date',
                                                    formatOptions: {
                                                        newformat: 'dd/mm/yyyy'
                                                    },
                                                    sorttype: 'date'
                                                }, {
                                                    name: 'codeMembreDeb',
                                                    index: 'codeMembreDeb',
                                                    width: 150
                                                }, {
                                                    name: 'montCreance',
                                                    index: 'montCreance',
                                                    width: 90
                                                }, {
                                                    name: 'etatCreance',
                                                    index: 'etatCreance',
                                                    width: 80
                                                }, {
                                                    name: 'modeReglement',
                                                    index: 'modeReglement',
                                                    width: 150,
                                                    sortable: false
                                                }, {
                                                    name: 'dateReglement',
                                                    index: 'dateReglement',
                                                    width: 90,
                                                    formatter: 'date',
                                                    sortable: false
                                                }],

                                            viewrecords: true,
                                            sortname: 'dateCreance',
                                            sortorder: 'asc',
                                            rowNum: 10,
                                            rowList: [10, 20, 30],
                                            pager: pager_selector,
                                            altRows: true,

                                            multiselect: true,
                                            multiboxonly: true,
                                            caption: "Listes des Creances"
                                        });
                        $(window).triggerHandler('resize.jqGrid');// trigger
                     
                       

                        $(document).one('ajaxloadstart.page', function (e) {
                            $.jgrid.gridDestroy(grid_selector);
                            $('.ui-jqdialog').remove();
                        });

                    });
                });