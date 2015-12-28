(function ($) {

	'use strict';

	var tableUtils = {};

	var elementoIcone = function (table, th, divHiddenColumns, index) {
		return $('<i class="fa fa-eye-slash block"></i>').unbind('click').click(function () {
			var widthTabela = table[0].getClientRects()[0].width,
				icon = $(this);

			table.find('tr:not(:last)').find('td:eq(' + index + '), th:eq(' + index + ')').fadeOut(500);

			th.fadeOut(500, function () {
				table.colocarTotalizador();
			});

			divHiddenColumns.append('<div class="hidden-column">' + th.text() + '<i class="fa fa-eye" data-i="' + index + '"></div>');
		});
	};

	var somarConteudoColuna = function (table, i) {
		var soma = 0;

		table.find('tr').find('td:eq(' + i + ')').each(function () {
			soma += utils.toFloat($(this).text());
		});

		return formatter.formatarFloat(soma);
	};

	$.fn.colocarTotalizador = function () {
		return this.each(function () {
			var camposTabelaSomar = $(this).data('totalizadores');
			if (camposTabelaSomar && camposTabelaSomar.length > 0) {
				var qtdColunas = $(this).find('thead tr:last-child th, thead tr:last-child td').size(),
					elementosAntes = $(this).find('tbody tr:first td').eq(camposTabelaSomar[0]).prevAll(),
					rolspan = elementosAntes.filter(function () {
						return $(this).is(':visible')
					}).size() == 0 ? elementosAntes.size() : elementosAntes.filter(function () {
						return $(this).is(':visible')
					}).size(),
					qtdColunasAntes = elementosAntes.size(),
					totalizador = '<tr class="totalizador"><td colspan="' + rolspan + '">TOTAL</td>';

				$(this).find('tr.totalizador').remove();

				for (var i = 0; i < qtdColunas; i++) {
					if ($(this).find('tr th:eq(' + i + '):visible').size() > 0) {
						if (camposTabelaSomar.indexOf(i) == -1 && i > qtdColunasAntes) {
							totalizador += '<td></td>';

						} else if (camposTabelaSomar.indexOf(i) > -1) {
							totalizador += '<td>' + somarConteudoColuna($(this), i) + '</td>';
						}
					}
				}

				totalizador += '</tr>';
				$(this).find('tbody').append(totalizador);
			}
		});
	};

	$.fn.fixTableGigante = function (options) {
		var options = options || {};
		return this.each(function () {
			var sum = 0,
				divBeforeTable = $(this),
				divHiddenColumns = $('<div class="hidden-columns"></div>'),
				table = $(this).find('table');

			table.setFixedHeader();
			var tableBody = $(this).find('table:eq(1)'),
				tableHeader = $(this).find('table:eq(0)'),
				tables = $(this).find('table');

			if (!options.cannotHide) {
				divBeforeTable.prepend(divHiddenColumns);
			}

			divHiddenColumns.on('click', '.fa-eye', function () {
				var index = $(this).data('i');

				tables.find('tr').find('td:eq(' + index + '), th:eq(' + index + ')').fadeIn(500, function () {
					tableBody.colocarTotalizador();
				});

				$(this).parents('.hidden-column').remove();
			});

			tableHeader.find('tr:first th').each(function (index) {
				var th = $(this),
					width = th[0].offsetWidth;

				sum += width;

				if (!options.cannotHide) {
					th.prepend(elementoIcone(tableBody, th, divHiddenColumns, index));
				}
				tableBody.find('tr:first td').eq(index).addClass($(this).prop('class'));
			});

			tables.css('width', tableHeader[0].getClientRects()[0].width);
		});
	};

	$.fn.setFixedHeader = function () {
		return this.each(function () {
			var tabelaAtual = $(this),
				header = $(this).clone(),
				parent = $(this).parent();

			header.find('tbody').remove();
			$(this).find('thead').addClass('size-header').find('th').text('');

			var headersParent = $('<div style="width:' + parent[0].offsetWidth + ';position:relative;"></div>');
			headersParent.append(header).insertBefore($(this));

			var heightHeader = headersParent[0].getClientRects()[0].height;

			headersParent.css('top', 0);
			$(window).scroll(function (e) {
				if ($('body').scrollTop() > tabelaAtual.offset().top - heightHeader) {
					headersParent.css('position', 'fixed');
					headersParent.css('overflow', 'hidden');
					header.css('marginLeft', -parent.scrollLeft());
				} else {
					headersParent.css('position', 'relative');
					header.css('marginLeft', 0);
					headersParent.css('overflow', 'visible');
				}
			});

			parent.scroll(function (e) {
				if (headersParent.css('position') == 'fixed') {
					header.css('marginLeft', -parent.scrollLeft());
				}
			});
		});
	};

	this.tableUtils = tableUtils;

}).apply(window, [window.jQuery]);