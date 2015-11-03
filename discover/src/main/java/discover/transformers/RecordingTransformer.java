package discover.transformers;

import java.util.Map;

import discover.models.Recording;
import io.yawp.repository.transformers.Transformer;

public class RecordingTransformer extends Transformer<Recording> {

	@Override
	public Object defaults(Recording object) {
		Map<String, Object> map = asMap(object);
		map.put("browser", object.getBrowser().toUpperCase());
		return map;
	}


}
