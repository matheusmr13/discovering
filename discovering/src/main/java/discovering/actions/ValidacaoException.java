package discovering.actions;

import io.yawp.commons.http.HttpException;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

public class ValidacaoException extends HttpException {

	private static final long serialVersionUID = 1684835492911109616L;

	public ValidacaoException(String mensagem) {
		super(422, mensagem);
	}

	public ValidacaoException(List<String> campos) {
		super(422, buildMessage(campos));
	}

	private static String buildMessage(List<String> campos) {
		StringBuilder sb = new StringBuilder();

		if (campos.size() == 1) {
			mensagemParaUmCampo(sb, campos.get(0));
		} else {
			mensagemParaVariosCampos(sb, campos);
		}

		return StringEscapeUtils.unescapeJava(sb.toString());
	}

	private static void mensagemParaVariosCampos(StringBuilder sb, List<String> campos) {
		sb.append("Os campos ");

		int count = 0;

		for (String string : campos) {
			count++;

			sb.append(string);
			if (count < campos.size()) {
				sb.append(", ");
			}
		}

		sb.append(" s&atilde;o obrigat&oacute;rios!");
	}

	private static void mensagemParaUmCampo(StringBuilder sb, String campo) {
		sb.append("O campo ");
		sb.append(campo);
		sb.append(" &eacute; obrigat&oacute;rio!");
	}

}
