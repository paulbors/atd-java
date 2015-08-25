package ws.bors.atd.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("suggestions")
public class Suggestions {
    @XStreamImplicit private List<String> option = new ArrayList<String>();

    public List<String> getOption() {
        return option;
    }
    public void setOption(List<String> option) {
        this.option = option;
    }
}