(function ($) {

	'use strict';

	var currentUser;

	$.currentUser = function (forceRequest) {
		var userApi = function () {
			yawp('/users').sync().noCache().get('me')
				.fail(function () {
					window.location = '/';
					throw 'Getting current user failed!';
				}).done(function (user) {
					currentUser = user;
				});
		};

		if (!currentUser || forceRequest) {
			userApi();
		}

		return currentUser;
	};

}).apply(window, [window.jQuery]);