package com.salesmanager.shop.security.userdetailsservice;

import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.exception.SecurityDataAccessException;
import com.salesmanager.shop.model.user.Group;
import com.salesmanager.shop.model.user.Permission;
import com.salesmanager.shop.service.permission.PermissionService;
import com.salesmanager.shop.service.user.UserService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author casams1
 *         http://stackoverflow.com/questions/5105776/spring-security-with
 *         -custom-user-details
 */
@Service("userDetailsService")
public class UserServicesImpl implements WebUserServices
{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServicesImpl.class);

	private static final String DEFAULT_INITIAL_PASSWORD = "password";

	@Inject
	private UserService userService;

	@Inject
	protected PermissionService permissionService;

	public final static String ROLE_PREFIX = "ROLE_";//Spring Security 4

	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {

		com.salesmanager.shop.model.user.User user = null;
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		try {

			user = userService.getByUserName(userName);

			if(user==null) {
				return null;
			}

			GrantedAuthority role = new SimpleGrantedAuthority(ROLE_PREFIX + Constants.PERMISSION_AUTHENTICATED);//required to login
			authorities.add(role);

			List<Integer> groupsId = new ArrayList<Integer>();
			List<Group> groups = user.getGroups();
			for(Group group : groups) {


				groupsId.add(group.getId());

			}



	    	List<Permission> permissions = permissionService.getPermissions(groupsId);
	    	for(Permission permission : permissions) {
	    		GrantedAuthority auth = new SimpleGrantedAuthority(ROLE_PREFIX + permission.getPermissionName());
	    		authorities.add(auth);
	    	}

		} catch (Exception e) {
			LOGGER.error("Exception while querrying user",e);
			throw new SecurityDataAccessException("Exception while querrying user",e);
		}

		User secUser = new User(userName, user.getAdminPassword(), user.isActive(), true,
				true, true, authorities);
		return secUser;
	}


}
