package com.bowerstechnologies.seamstressapp.repository;

import com.bowerstechnologies.seamstressapp.domain.CustOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustOrderRepository extends JpaRepository<CustOrder, Long> {}
