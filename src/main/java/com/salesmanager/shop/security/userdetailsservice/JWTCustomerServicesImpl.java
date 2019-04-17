package com.salesmanager.shop.security.userdetailsservice;

import com.salesmanager.shop.model.common.audit.AuditSection;
import com.salesmanager.shop.model.customer.Customer;
import com.salesmanager.shop.service.customer.CustomerService;
import com.salesmanager.shop.service.group.GroupService;
import com.salesmanager.shop.service.permission.PermissionService;
import java.util.Collection;
import java.util.Date;
import javax.inject.Inject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service("jwtCustomerDetailsService")
public class JWTCustomerServicesImpl extends AbstractCustomerServices
{
	
	
	@Inject
	public JWTCustomerServicesImpl(CustomerService customerService, PermissionService permissionService, GroupService groupService) {
		super(customerService, permissionService, groupService);
		this.customerService = customerService;
		this.permissionService = permissionService;
		this.groupService = groupService;
	}

	@Override
	protected UserDetails userDetails(String userName, Customer customer, Collection<GrantedAuthority> authorities) {
        
		AuditSection section = null;
		section = customer.getAuditSection();
		Date lastModified = null;
		if(section != null) {
			lastModified = section.getDateModified();
		}
		
		return new JWTUser(
        		customer.getId(),
        		userName,
        		customer.getBilling().getFirstName(),
        		customer.getBilling().getLastName(),
                customer.getEmailAddress(),
                customer.getPassword(),
                authorities,
                true,
                lastModified
        );
	}

}
