package com.salesmanager.shop.service.image;

import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.enums.FileContentType;
import com.salesmanager.shop.model.merchant.MerchantStore;
import org.springframework.stereotype.Component;

/**
 * To be used when working with shopizer servlet for managing images
 * 	<beans:bean id="img" class="com.salesmanager.shop.utils.LocalImageFilePathUtils">
		<beans:property name="basePath" value="/static" />
	</beans:bean>
 * @author c.samson
 *
 */
@Component
public class LocalImageFilePathUtils extends AbstractimageFilePath{
	
	private String basePath = Constants.STATIC_URI;

	@Override
	public String getBasePath() {
		return basePath;
	}

	@Override
	public void setBasePath(String context) {
		this.basePath = context;
	}
	
	/**
	 * Builds a merchant store logo path
	 * @param store
	 * @return
	 */
	public String buildStoreLogoFilePath(MerchantStore store) {
		return new StringBuilder().append(getBasePath()).append(Constants.FILES_URI).append(Constants.SLASH).append(store.getCode()).append("/").append(FileContentType.LOGO).append("/")
				.append(store.getStoreLogo()).toString();
	}
	
	@Override
	public String getContextPath() {
		return super.getProperties().getProperty(CONTEXT_PATH);
	}
	



}
