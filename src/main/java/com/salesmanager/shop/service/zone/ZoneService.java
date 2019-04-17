package com.salesmanager.shop.service.zone;

import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.reference.language.Language;
import com.salesmanager.shop.model.reference.zone.Zone;
import com.salesmanager.shop.service.persistence.SalesManagerEntityService;
import java.util.Map;

public interface ZoneService extends SalesManagerEntityService<Long, Zone> {
	
	Zone getByCode(String code);

	Map<String, Zone> getZones(Language language) throws ServiceException;

}
