package sk.tope.car_repair_register.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.tope.car_repair_register.api.service.VehicleApiService;
import sk.tope.car_repair_register.api.service.so.ErrorMessageSo;
import sk.tope.car_repair_register.api.service.so.VehicleCreateSo;
import sk.tope.car_repair_register.api.service.so.VehicleSo;
import sk.tope.car_repair_register.api.service.so.VehicleUpdateSo;

@Tag(name = "vehicle")
@RestController
@RequestMapping(value = "/vehicle")
public class VehicleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleController.class);

    private final VehicleApiService vehicleApiService;

    public VehicleController(VehicleApiService vehicleApiService) {
        this.vehicleApiService = vehicleApiService;
    }

    @Operation(
            description = "Get vehicle by id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "403", description = "forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "404", description = "not found", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "500", description = "internal server error", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<VehicleSo> getVehicleById(@PathVariable("id") Long id) {
        LOGGER.debug("getVehicleById({})", id);
        return new ResponseEntity<>(vehicleApiService.get(id), HttpStatus.OK);
    }


    @Operation(
            description = "Find vehicles by query string, customerId and pageable.",
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "page", content = @Content(schema = @Schema(type = "integer"))),
                    @Parameter(in = ParameterIn.QUERY, name = "size", content = @Content(schema = @Schema(type = "integer"))),
                    @Parameter(in = ParameterIn.QUERY, name = "sort",
                            content = @Content(array = @ArraySchema(schema = @Schema(type = "string")))
                    )
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "403", description = "forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "404", description = "not found", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "500", description = "internal server error", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
    })
    @GetMapping
    public ResponseEntity<Page<VehicleSo>> findVehicles(@RequestParam(value = "query", required = false) String query, @RequestParam(value = "customerId", required = false) Long customerId, @Parameter(hidden = true) Pageable pageable) {
        LOGGER.debug("findVehicles({},{},{})", query, customerId, pageable);
        return new ResponseEntity<>(vehicleApiService.find(query, customerId, pageable), HttpStatus.OK);
    }

    @Operation(
            description = "Create new vehicle."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "403", description = "forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "404", description = "not found", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "409", description = "conflict", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "500", description = "internal server error", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
    })
    @PostMapping
    public ResponseEntity<VehicleSo> createVehicle(@Valid @RequestBody VehicleCreateSo vehicleCreateSo) {
        LOGGER.debug("createVehicle({})", vehicleCreateSo);
        return new ResponseEntity<>(vehicleApiService.create(vehicleCreateSo), HttpStatus.CREATED);
    }

    @Operation(
            description = "Update existing vehicle."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "403", description = "forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "404", description = "not found", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "409", description = "conflict", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "500", description = "internal server error", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<VehicleSo> updateVehicle(@PathVariable("id") Long id, @Valid @RequestBody VehicleUpdateSo vehicleUpdateSo) {
        LOGGER.debug("updateVehicle({},{})", id, vehicleUpdateSo);
        return new ResponseEntity<>(vehicleApiService.update(id, vehicleUpdateSo), HttpStatus.OK);
    }

    @Operation(
            description = "Delete vehicle with all its records."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "403", description = "forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "404", description = "not found", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "500", description = "internal server error", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
    })
    @DeleteMapping(value = "/{id}")
    public void deleteVehicleById(@PathVariable("id") Long id) {
        LOGGER.debug("deleteVehicleById({})", id);
        vehicleApiService.delete(id);
    }
}
