(function ($) {
	var $super_notification = $('#esmc_notification');
	var $notification_final = $('#notification_final');
	var $codeMembreAcheteur;
	var $codeBonconso;
	var $referenceFacture;
	var $periodeFacture;
	var $prk= 0.0;
	var $typeMontantBon;
	var $montantAchatHT;
	var $typeR;
	var $typeBon;
	var $perioder ="";
	var $typeProduit;
	var $codeTypeCredit;
	

	
	$(document).ready(
			function() {
				$.ajax({
					url:"facturesordinaires",
					type:'GET',
					datatype:'json',
					contentType:'application/json',
					success: function(data){
						console.log(data);
						if(data===0){
					/*		$('#label-radioor-facture').css({'display':'inline-block'});
							$('#form-facture-radio-ordinaire').css({'display':'inline-block'});
							$('#label-radios-facture').css({'display':'inline-block'});
							$('#form-facture-radio-speciale').css({'display':'inline-block'});
							*/
							$('#label-radior-facture').css({'display':'inline-block'});
							$('#form-facture-radior').css({'display':'inline-block'});
						}else if(data ===10){
							/*$('#label-radioor-facture').css({'display':'inline-block'});
							$('#form-facture-radio-ordinaire').css({'display':'inline-block'});
							$('#label-radios-facture').css({'display':'inline-block'});
							$('#form-facture-radio-speciale').css({'display':'inline-block'});*/
							
							$('#label-radior-facture').css({'display':'none'});
							$('#form-facture-radior').css({'display':'none'});
						}else if(data ===1){

						/*	$('#label-radioor-facture').css({'display':'inline-block'});
							$('#form-facture-radio-ordinaire').css({'display':'inline-block'});
							$('#label-radios-facture').css({'display':'none'});
							$('#form-facture-radio-speciale').css({'display':'none'});*/
							
							$('#label-radior-facture').css({'display':'inline-block'});
							$('#form-facture-radior').css({'display':'inline-block'});
						}else if(data ===11){
					/*		$('#label-radioor-facture').css({'display':'inline-block'});
							$('#form-facture-radio-ordinaire').css({'display':'inline-block'});
							$('#label-radios-facture').css({'display':'none'});
							$('#form-facture-radio-speciale').css({'display':'none'});*/
							
							$('#label-radior-facture').css({'display':'none'});
							$('#form-facture-radior').css({'display':'none'});
						}else if(data ===2){
					/*		$('#label-radioor-facture').css({'display':'none'});
							$('#form-facture-radio-ordinaire').css({'display':'none'});
							$('#label-radios-facture').css({'display':'inline-block'});
							$('#form-facture-radio-speciale').css({'display':'inline-block'});*/
							
							$('#label-radior-facture').css({'display':'inline-block'});
							$('#form-facture-radior').css({'display':'inline-block'});
						}else if(data===12){
						/*	$('#label-radioor-facture').css({'display':'none'});
							$('#form-facture-radio-ordinaire').css({'display':'none'});
							$('#label-radios-facture').css({'display':'inline-block'});
							$('#form-facture-radio-speciale').css({'display':'inline-block'});*/

							$('#label-radior-facture').css({'display':'none'});
							$('#form-facture-radior').css({'display':'none'});

						}else if(data===3){
						/*	$('#label-radioor-facture').css({'display':'none'});
							$('#form-facture-radio-ordinaire').css({'display':'none'});

							$('#label-radios-facture').css({'display':'none'});
							$('#form-facture-radio-speciale').css({'display':'none'});*/
						}

					}
				});
			});
	
	
	
	if(!$('#form-facture-select-codetypecredit').val()){
		$('#notif_facture').css({'display':'inline-block'});
		$('#notif_facture').addClass('alert alert-danger');
		$('#notif_facture').text('VEUILLEZ FAIRE PARAMETRER VOTRE TE AVANT TOUTE OPERATION PAR UN TICKET DE SUPPORT');
	
	}
	
	//afficher nom et telephone

	$('body').on('blur', '#text-codemembre-facture', function(e){
		$codeMembreAcheteur = $('#text-codemembre-facture').val();
		console.log('$codeMembreAcheteur= '+$codeMembreAcheteur);
		if(!$codeMembreAcheteur){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Attention le codemembre ne doit pas être vide');  
			$('#text-codemembre-facture').focus();

		}else if($('#text-codemembre-facture').val().length<20){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Veuillez vérifier le code membre saisi');  
			$('#text-codemembre-facture').focus();
		}else{
			$.ajax({
				url:"nomAcheteurBoutikFacture/"+$codeMembreAcheteur,
				type:'GET',
				datatype:'json',
				contentType:'application/json',
				success: function(data){
					$('#form-facture-nom-membre').val('');
					$('#form-facture-nom-membre').val(''+data+'');
				}
			});
			$.ajax({
				url:"listeTelephoneBoutikFacture/"+$codeMembreAcheteur,
				type:'GET',
				datatype:'json',
				contentType:'application/json',
				success: function(data){
					$('#form-facture-select-telephone').html('');
					var options = $("#form-facture-select-telephone");
					for (var i in data) {
						options.append(new Option(data[i].numeroTelephone)); 
					}
				}
			});

			$('#form_facture_nom_telephone').css({'display':'inline-block'});	
		}
	
	})
	
	//select codetypecredit

	$('body').on('click', '#form-facture-select-codetypecredit',function(e){
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  

		$codeTypeCredit = $('#form-facture-select-codetypecredit option:selected').val();

		$.ajax({
			url:"findprkf/"+$codeTypeCredit,
			type:'GET',
			datatype:'json',
			contentType:'application/json',
			success: function(data){
				console.log(data);
				$('#form-field-select-prk-facture').html('');
				var options = $("#form-field-select-prk-facture");
				options.append(new Option(data));

			}
		});
	});
	
	

	/*$('body').on('blur', '#text-codemembre-facture', function(e){
		
		e.preventDefault();
	
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  	
		$codeMembreAcheteur = $('#text-codemembre-facture').val();
		console.log('$codeMembreAcheteur= '+$codeMembreAcheteur);
		if($codeMembreAcheteur===''){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Attention le codemembre ne doit pas être vide');  
			$('#text-codemembre-facture').focus();
		}else if($codeMembreAcheteur.length<20){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Veuillez vérifier le code membre saisi');  
			$('#text-codemembre-facture').focus();
		}else {
			
			
			$.ajax({
				url:"listtegcAcheteur/"+$codeMembreAcheteur,
				type:'GET',
				datatype:'json',
				contentType:'application/json',
				success: function(data){
					
					if(data.length ===0){
						$super_notification.removeClass('alert alert-danger');
						$super_notification.text('');  
						$super_notification.addClass('alert alert-danger');
						$super_notification.text('Cet acheteur n a pas de TE. Veuillez lui en creer un avant toute opération!!!');  
						
						$('#text-codemembre-facture').focus();
					}
				}
			});
		}
		
	});*/
	
	
	//radio nr
	$('body').on('click', '#form-facture-radionr',function(e){
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  


		$('#form-field-select-limite-facture').css({'display':'none'});
		$('#form-field-select-prk-facture').css({'display':'inline-block'});
		$('#label-prk-facture').css({'display':'inline-block'});

		$typeR ="nr";


	});


	//radio r

	$('body').on('click', '#form-facture-radior',function(e){
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  

		$('#form-field-select-limite-facture').css({'display':'inline-block'});

		$('#form-field-select-prk-facture').css({'display':'none'});
		$('#label-prk-facture').css({'display':'none'});

		$perioder = $('#form-field-select-limite-facture option:selected').val();
		console.log('$perioder= '+$perioder);

	});
	//radio CUMUL

	$('body').on('click', '#form-facture-radioDefaut',function(e){
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		
		$('#label-cumul-facture').css({'display':'inline-block'});
		$('#text-montant-cumul-facture').css({'display':'inline-block'});
		$('#label-montant-bc-cumul-facture').css({'display':'inline-block'});
		$('#text-montant-bc-cumul-facture').css({'display':'inline-block'});
		
		
		/*$('#label-montant-opi-facture').css({'display':'none'});
		$('#text-montant-facture-opi').css({'display':'none'});
		$('#label-montant-bc-opi-facture').css({'display':'none'});
		$('#text-montant-bc-opi-facture').css({'display':'none'});
	*/	
		$('#label-montant-ban-facture').css({'display':'none'});
		$('#text-montant-ban-facture').css({'display':'none'});
		$('#label-montant-bc-ban-facture').css({'display':'none'});
		$('#text-montant-bc-ban-facture').css({'display':'none'});
		
		$('#label-montant-ba-facture').css({'display':'none'});
		$('#text-montant-ba-facture').css({'display':'none'});
		$('#label-montant-bc-ba-facture').css({'display':'none'});
		$('#text-montant-bc-ba-facture').css({'display':'none'});
		
		$('#label-montant-bc-bc-facture').css({'display':'none'});
		$('#text-montant-bc-bc-facture').css({'display':'none'});
		
		
		$typeMontantBon = "mdf";
		console.log("$typeMontantBon= "+$typeMontantBon);
		$typeBon = "DF";

		$('#form-facture-radioBc').prop('checked', false);
		$('#form-facture-radioBn').prop('checked', false);
		$('#form-facture-radioBa').prop('checked', false);
	//	$('#form-facture-radioOpi').prop('checked', false);
		
		
		$('#text-montant-cumul-facture').val(''+0+'');
	//	$('#text-montant-facture-opi').val(''+0+'');
		$('#text-montant-ban-facture').val(''+0+'');
		$('#text-montant-ba-facture').val(''+0+'');

		$('#text-montant-bc-cumul-facture').val(''+0+'');
		//$('#text-montant-bc-opi-facture').val(''+0+'');
		$('#text-montant-bc-ban-facture').val(''+0+'');
		$('#text-montant-bc-ba-facture').val(''+0+'');
		$('#text-montant-bc-bc-facture').val(''+0+'');
		
	});

	
	
	//radio opi
/*	$('body').on('click', '#form-facture-radioOpi',function(e){
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		$total = Math.floor(parseInt($('#total-montant-prixht').val()));
		$('#text-montant-bc-opi').val(''+$total+'');
		
		$('#label-cumul-facture').css({'display':'none'});
		$('#text-montant-cumul-facture').css({'display':'none'});
		$('#label-montant-bc-cumul-facture').css({'display':'none'});
		$('#text-montant-bc-cumul-facture').css({'display':'none'});
		
		
		$('#label-montant-opi-facture').css({'display':'inline-block'});
		$('#text-montant-facture-opi').css({'display':'inline-block'});
		$('#label-montant-bc-opi-facture').css({'display':'inline-block'});
		$('#text-montant-bc-opi-facture').css({'display':'inline-block'});
		
		$('#label-montant-ban-facture').css({'display':'none'});
		$('#text-montant-ban-facture').css({'display':'none'});
		$('#label-montant-bc-ban-facture').css({'display':'none'});
		$('#text-montant-bc-ban-facture').css({'display':'none'});
		
		$('#label-montant-ba-facture').css({'display':'none'});
		$('#text-montant-ba-facture').css({'display':'none'});
		$('#label-montant-bc-ba-facture').css({'display':'none'});
		$('#text-montant-bc-ba-facture').css({'display':'none'});
		
		$('#label-montant-bc-bc-facture').css({'display':'none'});
		$('#text-montant-bc-bc-facture').css({'display':'none'});
		
		
		$typeMontantBon = "opi";
		console.log("$typeMontantBon= "+$typeMontantBon);
		$typeBon = "OPI";

		$('#form-facture-radioBc').prop('checked', false);
		$('#form-facture-radioBn').prop('checked', false);
		$('#form-facture-radioBa').prop('checked', false);
		$('#form-facture-radioDefaut').prop('checked', false);
	
		
		$('#text-montant-cumul-facture').val(''+0+'');
		$('#text-montant-facture-opi').val(''+0+'');
		$('#text-montant-ban-facture').val(''+0+'');
		$('#text-montant-ba-facture').val(''+0+'');

		$('#text-montant-bc-cumul-facture').val(''+0+'');
		$('#text-montant-bc-opi-facture').val(''+0+'');
		$('#text-montant-bc-ban-facture').val(''+0+'');
		$('#text-montant-bc-ba-facture').val(''+0+'');
		$('#text-montant-bc-bc-facture').val(''+0+'');
		
	});*/
	//radio BAN

	$('body').on('click', '#form-facture-radioBn',function(e){
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  

		$('#label-cumul-facture').css({'display':'none'});
		$('#text-montant-cumul-facture').css({'display':'none'});
		$('#label-montant-bc-cumul-facture').css({'display':'none'});
		$('#text-montant-bc-cumul-facture').css({'display':'none'});
		
		
		/*$('#label-montant-opi-facture').css({'display':'none'});
		$('#text-montant-facture-opi').css({'display':'none'});
		$('#label-montant-bc-opi-facture').css({'display':'none'});
		$('#text-montant-bc-opi-facture').css({'display':'none'});
		
		*/
		$('#label-montant-ban-facture').css({'display':'inline-block'});
		$('#text-montant-ban-facture').css({'display':'inline-block'});
		$('#label-montant-bc-ban-facture').css({'display':'inline-block'});
		$('#text-montant-bc-ban-facture').css({'display':'inline-block'});
		
		$('#label-montant-ba-facture').css({'display':'none'});
		$('#text-montant-ba-facture').css({'display':'none'});
		$('#label-montant-bc-ba-facture').css({'display':'none'});
		$('#text-montant-bc-ba-facture').css({'display':'none'});
		
		$('#label-montant-bc-bc-facture').css({'display':'none'});
		$('#text-montant-bc-bc-facture').css({'display':'none'});
		
		
		$typeBon = "BN";

		$('#form-facture-radioBc').prop('checked', false);
		$('#form-facture-radioBa').prop('checked', false);
	//	$('#form-facture-radioOpi').prop('checked', false);
		$('#form-facture-radioDefaut').prop('checked', false);
		
		$('#text-montant-cumul-facture').val(''+0+'');
	//	$('#text-montant-facture-opi').val(''+0+'');
		$('#text-montant-ban-facture').val(''+0+'');
		$('#text-montant-ba-facture').val(''+0+'');

		$('#text-montant-bc-cumul-facture').val(''+0+'');
	//	$('#text-montant-bc-opi-facture').val(''+0+'');
		$('#text-montant-bc-ban-facture').val(''+0+'');
		$('#text-montant-bc-ba-facture').val(''+0+'');
		$('#text-montant-bc-bc-facture').val(''+0+'');

	});
	
	
	//radio BA
	$('body').on('click', '#form-facture-radioBa',function(e){

		$('#form-facture-radioDefaut').prop('checked', false);
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
		
		$('#text-montant-cumul-facture').val(''+0+'');
		//$('#text-montant-facture-opi').val(''+0+'');
		$('#text-montant-ban-facture').val(''+0+'');
		$('#text-montant-ba-facture').val(''+0+'');

		$('#text-montant-bc-cumul-facture').val(''+0+'');
		//$('#text-montant-bc-opi-facture').val(''+0+'');
		$('#text-montant-bc-ban-facture').val(''+0+'');
		$('#text-montant-bc-ba-facture').val(''+0+'');
		$('#text-montant-bc-bc-facture').val(''+0+'');

		$('#label-cumul-facture').css({'display':'none'});
		$('#text-montant-cumul-facture').css({'display':'none'});
		$('#label-montant-bc-cumul-facture').css({'display':'none'});
		$('#text-montant-bc-cumul-facture').css({'display':'none'});
		
	/*	$('#label-montant-opi-facture').css({'display':'none'});
		$('#text-montant-facture-opi').css({'display':'none'});
		$('#label-montant-bc-opi-facture').css({'display':'none'});
		$('#text-montant-bc-opi-facture').css({'display':'none'});
*/		
		$('#label-montant-ban-facture').css({'display':'none'});
		$('#text-montant-ban-facture').css({'display':'none'});
		$('#label-montant-bc-ban-facture').css({'display':'none'});
		$('#text-montant-bc-ban-facture').css({'display':'none'});
		
		
		$('#label-montant-ba-facture').css({'display':'inline-block'});
		$('#text-montant-ba-facture').css({'display':'inline-block'});
		$('#label-montant-bc-ba-facture').css({'display':'inline-block'});
		$('#text-montant-bc-ba-facture').css({'display':'inline-block'});
		
		$('#label-montant-bc-bc-facture').css({'display':'none'});
		$('#text-montant-bc-bc-facture').css({'display':'none'});
		
	

		$('#form-facture-radioBc').prop('checked', false);
		$('#form-facture-radioBn').prop('checked', false);
		//$('#form-facture-radioOpi').prop('checked', false);
		$('#form-facture-radioDefaut').prop('checked', false);
		
		
		$typeBon ="BA";
		$typeMontantBon= "mba";
		console.log("$typeBon= "+$typeBon);
		console.log("$typeMontantBon= "+$typeMontantBon);

	});

	//radio Bc
	$('body').on('click', '#form-facture-radioBc',function(e){

		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');  
	
	
		$('#label-cumul-facture').css({'display':'none'});
		$('#text-montant-cumul-facture').css({'display':'none'});
		$('#label-montant-bc-cumul-facture').css({'display':'none'});
		$('#text-montant-bc-cumul-facture').css({'display':'none'});
		
		
		$('#label-montant-opi-facture').css({'display':'none'});
		$('#text-montant-facture-opi').css({'display':'none'});
		$('#label-montant-bc-opi-facture').css({'display':'none'});
		$('#text-montant-bc-opi-facture').css({'display':'none'});
		
		$('#label-montant-ban-facture').css({'display':'none'});
		$('#text-montant-ban-facture').css({'display':'none'});
		$('#label-montant-bc-ban-facture').css({'display':'none'});
		$('#text-montant-bc-ban-facture').css({'display':'none'});
		
		$('#label-montant-ba-facture').css({'display':'none'});
		$('#text-montant-ba-facture').css({'display':'none'});
		$('#label-montant-bc-ba-facture').css({'display':'none'});
		$('#text-montant-bc-ba-facture').css({'display':'none'});
		
		$('#label-montant-bc-bc-facture').css({'display':'inline-block'});
		$('#text-montant-bc-bc-facture').css({'display':'inline-block'});
		
		
		$typeMontantBon = "mbonc";
		console.log("$typeMontantBon= "+$typeMontantBon);
		$typeBon = "BC";

		$('#form-facture-radioBn').prop('checked', false);
		$('#form-facture-radioBa').prop('checked', false);
		$('#form-facture-radioOpi').prop('checked', false);
		$('#form-facture-radioDefaut').prop('checked', false);
		
		$('#text-montant-cumul-facture').val(''+0+'');
		$('#text-montant-facture-opi').val(''+0+'');
		$('#text-montant-ban-facture').val(''+0+'');
		$('#text-montant-ba-facture').val(''+0+'');

		$('#text-montant-bc-cumul-facture').val(''+0+'');
		$('#text-montant-bc-opi-facture').val(''+0+'');
		$('#text-montant-bc-ban-facture').val(''+0+'');
		$('#text-montant-bc-ba-facture').val(''+0+'');
		$('#text-montant-bc-bc-facture').val(''+0+'');
		});

	
	
	
	/*
	//radio speciale
	$('body').on('click', '#form-facture-radio-speciale',function(e){
		$typeProduit = "PS";
		console.log("$typeProduit: "+$typeProduit);	

	});
	
	//radio ordinaire
	$('body').on('click', '#form-facture-radio-ordinaire',function(e){
		$typeProduit = "PO";
		console.log("$typeProduit: "+$typeProduit);

	});*/
	// boite de dialogue pour le formulaire facture simple

	$('body').on('click','#form-facture-btOK',function(e){
		e.preventDefault();

		$super_notification.removeClass('alert alert-danger');
		$super_notification.text(''); 
		$typeR = "";
		$typeBon ="";

		//recuperation du code type Credit
		$codeTypeCredit = $('#form-facture-select-codetypecredit option:selected').val();
		console.log("$codeTypeCredit : "+$codeTypeCredit );

		
		
		//recupération du type recurrent

		if($('#form-facture-radior').is(':checked') === true){
			$typeR = "r";
			console.log("type r: "+$typeR);
			$perioder = $('#form-field-select-limite-facture option:selected').val();
		}else if($('#form-facture-radionr').is(':checked') === true){
			$typeR = "nr";
			console.log("type r: "+$typeR);
			$prk = $('#form-field-select-prk-facture option:selected').val();
		}

		/*//recuperation typeProduit
		if($('#form-facture-radio-ordinaire').is(':checked') === true){
			//recuperation du type marchandise
			$typeProduit = "PO";
			console.log("$typeProduit: "+$typeProduit);
		}
		if($('#form-facture-radio-speciale').is(':checked') === true){
			//recuperation du type marchandise
			$typeProduit = "PS";
			console.log("$typeProduit: "+$typeProduit);
		}
*/

		
		
		if($('#form-facture-radioDefaut').is(':checked') === true){
			//recuperation du type bon
			$typeBon = "DF";
			console.log("$typeBon: "+$typeBon);
			//recuperation du type de montant
			$typeMontantBon ="mdf";
			console.log('$typeMontantBon= '+$typeMontantBon)
			
		}
		
		/*if($('#form-facture-radioOpi').is(':checked') === true){
			//recuperation du type bon
			$typeBon = "OPI";
			console.log("$typeBon: "+$typeBon);
			//recuperation du type de montant
			$typeMontantBon ="opi";
			console.log('$typeMontantBon= '+$typeMontantBon)
		}*/
		
		if($('#form-facture-radioBn').is(':checked') === true){
			//recuperation du type bon
			$typeBon = "BN";
			console.log("$typeBon: "+$typeBon);
			//recuperation du type de montant
			if (parseInt($('#text-montant-ban-facture').val())===0){
				$typeMontantBon ="mbc";
				console.log('$typeMontantBon= '+$typeMontantBon)
			}else{
				$typeMontantBon ="mbn";
				console.log('$typeMontantBon= '+$typeMontantBon)
			}
		}
		if($('#form-facture-radioBa').is(':checked') === true){
			//recuperation du type bon
			$typeBon = "BA";
			console.log("$typeBon: "+$typeBon);
			//recuperation du type de montant
			$typeMontantBon ="mba";
			console.log('$typeMontantBon= '+$typeMontantBon)
		}
		if($('#form-facture-radioBc').is(':checked') === true){
			//recuperation du type bon
			$typeBon = "BC";
			console.log("$typeBon: "+$typeBon);
			//recuperation du type de montant
			$typeMontantBon ="mbonc";
			console.log('$typeMontantBon= '+$typeMontantBon)
		}

		//recuperation du typeR
		if($typeR===''){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Veuillez selectionner un type recurrent ou non recurrent'); 
			$('#form-facture-radior').focus();

		}else if($typeBon===''){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Veuillez selectionner le type de bon à utiliser'); 
			$('#form-facture-radioDefaut').focus();

		}else if($typeMontantBon===''){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');  
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Veuillez selectionner le type de bon à utiliser'); 
			$('#form-facture-radioDefaut').focus();
		}else if($codeTypeCredit==""){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Veuillez selectionner le type de produit');
			$('#form-facture-select-codetypecredit').focus();
		}else{			

			//recuperation du code membre

			$codeMembreAcheteur = $('#text-codemembre-facture').val();
			console.log('$codeMembreAcheteur= '+$codeMembreAcheteur);
			if($codeMembreAcheteur===''){
				$super_notification.removeClass('alert alert-danger');
				$super_notification.text('');  
				$super_notification.addClass('alert alert-danger');
				$super_notification.text('Attention le codemembre ne doit pas être vide');  
				$('#text-codemembre-facture').focus();

			}else if($('#text-codemembre-facture').val().length<20){
				$super_notification.removeClass('alert alert-danger');
				$super_notification.text('');  
				$super_notification.addClass('alert alert-danger');
				$super_notification.text('Veuillez vérifier le code membre saisi');  
				$('#text-codemembre-facture').focus();
			}else{
				//recuperation du montant 
				if($typeMontantBon ==="mdf"){
					$montantAchatHT = parseInt($('#text-montant-bc-cumul-facture').val());
					console.log("$montantAchatHT mdf: "+$montantAchatHT);
					
				}else if($typeMontantBon ==="mbc"){
					$montantAchatHT = parseInt($('#text-montant-bc-ban-facture').val());
					console.log("$montantAchatHT mbc: "+$montantAchatHT);
				}else if($typeMontantBon ==="mbn"){
					$montantAchatHT = parseInt($('#text-montant-ban-facture').val());
					console.log("$montantAchatHT mbn: "+$montantAchatHT);
				}else if($typeMontantBon ==="mba"){
					$montantAchatHT = parseInt($('#text-montant-bc-ba-facture').val());
					console.log("$montantAchatHT mbc: "+$montantAchatHT);
					
				}else if($typeMontantBon ==="mbonc"){
					$montantAchatHT = parseInt($('#text-montant-bc-bc-facture').val());
					console.log("$montantAchatHT mbonc: "+$montantAchatHT);
				}

				if($montantAchatHT<= 0){
					$super_notification.addClass('alert alert-danger');
					$super_notification.text('Attention le montant n est pas valide');  
					
				}else{
					
					$csrf = $('input[name="_csrf"]').val();

					rechercheMontant = "perioder="+$perioder+"&codeTypeCredit="+$codeTypeCredit+"&prk="+$prk+"&typeMontantBon="+$typeMontantBon+"&montantAchat="+$montantAchatHT+"&typeR="+$typeR+"&codeMembreAcheteur="+$codeMembreAcheteur+"&_csrf=" + $csrf + "";

					$('#load_createbon_facture').css({'display':'inline-block'});
					$('#load_createbon_icone').css({'display':'inline-block'});
					
					$.ajax({
						url:"findmontantandsendsmsinterim",
						type:'POST',
						data: rechercheMontant,
						success: function(data){
							console.log(data);
							$('#load_createbon_facture').fadeOut();
							$('#load_createbon_icone').fadeOut();
							
							if(data[0]=="KO0"){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('Le quota est depassé');    
							}else if(data[0]=="KO1"){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('Le montant du bon neutre est insuffisant');    
							}else if(data[0]=="KO2"){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('il n y a pas de bon neutre correspondant à ce membre');  
							}else if(data[0]=="KO3"){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('les données de cette maison ne sont pas valides');
							}else if(data[0]=="KO4"){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('le montant des opi disponible est insuffisant'); 
							}else if(data[0]=="KO5"){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('Il n y a pas d OPI disponibles'); 
							}else if(data[0]=="KO6"){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('le montant du bon d achat est insuffisant'); 
							}else if(data[0]=="KO7"){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('il n ya pas de bon d achat disponible'); 
							}else if(data[0]=="KO8"){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('le montant du bon de consommation est insuffisant'); 
							}else if(data[0]=="KO9"){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('Il n y a pas de bons disponibles'); 
							}else if(data[0]=="KO10"){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('Les donnees de la maison ne sont pas valides'); 
							}else if(data[0]=="KO11"){
								$super_notification.addClass('alert alert-danger');
								$super_notification.text('revoir la saisie des données'); 
							}else{
								$('#text-codesms_facture').val('ESMC '+data[0]+'');
								$('#notification_sms_facture').css({'display':'inline-block'});
								$codeEnvoi = data[0];
								
								
								if($typeMontantBon ==="mdf"){
									$('#text-montant-cumul-facture').val(''+data[1]+'');
															
								}else if($typeMontantBon ==="opi"){
									$('#text-montant-facture-opi').val(''+data[1]+'');
									
								}else if($typeMontantBon ==="mbc"){
									$('#text-montant-ban-facture').val(''+data[1]+'');
							
								}else if($typeMontantBon ==="mba"){
									$('#text-montant-ba-facture').val(''+data[1]+'');
									
								}
								
							}
						},
						error: function (xhr, textStatus, errorThrown) {
							$('#load_createbon_facture').fadeOut();
							$('#load_createbon_icone').fadeOut();
							$super_notification.addClass('alert alert-danger');
							$super_notification.text('La communication avec le serveur a echoué. Veuillez informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

						}
				}); 
				}
			}
		}
	});


/*//	bouton CONFIRMER DU MODAL

	$('body').on('click', '#form-facture-confirmer', function (e) {
		e.preventDefault();
		$('#viewMontantBn-modal-facture').hide();
		$('.modal-backdrop').hide();
	});
*/
//	bouton FINAL VALIDER 

	$('body').on('click', '#form-facture-valider', function (e) {
		e.preventDefault();
		$super_notification.removeClass('alert alert-danger');
		$super_notification.text('');
		
		//recupération du type recurrent

		if($('#form-facture-radior').is(':checked') === true){
			$typeR = "r";
			console.log("type r: "+$typeR);
			$perioder = $('#form-field-select-limite-facture option:selected').val();
		}

		if($('#form-facture-radionr').is(':checked') === true){
			$typeR = "nr";
			console.log("type r: "+$typeR);
			$prk = $('#form-field-select-prk-facture option:selected').val();
		}

		/*//recuperation typeProduit
		if($('#form-facture-radio-ordinaire').is(':checked') === true){
			//recuperation du type marchandise
			$typeProduit = "PO";
			console.log("$typeProduit: "+$typeProduit);
		}
		if($('#form-facture-radio-speciale').is(':checked') === true){
			//recuperation du type marchandise
			$typeProduit = "PS";
			console.log("$typeProduit: "+$typeProduit);
		}
		*/
		//récuperation du codemembre

		$codeMembreAcheteur = $('#text-codemembre-facture').val();
		console.log("$codeMembreAcheteur: "+$codeMembreAcheteur);
		if($codeMembreAcheteur===""){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Attention le codemembre ne doit pas être vide');
			$('#text-codemembre-facture').focus();

		}
		
		//recuperation  de la reference facture
		$referenceFacture = $('#text-reference-facture').val();
		console.log("$referenceFacture: "+$referenceFacture);
		if($referenceFacture===""){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Attention le référence ne doit pas être vide');
			$('#text-reference-facture').focus();

		}
		
		$periodeFacture= $('#text-periode-facture').val();
		console.log("$periodeFacture: "+$periodeFacture);
		/*if($periodeFacture===""){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Attention la periode ne doit pas être vide');
			$('#text-periode-facture').focus();

		}*/
		//recuperation du montant 
		if($typeMontantBon ==="mdf"){
			$montantAchatHT = parseInt($('#text-montant-bc-cumul-facture').val());
			console.log("$montantAchatHT mdf: "+$montantAchatHT);
			
		}else if($typeMontantBon ==="opi"){
			$montantAchatHT = parseInt($('#text-montant-bc-opi-facture').val());
			console.log("$montantAchatHT opi: "+$montantAchatHT);
			
		}else if($typeMontantBon ==="mbc"){
			$montantAchatHT = parseInt($('#text-montant-bc-ban-facture').val());
			console.log("$montantAchatHT mbc: "+$montantAchatHT);
		}else if($typeMontantBon ==="mbn"){
			$montantAchatHT = parseInt($('#text-montant-ban-facture').val());
			console.log("$montantAchatHT mbn: "+$montantAchatHT);
		}else if($typeMontantBon ==="mba"){
			$montantAchatHT = parseInt($('#text-montant-bc-ba-facture').val());
			console.log("$montantAchatHT mbc: "+$montantAchatHT);
			
		}else if($typeMontantBon ==="mbonc"){
			$montantAchatHT = parseInt($('#text-montant-bc-bc-facture').val());
			console.log("$montantAchatHT mbonc: "+$montantAchatHT);
		}

		
		//recuperation du code du bon de consommation

		$codeBonConso = $('#form-facture-bonConso').val();
		console.log('$codeBonConso = '+$codeBonConso);
		if($codeBonConso===""){
			$('#load_validate_facture').fadeOut();

			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Attention le code du bon de consommation ne doit pas être vide');
			$('#form-facture-bonConso').focus();

		}else if($typeBon===""){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Veuillez choisir le type de bon a utiliser ');
			$('#form-facture-radioDefaut').focus();

		}else if($typeR===""){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Veuillez choisir le type recurrent a utiliser');
			$('#form-facture-radior').focus();
		}else if($codeTypeCredit===""){
			$super_notification.removeClass('alert alert-danger');
			$super_notification.text('');
			$super_notification.addClass('alert alert-danger');
			$super_notification.text('Veuillez selectionner le type de produit');
			$('#form-field-select-prk-facture').focus();
		}else{
		
		$csrf = $('input[name="_csrf"]').val();

		$('#load_validate_facture').css({'display':'inline-block'});
		$('#load_validate_icone').css({'display':'inline-block'});

		
			reglement = "codeTypeCredit="+$codeTypeCredit+"&perioder="+$perioder+"&codeEnvoi="+$codeEnvoi+"&referenceFacture="+$referenceFacture+"&periodeFacture="+$periodeFacture+"&prk="+$prk+"&codeBonConso="+$codeBonConso+"&codeMembreAcheteur="+$codeMembreAcheteur+"&typeMontantBon="+$typeMontantBon+"&typeR="+$typeR+"&montantAchat="+$montantAchatHT+"&_csrf=" + $csrf + "";

			$.ajax({
				url:"creationBonConsoFactureByBanInterim",
				type:'POST',
				data:reglement,
				success: function(data){	
					$('#load_validate_facture').fadeOut();
					$('#load_validate_icone').fadeOut();
					if(data === 0){
						
						$('#text-codesms_facture').val('');
						$('#notification_sms_facture').css({'display':'none'});
						$codeEnvoi = "";
						
						$('#notification_final').css({'display':'block'});
						$('#text-produit-facture').val('');
						$('#text-reference-facture').val('');
						$('#text-police-facture').val('');
						$('#text-periode-facture').val('');

											
						$('#label-cumul-facture').css({'display':'none'});
						$('#text-montant-cumul-facture').css({'display':'none'});
						$('#text-montant-cumul-facture').val(''+0+'');
						$('#label-montant-bc-cumul-facture').css({'display':'none'});
						$('#text-montant-bc-cumul-facture').css({'display':'none'});
						$('#text-montant-bc-cumul-facture').val(''+0+'');
						/*
						$('#label-montant-opi-facture').css({'display':'none'});
						$('#text-montant-facture-opi').css({'display':'none'});
						$('#text-montant-facture-opi').val(''+0+'');
						$('#label-montant-bc-opi-facture').css({'display':'none'});
						$('#text-montant-bc-opi-facture').css({'display':'none'});
						$('#text-montant-bc-opi-facture').val(''+0+'');
						*/
						
						$('#label-montant-ban-facture').css({'display':'none'});
						$('#text-montant-ban-facture').css({'display':'none'});
						$('#text-montant-ban-facture').val(''+0+'');
						$('#label-montant-bc-ban-facture').css({'display':'none'});
						$('#text-montant-bc-ban-facture').css({'display':'none'});
						$('#text-montant-bc-ban-facture').val(''+0+'');
						
						
						$('#label-montant-ba-facture').css({'display':'none'});
						$('#text-montant-ba-facture').css({'display':'none'});
						$('#text-montant-ba-facture').val(''+0+'');
						$('#label-montant-bc-ba-facture').css({'display':'none'});
						$('#text-montant-bc-ba-facture').css({'display':'none'});
						$('#text-montant-bc-ba-facture').val(''+0+'');
						
						
						$('#label-montant-bc-bc-facture').css({'display':'none'});
						$('#text-montant-bc-bc-facture').css({'display':'none'});
						$('#text-montant-bc-bc-facture').val(''+0+'');
						
						
						$('#text-codemembre-facture').val('');
						$('#form-facture-bonConso').val('');
						
						
						$('#form-facture-radioBc').prop('checked', false);
						$('#form-facture-radioBn').prop('checked', false);
						$('#form-facture-radioBa').prop('checked', false);
					//	$('#form-facture-radioOpi').prop('checked', false);
						$('#form-facture-radioDefaut').prop('checked', false);
						
						
						$('#form-facture-radioBn').prop('checked', false);
						$('#form-facture-radionr').prop('checked', false);
						$('#form-facture-radior').prop('checked', false);
						
						$('#form-field-select-limite-facture').css({'display':'none'});
						$('#form-field-select-prk-facture').css({'display':'none'});
						$('#label-prk-facture').css({'display':'none'});
						
					}else if(data === 1){
						$super_notification.addClass('alert alert-danger');
						$super_notification.text('Echec de creation du bon de consommation');
					}else if(data === 2){
						$super_notification.addClass('alert alert-danger');
						$super_notification.text('Echec de souscription du bon de consommation');
					}else if(data === 3){
						$super_notification.addClass('alert alert-danger');
						$super_notification.text('Echec de souscription au bon d achat');
					}else if(data === 4){
						$super_notification.addClass('alert alert-danger');
						$super_notification.text('Le code du bon de consommation est invalide');
					}else if(data === 5){
						$super_notification.addClass('alert alert-danger');
						$super_notification.text('Revoir la saise des données');
					}

				},
				error: function (xhr, textStatus, errorThrown) {
					$('#load_validate_facture').fadeOut();
					$('#load_validate_icone').fadeOut();
					$super_notification.addClass('alert alert-danger');
					$super_notification.text('La communication avec le serveur a echoué. Veuillez informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 

				}
			});   


		}


	});
	
	$('body').on('click', '#notification_final_close', function (e) {
		e.preventDefault();
		$('#notification_final').css({'display':'none'});
	});
	
	$('body').on('click', '#notification_sms_final_close_facture', function (e) {
		e.preventDefault();
		$('#notification_sms_facture').css({'display':'none'});
	});

})(jQuery);