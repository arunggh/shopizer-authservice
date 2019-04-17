package com.salesmanager.shop.security.userdetailsservice;

import com.salesmanager.shop.model.customer.Customer;
import com.salesmanager.shop.service.customer.CustomerService;
import com.salesmanager.shop.service.group.GroupService;
import com.salesmanager.shop.service.permission.PermissionService;
import java.util.Collection;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * 
 * @author casams1
 *         http://stackoverflow.com/questions/5105776/spring-security-with
 *         -custom-user-details
 */
@Service("customerDetailsService")
public class CustomerServicesImpl extends AbstractCustomerServices
{

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServicesImpl.class);
	
	@Inject
	public CustomerServicesImpl(CustomerService customerService, PermissionService permissionService, GroupService groupService) {
		super(customerService, permissionService, groupService);
	}
	
	@Override
	protected UserDetails userDetails(String userName, Customer customer, Collection<GrantedAuthority> authorities) {

		CustomerDetails authUser = new CustomerDetails(userName, customer.getPassword(), true, true,
				true, true, authorities);
		
		authUser.setEmail(customer.getEmailAddress());
		authUser.setId(customer.getId());
		
		return authUser;
	}
	




}
