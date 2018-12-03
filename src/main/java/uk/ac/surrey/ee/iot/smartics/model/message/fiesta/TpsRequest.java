package uk.ac.surrey.ee.iot.smartics.model.message.fiesta;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"sensorIDs",
"startDate",
"stopDate"
})
public class TpsRequest implements Serializable
{

@JsonProperty("sensorIDs")
private List<String> sensorIDs = null;
@JsonProperty("startDate")
private String startDate;
@JsonProperty("stopDate")
private String stopDate;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();
private final static long serialVersionUID = 4112759670764298942L;

/**
* No args constructor for use in serialization
* 
*/
public TpsRequest() {
}

/**
* 
* @param startDate
* @param stopDate
* @param sensorIDs
*/
public TpsRequest(List<String> sensorIDs, String startDate, String stopDate) {
super();
this.sensorIDs = sensorIDs;
this.startDate = startDate;
this.stopDate = stopDate;
}

@JsonProperty("sensorIDs")
public List<String> getSensorIDs() {
return sensorIDs;
}

@JsonProperty("sensorIDs")
public void setSensorIDs(List<String> sensorIDs) {
this.sensorIDs = sensorIDs;
}

@JsonProperty("startDate")
public String getStartDate() {
return startDate;
}

@JsonProperty("startDate")
public void setStartDate(String startDate) {
this.startDate = startDate;
}

@JsonProperty("stopDate")
public String getStopDate() {
return stopDate;
}

@JsonProperty("stopDate")
public void setStopDate(String stopDate) {
this.stopDate = stopDate;
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