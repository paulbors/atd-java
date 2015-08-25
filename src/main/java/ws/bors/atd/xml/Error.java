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
package ws.bors.atd.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * The AtD response <error /> XML element.
 *
 * @see <a href="http://www.AfterTheDeadline.com/api.slp">AtD API</a>
 */
@XStreamAlias("error")
public class Error {
    private String string;
    private String description;
    private String precontext;
    private String type;
    private String url;
    @XStreamImplicit private List<Suggestions> suggestions = new ArrayList<Suggestions>();

    public String getString() {
        return string;
    }
    public void setString(String string) {
        this.string = string;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrecontext() {
        return precontext;
    }
    public void setPrecontext(String precontext) {
        this.precontext = precontext;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public List<Suggestions> getSuggestions() {
        return suggestions;
    }
    public void setSuggestions(List<Suggestions> suggestions) {
        this.suggestions = suggestions;
    }
}