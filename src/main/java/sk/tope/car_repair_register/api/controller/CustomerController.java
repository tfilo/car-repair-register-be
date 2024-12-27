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
import sk.tope.car_repair_register.api.service.CustomerApiService;
import sk.tope.car_repair_register.api.service.so.*;

@Tag(name = "customer")
@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerApiService customerApiService;

    public CustomerController(CustomerApiService customerApiService) {
        this.customerApiService = customerApiService;
    }

    @Operation(
            description = "Get customer by id."
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
    public ResponseEntity<CustomerSo> getCustomerById(@PathVariable("id") Long id) {
        LOGGER.debug("getCustomerById({})", id);
        return new ResponseEntity<>(customerApiService.get(id), HttpStatus.OK);
    }


    @Operation(
            description = "Find customers by query string and pageable.",
           parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "page", content = @Content(schema = @Schema(type = "integer"))),
                    @Parameter(in = ParameterIn.QUERY, name = "size", content = @Content(schema = @Schema(type = "integer"))),
                    @Parameter(in = ParameterIn.QUERY, name = "sort",
                            content = @Content(array = @ArraySchema(schema = @Schema(type = "string")))
                    )
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok", content = {
                    @Content(schema = @Schema(implementation = CustomerPagedModel.class))
            }),
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
    public ResponseEntity<Page<CustomerSo>> findCustomers(@RequestParam(value = "query", required = false) String query, @Parameter(hidden = true) Pageable pageable) {
        LOGGER.debug("findCustomers({},{})", query, pageable);
        return new ResponseEntity<>(customerApiService.find(query, pageable), HttpStatus.OK);
    }

    @Operation(
            description = "Create new customer."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "403", description = "forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "500", description = "internal server error", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
    })
    @PostMapping
    public ResponseEntity<CustomerSo> createCustomer(@Valid @RequestBody CustomerCreateSo customerCreateSo) {
        LOGGER.debug("createCustomer({})", customerCreateSo);
        return new ResponseEntity<>(customerApiService.create(customerCreateSo), HttpStatus.CREATED);
    }

    @Operation(
            description = "Update existing customer."
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
    @PutMapping(value = "/{id}")
    public ResponseEntity<CustomerSo> updateCustomer(@PathVariable("id") Long id, @Valid @RequestBody CustomerUpdateSo customerUpdateSo) {
        LOGGER.debug("updateCustomer({},{})", id, customerUpdateSo);
        return new ResponseEntity<>(customerApiService.update(id, customerUpdateSo), HttpStatus.OK);
    }

    @Operation(
            description = "Delete customer with all his vehicles and records."
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
    public void deleteCustomerById(@PathVariable("id") Long id) {
        LOGGER.debug("deleteCustomerById({})", id);
        customerApiService.delete(id);
    }

}
