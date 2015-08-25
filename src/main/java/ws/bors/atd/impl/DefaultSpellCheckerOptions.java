package ws.bors.atd.impl;

import ws.bors.atd.ISpellCheckerOptions;

/**
 * Default {@link ISpellCheckerOptions} that performs no text processing and it dose not ignore any reported misspelled
 * words.
 */
public class DefaultSpellCheckerOptions implements ISpellCheckerOptions {
    @Override
    public String processChars(String text) {
        return text;
    }

    @Override
    public boolean ignoreWord(String misspelledWord) {
        return false;
    }
}
