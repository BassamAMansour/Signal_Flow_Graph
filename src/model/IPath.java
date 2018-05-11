package model;

public interface IPath {
	/**
	 * get the content of a path.
	 *
	 * @return pathContent.
	 */
    String getPathContent();

	/**
	 * set the content of a path.
	 *
     * @param pathContent.
	 */
    void setPathContent(String pathContent);

	/**
	 * get the gain of a path.
	 *
	 * @return pathGain.
	 */
    float getPathGain();

	/**
	 * set the gain of a path.
	 *
     * @param pathGain.
	 */
    void setPathGain(float pathGain);

}
