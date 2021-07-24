(function ($) {

	var $codeMembreDemandeur;
	var $notif_final = $('#notification_generer_final');
	var $notif = $('#notification_generer'); 
	var $typeCode;
	var $nbreCode;
	var $nbreCodeSg;
	var $nbreCodeDet;


	//radio grossiste
	$('body').on('click', '#form-generer-grossiste', function(e){
		$notif.removeClass('alert alert-danger');
		$notif.text('');  

		$('#form-generer-nb').val('0');
		$('#form-generer-nbsg').val('0');
		$('#form-generer-nbdet').val('0');
		$('#label-nbre-sg').css({'display':'inline-block'});
		$('#form-generer-nbsg').css({'display':'inline-block'});
		$('#label-nbre-det').css({'display':'inline-block'});
		$('#form-generer-nbdet').css({'display':'inline-block'});

		$typeCode ="g";

	});


	//radio semi grossiste
	$('body').on('click', '#form-generer-semi-grossiste', function(e){
		$notif.removeClass('alert alert-danger');
		$notif.text('');  
		$('#form-generer-nb').val('0');
		$('#form-generer-nbsg').val('0');
		$('#form-generer-nbdet').val('0');
		$('#label-nbre-sg').css({'display':'none'});
		$('#form-generer-nbsg').css({'display':'none'});
		
		$('#label-nbre-det').css({'display':'inline-block'});
		$('#form-generer-nbdet').css({'display':'inline-block'});

		$typeCode ="sg";

	});

	//radio detaillant
	$('body').on('click', '#form-generer-detaillant', function(e){
		$notif.removeClass('alert alert-danger');
		$notif.text('');  
		$('#form-generer-nb').val('0');
		$('#form-generer-nbsg').val('0');
		$('#form-generer-nbdet').val('0');
		$('#label-nbre-sg').css({'display':'none'});
		$('#form-generer-nbsg').css({'display':'none'});
		$('#label-nbre-det').css({'display':'none'});
		$('#form-generer-nbdet').css({'display':'none'});

		$typeCode ="det";

	});


	$('body').on('click', '#form-generer-enregistrer', function(e){
		e.preventDefault();
		$('#confirm-generer-modal').show();
		$('.modal-backdrop').show();

		$('#confirm-generer-modal').modal({
			keyboard : false,
			backdrop : 'static',
			width : 300
		});
	});


	$('body').on('click', '#form-generer-oui', function(e){
		$notif.removeClass('alert alert-danger');
		$notif.text('');  
		
		$nbreCode = Math.floor(parseInt($('#form-generer-nb').val()));
		console.log("$nbreCode : "+$nbreCode);
		$nbreCodeSg = Math.floor(parseInt($('#form-generer-nbsg').val()));
		console.log("$nbreCodeSg : "+$nbreCodeSg);
		$nbreCodeDet = Math.floor(parseInt($('#form-generer-nbdet').val()));
		console.log("$nbreCodeDet : "+$nbreCodeDet);
		
		
		if($('#form-generer-grossiste').is(':checked') === true){
			$typeCode ="g";
			console.log("$typeCode : "+$typeCode);

			
		}else if($('#form-generer-semi-grossiste').is(':checked') === true){
			$typeCode ="sg";
			console.log("$typeCode : "+$typeCode);
			
		}else if($('#form-generer-detaillant').is(':checked') === true){
			$typeCode ="det";
			console.log("$typeCode : "+$typeCode);
			
		}

		if($typeCode ===''){
			$notif.removeClass('alert alert-danger');
			$notif.text('');  
			$notif.addClass('alert alert-danger');
			$notif.text('Veuillez choisir le créneau du demandeur');
		}else{

			$codeMembreDemandeur = $('#form-generer-codemembre').val();
			console.log('$codeMembreDemandeur= '+$codeMembreDemandeur);
			if($codeMembreDemandeur===''){
				$notif.removeClass('alert alert-danger');
				$notif.text('');  
				$notif.addClass('alert alert-danger');
				$notif.text('Veuillez saisir le code membre');  
				$('#form-generer-codemembre').focus();

			}else if($('#form-generer-codemembre').val().length<20){
				$notif.removeClass('alert alert-danger');
				$notif.text('');  
				$notif.addClass('alert alert-danger');
				$notif.text('Veuillez vérifier le code membre saisi');  
				$('#form-generer-codemembre').focus();
			}else{
				if($nbreCode<=0){
					$notif.removeClass('alert alert-danger');
					$notif.text('');  
					$notif.addClass('alert alert-danger');
					$notif.text('Le nombre de codes saisi est invalide');  
					$('#form-generer-nb').focus();
				}else{

				$csrf = $('input[name="_csrf"]').val();
				enregistrercode = "codeMembreDemandeur="+$codeMembreDemandeur+"&typeCode="+$typeCode+"&nbreCode="+$nbreCode+"&nbreCodeSg="+$nbreCodeSg+"&nbreCodeDet="+$nbreCodeDet+"&_csrf=" + $csrf + "";

				$('#load_generer').css({'display':'inline-block'});
				$('#load_generer_icone').css({'display':'inline-block'});

				$.ajax({
					url:"creationCodeBarrePourTous",
					type:'POST',
					data: enregistrercode,
					success: function(data){
						$('#load_generer').fadeOut();
						$('#load_generer_icone').fadeOut();
						if(data==0){
							$('#notification_generer_final').css({'display':'block'});
							
							$('#form-generer-codemembre').val('');
							$('#form-generer-grossiste').prop('checked', false);
							$('#form-generer-semi-grossiste').prop('checked', false);
							$('#form-generer-detaillant').prop('checked', false);
							
							$('#form-generer-nb').val('0');
							$('#form-generer-nbsg').val('0');
							$('#form-generer-nbdet').val('0');
							
							$typeCode ='';
							$codeMembreDemandeur='';
							$nbreCode=0;
							$nbreCodeSg=0;
							$nbreCodeDet=0;
						}else{
						
							$notif.addClass('alert alert-danger');
							$notif.text('Revoir la saisie des données');  
														
						}
						
					},
					error: function (xhr, textStatus, errorThrown) {
						$('#load_generer').fadeOut();
						$('#load_generer_icone').fadeOut();
						$notif.addClass('alert alert-danger');
						$notif.text('La communication avec le serveur a echoué. Veuillez informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 
					}
				}); 



			}

		}
		}
	});


	$('body').on('click', '#form-generer-reprendre', function(e){
    	$notif.removeClass('alert alert-danger');
		$notif.text('');  
		$('#form-generer-codemembre').val('');
		$('#form-generer-grossiste').prop('checked', false);
		$('#form-generer-semi-grossiste').prop('checked', false);
		$('#form-generer-detaillant').prop('checked', false);
		
		$('#form-generer-nb').val('0');
		$('#form-generer-nbsg').val('0');
		$('#form-generer-nbdet').val('0');
	});
	
	$('body').on('click', '#notification_generer_final_close', function (e) {
		e.preventDefault();
		$('#notification_generer_final').css({'display':'none'});
	});
	
	
	
	
	$('body').on('click', '#form-generer-exporter', function (e) {
		e.preventDefault();
		$codeMembreDemandeur = $('#form-generer-codemembre').val();
		console.log('$codeMembreDemandeur= '+$codeMembreDemandeur);
		$csrf = $('input[name="_csrf"]').val();
		enregistrercode = "codeMembreDemandeur="+$codeMembreDemandeur+"&_csrf=" + $csrf + "";

		$('#load_generer').css({'display':'inline-block'});
		$('#load_generer_icone').css({'display':'inline-block'});

		$.ajax({
			url:"creerFichierExcel",
			type:'POST',
			data: enregistrercode,
			success: function(data){
				$('#load_generer').fadeOut();
				$('#load_generer_icone').fadeOut();
				if(data[0]=="1"){
					
					$notif.addClass('alert alert-danger');
					$notif.text(data[1]);  
				}
			},
			error: function (xhr, textStatus, errorThrown) {
				
				$notif.addClass('alert alert-danger');
				$notif.text('La communication avec le serveur a echoué. Veuillez informer le service de développement. \n Erreur: '+(errorThrown ? errorThrown : xhr.status)); 
			}
			});
	});

})(jQuery);