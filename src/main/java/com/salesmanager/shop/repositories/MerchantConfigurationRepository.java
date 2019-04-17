package com.salesmanager.shop.repositories;

import com.salesmanager.shop.model.merchant.MerchantConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MerchantConfigurationRepository extends JpaRepository<MerchantConfiguration, Long>
{

	@Query("select m from MerchantConfiguration m join fetch m.merchantStore ms where ms.id=?1 and m.key=?2")
	MerchantConfiguration findByMerchantStoreAndKey(Integer id, String key);
	
}
