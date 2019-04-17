package com.salesmanager.shop.service.merchant;

import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.merchant.MerchantStore;
import com.salesmanager.shop.service.persistence.SalesManagerEntityService;

public interface MerchantStoreService extends SalesManagerEntityService<Integer, MerchantStore> {

	MerchantStore getByCode(String code) throws ServiceException;

}
