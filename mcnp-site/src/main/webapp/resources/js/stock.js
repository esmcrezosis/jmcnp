jQuery(function ($) {
    var grid_selector = "#tbl-stock";
    var pager_selector = "#tbl-pager";
//    var csrfParameter =$("meta[name='_csrf_parameter']").attr("content");
//    var csrfHeader =$("meta[name='_csrf_header']").attr("content");
//    var csrfToken =$("meta[name='_csrf']").attr("content");

    var token = $("input[name='_csrf']").val();
    var header = "X-CSRF-TOKEN";
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
    var parent_column = $(grid_selector).closest('[class*="col-"]');
    //resize to fit page size
    $(window).on('resize.jqGrid', function () {
        $(grid_selector).jqGrid('setGridWidth', parent_column.width());
    });

    //resize on sidebar collapse/expand
    $(document).on('settings.ace.jqGrid', function (ev, event_name, collapsed) {
        if (event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed') {
//setTimeout is for webkit only to give time for DOM changes and then redraw!!!
            setTimeout(function () {
                $(grid_selector).jqGrid('setGridWidth', parent_column.width());
            }, 0);
        }
    });
    jQuery(grid_selector).jqGrid({
//direction: "rtl",
        url: 'allListarticles',
        datatype: "json",
        height: 250,
        colNames: ['CODE BARRE', 'REFERENCE', 'DESIGNATION', 'PRIX', 'CATEGORIE', 'TYPE', 'PUBLIER'],
        colModel: [
            {name: 'codeBarre', index: 'codeBarre', width: 100, sortable: false, resize: false},
            {name: 'reference', index: 'reference', width: 70, editable: false},
            {name: 'designation', index: 'designation', width: 120, editable: false},
            {name: 'prix', index: 'prix', width: 90, editable: false},
            {name: 'categorie', index: 'categorie', width: 120, editable: false},
            {name: 'type', index: 'type', width: 100, editable: false},
            {name: 'publier', index: 'publier', width: 70, formatter: 'integer'}
        ],
        jsonReader: {
            root: 'articles',
            page: 'currentPage',
            total: 'totalPages',
            repeatitems: false,
            id: 'id'
        },
        viewrecords: true,
        rowNum: 10,
        sortname: 'reference',
        sortorder: 'asc',
        rowList: [10, 20, 30],
        pager: pager_selector,
        altRows: true,
        gridview: true,
        loadComplete: function () {
            var table = this;
            setTimeout(function () {
               // styleCheckbox(table);
                updateActionIcons(table);
                updatePagerIcons(table);
                enableTooltips(table);
            }, 0);
        },
        caption: "Liste des articles",
        onSelectRow: function (id) {
            if (id !== null && id > 0) {
                row = jQuery(grid_selector).jqGrid('getRowData', id);
                $.ajax({
                    url: "articleStocke/" + row.codeBarre,
                    type: 'GET',
                    datatype: 'json',
                    contentType: 'application/json',
                    success: function (data) {
                        $('#st_codeBarre').val(data.codeBarre);
                        $('#st_reference').val(data.reference);
                        $('#st_designation').val(data.designation);
                        $('#st_prix').val(data.prix);
                        $('#st_type').val(data.type);
                        $('#st_categorie').val(data.categorie);
                        if (data.publier === 1) {
                            console.log(data.publier);
                            $('#st_publier').prop({'checked': true});
                        } else {
                            console.log(data.publier);
                            $('#st_publier').prop({'checked': false});
                        }
                    }
                });
            }
        }
    });
    $(window).triggerHandler('resize.jqGrid'); //trigger window resize to make the grid get the correct size

//unlike navButtons icons, action icons in rows seem to be hard-coded
//you can change them like this in here if you want
    function updateActionIcons(table) {
        /**
         var replacement = 
         {
         'ui-ace-icon fa fa-pencil' : 'ace-icon fa fa-pencil blue',
         'ui-ace-icon fa fa-trash-o' : 'ace-icon fa fa-trash-o red',
         'ui-icon-disk' : 'ace-icon fa fa-check green',
         'ui-icon-cancel' : 'ace-icon fa fa-times red'
         };
         $(table).find('.ui-pg-div span.ui-icon').each(function(){
         var icon = $(this);
         var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
         if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
         })
         */
    }

//replace icons with FontAwesome icons like above
    function updatePagerIcons(table) {
        var replacement =
                {
                    'ui-icon-seek-first': 'ace-icon fa fa-angle-double-left bigger-140',
                    'ui-icon-seek-prev': 'ace-icon fa fa-angle-left bigger-140',
                    'ui-icon-seek-next': 'ace-icon fa fa-angle-right bigger-140',
                    'ui-icon-seek-end': 'ace-icon fa fa-angle-double-right bigger-140'
                };
        $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function () {
            var icon = $(this);
            var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
            if ($class in replacement)
                icon.attr('class', 'ui-icon ' + replacement[$class]);
        });
    }

    function enableTooltips(table) {
        $('.navtable .ui-pg-button').tooltip({container: 'body'});
        $(table).find('.ui-pg-div').tooltip({container: 'body'});
    }

//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');

    $(document).one('ajaxloadstart.page', function (e) {
        $.jgrid.gridDestroy(grid_selector);
        $('.ui-jqdialog').remove();
    });




//    $('#st-btn_add').click(function (e) {
//        e.preventDefault();
//
//        //var mat = $('#frm_stock').serialize();
//
//        var entityJson = form2js("frm_stock", null, false); // js library to get json from form
//        // $csrf = $('input[name="_csrf"]').val();
//
//        var entityJsonStr = JSON.stringify(entityJson);
//
//        var formData = new FormData();
//        formData.append("data", entityJsonStr); // the entered data as JSON
//        // formData.append("image", image.files[0]);
//
//        // append files, if there are any
//        //    formData.append("image", $("frm_stock").find("input[type='file']"));
//
//
//// append files, if there are any
//        $.each($("frm_stock").find("input[type='file']"), function (i, tag) {
//            $.each($(tag)[0].files, function (i, file) {
//                formData.append(tag.name, file);
//            });
//        });
//
//
//        console.log(formData);
//        $.ajax({
//            url: 'savearticle',
//            type: 'POST',
//            contentType: false,
//            processData: false,
//            data: formData,
//            //dataType:'json',
//            headers: headers,
//            success: function (data) {
//                if (data === true) {
//                    clearForm();
//                    jQuery(grid_selector).jqGrid('setGridParam', {
//                        url: 'allListarticles'
//                    }).trigger('reloadGrid');
//                } else {
//                    alert("Echec de l'enregistrement");
//                }
//            },
//            error: function (xhr, textStatus, errorThrown) {
//                alert('Ce CodeBarre est ' + (errorThrown ? errorThrown : xhr.status));
//            }
//
//
//        });
//
//        return false;
//    });

    $('#st-btn_new').click(function (e) {
        e.preventDefault();
        clearForm();
    });
    function clearForm() {
        $('#st_codeBarre').val("");
        $('#st_reference').val("");
        $('#st_designation').val("");
        $('#st_prix').val("");
        $('#st_type').val("");
        $('#st_categorie').val("");
        $('#st_publier').val(0);
    }

    /**GESTION DE L'UPLOAD D'UN FICHIER 

    $('.stock-prod-upload').bind('change', function (e) {
        var files = this.files;
        console.log(e);
        upload(files, $(this), 0);
    });
    function upload(files, area, index) {
        var file = files[index];
        $extensions = ['image/png', 'image/jpg', 'image/jpeg'];
        $verifextensions = "";
        for (var i in $extensions) {
            if ($extensions[i].match(file.type)) {
                $verifextensions = $extensions[i];
            }
        }
        console.log(file);
        if (file.type === "" || $extensions === "") {
            alert('Fichier invalide');
            return false;
        }

        if (file.type !== $verifextensions) {
            alert('Ce Type de fichier n\'est pas autorisé');
            return false;
        }

        if (file.name === "") {
            alert('Ce Fichier est invalide');
            return false;
        }
        if (file.type === $verifextensions && file.name !== "" && file.size !== 0) {
            console.log('Reussit');
            /*
             var xhr = new XMLHttpRequest();
             xhr.addEventListener('load',function(e){
             
             },false);*/
            /*
             xhr.open('get','/enregistrement/'+file.name+'/'+file+'',true)
             
             xhr.send();
        }

    }*/









});

	