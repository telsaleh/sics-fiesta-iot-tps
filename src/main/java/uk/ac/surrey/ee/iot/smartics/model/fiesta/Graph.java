
package uk.ac.surrey.ee.iot.smartics.model.fiesta;

import javax.annotation.Generated;
import javax.validation.Valid;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class Graph {

    @SerializedName("@id")
    @Expose
    private String Id;
    @SerializedName("@type")
    @Expose
    private String Type;
    @SerializedName("ssn:hasLocation")
    @Expose
    @Valid
    private SsnHasLocation ssnHasLocation;
    @SerializedName("ssn:observationResult")
    @Expose
    @Valid
    private SsnObservationResult ssnObservationResult;
    @SerializedName("ssn:observationSamplingTime")
    @Expose
    @Valid
    private SsnObservationSamplingTime ssnObservationSamplingTime;
    @SerializedName("ssn:observedBy")
    @Expose
    @Valid
    private SsnObservedBy ssnObservedBy;
    @SerializedName("ssn:observedProperty")
    @Expose
    @Valid
    private SsnObservedProperty ssnObservedProperty;
    @SerializedName("dul:hasIntervalDate")
    @Expose
    @Valid
    private DulHasIntervalDate dulHasIntervalDate;
    @SerializedName("geo:lat")
    @Expose
    @Valid
    private GeoLat geoLat;
    @SerializedName("geo:long")
    @Expose
    @Valid
    private GeoLong geoLong;
    @SerializedName("ssn:hasValue")
    @Expose
    @Valid
    private SsnHasValue ssnHasValue;
    @SerializedName("iot-lite:hasUnit")
    @Expose
    @Valid
    private IotLiteHasUnit iotLiteHasUnit;
    @SerializedName("dul:hasDataValue")
    @Expose
    @Valid
    private DulHasDataValue dulHasDataValue;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Graph() {
    }

    /**
     * 
     * @param dulHasDataValue
     * @param geoLat
     * @param iotLiteHasUnit
     * @param ssnHasLocation
     * @param Type
     * @param dulHasIntervalDate
     * @param ssnObservationResult
     * @param geoLong
     * @param Id
     * @param ssnObservedProperty
     * @param ssnObservedBy
     * @param ssnObservationSamplingTime
     * @param ssnHasValue
     */
    public Graph(String Id, String Type, SsnHasLocation ssnHasLocation, SsnObservationResult ssnObservationResult, SsnObservationSamplingTime ssnObservationSamplingTime, SsnObservedBy ssnObservedBy, SsnObservedProperty ssnObservedProperty, DulHasIntervalDate dulHasIntervalDate, GeoLat geoLat, GeoLong geoLong, SsnHasValue ssnHasValue, IotLiteHasUnit iotLiteHasUnit, DulHasDataValue dulHasDataValue) {
        this.Id = Id;
        this.Type = Type;
        this.ssnHasLocation = ssnHasLocation;
        this.ssnObservationResult = ssnObservationResult;
        this.ssnObservationSamplingTime = ssnObservationSamplingTime;
        this.ssnObservedBy = ssnObservedBy;
        this.ssnObservedProperty = ssnObservedProperty;
        this.dulHasIntervalDate = dulHasIntervalDate;
        this.geoLat = geoLat;
        this.geoLong = geoLong;
        this.ssnHasValue = ssnHasValue;
        this.iotLiteHasUnit = iotLiteHasUnit;
        this.dulHasDataValue = dulHasDataValue;
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
     *     The ssnHasLocation
     */
    public SsnHasLocation getSsnHasLocation() {
        return ssnHasLocation;
    }

    /**
     * 
     * @param ssnHasLocation
     *     The ssn:hasLocation
     */
    public void setSsnHasLocation(SsnHasLocation ssnHasLocation) {
        this.ssnHasLocation = ssnHasLocation;
    }

    /**
     * 
     * @return
     *     The ssnObservationResult
     */
    public SsnObservationResult getSsnObservationResult() {
        return ssnObservationResult;
    }

    /**
     * 
     * @param ssnObservationResult
     *     The ssn:observationResult
     */
    public void setSsnObservationResult(SsnObservationResult ssnObservationResult) {
        this.ssnObservationResult = ssnObservationResult;
    }

    /**
     * 
     * @return
     *     The ssnObservationSamplingTime
     */
    public SsnObservationSamplingTime getSsnObservationSamplingTime() {
        return ssnObservationSamplingTime;
    }

    /**
     * 
     * @param ssnObservationSamplingTime
     *     The ssn:observationSamplingTime
     */
    public void setSsnObservationSamplingTime(SsnObservationSamplingTime ssnObservationSamplingTime) {
        this.ssnObservationSamplingTime = ssnObservationSamplingTime;
    }

    /**
     * 
     * @return
     *     The ssnObservedBy
     */
    public SsnObservedBy getSsnObservedBy() {
        return ssnObservedBy;
    }

    /**
     * 
     * @param ssnObservedBy
     *     The ssn:observedBy
     */
    public void setSsnObservedBy(SsnObservedBy ssnObservedBy) {
        this.ssnObservedBy = ssnObservedBy;
    }

    /**
     * 
     * @return
     *     The ssnObservedProperty
     */
    public SsnObservedProperty getSsnObservedProperty() {
        return ssnObservedProperty;
    }

    /**
     * 
     * @param ssnObservedProperty
     *     The ssn:observedProperty
     */
    public void setSsnObservedProperty(SsnObservedProperty ssnObservedProperty) {
        this.ssnObservedProperty = ssnObservedProperty;
    }

    /**
     * 
     * @return
     *     The dulHasIntervalDate
     */
    public DulHasIntervalDate getDulHasIntervalDate() {
        return dulHasIntervalDate;
    }

    /**
     * 
     * @param dulHasIntervalDate
     *     The dul:hasIntervalDate
     */
    public void setDulHasIntervalDate(DulHasIntervalDate dulHasIntervalDate) {
        this.dulHasIntervalDate = dulHasIntervalDate;
    }

    /**
     * 
     * @return
     *     The geoLat
     */
    public GeoLat getGeoLat() {
        return geoLat;
    }

    /**
     * 
     * @param geoLat
     *     The geo:lat
     */
    public void setGeoLat(GeoLat geoLat) {
        this.geoLat = geoLat;
    }

    /**
     * 
     * @return
     *     The geoLong
     */
    public GeoLong getGeoLong() {
        return geoLong;
    }

    /**
     * 
     * @param geoLong
     *     The geo:long
     */
    public void setGeoLong(GeoLong geoLong) {
        this.geoLong = geoLong;
    }

    /**
     * 
     * @return
     *     The ssnHasValue
     */
    public SsnHasValue getSsnHasValue() {
        return ssnHasValue;
    }

    /**
     * 
     * @param ssnHasValue
     *     The ssn:hasValue
     */
    public void setSsnHasValue(SsnHasValue ssnHasValue) {
        this.ssnHasValue = ssnHasValue;
    }

    /**
     * 
     * @return
     *     The iotLiteHasUnit
     */
    public IotLiteHasUnit getIotLiteHasUnit() {
        return iotLiteHasUnit;
    }

    /**
     * 
     * @param iotLiteHasUnit
     *     The iot-lite:hasUnit
     */
    public void setIotLiteHasUnit(IotLiteHasUnit iotLiteHasUnit) {
        this.iotLiteHasUnit = iotLiteHasUnit;
    }

    /**
     * 
     * @return
     *     The dulHasDataValue
     */
    public DulHasDataValue getDulHasDataValue() {
        return dulHasDataValue;
    }

    /**
     * 
     * @param dulHasDataValue
     *     The dul:hasDataValue
     */
    public void setDulHasDataValue(DulHasDataValue dulHasDataValue) {
        this.dulHasDataValue = dulHasDataValue;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
