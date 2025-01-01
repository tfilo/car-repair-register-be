package sk.tope.car_repair_register.api.controller;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import sk.tope.car_repair_register.api.service.so.CustomerCreateSo;
import sk.tope.car_repair_register.api.service.so.CustomerSo;
import sk.tope.car_repair_register.api.service.so.CustomerUpdateSo;
import sk.tope.car_repair_register.common.TestBase;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerIT extends TestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerControllerIT.class);

    @Test
    public void testCreateCustomer() throws Exception {
        CustomerCreateSo so = new CustomerCreateSo(
                "  Test",
                " User  ",
                " 0900000123",
                " mail@mail.mail  "
        );
        MvcResult mvcResult = mockMvc.perform(post("/customer")
                        .content(asJsonString(so))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser()))
                .andExpect(status().isCreated()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isNotNull();
        CustomerSo result = asJsonObject(mvcResult.getResponse().getContentAsString(), CustomerSo.class);
        assertThat(result.id()).isNotNull();
        assertThat(result.name()).isEqualTo(so.name().trim());
        assertThat(result.surname()).isEqualTo(so.surname().trim());
        assertThat(result.mobile()).isEqualTo(so.mobile().trim());
        assertThat(result.email()).isEqualTo(so.email().trim());
        assertThat(result.created()).isNotNull();
        assertThat(result.modified()).isNull();
    }

    @Test
    public void testGetCustomerById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/customer/1000")
                        .with(getUser()))
                .andExpect(status().isOk()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isNotNull();
        CustomerSo result = asJsonObject(mvcResult.getResponse().getContentAsString(), CustomerSo.class);
        assertThat(result.id()).isEqualTo(1000);
        assertThat(result.name()).isEqualTo("First");
        assertThat(result.surname()).isEqualTo("Person");
        assertThat(result.mobile()).isEqualTo("0900000001");
        assertThat(result.email()).isEqualTo("first@person.com");
        assertThat(result.created()).isEqualToIgnoringNanos(LocalDateTime.of(2020, 1, 1, 8, 0));
        assertThat(result.modified()).isNull();

        mockMvc.perform(get("/customer/999")
                        .with(getUser()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindCustomers() throws Exception {
        mockMvc.perform(get("/customer?query=person")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("sort", "name")
                        .with(getUser()))
                .andExpect(jsonPath("$.page.totalElements").value("5"))
                .andExpect(jsonPath("$.content[0].name").value("Fifth"))
                .andExpect(jsonPath("$.content[1].name").value("First"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/customer?query=fir")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("sort", "name")
                        .with(getUser()))
                .andExpect(jsonPath("$.page.totalElements").value("1"))
                .andExpect(jsonPath("$.content[0].name").value("First"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/customer?query=person.com")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("sort", "name")
                        .with(getUser()))
                .andExpect(jsonPath("$.page.totalElements").value("5"));

        mockMvc.perform(get("/customer?query=09000")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("sort", "name")
                        .with(getUser()))
                .andExpect(jsonPath("$.page.totalElements").value("5"));

        mockMvc.perform(get("/customer?query=Second Person")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("sort", "name")
                        .with(getUser()))
                .andExpect(jsonPath("$.page.totalElements").value("1"))
                .andExpect(jsonPath("$.content[0].name").value("Second"));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        CustomerUpdateSo so = new CustomerUpdateSo(
                " Test  ",
                "   User",
                "111111   ",
                "new@mail.com   "
        );
        MvcResult mvcResult = mockMvc.perform(put("/customer/1000")
                        .content(asJsonString(so))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser()))
                .andExpect(status().isOk()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isNotNull();
        CustomerSo result = asJsonObject(mvcResult.getResponse().getContentAsString(), CustomerSo.class);
        assertThat(result.id()).isEqualTo(1000);
        assertThat(result.name()).isEqualTo(so.name().trim());
        assertThat(result.surname()).isEqualTo(so.surname().trim());
        assertThat(result.mobile()).isEqualTo(so.mobile().trim());
        assertThat(result.email()).isEqualTo(so.email().trim());
        assertThat(result.created()).isNotNull();
        assertThat(result.modified()).isNotNull();

        mockMvc.perform(put("/customer/999")
                        .content(asJsonString(so))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete("/customer/1001")
                        .with(getUser()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/customer/1001")
                        .with(getUser()))
                .andExpect(status().isNotFound());

        // vehicles of customer should be deleted too
        mockMvc.perform(get("/vehicle/1001")
                        .with(getUser()))
                .andExpect(status().isNotFound());

        // vehicles of customer should be deleted too
        mockMvc.perform(get("/vehicle/1002")
                        .with(getUser()))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/customer/999")
                        .with(getUser()))
                .andExpect(status().isNotFound());
    }
}
