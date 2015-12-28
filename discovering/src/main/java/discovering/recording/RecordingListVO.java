package discovering.recording;

public class RecordingListVO extends Recording {

	public RecordingListVO(Recording recording) {
		this.setStartDate(recording.getStartDate());
		this.setBrowser(recording.getBrowser());
		this.setIp(recording.getIp());
		this.setOperatingSystem(recording.getOperatingSystem());
		this.setId(recording.getId());
	}

}
