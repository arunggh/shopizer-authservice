package com.salesmanager.shop.service.merchant;

import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.merchant.MerchantConfiguration;
import com.salesmanager.shop.model.merchant.MerchantStore;
import com.salesmanager.shop.service.persistence.SalesManagerEntityService;

public interface MerchantConfigurationService extends
	SalesManagerEntityService<Long, MerchantConfiguration>
{
	
	MerchantConfiguration getMerchantConfiguration(String key, MerchantStore store) throws ServiceException;
	
}
