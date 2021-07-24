
(function ($) {
	var $notification_smcipn = $('#notification_smcipn');
	var $final_notification_smcipn = $('#notification_final_smcipn');
	var $typeProduit;
	var $codeTegc;
	var $montantDemandeSmcipn;
	var $codeMembreDemandeur;
	var $reference;
	var $designation;
	var $codeBonConso;
	var $numAppelOffre;
	var $prk;
	var $listArticlesVendus=[];
	var $codeEnvoi;

	$(document).ready(
			function() {
				$.ajax({
					url:"smcipnordinaires",
					type:'GET',
					datatype:'json',
					contentType:'application/json',
					success: function(data){
						console.log(data);
						if(data===1){
							$('#label-radioor-smcipn').css({'display':'inline-block'});
							$('#form-smcipn-radio-ordinaire').css({'display':'inline-block'});

							$('#label-radios-smcipn').css({'display':'inline-block'});
							$('#form-smcipn-radio-speciale').css({'display':'inline-block'});
						}else if(data===2){
							$('#label-radioor-smcipn').css({'display':'inline-block'});
							$('#form-smcipn-radio-ordinaire').css({'display':'inline-block'});

							$('#label-radios-smcipn').css({'display':'none'});
							$('#form-smcipn-radio-speciale').css({'display':'none'});
						}else if(data===3){
							$('#label-radioor-smcipn').css({'display':'none'});
							$('#form-smcipn-radio-ordinaire').css({'display':'none'});

							$('#label-radios-smcipn').css({'display':'inline-block'});
							$('#form-smcipn-radio-speciale').css({'display':'inline-block'});

						}else if(data===4){
							$('#label-radioor-smcipn').css({'display':'none'});
							$('#form-smcipn-radio-ordinaire').css({'display':'none'});

							$('#label-radios-smcipn').css({'display':'none'});
							$('#form-smcipn-radio-speciale').css({'display':'none'});
						}
					}
				});
			});

//	PERTE DE FOCUS CODEMEMBRE POUR TROUVER LES TE

	/*$('body').on('blur','#text-codemembre-smcipn',function(e){
		$notification_smcipn.removeClass('alert alert-danger');
		$notification_smcipn.text('');
		$codeMembreDemandeur = $('#text-codemembre-smcipn').val();
		if($codeMembreDemandeur===''){
			$notification_smcipn.removeClass('alert alert-danger');
			$notification_smcipn.text('');  
			$notification_smcipn.addClass('alert alert-danger');
			$notification_smcipn.text('Veuillez renseigner le code du membre du demandeur');  
			$('#text-codemembre-smcipn').focus();
		}else if($codeMembreDemandeur.search('M')==-1){
			
		}else{

			$.ajax({
				url:"tegcsmcipn/"+$codeMembreDemandeur,
				type:'GET',
				datatype:'json',
				contentType:'application/json',
				success: function(data){

					$('#text-tesmcipn').val(''+data.nomTegc+'');
				}
			});
		}

	});	*/

	$('body').on('click', '#form-smcipn-radio-ordinaire',function(e){
		$typeProduit ="PO";
		console.log("$typeProduit= "+$typeProduit);
	});

	$('body').on('click', '#form-smcipn-radio-speciale',function(e){
		$typeProduit ="PS";
		console.log("$typeProduit= "+$typeProduit);
	});

	//bouton creer le bon
	
	$('body').on('click', '#form-smcipn-createBonConso',function(e){
		e.preventDefault();
		$notification_smcipn.removeClass('alert alert-danger');
		$notification_smcipn.text('');
		
		if($('#form-smcipn-radio-speciale').is(':checked') === true){
			$typeProduit ="PS";;
		}
		if($('#form-smcipn-radio-ordinaire').is(':checked') === true){
			$typeProduit ="PO";
		}
		$codeMembreDemandeur = $('#text-codemembre-smcipn').val();
		$reference = $('#text-reference-smcipn').val();
		$designation = $('#text-designation-smcipn').val();
		$codeBonConso = $('#form-smcipn-bonConso').val();	
		$numAppelOffre = $('#text-numAppelOffre').val();
		$prk = $('#form-field-select-prk-smcipn option:selected').val();
		
		
		$montantDemandeSmcipn = parseInt($('#montant-demande-smcipn').val());

		if($typeProduit===""){
			$notification_smcipn.addClass('alert alert-danger');
			$notification_smcipn.text('');
			$('#form-smcipn-radio-ordinaire').focus();
		}else if($codeMembreDemandeur===""){
			$notification_smcipn.addClass('alert alert-danger');
			$notification_smcipn.text('Veuillez renseigner le code membre');
			$('#text-codemembre-smcipn').focus();
		}else if($designation===""){
			$notification_smcipn.addClass('alert alert-danger');
			$notification_smcipn.text('Veuillez renseigner la designation de l achat');
			$('#text-designation-smcipn').focus;
		}else if($numAppelOffre===""){
			$notification_smcipn.addClass('alert alert-danger');
			$notification_smcipn.text('Veuillez renseigner le numero d appel d offre');
			$('#text-numAppelOffre').focus();
		}else if($montantDemandeSmcipn <= 0){
			$notification_smcipn.addClass('alert alert-danger');
			$notification_smcipn.text('Le montant est invalide');
			$('#montant-demande-smcipn').focus();

		}else if($montantDemandeSmcipn > 0){
			$('#load_createbon_smcipn').css({'display':'inline-block'});
			$('#load_createbon_smcipn_icone').css({'display':'inline-block'});

			$csrf = $('input[name="_csrf"]').val();
			recherche = "codeMembreDemandeur="+$codeMembreDemandeur+"&montantDemandeSmcipn="+$montantDemandeSmcipn+"&_csrf=" + $csrf + "";

			$.ajax({
				url:"sendSmsCodeBonSmcipn",
				type:'POST',
				data: recherche,
				success: function(data){
					console.log(data);
					$('#load_createbon_smcipn').fadeOut();
					$('#load_createbon_smcipn_icone').fadeOut();
					if(data[0]=="KO1"){
						$notification_smcipn.addClass('alert alert-danger');
						$notification_smcipn.text('Le montant disponible est insuffisant');
					}else if(data[0]=="KO2"){ 
						$notification_smcipn.addClass('alert alert-danger');
						$notification_smcipn.text('Revoir la saisie des données');
					}else{
						
						$('#text-codesms_smcipn').val('ESMC '+data[0]+'');
						$('#notification_sms_smcipn').css({'display':'inline-block'});
						$codeEnvoi = data[0];
						console.log("$codeEnvoi= "+$codeEnvoi);
					}
					
				},
				error: function (xhr, textStatus, errorThrown) {
					$('#load_createbon_smcipn').fadeOut();
					$('#load_createbon_smcipn_icone').fadeOut();
					$notification_smcipn.addClass('alert alert-danger');
					$notification_smcipn.text('La communication avec le serveur a echoué. Veuillez en informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

				}


			});

		}
	
	});
	
	

	//bouton valider final
	$('body').on('click', '#form-smcipn-valider',function(e){
		e.preventDefault();
		$notification_smcipn.removeClass('alert alert-danger');
		$notification_smcipn.text('');
		$listArticlesVendus = [];
		if($('#form-smcipn-radio-speciale').is(':checked') === true){
			$typeProduit ="PS";;
		}
		if($('#form-smcipn-radio-ordinaire').is(':checked') === true){
			$typeProduit ="PO";
		}
		$codeMembreDemandeur = $('#text-codemembre-smcipn').val();
		$reference = $('#text-reference-smcipn').val();
		$designation = $('#text-designation-smcipn').val();
		$codeBonConso = $('#form-smcipn-bonConso').val();	
		$numAppelOffre = $('#text-numAppelOffre').val();
		$prk = $('#form-field-select-prk-smcipn option:selected').val();
		
		$montantDemandeSmcipn = parseInt($('#montant-demande-smcipn').val());

		if($typeProduit===""){
			$('#form-smcipn-radio-ordinaire').focus();
		}else if($codeMembreDemandeur===""){
			$('#text-codemembre-smcipn').focus();
		}else if($designation===""){
			$('#text-designation-smcipn').focus;
		}else if($codeBonConso===""){
			$('#form-smcipn-bonConso').focus();
		}else if($numAppelOffre===""){
			$('#text-numAppelOffre').focus();
		}else if($montantDemandeSmcipn <= 0){


		}else if($montantDemandeSmcipn > 0){
			
			var $articlesVendus = new ArticleVendu('', $reference, $designation, 1, $montantDemandeSmcipn);
			$listArticlesVendus.push($articlesVendus);

			$jsonlist = JSON.stringify($listArticlesVendus);
			console.log('$jsonlist: '+ $jsonlist );
			
			
			$('#load_validate_smcipn').css({'display':'inline-block'});
			$('#load_validate_smcipn_icone').css({'display':'inline-block'});

			$csrf = $('input[name="_csrf"]').val();
			reglement = "designation="+$designation+"&prk="+$prk+"&numAppelOffre="+$numAppelOffre+"&codeEnvoi="+$codeEnvoi+"&codeBonConso="+$codeBonConso+"&reference="+$reference+"&typeProduit="+$typeProduit+"&codeMembreDemandeur="+$codeMembreDemandeur+"&montantDemandeSmcipn="+$montantDemandeSmcipn+"&listArticlesVendus="+$jsonlist+"&_csrf=" + $csrf + "";

			$.ajax({
				url:"reglementSmcipn",
				type:'POST',
				data: reglement,
				success: function(data){
					$('#load_validate_smcipn').fadeOut();
					$('#load_validate_smcipn_icone').fadeOut();
					if(data===0){
						
						$('#text-codesms_smcipn').val('');
						$('#notification_sms_smcipn').css({'display':'none'});
						$final_notification_smcipn.css({'display':'block'});
						$('#text-codemembre-smcipn').val('');
						$('#form-smcipn-radio-ordinaire').prop('checked', false);
						$('#form-smcipn-radio-speciale').prop('checked', false);
						$('#montant-demande-smcipn').val(0);
						$('#text-numAppelOffre').val('');
						$('#form-smcipn-bonConso').val('');
						$('#text-reference-smcipn').val('');
		                $('#text-designation-smcipn').val('');
						$listArticlesVendus = [];
						
					}else if(data===1){ 
						$notification_smcipn.addClass('alert alert-danger');
						$notification_smcipn.text('Echec de creation du bon de conso');
					}else if(data===2){ 
						$notification_smcipn.addClass('alert alert-danger');
						$notification_smcipn.text('Echec de l echange ');
					}else if(data===3){ 
						$notification_smcipn.addClass('alert alert-danger');
						$notification_smcipn.text('Le code du bon est invalide');
					}else if(data===4){ 
						$notification_smcipn.addClass('alert alert-danger');
						$notification_smcipn.text('Revoir la saisie');
					}
				},
				error: function (xhr, textStatus, errorThrown) {
					$('#load_validate_smcipn').fadeOut();
					$('#load_validate_smcipn_icone').fadeOut();
					$listArticlesVendus = [];
					$notification_smcipn.addClass('alert alert-danger');
					$notification_smcipn.text('La communication avec le serveur a echoué. Veuillez en informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

				}


			});

		}		



	});

	$('body').on('click', '#notification_final_smcipn_close', function (e) {
		e.preventDefault();
		$final_notification_smcipn.css({'display':'none'});
	});



	$('body').on('click', '#form-smcipn-annuler',function(e){
		$('#text-codemembre-smcipn').val('');
		$('#montant-demande-smcipn').val(0);
	});
	
	function ArticleVendu(codeBarre, reference, designation, quantite, prix) {
		this.codeBarre = codeBarre;
		this.reference = reference;
		this.designation = designation;
		this.quantite = quantite;
		this.prix = prix;
	}
	
	$('body').on('click', '#notification_sms_final_close_smcipn', function (e) {
		e.preventDefault();
		$('#text-codesms_smcipn').val('');
		$notification_sms_smcipn.css({'display':'none'});
	});

})(jQuery);