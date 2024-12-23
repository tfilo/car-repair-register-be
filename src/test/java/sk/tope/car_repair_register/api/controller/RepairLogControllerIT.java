package sk.tope.car_repair_register.api.controller;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import sk.tope.car_repair_register.api.service.so.RepairLogCreateSo;
import sk.tope.car_repair_register.api.service.so.RepairLogSo;
import sk.tope.car_repair_register.api.service.so.RepairLogUpdateSo;
import sk.tope.car_repair_register.bundle.ErrorBundle;
import sk.tope.car_repair_register.common.TestBase;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RepairLogControllerIT extends TestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepairLogControllerIT.class);

    @Test
    public void testCreateRepairLog() throws Exception {
        RepairLogCreateSo so = new RepairLogCreateSo(
                "Lorem ipsum dolor sit amet",
                1001L,
                LocalDate.of(2020, 1, 1)
        );
        MvcResult mvcResult = mockMvc.perform(post("/repair-log")
                        .content(asJsonString(so))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser()))
                .andExpect(status().isCreated()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isNotNull();
        RepairLogSo result = asJsonObject(mvcResult.getResponse().getContentAsString(), RepairLogSo.class);
        assertThat(result.id()).isNotNull();
        assertThat(result.content()).isEqualTo(so.content());
        assertThat(result.vehicle().id()).isEqualTo(so.vehicleId());
        assertThat(result.repairDate()).isEqualTo(so.repairDate());
        assertThat(result.created()).isNotNull();
        assertThat(result.modified()).isNull();

        mockMvc.perform(post("/repair-log")
                        .content(asJsonString(so))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser()))
                .andExpect(status().isConflict());

        RepairLogCreateSo emptySo = new RepairLogCreateSo(
                null,
                null,
                null
        );

        mockMvc.perform(post("/repair-log")
                        .content(asJsonString(emptySo))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser()))
                .andExpect(jsonPath("$.httpStatus").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value(ErrorBundle.VALIDATION_ERROR.name()))
                .andExpect(jsonPath("$.fieldError[0].fieldName").value("content"))
                .andExpect(jsonPath("$.fieldError[0].errorMessage").value("must not be blank"))
                .andExpect(jsonPath("$.fieldError[1].fieldName").value("repairDate"))
                .andExpect(jsonPath("$.fieldError[1].errorMessage").value("must not be null"))
                .andExpect(jsonPath("$.fieldError[2].fieldName").value("vehicleId"))
                .andExpect(jsonPath("$.fieldError[2].errorMessage").value("must not be null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetRepairLogById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/repair-log/1003")
                        .with(getUser()))
                .andExpect(status().isOk()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isNotNull();
        RepairLogSo result = asJsonObject(mvcResult.getResponse().getContentAsString(), RepairLogSo.class);
        assertThat(result.id()).isEqualTo(1003);
        assertThat(result.content()).isEqualTo("4 This is some content of repair description");
        assertThat(result.repairDate()).isEqualTo(LocalDate.of(2020, 4, 7));
        assertThat(result.vehicle().customer().name()).isEqualTo("Second");
        assertThat(result.vehicle().customer().surname()).isEqualTo("Person");
        assertThat(result.vehicle().customer().mobile()).isEqualTo("0900000002");
        assertThat(result.vehicle().customer().email()).isEqualTo("second@person.com");
        assertThat(result.vehicle().customer().created()).isNotNull();
        assertThat(result.vehicle().customer().modified()).isNull();
        assertThat(result.vehicle().registrationPlate()).isEqualTo("XX345YY");
        assertThat(result.vehicle().vin()).isEqualTo("2HHMB4640XX900493");
        assertThat(result.vehicle().engineCode()).isEqualTo("XX3777");
        assertThat(result.vehicle().fuelType()).isEqualTo("EV");
        assertThat(result.vehicle().enginePower()).isEqualTo(120);
        assertThat(result.vehicle().engineVolume()).isNull();
        assertThat(result.vehicle().batteryCapacity()).isEqualTo(55);
        assertThat(result.vehicle().brand()).isEqualTo("Elec");
        assertThat(result.vehicle().model()).isEqualTo("Cric");
        assertThat(result.vehicle().yearOfManufacture()).isEqualTo(2018);
        assertThat(result.created()).isEqualToIgnoringNanos(LocalDateTime.of(2020, 4, 7, 8, 33, 00));
        assertThat(result.modified()).isNull();

        mockMvc.perform(get("/repair-log/999")
                        .with(getUser()))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/repair-log/1001")
                        .with(getUser()))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/repair-log/1002")
                        .with(getUser()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindRepairLogs() throws Exception {
        mockMvc.perform(get("/repair-log?query=YY")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("sort", "repairDate")
                        .with(getUser()))
                .andExpect(jsonPath("$.page.totalElements").value("1"))
                .andExpect(jsonPath("$.content[0].content").value("4 This is some content of repair description"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/repair-log?query=fir")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("sort", "repairDate")
                        .with(getUser()))
                .andExpect(jsonPath("$.page.totalElements").value("0"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/repair-log?vehicleId=1001")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("sort", "repairDate")
                        .with(getUser()))
                .andExpect(jsonPath("$.page.totalElements").value("0"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/repair-log?vehicleId=1002")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("sort", "repairDate")
                        .with(getUser()))
                .andExpect(jsonPath("$.page.totalElements").value("1"))
                .andExpect(jsonPath("$.content[0].content").value("4 This is some content of repair description"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/repair-log?vehicleId=1002&query=some content")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("sort", "repairDate")
                        .with(getUser()))
                .andExpect(jsonPath("$.page.totalElements").value("1"))
                .andExpect(jsonPath("$.content[0].content").value("4 This is some content of repair description"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/repair-log?vehicleId=1003")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("sort", "repairDate")
                        .with(getUser()))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateRepairLog() throws Exception {
        RepairLogUpdateSo fullSo = new RepairLogUpdateSo(
                "Lorem ipsum dolor sit amet..",
                1001L,
                LocalDate.of(2020, 1, 1)
        );
        MvcResult mvcResult = mockMvc.perform(put("/repair-log/1003")
                        .content(asJsonString(fullSo))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser()))
                .andExpect(status().isOk()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isNotNull();
        RepairLogSo result = asJsonObject(mvcResult.getResponse().getContentAsString(), RepairLogSo.class);
        assertThat(result.id()).isEqualTo(1003);
        assertThat(result.content()).isEqualTo(fullSo.content());
        assertThat(result.repairDate()).isEqualTo(fullSo.repairDate());
        assertThat(result.vehicle().customer().id()).isEqualTo(1001);
        assertThat(result.vehicle().customer().name()).isEqualTo("Second");
        assertThat(result.vehicle().customer().surname()).isEqualTo("Person");
        assertThat(result.vehicle().customer().mobile()).isEqualTo("0900000002");
        assertThat(result.vehicle().customer().email()).isEqualTo("second@person.com");
        assertThat(result.vehicle().customer().created()).isNotNull();
        assertThat(result.vehicle().customer().modified()).isNull();
        assertThat(result.created()).isNotNull();
        assertThat(result.modified()).isNotNull();
        assertThat(result.vehicle().registrationPlate()).isEqualTo("XX234YY");
        assertThat(result.vehicle().vin()).isEqualTo("2HHMB4640XX900492");
        assertThat(result.vehicle().engineCode()).isEqualTo("XX2777");
        assertThat(result.vehicle().fuelType()).isEqualTo("DIESEL");
        assertThat(result.vehicle().enginePower()).isEqualTo(93);
        assertThat(result.vehicle().engineVolume()).isEqualTo(1900);
        assertThat(result.vehicle().batteryCapacity()).isNull();
        assertThat(result.vehicle().brand()).isEqualTo("Die");
        assertThat(result.vehicle().model()).isEqualTo("Sel");
        assertThat(result.vehicle().yearOfManufacture()).isEqualTo(2005);
        assertThat(result.vehicle().created()).isEqualToIgnoringNanos(LocalDateTime.of(2020, 1, 4, 8, 0, 0));
        assertThat(result.vehicle().modified()).isNull();

        mockMvc.perform(put("/repair-log/999")
                        .content(asJsonString(fullSo))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser()))
                .andExpect(status().isNotFound());

        RepairLogUpdateSo emptySo = new RepairLogUpdateSo(
                null,
                null,
                null
        );

        mockMvc.perform(put("/repair-log/1003")
                        .content(asJsonString(emptySo))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser()))
                .andExpect(jsonPath("$.httpStatus").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value(ErrorBundle.VALIDATION_ERROR.name()))
                .andExpect(jsonPath("$.fieldError[0].fieldName").value("content"))
                .andExpect(jsonPath("$.fieldError[0].errorMessage").value("must not be blank"))
                .andExpect(jsonPath("$.fieldError[1].fieldName").value("repairDate"))
                .andExpect(jsonPath("$.fieldError[1].errorMessage").value("must not be null"))
                .andExpect(jsonPath("$.fieldError[2].fieldName").value("vehicleId"))
                .andExpect(jsonPath("$.fieldError[2].errorMessage").value("must not be null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteRepairLog() throws Exception {
        mockMvc.perform(delete("/repair-log/1003")
                        .with(getUser()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/repair-log/1003")
                        .with(getUser()))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/repair-log/999")
                        .with(getUser()))
                .andExpect(status().isNotFound());
    }
}
