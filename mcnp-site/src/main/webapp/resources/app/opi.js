$(document).ready(function() {
  function remove(array, element) {
    const index = array.indexOf(element);

    if (index !== -1) {
      array.splice(index, 1);
    }
  }

  var nbre_opi = 0;
  var mont_opi = 0;
  var listOpi = Array();
  var tpagcpId = 0;

  $.getJSON("listePayement/", function(data) {
    var options = $("#txtModePaiementBenef");
    var i;
    for (i = 0; i < data.length; i++) {
      options.append(new Option(data[i].codeBanque, data[i].codeBanque));
    }
  });

  var tblTraiteApp = "#tbl-traiteApp";
  var tblTraiteAppPager = "#tbl-traiteAppPager";
  $(tblTraiteApp).jqGrid({
    datatype: "local",
    colNames: ["ID", "Date Début", "Date Fin", "Montant"],
    colModel: [
      {
        name: "traiteId",
        index: "traiteId",
        width: 70
      },
      {
        name: "traiteDateDebut",
        index: "traiteDateDebut",
        width: 100,
        formatter: "date",
        formatOptions: {
          newformat: "dd/mm/yyyy"
        }
      },
      {
        name: "traiteDateFin",
        index: "traiteDateFin",
        width: 100,
        formatter: "date",
        formatOptions: {
          newformat: "dd/mm/yyyy"
        }
      },
      {
        name: "traiteMontant",
        index: "traiteMontant",
        width: 120
      }
    ],
    viewrecords: true,
    sortname: "traiteId",
    sortorder: "asc",
    rowNum: 20,
    rowList: [20, 40, 60],
    pager: tblTraiteAppPager,
    altRows: true,
    rownumbers: true,
    rownumWidth: 25,
    multiselect: true,
    multiboxonly: true,

    caption: "Détails des OPI",
    height: 220,
    width: "100%",
    beforeSelectRow: function(rowId, e) {
      return $(e.target).is("input:checkbox");
    },
    onSelectRow: function(id, status, e) {
      if (id !== null && id > 0) {
        mont_opi =
          $("#txtMontant").val() !== ""
            ? parseFloat($("#txtMontant").val())
            : 0;
        nbre_opi =
          $("#txtNbreOpi").val() !== ""
            ? parseFloat($("#txtNbreOpi").val())
            : 0;
        row = jQuery(tblTraiteApp).jqGrid("getRowData", id);
        if (status) {
          mont_opi = mont_opi + parseFloat(row.traiteMontant);
          nbre_opi = nbre_opi + 1;
          listOpi.push(row.traiteId);
        } else {
          mont_opi = mont_opi - parseFloat(row.traiteMontant);
          nbre_opi = nbre_opi - 1;
          remove(listOpi, row.traiteId);
        }
        $("#txtMontant").val(mont_opi);
        $("#txtNbreOpi").val(nbre_opi);
        lastsel = id;
      }
    },
    onSelectAll: function(aRowids, status) {
      if (status) {
        mont_opi = 0;
        aRowids.forEach(function(element) {
          row = jQuery(tblTraiteApp).jqGrid("getRowData", element);
          mont_opi += parseFloat(row.traiteMontant);
          listOpi.push(row.traiteId);
        });
        nbre_opi = aRowids.length;
      } else {
        aRowids.forEach(function(element) {
          row = jQuery(tblTraiteApp).jqGrid("getRowData", element);
          mont_opi -= parseFloat(row.traiteMontant);
          remove(listOpi, row.traiteId);
        });
        nbre_opi = 0;
      }
      $("#txtMontant").val(mont_opi);
      $("#txtNbreOpi").val(nbre_opi);
    }
  });

  var tblTpagcpApp = "#tbl-tpagcpApp";
  var tblTpagcpAppPager = "#tbl-tpagcpAppPager";
  $(tblTpagcpApp).jqGrid({
    datatype: "local",
    colNames: ["ID", "Code Membre", "Nombre Restant", "Montant"],
    colModel: [
      {
        name: "idTpagcp",
        index: "idTpagcp",
        width: 70
      },
      {
        name: "codeMembre",
        index: "codeMembre",
        width: 200
      },
      {
        name: "resteNtf",
        index: "resteNtf",
        width: 100
      },
      {
        name: "montTranche",
        index: "montTranche",
        width: 150
      }
    ],
    viewrecords: true,
    sortname: "idTpagcp",
    sortorder: "asc",
    rowNum: 20,
    rowList: [20, 40, 60],
    pager: tblTpagcpAppPager,
    altRows: true,
    rownumbers: true,
    rownumWidth: 25,
    multiselect: false,

    caption: "Listes des OPI",
    height: 230,
    onSelectRow: function(id) {
      if (id !== null && id > 0) {
        nbre_opi = 0;
        mont_opi = 0;
        $("#txtMontant").val(mont_opi);
        $("#txtNbreOpi").val(nbre_opi);
        row = jQuery(tblTpagcpApp).jqGrid("getRowData", id);
        tpagcpId = parseInt(row.idTpagcp);
        $.getJSON("listTraite/", "id=" + row.idTpagcp, function(gridData) {
          $(tblTraiteApp).jqGrid("clearGridData");
          $(tblTraiteApp).jqGrid("setGridParam", { data: gridData });
          // hide the show message
          $(tblTraiteApp)[0].grid.endReq();
          // refresh the grid
          $(tblTraiteApp).trigger("reloadGrid");
        });
        /* if (listOpi.length > 0) {
          console.log("Longueur : " + listOpi.length);
          listOpi.forEach(function(element) {
            console.log("idTpagcp : " + element);
            jQuery(tblTraiteApp).jqGrid('setSelection', element);
          });
        } */
        lastsel = id;
      }
    }
  });

  $("#txtMembreApp").blur(function(e) {
    e.defaultPrevented;
    var param = "codeMembre=" + $(this).val();
    $.getJSON("te/", param, function(data) {
      var options = $("#selTegcApp");
      options.html("");
      var i;
      for (i = 0; i < data.length; i++) {
        options.append(new Option(data[i].nomTegc, data[i].codeTegc));
      }
    });
    $.getJSON("membre/", param, function(data) {
      $("input#txtNomMembreApp").val(data.rep);
    });

    $.getJSON("tpagcp/", param, function(gridData) {
      //$(tblTpagcpApp).jqGrid("clearGridData");
      $(tblTpagcpApp).jqGrid("setGridParam", { data: gridData });
      // hide the show message
      $(tblTpagcpApp)[0].grid.endReq();
      // refresh the grid
      $(tblTpagcpApp).trigger("reloadGrid");
    });
  });

  $("#txtMembreBenef").blur(function(e) {
    e.preventDefault();
    var param = "codeMembre=" + $(this).val();
    $.getJSON("membre/", param, function(data) {
      $("input#txtNomMembreBenef").val(data.rep);
    });
  });

  $("#btValiderAppro").click(function(e) {
    e.preventDefault();
    var traiteList = new Array();
    if (nbre_opi > 0 && tpagcpId != 0 && listOpi.length > 0) {
      var approOpi = {
        codeMembreAcheteur: $("#txtMembreBenef").val(),
        codeMembreVendeur: $("#txtMembreApp").val(),
        moyenPayement: $("#txtModePaiementBenef").val(),
        numeroCompte: $("#txtNumCompteBenef").val(),
        montant: mont_opi,
        idTpagcp: tpagcpId,
        nbreOpi: nbre_opi,
        traites: listOpi
      };
      var csrf = $('input[name="_csrf"]').val();
      $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: {
          "X-CSRF-Token": csrf
        },
        url: "approOpi/",
        data: JSON.stringify(approOpi),
        success: function(data) {
          alert(data.message);
        }
      });
    }
  });
});
