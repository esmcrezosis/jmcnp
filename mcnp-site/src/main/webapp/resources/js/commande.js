jQuery(function(){
	$('body').on('click','#reglement-validation',function(){
		$('.dist-cmd-first').addClass('commande-left');
		$('.dist-cmd-second').removeClass('commande-right')
	});
});