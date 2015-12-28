package discovering.recording;

import io.yawp.repository.transformers.Transformer;

import java.util.Map;

public class RecordingTransformer extends Transformer<Recording> {

	@Override
	public Recording create(Recording recording) {
		return null;
	}

	@Override
	public Recording update(Recording recording) {
		return null;
	}

	public RecordingListVO list(Recording recording) {
		return new RecordingListVO(recording);
	}

}
