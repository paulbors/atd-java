package ws.bors.atd;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import ws.bors.atd.impl.DefaultSpellCheckerOptions;
import ws.bors.atd.xml.Error;
import ws.bors.atd.xml.Results;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Uses the default English AtD server. If need to install a local instance see
 * <a href="https://OpenAtD.svn.WordPress.org/atd-server/">https://OpenAtD.svn.WordPress.org/atd-server/</a>
 * <br/><br/>
 * For API of the AtD server see: <a href="http://www.AfterTheDeadline.com/api.slp">http://www.AfterTheDeadline.com/api.slp</a>
 *
 * @see <a href="http://www.AfterTheDeadline.com/">http://www.AfterTheDeadline.com/</a>
 */
public class SpellChecker {
    private ISpellCheckerOptions options;
    private String atdServer = "http://Service.AfterTheDeadline.com/checkDocument";
    private String apiKey;

    /**
     * Construct a new atd-java {@link SpellChecker} using the default English AtD server.
     *
     * @see <a href="http://www.AfterTheDeadline.com/api.slp">http://www.AfterTheDeadline.com/api.slp</a>
     */
    public SpellChecker() {
        apiKey = "https://github.com/paulbors/atd-java?" + System.currentTimeMillis();
        options = new DefaultSpellCheckerOptions();
    }

    /**
     * Construct a new atd-java {@link SpellChecker} using a custom AtD server and API key.
     *
     * @see <a href="http://www.AfterTheDeadline.com/api.slp">http://www.AfterTheDeadline.com/api.slp</a>
     */
    public SpellChecker(String atdServerUrl, String apiKey) {
        this();
        this.atdServer = atdServerUrl;
        this.apiKey = apiKey;
    }

    /**
     * Override the {@link DefaultSpellCheckerOptions} with your custom {@link ISpellCheckerOptions}.
     *
     * @param options       Your custom {@link ISpellCheckerOptions} implementation.
     */
    public void setOptions(ISpellCheckerOptions options) {
        this.options = options;
    }

    /**
     * Runs the given text through the AtD server and returns a list of misspelled words.
     *
     * @param text          The raw text to submit via a POST to the AtD server.<br/>
     *                      If needed, tt can be modified via {@link ISpellCheckerOptions#processChars(String)}
     * @return              List of misspelled words contained in the given string.
     * @throws IOException  In case of a problem or connection was aborted to the AtD server
     */
    public List<String> spellErrors(String text) throws IOException {
        List<Error> spellingErrors = new ArrayList<Error>();

        Results results = queryServer(text);
        if(results.getError() != null) {
            for(Error error : results.getError()) {
                if(error.getType().equalsIgnoreCase("spelling") && !options.ignoreWord(error.getString())) {
                    /* TODO: Split the camel and book titles into separate words and check them again
                    List<String> wordErrors = new ArrayList<String>();
                    for(String word : error.getString().split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
                        wordErrors.addAll(check(word));
                    }*/
                    spellingErrors.add(error);
                }
            }
        }

        List<String> errors = new ArrayList<String>();
        for(Error error : spellingErrors) {
            errors.add(error.getString());
        }

        return errors;
    }

    /**
     * Runs the given text through the AtD server and returns its {@link Results}
     *
     * @param text          The raw text to submit via a POST to the AtD server.<br/>
     *                      If needed, tt can be modified via {@link ISpellCheckerOptions#processChars(String)}
     * @return              The {@link Results} of the query to the AtD server
     * @throws IOException  In case of a problem or connection was aborted to the AtD server
     */
    public Results queryServer(String text) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(atdServer);
        List <NameValuePair> nameValuePairs = new ArrayList <NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("key", apiKey));
        text = options.processChars(text);
        nameValuePairs.add(new BasicNameValuePair("data", text));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpClient.execute(httpPost);

        String result = EntityUtils.toString(response.getEntity());

        XStream xStream = new XStream(new DomDriver());
        xStream.processAnnotations(Results.class);
        return (Results)xStream.fromXML(result);
    }
}