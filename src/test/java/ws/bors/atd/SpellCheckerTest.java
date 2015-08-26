package ws.bors.atd;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class SpellCheckerTest {
    private Map<Language, String> pass;
    private Map<Language, String> fail;

    @BeforeTest
    public void setup() {
        pass = new HashMap<Language, String>();
        pass.put(Language.ENGLISH,      "Hello world");
        pass.put(Language.FRENCH,       "Bonjour monde");
        pass.put(Language.GERMAN,       "Hallo Welt");
        pass.put(Language.SPANISH,      "Hola mundo");
        pass.put(Language.PORTUGUESE,   "Ola mundo");

        fail = new HashMap<Language, String>();
        fail.put(Language.ENGLISH,      "Helo world");
        fail.put(Language.FRENCH,       "Bonour monde");
        fail.put(Language.GERMAN,       "Halllo Welt");
        fail.put(Language.SPANISH,      "Holla mundo");
        fail.put(Language.PORTUGUESE,   "Olla mundo");
    }

    @Test
    public void basicSpellTest() throws Exception {
        SpellChecker spellChecker = new SpellChecker();

        for(Language lang : pass.keySet()) {
            spellChecker.setLanguage(lang);
            List<String> errors = spellChecker.spellErrors(pass.get(lang));
            assertTrue(errors.isEmpty(), "There should be no spelling errors in '" + pass.get(lang) + "'!");
            Thread.sleep(500);
        }

        for(Language lang : fail.keySet()) {
            spellChecker.setLanguage(lang);
            String error = fail.get(lang).split(" ")[0];
            List<String> errors = spellChecker.spellErrors(fail.get(lang));
            assertFalse(errors.isEmpty(), "Expected errors for " + lang);
            assertTrue(errors.get(0).equalsIgnoreCase(error), "'" + error + "' is not spelled right in '" + fail.get(lang) + "'!");
            Thread.sleep(500);
        }
    }
}
