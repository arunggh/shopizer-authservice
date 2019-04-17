package com.salesmanager.shop.service.permission;

import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.user.Permission;
import com.salesmanager.shop.repositories.PermissionRepository;
import com.salesmanager.shop.service.persistence.SalesManagerEntityServiceImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("permissionService")
public class PermissionServiceImpl extends
	SalesManagerEntityServiceImpl<Integer, Permission> implements
		PermissionService {

	private PermissionRepository permissionRepository;

	@Inject
	public PermissionServiceImpl(PermissionRepository permissionRepository) {
		super(permissionRepository);
		this.permissionRepository = permissionRepository;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permission> getPermissions(List<Integer> groupIds)
			throws ServiceException {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Set ids = new HashSet(groupIds);
		return permissionRepository.findByGroups(ids);
	}

}
