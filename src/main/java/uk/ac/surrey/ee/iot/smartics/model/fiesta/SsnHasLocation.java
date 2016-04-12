
package uk.ac.surrey.ee.iot.smartics.model.fiesta;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class SsnHasLocation {

    @SerializedName("@id")
    @Expose
    private String Id;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SsnHasLocation() {
    }

    /**
     * 
     * @param Id
     */
    public SsnHasLocation(String Id) {
        this.Id = Id;
    }

    /**
     * 
     * @return
     *     The Id
     */
    public String getId() {
        return Id;
    }

    /**
     * 
     * @param Id
     *     The @id
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
