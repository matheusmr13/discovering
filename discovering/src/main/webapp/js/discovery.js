 $(document).ready(function () {
 	$.fn.getPath = function () {
 		var path, node = this;
 		while (node.length) {
 			var realNode = node[0],
 				name = realNode.localName;
 			if (!name) break;
 			name = name.toLowerCase();

 			var parent = node.parent();

 			var sameTagSiblings = parent.children(name);
 			if (sameTagSiblings.length > 1) {
 				allSiblings = parent.children();
 				var index = allSiblings.index(realNode) + 1;
 				if (index > 1) {
 					name += ':nth-child(' + index + ')';
 				}
 			}

 			path = name + (path ? '>' + path : '');
 			node = parent;
 		}

 		return path;
 	};
 	var actionType = {
 		MOUSE_MOVE: 'MM',
 		MOUSE_DRAG: 'MD',
 		MOUSE_CLICK: 'MC',
 		COPY: 'CP',
 		INPUT_FOCUS: 'IF',
 		INPUT_KEY: 'IK'
 	};
 	var ip;
 	var countryCode;
 	var countryName;
 	var latitude;
 	var longitude;
 	window.someRandomFuncion = function (json) {
 		ip = json.ip;
 		$.get('http://freegeoip.net/json/' + ip).done(function (location) {
 			countryCode = location.countryCode;
 			countryName = location.countryName;
 			latitude = location.latitude;
 			longitude = location.longitude;
 		});
 	};
 	$('body').append('<script type="application/javascript" src="http://www.telize.com/jsonip?callback=someRandomFuncion"></script>');

 	var getUserInfo = function () {
 		var nVer = navigator.appVersion;
 		var nAgt = navigator.userAgent;
 		var browserName = navigator.appName;
 		var fullVersion = '' + parseFloat(navigator.appVersion);
 		var majorVersion = parseInt(navigator.appVersion, 10);
 		var nameOffset, verOffset, ix;

 		if ((verOffset = nAgt.indexOf("Opera")) != -1) {
 			browserName = "Opera";
 			fullVersion = nAgt.substring(verOffset + 6);
 			if ((verOffset = nAgt.indexOf("Version")) != -1)
 				fullVersion = nAgt.substring(verOffset + 8);
 		} else if ((verOffset = nAgt.indexOf("MSIE")) != -1) {
 			browserName = "Microsoft Internet Explorer";
 			fullVersion = nAgt.substring(verOffset + 5);
 		} else if ((verOffset = nAgt.indexOf("Chrome")) != -1) {
 			browserName = "Chrome";
 			fullVersion = nAgt.substring(verOffset + 7);
 		} else if ((verOffset = nAgt.indexOf("Safari")) != -1) {
 			browserName = "Safari";
 			fullVersion = nAgt.substring(verOffset + 7);
 			if ((verOffset = nAgt.indexOf("Version")) != -1)
 				fullVersion = nAgt.substring(verOffset + 8);
 		} else if ((verOffset = nAgt.indexOf("Firefox")) != -1) {
 			browserName = "Firefox";
 			fullVersion = nAgt.substring(verOffset + 8);
 		} else if ((nameOffset = nAgt.lastIndexOf(' ') + 1) < (verOffset = nAgt.lastIndexOf('/'))) {
 			browserName = nAgt.substring(nameOffset, verOffset);
 			fullVersion = nAgt.substring(verOffset + 1);
 			if (browserName.toLowerCase() == browserName.toUpperCase()) {
 				browserName = navigator.appName;
 			}
 		}
 		if ((ix = fullVersion.indexOf(";")) != -1) {
 			fullVersion = fullVersion.substring(0, ix);
 		}
 		if ((ix = fullVersion.indexOf(" ")) != -1) {
 			fullVersion = fullVersion.substring(0, ix);
 		}

 		majorVersion = parseInt('' + fullVersion, 10);
 		if (isNaN(majorVersion)) {
 			fullVersion = '' + parseFloat(navigator.appVersion);
 			majorVersion = parseInt(navigator.appVersion, 10);
 		}

 		return {
 			countryCode: countryCode,
 			countryName: countryName,
 			latitude: latitude,
 			longitude: longitude,
 			browser: browserName,
 			version: fullVersion,
 			majorVersion: majorVersion,
 			navigatorAppName: navigator.appName,
 			navigatorUserAgent: navigator.userAgent,
 			operatingSystem: navigator.platform,
 			language: navigator.language,
 			ip: ip,
 			screenWidth: window.screen.availWidth,
 			screenHeight: window.screen.availHeight,
 			browserWidth: $(document).width(),
 			browserHeight: $(document).height(),
 			startDate: yawpUtils.dateToYawpDate(new Date()),
 			fullUrl: window.location.origin + window.location.pathname + window.location.hash
 		};
 	};

 	var mouseAction = function (e, t) {
 		return {
 			x: e.pageX,
 			y: e.pageY,
 			s: e.timeStamp,
 			t: t
 		};
 	};

 	var copyAction = function (copiedText) {
 		return {
 			c: copiedText,
 			s: new Date().getTime(),
 			t: actionType.COPY
 		};
 	};

 	var inputFocusAction = function (e) {
 		return {
 			q: $(e.target).getPath(),
 			s: e.timeStamp,
 			t: actionType.INPUT_FOCUS
 		};
 	};

 	var inputKeyAction = function (e) {
 		return {
 			k: e.keyCode,
 			s: e.timeStamp,
 			t: actionType.INPUT_KEY
 		};
 	};

 	var actions = [];
 	var holding = false;

 	$('body').mousemove(function (e) {
 		actions.push(mouseAction(e, holding ? actionType.MOUSE_DRAG : actionType.MOUSE_MOVE));
 	});

 	$('body').mousedown(function (e) {
 		holding = true;
 		actions.push(mouseAction(e, actionType.MOUSE_CLICK));
 	});

 	$('body').mouseup(function (e) {
 		holding = false;
 	});

 	$('input').on('focus', function (e) {
 		actions.push(inputFocusAction(e));
 	});
 	$('input').keydown(function (e) {
 		actions.push(inputKeyAction(e));
 	});

 	var registerCopy = function () {
 		actions.push(copyAction(window.getSelection().toString()));
 	};
 	document.addEventListener('copy', registerCopy);
 	$('button').click(function () {
 		var recording = getUserInfo();
 		recording.actions = actions;
 		$.ajax({
 			type: 'POST',
 			url: '/api/recording/create',
 			data: {
 				recording: JSON.stringify(recording)
 			},
 			dataType: 'json'
 		}).done(function (a, b, c) {
 			console.info(a, b, c)
 		}).fail(function (a, b, c) {
 			console.info("sad", a, b, c)
 		});
 	});
 });