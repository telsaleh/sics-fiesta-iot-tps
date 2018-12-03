package uk.ac.surrey.ee.iot.smartics.model.data.ics;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "iot-type",
    "mobile",
    "resourceId",
    "measurement",
    "lon",
    "system",
    "relativeAlt",
    "platform",
    "deviceId",
    "deployment",
    "lat",
    "qk",
    "alt",
    "unit"
})
public class Resource {

    @JsonProperty("iot-type")
    private String iotType;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("resourceId")
    private String resourceId;
    @JsonProperty("measurement")
    private String measurement;
    @JsonProperty("lon")
    private String lon;
    @JsonProperty("system")
    private String system;
    @JsonProperty("relativeAlt")
    private String relativeAlt;
    @JsonProperty("platform")
    private String platform;
    @JsonProperty("deviceId")
    private String deviceId;
    @JsonProperty("deployment")
    private String deployment;
    @JsonProperty("lat")
    private String lat;
    @JsonProperty("qk")
    private String qk;
    @JsonProperty("alt")
    private String alt;
    @JsonProperty("unit")
    private String unit;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Resource() {
    }

    public Resource(String resourceId, String deviceId, String system, String platform,
            String mobile, String deployment, String iotType, String qk, String unit,
            String measurement, String lat, String lon, String alt, String relativeAlt) {

        this.iotType = iotType;
        this.mobile = mobile;
        this.resourceId = resourceId;
        this.measurement = measurement;
        this.lon = lon;
        this.system = system;
        this.relativeAlt = relativeAlt;
        this.platform = platform;
        this.deviceId = deviceId;
        this.deployment = deployment;
        this.lat = lat;
        this.qk = qk;
        this.alt = alt;
        this.unit = unit;
    }

    @JsonProperty("iot-type")
    public String getIotType() {
        return iotType;
    }

    @JsonProperty("iot-type")
    public void setIotType(String iotType) {
        this.iotType = iotType;
    }

    @JsonProperty("mobile")
    public String getMobile() {
        return mobile;
    }

    @JsonProperty("mobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @JsonProperty("resourceId")
    public String getResourceId() {
        return resourceId;
    }

    @JsonProperty("resourceId")
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @JsonProperty("measurement")
    public String getMeasurement() {
        return measurement;
    }

    @JsonProperty("measurement")
    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    @JsonProperty("lon")
    public String getLon() {
        return lon;
    }

    @JsonProperty("lon")
    public void setLon(String lon) {
        this.lon = lon;
    }

    @JsonProperty("system")
    public String getSystem() {
        return system;
    }

    @JsonProperty("system")
    public void setSystem(String system) {
        this.system = system;
    }

    @JsonProperty("relativeAlt")
    public String getRelativeAlt() {
        return relativeAlt;
    }

    @JsonProperty("relativeAlt")
    public void setRelativeAlt(String relativeAlt) {
        this.relativeAlt = relativeAlt;
    }

    @JsonProperty("platform")
    public String getPlatform() {
        return platform;
    }

    @JsonProperty("platform")
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @JsonProperty("deviceId")
    public String getDeviceId() {
        return deviceId;
    }

    @JsonProperty("deviceId")
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @JsonProperty("deployment")
    public String getDeployment() {
        return deployment;
    }

    @JsonProperty("deployment")
    public void setDeployment(String deployment) {
        this.deployment = deployment;
    }

    @JsonProperty("lat")
    public String getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(String lat) {
        this.lat = lat;
    }

    @JsonProperty("qk")
    public String getQk() {
        return qk;
    }

    @JsonProperty("qk")
    public void setQk(String qk) {
        this.qk = qk;
    }

    @JsonProperty("alt")
    public String getAlt() {
        return alt;
    }

    @JsonProperty("alt")
    public void setAlt(String alt) {
        this.alt = alt;
    }

    @JsonProperty("unit")
    public String getUnit() {
        return unit;
    }

    @JsonProperty("unit")
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
