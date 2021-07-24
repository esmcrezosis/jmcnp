(function ($) {
	var $codeMembreAcheteur;
	var $listArticlesVendus=[];
	var $montantAchatHT;
	var $typeBon="";
	var $typeR;
	var $super_notification = $('#esmc_notification');
	var $super_notification2 = $('#esmc_notification2');
	var $super_notification_final = $('#esmc_notification_final');
	var $montantBonNeutreUtiliser;
	var $montantBonConsommationSouscrit;
	var $typeMontantBon;
	var $produit="";
	var $codeBonconso="";
	var $prk=0.0;
	var $perioder;
	var $codebarre=""; 
	var $reference="";
	var $designation="";
	var $prix;
	var $qte;
	var $montantBa;
//	var $montantOpi;
	var $montantBan;
	var $montantBc;
	var $codeEnvoi;
	var $bps;
	var $frequence;
	var $codeTypeCredit="";
	var $codeTegc;
	var $codeTegcVendeur;
	var $adresseLivraison;
	var $modeLivraison =2;
    var $idArticleStockesCategorie;
    var $nomArticleStockesCategorie;
    var $qteEnStock;
    var $fraisLivraison;
    var $domiciliation =0;
    var $referenceAdditive;
    var $codeAchat;
    var $typeBps;
 
//	BOUTIQUE SIMPLE 

	$(document).ready(
	
			function() {
				
				$.ajax({
					url:"signerEli",
					type:'GET',
					datatype:'json',
					contentType:'application/json',
					success: function(data){
					        if(data===0){
							$('#notif_boutik').css({'display':'inline-block'});
							$('#notif_boutik').addClass('alert alert-danger');
							$('#notif_boutik').text('VEUILLEZ SIGNER LE CONTRAT ELI AVANT TOUTE OPERATION');
							$('#form-vente-code-membre').css({'display':'none'});
						 }	
						}
					});
				
				/*//recuperer les articles
				$.ajax({
					url:"infosReferenceByCodeTegc/",
					type:'GET',
					datatype:'json',
					contentType:'application/json',
					success: function(data){
						console.log("reference= "+data)
						$('#form-vente-select-reference').html('');
						var options = $("#form-vente-select-reference");
						for (var i in data) {
							options.append(new Option(data[i])); 
						}
						$('#text-codebarre-search').val(''+data.codeBarre+'');
						$('#text-designation').val(''+data.designation+'');
						$('#text-prix').val(data.prix);
						$codeTegcVendeur = data.categorie;
						$qteEnStock = data.quantite;
						if(data.quantite==null){
						$qteEnStock=0;	
						}
						$super_notification.removeClass('alert alert-danger');
						$super_notification.text('stock disponible: '+$qteEnStock);  
					}
				});*/

			});

	/*$codeTypeCredit = $('#form-field-select-codetypecredit option:selected').val();
	if($codeTypeCredit === ''){
		$('#notif_boutik').css({'display':'inline-block'});
		$('#notif_boutik').addClass('alert alert-danger');
		$('#notif_boutik').text('TERMINAL INVALIDE POUR L UTILISATEUR CONNECTE');
	}
	 */

	if(!$('#form-field-select-codetypecredit').val()){
		$('#notif_boutik').css({'display':'inline-block'});
		$('#notif_boutik').addClass('alert alert-danger');
		$('#notif_boutik').text('VEUILLEZ FAIRE PARAMETRER VOTRE TE AVANT TOUTE OPERATION PAR UN TICKET DE SUPPORT');

	}
	
	
	
	$('body').on('blur','#text-code-achat-interim',function(e){
		e.preventDefault();
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text(''); 
		$codeAchat = $('#text-code-achat-interim').val();
		/*recuperation des infos du codemembre interim achat*/
		$.ajax({
			url:"findCodeMembreInterimAchat/"+$codeAchat,
			type:'GET',
			datatype:'json',
			contentType:'application/json',
			success: function(data){
				$('#form-vente-code-membre').val(''+data+'');
				
			}
		});
	});
	
	
	
	
	
	/*
	$('body').on('blur','#text-codebarre-search',function(e){
		e.preventDefault();
		$codebarre =$('#text-codebarre-search').val(); 
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		recuperation des infos des articles
		$.ajax({
			url:"infoarticle/"+$codebarre,
			type:'GET',
			datatype:'json',
			contentType:'application/json',
			success: function(data){
				var options = $("#form-vente-select-reference");
				options.append(new Option(''+data.reference+''));
				$('#form-vente-select-designation option:selected').text(''+data.designation+'');
				$('#form-vente-select-designation option:selected').text()
				$('#text-prix').val(data.prix);
				$codeTegcVendeur = data.categorie;
				}
		});
	});*/
	
	/*$('body').on('focus','#form-vente-select-categorie',function(e){
		e.preventDefault();
		console.log("focus");
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		recuperation des infos des articles
		$.ajax({
			url:"recuperationCategorie/",
			type:'GET',
			datatype:'json',
			contentType:'application/json',
			success: function(data){
				$('#form-vente-select-categorie').html('');
				var options = $("#form-vente-select-categorie");
				for (var i in data) {
					options.append(new Option(data[i].nomCategorie,data[i].idCategorie)); 
				}
			}
		});
	});
	*/
	//remplir reference
	/*$('body').on('change','#form-vente-select-categorie',function(e){
		console.log("change categorie");
		$idArticleStockesCategorie = parseInt($('#form-vente-select-categorie option:selected').val()); 
		console.log("$idArticleStockesCategorie ="+$idArticleStockesCategorie);
		
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		recuperation des infos des articles
		$.ajax({
			url:"ReferenceArticleByCategorie/"+$idArticleStockesCategorie,
			type:'GET',
			datatype:'json',
			contentType:'application/json',
			success: function(data){
				
				$('#form-vente-select-reference').html('');
				var options = $("#form-vente-select-reference");
				for (var i in data) {
					options.append(new Option(data[i])); 
				}
				
				$('#text-designation').val(''+data.designation+'');
				$('#text-prix').val(data.prix);
				$codeTegcVendeur = data.categorie;
				
			}
		});
	});
	*/
	
	
	
	

	/*$('body').on('click','#form-vente-select-reference',function(e){
		e.preventDefault();
		
		$idArticleStockesCategorie = parseInt($('#form-vente-select-categorie option:selected').val()); 
		console.log("$idArticleStockesCategorie ="+$idArticleStockesCategorie);
		
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		recuperation des infos des articles
		$.ajax({
			url:"ReferenceArticleByCategorie/"+$idArticleStockesCategorie,
			type:'GET',
			datatype:'json',
			contentType:'application/json',
			success: function(data){
				
				$('#form-vente-select-reference').html('');
				var options = $("#form-vente-select-reference");
				for (var i in data) {
					options.append(new Option(data[i])); 
				}
				
				$('#text-designation').val(''+data.designation+'');
				$('#text-prix').val(data.prix);
				$codeTegcVendeur = data.categorie;
				
			}
		});
	});
	*/
	
	/*$('body').on('click','#form-vente-select-designation',function(e){
		e.preventDefault();
		$reference = $('#form-vente-select-designation option:selected').val(); 
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		recuperation des infos des articles
		$.ajax({
			url:"infosArticleByReference/"+$reference,
			type:'GET',
			datatype:'json',
			contentType:'application/json',
			success: function(data){
				//$('#text-codebarre-search').val(''+data.codeBarre+'');
				//$('#text-designation').val(''+data.designation+'');
				$('#form-vente-reference').val(''+data.reference+'');
				$('#text-prix').val(data.prix);
				$codeTegcVendeur = data.categorie;
				$qteEnStock = data.quantite;
				if(data.quantite==null){
				$qteEnStock=0;	
				}
				$typeBps = data.type;
				if($typeBps == "BP"){
					
					$('#text-prix').attr('readonly', 'readonly');	
					
				}	
				$super_notification.removeClass('alert alert-danger');
				$super_notification.text('stock disponible: '+$qteEnStock); 
				
			}
		});
	});
	*/
	
	
	
	
	/*$('body').on('blur','#form-vente-code-membre',function(e){
		e.preventDefault();
		$codeMembreAcheteur = $('#form-vente-code-membre').val();
		if($codeMembreAcheteur===''){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Veuillez renseigner le code du membre acheteur');  
			$('#form-vente-code-membre').focus();
		}else{
			$.ajax({
				url:"listte/"+$codeMembreAcheteur,
				type:'GET',
				datatype:'json',
				contentType:'application/json',
				success: function(data){
					$('#form-field-select-te-bl').html('');
					var options = $("#form-field-select-te-bl");
					for (var i in data) {
						options.append(new Option(data[i].nomTegc, data[i].codeTegc)); 
					}
				}
			});
		}
		
	});		*/


	
	//afficher nom et telephone

	$('body').on('blur', '#form-vente-code-membre', function(e){
		e.preventDefault();
		$codeMembreAcheteur = $('#form-vente-code-membre').val();
		if(!$codeMembreAcheteur){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Attention le codemembre ne doit pas être vide');  
			$('#form-vente-code-membre').focus();

		}else if($('#form-vente-code-membre').val().length<20){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Veuillez vérifier le code membre saisi');  
			$('#form-vente-code-membre').focus();
		}else{
			$.ajax({
				url:"nomAcheteurBoutik/"+$codeMembreAcheteur,
				type:'GET',
				datatype:'json',
				contentType:'application/json',
				success: function(data){
					
					$('#form-vente-nom-membre').val('');
					$('#form-vente-nom-membre').val(''+data[0]+'');
					$('#form-vente-telephone').val('');
					$('#form-vente-telephone').val(''+data[1]+'');
					$('#form_vente_nom_telephone').css({'display':'inline-block'});	
					$('#form-vente-reference').focus();
				}
			});
		}
	});
	
	
	$('body').on('blur','#form-vente-reference',function(e){
		e.preventDefault();
		$reference = $('#form-vente-reference').val().toUpperCase(); 
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		//recuperation des infos des articles
		if(!$reference){
		}else{
			
		$.ajax({
			url:"infosArticleByReference/"+$reference,
			type:'GET',
			datatype:'json',
			contentType:'application/json',
			success: function(data){
				if(data.designation == null){
					$super_notification.addClass('alert alert-danger');
					$super_notification.text('Pas de stock disponible pour ce produit'); 
				}else{
				//$('#text-codebarre-search').val(''+data.codeBarre+'');
				//$('#text-designation').val(''+data.designation+'');
				//$('#form-vente-select-designation').html('');
				//$('#form-vente-select-designation').append(data.designation); 
				
				/*var opt = $('#form-vente-select-designation').html();
				$('#form-vente-select-designation').html(opt+"<option selected=\"selected\" value=\"\">" + data.designation + "</option>") ;
				*/		
					
				//$('#form-vente-select-designation option:selected').val()
				//$('#form-vente-reference').val(''+data.reference+'');
				$('#form-vente-select-designation').val(''+data.designation+'');
				$('#text-prix').val(data.prix);
				$codeTegcVendeur = data.categorie;
				$qteEnStock = data.quantite;
				if(data.quantite==null){
				$qteEnStock=0;	
				}
				$typeBps = data.type;
				if($typeBps == "BP"){
				  $('#text-prix').attr('readonly', 'readonly');	
				}	
				$super_notification.removeClass('alert alert-danger');
				$super_notification.text('stock disponible: '+$qteEnStock); 
				}
			}
		});
		}
	});
	
	
	
	//radio Achat Interim
	$('body').on('click', '#form-vente-radioInterimAchat',function(e){
		if($('#form-vente-radioInterimAchat').is(':checked') === false){
			$('#label-achatTe').css({'display':'none'});
			$('#text-code-achat-interim').css({'display':'none'});
			$typeBon = "";
			
		}else{
			$typeBon = "BIA";
			$('#label-achatTe').css({'display':'inline-block'});
			$('#text-code-achat-interim').css({'display':'inline-block'});
		}
		
	});
	//radio r
	$('body').on('click', '#form-vente-radior',function(e){
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		$('#form-field-select-limite').css({'display':'inline-block'});
		$('#form-field-select-prk').css({'display':'none'});
		$('#label-prk').css({'display':'none'});
		$typeR ="r";
		
		
		/*	$('#label-produit').css({'display':'inline-block'});
		$('#form-field-select-bps').css({'display':'inline-block'});

		$('#label-frequence').css({'display':'inline-block'});
		$('#form-field-select-frequence').css({'display':'inline-block'});*/

	});

	/*$('body').on('blur', '#form-vente-code-membre', function(e){
		$codeTypeCredit = $('#form-field-select-codetypecredit option:selected').val();
		console.log("$codeTypeCredit=" +$codeTypeCredit);
		if(!$('#form-field-select-codetypecredit').val()){
			$('#notif_boutik').css({'display':'inline-block'});
			$('#notif_boutik').addClass('alert alert-danger');
			$('#notif_boutik').text('TERMINAL INVALIDE POUR L UTILISATEUR CONNECTE');

		}

	});*/

	//radio nr
	$('body').on('click', '#form-vente-radionr',function(e){
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		$('#form-field-select-limite').css({'display':'none'});
		$('#form-field-select-prk').css({'display':'inline-block'});
		$('#label-prk').css({'display':'inline-block'});
		$typeR ="nr";
		$('#label-produit').css({'display':'none'});
		$('#form-field-select-bps').css({'display':'none'});

		$('#label-frequence').css({'display':'none'});
		$('#form-field-select-frequence').css({'display':'none'});
		
		$('#label-domicilie').css({'display':'none'});
		$('#form-produit-domicilie').css({'display':'none'});
		
		$('#label-radior').css({'display':'none'});
		$('#form-vente-radior').css({'display':'none'});

	});


	//select codetypecredit

	$('body').on('click', '#form-field-select-codetypecredit',function(e){
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  

		$codeTypeCredit = $('#form-field-select-codetypecredit option:selected').val();

		$.ajax({
			url:"findprk/"+$codeTypeCredit,
			type:'GET',
			datatype:'json',
			contentType:'application/json',
			success: function(data){
				$('#form-field-select-prk').html('');
				var options = $("#form-field-select-prk");
				options.append(new Option(data));

			}
		});
	});

//	select limite
	$('body').on('click', '#form-field-select-limite',function(e){
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  

		if($('#form-field-select-limite option:selected').val()==='limité 1'){
			$('#label-produit').css({'display':'inline-block'});
			$('#form-field-select-bps').css({'display':'inline-block'});

			$('#label-frequence').css({'display':'inline-block'});
			$('#form-field-select-frequence').css({'display':'inline-block'});

		}

		$('#label-domicilie').css({'display':'inline-block'});
		$('#form-produit-domicilie').css({'display':'inline-block'});

	});

	//radio CUMUL

	$('body').on('click', '#form-vente-radioDefaut',function(e){
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		/*$('#label-montant-bc-opi').css({'display':'none'});
		$('#text-montant-bc-opi').css({'display':'none'});*/

		$('#label-montant-ban').css({'display':'none'});
		$('#text-montant-vente-bn').css({'display':'none'});

		$('#label-montant-bc-ban').css({'display':'none'});
		$('#text-montant-bc-ban').css({'display':'none'});

		$('#label-montant-bc-ba').css({'display':'none'});
		$('#text-montant-bc-ba').css({'display':'none'});

		$('#label-montant-approban').css({'display':'none'});
		$('#text-montant-vente-approbn').css({'display':'none'});

		$('#label-montant-bc-approban').css({'display':'none'});
		$('#text-montant-bc-approban').css({'display':'none'});
		
		 $('#label-montant-bl').css({'display':'none'});
	  	 $('#label-te-bl').css({'display':'none'});
		 $('#text-montant-bl').css({'display':'none'});
		 $('#form-field-select-te-bl').css({'display':'none'});



		/*$('#label-typeBa').css({'display':'none'});
		$('#form-field-select-tba').css({'display':'none'});
		 */
		/*	$('#label-montant-ban').css({'display':'inline-block'});
		$('#text-montant-vente-bn').css({'display':'inline-block'});

		$('#label-montant-opi').css({'display':'inline-block'});
		$('#text-montant-vente-opi').css({'display':'inline-block'});

		$('#label-montant-ba').css({'display':'inline-block'});
		$('#text-montant-ba').css({'display':'inline-block'});

		$('#label-montant-bc-bc').css({'display':'inline-block'});
		$('#text-montant-bc-bc').css({'display':'inline-block'});*/

		$('#label-cumul').css({'display':'inline-block'});
		$('#text-montant-cumul').css({'display':'inline-block'});

		$('#label-montant-bc-cumul').css({'display':'inline-block'});
		$('#text-montant-bc-cumul').css({'display':'inline-block'});

		$typeMontantBon = "mdf";
		$typeBon = "DF";

		$('#form-vente-radioBc').prop('checked', false);
		$('#form-vente-radioBn').prop('checked', false);
		$('#form-vente-radioBa').prop('checked', false);
		//	$('#form-vente-radioOpi').prop('checked', false);
		$('#form-vente-approban').prop('checked', false);

		$total = Math.floor(parseInt($('#total-montant-prixht').val()));
		$('#text-montant-bc-cumul').val(''+$total+'');

	});

	/*//radio opi
	$('body').on('click', '#form-vente-radioOpi',function(e){
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		$total = Math.floor(parseInt($('#total-montant-prixht').val()));
		$('#text-montant-bc-opi').val(''+$total+'');

		$('#label-montant-opi').css({'display':'inline-block'});
		$('#text-montant-vente-opi').css({'display':'inline-block'});

		$('#label-montant-bc-opi').css({'display':'inline-block'});
		$('#text-montant-bc-opi').css({'display':'inline-block'});

		$('#label-montant-ban').css({'display':'none'});
		$('#text-montant-vente-bn').css({'display':'none'});

		$('#label-montant-bc-ban').css({'display':'none'});
		$('#text-montant-bc-ban').css({'display':'none'});

		$('#label-montant-bc-ba').css({'display':'none'});
		$('#text-montant-bc-ba').css({'display':'none'});

		$('#label-montant-ba').css({'display':'none'});
		$('#text-montant-ba').css({'display':'none'});

		$('#label-montant-bc-bc').css({'display':'none'});
		$('#text-montant-bc-bc').css({'display':'none'});

		$('#label-cumul').css({'display':'none'});
		$('#text-montant-cumul').css({'display':'none'});

		$('#label-montant-bc-cumul').css({'display':'none'});
		$('#text-montant-bc-cumul').css({'display':'none'});	

		$('#label-montant-approban').css({'display':'none'});
		$('#text-montant-vente-approbn').css({'display':'none'});

		$('#label-montant-bc-approban').css({'display':'none'});
		$('#text-montant-bc-approban').css({'display':'none'});

		$('#text-montant-cumul').val(''+0+'');
		$('#text-montant-vente-opi').val(''+0+'');
		$('#text-montant-vente-bn').val(''+0+'');
		$('#text-montant-ba').val(''+0+'');

		$('#form-vente-radioDefaut').prop('checked', false);
		$('#form-vente-radioBc').prop('checked', false);
		$('#form-vente-radioBn').prop('checked', false);
		$('#form-vente-radioBa').prop('checked', false);
		$('#form-vente-approban').prop('checked', false);


		$typeBon = "OPI";
		console.log("$typeBon= "+$typeBon);

	});

	 */
	//radio BAN

	$('body').on('click', '#form-vente-radioBn',function(e){
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		$total = Math.floor(parseInt($('#total-montant-prixht').val()));
		$('#text-montant-bc-ban').val(''+$total+'');

		/*//calcul montant ban
		
		$.ajax({
			url:"calculMontantBan/"+$total,
			type:'GET',
			datatype:'json',
			contentType:'application/json',
			success: function(data){
				console.log(data);
				$('#text-montant-vente-bn').val(''+data+'');

			}
		});
		*/
		
		
		$('#label-montant-ban').css({'display':'inline-block'});
		$('#text-montant-vente-bn').css({'display':'inline-block'});

		$('#label-montant-bc-ban').css({'display':'inline-block'});
		$('#text-montant-bc-ban').css({'display':'inline-block'});

		$('#label-montant-bc-ba').css({'display':'none'});
		$('#text-montant-bc-ba').css({'display':'none'});

		$('#label-montant-bc-bc').css({'display':'none'});
		$('#text-montant-bc-bc').css({'display':'none'});

		$('#label-montant-ba').css({'display':'none'});
		$('#text-montant-ba').css({'display':'none'});

		/*	$('#label-montant-opi').css({'display':'none'});
		$('#text-montant-vente-opi').css({'display':'none'});

		$('#label-montant-bc-opi').css({'display':'none'});
		$('#text-montant-bc-opi').css({'display':'none'});
		 */
		$('#label-cumul').css({'display':'none'});
		$('#text-montant-cumul').css({'display':'none'});
		$('#label-montant-bc-cumul').css({'display':'none'});
		$('#text-montant-bc-cumul').css({'display':'none'});

		$('#label-montant-approban').css({'display':'none'});
		$('#text-montant-vente-approbn').css({'display':'none'});

		$('#label-montant-bc-approban').css({'display':'none'});
		$('#text-montant-bc-approban').css({'display':'none'});
		/*$('#label-typeBa').css({'display':'none'});
		$('#form-field-select-tba').css({'display':'none'});*/
		
		$('#label-montant-bl').css({'display':'none'});
		$('#label-te-bl').css({'display':'none'});
		$('#text-montant-bl').css({'display':'none'});
		$('#form-field-select-te-bl').css({'display':'none'});

		$('#form-vente-radioDefaut').prop('checked', false);
		$('#form-vente-radioBc').prop('checked', false);
		$('#form-vente-radioBa').prop('checked', false);
		//$('#form-vente-radioOpi').prop('checked', false);
		$('#form-vente-approban').prop('checked', false);

		$('#text-montant-cumul').val(''+0+'');
		//$('#text-montant-vente-opi').val(''+0+'');
		$('#text-montant-vente-bn').val(''+0+'');
		$('#text-montant-ba').val(''+0+'');

	});

	//radio BA
	$('body').on('click', '#form-vente-radioBa',function(e){

		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		$total = Math.floor(parseInt($('#total-montant-prixht').val()));
		$('#text-montant-bc-ba').val(''+$total+'');

		
	/*	//calcul montant bai
		
		$.ajax({
			url:"calculMontantBai/"+$total,
			type:'GET',
			datatype:'json',
			contentType:'application/json',
			success: function(data){
				console.log(data);
				$('#text-montant-ba').val(''+data+'');

			}
		});
		*/
		
		
		
		$('#text-montant-cumul').val(''+0+'');
		//$('#text-montant-vente-opi').val(''+0+'');
		$('#text-montant-vente-bn').val(''+0+'');
		$('#text-montant-ba').val(''+0+'');


		$('#label-montant-bc-ba').css({'display':'inline-block'});
		$('#text-montant-bc-ba').css({'display':'inline-block'});
		$('#label-montant-ba').css({'display':'inline-block'});
		$('#text-montant-ba').css({'display':'inline-block'});

		/*$('#label-montant-opi').css({'display':'none'});
		$('#text-montant-vente-opi').css({'display':'none'});

		$('#label-montant-bc-opi').css({'display':'none'});
		$('#text-montant-bc-opi').css({'display':'none'});
		 */
		$('#label-montant-ban').css({'display':'none'});
		$('#text-montant-vente-bn').css({'display':'none'});

		$('#label-montant-bc-ban').css({'display':'none'});
		$('#text-montant-bc-ban').css({'display':'none'});

		

		$('#label-montant-bc-bc').css({'display':'none'});
		$('#text-montant-bc-bc').css({'display':'none'});

		
		$('#label-cumul').css({'display':'none'});
		$('#text-montant-cumul').css({'display':'none'});
		$('#label-montant-bc-cumul').css({'display':'none'});
		$('#text-montant-bc-cumul').css({'display':'none'});

		$('#label-montant-approban').css({'display':'none'});
		$('#text-montant-vente-approbn').css({'display':'none'});

		$('#label-montant-bc-approban').css({'display':'none'});
		$('#text-montant-bc-approban').css({'display':'none'});
		
		$('#label-montant-bl').css({'display':'none'});
		$('#label-te-bl').css({'display':'none'});
		$('#text-montant-bl').css({'display':'none'});
		$('#form-field-select-te-bl').css({'display':'none'});


		$('#form-vente-radioDefaut').prop('checked', false);
		$('#form-vente-radioBc').prop('checked', false);
		$('#form-vente-radioBn').prop('checked', false);
		//	$('#form-vente-radioOpi').prop('checked', false);
		$('#form-vente-approban').prop('checked', false);

		$typeBon ="BA";
		$typeMontantBon= "mba";
		
	});
	//radio BCOMMANDE
	$('body').on('click', '#form-vente-radioBcom',function(e){

		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		$total = Math.floor(parseInt($('#total-montant-prixht').val()));
		$('#text-montant-bcom').val(''+$total+'');

		$('#text-montant-cumul').val(''+0+'');
		$('#text-montant-vente-bn').val(''+0+'');
		$('#text-montant-ba').val(''+0+'');



		$('#label-montant-ban').css({'display':'none'});
		$('#text-montant-vente-bn').css({'display':'none'});

		$('#label-montant-bc-ban').css({'display':'none'});
		$('#text-montant-bc-ban').css({'display':'none'});

		$('#label-montant-bc-ba').css({'display':'none'});
		$('#text-montant-bc-ba').css({'display':'none'});



		$('#label-montant-bc-bc').css({'display':'none'});
		$('#text-montant-bc-bc').css({'display':'none'});

		$('#label-montant-bcom').css({'display':'inline-block'});
		$('#text-montant-bcom').css({'display':'inline-block'});

		$('#text-delai-livraison').css({'display':'inline-block'});
		$('#label-delai-livraison').css({'display':'inline-block'});

		$('#label-cumul').css({'display':'none'});
		$('#text-montant-cumul').css({'display':'none'});
		$('#label-montant-bc-cumul').css({'display':'none'});
		$('#text-montant-bc-cumul').css({'display':'none'});

		$('#label-montant-approban').css({'display':'none'});
		$('#text-montant-vente-approbn').css({'display':'none'});

		$('#label-montant-bc-approban').css({'display':'none'});
		$('#text-montant-bc-approban').css({'display':'none'});

		$('#label-montant-bl').css({'display':'none'});
		$('#label-te-bl').css({'display':'none'});
		$('#text-montant-bl').css({'display':'none'});
		$('#form-field-select-te-bl').css({'display':'none'});


		$('#form-vente-radioDefaut').prop('checked', false);
		$('#form-vente-radioBc').prop('checked', false);
		$('#form-vente-radioBn').prop('checked', false);
		//	$('#form-vente-radioOpi').prop('checked', false);
		$('#form-vente-approban').prop('checked', false);
		$('#form-vente-radioBa').prop('checked', false);

		$typeBon ="BCo";
		$typeMontantBon= "mbcom";
		
	});

	//radio Bc
	$('body').on('click', '#form-vente-radioBc',function(e){

		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		$total = Math.floor(parseInt($('#total-montant-prixht').val()));
		$('#text-montant-bc-bc').val(''+$total+'');


		$('#text-montant-cumul').val('0');
		//$('#text-montant-vente-opi').val('0');
		$('#text-montant-vente-bn').val('0');
		$('#text-montant-ba').val('0');

		/*$('#label-montant-opi').css({'display':'none'});
		$('#text-montant-vente-opi').css({'display':'none'});

		$('#label-montant-bc-opi').css({'display':'none'});
		$('#text-montant-bc-opi').css({'display':'none'});
		 */
		$('#label-montant-ban').css({'display':'none'});
		$('#text-montant-vente-bn').css({'display':'none'});

		$('#label-montant-bc-ban').css({'display':'none'});
		$('#text-montant-bc-ban').css({'display':'none'});

		$('#label-montant-bc-ba').css({'display':'none'});
		$('#text-montant-bc-ba').css({'display':'none'});

		$('#label-montant-bc-bc').css({'display':'inline-block'});
		$('#text-montant-bc-bc').css({'display':'inline-block'});

		$('#label-montant-ba').css({'display':'none'});
		$('#text-montant-ba').css({'display':'none'});

		$('#label-cumul').css({'display':'none'});
		$('#text-montant-cumul').css({'display':'none'});
		$('#label-montant-bc-cumul').css({'display':'none'});
		$('#text-montant-bc-cumul').css({'display':'none'});

		$('#label-montant-approban').css({'display':'none'});
		$('#text-montant-vente-approbn').css({'display':'none'});

		$('#label-montant-bc-approban').css({'display':'none'});
		$('#text-montant-bc-approban').css({'display':'none'});
		
		$('#label-montant-bl').css({'display':'none'});
		$('#label-te-bl').css({'display':'none'});
		$('#text-montant-bl').css({'display':'none'});
		$('#form-field-select-te-bl').css({'display':'none'});

		$('#form-vente-radioDefaut').prop('checked', false);
		$('#form-vente-radioBn').prop('checked', false);
		$('#form-vente-radioBa').prop('checked', false);
		//	$('#form-vente-radioOpi').prop('checked', false);
		$('#form-vente-approban').prop('checked', false);
		
		$('#form-vente-radionr').prop('checked', false);

		$typeBon ="BC";
		$typeMontantBon= "mbonc";
	
	});


	//radio Bc
	$('body').on('click', '#form-vente-approban',function(e){


		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		$total = Math.floor(parseInt($('#total-montant-prixht').val()));
		$('#text-montant-bc-approban').val(''+$total+'');


		$('#text-montant-cumul').val('0');
		//$('#text-montant-vente-opi').val('0');
		$('#text-montant-vente-bn').val('0');
		$('#text-montant-ba').val('0');

		$('#label-montant-approban').css({'display':'inline-block'});
		$('#text-montant-vente-approbn').css({'display':'inline-block'});

		$('#label-montant-bc-approban').css({'display':'inline-block'});
		$('#text-montant-bc-approban').css({'display':'inline-block'});

		/*$('#label-montant-opi').css({'display':'none'});
		$('#text-montant-vente-opi').css({'display':'none'});

		$('#label-montant-bc-opi').css({'display':'none'});
		$('#text-montant-bc-opi').css({'display':'none'});
		 */
		$('#label-montant-ban').css({'display':'none'});
		$('#text-montant-vente-bn').css({'display':'none'});

		$('#label-montant-bc-ban').css({'display':'none'});
		$('#text-montant-bc-ban').css({'display':'none'});

		$('#label-montant-bc-ba').css({'display':'none'});
		$('#text-montant-bc-ba').css({'display':'none'});

		$('#label-montant-bc-bc').css({'display':'none'});
		$('#text-montant-bc-bc').css({'display':'none'});

		$('#label-montant-ba').css({'display':'none'});
		$('#text-montant-ba').css({'display':'none'});

		$('#label-cumul').css({'display':'none'});
		$('#text-montant-cumul').css({'display':'none'});
		$('#label-montant-bc-cumul').css({'display':'none'});
		$('#text-montant-bc-cumul').css({'display':'none'});
		
		$('#label-montant-bl').css({'display':'none'});
		$('#label-te-bl').css({'display':'none'});
		$('#text-montant-bl').css({'display':'none'});
		$('#form-field-select-te-bl').css({'display':'none'});


		$('#form-vente-radioDefaut').prop('checked', false);
		$('#form-vente-radioBc').prop('checked', false);
		$('#form-vente-radioBn').prop('checked', false);
		$('#form-vente-radioBa').prop('checked', false);
		//$('#form-vente-radioOpi').prop('checked', false);

		$typeBon ="APBAN";
		$typeMontantBon= "mbappro";
		console.log("$typeBon= "+$typeBon);
		console.log("$typeMontantBon= "+$typeMontantBon);

	});


	//radio BL
	$('body').on('click', '#form-vente-radioBl',function(e){

		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		$total = Math.floor(parseInt($('#total-montant-prixht').val()));
		
		$('#text-montant-bl').val(''+$total+'');
		$('#text-montant-bc-ba').val(''+0+'');
    	$('#text-montant-cumul').val(''+0+'');
		//$('#text-montant-vente-opi').val(''+0+'');
		$('#text-montant-vente-bn').val(''+0+'');
		$('#text-montant-ba').val(''+0+'');

		$('#label-montant-bl').css({'display':'inline-block'});
		$('#label-te-bl').css({'display':'inline-block'});
		$('#text-montant-bl').css({'display':'inline-block'});
		$('#form-field-select-te-bl').css({'display':'inline-block'});
		
		/*$('#label-montant-opi').css({'display':'none'});
		$('#text-montant-vente-opi').css({'display':'none'});

		$('#label-montant-bc-opi').css({'display':'none'});
		$('#text-montant-bc-opi').css({'display':'none'});
		 */
		$('#label-montant-ban').css({'display':'none'});
		$('#text-montant-vente-bn').css({'display':'none'});

		$('#label-montant-bc-ban').css({'display':'none'});
		$('#text-montant-bc-ban').css({'display':'none'});

		$('#label-montant-bc-ba').css({'display':'none'});
		$('#text-montant-bc-ba').css({'display':'none'});

		$('#label-montant-bc-bc').css({'display':'none'});
		$('#text-montant-bc-bc').css({'display':'none'});

		$('#label-montant-ba').css({'display':'none'});
		$('#text-montant-ba').css({'display':'none'});
		
		$('#label-cumul').css({'display':'none'});
		$('#text-montant-cumul').css({'display':'none'});
		$('#label-montant-bc-cumul').css({'display':'none'});
		$('#text-montant-bc-cumul').css({'display':'none'});

		$('#label-montant-approban').css({'display':'none'});
		$('#text-montant-vente-approbn').css({'display':'none'});

		$('#label-montant-bc-approban').css({'display':'none'});
		$('#text-montant-bc-approban').css({'display':'none'});


		$('#form-vente-radioDefaut').prop('checked', false);
		$('#form-vente-radioBc').prop('checked', false);
		$('#form-vente-radioBn').prop('checked', false);
		//	$('#form-vente-radioOpi').prop('checked', false);
		$('#form-vente-approban').prop('checked', false);
		$('#form-vente-radioBa').prop('checked', false);
		
		$typeBon ="BL";
		$typeMontantBon= "mbl";
		
	});





	//CALCUL DU CUMUL DES MONTANTS

	/*$('body').on('blur','#text-montant-vente-opi', function(e){
		$('#text-montant-cumul').val('0');
		$somme = parseInt($('#text-montant-cumul').val());
		$montantOpi = parseInt($('#text-montant-vente-opi').val());
		$montantBan = parseInt($('#text-montant-vente-bn').val());
		$montantBa = parseInt($('#text-montant-ba').val());
		$total = $somme+$montantOpi+$montantBan+$montantBa;
		$('#text-montant-cumul').val(''+$total+'');
		$('#text-montant-bc-cumul').val('0');
		$montantBc = parseInt($('#text-montant-bc-bc').val());
		$('#text-montant-bc-cumul').val(''+$montantBc+'');

	});
	$('body').on('blur','#text-montant-vente-bn',function(e){
		$('#text-montant-cumul').val('0');
		$somme = parseInt($('#text-montant-cumul').val());
		$montantOpi = parseInt($('#text-montant-vente-opi').val());
		$montantBan = parseInt($('#text-montant-vente-bn').val());
		$montantBa = parseInt($('#text-montant-ba').val());
		$total = $somme+$montantOpi+$montantBan+$montantBa;
		$('#text-montant-cumul').val(''+$total+'');
		$('#text-montant-bc-cumul').val('0');
		$montantBc = parseInt($('#text-montant-bc-bc').val());
		$('#text-montant-bc-cumul').val(''+$montantBc+'');

	});

	$('body').on('blur','#text-montant-ba',function(e){
		$('#text-montant-cumul').val('0');
		$somme = parseInt($('#text-montant-cumul').val());
		$montantOpi = parseInt($('#text-montant-vente-opi').val());
		$montantBan = parseInt($('#text-montant-vente-bn').val());
		$montantBa = parseInt($('#text-montant-ba').val());
		$total = $somme+$montantOpi+$montantBan+$montantBa;
		$('#text-montant-cumul').val(''+$total+'');
		$('#text-montant-bc-cumul').val('0');
		$montantBc = parseInt($('#text-montant-bc-bc').val());
		$('#text-montant-bc-cumul').val(''+$montantBc+'');

	});

	$('body').on('blur','#text-montant-bc-bc',function(e){
		$('#text-montant-cumul').val('0');
		$somme = parseInt($('#text-montant-cumul').val());
		$montantOpi = parseInt($('#text-montant-vente-opi').val());
		$montantBan = parseInt($('#text-montant-vente-bn').val());
		$montantBa = parseInt($('#text-montant-ba').val());
		$total = $somme+$montantOpi+$montantBan+$montantBa;
		$('#text-montant-cumul').val(''+$total+'');
		$('#text-montant-bc-cumul').val('0');
		$montantBc = parseInt($('#text-montant-bc-bc').val());
		$('#text-montant-bc-cumul').val(''+$montantBc+'');

	});
	 */


	/*//recuperation du montant du ba
	$('body').on('blur','#text-numero-ba',function(e){
		$super_notification2.removeClass('alert alert-danger');
		$super_notification2.text('');
		$numeroBA = $('#text-numero-ba').val();
		$codeMembreAcheteur= $('#form-vente-code-membre').val();
		console.log('$codeMembreAcheteur= '+$codeMembreAcheteur);
		if($codeMembreAcheteur===''){
			$super_notification2.removeClass('alert alert-danger');
			$super_notification2.text('');  
			$super_notification2.addClass('alert alert-danger');
			$super_notification2.text('Attention le codemembre ne doit pas être vide');  
			$('#form-vente-code-membre').focus();
		}else if($numeroBA===""){
			$super_notification2.removeClass('alert alert-danger');
			$super_notification2.text('');  
			$super_notification2.addClass('alert alert-danger');
			$super_notification2.text('Veuillez saisir le numero de BA'); 
			$('#text-numero-ba').focus();
		}else{
			$.ajax({
				url:"montantba/"+$codeMembreAcheteur+"/"+$numeroBA,
				type:'GET',
				datatype:'json',
				contentType:'application/json',
				success: function(data){
					console.log(data);
					if(data===0){
						$super_notification2.addClass('alert alert-danger');
						$super_notification2.text('Le numero de bon n est pas valide!!'); 
					}else{
						$montantBa= Math.floor(data); 
						$('#text-montant-ba').val(''+$montantBa+'');
					}
				}
			});
		}
	});*/

	//radio livraison sur place
	$('body').on('click', '#form-vente-surplace',function(e){

		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		$modeLivraison = 2;
		$('#label-adresseLivraison').css({'display':'none'});
		$('#form-vente-adresseLivraison').css({'display':'none'});
		$('#label-frais-livraison').css({'display':'none'});
		$('#text-montant-frais-livraison').css({'display':'none'});
		$('#form-vente-domicile').prop('checked', false);
	
		
	});
	
	//radio livraison a domicile
	$('body').on('click', '#form-vente-domicile',function(e){

		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		$modeLivraison = 1;
		$('#label-adresseLivraison').css({'display':'inline'});
		$('#form-vente-adresseLivraison').css({'display':'inline'});
		$('#label-frais-livraison').css({'display':'inline'});
		$('#text-montant-frais-livraison').css({'display':'inline'});
		$('#form-vente-surplace').prop('checked', false);
		
	});

	// boite de dialogue pour le formulaire vente simple

	$('body').on('click','#form-vente-btOK', function(e){
		e.preventDefault();
		$super_notification2.removeClass('alert alert-danger');
		$super_notification2.text('');
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');

		$typeR = "";
		$typeBon ="";

		//recuperation du code type Credit
		$codeTypeCredit = $('#form-field-select-codetypecredit option:selected').val();
		//recupération du type recurrent

		if($('#form-vente-radior').is(':checked') === true){
			$typeR = "r";
			$perioder = $('#form-field-select-limite option:selected').val();
		}else if($('#form-vente-radionr').is(':checked') === true){
			$typeR = "nr";
			$prk = $('#form-field-select-prk option:selected').val();
		}

		$bps = $('#form-field-select-bps option:selected').val();
		$frequence = $('#form-field-select-frequence option:selected').val();
		

		if($('#form-vente-radioDefaut').is(':checked') === true){
			//recuperation du type bon
			$typeMontantBon = "mdf";
			$typeBon = "DF";
		
		}


		if($('#form-vente-radioBn').is(':checked') === true){
			//recuperation du type bon
			$typeBon = "BN";
			
			//recuperation du type de montant
			if (parseInt($('#text-montant-vente-bn').val())===0){
				$typeMontantBon ="mbc";
				
			}else{
				$typeMontantBon ="mbn";
				
			}
			if($('#form-vente-radioInterimAchat').is(':checked') === true){
				$typeBon = "BIA";
				
			}
			
		}
		if($('#form-vente-radioBa').is(':checked') === true){
			//recuperation du type bon
			$typeBon = "BA";
			//recuperation du type de montant
			$typeMontantBon ="mba";
		}
		
		if($('#form-vente-radioBl').is(':checked') === true){
			//recuperation du type bon
			$typeBon = "BL";
			//recuperation du type de montant
			$typeMontantBon ="mbl";
		}
		if($('#form-vente-radioBc').is(':checked') === true){
			//recuperation du type bon
			$typeBon = "BC";
			//recuperation du type de montant
			$typeMontantBon ="mbonc";
			/*//recuperation du numero du bon
			$numeroBA =	$('#text-numero-ba').val();*/
		}
		/*if($('#form-vente-radio-ordinaire').is(':checked') === true){
			//recuperation du type marchandise
			$typeProduit = "PO";
			console.log("$typeProduit: "+$typeProduit);
		}
		if($('#form-vente-radio-speciale').is(':checked') === true){
			//recuperation du type marchandise
			$typeProduit = "PS";
			console.log("$typeProduit: "+$typeProduit);
		}*/
		if($('#form-produit-domicilie').is(':checked') === true){
			//domiciliation
			$domiciliation = 1;
		}
		
		if($('#form-vente-approban').is(':checked') === true){
			//recuperation du type marchandise
			$typeBon = "APBAN";
			
		}
		if($modeLivraison ==1){
			$adresseLivraison = $('#form-vente-adresseLivraison').val();
			$fraisLivraison =parseInt($('#text-montant-frais-livraison').val());
		
		}else{
			$fraisLivraison=0;
		}
		//contrôles
		/*if($typeR===''){
			$super_notification2.removeClass('alert alert-danger');
			$super_notification2.text('');  
			$super_notification2.addClass('alert alert-danger');
			$super_notification2.text('Veuillez selectionner un type recurrent ou non recurrent'); 
			$('#form-vente-radior').focus();

		}else */if($typeBon===''){
			$super_notification2.removeClass('alert alert-danger');
			$super_notification2.text('');  
			$super_notification2.addClass('alert alert-danger');
			$super_notification2.text('Veuillez selectionner le type de bon à utiliser'); 
			$('#form-vente-radioBn').focus();

		}else if($typeMontantBon===''){
			$super_notification2.removeClass('alert alert-danger');
			$super_notification2.text('');  
			$super_notification2.addClass('alert alert-danger');
			$super_notification2.text('Veuillez selectionner le type de bon à utiliser'); 
			$('#form-vente-radioBn').focus();
		}/*else if($typeProduit===''){
			$super_notification2.removeClass('alert alert-danger');
			$super_notification2.text('');  
			$super_notification2.addClass('alert alert-danger');
			$super_notification2.text('Veuillez selectionner le type de marchandise'); 

		}*/else if($codeTypeCredit===''){
			$super_notification2.removeClass('alert alert-danger');
			$super_notification2.text('');  
			$super_notification2.addClass('alert alert-danger');
			$super_notification2.text('Veuillez selectionner le type de produit'); 

		}else{//recuperation du code membre
			$codeMembreAcheteur = $('#form-vente-code-membre').val();
			if($codeMembreAcheteur===''){
				$super_notification2.removeClass('alert alert-danger');
				$super_notification2.text('');  
				$super_notification2.addClass('alert alert-danger');
				$super_notification2.text('Attention le codemembre ne doit pas être vide');  
				$('#form-vente-code-membre').focus();

			}else if($('#form-vente-code-membre').val().length<20){
				$super_notification2.removeClass('alert alert-danger');
				$super_notification2.text('');  
				$super_notification2.addClass('alert alert-danger');
				$super_notification2.text('Veuillez vérifier le code membre saisi');  
				$('#form-vente-code-membre').focus();
			}
			

			if($typeBon ==='BA'){
				$montantAchatHT = Math.floor(parseInt($('#text-montant-bc-ba').val()));
				console.log("$montantAchatHT mba: "+$montantAchatHT);
				if($montantAchatHT<=0){
					$super_notification2.removeClass('alert alert-danger');
					$super_notification2.text('');  
					$super_notification2.addClass('alert alert-danger');
					$super_notification2.text('Le montant de l achat est invalide!! Veuillez remplir le bon de livraison'); 
					$('#form-vente-reference').focus();
				}else{
					$csrf = $('input[name="_csrf"]').val();
					rechercheMontant = "modeLivraison="+$modeLivraison+"&fraisLivraison="+$fraisLivraison+"&codeTypeCredit="+$codeTypeCredit+"&perioder="+$perioder+"&bps="+$bps+"&frequence="+$frequence+"&prk="+$prk+"&typeMontantBon="+$typeMontantBon+"&montantAchat="+$montantAchatHT+"&typeR="+$typeR+"&codeMembreAcheteur="+$codeMembreAcheteur+"&_csrf=" + $csrf + "";

					$('#load_createbon_vente').css({'display':'inline-block'});
					$('#load_createbon_vente_icone').css({'display':'inline-block'});

					$.ajax({
						url:"findmontantandsendsmsbons",
						type:'POST',
						data: rechercheMontant,
						success: function(data){
							$('#load_createbon_vente').fadeOut();
							$('#load_createbon_vente_icone').fadeOut();
							if(data[0]=="KO1"){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('le montant du bon est insuffisant');    
							}else if(data[0]=="KO2"){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('Il n y a pas de bon d achat disponible');  
							}else if(data[0]=="KO3"){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('revoir la saisie des données'); 
							}else if(data[0]=="KO02"){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('Le quota est dépassé'); 
							}else{
								//qrcaptcha
								$('#qrcaptcha').css({'display':'inline-block'});
								$('#code_membre_qr_auth').val(''+$codeMembreAcheteur+'');

								/*$('#text-codesms').val('ESMC '+data[0]+'');
								$('#notification_sms').css({'display':'inline-block'});
								$codeEnvoi = data[0];
								console.log("$codeEnvoi= "+$codeEnvoi);
								$super_notification2.addClass('alert alert-info');
								$super_notification2.text('Envoi de SMS au membre');*/
								$('#text-montant-ba').val(''+data[0]+'');

								var montantBonConso = parseInt($("#text-montant-bc-ba").val());

								if(montantBonConso !== parseInt($('#total-montant-prixht').val())){
									$('#table-article-list-retreave-container').html('');
									$('#text-prix').val(''+montantBonConso+'');
									$('#text-quantite').val('1');
									$('#total-montant-prixht').val('0');
									$super_notification2.addClass('alert alert-danger');
									$super_notification2.text('Bien renseigner le bon de livraison'); 	
								} 
							}
						},
						error: function (xhr, textStatus, errorThrown) {
							$('#load_createbon_vente').fadeOut();
							$('#load_createbon_vente_icone').fadeOut();
							$super_notification2.addClass('alert alert-danger');
							$super_notification2.text('La communication avec le serveur a echoué. Veuillez informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 
						}
					}); 

				}	

				/*}else if($typeBon ==='OPI'){
				$montantAchatHT = Math.floor(parseInt($('#text-montant-bc-opi').val()));
				console.log("$montantAchatHT opi: "+$montantAchatHT);
				if($montantAchatHT<=0){
					$super_notification2.removeClass('alert alert-danger');
					$super_notification2.text('');  
					$super_notification2.addClass('alert alert-danger');
					$super_notification2.text('Le montant de l achat est invalide!! Veuillez remplir le bon de livraison'); 
					$('#text-codebarre-search').focus();
				}else{

					$csrf = $('input[name="_csrf"]').val();
					rechercheMontant = "perioder="+$perioder+"&bps="+$bps+"&frequence="+$frequence+"&prk="+$prk+"&typeProduit="+$typeProduit+"&typeMontantBon="+$typeMontantBon+"&montantAchat="+$montantAchatHT+"&typeR="+$typeR+"&codeMembreAcheteur="+$codeMembreAcheteur+"&_csrf=" + $csrf + "";

					$('#load_createbon_vente').css({'display':'inline-block'});
					$('#load_createbon_vente_icone').css({'display':'inline-block'});

					$.ajax({
						url:"findmontantandsendsmsbons",
						type:'POST',
						data: rechercheMontant,
						success: function(data){
							$('#load_createbon_vente').fadeOut();
							$('#load_createbon_vente_icone').fadeOut();

							if(data[0]=="KO1"){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('le montant des opi disponible est insuffisant');    
							}else if(data[0]=="KO2"){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('Il n y a pas de OPI disponibles');  
							}else if(data[0]=="KO3"){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('revoir la saisie des données'); 
							}else{
								$('#text-codesms').val('ESMC '+data[0]+'');
								$('#notification_sms').css({'display':'inline-block'});
								$codeEnvoi = data[0];
								console.log("$codeEnvoi= "+$codeEnvoi);	

								$('#text-montant-vente-opi').val(''++'');

								var montantBonConso = parseInt($("#text-montant-bc-opi").val());

								if(montantBonConso !== parseInt($('#total-montant-prixht').val())){
									$('#table-article-list-retreave-container').html('');
									$('#text-prix').val(''+montantBonConso+'');
									$('#text-quantite').val('1');
									$('#total-montant-prixht').val('0');
									$super_notification2.addClass('alert alert-danger');
									$super_notification2.text('Bien renseigner le bon de livraison'); 	
								}
							}
						},
						error: function (xhr, textStatus, errorThrown) {
							$('#load_createbon_vente').fadeOut();
							$('#load_createbon_vente_icone').fadeOut();
							$super_notification2.addClass('alert alert-danger');
							$super_notification2.text('La communication avec le serveur a echoué. Veuillez informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 
						}
					}); 
				}	

				 */}else if($typeBon ==='BN'){
					 //recuperation du montant 
					 if($typeMontantBon ==="mbc"){
						 $montantAchatHT = parseInt($('#text-montant-bc-ban').val());
						 
					 }else{
						 $montantAchatHT = parseInt($('#text-montant-vente-bn').val());
						
					 }

					 if($montantAchatHT<= 0){

						 if($typeMontantBon ==="mbc"){
							 $('#form-vente-reference').focus();
						 }else{
							 //ramener le focus sur le champ codebarre
							 $('#text-montant-vente-bn').focus(); 
						 }
					 }else{
						 $csrf = $('input[name="_csrf"]').val();
						 
						 rechercheMontant =  "modeLivraison="+$modeLivraison+"&fraisLivraison="+$fraisLivraison+"&codeTypeCredit="+$codeTypeCredit+"&perioder="+$perioder+"&bps="+$bps+"&frequence="+$frequence+"&prk="+$prk+"&typeMontantBon="+$typeMontantBon+"&montantAchat="+$montantAchatHT+"&typeR="+$typeR+"&codeMembreAcheteur="+$codeMembreAcheteur+"&_csrf=" + $csrf + "";

						 $('#load_createbon_vente').css({'display':'inline-block'});
						 $('#load_createbon_vente_icone').css({'display':'inline-block'});

						 $.ajax({
							 url:"findmontantandsendsms",
							 type:'POST',
							 data: rechercheMontant,
							 success: function(data){

								 $('#load_createbon_vente').fadeOut();
								 $('#load_createbon_vente_icone').fadeOut();
								 if(data[0]=="KO1"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Le montant du bon neutre est insuffisant');    
								 }else if(data[0]=="KO2"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('il n y a pas de bon neutre correspondant à ce codemembre');  
								 }else if(data[0]=="KO3"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('revoir la saisie des données'); 
								 }else if(data[0]=="KO0"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Le quota est dépassé'); 
								 }else{
									 if($typeMontantBon ==="mbc"){
										 $('#text-montant-vente-bn').val(''+data[0]+'');

									 }else{
										 $('#text-montant-bc-ban').val(''+data[0]+'');
									 }
									 var montantBonConso = parseInt($('#text-montant-bc-ban').val());

									 if(montantBonConso !== parseInt($('#total-montant-prixht').val())){
										 $('#table-article-list-retreave-container').html('');
										 $('#text-prix').val(''+montantBonConso+'');
										 $('#text-quantite').val('1');
										 $('#total-montant-prixht').val('0');
										 $super_notification2.addClass('alert alert-danger');
										 $super_notification2.text('Bien renseigner le bon de livraison'); 	
									 } 
									//qrcaptcha
									$('#qrcaptcha').css({'display':'inline-block'});
									$('#code_membre_qr_auth').val(''+$codeMembreAcheteur+'');

									/*$('#text-codesms').val('ESMC '+data[0]+'');
									 $('#notification_sms').css({'display':'inline-block'});
									 $codeEnvoi = data[0];
									 console.log("$codeEnvoi= "+$codeEnvoi);
									 $super_notification2.addClass('alert alert-info');
									 $super_notification2.text('Envoi de SMS au membre');*/
									
									 
								 }
							 },
							 error: function (xhr, textStatus, errorThrown) {
								 $('#load_createbon_vente').fadeOut();
								 $('#load_createbon_vente_icone').fadeOut();
								 $super_notification2.addClass('alert alert-danger');
								 $super_notification2.text('La communication avec le serveur a echoué. Veuillez informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 
							 }
						 }); 
					 }


				 }else if($typeBon ==='BC'){
					 $montantAchatHT = Math.floor(parseInt($('#text-montant-bc-bc').val()));
					 console.log("$montantAchatHT mbonc: "+$montantAchatHT);
					 if($montantAchatHT<=0){
						 $super_notification2.removeClass('alert alert-danger');
						 $super_notification2.text('');  
						 $super_notification2.addClass('alert alert-danger');
						 $super_notification2.text('Le montant de l achat est invalide!! Veuillez remplir le bon de livraison'); 
						 $('#form-vente-reference').focus();
					 }else{

						 $csrf = $('input[name="_csrf"]').val();
						 rechercheMontant =  "modeLivraison="+$modeLivraison+"&fraisLivraison="+$fraisLivraison+"&codeTypeCredit="+$codeTypeCredit+"&perioder="+$perioder+"&bps="+$bps+"&frequence="+$frequence+"&prk="+$prk+"&typeMontantBon="+$typeMontantBon+"&montantAchat="+$montantAchatHT+"&typeR="+$typeR+"&codeMembreAcheteur="+$codeMembreAcheteur+"&_csrf=" + $csrf + "";

						 $('#load_createbon_vente').css({'display':'inline-block'});
						 $('#load_createbon_vente_icone').css({'display':'inline-block'});

						 $.ajax({
							 url:"findmontantandsendsmsbons",
							 type:'POST',
							 data: rechercheMontant,
							 success: function(data){
								 $('#load_createbon_vente').fadeOut();
								 $('#load_createbon_vente_icone').fadeOut();
								 if(data[0]=="KO1"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('le montant du bon de consommation est insuffisant');    
								 }else if(data[0]=="KO2"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Il n y a pas de bon de consommation disponible');  
								 }else if(data[0]=="KO02"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Le quota est dépassé'); 
								 }else if(data[0]=="KO3"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('revoir la saisie des données'); 
								 }else if(data[0]=="KO0"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Echec d envoi du sms de confirmation'); 
								 }else{
									//qrcaptcha
									$('#qrcaptcha').css({'display':'inline-block'});
									$('#code_membre_qr_auth').val(''+$codeMembreAcheteur+'');

									/* $('#text-codesms').val('ESMC '+data[0]+'');
									 $('#notification_sms').css({'display':'inline-block'});
									 $codeEnvoi = data[0];
									 console.log("$codeEnvoi= "+$codeEnvoi);	
									 $super_notification2.addClass('alert alert-info');
									 $super_notification2.text('Envoi de SMS au membre');*/
									
								 }
							 },
							 error: function (xhr, textStatus, errorThrown) {
								 $('#load_createbon_vente').fadeOut();
								 $('#load_createbon_vente_icone').fadeOut();
								 $super_notification2.addClass('alert alert-danger');
								 $super_notification2.text('La communication avec le serveur a echoué. Veuillez informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 
							 }
						 }); 
					 }	

				 }else if($typeBon ==='DF'){
					 $montantAchatHT = parseInt($('#text-montant-bc-cumul').val());
					 console.log("$montantAchatHT def: "+$montantAchatHT);

					 if($montantAchatHT<= 0){ 

						 $super_notification2.addClass('alert alert-danger');
						 $super_notification2.text('Le montant du cumul est invalide');   	
					 }else{
						 $csrf = $('input[name="_csrf"]').val();

						 rechercheMontant =  "modeLivraison="+$modeLivraison+"&fraisLivraison="+$fraisLivraison+"&codeTypeCredit="+$codeTypeCredit+"&perioder="+$perioder+"&bps="+$bps+"&frequence="+$frequence+"&prk="+$prk+"&typeMontantBon="+$typeMontantBon+"&montantAchat="+$montantAchatHT+"&typeR="+$typeR+"&codeMembreAcheteur="+$codeMembreAcheteur+"&_csrf=" + $csrf + "";

						 $('#load_createbon_vente').css({'display':'inline-block'});
						 $('#load_createbon_vente_icone').css({'display':'inline-block'});

						 $.ajax({
							 url:"findmontantandsendsmsCumul",
							 type:'POST',
							 data: rechercheMontant,
							 success: function(data){
								 $('#load_createbon_vente').fadeOut();
								 $('#load_createbon_vente_icone').fadeOut();

								 if(data[0]=="KO1"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Les ressources demandées sont indisponibles'); 
								 }else if(data[0]=="KO2"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Revoir la saisie des données');  
								 }else if(data[0]=="KO02"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Le quota est dépassé'); 
								 }else if(data[0]=="KO0"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Echec d envoi du sms de confirmation'); 
								 }else{
									//qrcaptcha
									$('#qrcaptcha').css({'display':'inline-block'});
									$('#code_membre_qr_auth').val(''+$codeMembreAcheteur+'');

									/* $super_notification2.addClass('alert alert-info');
									 $super_notification2.text('Envoi de SMS au membre');
									
									 $('#text-codesms').val('ESMC '+data[0]+'');
									 $('#notification_sms').css({'display':'inline-block'});
									 $codeEnvoi = data[0];
									 console.log("$codeEnvoi= "+$codeEnvoi);	*/

									 $('#text-montant-cumul').val(''+data[0]+'');

									 /*var montantBonConso = parseInt($('#text-montant-bc-cumul').val());

								if(montantBonConso !== parseInt($('#total-montant-prixht').val())){
									$('#table-article-list-retreave-container').html('');
									$('#text-prix').val(''+montantBonConso+'');
									$('#text-quantite').val('1');
									$('#total-montant-prixht').val('0');
									$super_notification2.addClass('alert alert-danger');
									$super_notification2.text('Bien renseigner le bon de livraison'); 	
								}  
									  */
									 /*$montantBonConsommationSouscrit = Math.floor(data);

									if($typeR=='r'){
										$('#texteRecurrent').val('RECURRENT');
									}else{
										$('#texteRecurrent').val('NON RECURRENT');
									}
									$('#montantAchatModal').val(''+$montantBonConsommationSouscrit+'');
									$('#montantBonNeutreUtiliser').val(''+$montantAchatHT+'');


									$('#viewMontantBn-modal').show();
									$('.modal-backdrop').show();


									$('#viewMontantBn-modal').modal({
										keyboard : false,
										backdrop : 'static',
										width : 300
									});*/



								 }
							 },
							 error: function (xhr, textStatus, errorThrown) {
								 $('#load_createbon_vente').fadeOut();
								 $('#load_createbon_vente_icone').fadeOut();
								 $super_notification2.addClass('alert alert-danger');
								 $super_notification2.text('La communication avec le serveur a echoué. Veuillez informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

							 }


						 }); 
					 }
				 }else if($typeBon ==='APBAN'){
					 /*	$('#label-montant-approban').css({'display':'none'});
				$('#text-montant-vente-approbn').css({'display':'none'});

				$('#label-montant-bc-approban').css({'display':'none'});
				$('#text-montant-bc-approban').css({'display':'none'});
					  */
					 $montantAchatHT = Math.floor(parseInt($('#text-montant-bc-approban').val()));
					 console.log("$montantAchatHT apban: "+$montantAchatHT);
					 if($montantAchatHT<=0){
						 $super_notification2.removeClass('alert alert-danger');
						 $super_notification2.text('');  
						 $super_notification2.addClass('alert alert-danger');
						 $super_notification2.text('Le montant de l achat est invalide!! Veuillez remplir le bon de livraison'); 
						 $('#form-vente-reference').focus();
					 }else{

						 $csrf = $('input[name="_csrf"]').val();
						 rechercheMontant =  "modeLivraison="+$modeLivraison+"&fraisLivraison="+$fraisLivraison+"&codeTypeCredit="+$codeTypeCredit+"&perioder="+$perioder+"&bps="+$bps+"&frequence="+$frequence+"&prk="+$prk+"&typeMontantBon="+$typeMontantBon+"&montantAchat="+$montantAchatHT+"&typeR="+$typeR+"&codeMembreAcheteur="+$codeMembreAcheteur+"&_csrf=" + $csrf + "";

						 $('#load_createbon_vente').css({'display':'inline-block'});
						 $('#load_createbon_vente_icone').css({'display':'inline-block'});

						 $.ajax({
							 url:"findmontantandsendsmsforappro",
							 type:'POST',
							 data: rechercheMontant,
							 success: function(data){
								 $('#load_createbon_vente').fadeOut();
								 $('#load_createbon_vente_icone').fadeOut();
								 if(data[0]=="KO1"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('le montant du bon est insuffisant');    
								 }else if(data[0]=="KO2"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Il n y a pas de bon neutre disponible pour le fournisseur');  
								 }else if(data[0]=="KO3"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('revoir la saisie des données'); 
								 }else if(data[0]=="KO0"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Echec d envoi du sms de confirmation'); 
								 }else if(data[0]=="KO02"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Le quota est dépassé'); 
								 }else{
									//qrcaptcha
										$('#qrcaptcha').css({'display':'inline-block'});
										$('#code_membre_qr_auth').val(''+$codeMembreAcheteur+'');

									 /*$('#text-codesms').val('ESMC '+data[0]+'');
									 $('#notification_sms').css({'display':'inline-block'});
									 $codeEnvoi = data[0];
									 console.log("$codeEnvoi= "+$codeEnvoi);*/

									 $('#text-montant-vente-approbn').val(''+data[0]+'');


								 }
							 },
							 error: function (xhr, textStatus, errorThrown) {
								 $('#load_createbon_vente').fadeOut();
								 $('#load_createbon_vente_icone').fadeOut();
								 $super_notification2.addClass('alert alert-danger');
								 $super_notification2.text('La communication avec le serveur a echoué. Veuillez informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 
							 }
						 }); 

					 }	

				 }else if($typeBon ==='BL'){
					
						 $montantAchatHT = Math.floor(parseInt($('#text-montant-bl').val()));
						 console.log("$montantAchatHT mbl: "+$montantAchatHT);
						 if($montantAchatHT<=0){
							 $super_notification2.removeClass('alert alert-danger');
							 $super_notification2.text('');  
							 $super_notification2.addClass('alert alert-danger');
							 $super_notification2.text('Le montant de l achat est invalide!! Veuillez remplir le bon de livraison'); 
							 $('#form-vente-reference').focus();
						 }else {
							 $codeTegc = $('#form-field-select-te-bl option:selected').val();
							 console.log("$codeTegc: "+$codeTegc);
													 
							 if($codeTegc===''){		
								 $super_notification2.removeClass('alert alert-danger');
								 $super_notification2.text('');  
								 $super_notification2.addClass('alert alert-danger');
								 $super_notification2.text('Pas de TE valide'); 
								 $('#form-field-select-te-bl option:selected').focus();
							
							  }else{

							 $csrf = $('input[name="_csrf"]').val();
							 rechercheMontant =  "modeLivraison="+$modeLivraison+"&fraisLivraison="+$fraisLivraison+"&codeTegc="+$codeTegc+"&codeTypeCredit="+$codeTypeCredit+"&perioder="+$perioder+"&bps="+$bps+"&frequence="+$frequence+"&prk="+$prk+"&typeMontantBon="+$typeMontantBon+"&montantAchat="+$montantAchatHT+"&typeR="+$typeR+"&codeMembreAcheteur="+$codeMembreAcheteur+"&_csrf=" + $csrf + "";

							 $('#load_createbon_vente').css({'display':'inline-block'});
							 $('#load_createbon_vente_icone').css({'display':'inline-block'});

							 $.ajax({
								 url:"findmontantandsendsmsbons",
								 type:'POST',
								 data: rechercheMontant,
								 success: function(data){
									 $('#load_createbon_vente').fadeOut();
									 $('#load_createbon_vente_icone').fadeOut();
									 if(data[0]=="KO1"){
										 $super_notification2.addClass('alert alert-danger');
										 $super_notification2.text('le montant du BL est insuffisant');    
									 }else if(data[0]=="KO2"){
										 $super_notification2.addClass('alert alert-danger');
										 $super_notification2.text('Il n y a pas de TE valide');  
									 }else if(data[0]=="KO3"){
										 $super_notification2.addClass('alert alert-danger');
										 $super_notification2.text('revoir la saisie des données'); 
									 }else if(data[0]=="KO0"){
										 $super_notification2.addClass('alert alert-danger');
										 $super_notification2.text('Echec d envoi du sms de confirmation'); 
									 }else if(data[0]=="KO02"){
										 $super_notification2.addClass('alert alert-danger');
										 $super_notification2.text('Le quota est dépassé'); 
									 }else{
										//qrcaptcha
											$('#qrcaptcha').css({'display':'inline-block'});
										 /*$('#text-codesms').val('ESMC '+data[0]+'');
										 $('#notification_sms').css({'display':'inline-block'});
										 $codeEnvoi = data[0];
										 $super_notification2.addClass('alert alert-info');
										 $super_notification2.text('Envoi de SMS au membre');*/
										
									 }
								 },
								 error: function (xhr, textStatus, errorThrown) {
									 $('#load_createbon_vente').fadeOut();
									 $('#load_createbon_vente_icone').fadeOut();
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('La communication avec le serveur a echoué. Veuillez informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 
								 }
							 }); 
						 }
						}

				 }else if($typeBon ==='BIA'){
					 
					 if($typeMontantBon ==="mbc"){
						 $montantAchatHT = parseInt($('#text-montant-bc-ban').val());
						 console.log("$montantAchatHT mbc: "+$montantAchatHT);
					 }else{
						 $montantAchatHT = parseInt($('#text-montant-vente-bn').val());
						 console.log("$montantAchatHT mbn: "+$montantAchatHT);
					 }

					 if($montantAchatHT<= 0){

						 if($typeMontantBon ==="mbc"){
							 $('#form-vente-reference').focus();
						 }else{
							 //ramener le focus sur le champ codebarre
							 $('#text-montant-vente-bn').focus(); 
						 }
					 }else{
						 $csrf = $('input[name="_csrf"]').val();
						 rechercheMontant =  "codeAchat="+$codeAchat+"&modeLivraison="+$modeLivraison+"&fraisLivraison="+$fraisLivraison+"&codeTypeCredit="+$codeTypeCredit+"&perioder="+$perioder+"&bps="+$bps+"&frequence="+$frequence+"&prk="+$prk+"&typeMontantBon="+$typeMontantBon+"&montantAchat="+$montantAchatHT+"&typeR="+$typeR+"&codeMembreAcheteur="+$codeMembreAcheteur+"&_csrf=" + $csrf + "";

						 $('#load_createbon_vente').css({'display':'inline-block'});
						 $('#load_createbon_vente_icone').css({'display':'inline-block'});

						 $.ajax({
							 url:"findmontantandsendsmsInterim",
							 type:'POST',
							 data: rechercheMontant,
							 success: function(data){

								 $('#load_createbon_vente').fadeOut();
								 $('#load_createbon_vente_icone').fadeOut();
								 if(data[0]=="KO1"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Vous avez dépassé le quota autorisé');    
								 }else if(data[0]=="KO2"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Montant du Bon neutre insuffisant');  
								 }else if(data[0]=="KO3"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Aucun enregistrement trouvé chez l acheteur'); 
								 }else if(data[0]=="KO0"){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Revoir la saisie'); 
								 }else{
									 $('#text-codesms').val('ESMC '+data[0]+'');
									 $('#notification_sms').css({'display':'inline-block'});
									 $codeEnvoi = data[0];
									 console.log("$codeEnvoi= "+$codeEnvoi);
									/* $super_notification2.addClass('alert alert-info');
									 $super_notification2.text('Envoi de SMS au membre');*/
									
									 if($typeMontantBon ==="mbc"){
										 $('#text-montant-vente-bn').val(''+data[0]+'');

									 }else{
										 $('#text-montant-bc-ban').val(''+data[0]+'');
									 }
									 var montantBonConso = parseInt($('#text-montant-bc-ban').val());

									 if(montantBonConso !== parseInt($('#total-montant-prixht').val())){
										 $('#table-article-list-retreave-container').html('');
										 $('#text-prix').val(''+montantBonConso+'');
										 $('#text-quantite').val('1');
										 $('#total-montant-prixht').val('0');
										 $super_notification2.addClass('alert alert-danger');
										 $super_notification2.text('Bien renseigner le bon de livraison'); 	
									 } 
								 }
							 },
							 error: function (xhr, textStatus, errorThrown) {
								 $('#load_createbon_vente').fadeOut();
								 $('#load_createbon_vente_icone').fadeOut();
								 $super_notification2.addClass('alert alert-danger');
								 $super_notification2.text('La communication avec le serveur a echoué. Veuillez informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 
							 }
						 }); 
					 }



				 }
			
		}

	}); 

	//bouton supprimer de la liste
	$('body').on('click', '#article-delete', function (e) {
		var $deletearticle = $(this);
		$selectparent = $deletearticle.parent().parent();

		$somme = parseInt($('#total-montant-prixht').val());
		$montantEnCours = $('td:eq(5)', $selectparent).text();
		$somme = $somme - ($montantEnCours);


		$selectparent.remove();
		$('#total-montant-prixht').val(''+$somme+'');

	});



	//bouton AJOUTER

	$('body').on('click', '#form-vente-button-ajout', function (e) {
		e.preventDefault();
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text(''); 
		$super_notification2.removeClass('alert alert-danger');
		$super_notification2.text(''); 

		//$codebarre = $('#text-codebarre-search').val();
		$reference = $('#form-vente-reference').val().toUpperCase(); 
		$designation = $('#form-vente-select-designation').val();
		$prix = $('#text-prix').val();
		$qte = $('#text-quantite').val();
		
		npatern = new RegExp('[^0-9]+', 'i');
		patern = new RegExp('[0-9]+', 'i');
		if($prix.match(npatern)){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Le prix doit être numerique');
			$('#text-prix').focus();
		}else if($qte.match(npatern)){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('La quantite doit être un nombre entier');
			$('#text-quantite').focus();
		}/*else if($prix <= 0){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Le prix est invalide');
			$('#text-prix').focus();
		}*/else if ($('#form-vente-select-designation').val() === "") {
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Attention la designation ne doit pas être vide');
			$('#form-vente-select-designation').focus();

		}else if($prix > 0){
			
			$qteEnStock = $qteEnStock - $qte;
			if($qteEnStock<0){
				$super_notification.removeClass('alert alert-danger');
				$super_notification.text('Plus de stock disponible!');
			  
			}else{

			$montant_total = parseInt($prix) * parseInt($qte);

			$stockretreave = '<tr class="reglement-commande-record"><td style="display:none" class="reglement-commande-record-child">' + $codebarre + '</td><td style="text-align:center" class="reglement-commande-record-child">' + $reference + '</td><td style="text-align:center" class="reglement-commande-record-child">' + $designation + '</td><td style="text-align:center" class="reglement-commande-record-child">' + $qte + '</td><td style="text-align:center" class="reglement-commande-record-child">' + $prix + '</td><td style="text-align:center" class="reglement-commande-record-child">' + $montant_total + '</td><td><button type="button" class="btn btn-primary btn-sm" id="article-delete" ><i class="ace-icon fa fa-fa-times"></i>Supprimer </button></td><td style="display:none" class="reglement-commande-record-child">' + $codeTegcVendeur + '</td> </tr>';
			if ($('#table-article-list-retreave-container > tr').length === 0 ) {
				$('#table-article-list-retreave-container').append('' + $stockretreave + '');
			} else if ($('#table-article-list-retreave-container > tr').length > 0) {
				$('#table-article-list-retreave-container > tr:last-child').after('' + $stockretreave + '');
			}
			$somme = parseInt($('#total-montant-prixht').val());
			$somme = $somme + ($montant_total);

			$montantAchatHT = $somme;
			$('#total-montant-prixht').val(''+$montantAchatHT+'');
			$('#text-codebarre-search').val('');
			$('#form-vente-reference').val('');
			$('#form-vente-select-designation').val('');
			$('#text-quantite').val('1');
			$('#text-prix').val('0');
			//mise à jour de la quantité des articles 
			//$qteEnStock = $qteEnStock - $qte;
			if($qteEnStock<1){
				$qteEnStock = 0;	
			}
			
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('stock disponible: '+$qteEnStock);
			$('#form-vente-reference').focus();
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





//	bouton CONFIRMER 

	$('body').on('click', '#form-vente-confirmer', function (e) {
		e.preventDefault();

		if($typeBon==="BN"){
			if($typeMontantBon ==="mbn"){
				$('#viewMontantBn-modal').hide();
				$('.modal-backdrop').hide();

				//supprimer le bordereau
				var montantBonConso = parseInt($("#text-montant-bc-ban").val());
				$('#table-article-list-retreave-container').html('');

				$("#text-prix").val(''+montantBonConso+'');
				$("#text-quantite").val('1');
				$('#total-montant-prixht').val('0');
				$designation = $('#form-vente-select-designation option:selected').text();
				if($designation===""){
					$super_notification.removeClass('alert alert-danger');
					$super_notification.text('');
					$super_notification.addClass('alert alert-danger');
					$super_notification.text('La désignation ne doit pas être vide');
					$('#form-vente-select-designation').focus();
				}
			}else{
				$('#viewMontantBn-modal').hide();
				$('.modal-backdrop').hide();
			}
		}else if($typeBon==="BA"){
			$('#viewMontantBn-modal').hide();
			$('.modal-backdrop').hide();

			//supprimer le bordereau
			//	var montantBonConso = parseInt($("#text-montant-ba").val());
			$('#table-article-list-retreave-container').html('');

			$("#text-prix").val(''+$montantBonConsommationSouscrit+'');
			$("#text-quantite").val('1');
			$('#total-montant-prixht').val('0');
			$designation = $('#form-vente-select-designation option:selected').text();
			if($designation===""){
				$super_notification.removeClass('alert alert-danger');
				$super_notification.text('');
				$super_notification.addClass('alert alert-danger');
				$super_notification.text('La désignation ne doit pas être vide');
				$('#form-vente-select-designation').focus();
			}
		}else{
			$('#viewMontantBn-modal').hide();
			$('.modal-backdrop').hide();
		}


	});

//	bouton FINAL VALIDER 

	$('body').on('click', '#form-vente-valider', function (e) {
		e.preventDefault();
		$super_notification2.removeClass('alert alert-danger');
		$super_notification2.text('');
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');

		$listArticlesVendus = [];

		//recuperation des articles du bordereau
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
		});
		/*if($modeLivraison ==1){
			$adresseLivraison = $('#form-vente-adresseLivraison').val();
			$fraisLivraison =parseInt($('#text-montant-frais-livraison').val());
			var $articlesVendus = new ArticleVendu('FLIV','FLIV', 'Frais de Livraison', 1, $fraisLivraison, '');
			$listArticlesVendus.push($articlesVendus);
		}*/
		
		if($listArticlesVendus.length <= 0 ){
			$super_notification2.removeClass('alert alert-danger');
			$super_notification2.text('');
			$super_notification2.addClass('alert alert-danger');
			$super_notification2.text('Le bon de livraison est vide');
		}else{
			$jsonlist = JSON.stringify($listArticlesVendus);
			$telephoneAcheteur = $('#form-vente-telephone').val();
			if(!$telephoneAcheteur){
				$super_notification2.removeClass('alert alert-danger');
				$super_notification2.text('');  
				$super_notification2.addClass('alert alert-danger');
				$super_notification2.text('Veuillez choisir un numero de téléphone');  
				$('#form-vente-telephone').focus();
			}
			
						
			//recupération du type recurrent

			if($('#form-vente-radior').is(':checked') === true){
				$typeR = "r";
				$perioder = $('#form-field-select-limite option:selected').val();
			}

			if($('#form-produit-domicilie').is(':checked') === true){
				//domiciliation
				$domiciliation = 1;
				}
			if($('#form-vente-radionr').is(':checked') === true){
				$typeR = "nr";
				$prk = $('#form-field-select-prk option:selected').val();
			}

			//recuperation du type bon
			if($('#form-vente-radioBn').is(':checked') === true){
				$typeBon = "BN";
				
			}
			if($('#form-vente-radioDefaut').is(':checked') === true){
				$typeMontantBon = "mdf";
				
			}
			/*if($('#form-vente-radio-ordinaire').is(':checked') === true){
				//recuperation du type marchandise
				$typeProduit = "PO";
				console.log("$typeProduit: "+$typeProduit);
			}
			if($('#form-vente-radio-speciale').is(':checked') === true){
				//recuperation du type marchandise
				$typeProduit = "PS";
				console.log("$typeProduit: "+$typeProduit);
			}*/

			if($('#form-vente-radioBa').is(':checked') === true){
				//recuperation du type bon
				$typeBon = "BA";
				console.log("$typeBon: "+$typeBon);
				//recuperation du type de montant
				$typeMontantBon ="mba";
			}
			
			if($('#form-vente-radioBl').is(':checked') === true){
				//recuperation du type bon
				$typeBon = "BL";
				//recuperation du type de montant
				$typeMontantBon ="mbl";
			}
			$codeMembreAcheteur = $('#form-vente-code-membre').val();
			
			//recuperation des references additives
			$referenceAdditive = $('#text-reference-additive').val();
			
		
			//recuperation du code du bon de consommation
			$codeBonConso = $('#form-vente-bonConso').val();
			
			/*if($typeR===''){
				$super_notification2.removeClass('alert alert-danger');
				$super_notification2.text('');  
				$super_notification2.addClass('alert alert-danger');
				$super_notification2.text('Veuillez selectionner un type recurrent ou non recurrent'); 
				$('#form-vente-radior').focus();
			}else if($typeProduit===''){
				$super_notification2.removeClass('alert alert-danger');
				$super_notification2.text('');  
				$super_notification2.addClass('alert alert-danger');
				$super_notification2.text('Veuillez selectionner le type de marchandise'); 
				$('#form-vente-radio-ordinaire').focus();
			}else */if($codeMembreAcheteur===""){
				$super_notification2.removeClass('alert alert-danger');
				$super_notification2.text('');
				$super_notification2.addClass('alert alert-danger');
				$super_notification2.text('Attention!! Veuillez creer le bon au préalable après avoir renseigner le code membre...');
				$('#form-vente-code-membre').focus();
			}else if($codeBonConso===""){
				$('#load_validate_vente').fadeOut();
				$('#load_validate_vente_icone').fadeOut();
				$super_notification2.removeClass('alert alert-danger');
				$super_notification2.text('');
				$super_notification2.addClass('alert alert-danger');
				$super_notification2.text('le code du bon de consommation ne doit pas être vide');
				$('#form-vente-bonConso').focus();
			}


			if($typeBon==="BN"){
				if (parseInt($('#text-montant-vente-bn').val())===0 && parseInt($('#text-montant-bc-ban').val())!==0){
					//recuperation du type de montant
					$typeMontantBon ="mbc";
					$montantAchatHT = parseInt($('#text-montant-bc-ban').val());
				}else if (parseInt($('#text-montant-vente-bn').val())!==0){
					$typeMontantBon ="mbn";
					$montantAchatHT = parseInt($('#text-montant-vente-bn').val());
				}
				if($montantAchatHT<=0){
					$super_notification2.removeClass('alert alert-danger');
					$super_notification2.text('');  
					$super_notification2.addClass('alert alert-danger');
					$super_notification2.text('Le montant n est pas valide! veuillez reprendre le bon de livraison'); 
				}else{
					if($modeLivraison ==1){
						//$montantAchatHT = $montantAchatHT+$fraisLivraison;
						//$montantAchatHT = $montantAchatHT+$fraisLivraison;
					}
					
					$('#load_validate_vente').css({'display':'inline-block'});
					$('#load_validate_vente_icone').css({'display':'inline-block'});

					$csrf = $('input[name="_csrf"]').val();
					
					reglement =  "referenceAdditive="+$referenceAdditive+"&domiciliation="+$domiciliation+"&fraisLivraison="+$fraisLivraison+"&telephoneAcheteur="+$telephoneAcheteur+"&modeLivraison="+$modeLivraison+"&adresseLivraison="+$adresseLivraison+"&codeTypeCredit="+$codeTypeCredit+"&perioder="+$perioder+"&bps="+$bps+"&frequence="+$frequence+"&prk="+$prk+"&codeEnvoi="+$codeEnvoi+"&codeBonConso="+$codeBonConso+"&codeMembreAcheteur="+$codeMembreAcheteur+"&typeMontantBon="+$typeMontantBon+"&typeR="+$typeR+"&montantAchat="+$montantAchatHT+"&listArticlesVendus="+$jsonlist+"&_csrf=" + $csrf + "";

					$.ajax({
						url:"creationBonConsoFactureByBan",
						type:'POST',
						data:reglement,
						success: function(data){

							$('#load_validate_vente').fadeOut();
							$('#load_validate_vente_icone').fadeOut();

							if(data==0){

								/*$('#notification_sms').css({'display':'none'});
								$('#text-codesms').val('');*/
								$('#esmc_notification_final').css({'display':'block'});
								$('#text-reference-vente').val('');
								$('#text-montant-vente-bn').val('0');
								$('#text-montant-bc-ban').val('0');
								$('#form-vente-code-membre').val('');
								$('#form-vente-bonConso').val('');
								$('#total-montant-prixht').val('0');

								$('#label-montant-ban').css({'display':'none'});
								$('#text-montant-vente-bn').css({'display':'none'});

								$('#label-montant-bc-ban').css({'display':'none'});
								$('#text-montant-bc-ban').css({'display':'none'});
								$('#table-article-list-retreave-container').html('');

								$('#form-vente-radio-ordinaire').prop('checked', false);
								$('#form-vente-radio-speciale').prop('checked', false);
								$('#form-vente-radionr').prop('checked', false);
								$('#form-vente-radior').prop('checked', false);
								$('#form-vente-radioBn').prop('checked', false);
								$('#form-vente-radioDefaut').prop('checked', false);
								$('#form-vente-radioBa').prop('checked', false);
								//$('#form-vente-radioOpi').prop('checked', false);
								$('#form-vente-approban').prop('checked', false);
								$('#form-field-select-limite').css({'display':'none'});
								$('#label-produit').css({'display':'none'});
								$('#form-field-select-bps').css({'display':'none'});
								$('#label-frequence').css({'display':'none'});
								$('#form-field-select-frequence').css({'display':'none'});
								$('#text-reference-additive').val('');
								
								$('#form-vente-adresseLivraison').val('');
								$('#text-montant-frais-livraison').val('0');
								$('#label-adresseLivraison').css({'display':'none'});
								$('#label-frais-livraison').css({'display':'none'});
								$('#form-vente-adresseLivraison').css({'display':'none'});
								$('#text-montant-frais-livraison').css({'display':'none'});
								
								$('#qrcaptcha').css({'display':'none'});
								$('#code_membre_qr_auth').val('');
								$('#form-vente-valide').disabled =true;

								$modeLivraison =2;
								
								$perioder='';
								$typeR='';
								$codeMembreAcheteur='';
								$codeBonConso='';
								$listArticlesVendus = [];
								//console.log($listArticlesVendus);

							}else if(data==1){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('Echec de souscription au Bon de Consommation');
							}else if(data==2){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('Echec de souscription au bon d achat');
							}else if(data==3){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('Echec de souscription au bon d achat');
							}else if(data==5){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('Revoir la saise des données');
							}

						},error: function (xhr, textStatus, errorThrown) {
							$('#load_validate_vente').fadeOut();
							$('#load_validate_vente_icone').fadeOut();
							$listArticlesVendus = [];
							$super_notification2.addClass('alert alert-danger');
							$super_notification2.text('La communication avec le serveur a echoué. Veuillez en informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

						}
					});   


				}
			}else if ($typeBon == "BA"){
				$montantAchatHT = Math.floor(parseInt($('#text-montant-bc-ba').val()));
				console.log("$montantAchatHT mba: "+$montantAchatHT);
				if($montantAchatHT<=0){
					$super_notification2.removeClass('alert alert-danger');
					$super_notification2.text('');  
					$super_notification2.addClass('alert alert-danger');
					$super_notification2.text('Le montant de l achat est invalide!! Veuillez remplir le bon de livraison'); 
					$('#text-codebarre-search').focus();
				}else{
					if($modeLivraison ==1){
						$montantAchatHT = $montantAchatHT+$fraisLivraison;
					}
					$('#load_validate_vente').css({'display':'inline-block'});
					$('#load_validate_vente_icone').css({'display':'inline-block'});

					$csrf = $('input[name="_csrf"]').val();

					reglement = "referenceAdditive="+$referenceAdditive+"&domiciliation="+$domiciliation+"&fraisLivraison="+$fraisLivraison+"&telephoneAcheteur="+$telephoneAcheteur+"&modeLivraison="+$modeLivraison+"&adresseLivraison="+$adresseLivraison+"&codeTypeCredit="+$codeTypeCredit+"&perioder="+$perioder+"&bps="+$bps+"&frequence="+$frequence+"&prk="+$prk+"&codeEnvoi="+$codeEnvoi+"&codeBonConso="+$codeBonConso+"&codeMembreAcheteur="+$codeMembreAcheteur+"&typeMontantBon="+$typeMontantBon+"&typeR="+$typeR+"&montantAchat="+$montantAchatHT+"&listArticlesVendus="+$jsonlist+"&_csrf=" + $csrf + "";

					$.ajax({
						url:"creationBonConsoByBa",
						type:'POST',
						data:reglement,
						success: function(data){
							$('#load_validate_vente').fadeOut();
							$('#load_validate_vente_icone').fadeOut();

							if(data==0){
								$('#notification_sms').css({'display':'none'});
								$('#text-codesms').val('');
								$('#esmc_notification_final').css({'display':'block'});
								$('#text-reference-vente').val(''); 
								$('#form-vente-code-membre').val('');
								$('#form-vente-bonConso').val('');
								$('#total-montant-prixht').val('0');
								$('#table-article-list-retreave-container').html('');

								$('#form-vente-radio-ordinaire').prop('checked', false);
								$('#form-vente-radio-speciale').prop('checked', false);
								$('#form-vente-radionr').prop('checked', false);
								$('#form-vente-radior').prop('checked', false);
								$('#form-vente-radioDefaut').prop('checked', false);
								$('#form-vente-radioBn').prop('checked', false);
								$('#form-vente-radioBa').prop('checked', false);
								//$('#form-vente-radioOpi').prop('checked', false);
								$('#form-vente-approban').prop('checked', false);


								$('#label-montant-ba').css({'display':'none'});
								$('#text-montant-ba').css({'display':'none'});

								$('#label-montant-bc-ba').css({'display':'none'});
								$('#text-montant-bc-ba').css({'display':'none'});
								$('#text-montant-bc-ba').val('0');
								$('#form-field-select-limite').css({'display':'none'});
								$('#label-produit').css({'display':'none'});
								$('#form-field-select-bps').css({'display':'none'});
								$('#label-frequence').css({'display':'none'});
								$('#form-field-select-frequence').css({'display':'none'});
								
								$('#form-vente-radioInterimAchat').prop('checked', false);
								$('#label-achatTe').css({'display':'none'});
								$('#text-code-achat-interim').css({'display':'none'});
								$('#qrcaptcha').css({'display':'none'});
								$('#code_membre_qr_auth').val('');
								$('#form-vente-valide').disabled =true;


								$perioder='';
								$codeMembreAcheteur='';
								$codeBonConso='';
								$codeEnvoi='';
								$listArticlesVendus = [];
								
							}else if(data==1){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('echec de creation du bon');
							}else if(data==2){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('le code du bon de consommation est invalide');
							}else if(data==3){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('revoir la saisie');
							}

						},error: function (xhr, textStatus, errorThrown) {
							$('#load_validate_vente_vente').fadeOut();
							$('#load_validate_vente_icone').fadeOut();
							$listArticlesVendus = [];
							$super_notification2.addClass('alert alert-danger');
							$super_notification2.text('La communication avec le serveur a echoué. Veuillez en informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

						}
					});   
				}
				/*}else if ($typeBon == "OPI"){
				$montantAchatHT = Math.floor(parseInt($('#text-montant-bc-opi').val()));
				console.log("$montantAchatHT opi: "+$montantAchatHT);
				if($montantAchatHT<=0){
					$super_notification2.removeClass('alert alert-danger');
					$super_notification2.text('');  
					$super_notification2.addClass('alert alert-danger');
					$super_notification2.text('Le montant de l achat est invalide!! Veuillez remplir le bon de livraison'); 
					$('#text-codebarre-search').focus();
				}else{
					$('#load_validate_vente').css({'display':'inline-block'});
					$('#load_validate_vente_icone').css({'display':'inline-block'});

					$csrf = $('input[name="_csrf"]').val();
					reglement = "perioder="+$perioder+"&bps="+$bps+"&frequence="+$frequence+"&prk="+$prk+"&typeProduit="+$typeProduit+"&codeEnvoi="+$codeEnvoi+"&codeBonConso="+$codeBonConso+"&codeMembreAcheteur="+$codeMembreAcheteur+"&typeMontantBon="+$typeMontantBon+"&typeR="+$typeR+"&montantAchat="+$montantAchatHT+"&listArticlesVendus="+$jsonlist+"&_csrf=" + $csrf + "";
					$.ajax({
						url:"creationBonConsoByOpi",
						type:'POST',
						data:reglement,
						success: function(data){
							$('#load_validate_vente').fadeOut();
							$('#load_validate_vente_icone').fadeOut();

							if(data==0){
								$('#notification_sms').css({'display':'none'});
								$('#text-codesms').val('');
								$('#esmc_notification_final').css({'display':'block'});
								$('#text-reference-vente').val(''); 
								$('#form-vente-code-membre').val('');
								$('#form-vente-bonConso').val('');
								$('#total-montant-prixht').val('0');

								$('#table-article-list-retreave-container').html('');

								$('#form-vente-radio-ordinaire').prop('checked', false);
								$('#form-vente-radio-speciale').prop('checked', false);
								$('#form-vente-radionr').prop('checked', false);
								$('#form-vente-radior').prop('checked', false);
								$('#form-vente-radioDefaut').prop('checked', false);
								$('#form-vente-radioBn').prop('checked', false);
								$('#form-vente-radioBa').prop('checked', false);
								$('#form-vente-approban').prop('checked', false);

								$('#label-montant-bc-opi').css({'display':'none'});
								$('#text-montant-bc-opi').css({'display':'none'});
								$('#text-montant-bc-opi').val('0');

								$('#label-montant-opi').css({'display':'none'});
								$('#text-montant-vente-opi').css({'display':'none'});
								$('#text-montant-vente-opi').val('0');
								$('#form-field-select-limite').css({'display':'none'});
								$('#label-produit').css({'display':'none'});
								$('#form-field-select-bps').css({'display':'none'});
								$('#label-frequence').css({'display':'none'});
								$('#form-field-select-frequence').css({'display':'none'});


								$perioder='';
								$typeProduit='';
								$codeMembreAcheteur='';
								$codeBonConso='';
								$codeEnvoi='';
								$listArticlesVendus = [];
								console.log($listArticlesVendus);
							}else if(data==1){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('echec de creation du bon de consommation');
							}else if(data==2){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('echec de souscription au bon de consommation');
							}else if(data==3){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('echec de l echange');
							}else if(data==4){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('montant des opi disponibles est insuffisant');
							}else if(data==5){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('le code du bon invalide');
							}else if(data==6){
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('revoir la saisie');
							}

						},error: function (xhr, textStatus, errorThrown) {
							$('#load_validate_vente_vente').fadeOut();
							$('#load_validate_vente_icone').fadeOut();
							$listArticlesVendus = [];
							$super_notification2.addClass('alert alert-danger');
							$super_notification2.text('La communication avec le serveur a echoué. Veuillez en informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

						}
					});   


				}
				 */}else if ($typeBon == "BC"){
					 $montantAchatHT = Math.floor(parseInt($('#total-montant-prixht').val()));
				
					 if($montantAchatHT<=0){
						 $super_notification2.removeClass('alert alert-danger');
						 $super_notification2.text('');  
						 $super_notification2.addClass('alert alert-danger');
						 $super_notification2.text('Le montant de l achat est invalide!! Veuillez remplir le bon de livraison'); 
						 $('#text-codebarre-search').focus();
					 }else{

						 if($modeLivraison ==1){
							$montantAchatHT = $montantAchatHT+$fraisLivraison;
							}
						 $('#load_validate_vente').css({'display':'inline-block'});
						 $('#load_validate_vente_icone').css({'display':'inline-block'});

						 $csrf = $('input[name="_csrf"]').val();

						 reglement = "referenceAdditive="+$referenceAdditive+"&domiciliation="+$domiciliation+"&fraisLivraison="+$fraisLivraison+"&telephoneAcheteur="+$telephoneAcheteur+"&modeLivraison="+$modeLivraison+"&adresseLivraison="+$adresseLivraison+"&codeTypeCredit="+$codeTypeCredit+"&perioder="+$perioder+"&bps="+$bps+"&frequence="+$frequence+"&prk="+$prk+"&codeEnvoi="+$codeEnvoi+"&codeBonConso="+$codeBonConso+"&codeMembreAcheteur="+$codeMembreAcheteur+"&typeR="+$typeR+"&montantAchat="+$montantAchatHT+"&listArticlesVendus="+$jsonlist+"&_csrf=" + $csrf + "";

						 $.ajax({
							 url:"creationBonConsoByBc",
							 type:'POST',
							 data:reglement,
							 success: function(data){
								 $('#load_validate_vente').fadeOut();
								 $('#load_validate_vente_icone').fadeOut();

								 if(data==0){
									 $('#notification_sms').css({'display':'none'});
									 $('#text-codesms').val('');
									 $('#esmc_notification_final').css({'display':'block'});
									 $('#text-reference-vente').val('');
									 $('#form-vente-code-membre').val('');
									 $('#form-vente-bonConso').val('');
									 $('#total-montant-prixht').val('0');
									 $('#table-article-list-retreave-container').html('');

									 $('#form-vente-radio-ordinaire').prop('checked', false);
									 $('#form-vente-radio-speciale').prop('checked', false);
									 $('#form-vente-radionr').prop('checked', false);
									 $('#form-vente-radior').prop('checked', false);
									 $('#form-vente-radioDefaut').prop('checked', false);
									 $('#form-vente-radioBn').prop('checked', false);
									 $('#form-vente-radioBa').prop('checked', false);
									 $('#form-vente-radioBc').prop('checked', false);
									 //$('#form-vente-radioOpi').prop('checked', false);
									 $('#form-vente-approban').prop('checked', false);

									 $('#label-montant-ba').css({'display':'none'});
									 $('#text-montant-ba').css({'display':'none'});

									 $('#label-montant-bc-bc').css({'display':'none'});
									 $('#text-montant-bc-bc').css({'display':'none'});
									 $('#text-montant-bc-bc').val('0');
									 $('#form-field-select-limite').css({'display':'none'});
									 $('#label-produit').css({'display':'none'});
									 $('#form-field-select-bps').css({'display':'none'});
									 $('#label-frequence').css({'display':'none'});
									 $('#form-field-select-frequence').css({'display':'none'});
									
									 $('#form-vente-radioInterimAchat').prop('checked', false);
									 $('#label-achatTe').css({'display':'none'});
									 $('#text-code-achat-interim').css({'display':'none'});
									 $('#qrcaptcha').css({'display':'none'});
									 $('#code_membre_qr_auth').val('');
									 $('#form-vente-valide').disabled =true;


									 $perioder='';
									 $typeR='';
									 $codeMembreAcheteur='';
									 $codeBonConso='';
									 $listArticlesVendus = [];
									
								 }else if(data==1){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('echec de creation du bon');
								 }else if(data==2){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('le code du bon est invalide');
								 }else if(data==3){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('revoir la saisie');
								 }

							 },error: function (xhr, textStatus, errorThrown) {
								 $('#load_validate_vente_vente').fadeOut();
								 $('#load_validate_vente_icone').fadeOut();
								 $listArticlesVendus = [];
								 $super_notification2.addClass('alert alert-danger');
								 $super_notification2.text('La communication avec le serveur a echoué. Veuillez en informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

							 }
						 });   
					 }
				 }else if ($typeBon == "DF"){
					 $montantAchatHT = parseInt($('#text-montant-bc-cumul').val());
					 console.log("$montantAchatHT def: "+$montantAchatHT);
					 if($montantAchatHT<=0){
						 $super_notification2.removeClass('alert alert-danger');
						 $super_notification2.text('');  
						 $super_notification2.addClass('alert alert-danger');
						 $super_notification2.text('Le montant de l achat est invalide!! Veuillez remplir le bon de livraison'); 
						 $('#text-codebarre-search').focus();
					 }else{
						 if($modeLivraison ==1){
							$montantAchatHT = $montantAchatHT+$fraisLivraison;
							}
						 $('#load_validate_vente').css({'display':'inline-block'});
						 $('#load_validate_vente_icone').css({'display':'inline-block'});

						 $csrf = $('input[name="_csrf"]').val();

						 reglement = "referenceAdditive="+$referenceAdditive+"&domiciliation="+$domiciliation+"&fraisLivraison="+$fraisLivraison+"&telephoneAcheteur="+$telephoneAcheteur+"&modeLivraison="+$modeLivraison+"&adresseLivraison="+$adresseLivraison+"&codeTypeCredit="+$codeTypeCredit+"&perioder="+$perioder+"&bps="+$bps+"&frequence="+$frequence+"&prk="+$prk+"&codeEnvoi="+$codeEnvoi+"&codeBonConso="+$codeBonConso+"&codeMembreAcheteur="+$codeMembreAcheteur+"&typeMontantBon="+$typeMontantBon+"&typeR="+$typeR+"&montantAchat="+$montantAchatHT+"&listArticlesVendus="+$jsonlist+"&_csrf=" + $csrf + "";

						 $.ajax({
							 url:"creationBonConsoByDefaut",
							 type:'POST',
							 data:reglement,
							 success: function(data){
								 $('#load_validate_vente').fadeOut();
								 $('#load_validate_vente_icone').fadeOut();

								 if(data==0){
									 $('#notification_sms').css({'display':'none'});
									 $('#text-codesms').val('');
									 $('#esmc_notification_final').css({'display':'block'});
									 $('#text-reference-vente').val(''); 
									 $('#form-vente-code-membre').val('');
									 $('#form-vente-bonConso').val('');
									 $('#total-montant-prixht').val('0');
									 $('#table-article-list-retreave-container').html('');

									 $('#form-vente-radio-ordinaire').prop('checked', false);
									 $('#form-vente-radio-speciale').prop('checked', false);
									 $('#form-vente-radionr').prop('checked', false);
									 $('#form-vente-radior').prop('checked', false);
									 $('#form-vente-radioDefaut').prop('checked', false);
									 $('#form-vente-radioBn').prop('checked', false);
									 $('#form-vente-radioBa').prop('checked', false);
									 //	$('#form-vente-radioOpi').prop('checked', false);
									 $('#form-vente-approban').prop('checked', false);


									 $('#label-cumul').css({'display':'none'});
									 $('#text-montant-cumul').css({'display':'none'});
									 $('#text-montant-cumul').val('0');

									 $('#label-montant-bc-cumul').css({'display':'none'});
									 $('#text-montant-bc-cumul').css({'display':'none'});
									 $('#text-montant-bc-cumul').val('0');
									 $('#form-field-select-limite').css({'display':'none'});
									 $('#label-produit').css({'display':'none'});
									 $('#form-field-select-bps').css({'display':'none'});
									 $('#label-frequence').css({'display':'none'});
									 $('#form-field-select-frequence').css({'display':'none'});
									 
									 $('#form-vente-radioInterimAchat').prop('checked', false);
									 $('#label-achatTe').css({'display':'none'});
									 $('#text-code-achat-interim').css({'display':'none'});
									 $('#qrcaptcha').css({'display':'none'});
									 $('#code_membre_qr_auth').val('');
									 $('#form-vente-valide').disabled =true;


									 $perioder='';
									 $codeMembreAcheteur='';
									 $codeBonConso='';
									 $codeEnvoi='';
									 $listArticlesVendus = [];
									
								 }else if(data==1){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('echec de creation du bon');
								 }else if(data==2){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('le code du bon de consommation est invalide');
								 }else if(data==3){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('revoir la saisie');
								 }

							 },error: function (xhr, textStatus, errorThrown) {
								 $('#load_validate_vente_vente').fadeOut();
								 $('#load_validate_vente_icone').fadeOut();
								 $listArticlesVendus = [];
								 $super_notification2.addClass('alert alert-danger');
								 $super_notification2.text('La communication avec le serveur a echoué. Veuillez en informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

							 }
						 });   
					 }
				 }else if ($typeBon == "APBAN"){
					 $montantAchatHT = parseInt($('#text-montant-bc-approban').val());
					 console.log("$montantAchatHT apban: "+$montantAchatHT);
					 if($montantAchatHT<=0){
						 $super_notification2.removeClass('alert alert-danger');
						 $super_notification2.text('');  
						 $super_notification2.addClass('alert alert-danger');
						 $super_notification2.text('Le montant de l achat est invalide!! Veuillez remplir le bon de livraison'); 
						 $('#text-codebarre-search').focus();
					 }else{
						 if($modeLivraison ==1){
							$montantAchatHT = $montantAchatHT+$fraisLivraison;
							}
						 $('#load_validate_vente').css({'display':'inline-block'});
						 $('#load_validate_vente_icone').css({'display':'inline-block'});

						 $csrf = $('input[name="_csrf"]').val();

						 reglement = "referenceAdditive="+$referenceAdditive+"&domiciliation="+$domiciliation+"&fraisLivraison="+$fraisLivraison+"&telephoneAcheteur="+$telephoneAcheteur+"&modeLivraison="+$modeLivraison+"&adresseLivraison="+$adresseLivraison+"&codeTypeCredit="+$codeTypeCredit+"&perioder="+$perioder+"&bps="+$bps+"&frequence="+$frequence+"&prk="+$prk+"&codeEnvoi="+$codeEnvoi+"&codeBonConso="+$codeBonConso+"&codeMembreAcheteur="+$codeMembreAcheteur+"&typeMontantBon="+$typeMontantBon+"&typeR="+$typeR+"&montantAchat="+$montantAchatHT+"&listArticlesVendus="+$jsonlist+"&_csrf=" + $csrf + "";

						 $.ajax({
							 url:"creationBonConsoFactureByBanAppro",
							 type:'POST',
							 data:reglement,
							 success: function(data){
								 $('#load_validate_vente').fadeOut();
								 $('#load_validate_vente_icone').fadeOut();

								 if(data==0){
									 $('#esmc_notification_final').css({'display':'block'});

									 $('#notification_sms').css({'display':'none'});
									 $('#text-codesms').val('');
									 $('#text-reference-vente').val(''); 
									 $('#form-vente-code-membre').val('');
									 $('#form-vente-bonConso').val('');
									 $('#total-montant-prixht').val('0');
									 $('#table-article-list-retreave-container').html('');

									 $('#form-vente-radio-ordinaire').prop('checked', false);
									 $('#form-vente-radio-speciale').prop('checked', false);
									 $('#form-vente-radionr').prop('checked', false);
									 $('#form-vente-radior').prop('checked', false);
									 $('#form-vente-radioDefaut').prop('checked', false);
									 $('#form-vente-radioBn').prop('checked', false);
									 $('#form-vente-radioBa').prop('checked', false);
									 //	$('#form-vente-radioOpi').prop('checked', false);
									 $('#form-vente-approban').prop('checked', false);

									 $('#label-montant-approban').css({'display':'none'});
									 $('#text-montant-vente-approbn').css({'display':'none'});
									 $('#text-montant-vente-approbn').val('0');

									 $('#label-montant-bc-approban').css({'display':'none'});
									 $('#text-montant-bc-approban').css({'display':'none'});
									 $('#text-montant-bc-approban').val('0');
									 $('#form-field-select-limite').css({'display':'none'});
									 $('#label-produit').css({'display':'none'});
									 $('#form-field-select-bps').css({'display':'none'});
									 $('#label-frequence').css({'display':'none'});
									 $('#form-field-select-frequence').css({'display':'none'});
									 $('#text-reference-additive').val('');
										
									 $('#form-vente-adresseLivraison').val('');
								     $('#text-montant-frais-livraison').val('0');
									 $('#label-adresseLivraison').css({'display':'none'});
									 $('#label-frais-livraison').css({'display':'none'});
									 $('#form-vente-adresseLivraison').css({'display':'none'});
									 $('#text-montant-frais-livraison').css({'display':'none'});
									
									 
									 $('#form-vente-radioInterimAchat').prop('checked', false);
									 $('#label-achatTe').css({'display':'none'});
									 $('#text-code-achat-interim').css({'display':'none'});
									 $('#qrcaptcha').css({'display':'none'});
									 $('#code_membre_qr_auth').val('');
									 $('#form-vente-valide').disabled =true;

									 $perioder='';
									 $codeMembreAcheteur='';
									 $codeBonConso='';
									 $codeEnvoi='';
									 $listArticlesVendus = [];
									 
								 }else if(data==1){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Echec de creation du bon de consommation');
								 }else if(data==2){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Echec de souscription au bon de consommation');
								 }else if(data==3){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Echec de souscription au bon d achat');
								 }else if(data==4){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Le code du bon de consommation est invalide');
								 }else if(data==5){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('Echec de creation du bon neutre acheteur');
								 }else if(data==6){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('revoir la saisie');
								 }

							 },error: function (xhr, textStatus, errorThrown) {
								 $('#load_validate_vente_vente').fadeOut();
								 $('#load_validate_vente_icone').fadeOut();
								 $listArticlesVendus = [];
								 $super_notification2.addClass('alert alert-danger');
								 $super_notification2.text('La communication avec le serveur a echoué. Veuillez en informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

							 }
						 });   
					 }
				 }else if ($typeBon == "BL"){
					 $montantAchatHT = Math.floor(parseInt($('#total-montant-prixht').val()));
					 console.log("$montantAchatHT BC: "+$montantAchatHT);

					 if($montantAchatHT<=0){
						 $super_notification2.removeClass('alert alert-danger');
						 $super_notification2.text('');  
						 $super_notification2.addClass('alert alert-danger');
						 $super_notification2.text('Le montant de l achat est invalide!! Veuillez remplir le bon de livraison'); 
						 $('#text-codebarre-search').focus();
					 }else {
						 $codeTegc = $('#form-field-select-te-bl option:selected').val();
						 console.log("$codeTegc: "+$codeTegc);
												 
						 if($codeTegc===''){		
							 $super_notification2.removeClass('alert alert-danger');
							 $super_notification2.text('');  
							 $super_notification2.addClass('alert alert-danger');
							 $super_notification2.text('Pas de TE valide'); 
							 $('#form-field-select-te-bl option:selected').focus();
						
						  }else{
							  if($modeLivraison ==1){
									$montantAchatHT = $montantAchatHT+$fraisLivraison;
								}

						 $('#load_validate_vente').css({'display':'inline-block'});
						 $('#load_validate_vente_icone').css({'display':'inline-block'});

						 $csrf = $('input[name="_csrf"]').val();

						 reglement = "referenceAdditive="+$referenceAdditive+"&domiciliation="+$domiciliation+"&fraisLivraison="+$fraisLivraison+"&telephoneAcheteur="+$telephoneAcheteur+"&modeLivraison="+$modeLivraison+"&adresseLivraison="+$adresseLivraison+"&codeTegc="+$codeTegc+"&codeTypeCredit="+$codeTypeCredit+"&prk="+$prk+"&codeEnvoi="+$codeEnvoi+"&codeBonConso="+$codeBonConso+"&codeMembreAcheteur="+$codeMembreAcheteur+"&typeR="+$typeR+"&montantAchat="+$montantAchatHT+"&listArticlesVendus="+$jsonlist+"&_csrf=" + $csrf + "";

						 $.ajax({
							 url:"creationBonConsoByBl",
							 type:'POST',
							 data:reglement,
							 success: function(data){
								 $('#load_validate_vente').fadeOut();
								 $('#load_validate_vente_icone').fadeOut();

								 if(data==0){
									 $('#notification_sms').css({'display':'none'});
									 $('#text-codesms').val('');
									 $('#esmc_notification_final').css({'display':'block'});
									 $('#text-reference-vente').val('');
									 $('#form-vente-code-membre').val('');
									 $('#form-vente-bonConso').val('');
									 $('#total-montant-prixht').val('0');
									 $('#table-article-list-retreave-container').html('');

									 $('#form-vente-radio-ordinaire').prop('checked', false);
									 $('#form-vente-radio-speciale').prop('checked', false);
									 $('#form-vente-radionr').prop('checked', false);
									 $('#form-vente-radior').prop('checked', false);
									 $('#form-vente-radioDefaut').prop('checked', false);
									 $('#form-vente-radioBn').prop('checked', false);
									 $('#form-vente-radioBa').prop('checked', false);
									 $('#form-vente-radioBc').prop('checked', false);
									 //$('#form-vente-radioOpi').prop('checked', false);
									 $('#form-vente-approban').prop('checked', false);

									 $('#label-montant-ba').css({'display':'none'});
									 $('#text-montant-ba').css({'display':'none'});

									 $('#label-montant-bc-bc').css({'display':'none'});
									 $('#text-montant-bc-bc').css({'display':'none'});
									 $('#text-montant-bc-bc').val('0');
									 $('#form-field-select-limite').css({'display':'none'});
									 $('#label-produit').css({'display':'none'});
									 $('#form-field-select-bps').css({'display':'none'});
									 $('#label-frequence').css({'display':'none'});
									 $('#form-field-select-frequence').css({'display':'none'});
									 $('#label-montant-bl').css({'display':'none'});
								  	 $('#label-te-bl').css({'display':'none'});
									 $('#text-montant-bl').css({'display':'none'});
									 $('#form-field-select-te-bl').css({'display':'none'});

									 $('#text-reference-additive').val('');
										
									 $('#form-vente-adresseLivraison').val('');
									 $('#text-montant-frais-livraison').val('0');
									 $('#label-adresseLivraison').css({'display':'none'});
									 $('#label-frais-livraison').css({'display':'none'});
									 $('#form-vente-adresseLivraison').css({'display':'none'});
									 $('#text-montant-frais-livraison').css({'display':'none'});
									
									 $('#form-vente-radioInterimAchat').prop('checked', false);
									 $('#label-achatTe').css({'display':'none'});
									 $('#text-code-achat-interim').css({'display':'none'});
									 $('#qrcaptcha').css({'display':'none'});
									 $('#code_membre_qr_auth').val('');
									 $('#form-vente-valide').disabled =true;

									 
									 $perioder='';
									 $typeR='';
									 $codeMembreAcheteur='';
									 $codeBonConso='';
									 $listArticlesVendus = [];
									 console.log($listArticlesVendus);

								 }else if(data==1){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('echec de creation du bon');
								 }else if(data==2){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('le code du bon est invalide');
								 }else if(data==3){
									 $super_notification2.addClass('alert alert-danger');
									 $super_notification2.text('revoir la saisie');
								 }

							 },error: function (xhr, textStatus, errorThrown) {
								 $('#load_validate_vente_vente').fadeOut();
								 $('#load_validate_vente_icone').fadeOut();
								 $listArticlesVendus = [];
								 $super_notification2.addClass('alert alert-danger');
								 $super_notification2.text('La communication avec le serveur a echoué. Veuillez en informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

							 }
						 });   
					 }
				 }
				}else if($typeBon==="BIA"){
					$codeAchat = $('#text-code-achat-interim').val();
					if($codeAchat==""){
						$super_notification2.removeClass('alert alert-danger');
						$super_notification2.text('');  
						$super_notification2.addClass('alert alert-danger');
						$super_notification2.text('Le Code Achat n est pas valide'); 
					
					}
					if (parseInt($('#text-montant-vente-bn').val())===0 && parseInt($('#text-montant-bc-ban').val())!==0){
						//recuperation du type de montant
						$typeMontantBon ="mbc";
						$montantAchatHT = parseInt($('#text-montant-bc-ban').val());
					}else if (parseInt($('#text-montant-vente-bn').val())!==0){
						$typeMontantBon ="mbn";
						$montantAchatHT = parseInt($('#text-montant-vente-bn').val());
					}
					if($montantAchatHT<=0){
						$super_notification2.removeClass('alert alert-danger');
						$super_notification2.text('');  
						$super_notification2.addClass('alert alert-danger');
						$super_notification2.text('Le montant n est pas valide! veuillez reprendre le bon de livraison'); 
					}else{
						if($modeLivraison ==1){
							//$montantAchatHT = $montantAchatHT+$fraisLivraison;
							//$montantAchatHT = $montantAchatHT+$fraisLivraison;
						}
						
						$('#load_validate_vente').css({'display':'inline-block'});
						$('#load_validate_vente_icone').css({'display':'inline-block'});

						$csrf = $('input[name="_csrf"]').val();
						
						reglement =  "codeAchat="+$codeAchat+"&referenceAdditive="+$referenceAdditive+"&domiciliation="+$domiciliation+"&fraisLivraison="+$fraisLivraison+"&telephoneAcheteur="+$telephoneAcheteur+"&modeLivraison="+$modeLivraison+"&adresseLivraison="+$adresseLivraison+"&codeTypeCredit="+$codeTypeCredit+"&perioder="+$perioder+"&bps="+$bps+"&frequence="+$frequence+"&prk="+$prk+"&codeEnvoi="+$codeEnvoi+"&codeBonConso="+$codeBonConso+"&codeMembreAcheteur="+$codeMembreAcheteur+"&typeMontantBon="+$typeMontantBon+"&typeR="+$typeR+"&montantAchat="+$montantAchatHT+"&listArticlesVendus="+$jsonlist+"&_csrf=" + $csrf + "";

						$.ajax({
							url:"creationBonConsoByInterim",
							type:'POST',
							data:reglement,
							success: function(data){

								$('#load_validate_vente').fadeOut();
								$('#load_validate_vente_icone').fadeOut();

								if(data==0){

									$('#notification_sms').css({'display':'none'});
									$('#text-codesms').val('');
									$('#esmc_notification_final').css({'display':'block'});
									$('#text-reference-vente').val('');
									$('#text-montant-vente-bn').val('0');
									$('#text-montant-bc-ban').val('0');
									$('#form-vente-code-membre').val('');
									$('#form-vente-bonConso').val('');
									$('#total-montant-prixht').val('0');

									$('#label-montant-ban').css({'display':'none'});
									$('#text-montant-vente-bn').css({'display':'none'});

									$('#label-montant-bc-ban').css({'display':'none'});
									$('#text-montant-bc-ban').css({'display':'none'});
									$('#table-article-list-retreave-container').html('');

									$('#form-vente-radio-ordinaire').prop('checked', false);
									$('#form-vente-radio-speciale').prop('checked', false);
									$('#form-vente-radionr').prop('checked', false);
									$('#form-vente-radior').prop('checked', false);
									$('#form-vente-radioBn').prop('checked', false);
									$('#form-vente-radioDefaut').prop('checked', false);
									$('#form-vente-radioBa').prop('checked', false);
									//$('#form-vente-radioOpi').prop('checked', false);
									$('#form-vente-approban').prop('checked', false);
									$('#form-field-select-limite').css({'display':'none'});
									$('#label-produit').css({'display':'none'});
									$('#form-field-select-bps').css({'display':'none'});
									$('#label-frequence').css({'display':'none'});
									$('#form-field-select-frequence').css({'display':'none'});
									$('#text-reference-additive').val('');
									
									$('#form-vente-adresseLivraison').val('');
									$('#text-montant-frais-livraison').val('0');
									$('#label-adresseLivraison').css({'display':'none'});
									$('#label-frais-livraison').css({'display':'none'});
									$('#form-vente-adresseLivraison').css({'display':'none'});
									$('#text-montant-frais-livraison').css({'display':'none'});
								
									$('#form-vente-radioInterimAchat').prop('checked', false);
									$('#label-achatTe').css({'display':'none'});
									$('#text-code-achat-interim').css({'display':'none'});
									$('#qrcaptcha').css({'display':'none'});
									$('#code_membre_qr_auth').val('');
									$('#form-vente-valide').disabled =true;

									 
									$modeLivraison =2;
									
									$perioder='';
									$typeR='';
									$codeMembreAcheteur='';
									$codeBonConso='';
									$listArticlesVendus = [];
									console.log($listArticlesVendus);

								}else if(data==1){
									$super_notification2.addClass('alert alert-danger');
									$super_notification2.text('Echec de souscription au Bon de Consommation');
								}else if(data==2){
									$super_notification2.addClass('alert alert-danger');
									$super_notification2.text('Echec de souscription au bon d achat');
								}else if(data==3){
									$super_notification2.addClass('alert alert-danger');
									$super_notification2.text('Echec de souscription au bon d achat');
								}else if(data==4){
									$super_notification2.addClass('alert alert-danger');
									$super_notification2.text('Le code du bon de consommation est invalide');
								}else if(data==5){
									$super_notification2.addClass('alert alert-danger');
									$super_notification2.text('Revoir la saise des données');
								}

							},error: function (xhr, textStatus, errorThrown) {
								$('#load_validate_vente').fadeOut();
								$('#load_validate_vente_icone').fadeOut();
								$listArticlesVendus = [];
								console.log('$listArticlesVendus= '+$listArticlesVendus);
								$super_notification2.addClass('alert alert-danger');
								$super_notification2.text('La communication avec le serveur a echoué. Veuillez en informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

							}
						});   


					}
     		}
		}
	});






	$('body').on('click', '#esmc_notification_final_close', function (e) {
		e.preventDefault();
		$('#esmc_notification_final').css({'display':'none'});
	});

	$('body').on('click', '#form-vente-annuler', function (e) {
		e.preventDefault();
		
		
		$super_notification2.removeClass('alert alert-danger');
		$super_notification2.text('');
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');
		$('#text-reference-vente').val('');
		$('#text-montant-vente-bn').val('0');
		$('#text-montant-bc-ban').val('0');
		$('#form-vente-code-membre').val('');
		$('#form-vente-bonConso').val('');
		$('#total-montant-prixht').val('0');

		$('#label-montant-ba').css({'display':'none'});
		$('#text-montant-ba').css({'display':'none'});

		$('#label-montant-bc-ba').css({'display':'none'});
		$('#text-montant-bc-ba').css({'display':'none'});

		$('#label-montant-ban').css({'display':'none'});
		$('#text-montant-vente-bn').css({'display':'none'});

		$('#label-montant-bc-ban').css({'display':'none'});
		$('#text-montant-bc-ban').css({'display':'none'});

		$('#text-montant-cumul').css({'display':'none'});
		$('#label-cumul').css({'display':'none'});
		$('#label-montant-bc-cumul').css({'display':'none'});
		$('#text-montant-bc-cumul').css({'display':'none'});

		//	$('#label-montant-opi').css({'display':'none'});
		//	$('#text-montant-vente-opi').css({'display':'none'});
		//	$('#label-montant-bc-opi').css({'display':'none'});

		$('#label-montant-bc-bc').css({'display':'none'});
		$('#text-montant-bc-bc').css({'display':'none'});

		$('#label-montant-approban').css({'display':'none'});
		$('#text-montant-vente-approbn').css({'display':'none'});

		$('#label-montant-bc-approban').css({'display':'none'});
		$('#text-montant-bc-approban').css({'display':'none'});
		
		$('#label-montant-bl').css({'display':'none'});
		$('#label-te-bl').css({'display':'none'});
		$('#text-montant-bl').css({'display':'none'});
		$('#form-field-select-te-bl').css({'display':'none'});
		$('#label-adresseLivraison').css({'display':'none'});
		$('#form-vente-adresseLivraison').css({'display':'none'});
		$('#label-frais-livraison').css({'display':'none'});
		$('#text-montant-frais-livraison').css({'display':'none'});
		
		$('#form_vente_nom_telephone').css({'display':'none'});
				
		$('#table-article-list-retreave-container').html('');

		$('#form-vente-radio-ordinaire').prop('checked', false);
		$('#form-vente-radio-speciale').prop('checked', false);
		$('#form-vente-radionr').prop('checked', false);
		$('#form-vente-radior').prop('checked', false);
		$('#form-vente-radioDefaut').prop('checked', false);
		$('#form-vente-radioBn').prop('checked', false);
		$('#form-vente-radioBa').prop('checked', false);
		$('#form-vente-radioBc').prop('checked', false);
		$('#form-vente-domicile').prop('checked', false);
		$('#form-vente-surplace').prop('checked', false);
		$('#form-vente-approban').prop('checked', false);
		$('#form-produit-domicilie').prop('checked', false);
		$('#form-vente-radioInterimAchat').prop('checked', false);
		$('#label-achatTe').css({'display':'none'});
		$('#text-code-achat-interim').css({'display':'none'});
		
		$perioder='';
		$codeBonConso='';
		$typeR='';
		$codeMembreAcheteur='';
		$codeBonConso='';
		$listArticlesVendus = [];
		$domiciliation = 0;
		$modeLivraison = 2;
	});

	$('body').on('click', '#notification_sms_final_close', function (e) {
		e.preventDefault();
		$('#notification_sms').css({'display':'none'});
	});

})(jQuery);
