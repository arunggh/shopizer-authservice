package com.salesmanager.shop.utils;

import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.exception.ServiceRuntimeException;
import com.salesmanager.shop.model.merchant.MerchantStore;
import com.salesmanager.shop.model.reference.language.Language;
import com.salesmanager.shop.service.language.LanguageService;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class LanguageUtils
{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Inject
	LanguageService languageService;
	
	/**
	 * Should be used by rest web services
	 * @param request
	 * @param store
	 * @return
	 * @throws Exception
	 */
	public Language getRESTLanguage(HttpServletRequest request, MerchantStore store) {
		
		Validate.notNull(request,"HttpServletRequest must not be null");
		Validate.notNull(store,"MerchantStore must not be null");

		try {
			Language language = null;

			String lang = request.getParameter(Constants.LANG);

			if (StringUtils.isBlank(lang)) {
				if (language == null) {
					language = languageService.defaultLanguage();
				}
			} else {
				language = languageService.getByCode(lang);
				if (language == null) {
				    language = languageService.defaultLanguage();
				}
			}

			return language;

		} catch (ServiceException e) {
			throw new ServiceRuntimeException(e);
		}
	}

}
