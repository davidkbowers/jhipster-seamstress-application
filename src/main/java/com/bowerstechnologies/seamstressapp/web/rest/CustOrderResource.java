package com.bowerstechnologies.seamstressapp.web.rest;

import com.bowerstechnologies.seamstressapp.domain.CustOrder;
import com.bowerstechnologies.seamstressapp.repository.CustOrderRepository;
import com.bowerstechnologies.seamstressapp.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.bowerstechnologies.seamstressapp.domain.CustOrder}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustOrderResource {
    private final Logger log = LoggerFactory.getLogger(CustOrderResource.class);

    private static final String ENTITY_NAME = "custOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustOrderRepository custOrderRepository;

    public CustOrderResource(CustOrderRepository custOrderRepository) {
        this.custOrderRepository = custOrderRepository;
    }

    /**
     * {@code POST  /cust-orders} : Create a new custOrder.
     *
     * @param custOrder the custOrder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custOrder, or with status {@code 400 (Bad Request)} if the custOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-orders")
    public ResponseEntity<CustOrder> createCustOrder(@Valid @RequestBody CustOrder custOrder) throws URISyntaxException {
        log.debug("REST request to save CustOrder : {}", custOrder);
        if (custOrder.getId() != null) {
            throw new BadRequestAlertException("A new custOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustOrder result = custOrderRepository.save(custOrder);
        return ResponseEntity
            .created(new URI("/api/cust-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-orders} : Updates an existing custOrder.
     *
     * @param custOrder the custOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custOrder,
     * or with status {@code 400 (Bad Request)} if the custOrder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-orders")
    public ResponseEntity<CustOrder> updateCustOrder(@Valid @RequestBody CustOrder custOrder) throws URISyntaxException {
        log.debug("REST request to update CustOrder : {}", custOrder);
        if (custOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustOrder result = custOrderRepository.save(custOrder);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custOrder.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cust-orders} : get all the custOrders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custOrders in body.
     */
    @GetMapping("/cust-orders")
    public ResponseEntity<List<CustOrder>> getAllCustOrders(Pageable pageable) {
        log.debug("REST request to get a page of CustOrders");
        Page<CustOrder> page = custOrderRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cust-orders/:id} : get the "id" custOrder.
     *
     * @param id the id of the custOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-orders/{id}")
    public ResponseEntity<CustOrder> getCustOrder(@PathVariable Long id) {
        log.debug("REST request to get CustOrder : {}", id);
        Optional<CustOrder> custOrder = custOrderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(custOrder);
    }

    /**
     * {@code DELETE  /cust-orders/:id} : delete the "id" custOrder.
     *
     * @param id the id of the custOrder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-orders/{id}")
    public ResponseEntity<Void> deleteCustOrder(@PathVariable Long id) {
        log.debug("REST request to delete CustOrder : {}", id);
        custOrderRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
