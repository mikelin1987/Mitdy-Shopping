
var imagePath = contextPath + '/images/loading.gif';

$(document).ajaxStart(function() {
	console.log(imagePath)
  	$('#loading-status').html('<img src="' + imagePath + '" />'); 
});

$(document).ajaxComplete(function() {
	$('#loading-status').html('');
});