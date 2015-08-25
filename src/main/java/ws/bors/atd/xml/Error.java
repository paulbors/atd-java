package ws.bors.atd.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

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