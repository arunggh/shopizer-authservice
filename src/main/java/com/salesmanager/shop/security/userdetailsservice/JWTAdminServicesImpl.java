package com.salesmanager.shop.security.userdetailsservice;

import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.exception.SecurityDataAccessException;
import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.common.audit.AuditSection;
import com.salesmanager.shop.model.user.Group;
import com.salesmanager.shop.model.user.Permission;
import com.salesmanager.shop.service.group.GroupService;
import com.salesmanager.shop.service.permission.PermissionService;
import com.salesmanager.shop.service.user.UserService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("jwtAdminDetailsService")
public class JWTAdminServicesImpl implements UserDetailsService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JWTAdminServicesImpl.class);
	
	
	@Inject
	private UserService userService;
	@Inject
	private PermissionService permissionService;
	@Inject
	private GroupService groupService;
	
	public final static String ROLE_PREFIX = "ROLE_";//Spring Security 4


	private UserDetails userDetails(String userName, com.salesmanager.shop.model.user.User user, Collection<GrantedAuthority> authorities) {
        
		AuditSection section = null;
		section = user.getAuditSection();
		Date lastModified = null;
		if(section != null) {
			lastModified = section.getDateModified();
		}
		
		return new JWTUser(
        		user.getId(),
        		userName,
        		user.getFirstName(),
        		user.getLastName(),
                user.getAdminEmail(),
                user.getAdminPassword(),
                authorities,
                true,
                lastModified
        );
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		com.salesmanager.shop.model.user.User user = null;
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		try {
			
				LOGGER.debug("Loading user by user id: {}", userName);

				user = userService.getByUserName(userName);
			
				if(user==null) {
					//return null;
					throw new UsernameNotFoundException("User " + userName + " not found");
				}

			GrantedAuthority role = new SimpleGrantedAuthority(ROLE_PREFIX + Constants.PERMISSION_AUTHENTICATED);//required to login
			authorities.add(role); 
			
			List<Integer> groupsId = new ArrayList<Integer>();
			List<Group> groups = user.getGroups();
			for(Group group : groups) {
				groupsId.add(group.getId());
			}
			
	
			if(CollectionUtils.isNotEmpty(groupsId)) {
		    	List<Permission> permissions = permissionService.getPermissions(groupsId);
		    	for(Permission permission : permissions) {
		    		GrantedAuthority auth = new SimpleGrantedAuthority(permission.getPermissionName());
		    		authorities.add(auth);
		    	}
			}

		
		} catch (ServiceException e) {
			LOGGER.error("Exception while querrying customer",e);
			throw new SecurityDataAccessException("Cannot authenticate customer",e);
		}

		return userDetails(userName, user, authorities);
	}

}
