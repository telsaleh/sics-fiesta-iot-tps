/*
 * Copyright (C) 2017 te0003
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.surrey.ee.iot.smartics.model.fiesta.message;

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