package com.salesmanager.shop.service.image;

import com.salesmanager.shop.model.merchant.MerchantStore;

public interface ImageFilePath
{
	
	/**
	 * Context path configured in shopizer-properties.xml
	 * @return
	 */
	public String getContextPath();
	
	
	public String getBasePath();

	/**
	 * Builds a merchant store logo path
	 * @param store
	 * @return
	 */
	public String buildStoreLogoFilePath(MerchantStore store);

}
