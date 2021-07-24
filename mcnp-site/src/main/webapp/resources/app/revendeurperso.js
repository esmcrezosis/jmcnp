/**
 * 
 */
(function ($) {

	var $montantBonNeutre;
	var $super_notification = $('#perso_notification');
	var $super_notification_final = $('#perso_notification_final');
	var $codeBonconso="";
	var $codeEnvoi;
	var $designationProduit;
	var $codeTegc="";
	var $montantBon;
	var $modePayement = "";
	var $numeroCompteBancaire = "";
	var $numeroTelephone = "";
	var $reutiliser=0;
	var $periodeReutilisation=0;
	var $referencePayement = "";
	var $typeBon ="BAN";

	$('body').on('click','#oui_sousc_perso', function(e){
		$('#label_periode_perso').css({'display':'inline-block'});
		$('#periode_sousc_perso').css({'display':'inline-block'});
	});

	$('body').on('click','#non_sousc_perso', function(e){
		$('#label_periode_perso').css({'display':'none'});
		$('#periode_sousc_perso').css({'display':'none'});
	});



	$('body').on('click','#form-perso-select-produit', function(e){
		e.preventDefault();
		$.ajax({
			url:"revproduit",
			type:'GET',
			datatype:'json',
			contentType:'application/json',
			success: function(data){
				$('#form-perso-select-produit').html('');
				var options = $('#form-perso-select-produit');
				for (var i in data) {
					options.append(new Option(data[i])); 
				}
			}
		});
		$.ajax({
			url:"listeTeSousPerso",
			type:'GET',
			datatype:'json',
			contentType:'application/json',
			success: function(data){
				$('#form-perso-select-te').html('');
				var options = $('#form-perso-select-te');

				if(data.length ===0){
					alert('Pas de TE disponible!. Veuillez en creer un avant toute opération ');  
				}else{

					for (var i in data) {
						options.append(new Option(data[i].nomTegc, data[i].codeTegc)); 
					}
					$codeTegc = $('#form-perso-select-te option:selected').val();
					console.log("$codeTegc: "+$codeTegc);

				}
			}
		});


	});

	$('body').on('click','#form-perso-select-telephone', function(e){
		$numeroTelephone =	$('#form-perso-select-telephone option:selected').val();
	});




	//choix de moyens de paiement

	$('body').on('click','#form-perso-select-payement', function(e){
		e.preventDefault();
		$modePayement = $('#form-perso-select-payement option:selected').val();
		console.log("$modePayement= "+$modePayement);
		$numeroTelephone =	$('#form-perso-select-telephone option:selected').val();	
		if($modePayement == "WARI" || $modePayement == "FLOOZ"){
			$('#text-compte-bancaire').val(''+$numeroTelephone+'');
			$referencePayement = $numeroTelephone;
			$numeroCompteBancaire="";
		}else{
			$('#text-compte-bancaire').val(" ");
			$numeroTelephone="";

		}
	});


	// envoi de sms et ouverture de la boite de dialogue 
	$('body').on('click','#form-perso-btConfirm',function(e){
		e.preventDefault();
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');

		//recuperation du téléphone
		$numeroTelephone =	$('#form-perso-select-telephone option:selected').val();
		console.log("$numeroTelephone: "+$numeroTelephone);
		
		if($('#radio_ban_perso').is(':checked') === true){
			$typeBon = "BAN";
			console.log("$typeBon: "+$typeBon);
		}else if($('#radio_bai_perso').is(':checked') === true){
			$typeBon = "BAI";
			console.log("$typeBon: "+$typeBon);
		}
		//recuperation du montant 
		$montantBon = parseInt($('#text-montant-ban').val());
		console.log("$montantBon: "+$montantBon);


		if($montantBon<= 0){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Le montant du BAn est invalide');
			$('#text-montant-ban').focus();

		}else if(!$numeroTelephone) {
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Il n y a pas de numero de téléphone valide!!');   
		}else{
			$modePayement = $('#form-perso-select-payement option:selected').val();
			console.log("$modePayement= "+$modePayement);
			$referencePayement = $('#text-compte-bancaire').val();
			console.log("$referencePayement "+$referencePayement);
			if(!$referencePayement){
				$numeroTelephone =	$('#form-perso-select-telephone option:selected').val();
				$modePayement = "WARI"
				$referencePayement=$numeroTelephone;
			}
			$('#load_perso').css({'display':'inline-block'});
			$('#load_perso_icone').css({'display':'inline-block'});
			$csrf = $('input[name="_csrf"]').val();

			rechercheMontant = "typeBon="+$typeBon+"&montantBon="+$montantBon+"&numeroTelephone="+$numeroTelephone+"&_csrf=" + $csrf + "";

			$.ajax({
				url:"sendSmsRevendeurPerso",
				type:'POST',
				data: rechercheMontant,
				success: function(data){

					$('#load_perso').fadeOut();
					$('#load_perso_icone').fadeOut();
					if(data[0]=="KO0"){
						$super_notification.addClass('alert alert-danger');
						$super_notification.text('Erreur d envoi du SMS');    
					}else if(data[0]=="KO1"){
						$super_notification.addClass('alert alert-danger');
						$super_notification.text('Le montant du BAn est insuffisant');    
					}else if(data[0]=="KO2"){
						$super_notification.addClass('alert alert-danger');
						$super_notification.text('Pas de BAn'); 
					}else if(data[0]=="KO3"){
						$super_notification.addClass('alert alert-danger');
						$super_notification.text('revoir la saisie des données'); 
					}else{
						$('#text-codesms-perso').val('ESMC '+data[0]+'');
						$('#notification_sms_perso').css({'display':'inline-block'});
						$codeEnvoi = data[0];
						$super_notification.addClass('alert alert-warning');
						$super_notification.text('Un sms est envoyé. Saisir le code pour poursuivre l operation'); 
					}
				},
				error: function (xhr, textStatus, errorThrown) {
					$('#load_perso').fadeOut();
					$('#load_perso_icone').fadeOut();
					$super_notification.addClass('alert alert-danger');
					$super_notification.text('La communication avec le serveur a echoué. Veuillez informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

				}


			}); 


		}//


	}); 



//	bouton FINAL VALIDER 

	$('body').on('click', '#form-perso-valider', function (e) {
		e.preventDefault();
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text(''); 

		//recuperation du code du bon de consommation

		$codeBonConso = $('#code_confirm_perso').val();
		console.log('$codeBonConso = '+$codeBonConso);
		if($codeBonConso===""){
			$('#load_validate_perso').fadeOut();
			$('#load_validate_perso_icone').fadeOut();
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('le code du bon de consommation ne doit pas être vide');
			$('#code_confirm_perso').focus();
		}else{	

			//recuperation du montant 
			$montantBon = parseInt($('#text-montant-ban').val());
			console.log("$montantBon: "+$montantBon);
			if($montantBon <= 0){
				$('#load_validate_perso').fadeOut();
				$('#load_validate_perso_icone').fadeOut();
				$super_notification.removeClass('alert alert-danger');
				$super_notification.text('');
				$super_notification.addClass('alert alert-danger');
				$super_notification.text('Revoir le bon de livraison...Le montant est invalide');
				$('#text-montant-ban').focus();

			}else{
				$designationProduit = $('#form-perso-select-produit option:selected').val();
				console.log("$designationProduit: "+$designationProduit);
				if($designationProduit===""){
					$('#load_validate_perso').fadeOut();
					$('#load_validate_perso_icone').fadeOut();
					$super_notification.removeClass('alert alert-danger');
					$super_notification.text('');
					$super_notification.addClass('alert alert-danger');
					$super_notification.text('choisir le produit revendeur');
					$('#form-perso-select-produit').focus();
				}else{
					//mode de paiement et reinjection


					if($('#oui_sousc_perso').is(':checked') === true){
						$periodeReutilisation = $('#periode_sousc_perso option:selected').val();
						$reutiliser=1;
						console.log("$periodeReutilisation "+$periodeReutilisation);
						console.log("$reutiliser "+$reutiliser);
					}
					if($('#non_sousc_perso').is(':checked') === true){
						$reutiliser=0;
						$periodeReutilisation =0;
						console.log("$periodeReutilisation "+$periodeReutilisation);
						console.log("$reutiliser "+$reutiliser);
					}
					$modePayement = $('#form-perso-select-payement option:selected').val();
					console.log("$modePayement= "+$modePayement);
					if($modePayement == "WARI" || $modePayement == "FLOOZ" || $modePayement == "TMONEY"){
						$numeroTelephone =	$('#form-perso-select-telephone option:selected').val();
						$referencePayement = $numeroTelephone;
						$numeroCompteBancaire="";
					}else{
						$numeroCompteBancaire = $('#text-compte-bancaire').val();
						$referencePayement = $numeroCompteBancaire;
						$numeroTelephone="";

					}

					if(!$referencePayement){
						$numeroTelephone =	$('#form-perso-select-telephone option:selected').val();
						$modePayement = "WARI"
						$referencePayement=$numeroTelephone;
					}

					$('#load_validate_perso').css({'display':'inline-block'});
					$('#load_validate_perso_icone').css({'display':'inline-block'});

					$csrf = $('input[name="_csrf"]').val();

					reglement = "typeBon="+$typeBon+"&designationProduit="+$designationProduit+"&modePayement="+$modePayement+"&periodeReutilisation="+$periodeReutilisation+"&reutiliser="+$reutiliser+"&referencePayement="+$referencePayement+"&codeTegc="+$codeTegc+"&codeEnvoi="+$codeEnvoi+"&codeBonConso="+$codeBonConso+"&montantBon="+$montantBon+"&_csrf=" + $csrf + "";

					$.ajax({
						url:"executerOperationRevendeur",
						type:'POST',
						data: reglement,
						success: function(data){
							console.log("data= "+data);
							$('#load_validate_perso').fadeOut();
							$('#load_validate_perso_icone').fadeOut();
							if(data===0){
								$super_notification_final.css({'display':'block'});
								$('#code_confirm_perso').val('');
								$('#text-montant-ban').val('0');
								$codeBonConso='';
								$numeroCompteBancaire ="";
								$numeroTelephone ="";
								$periodeReutilisation ="";
								$referencePayement="";
								$('#oui_sousc_perso').prop('checked', false);
								$('#non_sousc_perso').prop('checked', false);
								$('#text-compte-bancaire').val('');
								$('#periode_sousc_perso').css({'display':'none'});
								$('#label_periode_perso').css({'display':'none'});
							}else if(data===1){ 
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('Echec de la 1ere vente');
							}else if(data===2){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('Echec de souscription au bon de consommation');
							}else if(data===3){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('Echec de creation du bon de consommation');
							}else if(data===4){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('Echec de creation du bon d achat');
							}else if(data===5){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('Echec de souscription au bon d achat');
							}else if(data===6){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text(' Le revendeur n a pas de TE');
							}else if(data===7){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text(' Le code de confirmation est invalide');
							}else if(data===8){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('Revoir la saisie des données');
							}
						},
						error: function (xhr, textStatus, errorThrown) {
							$('#load_validate_perso').fadeOut();
							$('#load_validate_perso_icone').fadeOut();
							$super_notification.addClass('alert alert-danger');
							$super_notification.text('La communication avec le serveur a echoué. Veuillez en informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

						}
					});

				}

			}
		}

	});


	$('body').on('click', '#form-perso-annuler', function (e) {
		e.preventDefault();
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');

		$('#form-perso-bonConso').val('');
		$('#text-montant-ban').val('0');
		$('#text-codesms-perso').val('');
		$('#notification_sms_perso').css({'display':'none'});
		$codeBonConso='';
		$numeroCompteBancaire ="";
		$numeroTelephone ="";
		$periodeReutilisation ="";
		$referencePayement="";
	});

	$('body').on('click', '#notification_sms_final_close_perso', function (e) {
		e.preventDefault();
		$('#notification_sms_perso').css({'display':'none'});
	});

	$('body').on('click', '#perso_notification_final_close', function (e) {
		e.preventDefault();
		$('#perso_notification_final').css({'display':'none'});
	});


})(jQuery);
