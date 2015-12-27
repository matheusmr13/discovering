package discovering.utils;

import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class CriptoUtils {
	public static String newToken() {
		String novoToken = new String(Base64.encodeBase64(UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "").getBytes()));
		novoToken = novoToken.substring(0, novoToken.length() - 1);
		return novoToken;
	}

	public static String sha256(String senha) {
		return DigestUtils.sha256Hex(senha);
	}
}
