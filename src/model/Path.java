package model;

/**
 * @author HANAN SAMIR
 *
 */
public class Path implements IPath {

	private String pathContent;
	private float pathGain;

	public Path(String pathContent, float pathGain) {
		this.pathContent = pathContent;
		this.pathGain = pathGain;
	}

	@Override
	public void setPathContent(String pathContent) {
		this.pathContent = pathContent;
	}

	@Override
	public String getPathContent() {
		return this.pathContent;
	}

	@Override
	public void setPathGain(float pathGain) {
		this.pathGain = pathGain;
	}

	@Override
	public float getPathGain() {
		return this.pathGain;
	}

}
