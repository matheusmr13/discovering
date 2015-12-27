(function () {
	'use strict';

	var Discovering = window.Discovering || {};
	Discovering.settings = Discovering.settings || {};

	Discovering.settings.init = function () {
		yawp('/users').get('settings').done(function (settings) {
			Discovering.navigation.populatePage('settings', settings);
			window.doForm();
		});
	};

	window.Discovering = Discovering;
})();