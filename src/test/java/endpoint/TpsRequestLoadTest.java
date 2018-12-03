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
package endpoint;

/**
 *
 * @author te0003
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import org.junit.Test;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ServerResource;
import uk.ac.surrey.ee.iot.smartics.endpoint.restlet.resource.TpsHandler;

public class TpsRequestLoadTest extends ServerResource {

    public String DEVICE = "de";
    public String QK = "light";
    public int SET_NUMBER = 10;

    @Test
    public void loadTest() throws JAXBException, IOException {

        String test = "{"+ "\n" + "  \"sensorIDs\": [";
        
        TpsRequestLoadTest rlt = new TpsRequestLoadTest();

        for (int count = 1; count <= rlt.SET_NUMBER; count++) {

            String countFormatted = String.format("%03d", count);
            String uri = "\"http://smart-ics.ee.surrey.ac.uk/fiesta-iot/resource/sc-sics-" + rlt.DEVICE + "-" + countFormatted + "-" + rlt.QK + "\"";
            test = test + uri;
            if (count != rlt.SET_NUMBER) {
                test = test + ",";
            }
        }

        test = test + "]\n}";

        System.out.println(test);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        TpsRequest tpsReq = objectMapper.readValue(test, TpsRequest.class);
        TpsHandler tpsH = new TpsHandler();
        Representation rep = new StringRepresentation(test);
        Representation response = tpsH.handlePost(rep);        
        System.out.print(response.getText());
        
//        FiestaObsAnnotator ri = new FiestaObsAnnotator();
//        String result = ri.annotateObservations(obs);
//        System.out.println(result);
    }

}
