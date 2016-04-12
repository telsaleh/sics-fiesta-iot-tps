
package uk.ac.surrey.ee.iot.smartics.model.fiesta;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class Context {

    @SerializedName("xsd")
    @Expose
    private String xsd;
    @SerializedName("dul")
    @Expose
    private String dul;
    @SerializedName("ssn")
    @Expose
    private String ssn;
    @SerializedName("iot-lite")
    @Expose
    private String iotLite;
    @SerializedName("geo")
    @Expose
    private String geo;
    @SerializedName("qu-unit")
    @Expose
    private String quUnit;
    @SerializedName("qu-quantity")
    @Expose
    private String quQuantity;
    @SerializedName("m3-lite")
    @Expose
    private String m3Lite;
    @SerializedName("fiesta-iot-srd")
    @Expose
    private String fiestaIotSrd;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Context() {
        
        this.xsd = "http://www.w3.org/2001/XMLSchema#";
        this.dul = "http://www.loa-cnr.it/ontologies/DUL.owl#";
        this.ssn = "http://www.w3.org/2005/Incubator/ssn/ssnx/ssn#";
        this.iotLite = "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#";
        this.geo = "http://www.w3.org/2003/01/geo/wgs84_pos#";
        this.quUnit = "http://purl.oclc.org/NET/ssnx/qu/unit#";
        this.quQuantity = "http://purl.oclc.org/NET/ssnx/qu/quantity#";
        this.m3Lite = "http://purl.org/iot/vocab/m3-lite#";
        this.fiestaIotSrd = "http://purl.oclc.org/NET/FIESTA-IoT/srd#";
    }

    /**
     * 
     * @param m3Lite
     * @param quQuantity
     * @param geo
     * @param iotLite
     * @param quUnit
     * @param fiestaIotSrd
     * @param dul
     * @param ssn
     * @param xsd
     */
    public Context(String xsd, String dul, String ssn, String iotLite, String geo, String quUnit, String quQuantity, String m3Lite, String fiestaIotSrd) {
        this.xsd = xsd;
        this.dul = dul;
        this.ssn = ssn;
        this.iotLite = iotLite;
        this.geo = geo;
        this.quUnit = quUnit;
        this.quQuantity = quQuantity;
        this.m3Lite = m3Lite;
        this.fiestaIotSrd = fiestaIotSrd;
    }

    /**
     * 
     * @return
     *     The xsd
     */
    public String getXsd() {
        return xsd;
    }

    /**
     * 
     * @param xsd
     *     The xsd
     */
    public void setXsd(String xsd) {
        this.xsd = xsd;
    }

    /**
     * 
     * @return
     *     The dul
     */
    public String getDul() {
        return dul;
    }

    /**
     * 
     * @param dul
     *     The dul
     */
    public void setDul(String dul) {
        this.dul = dul;
    }

    /**
     * 
     * @return
     *     The ssn
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * 
     * @param ssn
     *     The ssn
     */
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    /**
     * 
     * @return
     *     The iotLite
     */
    public String getIotLite() {
        return iotLite;
    }

    /**
     * 
     * @param iotLite
     *     The iot-lite
     */
    public void setIotLite(String iotLite) {
        this.iotLite = iotLite;
    }

    /**
     * 
     * @return
     *     The geo
     */
    public String getGeo() {
        return geo;
    }

    /**
     * 
     * @param geo
     *     The geo
     */
    public void setGeo(String geo) {
        this.geo = geo;
    }

    /**
     * 
     * @return
     *     The quUnit
     */
    public String getQuUnit() {
        return quUnit;
    }

    /**
     * 
     * @param quUnit
     *     The qu-unit
     */
    public void setQuUnit(String quUnit) {
        this.quUnit = quUnit;
    }

    /**
     * 
     * @return
     *     The quQuantity
     */
    public String getQuQuantity() {
        return quQuantity;
    }

    /**
     * 
     * @param quQuantity
     *     The qu-quantity
     */
    public void setQuQuantity(String quQuantity) {
        this.quQuantity = quQuantity;
    }

    /**
     * 
     * @return
     *     The m3Lite
     */
    public String getM3Lite() {
        return m3Lite;
    }

    /**
     * 
     * @param m3Lite
     *     The m3-lite
     */
    public void setM3Lite(String m3Lite) {
        this.m3Lite = m3Lite;
    }

    /**
     * 
     * @return
     *     The fiestaIotSrd
     */
    public String getFiestaIotSrd() {
        return fiestaIotSrd;
    }

    /**
     * 
     * @param fiestaIotSrd
     *     The fiesta-iot-srd
     */
    public void setFiestaIotSrd(String fiestaIotSrd) {
        this.fiestaIotSrd = fiestaIotSrd;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
