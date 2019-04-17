package com.salesmanager.shop.service.group;

import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.user.Group;
import com.salesmanager.shop.model.user.GroupType;
import com.salesmanager.shop.service.persistence.SalesManagerEntityService;
import java.util.List;
import java.util.Set;

public interface GroupService extends SalesManagerEntityService<Integer, Group> {


	List<Group> listGroup(GroupType groupType) throws ServiceException;

}
