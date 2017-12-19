package com.cardreader.demo.Controller;

import com.cardreader.demo.SoftwareArchitectureApplication;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SoftwareArchitectureApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ValidationControllerTest {

    private  MockMvc mockMvc;
    private static String card_1 = "846609c6-ce9c-41a2-b64d-ba8066a376af";
    private static String card_2 = "846609c6-ce9c-41a2-b64d-ba8066a376pd";


    private static String cit_lib_north = "7907775e-15ac-415f-a99c-e978856c8ec0";
    private static String cit_lib_west = "580ddc98-0db9-473d-a721-348f353f1d2b";
    private static String standford_ford_center = "287d07d6-e243-4947-807d-74ec80f57427";



    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }
/** Simple initial test, as DB is entry the previous event will not exist on start up,
 *  so the events are for the CIT Library North exit. This should result in a positive
 *  response.
 */
    @Test
    public void test1InitialSwipe_CIT_Library_North() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/panels/request").contentType(MediaType.APPLICATION_JSON)
                .param("panelid",cit_lib_north)
                .param("cardid",card_2)
                .param("allowed","true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.reason").value("Valid event."))
                .andExpect(jsonPath("$.currentEvent").exists())
                .andExpect(jsonPath("$.currentEvent.panelId").exists())
                .andExpect(jsonPath("$.currentEvent.cardId").exists())
                .andExpect(jsonPath("$.currentEvent.timestamp").exists())
                .andExpect(jsonPath("$.currentEvent.location").exists())
                .andExpect(jsonPath("$.currentEvent.location.coordinates").exists())
                .andExpect(jsonPath("$.currentEvent.location.coordinates.longitude").exists())
                .andExpect(jsonPath("$.currentEvent.location.coordinates.latitude").exists())
                .andExpect(jsonPath("$.currentEvent.location.altitude").exists())
                .andExpect(jsonPath("$.currentEvent.location.relativeLocation").exists())
                .andExpect(jsonPath("$.currentEvent.accessAllowed").exists())
                .andExpect(jsonPath("$.previousEvent").doesNotExist())
                .andExpect(jsonPath("$.previousEvent.panelId").doesNotExist())
                .andExpect(jsonPath("$.previousEvent.cardId").doesNotExist())
                .andExpect(jsonPath("$.previousEvent.timestamp").doesNotExist())
                .andExpect(jsonPath("$.previousEvent.location").doesNotExist())
                .andExpect(jsonPath("$.previousEvent.location.coordinates").doesNotExist())
                .andExpect(jsonPath("$.previousEvent.location.coordinates.longitude").doesNotExist())
                .andExpect(jsonPath("$.previousEvent.location.coordinates.latitude").doesNotExist())
                .andExpect(jsonPath("$.previousEvent.location.altitude").doesNotExist())
                .andExpect(jsonPath("$.previousEvent.location.relativeLocation").doesNotExist())
                .andExpect(jsonPath("$.previousEvent.accessAllowed").doesNotExist())
                .andExpect(jsonPath("$.validEvent").exists())
                .andDo(print());
    }

    @Test
    public void test2SecondSwipe_CIT_Library_West() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/panels/request").contentType(MediaType.APPLICATION_JSON)
                .param("panelid",cit_lib_west)
                .param("cardid",card_2)
                .param("allowed","true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.reason").exists())
                .andExpect(jsonPath("$.currentEvent").exists())
                .andExpect(jsonPath("$.currentEvent.panelId").exists())
                .andExpect(jsonPath("$.currentEvent.cardId").exists())
                .andExpect(jsonPath("$.currentEvent.timestamp").exists())
                .andExpect(jsonPath("$.currentEvent.location").exists())
                .andExpect(jsonPath("$.currentEvent.location.coordinates").exists())
                .andExpect(jsonPath("$.currentEvent.location.coordinates.longitude").exists())
                .andExpect(jsonPath("$.currentEvent.location.coordinates.latitude").exists())
                .andExpect(jsonPath("$.currentEvent.location.altitude").exists())
                .andExpect(jsonPath("$.currentEvent.location.relativeLocation").exists())
                .andExpect(jsonPath("$.currentEvent.accessAllowed").exists())
                .andExpect(jsonPath("$.previousEvent").exists())
                .andExpect(jsonPath("$.previousEvent.panelId").exists())
                .andExpect(jsonPath("$.previousEvent.cardId").exists())
                .andExpect(jsonPath("$.previousEvent.timestamp").exists())
                .andExpect(jsonPath("$.previousEvent.location").exists())
                .andExpect(jsonPath("$.previousEvent.location.coordinates").exists())
                .andExpect(jsonPath("$.previousEvent.location.coordinates.longitude").exists())
                .andExpect(jsonPath("$.previousEvent.location.coordinates.latitude").exists())
                .andExpect(jsonPath("$.previousEvent.location.altitude").exists())
                .andExpect(jsonPath("$.previousEvent.location.relativeLocation").exists())
                .andExpect(jsonPath("$.previousEvent.accessAllowed").exists())
                .andExpect(jsonPath("$.validEvent").value("false"))
                .andDo(print());
    }
    @Test
    public void test3ThirdSwipe_CIT_Library_North() throws Exception {
        try {
            //used to mimic walking  from one panel to the next
            Thread.sleep(123000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mockMvc.perform(MockMvcRequestBuilders.get("/api/panels/request").contentType(MediaType.APPLICATION_JSON)
                .param("panelid",cit_lib_north)
                .param("cardid",card_2)
                .param("allowed","true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.reason").exists())
                .andExpect(jsonPath("$.currentEvent").exists())
                .andExpect(jsonPath("$.currentEvent.panelId").exists())
                .andExpect(jsonPath("$.currentEvent.cardId").exists())
                .andExpect(jsonPath("$.currentEvent.timestamp").exists())
                .andExpect(jsonPath("$.currentEvent.location").exists())
                .andExpect(jsonPath("$.currentEvent.location.coordinates").exists())
                .andExpect(jsonPath("$.currentEvent.location.coordinates.longitude").exists())
                .andExpect(jsonPath("$.currentEvent.location.coordinates.latitude").exists())
                .andExpect(jsonPath("$.currentEvent.location.altitude").exists())
                .andExpect(jsonPath("$.currentEvent.location.relativeLocation").exists())
                .andExpect(jsonPath("$.currentEvent.accessAllowed").exists())
                .andExpect(jsonPath("$.previousEvent").exists())
                .andExpect(jsonPath("$.previousEvent.panelId").exists())
                .andExpect(jsonPath("$.previousEvent.cardId").exists())
                .andExpect(jsonPath("$.previousEvent.timestamp").exists())
                .andExpect(jsonPath("$.previousEvent.location").exists())
                .andExpect(jsonPath("$.previousEvent.location.coordinates").exists())
                .andExpect(jsonPath("$.previousEvent.location.coordinates.longitude").exists())
                .andExpect(jsonPath("$.previousEvent.location.coordinates.latitude").exists())
                .andExpect(jsonPath("$.previousEvent.location.altitude").exists())
                .andExpect(jsonPath("$.previousEvent.location.relativeLocation").exists())
                .andExpect(jsonPath("$.previousEvent.accessAllowed").exists())
                .andExpect(jsonPath("$.validEvent").value("true"))
                .andDo(print());
    }

    @Test
    public void test4FourthSwipe_Overseas() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/panels/request").contentType(MediaType.APPLICATION_JSON)
                .param("panelid",standford_ford_center)
                .param("cardid",card_2)
                .param("allowed","true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.reason").value("Impossible time-distance event."))
                .andExpect(jsonPath("$.currentEvent").exists())
                .andExpect(jsonPath("$.currentEvent.panelId").exists())
                .andExpect(jsonPath("$.currentEvent.cardId").exists())
                .andExpect(jsonPath("$.currentEvent.timestamp").exists())
                .andExpect(jsonPath("$.currentEvent.location").exists())
                .andExpect(jsonPath("$.currentEvent.location.coordinates").exists())
                .andExpect(jsonPath("$.currentEvent.location.coordinates.longitude").exists())
                .andExpect(jsonPath("$.currentEvent.location.coordinates.latitude").exists())
                .andExpect(jsonPath("$.currentEvent.location.altitude").exists())
                .andExpect(jsonPath("$.currentEvent.location.relativeLocation").exists())
                .andExpect(jsonPath("$.currentEvent.accessAllowed").exists())
                .andExpect(jsonPath("$.previousEvent").exists())
                .andExpect(jsonPath("$.previousEvent.panelId").exists())
                .andExpect(jsonPath("$.previousEvent.cardId").exists())
                .andExpect(jsonPath("$.previousEvent.timestamp").exists())
                .andExpect(jsonPath("$.previousEvent.location").exists())
                .andExpect(jsonPath("$.previousEvent.location.coordinates").exists())
                .andExpect(jsonPath("$.previousEvent.location.coordinates.longitude").exists())
                .andExpect(jsonPath("$.previousEvent.location.coordinates.latitude").exists())
                .andExpect(jsonPath("$.previousEvent.location.altitude").exists())
                .andExpect(jsonPath("$.previousEvent.location.relativeLocation").exists())
                .andExpect(jsonPath("$.previousEvent.accessAllowed").exists())
                .andExpect(jsonPath("$.validEvent").value("false"))
                .andDo(print());
    }




}
