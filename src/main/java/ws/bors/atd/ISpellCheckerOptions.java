package ws.bors.atd;

/**
 * @{link SpellChecker} options that modify the behavior and query string.
 */
public interface ISpellCheckerOptions {
    /**
     * Called right before the POST request is made to the AtD server to perform last minute string manipulations.
     *
     * @param text              Raw text that requested to be checked by the AtD server.
     * @return                  The modified text that will be submitted to the AtD server.
     */
    String processChars(String text);

    /**
     * Should the misspelled word be ignored? This is useful for jargon and medical terms.
     *
     * @param misspelledWord    The word that was reported as misspelled
     * @return                  <code>true</code> to ignore this misspelled word and excluded it from the results,
     *                          <code>false</code> otherwise.
     */
    boolean ignoreWord(String misspelledWord);
}
