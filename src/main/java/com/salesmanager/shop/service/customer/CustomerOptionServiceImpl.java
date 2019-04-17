package com.salesmanager.shop.service.customer;

import com.salesmanager.shop.model.customer.attribute.CustomerOption;
import com.salesmanager.shop.repositories.CustomerOptionRepository;
import com.salesmanager.shop.service.persistence.SalesManagerEntityServiceImpl;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("customerOptionService")
public class CustomerOptionServiceImpl extends
	SalesManagerEntityServiceImpl<Long, CustomerOption> implements CustomerOptionService {

	@Inject
	public CustomerOptionServiceImpl(
			CustomerOptionRepository customerOptionRepository) {
			super(customerOptionRepository);
	}
	


}
