
package uk.ac.surrey.ee.iot.smartics.model.fiesta;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class DulHasDataValue {

    @SerializedName("@type")
    @Expose
    private String Type;
    @SerializedName("@value")
    @Expose
    private Double Value;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DulHasDataValue() {
    }

    /**
     * 
     * @param Value
     * @param Type
     */
    public DulHasDataValue(String Type, Double Value) {
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
    public Double getValue() {
        return Value;
    }

    /**
     * 
     * @param Value
     *     The @value
     */
    public void setValue(Double Value) {
        this.Value = Value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
