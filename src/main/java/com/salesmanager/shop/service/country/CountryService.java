package com.salesmanager.shop.service.country;

import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.reference.country.Country;
import com.salesmanager.shop.model.reference.language.Language;
import com.salesmanager.shop.service.persistence.SalesManagerEntityService;
import java.util.List;
import java.util.Map;

public interface CountryService extends SalesManagerEntityService<Integer, Country> {

	Map<String, Country> getCountriesMap(Language language)
			throws ServiceException;

    @SuppressWarnings("unchecked")
    List<Country> getCountries(Language language) throws ServiceException;
}
