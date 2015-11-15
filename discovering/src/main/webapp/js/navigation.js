(function ($) {

	'use strict';


	var popped = ('state' in window.history && window.history.state !== null),
		initialURL = location.href;
	var popstate = function (e) {
		var initialPop = !popped && location.href == initialURL
		console.info(popped);
		popped = true;
		console.info(popped);
		console.info(window.location.href);
		if (initialPop) return;
		return false;
	};

	window.onpopstate = popstate;

	$('body').on('click', 'a', function () {
		console.info($(this));
		var a = $(this);
		popstate(a.data('href'));
		return false;
	});

}).apply(window, [window.jQuery]);