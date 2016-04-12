
package uk.ac.surrey.ee.iot.smartics.model.fiesta;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.validation.Valid;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class IotNodeReading {

    @SerializedName("@context")
    @Expose
    @Valid
    private uk.ac.surrey.ee.iot.smartics.model.fiesta.Context Context;
    @SerializedName("@graph")
    @Expose
    @Valid
    private List<uk.ac.surrey.ee.iot.smartics.model.fiesta.Graph> Graph = new ArrayList<uk.ac.surrey.ee.iot.smartics.model.fiesta.Graph>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public IotNodeReading() {
    }

    /**
     * 
     * @param Context
     * @param Graph
     */
    public IotNodeReading(uk.ac.surrey.ee.iot.smartics.model.fiesta.Context Context, List<uk.ac.surrey.ee.iot.smartics.model.fiesta.Graph> Graph) {
        this.Context = Context;
        this.Graph = Graph;
    }

    /**
     * 
     * @return
     *     The Context
     */
    public uk.ac.surrey.ee.iot.smartics.model.fiesta.Context getContext() {
        return Context;
    }

    /**
     * 
     * @param Context
     *     The @context
     */
    public void setContext(uk.ac.surrey.ee.iot.smartics.model.fiesta.Context Context) {
        this.Context = Context;
    }

    /**
     * 
     * @return
     *     The Graph
     */
    public List<uk.ac.surrey.ee.iot.smartics.model.fiesta.Graph> getGraph() {
        return Graph;
    }

    /**
     * 
     * @param Graph
     *     The @graph
     */
    public void setGraph(List<uk.ac.surrey.ee.iot.smartics.model.fiesta.Graph> Graph) {
        this.Graph = Graph;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
