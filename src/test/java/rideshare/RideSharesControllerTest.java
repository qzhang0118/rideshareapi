package rideshare;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RideSharesController.class)
public class RideSharesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RideSharesRepository rideSharesRepository;

    @Test
    public void testSearchAvailableRide_repositoryNoResult_noResult() throws Exception {
        when(rideSharesRepository.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(get("/rideshares/searchAvailableRide"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(new ArrayList<RideShares>())));
    }

    @Test
    public void testSearchAvailableRide_multipleResults_sameResults() throws Exception {
        RideShares one = new RideShares("testOne", "waterloo", "toronto", "2019-01-03", 4);
        RideShares two = new RideShares("testTwo", "hamilton", "ottawa", "2018-03-14", 2);

        one.id = new ObjectId();
        one.bookUserId = null;
        two.id = new ObjectId();
        two.bookUserId = null;

        when(rideSharesRepository.findAll()).thenReturn(Arrays.asList(one, two));

        this.mockMvc.perform(get("/rideshares/searchAvailableRide"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json; charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userId", is("testOne")))
                .andExpect(jsonPath("$[0].departure", is("waterloo")))
                .andExpect(jsonPath("$[0].destination", is("toronto")))
                .andExpect(jsonPath("$[0].time", is("2019-01-03")))
                .andExpect(jsonPath("$[0].passengers", is(4)))
                .andExpect(jsonPath("$[1].userId", is("testTwo")))
                .andExpect(jsonPath("$[1].departure", is("hamilton")))
                .andExpect(jsonPath("$[1].destination", is("ottawa")))
                .andExpect(jsonPath("$[1].time", is("2018-03-14")))
                .andExpect(jsonPath("$[1].passengers", is(2)));

        verify(rideSharesRepository, times(1)).findAll();

        verifyNoMoreInteractions(rideSharesRepository);
    }
}