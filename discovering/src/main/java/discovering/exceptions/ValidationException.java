package discovering.exceptions;

import io.yawp.commons.http.HttpException;

public class ValidationException extends HttpException {

	private static final long serialVersionUID = 2296985957648000555L;

	public ValidationException(String msg) {
		super(422, msg);
	}
}
