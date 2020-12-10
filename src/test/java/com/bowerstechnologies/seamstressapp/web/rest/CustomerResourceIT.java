package com.bowerstechnologies.seamstressapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bowerstechnologies.seamstressapp.JhipsterSeamstressApplicationApp;
import com.bowerstechnologies.seamstressapp.domain.Customer;
import com.bowerstechnologies.seamstressapp.repository.CustomerRepository;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CustomerResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSeamstressApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerResourceIT {
    private static final UUID DEFAULT_CUSTID = UUID.randomUUID();
    private static final UUID UPDATED_CUSTID = UUID.randomUUID();

    private static final String DEFAULT_CUSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTEMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CUSTEMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTPHONE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CUSTADDRESS = "BBBBBBBBBB";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerMockMvc;

    private Customer customer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .custid(DEFAULT_CUSTID)
            .custname(DEFAULT_CUSTNAME)
            .custemail(DEFAULT_CUSTEMAIL)
            .custphone(DEFAULT_CUSTPHONE)
            .custaddress(DEFAULT_CUSTADDRESS);
        return customer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createUpdatedEntity(EntityManager em) {
        Customer customer = new Customer()
            .custid(UPDATED_CUSTID)
            .custname(UPDATED_CUSTNAME)
            .custemail(UPDATED_CUSTEMAIL)
            .custphone(UPDATED_CUSTPHONE)
            .custaddress(UPDATED_CUSTADDRESS);
        return customer;
    }

    @BeforeEach
    public void initTest() {
        customer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();
        // Create the Customer
        restCustomerMockMvc
            .perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getCustid()).isEqualTo(DEFAULT_CUSTID);
        assertThat(testCustomer.getCustname()).isEqualTo(DEFAULT_CUSTNAME);
        assertThat(testCustomer.getCustemail()).isEqualTo(DEFAULT_CUSTEMAIL);
        assertThat(testCustomer.getCustphone()).isEqualTo(DEFAULT_CUSTPHONE);
        assertThat(testCustomer.getCustaddress()).isEqualTo(DEFAULT_CUSTADDRESS);
    }

    @Test
    @Transactional
    public void createCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer with an existing ID
        customer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc
            .perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCustnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setCustname(null);

        // Create the Customer, which fails.

        restCustomerMockMvc
            .perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc
            .perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].custid").value(hasItem(DEFAULT_CUSTID.toString())))
            .andExpect(jsonPath("$.[*].custname").value(hasItem(DEFAULT_CUSTNAME)))
            .andExpect(jsonPath("$.[*].custemail").value(hasItem(DEFAULT_CUSTEMAIL)))
            .andExpect(jsonPath("$.[*].custphone").value(hasItem(DEFAULT_CUSTPHONE)))
            .andExpect(jsonPath("$.[*].custaddress").value(hasItem(DEFAULT_CUSTADDRESS)));
    }

    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc
            .perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.custid").value(DEFAULT_CUSTID.toString()))
            .andExpect(jsonPath("$.custname").value(DEFAULT_CUSTNAME))
            .andExpect(jsonPath("$.custemail").value(DEFAULT_CUSTEMAIL))
            .andExpect(jsonPath("$.custphone").value(DEFAULT_CUSTPHONE))
            .andExpect(jsonPath("$.custaddress").value(DEFAULT_CUSTADDRESS));
    }

    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        // Disconnect from session so that the updates on updatedCustomer are not directly saved in db
        em.detach(updatedCustomer);
        updatedCustomer
            .custid(UPDATED_CUSTID)
            .custname(UPDATED_CUSTNAME)
            .custemail(UPDATED_CUSTEMAIL)
            .custphone(UPDATED_CUSTPHONE)
            .custaddress(UPDATED_CUSTADDRESS);

        restCustomerMockMvc
            .perform(
                put("/api/customers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedCustomer))
            )
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getCustid()).isEqualTo(UPDATED_CUSTID);
        assertThat(testCustomer.getCustname()).isEqualTo(UPDATED_CUSTNAME);
        assertThat(testCustomer.getCustemail()).isEqualTo(UPDATED_CUSTEMAIL);
        assertThat(testCustomer.getCustphone()).isEqualTo(UPDATED_CUSTPHONE);
        assertThat(testCustomer.getCustaddress()).isEqualTo(UPDATED_CUSTADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(put("/api/customers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Delete the customer
        restCustomerMockMvc
            .perform(delete("/api/customers/{id}", customer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
