(function () {
	'use strict';

	var Discovering = window.Discovering || {};
	Discovering.recordings = Discovering.recordings || {};

	Discovering.recordings.list = function () {
		yawp('/recordings').transform('list').list(function (recordingsList) {
			Discovering.navigation.populatePage('recordings', recordingsList);
		});
	};

	Discovering.recordings.only = function (params) {
		yawp(params.id).fetch(function (recording) {
			Discovering.navigation.populatePage('recording', recording);
		});
	};

	window.Discovering = Discovering;
})();