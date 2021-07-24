// callbacks that are called before the request is sent to the server --------------

// Fires after the submit button is clicked and the postdata is constructed.
// Parameters passed to this event are: a options array of the method and the posted data array.
// The event should return array of type {} which extends the postdata array.
function onclickSubmitEditCallback(options, data) {
    cleanEditForm();
    options.url = URL + "/" + data.dataTable_id;
}

function onclickSubmitDelCallback(options, rowid) {
    options.url = URL + "/" + rowid;
    // return { field1:value1 }; // will be sent to the server
}

// Serialize the data passed to the AJAX request. The function should return the serialized data.
// This event can be used when a custom data should be passed to the server (JSON, XML, etc.)
// The method receives the data which will be posted to the server.
function serializeEditDataCallback(data) {
    // create a copy
    var modifiedData = $.extend({}, data);

    // if it's a new record, replace its ID
    if(modifiedData.id == "_empty") {
        modifiedData.id = 0;
        modifiedData.version = 0;
    }

    // the operation is passed as an HTTP method, this field is not necessary
    delete modifiedData.oper;

    //The "autoencode:true" option HTML-encodes incoming and posted data.
    //The posted data should not be encoded. It is de-encoded here.
    modifiedData.name = $.jgrid.htmlDecode(modifiedData.name);

    // encode the data in `application/x-www-form-urlencoded`
    return $.param(modifiedData);

    // Remove the comment to return JSON instead
    // return JSON.stringify(modifiedData);
    // You also need to add `ajaxEditOptions: { contentType: "application/json" }` to `edit` options
}

// This callback is similar to serializeEditDataCallback(), but it is called when a row is deleted.
function serializeDelDataCallback(data) {

    return $.param( { version: $("#dataTable").jqGrid("getCell", data.id, "version") } );
}


// callbacks that are called when the response is received from server -----------------


// This callback is called when an error happens during an add/edit/delete operation on the server.
// It should return a string which is displayed at the top of the dialog.
function errorTextFormatCallback(xhr) {

    // If the response does not have JSON data, or it contains an error message that should be displayed on a separate error page...
    if(xhr.responseJSON == null || xhr.responseJSON.globalErrorCode != null) {
        return; // the error should be processed globally
    }

    var validationErrors = xhr.responseJSON.validationErrors;

    if(validationErrors != null) {

        for (var i = 0; i < validationErrors.length; i += 2) {
            var selector = ".DataTD #" + validationErrors[i];
            $(selector).after( "<div class='field-error-message'>" + validationErrors[i+1] + "</div>" );
        }
    }

    return xhr.responseJSON.localErrorMessage;
}

// removes error icons from an add/edit form
function cleanEditForm() {
    $(".field-error-message").remove();
}

//set the ID of the new row in the grid
function afterSubmitCallback(xhr, data) {
    data.version = xhr.responseJSON.version;
    // the id in the returned array is used for new records only; it is ignored for updated records
    return [ true, "", xhr.responseJSON.id ];
}

//error handler for the grid data requests (not for add/edit/delete operations)
function loadErrorCallback(xhr, st, err) {

    if(xhr.responseJSON != null && xhr.responseJSON.localErrorMessage != null) {
        // #error-message is a <span> tag in the HTML page
        $("#error-message").html(xhr.responseJSON.localErrorMessage);
    }
}

//This callback is called after loading all data to the grid. It cleans the error message on the main page (NOT in add/edit/delete dialogs).
function loadCompleteCallback(data) {

    //clean the error message (#error-message is a <span> tag in the HTML page)
    $("#error-message").html("");
}

// other methods --------------------------------------------------------------------


function setDataGridWidth() {
    $("html").css("overflow", "hidden"); // hack for bottom scrollbar in
    // Chrome that keeps appearing when you click the unmaximise button

    var width = $("#content").width();
    $("#dataTable").jqGrid("setColProp", "name", { widthOrg : width - 130 });
    $("#dataTable").jqGrid("setGridWidth", width - 10);

    $("html").css("overflow", "auto");
}

// Creates a jQuery UI dialog (hidden at this point)
function initialiseWarningDialog() {
    $("#selectRowMessage").dialog({
        autoOpen:false,
        height: 120,
        modal: true,
        buttons: {
            Close: function() {
                $(this).dialog("close");
            }
        }
    });
}

function attachButtons() {
    $("#editBtn").click(function() {
        var gr = $("#dataTable").jqGrid("getGridParam", "selrow");
        if (gr != null) {
            $("#dataTable").jqGrid("editGridRow", gr, jqGridEditOptions);
        } else {
            $("#selectRowMessage").dialog("open");
        }
    });

    $("#addBtn").click(function() {
        $("#dataTable").jqGrid("editGridRow", "new", jqGridAddOptions);
    });

    $("#deleteBtn").click(function() {
        var gr = $("#dataTable").jqGrid("getGridParam", "selrow");
        if (gr != null) {
            $("#dataTable").jqGrid("delGridRow", gr, jqGridDelOptions);
        } else {
            $("#selectRowMessage").dialog("open");
        }
    });

    $("#searchBtn").click(function() {
        $("#dataTable").jqGrid("searchGrid", jqGridSearchOptions);
    });

    $("#resetBtn").click(function() {
        var postdata = $("#dataTable").jqGrid("getGridParam","postData");
        postdata._search = false;
        postdata.searchField = "";
        postdata.searchOper = "";
        postdata.searchString = "";

        $("#dataTable").trigger("reloadGrid",[{page:1}]);
    });
}

// converts buttons into jQuery UI buttons
function decorateButtons()
{
    $("#addBtn").button({
        icons: { primary: "ui-icon-plus" }
    });

    $("#editBtn").button({
        icons: { primary: "ui-icon-pencil" }
    });

    $("#deleteBtn").button({
        icons: { primary: "ui-icon-trash" }
    });

    $("#searchBtn").button({
        icons: { primary: "ui-icon-search" }
    });

    $("#resetBtn").button({
        icons: { primary: "ui-icon-arrowreturnthick-1-w" }
    });
}

function preloadImages() {
    var themeImagesDir = "//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/redmond/images/";
    $("<img/>")[0].src = themeImagesDir + "ui-icons_217bc0_256x240.png";
    $("<img/>")[0].src = themeImagesDir + "ui-icons_f9bd01_256x240.png";
}