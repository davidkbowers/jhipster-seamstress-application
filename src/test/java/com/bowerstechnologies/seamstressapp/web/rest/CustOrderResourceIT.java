package com.bowerstechnologies.seamstressapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bowerstechnologies.seamstressapp.JhipsterSeamstressApplicationApp;
import com.bowerstechnologies.seamstressapp.domain.CustOrder;
import com.bowerstechnologies.seamstressapp.repository.CustOrderRepository;
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
 * Integration tests for the {@link CustOrderResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSeamstressApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustOrderResourceIT {
    private static final UUID DEFAULT_CUSTORDERID = UUID.randomUUID();
    private static final UUID UPDATED_CUSTORDERID = UUID.randomUUID();

    private static final UUID DEFAULT_CUSTID = UUID.randomUUID();
    private static final UUID UPDATED_CUSTID = UUID.randomUUID();

    private static final String DEFAULT_CUSTORDDESC = "AAAAAAAAAA";
    private static final String UPDATED_CUSTORDDESC = "BBBBBBBBBB";

    private static final Float DEFAULT_CUSTORDCOST = 1F;
    private static final Float UPDATED_CUSTORDCOST = 2F;

    private static final Float DEFAULT_CUSTORDPAID = 1F;
    private static final Float UPDATED_CUSTORDPAID = 2F;

    @Autowired
    private CustOrderRepository custOrderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustOrderMockMvc;

    private CustOrder custOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustOrder createEntity(EntityManager em) {
        CustOrder custOrder = new CustOrder()
            .custorderid(DEFAULT_CUSTORDERID)
            .custid(DEFAULT_CUSTID)
            .custorddesc(DEFAULT_CUSTORDDESC)
            .custordcost(DEFAULT_CUSTORDCOST)
            .custordpaid(DEFAULT_CUSTORDPAID);
        return custOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustOrder createUpdatedEntity(EntityManager em) {
        CustOrder custOrder = new CustOrder()
            .custorderid(UPDATED_CUSTORDERID)
            .custid(UPDATED_CUSTID)
            .custorddesc(UPDATED_CUSTORDDESC)
            .custordcost(UPDATED_CUSTORDCOST)
            .custordpaid(UPDATED_CUSTORDPAID);
        return custOrder;
    }

    @BeforeEach
    public void initTest() {
        custOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustOrder() throws Exception {
        int databaseSizeBeforeCreate = custOrderRepository.findAll().size();
        // Create the CustOrder
        restCustOrderMockMvc
            .perform(post("/api/cust-orders").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custOrder)))
            .andExpect(status().isCreated());

        // Validate the CustOrder in the database
        List<CustOrder> custOrderList = custOrderRepository.findAll();
        assertThat(custOrderList).hasSize(databaseSizeBeforeCreate + 1);
        CustOrder testCustOrder = custOrderList.get(custOrderList.size() - 1);
        assertThat(testCustOrder.getCustorderid()).isEqualTo(DEFAULT_CUSTORDERID);
        assertThat(testCustOrder.getCustid()).isEqualTo(DEFAULT_CUSTID);
        assertThat(testCustOrder.getCustorddesc()).isEqualTo(DEFAULT_CUSTORDDESC);
        assertThat(testCustOrder.getCustordcost()).isEqualTo(DEFAULT_CUSTORDCOST);
        assertThat(testCustOrder.getCustordpaid()).isEqualTo(DEFAULT_CUSTORDPAID);
    }

    @Test
    @Transactional
    public void createCustOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = custOrderRepository.findAll().size();

        // Create the CustOrder with an existing ID
        custOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustOrderMockMvc
            .perform(post("/api/cust-orders").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custOrder)))
            .andExpect(status().isBadRequest());

        // Validate the CustOrder in the database
        List<CustOrder> custOrderList = custOrderRepository.findAll();
        assertThat(custOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCustorderidIsRequired() throws Exception {
        int databaseSizeBeforeTest = custOrderRepository.findAll().size();
        // set the field null
        custOrder.setCustorderid(null);

        // Create the CustOrder, which fails.

        restCustOrderMockMvc
            .perform(post("/api/cust-orders").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custOrder)))
            .andExpect(status().isBadRequest());

        List<CustOrder> custOrderList = custOrderRepository.findAll();
        assertThat(custOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustorddescIsRequired() throws Exception {
        int databaseSizeBeforeTest = custOrderRepository.findAll().size();
        // set the field null
        custOrder.setCustorddesc(null);

        // Create the CustOrder, which fails.

        restCustOrderMockMvc
            .perform(post("/api/cust-orders").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custOrder)))
            .andExpect(status().isBadRequest());

        List<CustOrder> custOrderList = custOrderRepository.findAll();
        assertThat(custOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustOrders() throws Exception {
        // Initialize the database
        custOrderRepository.saveAndFlush(custOrder);

        // Get all the custOrderList
        restCustOrderMockMvc
            .perform(get("/api/cust-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].custorderid").value(hasItem(DEFAULT_CUSTORDERID.toString())))
            .andExpect(jsonPath("$.[*].custid").value(hasItem(DEFAULT_CUSTID.toString())))
            .andExpect(jsonPath("$.[*].custorddesc").value(hasItem(DEFAULT_CUSTORDDESC)))
            .andExpect(jsonPath("$.[*].custordcost").value(hasItem(DEFAULT_CUSTORDCOST.doubleValue())))
            .andExpect(jsonPath("$.[*].custordpaid").value(hasItem(DEFAULT_CUSTORDPAID.doubleValue())));
    }

    @Test
    @Transactional
    public void getCustOrder() throws Exception {
        // Initialize the database
        custOrderRepository.saveAndFlush(custOrder);

        // Get the custOrder
        restCustOrderMockMvc
            .perform(get("/api/cust-orders/{id}", custOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custOrder.getId().intValue()))
            .andExpect(jsonPath("$.custorderid").value(DEFAULT_CUSTORDERID.toString()))
            .andExpect(jsonPath("$.custid").value(DEFAULT_CUSTID.toString()))
            .andExpect(jsonPath("$.custorddesc").value(DEFAULT_CUSTORDDESC))
            .andExpect(jsonPath("$.custordcost").value(DEFAULT_CUSTORDCOST.doubleValue()))
            .andExpect(jsonPath("$.custordpaid").value(DEFAULT_CUSTORDPAID.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCustOrder() throws Exception {
        // Get the custOrder
        restCustOrderMockMvc.perform(get("/api/cust-orders/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustOrder() throws Exception {
        // Initialize the database
        custOrderRepository.saveAndFlush(custOrder);

        int databaseSizeBeforeUpdate = custOrderRepository.findAll().size();

        // Update the custOrder
        CustOrder updatedCustOrder = custOrderRepository.findById(custOrder.getId()).get();
        // Disconnect from session so that the updates on updatedCustOrder are not directly saved in db
        em.detach(updatedCustOrder);
        updatedCustOrder
            .custorderid(UPDATED_CUSTORDERID)
            .custid(UPDATED_CUSTID)
            .custorddesc(UPDATED_CUSTORDDESC)
            .custordcost(UPDATED_CUSTORDCOST)
            .custordpaid(UPDATED_CUSTORDPAID);

        restCustOrderMockMvc
            .perform(
                put("/api/cust-orders").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedCustOrder))
            )
            .andExpect(status().isOk());

        // Validate the CustOrder in the database
        List<CustOrder> custOrderList = custOrderRepository.findAll();
        assertThat(custOrderList).hasSize(databaseSizeBeforeUpdate);
        CustOrder testCustOrder = custOrderList.get(custOrderList.size() - 1);
        assertThat(testCustOrder.getCustorderid()).isEqualTo(UPDATED_CUSTORDERID);
        assertThat(testCustOrder.getCustid()).isEqualTo(UPDATED_CUSTID);
        assertThat(testCustOrder.getCustorddesc()).isEqualTo(UPDATED_CUSTORDDESC);
        assertThat(testCustOrder.getCustordcost()).isEqualTo(UPDATED_CUSTORDCOST);
        assertThat(testCustOrder.getCustordpaid()).isEqualTo(UPDATED_CUSTORDPAID);
    }

    @Test
    @Transactional
    public void updateNonExistingCustOrder() throws Exception {
        int databaseSizeBeforeUpdate = custOrderRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustOrderMockMvc
            .perform(put("/api/cust-orders").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custOrder)))
            .andExpect(status().isBadRequest());

        // Validate the CustOrder in the database
        List<CustOrder> custOrderList = custOrderRepository.findAll();
        assertThat(custOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustOrder() throws Exception {
        // Initialize the database
        custOrderRepository.saveAndFlush(custOrder);

        int databaseSizeBeforeDelete = custOrderRepository.findAll().size();

        // Delete the custOrder
        restCustOrderMockMvc
            .perform(delete("/api/cust-orders/{id}", custOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustOrder> custOrderList = custOrderRepository.findAll();
        assertThat(custOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
