package com.salesmanager.shop.service.zone;

import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.reference.language.Language;
import com.salesmanager.shop.model.reference.zone.Zone;
import com.salesmanager.shop.model.reference.zone.ZoneDescription;
import com.salesmanager.shop.repositories.ZoneRepository;
import com.salesmanager.shop.service.persistence.SalesManagerEntityServiceImpl;
import com.salesmanager.shop.utils.CacheUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("zoneService")
public class ZoneServiceImpl extends SalesManagerEntityServiceImpl<Long, Zone> implements
		ZoneService {
	
	private final static String ZONE_CACHE_PREFIX = "ZONES_";

	private ZoneRepository zoneRepository;
	
	@Inject
	private CacheUtils cache;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ZoneServiceImpl.class);

	@Inject
	public ZoneServiceImpl(ZoneRepository zoneRepository) {
		super(zoneRepository);
		this.zoneRepository = zoneRepository;
	}

	@Override
	public Zone getByCode(String code) {
		return zoneRepository.findByCode(code);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Zone> getZones(Language language) throws ServiceException {
		
		Map<String, Zone> zones = null;
		try {

			String cacheKey = ZONE_CACHE_PREFIX + language.getCode();
			
			zones = (Map<String, Zone>) cache.getFromCache(cacheKey);

		
		
			if(zones==null) {
				zones = new HashMap<String, Zone>();
				List<Zone> zns = zoneRepository.listByLanguage(language.getId());
			
				//set names
				for(Zone zone : zns) {
					ZoneDescription description = zone.getDescriptions().get(0);
					zone.setName(description.getName());
					zones.put(zone.getCode(), zone);
					
				}
				cache.putInCache(zones, cacheKey);
			}

		} catch (Exception e) {
			LOGGER.error("getZones()", e);
		}
		return zones;
		
		
	}

}
