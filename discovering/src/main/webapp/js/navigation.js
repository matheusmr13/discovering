(function ($) {

	'use strict';

	var extractParameters = function (paramsStr) {
		if (!paramsStr) {
			return {};
		}

		var paramsWithNames = paramsStr.split('&'),
			params = {};
		for (var i = 0; i < paramsWithNames.length; i++) {
			var param = paramsWithNames[i].split('=');
			params[param[0]] = param[1];
		}

		return params;
	};

	var changeHeaderMenu = function (page) {
		console.info(page);
		var $this = $('.nav a[href="#' + page + '"]').parent();
		$this.addClass('selected').siblings().removeClass('selected');
	};

	var openPage = function (hash, params) {
		$.get('/discovering/' + hash + '.html').done(function (page) {
			$('#content').html(page);
		});
	};

	function open(hash, params) {
		changeHeaderMenu(hash);
		openPage(hash, params);
	};

	function hashchange() {
		var hashAndParams = window.location.hash + '';
		if (!hashAndParams) {
			return;
		}

		hashAndParams = hashAndParams.slice(1).split('?');
		var hash = hashAndParams[0],
			params = extractParameters(hashAndParams[1]);

		if (!hash) {
			window.location = $('body .nav a[href]:first').attr('href');
			return;
		}

		open(hash, params);
	};

	window.onhashchange = hashchange;
	window.onhashchange();

}).apply(window, [window.jQuery]);