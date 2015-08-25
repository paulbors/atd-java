package ws.bors.atd;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SpellCheckerTest {
    @Test
    public void sanityTest() throws Exception {
        SpellChecker spellChecker = new SpellChecker();

        List<String> errors = spellChecker.spellErrors("hello world");
        Assert.assertTrue(errors.isEmpty(), "There should be no spelling errors in 'hello world'!");

        Thread.sleep(1000); // Too many requests are sometimes rejected with '503 Service Temporarily Unavailable'

        errors = spellChecker.spellErrors("helo world");
        Assert.assertTrue(errors.get(0).equalsIgnoreCase("helo"), "'helo' is not spelled right in 'helo world'!");
    }
}
