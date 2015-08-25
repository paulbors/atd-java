/**
 * Copyright (C) atd-java (https://github.com/paulbors/atd-java)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
     * @param atdServerUrl  Required, The AtD server to connect and send queries to.
     * @param apiKey        ID + 32 hex digits<br/><br/>Required, used to control server-side caching.<br/><br/>
     *                      The API key is used as both a synchronous locking point (e.g., only one request/key is
     *                      processed at a time) and to enable server-side caching of results and session information.
     *                      This makes subsequent requests for the same key much faster.
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
     * @param data          The raw text or HTML (is stripped) to submit via a POST to the AtD server.<br/>
     *                      If needed, it can be modified via {@link ISpellCheckerOptions#processChars(String)}
     * @return              List of misspelled words contained in the given string.
     * @throws IOException  In case of a problem or connection was aborted to the AtD server
     */
    public List<String> spellErrors(String data) throws IOException {
        List<Error> spellingErrors = new ArrayList<Error>();

        Results results = queryServer(data);
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
     * @param data          The raw text or HTML (is stripped) to submit via a POST to the AtD server.<br/>
     *                      If needed, tt can be modified via {@link ISpellCheckerOptions#processChars(String)}
     * @return              The {@link Results} of the query to the AtD server
     * @throws IOException  In case of a problem or connection was aborted to the AtD server
     */
    public Results queryServer(String data) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(atdServer);
        List <NameValuePair> nameValuePairs = new ArrayList <NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("key", apiKey));
        String text = options.processChars(data);
        nameValuePairs.add(new BasicNameValuePair("data", text));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpClient.execute(httpPost);

        String result = EntityUtils.toString(response.getEntity());

        XStream xStream = new XStream(new DomDriver());
        xStream.processAnnotations(Results.class);
        return (Results)xStream.fromXML(result);
    }
}