package com.salesmanager.shop.service.image;

import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.enums.FileContentType;
import com.salesmanager.shop.model.merchant.MerchantStore;
import java.util.Properties;
import javax.annotation.Resource;

public abstract class AbstractimageFilePath implements ImageFilePath {


	public abstract String getBasePath();

	public abstract void setBasePath(String basePath);
	
	protected static final String CONTEXT_PATH = "CONTEXT_PATH";
	
	public @Resource(name="shopizer-properties") Properties properties = new Properties();//shopizer-properties

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * Builds a merchant store logo path
	 * @param store
	 * @return
	 */
	public String buildStoreLogoFilePath(MerchantStore store) {
		return new StringBuilder().append(getBasePath()).append(Constants.FILES_URI).append(Constants.SLASH).append(store.getCode()).append(Constants.SLASH).append(FileContentType.LOGO).append(
            Constants.SLASH)
				.append(store.getStoreLogo()).toString();
	}
	

}
