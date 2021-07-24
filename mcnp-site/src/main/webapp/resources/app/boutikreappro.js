(function ($) {
	var $codeMembreAcheteur;
	var $listArticlesVendus=[];
	var $montantAchatHT;
	var $super_notification = $('#esmc_notification');
	var $super_notification2 = $('#esmc_notification2_reappro');
	var $super_notification_final = $('#esmc_notification_final_reappro');
	var $codeBonconso="";
	var $prk;
	var $codebarre=""; 
	var $reference="";
	var $designation="";
	var $prix;
	var $qte;
	var $typeRea;
	var $typeChoix;
	var $codeTegc;
	var $codeEnvoi;
	var $codeTypeCredit;
	var $codeCommande;
	//var $numeroTelephone;
	var $idArticleStockesCategorie;
    var $nomArticleStockesCategorie;
    var $qteEnStock;
    var $fraisLivraison;
    var $typeBps;
    var $adresseLivraison;
    var $modeLivraison =2;



//	BOUTIQUE REAPPRO 

    $(document).ready(
    		
			function() {
				
				$.ajax({
					url:"signerEliReappro",
					type:'GET',
					datatype:'json',
					contentType:'application/json',
					success: function(data){
							if(data===0){
							$('#notif_reappro').css({'display':'inline-block'});
							$('#notif_reappro').addClass('alert alert-danger');
							$('#notif_reappro').text('VEUILLEZ SIGNER LE CONTRAT ELI AVANT TOUTE OPERATION');
							$('#form-reappro-code-membre').css({'display':'none'});
						 }	
						}
					});
				});

	
	if(!$('#form-field-select-codetypecredit-reappro').val()){
		$('#notif_reappro').css({'display':'inline-block'});
		$('#notif_reappro').addClass('alert alert-danger');
		$('#notif_reappro').text('VEUILLEZ FAIRE PARAMETRER VOTRE TE AVANT TOUTE OPERATION PAR UN TICKET DE SUPPORT');
	
	}
	
	/*$('body').on('focus','#form-rea-select-categorie',function(e){
		e.preventDefault();
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		recuperation des infos des articles
		$.ajax({
			url:"recuperationCategorierea/",
			type:'GET',
			datatype:'json',
			contentType:'application/json',
			success: function(data){
				$('#form-rea-select-categorie').html('');
				var options = $("#form-rea-select-categorie");
				for (var i in data) {
					options.append(new Option(data[i].nomArticleStockesCategorie,data[i].idArticleStockesCategorie)); 
				}
			}
		});
	});*/
	
	/*$('body').on('focus','#form-rea-select-reference',function(e){
		e.preventDefault();
		
		$idArticleStockesCategorie = parseInt($('#form-rea-select-categorie option:selected').val()); 
		console.log("$idArticleStockesCategorie ="+$idArticleStockesCategorie);
		
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		recuperation des infos des articles
		$.ajax({
			url:"ReferenceArticleByCategorierea/"+$idArticleStockesCategorie,
			type:'GET',
			datatype:'json',
			contentType:'application/json',
			success: function(data){
				
				$('#form-rea-select-reference').html('');
				var options = $("#form-rea-select-reference");
				for (var i in data) {
					options.append(new Option(data[i])); 
				}
				
				$('#text-designation').val(''+data.designation+'');
				$('#text-prix').val(data.prix);
				$codeTegcVendeur = data.categorie;
				
			}
		});
	});*/
	
	
	/*$('body').on('click','#form-vente-select-reference',function(e){
		e.preventDefault();
		
		$reference = $('#form-vente-select-reference option:selected').val(); 
				
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		recuperation des infos des articles
		$.ajax({
			url:"infosArticleByReferencerea/"+$reference,
			type:'GET',
			datatype:'json',
			contentType:'application/json',
			success: function(data){
				$('#text-codebarre-search').val(''+data.codeBarre+'');
				$('#text-designation').val(''+data.designation+'');
				$('#text-prix').val(data.prix);
				$codeTegcVendeur = data.categorie;
				$qteEnStock = data.quantite;
				if(data.quantite==null){
				$qteEnStock=0;	
				}
				$typeBps = data.type;
				console.log("$typeBps= "+$typeBps);
				if($typeBps == "BP"){
					
					$('#text-prix').attr('readonly', 'readonly');	
					
				}	
				$super_notification.removeClass('alert alert-danger');
				$super_notification.text('stock disponible: '+$qteEnStock);  
			}
		});
	});
	*/
	
	//afficher nom et telephone

	$('body').on('blur', '#form-reappro-code-membre', function(e){
		$codeMembreAcheteur = $('#form-reappro-code-membre').val();
		if(!$codeMembreAcheteur){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Attention le codemembre ne doit pas être vide');  
			$('#form-reappro-code-membre').focus();

		}else if($('#form-reappro-code-membre').val().length<20){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Veuillez vérifier le code membre saisi');  
			$('#form-reappro-code-membre').val('');
			$('#form-reappro-code-membre').focus();
		}else if($codeMembreAcheteur.search('M') === -1){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Le code membre est invalide. Terminal valable uniquement pour les distributeurs');  
			$('#form-reappro-code-membre').val('');
			$('#form-reappro-code-membre').focus();
		}else{
			$.ajax({
				url:"raisonAcheteurBoutik/"+$codeMembreAcheteur,
				type:'GET',
				datatype:'json',
				contentType:'application/json',
				success: function(data){
					$('#form-reappro-nom-membre').val('');
					$('#form-reappro-nom-membre').val(''+data[0]+'');
					$('#form-reappro-select-telephone').val('');
					$('#form-reappro-select-telephone').val(''+data[1]+'');
				}
			});
			/*$.ajax({
				url:"listeTelephoneBoutik/"+$codeMembreAcheteur,
				type:'GET',
				datatype:'json',
				contentType:'application/json',
				success: function(data){
					$('#form-reappro-select-telephone').html('');
					var options = $("#form-reappro-select-telephone");
					for (var i in data) {
						options.append(new Option(data[i].numeroTelephone)); 
					}
				}
			});
			
*/			$.ajax({
				url:"listtegc/"+$codeMembreAcheteur,
				type:'GET',
				datatype:'json',
				contentType:'application/json',
				success: function(data){
					$('#form-field-select-te').html('');
					var options = $("#form-field-select-te");
					for (var i in data) {
						options.append(new Option(data[i].nomTegc, data[i].codeTegc)); 
					}
				}
			});
			
			$('#form_reappro_nom_telephone').css({'display':'inline-block'});	
		}
	});
	//select codetypecredit

	$('body').on('click', '#form-field-select-codetypecredit-reappro',function(e){
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  

		$codeTypeCredit = $('#form-field-select-codetypecredit-reappro option:selected').val();

		/*$.ajax({
			url:"findprkrea/"+$codeTypeCredit,
			type:'GET',
			datatype:'json',
			contentType:'application/json',
			success: function(data){
				console.log(data);
				$('#form-field-select-prk-reappro').html('');
				var options = $('#form-field-select-prk-reappro');
				options.append(new Option(data));
			}
		});*/
	});


	/*//PERTE DE FOCUS CODEMEMBRE POUR TROUVER LES TE

	$('body').on('blur','#form-reappro-code-membre',function(e){
		e.preventDefault();
		$codeMembreAcheteur = $('#form-reappro-code-membre').val();
		if($codeMembreAcheteur===''){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Veuillez renseigner le code du membre acheteur');  
			$('#form-reappro-code-membre').focus();
		}if($codeMembreAcheteur===''){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Veuillez renseigner le code du membre acheteur');  
			$('#form-reappro-code-membre').focus();
		}else{
			$.ajax({
				url:"listtegc/"+$codeMembreAcheteur,
				type:'GET',
				datatype:'json',
				contentType:'application/json',
				success: function(data){
					$('#form-field-select-te').html('');
					var options = $("#form-field-select-te");
					for (var i in data) {
						options.append(new Option(data[i].nomTegc, data[i].codeTegc)); 
					}
				}
			});
		}
		
	});		
*/

	//radio livraison sur place
	$('body').on('click', '#form-rea-surplace',function(e){

		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		$modeLivraison = 2;
		$('#label-adresseLivraison-rea').css({'display':'none'});
		$('#form-rea-adresseLivraison').css({'display':'none'});
		$('#label-frais-livraison-rea').css({'display':'none'});
		$('#text-montant-frais-livraison-rea').css({'display':'none'});
		$('#form-rea-domicile').prop('checked', false);
	
		
	});
	
	//radio livraison a domicile
	$('body').on('click', '#form-rea-domicile',function(e){

		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		$modeLivraison = 1;
		$('#label-adresseLivraison-rea').css({'display':'inline'});
		$('#form-rea-adresseLivraison').css({'display':'inline'});
		$('#label-frais-livraison-rea').css({'display':'inline'});
		$('#text-montant-frais-livraison-rea').css({'display':'inline'});
		$('#form-rea-surplace').prop('checked', false);
		
	});

	
	
	
	
	$('body').on('click', '#form-reappro-rea',function(e){
		$typeRea ="reap";
		

		$('#label').css({'display':'inline-block'});
		$('#label-form-reappro-oui').css({'display':'inline-block'});
		$('#form-reappro-oui').css({'display':'inline-block'});
		$('#label-form-reappro-non').css({'display':'inline-block'});
		$('#form-reappro-non').css({'display':'inline-block'});

		$('#form-reappro-code-commande').css({'display':'none'});
		$('#label-form-reappro-code-commande').css({'display':'none'});
	});

	$('body').on('click', '#form-reappro-com',function(e){

		$typeRea ="com";
		
		$('#label').css({'display':'none'});
		$('#label-form-reappro-oui').css({'display':'none'});
		$('#form-reappro-oui').css({'display':'none'});
		$('#label-form-reappro-non').css({'display':'none'});
		$('#form-reappro-non').css({'display':'none'});
		$('#label-te').css({'display':'none'});
		$('#form-field-select-te').css({'display':'none'});

		$('#form-reappro-code-commande').css({'display':'inline-block'});
		$('#label-form-reappro-code-commande').css({'display':'inline-block'});


	});

	$('body').on('click', '#form-reappro-oui', function(e){
		$('#label-te').css({'display':'inline-block'});
		$('#form-field-select-te').css({'display':'inline-block'});

	});

	$('body').on('click', '#form-reappro-non', function(e){
		$('#label-te').css({'display':'none'});
		$('#form-field-select-te').css({'display':'none'});
		$codeTegc ="";
	});




	// envoi de sms et ouverture de la boite de dialogue 
	$('body').on('click','#form-reappro-createBonConso',function(e){
		e.preventDefault();
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');
		$super_notification2.removeClass('alert alert-danger');
		$super_notification2.text(''); 

		//recuperation du code membre
		$codeMembreAcheteur = $('#form-reappro-code-membre').val();
		if($codeMembreAcheteur===''){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Attention le codemembre ne doit pas être vide');  
			$('#form-reappro-code-membre').focus();
		}else if($codeMembreAcheteur.length<20){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Veuillez vérifier le code membre saisi');  
			$('#form-reappro-code-membre').focus();
		}else if($codeMembreAcheteur.search('M') === -1){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Le code membre est invalide. Terminal valable uniquement pour les distributeurs');  
			$('#form-reappro-code-membre').focus();
		}else {//recuperation du typeRea

			//if($('#form-reappro-rea').is(':checked') === true){
				$typeRea = "reap"
				
			/*}
			if($('#form-reappro-com').is(':checked') === true){
				$typeRea = "com";
				console.log("$typeRea: "+$typeRea);
			}	*/
			
			if($modeLivraison ==1){
				$adresseLivraison = $('#form-rea-adresseLivraison').val();
				$fraisLivraison =parseInt($('#text-montant-frais-livraison-rea').val());
				}else{
				$fraisLivraison=0;
			}
			$numeroTelephone = $('#form-reappro-select-telephone').val();
			if(!$numeroTelephone){
				$super_notification2.removeClass('alert alert-danger');
				$super_notification2.text('');  
				$super_notification2.addClass('alert alert-danger');
				$super_notification2.text('Veuillez choisir un numero de téléphone');  
				$('#form-reappro-select-telephone').focus();
			}
			if($typeRea === ''){
				$super_notification2.removeClass('alert alert-danger');
				$super_notification2.text('');  
				$super_notification2.addClass('alert alert-danger');
				$super_notification2.text('Veuillez selectionner le type de reapprovisionnement a effectuer'); 
			}else if($typeRea === 'com'){
				$codeCommande =  $('#form-reappro-code-commande').val();

				if($codeCommande===''){
					$super_notification2.removeClass('alert alert-danger');
					$super_notification2.text('');  
					$super_notification2.addClass('alert alert-danger');
					$super_notification2.text('Attention le code de la commande ne doit pas être vide');  
					$('#form-reappro-code-commande').focus();
				}else{

					$('#load_createbon_reappro').css({'display':'inline-block'});
					$('#load_createbon_reappro_icone').css({'display':'inline-block'});
					$csrf = $('input[name="_csrf"]').val();

					//envoiSms = "numeroTelephone="+$numeroTelephone+"&codeCommande="+$codeCommande+"&codeMembreAcheteur="+$codeMembreAcheteur+"&_csrf=" + $csrf + "";

					envoiSms = "codeCommande="+$codeCommande+"&codeMembreAcheteur="+$codeMembreAcheteur+"&_csrf=" + $csrf + "";

					
					$.ajax({
						url:"searchCommandeAndSendSms",
						type:'POST',
						data: envoiSms,
						success: function(data){

							$('#load_createbon_reappro').fadeOut();
							$('#load_createbon_reappro_icone').fadeOut();

							if(data[0]=="KO1"){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('Ce code membre est invalide pour cette commande');    
							}else if(data[0]=="KO2"){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('le code du bon de commande est invalide'); 
							}else if(data[0]=="KO3"){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('Revoir la saisie'); 
							}else{
								/*$('#text-codesms_reappro').val('ESMC '+data[0]+'');
								$('#notification_sms_reappro').css({'display':'inline-block'});
								$codeEnvoi = data[0];*/
								
								//qrcaptcha
								$('#qrcaptcha').css({'display':'inline-block'});
								
							}


						},
						error: function (xhr, textStatus, errorThrown) {
							$('#load_createbon_reappro').fadeOut();
							$('#load_createbon_reappro_icone').fadeOut();
							$super_notification2.addClass('alert alert-danger');
							$super_notification2.text('La communication avec le serveur a echoué. Veuillez informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

						}


					});
				}



			}else if($typeRea === 'reap'){
				if($modeLivraison ==1){
					$adresseLivraison = $('#form-rea-adresseLivraison').val();
					$fraisLivraison =parseInt($('#text-montant-frais-livraison-rea').val());
					}else{
					$fraisLivraison=0;
				}

				/*$prk = $('#form-field-select-prk-reappro option:selected').val();
				console.log("prk= "+$prk);
*/
				//recuperation du code type Credit
				$codeTypeCredit = $('#form-field-select-codetypecredit-reappro option:selected').val();
				
				//if($('#form-reappro-oui').is(':checked') === true){
				$codeTegc = $('#form-field-select-te option:selected').val();
				console.log("$codeTegc: "+$codeTegc);
			//	}
				/*if($('#form-reappro-non').is(':checked') === true){
					$codeTegc = "";
					console.log("$codeTegc: "+$codeTegc);
				}*/
			    if($codeTegc===""){
			    	$super_notification2.removeClass('alert alert-danger');
			    	$super_notification2.text('');  
			    	$super_notification2.addClass('alert alert-danger');
			    	$super_notification2.text('Veuillez selectionner le TE'); 
				    $('#form-field-select-te').focus();
			    }else if($codeTypeCredit === ''){
					$super_notification2.removeClass('alert alert-danger');
					$super_notification2.text('');  
					$super_notification2.addClass('alert alert-danger');
					$super_notification2.text('Veuillez selectionner le type de produit'); 
					$('#form-field-select-codetypecredit-reappro').focus();
				}else{
					//recuperation du montant 
					$montantAchatHT = parseInt($('#total-montant-prixht').val());
						if($montantAchatHT<= 0){
						$super_notification.removeClass('alert alert-danger');
						$super_notification.text('');
						$super_notification.addClass('alert alert-danger');
						$super_notification.text('Revoir le bon de livraison...Le montant est invalide');
						$('#text-codebarre-search').focus();

					}else{
						$('#load_createbon_reappro').css({'display':'inline-block'});
						$('#load_createbon_reappro_icone').css({'display':'inline-block'});
						$csrf = $('input[name="_csrf"]').val();
						
					
						//rechercheMontant = "numeroTelephone="+$numeroTelephone+"&montantAchat="+$montantAchatHT+"&codeTypeCredit="+$codeTypeCredit+"&typeRea="+$typeRea+"&codeTegc="+$codeTegc+"&codeMembreAcheteur="+$codeMembreAcheteur+"&_csrf=" + $csrf + "";
						rechercheMontant = "modeLivraison="+$modeLivraison+"&fraisLivraison="+$fraisLivraison+"&montantAchat="+$montantAchatHT+"&codeTypeCredit="+$codeTypeCredit+"&typeRea="+$typeRea+"&codeTegc="+$codeTegc+"&codeMembreAcheteur="+$codeMembreAcheteur+"&_csrf=" + $csrf + "";

						$.ajax({
							url:"sendSmsCodeBon",
							type:'POST',
							data: rechercheMontant,
							success: function(data){

								$('#load_createbon_reappro').fadeOut();
								$('#load_createbon_reappro_icone').fadeOut();
								if(data[0]=="KO1"){
									$super_notification2.addClass('alert alert-danger');
									$super_notification2.text('Le quota de consommation est depassé');    
								}
								if(data[0]=="KO2"){
									$super_notification2.addClass('alert alert-danger');
									$super_notification2.text('Le montant des BL est insuffisant');    
								}/*else if(data[0]=="KO2"){
									$super_notification2.addClass('alert alert-danger');
									$super_notification2.text('le montant des opi disponible est insuffisant'); 
								}else if(data[0]=="KO3"){
									$super_notification2.addClass('alert alert-danger');
									$super_notification2.text('Il n y a pas d OPI disponibles'); 
								}*/else if(data[0]=="KO3"){
									$super_notification2.addClass('alert alert-danger');
									$super_notification2.text('revoir la saisie des données'); 
								}else if(data[0]=="OK"){
									//qrcaptcha
									$('#qrcaptcha').css({'display':'inline-block'});
									$('#code_membre_qr_auth').val(''+$codeMembreAcheteur+'');

									/*$('#text-codesms_reappro').val('ESMC '+data[0]+'');
									$('#notification_sms_reappro').css({'display':'inline-block'});
									$codeEnvoi = data[0];*/
									

								}
							},
							error: function (xhr, textStatus, errorThrown) {
								$('#load_createbon_reappro').fadeOut();
								$('#load_createbon_reappro_icone').fadeOut();
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('La communication avec le serveur a echoué. Veuillez informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

							}


						}); 

					}
				}
			}

		}
	}); 





	function ArticleVendu(codeBarre, reference, designation, quantite, prix, codeTegcVendeur) {
		this.codeBarre = codeBarre;
		this.reference = reference;
		this.designation = designation;
		this.quantite = quantite;
		this.prix = prix;
		this.codeTegc = codeTegcVendeur; 
	}


//	bouton CONFIRMER DU MODAL

	$('body').on('click', '#form-reappro-confirmer', function (e) {
		e.preventDefault();
		$('#viewMontantBn-modal-reappro').hide();
		$('.modal-backdrop').hide();

	});

//	bouton FINAL VALIDER 

	$('body').on('click', '#form-reappro-terminerOperation', function (e) {
		e.preventDefault();
		$super_notification2.removeClass('alert alert-danger');
		$super_notification2.text(''); 
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');

		
		//recuperation du code membre
		$codeMembreAcheteur = $('#form-reappro-code-membre').val();
		if($codeMembreAcheteur === ''){
			$('#load_validate_reappro').fadeOut();
			$('#load_validate_icone_reappro').fadeOut();
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Attention le codemembre ne doit pas être vide');  
			$('#form-reappro-code-membre').focus();
		}else if($codeMembreAcheteur.length<20){
			$('#load_validate_reappro').fadeOut();
			$('#load_validate_icone_reappro').fadeOut();
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Veuillez vérifier le code membre saisi');  
			$('#form-reappro-code-membre').focus();
		}else if($codeMembreAcheteur.search('M') === -1){
			$('#load_validate_reappro').fadeOut();
			$('#load_validate_icone_reappro').fadeOut();
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Le code membre est invalide');  
			$('#form-reappro-code-membre').focus();
		}else{
			//recuperation des references additives
			$referenceAdditive = $('#text-reference-additive-rea').val();
			
			//recuperation du code type Credit
			$codeTypeCredit = $('#form-field-select-codetypecredit-reappro option:selected').val();
			if($codeTypeCredit === ''){
				$('#load_validate_reappro').fadeOut();
				$('#load_validate_icone_reappro').fadeOut();
				$super_notification2.removeClass('alert alert-danger');
				$super_notification2.text('');  
				$super_notification2.addClass('alert alert-danger');
				$super_notification2.text('Veuillez selectionner le type de produit'); 
				$('#form-field-select-codetypecredit-reappro').focus();
			}else{
				//recuperation du code du bon de consommation

			/*	$codeBonConso = $('#form-reappro-bonConso').val();
				console.log('$codeBonConso = '+$codeBonConso);*/

				$typeRea = "reap"
				/*if($('#form-reappro-rea').is(':checked') === true){
					$typeRea = "reap"
						console.log("$typeRea: "+$typeRea);
				}
				if($('#form-reappro-com').is(':checked') === true){
					$typeRea = "com";
					console.log("$typeRea: "+$typeRea);
				}	*/

				/*if($codeBonConso===""){
					$('#load_validate_reappro').fadeOut();
					$('#load_validate_icone_reappro').fadeOut();
					$super_notification2.removeClass('alert alert-danger');
					$super_notification2.text('');
					$super_notification2.addClass('alert alert-danger');
					$super_notification2.text('le code du bon de consommation ne doit pas être vide');
					$('#form-vente-bonConso').focus();
				}else */if($typeRea === ''){
					$super_notification2.removeClass('alert alert-danger');
					$super_notification2.text('');  
					$super_notification2.addClass('alert alert-danger');
					$super_notification2.text('Veuillez selectionner le type de reapprovisionnement a effectuer'); 
				}else if($typeRea === 'com'){
					$codeCommande =  $('#form-reappro-code-commande').val();
					if($codeCommande===''){
						$super_notification2.removeClass('alert alert-danger');
						$super_notification2.text('');  
						$super_notification2.addClass('alert alert-danger');
						$super_notification2.text('Attention le code de la commande ne doit pas être vide');  
						$('#form-reappro-code-commande').focus();
					}else{

						$('#load_validate_reappro').css({'display':'inline-block'});
						$('#load_validate_icone_reappro').css({'display':'inline-block'});
						$csrf = $('input[name="_csrf"]').val();

					reglement = "codeCommande="+$codeCommande+"&codeTypeCredit="+$codeTypeCredit+"&codeEnvoi="+$codeEnvoi+"&codeBonConso="+$codeBonConso+"&codeMembreAcheteur="+$codeMembreAcheteur+"&_csrf=" + $csrf + "";

					$.ajax({
						url:"executerCommande",
						type:'POST',
						data: reglement,
						success: function(data){
							$('#load_createbon_reappro').fadeOut();
							$('#load_createbon_reappro_icone').fadeOut();
							if(data===0){
								$('#text-codesms_reappro').val('');
								$('#notification_sms_reappro').css({'display':'none'});
								$super_notification_final.css({'display':'block'});

								$('#form-reappro-code-commande').val('');
								$codeCommande="";
								$typeRea  = "";
								$codeTypeCredit="";
								$codeMembreAcheteur="";
								$('#form-reappro-rea').prop('checked', false);
								$('#form-reappro-com').prop('checked', false);
								$('#form-reappro-code-membre').val('');
								$('#form-reappro-bonConso').val('');
								
							}else if(data===1){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('Echec de souscription au bon de consommation');
							}else if(data===2){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('Echec de creation du bon de consommation');
							}else if(data===3){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('Echec de l echange');
							}else if(data===4){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('commande non trouvée ou déja exécutée');
							}else if(data===5){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text(' Le code de confirmation est invalide');
							}else if(data===6){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('Revoir la saisie des données');
							}
						},
						error: function (xhr, textStatus, errorThrown) {
							$('#load_validate_reappro').fadeOut();
							$('#load_validate_icone_reappro').fadeOut();
							$super_notification2.addClass('alert alert-danger');
							$super_notification2.text('La communication avec le serveur a echoué. Veuillez en informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

						}

					});
	
	}	
				}else if($typeRea === 'reap'){

					//recuperation des references additives
					$referenceAdditive = $('#text-reference-additive-rea').val();
					
					$listArticlesVendus = [];

				//	if($('#form-reappro-oui').is(':checked') === true){
						$codeTegc = $('#form-field-select-te option:selected').val();
						console.log("$codeTegc: "+$codeTegc);
					/*}
					if($('#form-reappro-non').is(':checked') === true){
						$codeTegc = "";
						console.log("$codeTegc: "+$codeTegc);
					}*/

					//recuperation du bordereau de livraison
					$('tr', $('#table-article-list-retreave-container')).each(function () {
						var $child = $(this).children();
						$codebarre = $child[0].innerHTML;
						$reference= $child[1].innerHTML;
						$designation = $child[2].innerHTML;
						$qte = $child[3].innerHTML;
						$prix = $child[4].innerHTML;
						$codeTegcVendeur = $child[7].innerHTML;
						var $articlesVendus = new ArticleVendu($codebarre, $reference, $designation, $qte, $prix, $codeTegcVendeur );
						$listArticlesVendus.push($articlesVendus);
						
						/*var $child = $(this).children();
						$codebarre = $child[0].innerHTML;
						$reference= $child[1].innerHTML;
						$designation = $child[2].innerHTML;
						$qte = $child[3].innerHTML;
						$prix = $child[4].innerHTML;
						var $articlesVendus = new ArticleVendu($codebarre, $reference, $designation, $qte, $prix);
						$listArticlesVendus.push($articlesVendus);*/
					});

					if($listArticlesVendus.length <= 0 ){
						$('#load_validate_reappro').fadeOut();
						$('#load_validate_icone_reappro').fadeOut();
						$super_notification.removeClass('alert alert-danger');
						$super_notification.text('');
						$super_notification.addClass('alert alert-danger');
						$super_notification.text('Le bon de livraison est vide');
						$('#text-codebarre-search').focus();
					}else{
						//recuperation du montant 
						$montantAchatHT = parseInt($('#total-montant-prixht').val());
						
						
						if($montantAchatHT<= 0){
							$('#load_validate_reappro').fadeOut();
							$('#load_validate_icone_reappro').fadeOut();
							$super_notification.removeClass('alert alert-danger');
							$super_notification.text('');
							$super_notification.addClass('alert alert-danger');
							$super_notification.text('Revoir le bon de livraison...Le montant est invalide');
							$('#text-codebarre-search').focus();

						}else{
							$jsonlist = JSON.stringify($listArticlesVendus);
						
							$('#load_validate_reappro').css({'display':'inline-block'});
							$('#load_validate_icone_reappro').css({'display':'inline-block'});

							$csrf = $('input[name="_csrf"]').val();

							reglement = "referenceAdditive="+$referenceAdditive+"&fraisLivraison="+$fraisLivraison+"&numeroTelephone="+$numeroTelephone+"&modeLivraison="+$modeLivraison+"&adresseLivraison="+$adresseLivraison+"&codeTegc="+$codeTegc+"&codeTypeCredit="+$codeTypeCredit+"&codeMembreAcheteur="+$codeMembreAcheteur+"&montantAchat="+$montantAchatHT+"&listArticlesVendus="+$jsonlist+"&_csrf=" + $csrf + "";

							$.ajax({
								url:"creerBonCommande",
								type:'POST',
								data: reglement,
								success: function(data){
									$('#load_validate_reappro').fadeOut();
									$('#load_validate_icone_reappro').fadeOut();
									if(data===0){
									//	$('#text-codesms_reappro').val('');
										$('#notification_sms_reappro').css({'display':'none'});
										$super_notification_final.css({'display':'block'});

										//$('#label-te').css({'display':'none'});
										//$('#form-field-select-te').css({'display':'none'});
										$('#form-field-select-te').html('');
										$codeTegc ="";
										$('#form-reappro-bonConso').val('');
										$('#total-montant-prixht').val('0');
										$('#table-article-list-retreave-container').html('');
										$listArticlesVendus = [];
										$('#label').css({'display':'none'});
										$('#label-form-reappro-oui').css({'display':'none'});
										$('#form-reappro-oui').css({'display':'none'});
										$('#label-form-reappro-non').css({'display':'none'});
										$('#form-reappro-non').css({'display':'none'});

										$typeRea  = "";
										$codeTypeCredit="";
										$codeMembreAcheteur="";
										$('#form-reappro-rea').prop('checked', false);
										$('#form-reappro-com').prop('checked', false);
										$('#form-reappro-code-membre').val('');
									}else if(data===1){ 
										$super_notification2.addClass('alert alert-danger');
										$super_notification2.text('Echec de creation du bon de commande');
									}else if(data===2){
										$super_notification2.addClass('alert alert-danger');
										$super_notification2.text('echec de l operation d echange');
									}else if(data===3){
										$super_notification2.addClass('alert alert-danger');
										$super_notification2.text('montant des GCP insuffisant');
									}else if(data===4){
										$super_notification2.addClass('alert alert-danger');
										$super_notification2.text('Echec de creation du bon d achat');
									}else if(data===5){
										$super_notification2.addClass('alert alert-danger');
										$super_notification2.text('Le montant des OPI est insuffisant');
									}else if(data===6){
										$super_notification2.addClass('alert alert-danger');
										$super_notification2.text('Le code du bon de commande est invalide');
									}else if(data===7){
										$super_notification2.addClass('alert alert-danger');
										$super_notification2.text('Revoir la saise des données');
									}
								},
								error: function (xhr, textStatus, errorThrown) {
									$('#load_validate_reappro').fadeOut();
									$('#load_validate_icone_reappro').fadeOut();
									$listArticlesVendus = [];
									$super_notification2.addClass('alert alert-danger');
									$super_notification2.text('La communication avec le serveur a echoué. Veuillez en informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

								}


							});

						}
					}
				}

			}
		}
	});







				
				

	$('body').on('click', '#esmc_notification_final_reappro_close', function (e) {
		e.preventDefault();
		$super_notification_final.css({'display':'none'});
	});


	$('body').on('click', '#form-reappro-annuler', function (e) {
		e.preventDefault();
		$super_notification2.removeClass('alert alert-danger');
		$super_notification2.text('');
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');

		$('#label-te').css({'display':'none'});
		$('#form-field-select-te').css({'display':'none'});
		$codeTegc ="";
		//$('#form-reappro-bonConso').val('');
		$('#form-reappro-code-membre').val('');
		$('#total-montant-prixht').val('0');
		$('#table-article-list-retreave-container').html('');
		$listArticlesVendus = [];
		$('#label').css({'display':'none'});
		$('#label-form-reappro-oui').css({'display':'none'});
		$('#form-reappro-oui').css({'display':'none'});
		$('#label-form-reappro-non').css({'display':'none'});
		$('#form-reappro-non').css({'display':'none'});
		$('#form_reappro_nom_telephone').css({'display':'none'});

		//$('#form-reappro-esc').prop('checked', false);
		$('#form-reappro-rea').prop('checked', false);
		$codeBonConso='';
		$codeMembreAcheteur='';
		$codeBonConso='';

	});







})(jQuery);
