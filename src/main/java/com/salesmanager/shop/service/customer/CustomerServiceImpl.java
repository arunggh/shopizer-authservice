package com.salesmanager.shop.service.customer;

import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.customer.Customer;
import com.salesmanager.shop.repositories.CustomerRepository;
import com.salesmanager.shop.service.persistence.SalesManagerEntityServiceImpl;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerService")
public class CustomerServiceImpl extends SalesManagerEntityServiceImpl<Long, Customer> implements CustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	private CustomerRepository customerRepository;
	
	@Inject
	public CustomerServiceImpl(CustomerRepository customerRepository) {
		super(customerRepository);
		this.customerRepository = customerRepository;
	}

	@Override
	public Customer getByNick(String nick) {
		return customerRepository.findByNick(nick);	
	}
	
	@Override
	public Customer getByNick(String nick, int storeId) {
		return customerRepository.findByNick(nick, storeId);
	}

	@Override
	public void saveOrUpdate(Customer customer) throws ServiceException
	{

		LOGGER.debug("Creating Customer");
		
		if(customer.getId()!=null && customer.getId()>0) {
			super.update(customer);
		} else {			
		
			super.create(customer);

		}
	}

}
