package com.salesmanager.shop.service.user;

import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.user.User;
import com.salesmanager.shop.service.persistence.SalesManagerEntityService;

public interface UserService extends SalesManagerEntityService<Long, User>
{

	User getByUserName(String userName) throws ServiceException;

	/**
	 * Create or update a User
	 * @param user
	 * @throws ServiceException
	 */
	void saveOrUpdate(User user) throws ServiceException;

}
