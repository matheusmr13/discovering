package discovering.authentication;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.apache.commons.io.IOUtils;

import br.com.dextra.security.configuration.CertificateRepository;
import br.com.dextra.security.configuration.StringBase64CertificateRepository;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class PersistentCertificateRepository implements CertificateRepository {

	private final StringBase64CertificateRepository repository = new StringBase64CertificateRepository();
	private static final String PROVIDER = "DISCOVERING";

	@Override
	public void clearCaches() {
		this.repository.clearCaches();
		loadKeys();
	}

	@Override
	public PrivateKey getPrivateKey() {
		if (this.repository.getPrivateKey() == null) {
			loadKeys();
		}

		return this.repository.getPrivateKey();
	}

	@Override
	public PublicKey getPublicKeyFor(String provider) {
		if (this.repository.getPublicKeyFor(provider) == null) {
			loadKeys();
		}

		return this.repository.getPublicKeyFor(provider);
	}

	public void generateKeys() throws NoSuchAlgorithmException, NoSuchProviderException, IOException {
		loadKeys();
	}

	protected void loadKeys() {
		try {
			JsonParser parser = new JsonParser();
			JsonObject obj = parser.parse(loadStringFromFile()).getAsJsonObject();
			this.repository.configurePrivateKey(obj.get("privateKey").getAsString());
			this.repository.configurePublicKey(PROVIDER, obj.get("publicKey").getAsString());
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String loadStringFromFile() throws IOException {
		StringWriter writer = new StringWriter();
		IOUtils.copy(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("AuthKeys.json")), writer);

		return writer.toString();
	}

}
