package com.salesmanager.shop.service.user;

import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.user.User;
import com.salesmanager.shop.repositories.UserRepository;
import com.salesmanager.shop.service.persistence.SalesManagerEntityServiceImpl;
import javax.inject.Inject;

public class UserServiceImpl extends SalesManagerEntityServiceImpl<Long, User>
		implements UserService {


	private UserRepository userRepository;
	
	@Inject
	public UserServiceImpl(UserRepository userRepository) {
		super(userRepository);
		this.userRepository = userRepository;

	}

	@Override
	public User getByUserName(String userName) throws ServiceException {
		return userRepository.findByUserName(userName);
	}

	@Override
	public void saveOrUpdate(User user) throws ServiceException {
		userRepository.save(user);
	}
	
	@Override
	public void delete(User user) throws ServiceException {
		User u = this.getById(user.getId());
		super.delete(u);
		
	}

}
