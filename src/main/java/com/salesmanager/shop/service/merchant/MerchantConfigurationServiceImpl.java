package com.salesmanager.shop.service.merchant;

import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.merchant.MerchantConfiguration;
import com.salesmanager.shop.model.merchant.MerchantStore;
import com.salesmanager.shop.repositories.MerchantConfigurationRepository;
import com.salesmanager.shop.service.persistence.SalesManagerEntityServiceImpl;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("merchantConfigurationService")
public class MerchantConfigurationServiceImpl extends
	SalesManagerEntityServiceImpl<Long, MerchantConfiguration> implements
		MerchantConfigurationService {

	private MerchantConfigurationRepository merchantConfigurationRepository;
	
	@Inject
	public MerchantConfigurationServiceImpl(
			MerchantConfigurationRepository merchantConfigurationRepository) {
			super(merchantConfigurationRepository);
			this.merchantConfigurationRepository = merchantConfigurationRepository;
	}
	

	@Override
	public MerchantConfiguration getMerchantConfiguration(String key, MerchantStore store) throws ServiceException
	{
		return merchantConfigurationRepository.findByMerchantStoreAndKey(store.getId(), key);
	}
	
}
