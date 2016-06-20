package com.neueda.homework.devops.service;

import com.neueda.homework.devops.ConsumerApplication;
import com.neueda.homework.devops.dto.Event;
import com.neueda.homework.devops.dto.ServiceInfo;
import com.neueda.homework.devops.dto.Statistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ConsumerApplication.class)
public class ConsumerControllerTest {

    @Autowired
    private ConsumerController controller;

    @Test
    public void testInfo() throws Exception {
        ServiceInfo info = controller.getInfo();

        assertThat(info).hasFieldOrPropertyWithValue("name", "consumer");
    }

    @Test
    public void testStatistics() {
        Statistics statistics1 = controller.getStatistics();

        assertThat(statistics1)
            .hasFieldOrPropertyWithValue("min", 0)
            .hasFieldOrPropertyWithValue("max", 0)
            .hasFieldOrPropertyWithValue("average", 0.0)
            .hasFieldOrPropertyWithValue("count", 0);

        Statistics statistics2 = controller.postEvent(new Event(3));

        assertThat(statistics2)
            .hasFieldOrPropertyWithValue("min", 3)
            .hasFieldOrPropertyWithValue("max", 3)
            .hasFieldOrPropertyWithValue("average", 3.0)
            .hasFieldOrPropertyWithValue("count", 1);

        controller.postEvent(new Event(5));
        controller.postEvent(new Event(1));
        controller.postEvent(new Event(2));
        Statistics statistics3 = controller.getStatistics();

        assertThat(statistics3)
            .hasFieldOrPropertyWithValue("min", 1)
            .hasFieldOrPropertyWithValue("max", 5)
            .hasFieldOrPropertyWithValue("average", 2.75)
            .hasFieldOrPropertyWithValue("count", 4);
    }

}
