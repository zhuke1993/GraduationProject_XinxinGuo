var server_host = 'http://127.0.0.1:8080/naquhua';
//var server_host = 'http://127.0.0.1:8080';

function getURLParameter(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [, ""])[1].replace(/\+/g, '%20')) || null;
}