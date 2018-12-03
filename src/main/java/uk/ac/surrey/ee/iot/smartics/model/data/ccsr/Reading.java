package uk.ac.surrey.ee.iot.smartics.model.data.ccsr;

//
//package uk.ac.surrey.ee.iot.smartics.model.reduce;
//
//import javax.annotation.Generated;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//import org.apache.commons.lang3.builder.ToStringBuilder;
//
//@Generated("org.jsonschema2pojo")
//public class Reading {
//
//    @SerializedName("light")
//    @Expose
//    private Integer light;
//    @SerializedName("mic")
//    @Expose
//    private Integer mic;
//    @SerializedName("nodeId")
//    @Expose
//    private Integer nodeId;
//    @SerializedName("PIR")
//    @Expose
//    private Integer PIR;
//    @SerializedName("temp")
//    @Expose
//    private Integer temp;
//    @SerializedName("timeStamp")
//    @Expose
//    private String timeStamp;
//    @SerializedName("watts")
//    @Expose
//    private String watts;
//
//    /**
//     * No args constructor for use in serialization
//     * 
//     */
//    public Reading() {
//    }
//
//    /**
//     * 
//     * @param nodeId
//     * @param timeStamp
//     * @param mic
//     * @param watts
//     * @param PIR
//     * @param temp
//     * @param light
//     */
//    public Reading(Integer light, Integer mic, Integer nodeId, Integer PIR, Integer temp, String timeStamp, String watts) {
//        this.light = light;
//        this.mic = mic;
//        this.nodeId = nodeId;
//        this.PIR = PIR;
//        this.temp = temp;
//        this.timeStamp = timeStamp;
//        this.watts = watts;
//    }
//
//    /**
//     * 
//     * @return
//     *     The light
//     */
//    public Integer getLight() {
//        return light;
//    }
//
//    /**
//     * 
//     * @param light
//     *     The light
//     */
//    public void setLight(Integer light) {
//        this.light = light;
//    }
//
//    /**
//     * 
//     * @return
//     *     The mic
//     */
//    public Integer getMic() {
//        return mic;
//    }
//
//    /**
//     * 
//     * @param mic
//     *     The mic
//     */
//    public void setMic(Integer mic) {
//        this.mic = mic;
//    }
//
//    /**
//     * 
//     * @return
//     *     The nodeId
//     */
//    public Integer getNodeId() {
//        return nodeId;
//    }
//
//    /**
//     * 
//     * @param nodeId
//     *     The nodeId
//     */
//    public void setNodeId(Integer nodeId) {
//        this.nodeId = nodeId;
//    }
//
//    /**
//     * 
//     * @return
//     *     The PIR
//     */
//    public Integer getPIR() {
//        return PIR;
//    }
//
//    /**
//     * 
//     * @param PIR
//     *     The PIR
//     */
//    public void setPIR(Integer PIR) {
//        this.PIR = PIR;
//    }
//
//    /**
//     * 
//     * @return
//     *     The temp
//     */
//    public Integer getTemp() {
//        return temp;
//    }
//
//    /**
//     * 
//     * @param temp
//     *     The temp
//     */
//    public void setTemp(Integer temp) {
//        this.temp = temp;
//    }
//
//    /**
//     * 
//     * @return
//     *     The timeStamp
//     */
//    public String getTimeStamp() {
//        return timeStamp;
//    }
//
//    /**
//     * 
//     * @param timeStamp
//     *     The timeStamp
//     */
//    public void setTimeStamp(String timeStamp) {
//        this.timeStamp = timeStamp;
//    }
//
//    /**
//     * 
//     * @return
//     *     The watts
//     */
//    public String getWatts() {
//        return watts;
//    }
//
//    /**
//     * 
//     * @param watts
//     *     The watts
//     */
//    public void setWatts(String watts) {
//        this.watts = watts;
//    }
//
//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this);
//    }
//
//}
