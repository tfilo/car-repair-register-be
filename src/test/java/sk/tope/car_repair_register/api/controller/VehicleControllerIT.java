package sk.tope.car_repair_register.api.controller;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import sk.tope.car_repair_register.api.service.so.VehicleCreateSo;
import sk.tope.car_repair_register.api.service.so.VehicleSo;
import sk.tope.car_repair_register.api.service.so.VehicleUpdateSo;
import sk.tope.car_repair_register.bundle.ErrorBundle;
import sk.tope.car_repair_register.common.TestBase;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VehicleControllerIT extends TestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleControllerIT.class);

    @Test
    public void testCreateVehicle() throws Exception {
        VehicleCreateSo minimalSo = new VehicleCreateSo(
                "  XX123YY  ",
                1000L,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        MvcResult minimalMvcResult = mockMvc.perform(post("/vehicle")
                        .content(asJsonString(minimalSo))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser()))
                .andExpect(status().isCreated()).andReturn();

        assertThat(minimalMvcResult.getResponse().getContentAsString()).isNotNull();
        VehicleSo minimalSoResult = asJsonObject(minimalMvcResult.getResponse().getContentAsString(), VehicleSo.class);
        assertThat(minimalSoResult.id()).isNotNull();
        assertThat(minimalSoResult.registrationPlate()).isEqualTo(minimalSo.registrationPlate().trim());
        assertThat(minimalSoResult.customer().id()).isEqualTo(minimalSo.customerId());
        assertThat(minimalSoResult.created()).isNotNull();
        assertThat(minimalSoResult.modified()).isNull();
        assertThat(minimalSoResult.vin()).isNull();
        assertThat(minimalSoResult.engineCode()).isNull();
        assertThat(minimalSoResult.fuelType()).isNull();
        assertThat(minimalSoResult.enginePower()).isNull();
        assertThat(minimalSoResult.engineVolume()).isNull();
        assertThat(minimalSoResult.batteryCapacity()).isNull();
        assertThat(minimalSoResult.brand()).isNull();
        assertThat(minimalSoResult.model()).isNull();
        assertThat(minimalSoResult.yearOfManufacture()).isNull();

        mockMvc.perform(post("/vehicle")
                        .content(asJsonString(minimalSo))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser()))
                .andExpect(status().isConflict());

        VehicleCreateSo fullSo = new VehicleCreateSo(
                "  Xz123yy ",
                1001L,
                " 2hhMB4640XX900492 ",
                " Xx4444zz ",
                " HEVC ",
                77,
                1500,
                15,
                " New ",
                " Model  ",
                2024
        );
        MvcResult fullMvcResult = mockMvc.perform(post("/vehicle")
                        .content(asJsonString(fullSo))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser()))
                .andExpect(status().isCreated()).andReturn();

        assertThat(fullMvcResult.getResponse().getContentAsString()).isNotNull();
        VehicleSo fullSoResult = asJsonObject(fullMvcResult.getResponse().getContentAsString(), VehicleSo.class);
        assertThat(fullSoResult.id()).isNotNull();
        assertThat(fullSoResult.registrationPlate()).isEqualTo(fullSo.registrationPlate().trim().toUpperCase());
        assertThat(fullSoResult.customer().id()).isEqualTo(fullSo.customerId());
        assertThat(fullSoResult.created()).isNotNull();
        assertThat(fullSoResult.modified()).isNull();
        assertThat(fullSoResult.vin()).isEqualTo(fullSo.vin().trim().toUpperCase());
        assertThat(fullSoResult.engineCode()).isEqualTo(fullSo.engineCode().trim().toUpperCase());
        assertThat(fullSoResult.fuelType()).isEqualTo(fullSo.fuelType().trim());
        assertThat(fullSoResult.enginePower()).isEqualTo(fullSo.enginePower());
        assertThat(fullSoResult.engineVolume()).isEqualTo(fullSo.engineVolume());
        assertThat(fullSoResult.batteryCapacity()).isEqualTo(fullSo.batteryCapacity());
        assertThat(fullSoResult.brand()).isEqualTo(fullSo.brand().trim());
        assertThat(fullSoResult.model()).isEqualTo(fullSo.model().trim());
        assertThat(fullSoResult.yearOfManufacture()).isEqualTo(fullSo.yearOfManufacture());

        VehicleCreateSo emptySo = new VehicleCreateSo(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        mockMvc.perform(post("/vehicle")
                        .content(asJsonString(emptySo))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser()))
                .andExpect(jsonPath("$.httpStatus").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value(ErrorBundle.VALIDATION_ERROR.name()))
                .andExpect(jsonPath("$.fieldError[0].fieldName").value("customerId"))
                .andExpect(jsonPath("$.fieldError[0].errorMessage").value("must not be null"))
                .andExpect(jsonPath("$.fieldError[1].fieldName").value("registrationPlate"))
                .andExpect(jsonPath("$.fieldError[1].errorMessage").value("must not be blank"))
                .andExpect(status().isBadRequest());

        VehicleCreateSo duplicateSo = new VehicleCreateSo(
                " xZ123yY ",
                1001L,
                " 2hhMB4640XX900492 ",
                " Xx4444zz ",
                " HEVC ",
                77,
                1500,
                15,
                " New ",
                " Model  ",
                2024
        );
        mockMvc.perform(post("/vehicle")
                        .content(asJsonString(fullSo))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser()))
                .andExpect(jsonPath("$.httpStatus").value(HttpStatus.CONFLICT.name()))
                .andExpect(jsonPath("$.message").value(ErrorBundle.VEHICLE_ALREADY_EXISTS.name()))
                .andExpect(status().isConflict());
    }

    @Test
    public void testGetVehicleById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/vehicle/1001")
                        .with(getUser()))
                .andExpect(status().isOk()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isNotNull();
        VehicleSo result = asJsonObject(mvcResult.getResponse().getContentAsString(), VehicleSo.class);
        assertThat(result.id()).isEqualTo(1001);
        assertThat(result.registrationPlate()).isEqualTo("XX234YY");
        assertThat(result.customer().id()).isEqualTo(1001);
        assertThat(result.customer().name()).isEqualTo("Second");
        assertThat(result.customer().surname()).isEqualTo("Person");
        assertThat(result.customer().mobile()).isEqualTo("0900000002");
        assertThat(result.customer().email()).isEqualTo("second@person.com");
        assertThat(result.customer().created()).isNotNull();
        assertThat(result.customer().modified()).isNull();
        assertThat(result.vin()).isEqualTo("2HHMB4640XX900492");
        assertThat(result.engineCode()).isEqualTo("XX2777");
        assertThat(result.fuelType()).isEqualTo("DIESEL");
        assertThat(result.enginePower()).isEqualTo(93);
        assertThat(result.engineVolume()).isEqualTo(1900);
        assertThat(result.batteryCapacity()).isNull();
        assertThat(result.brand()).isEqualTo("Die");
        assertThat(result.model()).isEqualTo("Sel");
        assertThat(result.yearOfManufacture()).isEqualTo(2005);
        assertThat(result.created()).isEqualToIgnoringNanos(LocalDateTime.of(2020, 1, 4, 8, 0));
        assertThat(result.modified()).isNull();

        mockMvc.perform(get("/customer/999")
                        .with(getUser()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindVehicles() throws Exception {
        mockMvc.perform(get("/vehicle?query=YY")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("sort", "registrationPlate")
                        .with(getUser()))
                .andExpect(jsonPath("$.page.totalElements").value("2"))
                .andExpect(jsonPath("$.content[0].registrationPlate").value("XX234YY"))
                .andExpect(jsonPath("$.content[1].registrationPlate").value("XX345YY"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/vehicle?query=fir")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("sort", "registrationPlate")
                        .with(getUser()))
                .andExpect(jsonPath("$.page.totalElements").value("0"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/vehicle?customerId=1001")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("sort", "registrationPlate")
                        .with(getUser()))
                .andExpect(jsonPath("$.page.totalElements").value("2"))
                .andExpect(jsonPath("$.content[0].registrationPlate").value("XX234YY"))
                .andExpect(jsonPath("$.content[1].registrationPlate").value("XX345YY"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/vehicle?customerId=1001&query=234")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("sort", "registrationPlate")
                        .with(getUser()))
                .andExpect(jsonPath("$.page.totalElements").value("1"))
                .andExpect(jsonPath("$.content[0].registrationPlate").value("XX234YY"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/vehicle?query=elec")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("sort", "registrationPlate")
                        .with(getUser()))
                .andExpect(jsonPath("$.page.totalElements").value("1"))
                .andExpect(jsonPath("$.content[0].registrationPlate").value("XX345YY"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateVehicle() throws Exception {
        VehicleUpdateSo fullSo = new VehicleUpdateSo(
                "XX123YY",
                1003L,
                "2HHMB4640XX900492",
                "XX4444ZZ",
                "HEVC",
                77,
                1500,
                15,
                "New",
                "Model",
                2024);
        MvcResult mvcResult = mockMvc.perform(put("/vehicle/1001")
                        .content(asJsonString(fullSo))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser()))
                .andExpect(status().isOk()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isNotNull();
        VehicleSo result = asJsonObject(mvcResult.getResponse().getContentAsString(), VehicleSo.class);
        assertThat(result.id()).isEqualTo(1001);
        assertThat(result.registrationPlate()).isEqualTo(fullSo.registrationPlate());
        assertThat(result.customer().id()).isEqualTo(fullSo.customerId());
        assertThat(result.customer().name()).isEqualTo("Fourth");
        assertThat(result.customer().surname()).isEqualTo("Person");
        assertThat(result.customer().mobile()).isEqualTo("0900000004");
        assertThat(result.customer().email()).isEqualTo("fourth@person.com");
        assertThat(result.customer().created()).isNotNull();
        assertThat(result.customer().modified()).isNull();
        assertThat(result.created()).isNotNull();
        assertThat(result.modified()).isNotNull();
        assertThat(result.vin()).isEqualTo(fullSo.vin());
        assertThat(result.engineCode()).isEqualTo(fullSo.engineCode());
        assertThat(result.fuelType()).isEqualTo(fullSo.fuelType());
        assertThat(result.enginePower()).isEqualTo(fullSo.enginePower());
        assertThat(result.engineVolume()).isEqualTo(fullSo.engineVolume());
        assertThat(result.batteryCapacity()).isEqualTo(fullSo.batteryCapacity());
        assertThat(result.brand()).isEqualTo(fullSo.brand());
        assertThat(result.model()).isEqualTo(fullSo.model());
        assertThat(result.yearOfManufacture()).isEqualTo(fullSo.yearOfManufacture());

        VehicleUpdateSo minimalSo = new VehicleUpdateSo(
                "XX123YY",
                1000L,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        MvcResult minimalMvcResult = mockMvc.perform(put("/vehicle/1001")
                        .content(asJsonString(minimalSo))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser()))
                .andExpect(status().isOk()).andReturn();

        assertThat(minimalMvcResult.getResponse().getContentAsString()).isNotNull();
        VehicleSo minimalSoResult = asJsonObject(minimalMvcResult.getResponse().getContentAsString(), VehicleSo.class);
        assertThat(minimalSoResult.id()).isEqualTo(1001);
        assertThat(minimalSoResult.registrationPlate()).isEqualTo(minimalSo.registrationPlate());
        assertThat(minimalSoResult.customer().id()).isEqualTo(minimalSo.customerId());
        assertThat(minimalSoResult.created()).isNotNull();
        assertThat(minimalSoResult.modified()).isNotNull();
        assertThat(minimalSoResult.vin()).isNull();
        assertThat(minimalSoResult.engineCode()).isNull();
        assertThat(minimalSoResult.fuelType()).isNull();
        assertThat(minimalSoResult.enginePower()).isNull();
        assertThat(minimalSoResult.engineVolume()).isNull();
        assertThat(minimalSoResult.batteryCapacity()).isNull();
        assertThat(minimalSoResult.brand()).isNull();
        assertThat(minimalSoResult.model()).isNull();
        assertThat(minimalSoResult.yearOfManufacture()).isNull();

        mockMvc.perform(put("/vehicle/999")
                        .content(asJsonString(fullSo))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser()))
                .andExpect(status().isNotFound());

        VehicleUpdateSo emptySo = new VehicleUpdateSo(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        mockMvc.perform(put("/vehicle/1001")
                        .content(asJsonString(emptySo))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser()))
                .andExpect(jsonPath("$.httpStatus").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value(ErrorBundle.VALIDATION_ERROR.name()))
                .andExpect(jsonPath("$.fieldError[0].fieldName").value("customerId"))
                .andExpect(jsonPath("$.fieldError[0].errorMessage").value("must not be null"))
                .andExpect(jsonPath("$.fieldError[1].fieldName").value("registrationPlate"))
                .andExpect(jsonPath("$.fieldError[1].errorMessage").value("must not be blank"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteVehicle() throws Exception {
        mockMvc.perform(delete("/vehicle/1002")
                        .with(getUser()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/vehicle/1002")
                        .with(getUser()))
                .andExpect(status().isNotFound());

        // repair logs of vehicle should be deleted too
        mockMvc.perform(get("/repair-log/1003")
                        .with(getUser()))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/vehicle/999")
                        .with(getUser()))
                .andExpect(status().isNotFound());
    }
}
