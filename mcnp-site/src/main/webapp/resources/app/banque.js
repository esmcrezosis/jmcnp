/**
 * http://usejsdoc.org/
 */
$(document).ready(function() {
	
	$.getJSON("listePayement/",  function(data) {
		var options = $("#smcipn_modePaiement1" );
		var i;
		for (i = 0; i < data.length; i++) {
			options.append(new Option(data[i].codeBanque, data[i].codeBanque));
		}
	});
			
	$('#opi_codeMembre').blur(function(e) {
		var param = "codeMembre=" + $(this).val();
		$.getJSON("te/", param, function(data) {
			var options = $("#opi_tegc");
			var i;
			for (i = 0; i < data.length; i++) {
				options.append(new Option(data[i].nomTegc, data[i].codeTegc));
			}
		});
	});

	$('#opi_montant').blur(function(e) {
		e.preventDefault();
		if (parseFloat($(this).val()) > 0) {
			if($('input[type="checkbox"]#ckMarge').is(":checked")){
				var param = "montant=" + $(this).val();
				$.getJSON("calculMontOpi/", param, function(data) {
					$('#opi_mont_maj').val(data);
					if (parseInt($('#opi_nbre').val()) > 0) {
						var param = '';
						if (parseFloat($('#opi_tranche1').val()) > 0) {
							param = "montant=" + $('#opi_mont_maj').val() + "&tranche1=" + $('#opi_tranche1').val() + "&nbre=" + $('#opi_nbre').val();
						} else {
							param = "montant=" + $('#opi_mont_maj').val() + "&nbre=" + $('#opi_nbre').val();
						}
						$.getJSON("calculMontTranche/", param, function(data) {
							$('#opi_tranche').val(data);
						});
				  }
				});
			} else {
				$('#opi_mont_maj').val($(this).val());
				if (parseInt($('#opi_nbre').val()) > 0) {
					var param = '';
					if (parseFloat($('#opi_tranche1').val()) > 0) {
						param = "montant=" + $('#opi_mont_maj').val() + "&tranche1=" + $('#opi_tranche1').val() + "&nbre=" + $('#opi_nbre').val();
					} else {
						param = "montant=" + $('#opi_mont_maj').val() + "&nbre=" + $('#opi_nbre').val();
					}
					$.getJSON("calculMontTranche/", param, function(data) {
						$('#opi_tranche').val(data);
					});
			}
			}
		}
	});

	$('#opi_tranche1').blur(function(e) {
		e.defaultPrevented;
		var param = '';
		if ((parseFloat($('#opi_mont_maj').val()) > 0) && (parseFloat($(this).val()) > 0) && (parseInt($('#opi_nbre').val()) > 0)) {
			param = "montant=" + $('#opi_mont_maj').val() + "&tranche1=" + $(this).val() + "&nbre=" + $('#opi_nbre').val();
			$.getJSON("calculMontTranche/", param, function(data) {
				$('#opi_tranche').val(data);
			});
		}
	});

	$('#cycle').change(function(e) {
		if ($(this).val() !== '') {
			var param = "cycle=" + $(this).val();
			$.getJSON("cycle/", param, function(data) {
				if (data !== null) {
					console.log(data.dureeCycleFormation);
					$('#dureeForm').val(data.dureeCycleFormation);
				} else {
					alert("Ce cycle n'existe pas");
				}
			});
		}
	});

	$('#montFormation').blur(function(e) {
		e.preventDefault();
		if (parseFloat($(this).val()) > 0 && $('#cycle').val() !== '') {
			var montForm = parseFloat($(this).val());
			var param = "cycle=" + $('#cycle').val();
			var montSous = 0,
				montbc = 0;
			$.getJSON("cycle/", param, function(data) {
				if (data !== null) {
					montbc = Math.floor(montForm / data.dureeAnneeFormation);
					montSous = Math.floor(montbc * data.tauxCycleFormation);
					console.log(montbc);
					$('#dureeForm').val(data.dureeCycleFormation);
					$('#montBonconso').val(montbc);
					$('#montSouscription').val(montSous);
				} else {
					alert("Ce cycle n'existe pas");
				}
			});
		} else {
			console.log("error");
		}
	});

	$('#opi_nbre').blur(function(e) {
		if (parseInt($(this).val()) > 0 && parseFloat($('#opi_mont_maj').val()) > 0) {
			var param = '';
			if (parseFloat($('#opi_tranche1').val()) > 0) {
				param = "montant=" + $('#opi_mont_maj').val() + "&tranche1=" + $('#opi_tranche1').val() + "&nbre=" + $('#opi_nbre').val();
			} else {
				param = "montant=" + $('#opi_mont_maj').val() + "&nbre=" + $('#opi_nbre').val();
			}
			$.getJSON("calculMontTranche/", param, function(data) {
				$('#opi_tranche').val(data);
			});
		}
	});
	
	$('#codeMembreSmcipn').blur(function(e){
		e.defaultPrevented;
		$.get(
				'nomMembre/',
				{
					codeMembre : $(this).val()
				},
				function success(data) {
					if (data != "") {
						$('#nomMembreSmcipn').val(data);
					} else {
						alert('Pas de Membre correspondant à ce code');
					}
				});
	});
	
	$('#ls_codeMembreAnim').blur(function(e){
		e.defaultPrevented;
		$.get(
				'nomMembre/',
				{
					codeMembre : $(this).val()
				},
				function success(data) {
					if (data != "") {
						$('#ls_nomMembreAnim').val(data);
					} else {
						alert('Pas de Membre correspondant à ce code');
					}
				});
	});

	$('#codeMembreBenef').blur(function(e) {
		e.defaultPrevented;
		$.get(
				'nomMembre/',
				{
					codeMembre : $(this).val()
				},
				function success(data) {
					if (data != "") {
						$('#nomMembreBenef').val(data);
					} else {
						alert('Pas de Membre correspondant à ce code');
					}
				});
		var param = "codeMembre=" + $(this).val();
		$.getJSON("te/", param, function(data) {
			var options = $("#smcipn_tegc");
			options.html('');
			var i;
			for (i = 0; i < data.length; i++) {
				options.append(new Option(data[i].nomTegc, data[i].codeTegc));
			}
		});
	});

	$('#codeMembreBenefBnp').blur(function(e) {
		e.preventDefault();
		var param = "codeMembre=" + $(this).val();
		$.getJSON("membre/", param, function(data) {
			$('input#nomMembreBenefBnp').val(data.rep);
		});
	});

	$('#codeMembreApp').blur(function(e) {
		e.defaultPrevented;
		var param = "codeMembre=" + $(this).val();
		$.getJSON("te/", param, function(data) {
			var options = $("#codeTegcApp");
			options.html('');
			var i;
			for (i = 0; i < data.length; i++) {
				options.append(new Option(data[i].nomTegc, data[i].codeTegc));
			}
		});
	});

	$('#type_paiement').change(function(e) {
		e.preventDefault();
		if ($(this).val() !== '') {
			if ($(this).val() === 'SR') {
				$('select#smcipn_type').val('TPN');
			} else {
				$('select#smcipn_type').val('I');
			}
			$('select#smcipn_type').attr('disabled', true);
		} else {
			$('select#smcipn_type').attr('disabled', false);
		}
	});

	$('#numAppelOffre').blur(function(e) {
		e.preventDefault();
		if ($(this).val() !== '') {
			var params = "numero=" + $(this).val() + "&membre=" + $('#codeMembreBenef').val() + "&type=" + $('#type_commis').val();
			$.getJSON("demande/", params, function(data) {
				if (data !== null) {
					$('#nomAppelOffre').val(data.libelle);
					$('#smc_montant').val(data.montant);
				}
			});
		}

	});

	$('input[type="checkbox"]#ckformation').click(function() {
		if ($(this).is(":checked")) {
			$('#typeRecurrent').val("limite");
			$('#typeRecurrent').prop('disabled', true);
			$('#formation').css('display', 'block');
		} else if ($(this).is(":not(:checked)")) {
			$('#typeRecurrent').prop('disabled', false);
			$('#formation').css('display', 'none');
		}

	});
	
	$('input[type="checkbox"]#ckTva').click(function() {
		if ($(this).is(":checked")) {
			$('#txtTauxTva').prop('readonly', false);
		} else if ($(this).is(":not(:checked)")) {
			$('#txtTauxTva').prop('readonly', true);
		}

	});

	$('#montBnp').blur(function(e) {
		e.preventDefault();
		if (parseFloat($(this).val()) > 0) {
			var params = "typeBnp=" + $('#typeBnp').val() + "&typeProduit=" + $('#typeProduit').val() + "&montantBnp=" + parseFloat($(this).val());
			$.getJSON("calculBcTiers/", params, function(data) {
				$('#montBonconso').val(data);
			});
		}
	});

	$('#montBonconso').blur(function(e) {
		e.defaultPrevented;
		if (parseFloat($(this).val()) > 0) {
			var params = "typeBnp=" + $('#typeBnp').val() + "&typeProduit=" + $('#typeProduit').val() + "&montBc=" + parseFloat($(this).val());
			$.getJSON("calculMsbcTiers/", params, function(data) {
				$('#montBnp').val(data);
			});
		}
	});

	$('#opi_tegc').change(function(e) {
		e.defaultPrevented;
		var params = "codeTegc=" + $(this).val();
		$.getJSON("nbreOpi/", params, function(data) {
			$('#opi_nbre').val(data);
		});
	});

	$('#smcipn_codeMembreDebiteur').blur(function(e) {
		e.preventDefault();
		if ($(this).val() !== '') {
			/*$.get(
				'nomMembre/',
				{
					codeMembre : $(this).val()
				},
				function success(data) {
					if (data != "") {
						$('#smcipn_nomMembreDebiteur').val(data);
					} else {
						alert('Pas de Membre correspondant à ce code');
					}
				});*/
			var param = "codeMembre=" + $(this).val();
			$.getJSON("te/", param, function(data) {
				var options = $("#smcipn_CodeTegcp");
				var i;
				for (i = 0; i < data.length; i++) {
					options.append(new Option(data[i].nomTegc, data[i].codeTegc));
				}
			});
		}
	});

	$('#codeMembreCreancier1').blur(function(e) {
		e.preventDefault();
		if ($(this).val() !== '') {
			$.get(
				'nomMembre/',
				{
					codeMembre : $(this).val()
				},
				function success(data) {
					if (data != "") {
						$('#nomMembreCreancier1').val(data);
					} else {
						alert('Pas de Membre correspondant à ce code');
					}
				});
			var param = "codeMembre=" + $(this).val();
			$.getJSON("te/", param, function(data) {
				var options = $("#codeTegc1");
				var i;
				for (i = 0; i < data.length; i++) {
					options.append(new Option(data[i].nomTegc, data[i].codeTegc));
				}
			});
		}
	});

	$('#montantOp1').blur(function(e) {
		e.preventDefault();
		if ($(this).val() !== '') {
			var montant = 0;
			if ($('#smcipn_montantOp').val() !== '') {
				montant = parseFloat($('#smcipn_montantOp').val());
			}
			montant += parseFloat($(this).val());
			$('#smcipn_montantOp').val(montant);
		}
	});

	$('#budget_id').change(function(e) {
		e.preventDefault();
		var param = "type=" + $(this).val();
		$.getJSON("loadCharge/", param, function(data) {
			var options = $("#smcipn_codeCharge");
			options.html('');
			var i;
			for (i = 0; i < data.length; i++) {
				options.append(new Option(data[i].libelleCharge, data[i].codeCharge));
			}
		});
	});

	$('#btn-val-kit-budget').on('click', function(e) {
		e.preventDefault();
		var creances = new Array();
		var cpteur = $('#cpteur').val();
		var i = 0;
		var charge = {
			numeroAppel : $('#smcipn_numero').val(),
			typeCharge : $('#budget_id').val(),
			codeCharge : $('#smcipn_codeCharge').val(),
			origineCharge : $('#smcipn_origineCharge').val(),
			codeMembreDebiteur : $('#smcipn_codeMembreDebiteur').val(),
			codeTegcCreancier : $('#smcipn_CodeTegcp').val(),
			montantCharge : $('#smcipn_montantOp').val(),
			libelleCharge : $('#smcipn_libOp').val()
		};
		for (i = 1; i <= cpteur; i++) {
			var marge = 0;
			if($('input[type="checkbox"]#smcipn_opimarge' + i).is(":checked")){
				marge = 1;
			}
			var creance = {
				codeMembreCreancier : $('#codeMembreCreancier' + i).val(),
				codeTegc : $('#codeTegc' + i).val(),
				montantOp : $('#montantOp' + i).val(),
				modePaiement: $('#smcipn_modePaiement' + i).val(),
				referencePaiement : $('#smcipn_refPaiement' + i).val(),
				nbreOpi : $('#smcipn_nbreOpi' + i).val(),
				marge : marge,
				numDoc : $('#numDoc' + i).val()
			};
			creances.push(creance);
		}
		charge.creances = creances;
		if (creances.length > 0 && charge.codeMembreDebiteur !== '') {
			var csrf = $('input[name="_csrf"]').val();
			$.ajax({
				type : 'POST',
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				headers : {
					'X-CSRF-Token' : csrf
				},
				url : 'payerCharge/',
				data : JSON.stringify(charge),
				success: function(data){
						alert(data.message);
				}
			});
		}
	});
	
	$('#btn-val-salaire').on('click', function(e) {
		e.preventDefault();
		var creances = new Array();
		var cpteur = $('#cpteur').val();
		var i = 0;
		var charge = {
			numeroAppel : $('#smcipn_numero').val(),
			libelleCharge : $('#smcipn_libelleCharge').val(),
			codeCharge : $('#smcipn_codeCharge').val(),
			montantCharge : $('#smcipn_montantOp').val()
		};
		for (i = 1; i <= cpteur; i++) {
			var marge = 0;
			if($('input[type="checkbox"]#smcipn_opimarge' + i).is(":checked")){
				marge = 1;
			}
			var creance = {
				codeMembreCreancier : $('#codeMembreCreancier' + i).val(),
				codeTegc : $('#codeTegc' + i).val(),
				montantOp : $('#montantOp' + i).val(),
				modePaiement: $('#smcipn_modePaiement' + i).val(),
				referencePaiement : $('#smcipn_refPaiement' + i).val(),
				nbreOpi : $('#smcipn_nbreOpi' + i).val(),
				marge : marge,
				numDoc : $('#numDoc' + i).val()
			};
			creances.push(creance);
		}
		charge.creances = creances;
		if (creances.length > 0 && charge.codeMembreDebiteur !== '') {
			var csrf = $('input[name="_csrf"]').val();
			$.ajax({
				type : 'POST',
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				headers : {
					'X-CSRF-Token' : csrf
				},
				url : 'payerSalaire/',
				data : JSON.stringify(charge),
				success: function(data){
					alert(data.message);
				}
			});
		}
	});

	$('#ancCodeMembre').blur(function(e) {
		e.preventDefault();
		var param = "codeMembre=" + $(this).val() + "&type=" + $("#typeCreance").val() + "&ressource=" + $("#typeRessource").val();
		$.getJSON("ancNomMembre/", param, function(data) {
			$('input#nomMembreAnc').val(data.rep);
		});
	});

	$('#newCodeMembre').blur(function(e) {
		e.preventDefault();
		var param = "codeMembre=" + $(this).val();
		$.getJSON("membre/", param, function(data) {
			$('input#newNomMembre').val(data.rep);
		});
	});

	$("#typeRessource").change(function(e) {
		e.preventDefault();
		if ($(this).val() === "MF11000") {
			$("label[for='ancCodeMembre']").text("Numéro du Bon");
		} else {
			$("label[for='ancCodeMembre']").text("Ancien Code Membre");
		}
	});

	$('#sscodeMembreBenefBnp').blur(function(e) {
		e.preventDefault();
		var param = "codeMembre=" + $(this).val();
		$.getJSON("membre/", param, function(data) {
			$('input#ssnomMembreBenefBnp').val(data.rep);
		});
	});

	$('#sscodeMembreApp').blur(function(e) {
		e.defaultPrevented;
		var param = "codeMembre=" + $(this).val();
		$.getJSON("membre/", param, function(data) {
			$('input#ssnomMembreApp').val(data.rep);
		});
	});
	
	$('#codeMembreBan').blur(function(e) {
		e.defaultPrevented;
		var param = "codeMembre=" + $(this).val();
		$.getJSON("raisonMorale/", param, function(data) {
			$('input#raison_sociale_ban').val(data.nom);
		});
	});
	
	$('#codeMembreBanAff').blur(function(e) {
		e.defaultPrevented;
		var param = "codeMembre=" + $(this).val();
		$.getJSON("nomMembreAff/", param, function(data) {
			$('input#raisonSocialeBanAff').val(data.nom);
		});
	});

});