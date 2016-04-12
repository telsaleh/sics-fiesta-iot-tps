
package uk.ac.surrey.ee.iot.smartics.model.fiesta;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class DulHasIntervalDate {

    @SerializedName("@type")
    @Expose
    private String Type;
    @SerializedName("@value")
    @Expose
    private String Value;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DulHasIntervalDate() {
    }

    /**
     * 
     * @param Value
     * @param Type
     */
    public DulHasIntervalDate(String Type, String Value) {
        this.Type = Type;
        this.Value = Value;
    }

    /**
     * 
     * @return
     *     The Type
     */
    public String getType() {
        return Type;
    }

    /**
     * 
     * @param Type
     *     The @type
     */
    public void setType(String Type) {
        this.Type = Type;
    }

    /**
     * 
     * @return
     *     The Value
     */
    public String getValue() {
        return Value;
    }

    /**
     * 
     * @param Value
     *     The @value
     */
    public void setValue(String Value) {
        this.Value = Value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
