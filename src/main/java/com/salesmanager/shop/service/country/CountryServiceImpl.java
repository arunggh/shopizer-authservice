package com.salesmanager.shop.service.country;

import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.reference.country.Country;
import com.salesmanager.shop.model.reference.country.CountryDescription;
import com.salesmanager.shop.model.reference.language.Language;
import com.salesmanager.shop.repositories.CountryRepository;
import com.salesmanager.shop.service.persistence.SalesManagerEntityServiceImpl;
import com.salesmanager.shop.utils.CacheUtils;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("countryService")
public class CountryServiceImpl extends SalesManagerEntityServiceImpl<Integer, Country>
		implements CountryService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CountryServiceImpl.class);
	
	private CountryRepository countryRepository;
	
	@Inject
	private CacheUtils cache;

	
	@Inject
	public CountryServiceImpl(CountryRepository countryRepository) {
		super(countryRepository);
		this.countryRepository = countryRepository;
	}
	
	public Country getByCode(String code) throws ServiceException {
		return countryRepository.findByIsoCode(code);
	}

	@Override
	public Map<String,Country> getCountriesMap(Language language) throws ServiceException {
		
		List<Country> countries = this.getCountries(language);
		
		Map<String,Country> returnMap = new LinkedHashMap<String,Country>();
		
		for(Country country : countries) {
			returnMap.put(country.getIsoCode(), country);
		}
		
		return returnMap;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Country> getCountries(Language language) throws ServiceException {
		
		List<Country> countries = null;
		try {

			countries = (List<Country>) cache.getFromCache("COUNTRIES_" + language.getCode());

		
		
			if(countries==null) {
			
				countries = countryRepository.listByLanguage(language.getId());
			
				//set names
				for(Country country : countries) {
					
					CountryDescription description = country.getDescriptions().iterator().next();
					country.setName(description.getName());
					
				}
				
				cache.putInCache(countries, "COUNTRIES_" + language.getCode());
			}

		} catch (Exception e) {
			LOGGER.error("getCountries()", e);
		}
		
		return countries;
		
		
	}

}
