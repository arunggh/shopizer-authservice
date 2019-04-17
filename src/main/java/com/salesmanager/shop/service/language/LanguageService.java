package com.salesmanager.shop.service.language;

import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.merchant.MerchantStore;
import com.salesmanager.shop.model.reference.language.Language;
import com.salesmanager.shop.service.persistence.SalesManagerEntityService;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface LanguageService extends SalesManagerEntityService<Integer, Language> {

	Language getByCode(String code) throws ServiceException;

	Locale toLocale(Language language, MerchantStore store);

	Language toLanguage(Locale locale);

	Map<String,Language> getLanguagesMap() throws ServiceException;

	@SuppressWarnings("unchecked")
	List<Language> getLanguages() throws ServiceException;

	Language defaultLanguage();
}
