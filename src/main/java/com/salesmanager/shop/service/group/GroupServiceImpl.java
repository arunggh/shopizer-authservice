package com.salesmanager.shop.service.group;

import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.user.Group;
import com.salesmanager.shop.model.user.GroupType;
import com.salesmanager.shop.repositories.GroupRepository;
import com.salesmanager.shop.service.persistence.SalesManagerEntityServiceImpl;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("groupService")
public class GroupServiceImpl extends
	SalesManagerEntityServiceImpl<Integer, Group> implements GroupService {

	GroupRepository groupRepository;


	@Inject
	public GroupServiceImpl(GroupRepository groupRepository) {
		super(groupRepository);
		this.groupRepository = groupRepository;

	}


	@Override
	public List<Group> listGroup(GroupType groupType) throws ServiceException {
		try {
			return groupRepository.findByType(groupType);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
}
