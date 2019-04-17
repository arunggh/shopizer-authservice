package com.salesmanager.shop.service.customer;

import com.salesmanager.shop.model.customer.attribute.CustomerOptionValue;
import com.salesmanager.shop.repositories.CustomerOptionValueRepository;
import com.salesmanager.shop.service.persistence.SalesManagerEntityServiceImpl;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("customerOptionValueService")
public class CustomerOptionValueServiceImpl extends
	SalesManagerEntityServiceImpl<Long, CustomerOptionValue> implements
		CustomerOptionValueService {

	@Inject
	public CustomerOptionValueServiceImpl(
			CustomerOptionValueRepository customerOptionValueRepository) {
			super(customerOptionValueRepository);
	}

}
