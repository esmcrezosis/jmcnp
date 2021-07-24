/**
 * 
 */
(function ($) {

	var $codeMembreRevendeur;
	var $super_notification = $('#intermediaire_notification');
	var $super_notification_final = $('#intermediaire_notification_final');
	var $montantBonNeutre;
	var $codeBonconso="";
	var $codeEnvoi;
	var $designationProduit;
	var $codeTegc="";
	var $montantBan;
	var $modePayement;
	var $numeroCompteBancaire;
	var $numeroTelephone;
	var $reutiliser=0;
	var $periodeReutilisation=0;
	var $referencePayement;
	var $typeBon ="";




	$('body').on('blur', '#form-intermediaire-code-membre', function(e){
		e.preventDefault();
		$('#form-interim-select-te').html('');
		$('#labelte').css({'display':'none'});
		$('#form-interim-select-te').css({'display':'none'});
		$('#form-intermediaire-select-telephone').html('');
		$('#labelteleph').css({'display':'none'});
		$('#form-intermediaire-select-telephone').css({'display':'none'});


		$codeMembreRevendeur = $('#form-intermediaire-code-membre').val();
		console.log('$codeMembreRevendeur= '+$codeMembreRevendeur);
		if($codeMembreRevendeur===''){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Attention le codemembre ne doit pas être vide');  
			$('#form-intermediaire-code-membre').focus();
		}else if($codeMembreRevendeur.length<20){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Veuillez vérifier le code membre saisi');  
			$('#form-intermediaire-code-membre').focus();
		}else {
			$.ajax({
				url:"listtegcRevendeur/"+$codeMembreRevendeur,
				type:'GET',
				datatype:'json',
				contentType:'application/json',
				success: function(data){
					$('#form-interim-select-te').html('');
					var options = $('#form-interim-select-te');

					if(data.length ===0){
						alert('Ce Revendeur n a pas de TE. Veuillez lui en creer un avant toute opération ');  

					}else{

						for (var i in data) {
							options.append(new Option(data[i].nomTegc, data[i].codeTegc)); 
						}
						$('#labelte').css({'display':'inline-block'});
						$('#form-interim-select-te').css({'display':'inline-block'});

						$codeTegc = $('#form-interim-select-te option:selected').val();
						console.log("$codeTegc: "+$codeTegc);

					}
				}
			});
			$.ajax({
				url:"listeTelephoneint/"+$codeMembreRevendeur,
				type:'GET',
				datatype:'json',
				contentType:'application/json',
				success: function(data){
					$('#form-intermediaire-select-telephone').html('');
					var options = $('#form-intermediaire-select-telephone');

					if(data.length ===0){
						alert('Ce Revendeur n a pas de numero de telephone. Veuillez en ajouter dans son espace personnel! ');  

					}else{

						for (var i in data) {
							options.append(new Option(data[i].numeroTelephone)); 
						}
						$('#labelteleph').css({'display':'inline-block'});
						$('#form-intermediaire-select-telephone').css({'display':'inline-block'});

						$numeroTelephone = $('#form-intermediaire-select-telephone option:selected').val();
						console.log("$numeroTelephone: "+$numeroTelephone);

					}
				}
			});
			
			
		}

	});

	$('body').on('click','#form-interim-select-te',function(e){
		$codeTegc = $('#form-interim-select-te option:selected').val();
		console.log("$codeTegc: "+$codeTegc);
	});

	$('body').on('click','#form-intermediaire-select-telephone', function(e){
		$numeroTelephone =	$('#form-intermediaire-select-telephone option:selected').val();
	});

	/*$('body').on('blur', '#text-prix-ban', function(e){
		e.preventDefault();

		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');

			$montantBan = parseInt($('#text-prix-ban').val());
			console.log("$montantBan: "+$montantBan);
			if($montantBan> 0){
				$.ajax({
					url:"calculBonConso/"+$montantBan,
					type:'GET',
					datatype:'json',
					contentType:'application/json',
					success: function(data){
						if(data==0){
							$super_notification.addClass('alert alert-danger');
							$super_notification.text('Veuillez bien sélectionner les données'); 
						}else{
							$('#text-prix').val(''+data+'');
							$('#text-prix-ban').val(''+0+'');
						}

					}
				});

			}
		});	*/

	$('body').on('click','#oui_sousc_int', function(e){
		$('#label_periode_int').css({'display':'inline-block'});
		$('#periode_sousc_int').css({'display':'inline-block'});
	});

	$('body').on('click','#non_sousc_int', function(e){
		$('#label_periode_int').css({'display':'none'});
		$('#periode_sousc_int').css({'display':'none'});
	});

	//choix de moyens de paiement

	$('body').on('click','#form-interm-select-payement', function(e){
		e.preventDefault();
		
		$modePayement = $('#form-interm-select-payement option:selected').val();
		console.log("$modePayement= "+$modePayement);
		$numeroTelephone =	$('#form-perso-select-telephone option:selected').val();	
		if($modePayement == "WARI" || $modePayement == "FLOOZ"){
			$('#text-compte-bancaire-interm').val(''+$numeroTelephone+'');
			$referencePayement = $numeroTelephone;
			$numeroCompteBancaire="";
		}else{
			$('#text-compte-bancaire-interm').val(" ");
			$numeroTelephone="";

		}
	});




	// envoi de sms et ouverture de la boite de dialogue 
	$('body').on('click','#form-intermediaire-btConfirm',function(e){
		e.preventDefault();
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');
		if($('#radio_ban').is(':checked') === true){
			$typeBon = "BAN";
			console.log("$typeBon: "+$typeBon);
		}else if($('#radio_bai').is(':checked') === true){
			$typeBon = "BAI";
			console.log("$typeBon: "+$typeBon);
		}
		//recuperation du code membre

		$codeMembreRevendeur = $('#form-intermediaire-code-membre').val();
		console.log('$codeMembreRevendeur= '+$codeMembreRevendeur);
		if($codeMembreRevendeur===''){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Attention le codemembre ne doit pas être vide');  
			$('#form-intermediaire-code-membre').focus();
		}else if($codeMembreRevendeur.length<20){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Veuillez vérifier le code membre saisi');  
			$('#form-intermediaire-code-membre').focus();
		}else{

			//recuperation du montant 
			$montantBon= parseInt($('#text-montant-intermediaire-ban').val());
			console.log("$montantBon: "+$montantBon);

			if($montantBon<= 0){
				$super_notification.removeClass('alert alert-danger');
				$super_notification.text('');
				$super_notification.addClass('alert alert-danger');
				$super_notification.text('Le montant du BAn est invalide');
				$('#text-montant-intermediaire-ban').focus();

			}else {
				$modePayement = $('#form-interm-select-payement option:selected').val();
				$numeroTelephone =	$('#form-intermediaire-select-telephone option:selected').val();
				if($modePayement == "WARI" || $modePayement == "FLOOZ"){
					$referencePayement = $numeroTelephone;
					
				}else{
					$numeroCompteBancaire = 
					$referencePayement = $('#text-compte-bancaire-interm').val(); 
					
				}
				
				if(!$referencePayement){
					$numeroTelephone =	$('#form-perso-select-telephone option:selected').val();
					$modePayement = "WARI"
					$referencePayement=$numeroTelephone;
				}

					$('#load_createbon_intermediaire').css({'display':'inline-block'});
					$('#load_createbon_intermediaire_icone').css({'display':'inline-block'});
					$csrf = $('input[name="_csrf"]').val();

					rechercheMontant = "typeBon="+$typeBon+"&montantBon="+$montantBon+"&codeMembreRevendeur="+$codeMembreRevendeur+"&numeroTelephone="+$numeroTelephone+"&_csrf=" + $csrf + "";

					$.ajax({
						url:"sendSmsRevendeur",
						type:'POST',
						data: rechercheMontant,
						success: function(data){

							$('#load_createbon_intermediaire').fadeOut();
							$('#load_createbon_intermediaire_icone').fadeOut();
							if(data[0]=="KO0"){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('Erreur d envoi du SMS');    
							}else  if(data[0]=="KO1"){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('Le montant du BAn est insuffisant');    
							}else if(data[0]=="KO2"){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('Pas de BAn'); 
							}else if(data[0]=="KO3"){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('revoir la saisie des données'); 
							}else{
								$('#text-codesms-intermediaire').val('ESMC '+data[0]+'');
								$('#notification_sms_intermediaire').css({'display':'inline-block'});
								$codeEnvoi = data[0];
								console.log("$codeEnvoi= "+$codeEnvoi);
							}
						},
						error: function (xhr, textStatus, errorThrown) {
							$('#load_createbon_intermediaire').fadeOut();
							$('#load_createbon_intermediaire_icone').fadeOut();
							$super_notification.addClass('alert alert-danger');
							$super_notification.text('La communication avec le serveur a echoué. Veuillez informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

						}


					}); 

				
			}
		}

	}); 



//	bouton FINAL VALIDER 

	$('body').on('click', '#form-intermediaire-valider', function (e) {
		e.preventDefault();
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text(''); 

		//recuperation du code du bon de consommation

		$codeBonConso = $('#form-intermediaire-bonConso').val();
		console.log('$codeBonConso = '+$codeBonConso);
		if($codeBonConso===""){
			$('#load_validate_intermediaire').fadeOut();
			$('#load_validate_intermediaire_icone').fadeOut();
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('le code du bon de consommation ne doit pas être vide');
			$('#form-intermediaire-bonConso').focus();
		}else{	

			//recuperation du code membre

			$codeMembreRevendeur = $('#form-intermediaire-code-membre').val();
			console.log('$codeMembreRevendeur= '+$codeMembreRevendeur);
			if($codeMembreRevendeur===''){
				$super_notification.removeClass('alert alert-danger');
				$super_notification.text('');  
				$super_notification.addClass('alert alert-danger');
				$super_notification.text('Attention le codemembre ne doit pas être vide');  
				$('#form-intermediaire-code-membre').focus();
			}else if($codeMembreRevendeur.length<20){
				$super_notification.removeClass('alert alert-danger');
				$super_notification.text('');  
				$super_notification.addClass('alert alert-danger');
				$super_notification.text('Veuillez vérifier le code membre saisi');  
				$('#form-intermediaire-code-membre').focus();
			}else{


				//recuperation du montant 
				$montantBon = parseInt($('#text-montant-intermediaire-ban').val());
				console.log("$montantBon: "+$montantBon);
				if($montantBon <= 0){
					$('#load_validate_intermediaire').fadeOut();
					$('#load_validate_intermediaire_icone').fadeOut();
					$super_notification.removeClass('alert alert-danger');
					$super_notification.text('');
					$super_notification.addClass('alert alert-danger');
					$super_notification.text('Le montant est invalide');
					$('#text-montant-intermediaire-ban').focus();

				}else{
					$designationProduit = $('#form-interm-select-produit option:selected').val();
					console.log("$designationProduit: "+$designationProduit);
					if($designationProduit===""){
						$('#load_validate_intermediaire').fadeOut();
						$('#load_validate_intermediaire_icone').fadeOut();
						$super_notification.removeClass('alert alert-danger');
						$super_notification.text('');
						$super_notification.addClass('alert alert-danger');
						$super_notification.text('choisir le produit revendeur');
						$('#form-interm-select-produit').focus();
					}else{
						//mode de paiement et reinjection


						if($('#oui_sousc_int').is(':checked') === true){
							$periodeReutilisation = $('#periode_sousc_int option:selected').val();
							$reutiliser=1;
							console.log("$periodeReutilisation "+$periodeReutilisation);
							console.log("$reutiliser "+$reutiliser);
						}
						if($('#non_sousc_int').is(':checked') === true){
							$reutiliser=0;
							$periodeReutilisation =0;
							console.log("$periodeReutilisation "+$periodeReutilisation);
							console.log("$reutiliser "+$reutiliser);
						}
						$modePayement = $('#form-interm-select-payement option:selected').val();
						if($modePayement == "WARI" || $modePayement == "FLOOZ"){
							$numeroTelephone =	$('#form-intermediaire-select-telephone option:selected').val();
							$referencePayement = $numeroTelephone;
							$numeroCompteBancaire="";
						}else{
							$numeroCompteBancaire = $('#text-compte-bancaire-interm').val();
							$referencePayement = $numeroCompteBancaire;
							$numeroTelephone="";

						}
					
						if(!$referencePayement){
							$modePayement = "WARI";
							$referencePayement ="";
						}

							$('#load_validate_intermediaire').css({'display':'inline-block'});
							$('#load_validate_intermediaire_icone').css({'display':'inline-block'});

							$csrf = $('input[name="_csrf"]').val();

							reglement = "typeBon="+$typeBon+"&designationProduit="+$designationProduit+"&codeMembreRevendeur="+$codeMembreRevendeur+"&modePayement="+$modePayement+"&periodeReutilisation="+$periodeReutilisation+"&reutiliser="+$reutiliser+"&referencePayement="+$referencePayement+"&codeTegc="+$codeTegc+"&codeEnvoi="+$codeEnvoi+"&codeBonConso="+$codeBonConso+"&montantBon="+$montantBon+"&_csrf=" + $csrf + "";

							$.ajax({
								url:"executerReglementRevendeurByBan",
								type:'POST',
								data: reglement,
								success: function(data){
									console.log("data= "+data);
									$('#load_validate_intermediaire').fadeOut();
									$('#load_validate_intermediaire_icone').fadeOut();
									if(data===0){

										$('#text-codesms-intermediaire').val('');
										$('#notification_sms_intermediaire').css({'display':'none'});
										$super_notification_final.css({'display':'block'});
										$('#form-intermediaire-bonConso').val('');
										$('#form-intermediaire-code-membre').val('');
										$('#labelte').css({'display':'none'});
										$('#form-interim-select-te').html('');
										$('#form-interim-select-te').css({'display':'none'});
										$('#text-montant-intermediaire-ban').val('0');
										$codeMembreRevendeur='';
										$codeBonConso='';
										$numeroCompteBancaire ="";
										$numeroTelephone ="";
										$periodeReutilisation ="";
										$referencePayement="";
										
										$('#oui_sousc_int').prop('checked', false);
										$('#non_sousc_int').prop('checked', false);
										$('#text-compte-bancaire-interm').val('');
										$('#periode_sousc_int').css({'display':'none'});
										$('#label_periode_int').css({'display':'none'});
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
									$('#load_validate_intermediaire').fadeOut();
									$('#load_validate_intermediaire_icone').fadeOut();
									$super_notification.addClass('alert alert-danger');
									$super_notification.text('La communication avec le serveur a echoué. Veuillez en informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

								}
							});

						

					}
				}
			}
		}

	});




	$('body').on('click', '#form-intermediaire-annuler', function (e) {
		e.preventDefault();
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');

		$('#form-intermediaire-bonConso').val('');
		$('#form-intermediaire-code-membre').val('');
		$('#labelte').css({'display':'none'});
		$('#form-interim-select-te').css({'display':'none'});
		$('#text-codesms-intermediaire').val('');
		$('#notification_sms_intermediaire').css({'display':'none'});
		$('#text-montant-intermediaire-ban').val('0');
		$codeBonConso='';
		$codeMembreRevendeur='';

	});

	$('body').on('click', '#notification_sms_final_close_intermediaire', function (e) {
		e.preventDefault();
		$('#notification_sms_intermediaire').css({'display':'none'});
	});

	$('body').on('click', '#intermediaire_notification_final_close', function (e) {
		e.preventDefault();
		$('#intermediaire_notification_final').css({'display':'none'});
	});





})(jQuery);
