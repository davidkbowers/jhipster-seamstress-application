package com.bowerstechnologies.seamstressapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A CustOrder.
 */
@Entity
@Table(name = "cust_order")
public class CustOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Type(type = "uuid-char")
    @Column(name = "custorderid", length = 36, nullable = false, unique = true)
    private UUID custorderid;

    @Type(type = "uuid-char")
    @Column(name = "custid", length = 36)
    private UUID custid;

    @NotNull
    @Size(max = 1000)
    @Column(name = "custorddesc", length = 1000, nullable = false)
    private String custorddesc;

    @Column(name = "custordcost")
    private Float custordcost;

    @Column(name = "custordpaid")
    private Float custordpaid;

    @ManyToOne
    @JsonIgnoreProperties(value = "custOrders", allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getCustorderid() {
        return custorderid;
    }

    public CustOrder custorderid(UUID custorderid) {
        this.custorderid = custorderid;
        return this;
    }

    public void setCustorderid(UUID custorderid) {
        this.custorderid = custorderid;
    }

    public UUID getCustid() {
        return custid;
    }

    public CustOrder custid(UUID custid) {
        this.custid = custid;
        return this;
    }

    public void setCustid(UUID custid) {
        this.custid = custid;
    }

    public String getCustorddesc() {
        return custorddesc;
    }

    public CustOrder custorddesc(String custorddesc) {
        this.custorddesc = custorddesc;
        return this;
    }

    public void setCustorddesc(String custorddesc) {
        this.custorddesc = custorddesc;
    }

    public Float getCustordcost() {
        return custordcost;
    }

    public CustOrder custordcost(Float custordcost) {
        this.custordcost = custordcost;
        return this;
    }

    public void setCustordcost(Float custordcost) {
        this.custordcost = custordcost;
    }

    public Float getCustordpaid() {
        return custordpaid;
    }

    public CustOrder custordpaid(Float custordpaid) {
        this.custordpaid = custordpaid;
        return this;
    }

    public void setCustordpaid(Float custordpaid) {
        this.custordpaid = custordpaid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustOrder customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustOrder)) {
            return false;
        }
        return id != null && id.equals(((CustOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustOrder{" +
            "id=" + getId() +
            ", custorderid='" + getCustorderid() + "'" +
            ", custid='" + getCustid() + "'" +
            ", custorddesc='" + getCustorddesc() + "'" +
            ", custordcost=" + getCustordcost() +
            ", custordpaid=" + getCustordpaid() +
            "}";
    }
}
