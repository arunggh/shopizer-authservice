package com.salesmanager.shop.service.customer;

import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.customer.Customer;
import com.salesmanager.shop.service.persistence.SalesManagerEntityService;

public interface CustomerService  extends SalesManagerEntityService<Long, Customer>
{

	Customer getByNick(String nick);

	void saveOrUpdate(Customer customer) throws ServiceException;

	Customer getByNick(String nick, int storeId);

}
