package ws.bors.atd.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("results")
public class Results {
    @XStreamImplicit private List<Error> error = new ArrayList<Error>();

    public List<Error> getError() {
        return error;
    }
    public void setError(List<Error> error) {
        this.error = error;
    }
}