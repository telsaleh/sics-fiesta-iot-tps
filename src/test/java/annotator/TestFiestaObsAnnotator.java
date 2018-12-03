/*
 * Copyright (C) 2018 te0003
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
package annotator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import org.junit.Test;
import uk.ac.surrey.ee.iot.smartics.annotator.fiesta.FiestaObsAnnotator;
import uk.ac.surrey.ee.iot.smartics.model.data.ics.Observations;

/**
 *
 * @author te0003
 */
public class TestFiestaObsAnnotator {
    
    @Test
    public void serialize() throws JAXBException, IOException {

        String test = "{\n"
                + "	\"Observations\": [{\n"
                + "		\"iot-type\": \"EnergyMeter\",\n"
                + "		\"timestamp\": \"2016-12-23T18:56:00.001\",\n"
                + "		\"mobile\": \"False\",\n"
                + "		\"resourceId\": \"sc-sics-sp-001-Power\",\n"
                + "		\"dataValue\": \"0.297018\",\n"
                + "		\"measurement\": \"Watts\",\n"
                + "		\"lon\": \"-0.1\",\n"
                + "		\"system\": \"smart-ics\",\n"
                + "		\"relativeAlt\": \"2\",\n"
                + "		\"platform\": \"desk-ICS-02-18\",\n"
                + "		\"deviceId\": \"sc-sics-sp-001\",\n"
                + "		\"deployment\": \"unis-smart-campus\",\n"
                + "		\"lat\": \"51.1\",\n"
                + "		\"qk\": \"Power\",\n"
                + "		\"alt\": \"53\",\n"
                + "		\"unit\": \"Watt\"\n"
                + "	}]\n"
                + "}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        Observations obs = objectMapper.readValue(test, Observations.class);

//        Observation ob = new Observation("sc-si-sc-001-temp", "sc-si-sc-001", "smart-ics", "desk-ICS-02-18", "false", "unis-smart-campus", "sensor", "AmbientTemperature", "Celsius", "automatic", "51.1", "-0.1", "50", "2", "2002-05-30T09:30:10.5", "23.1");
//        ArrayList<Observation> obsArray = new ArrayList<>();
//        obsArray.add(ob);
//        Observations obs = new Observations(); 
//        obs.setObservations(obsArray);
        FiestaObsAnnotator ri = new FiestaObsAnnotator();
        String result = ri.annotateObservations(obs);
        System.out.println(result);
    }
    
}
