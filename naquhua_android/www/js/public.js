var server_host = 'http://127.0.0.1:8081/naquhua';

function getURLParameter(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [, ""])[1].replace(/\+/g, '%20')) || null;
}

function isNum(str){
	pattern = /^[1-9]\d*$/;
	pattern1 = /^[0-9]\d*\.\d*$/
	return pattern.test(str) || pattern1.test(str);
}