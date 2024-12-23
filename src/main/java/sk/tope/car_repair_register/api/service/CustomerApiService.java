package sk.tope.car_repair_register.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sk.tope.car_repair_register.api.service.so.CustomerCreateSo;
import sk.tope.car_repair_register.api.service.so.CustomerSo;
import sk.tope.car_repair_register.api.service.so.CustomerUpdateSo;
import sk.tope.car_repair_register.bundle.ErrorBundle;
import sk.tope.car_repair_register.component.TokenHandler;
import sk.tope.car_repair_register.dal.domain.Customer;
import sk.tope.car_repair_register.dal.repository.CustomerRepository;
import sk.tope.car_repair_register.dal.specification.CustomerSpecification;
import sk.tope.car_repair_register.mapper.CustomerMapper;

@Service
public class CustomerApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerApiService.class);
    private static TokenHandler tokenHandler;
    private CustomerMapper customerMapper;
    private CustomerRepository customerRepository;

    @Autowired
    public void setTokenHandler(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @Autowired
    public void setCustomerMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<CustomerSo> find(String query, Pageable pageable) {
        LOGGER.debug("find({},{})", query, pageable);
        Page<Customer> result = customerRepository.findAll(new CustomerSpecification(query, tokenHandler), pageable);
        LOGGER.debug("find({},{})={}", query, pageable, result);
        return result.map(c -> customerMapper.mapToCustomerSo(c));
    }

    public CustomerSo get(Long id) {
        LOGGER.debug("get({})", id);
        Customer result = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorBundle.CUSTOMER_NOT_FOUND.name()));
        LOGGER.debug("get({})={}", id, result);
        return customerMapper.mapToCustomerSo(result);
    }

    @Transactional
    public CustomerSo create(CustomerCreateSo customerCreateSo) {
        LOGGER.debug("create({})", customerCreateSo);
        Customer customer = customerMapper.mapToCustomer(customerCreateSo);
        customer = customerRepository.saveAndFlush(customer);
        LOGGER.debug("create({})={}", customerCreateSo, customer);
        return customerMapper.mapToCustomerSo(customer);
    }

    @Transactional
    public CustomerSo update(Long id, CustomerUpdateSo customerUpdateSo) {
        LOGGER.debug("update({})", customerUpdateSo);
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorBundle.CUSTOMER_NOT_FOUND.name()));
        customerMapper.mapTo(customer, customerUpdateSo);
        customer = customerRepository.saveAndFlush(customer);
        LOGGER.debug("update({})={}", customerUpdateSo, customer);
        return customerMapper.mapToCustomerSo(customer);
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.debug("delete({})", id);
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorBundle.CUSTOMER_NOT_FOUND.name()));
        customerRepository.delete(customer);
    }
}
