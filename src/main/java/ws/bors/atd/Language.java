package ws.bors.atd;

/**
 * Server supported languages. Default is <code>ENGLISH</code>.
 */
public enum Language {
    ENGLISH     ("en"),
    FRENCH      ("fr"),
    GERMAN      ("de"),
    SPANISH     ("es"),
    PORTUGUESE  ("pt");

    private String code;

    private Language(String code) {
        this.code = code;
    }

    public String toString() {
        return code;
    }
}
