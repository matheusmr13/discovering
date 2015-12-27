(function($) {
    var yawpUtils = window.yawpUtils || {};

    var splitDate = function (date) {
		var segundos = date.getSeconds() + '',
			minutes = date.getMinutes() + '',
			hours = date.getHours() + '',
			day = date.getDate() + '',
			month = (date.getMonth() + 1) + '',
			year = date.getFullYear(),
			splited = {};

		if (day.length === 1) {
			day = '0' + day;
		}
		if (month.length === 1) {
			month = '0' + month;
		}

		while (hours.length < 2) {
			hours = '0' + hours;
		}
		while (minutes.length < 2) {
			minutes = '0' + minutes;
		}

		while (segundos.length < 2) {
			segundos = '0' + segundos;
		}

		splited.segundos = segundos;
		splited.hora = hours;
		splited.minutos = minutes;
		splited.dia = day;
		splited.mes = month;
		splited.ano = year;
		return splited;

	};

    yawpUtils.dateToYawpDate = function (date) {
		if (!date) {
			return '';
		}

		var dataSeparada = splitDate(date);
		return dataSeparada.ano + '/' + dataSeparada.mes + '/' + dataSeparada.dia +
			' ' + dataSeparada.hora + ':' + dataSeparada.minutos + ':' + dataSeparada.segundos;

	};

	yawpUtils.formatarHora = function (dateStr) {
		if (!dateStr) {
			return '00:00:00';
		}
		var timestamp = Date.parse(dateStr);
		if (isNaN(timestamp) == false) {
			var dataSeparada = splitDate(new Date(dateStr));
			return dataSeparada.hora + ':' + dataSeparada.minutos + ':' + dataSeparada.segundos;
		}

		return '00:00:00';
	};

	yawpUtils.formatarData = function (dateStr) { // yyyy/MM/dd
		if (!dateStr) {
			return '';
		}

		if (dateStr.split('/')[0].length === 2) { //day as first argument
			return dateStr;
		}
		var data = new Date(dateStr);
        if (data == 'Invalid Date')
            return '';

		var dataSeparada = splitDate(data);
		return dataSeparada.dia + '/' + dataSeparada.mes + '/' + dataSeparada.ano + ' ' + yawpUtils.formatarHora(dateStr);
	};

    yawpUtils.yawpDateToDate = function (val) {
		val = val || '';
		var args = val.split('/');

		if (args.length == 3) {
			var a = args[2].split(' ');
			var d;
			if (a.length == 1) {
				d = new Date(args[2], args[1] - 1,args[0]);
			} else {
				var splitTime = a[1].split(':');
				d = new Date(a[0], args[1] - 1, args[0], splitTime[0], splitTime[1], splitTime[2]);
			}

			return d;
		}

		return new Date();
	}

    this.yawpUtils = yawpUtils;
}).apply(window, [window.jQuery]);