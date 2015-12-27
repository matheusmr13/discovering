(function () {
	'use strict';

	var Discovering = window.Discovering || {};
	Discovering.home = Discovering.home || {};

	Discovering.home.init = function () {
		Discovering.navigation.showPage('home');
	};

	window.Discovering = Discovering;
})();