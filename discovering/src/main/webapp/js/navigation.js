(function ($) {

	'use strict';

	var Discovering = window.Discovering || {};

	Discovering.navigation = Discovering.navigation || {};
	Discovering.navigation.basePageUrl = '/discovering/';
	Discovering.navigation.baseApiUrl = '/discovering/';

	Discovering.navigation.showPage = function (pageUrl, container) {
		container = container || $('#content');
		$.get(Discovering.navigation.basePageUrl + pageUrl + '.html').done(function (pageHtml) {
			container.html(pageHtml);
		});
	};

	Discovering.navigation.populatePage = function (pageUrl, data, container) {
		container = container || $('#content');
		$.get(Discovering.navigation.basePageUrl + pageUrl + '.html').done(function (pageHtml) {
			container.html(doT.template(pageHtml)(data));
		});
	};

	Discovering.navigation.openNewPage = function (pageUrl, dataUrl, container) {
		container = container || $('#content');
		var pageRequest = $.get(Discovering.navigation.basePageUrl + pageUrl + '.html');
		var dataRequest = $.getJSON(Discovering.navigation.basePageUrl + dataUrl);

		$.when(pageRequest, dataRequest).done(function (pageReturn, dataReturn) {
			container.html(doT(pageReturn[0])(dataReturn[0]));
		});
	}

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
		var $this = $('.nav a[href="#' + page + '"]').parent();
		$this.addClass('selected').siblings().removeClass('selected');
	};

	var openPage = function (hash, params) {
		var hashParts = hash.split('/');
		if (hashParts.length > 1) {
			Discovering[hashParts[0]][hashParts[1]](params);
		} else {
			Discovering[hashParts[0]]['init'](params);
		}
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