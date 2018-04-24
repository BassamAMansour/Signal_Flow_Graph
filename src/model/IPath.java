package model;

public interface IPath {
	/**
	 * set the content of a path.
	 * 
	 * @param pathContent.
	 */
	public void setPathContent(String pathContent);

	/**
	 * get the content of a path.
	 * 
	 * @return pathContent.
	 */
	public String getPathContent();

	/**
	 * set the gain of a path.
	 * 
	 * @param pathGain.
	 */
	public void setPathGain(float pathGain);

	/**
	 * get the gain of a path.
	 * 
	 * @return pathGain.
	 */
	public float getPathGain();

}
