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
import uk.ac.surrey.ee.iot.smartics.annotator.fiesta.FiestaResAnnotator;
import uk.ac.surrey.ee.iot.smartics.model.data.ics.Resources;

/**
 *
 * @author te0003
 */
public class TestFiestaResAnnotator {
    
    @Test
    public void serialize() throws JAXBException, IOException {

        String test = "{\n"
                + "	\"Resources\": [{\n"
                + "		\"iot-type\": \"EnergyMeter\",\n"
                + "		\"mobile\": \"False\",\n"
                + "		\"resourceId\": \"sc-sics-sp-001-power\",\n"
                + "		\"measurement\": \"Watts\",\n"
                + "		\"lon\": \"-0.1\",\n"
                + "		\"system\": \"smart-ics\",\n"
                + "		\"relativeAlt\": \"2\",\n"
                + "		\"platform\": \"desk-ICS-02-18\",\n"
                + "		\"deviceId\": \"sc-sics-sp-001\",\n"
                + "		\"deployment\": \"unis-smart-campus\",\n"
                + "		\"lat\": \"51.1\",\n"
                + "		\"qk\": \"ChemicalAgentAtmosphericConcentrationCO\",\n"
                + "		\"alt\": \"53\",\n"
                + "		\"unit\": \"Watt\"\n"
                + "	}]\n"
                + "}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        Resources res = objectMapper.readValue(test, Resources.class);

        FiestaResAnnotator ri = new FiestaResAnnotator();
        String result = ri.annotateResources(res);
        System.out.println(result);
    }
    
}
