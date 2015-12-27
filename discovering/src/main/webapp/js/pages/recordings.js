(function () {
	'use strict';

	var Discovering = window.Discovering || {};
	Discovering.recordings = Discovering.recordings || {};

	Discovering.recordings.init = function () {
		yawp('/recordings').list(function (recordingsList) {
			Discovering.navigation.populatePage('recordings', recordingsList);
		});
	};

	window.Discovering = Discovering;
})();