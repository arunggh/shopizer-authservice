package com.salesmanager.shop.service.permission;

import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.user.Permission;
import com.salesmanager.shop.service.persistence.SalesManagerEntityService;
import java.util.List;

public interface PermissionService extends SalesManagerEntityService<Integer, Permission>
{

  List<Permission> getPermissions(List<Integer> groupIds) throws ServiceException;

}
