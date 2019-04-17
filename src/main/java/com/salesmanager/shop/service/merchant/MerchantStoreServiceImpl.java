package com.salesmanager.shop.service.merchant;

import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.merchant.MerchantStore;
import com.salesmanager.shop.repositories.MerchantRepository;
import com.salesmanager.shop.service.persistence.SalesManagerEntityServiceImpl;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("merchantService")
public class MerchantStoreServiceImpl extends SalesManagerEntityServiceImpl<Integer, MerchantStore>
		implements MerchantStoreService {

	private MerchantRepository merchantRepository;
	
	@Inject
	public MerchantStoreServiceImpl(MerchantRepository merchantRepository) {
		super(merchantRepository);
		this.merchantRepository = merchantRepository;
	}

	@Override
	public MerchantStore getByCode(String code) throws ServiceException {
		
		return merchantRepository.findByCode(code);
	}

/*	@Override
	public void delete(MerchantStore merchant) throws ServiceException {
		
		merchant = this.getById(merchant.getId());
		
		
		//reference
		List<Manufacturer> manufacturers = manufacturerService.listByStore(merchant);
		for(Manufacturer manufacturer : manufacturers) {
			manufacturerService.delete(manufacturer);
		}
		
		List<MerchantConfiguration> configurations = merchantConfigurationService.listByStore(merchant);
		for(MerchantConfiguration configuration : configurations) {
			merchantConfigurationService.delete(configuration);
		}
		

		//TODO taxService
		List<TaxClass> taxClasses = taxClassService.listByStore(merchant);
		for(TaxClass taxClass : taxClasses) {
			taxClassService.delete(taxClass);
		}
		
		//content
		contentService.removeFiles(merchant.getCode());
		//TODO staticContentService.removeImages
		
		//category / product
		List<Category> categories = categoryService.listByStore(merchant);
		for(Category category : categories) {
			categoryService.delete(category);
		}

		//users
		List<User> users = userService.listByStore(merchant);
		for(User user : users) {
			userService.delete(user);
		}
		
		//customers
		List<Customer> customers = customerService.listByStore(merchant);
		for(Customer customer : customers) {
			customerService.delete(customer);
		}
		
		//orders
		List<Order> orders = orderService.listByStore(merchant);
		for(Order order : orders) {
			orderService.delete(order);
		}
		
		super.delete(merchant);
		
	}*/

}
