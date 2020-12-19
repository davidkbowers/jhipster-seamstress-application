package com.bowerstechnologies.seamstressapp.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "uuid-char")
    @Column(name = "custid", length = 36)
    private UUID custid;

    @NotNull
    @Size(max = 75)
    @Column(name = "custname", length = 75, nullable = false, unique = true)
    private String custname;

    @Column(name = "custemail")
    private String custemail;

    @Column(name = "custphone")
    private String custphone;

    @Column(name = "custaddress")
    private String custaddress;

    @OneToMany(mappedBy = "customer")
    private Set<CustOrder> custOrders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getCustid() {
        return custid;
    }

    public Customer custid(UUID custid) {
        this.custid = custid;
        return this;
    }

    public void setCustid(UUID custid) {
        this.custid = custid;
    }

    public String getCustname() {
        return custname;
    }

    public Customer custname(String custname) {
        this.custname = custname;
        return this;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getCustemail() {
        return custemail;
    }

    public Customer custemail(String custemail) {
        this.custemail = custemail;
        return this;
    }

    public void setCustemail(String custemail) {
        this.custemail = custemail;
    }

    public String getCustphone() {
        return custphone;
    }

    public Customer custphone(String custphone) {
        this.custphone = custphone;
        return this;
    }

    public void setCustphone(String custphone) {
        this.custphone = custphone;
    }

    public String getCustaddress() {
        return custaddress;
    }

    public Customer custaddress(String custaddress) {
        this.custaddress = custaddress;
        return this;
    }

    public void setCustaddress(String custaddress) {
        this.custaddress = custaddress;
    }

    public Set<CustOrder> getCustOrders() {
        return custOrders;
    }

    public Customer custOrders(Set<CustOrder> custOrders) {
        this.custOrders = custOrders;
        return this;
    }

    public Customer addCustOrder(CustOrder custOrder) {
        this.custOrders.add(custOrder);
        custOrder.setCustomer(this);
        return this;
    }

    public Customer removeCustOrder(CustOrder custOrder) {
        this.custOrders.remove(custOrder);
        custOrder.setCustomer(null);
        return this;
    }

    public void setCustOrders(Set<CustOrder> custOrders) {
        this.custOrders = custOrders;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", custid='" + getCustid() + "'" +
            ", custname='" + getCustname() + "'" +
            ", custemail='" + getCustemail() + "'" +
            ", custphone='" + getCustphone() + "'" +
            ", custaddress='" + getCustaddress() + "'" +
            "}";
    }
}
