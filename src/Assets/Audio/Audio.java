package Assets.Audio;

/**
 * @brief Interface for handling audio assets.
 */
public interface Audio {
    /**
     * Playes the audio.
     */
    void Play();

    /**
     * Stops the audio.
     */
    void Stop();

    /**
     * Resumes the playing of the audio.
     */
    void Resume();
}
