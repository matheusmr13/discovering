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
    
    yawpUtils.yawpDatetoDate = function (val) {
		val = val || '';
		var args = val.split('/');

		if (args.length == 3) {
			var a = args[2].split(' ');

			if (a.length == 1) {
				var d = new Date(args[2], args[1] - 1,args[0]);
			} else {
				var splitTime = a[1].split(':');
				var d = new Date(a[0], args[1] - 1, args[0], splitTime[0], splitTime[1], splitTime[2]);
			}

			return d;
		}

		return new Date();//FIXME deveria retornar new date?
	}
    
    this.yawpUtils = yawpUtils;
}).apply(window, [window.jQuery]);