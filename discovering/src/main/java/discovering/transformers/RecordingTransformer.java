package discovering.transformers;

import io.yawp.repository.transformers.Transformer;

import java.util.Map;

import discovering.models.Recording;

public class RecordingTransformer extends Transformer<Recording> {

	@Override
	public Object defaults(Recording object) {
		Map<String, Object> map = asMap(object);
		map.put("browser", object.getBrowser().toUpperCase());
		return map;
	}


}