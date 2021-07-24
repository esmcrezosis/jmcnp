/**
 * 
 */
$(document).ready( function () {
$('#codeMembreDebiteur').blur(function(e) {
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

$('#codeMembreCreancier').blur(function(e) {
	e.preventDefault();
	if ($(this).val() !== '') {
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
			var options = $("#paiement_tegc");
			var i;
			for (i = 0; i < data.length; i++) {
				options.append(new Option(data[i].nomTegc, data[i].codeTegc));
			}
		});
	}
});


});	
