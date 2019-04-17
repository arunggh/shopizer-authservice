/**
 *
 */
package com.salesmanager.shop.service.customer;

import com.salesmanager.shop.dao.customer.PersistableCustomer;
import com.salesmanager.shop.model.customer.Customer;
import com.salesmanager.shop.model.merchant.MerchantStore;
import com.salesmanager.shop.model.reference.language.Language;

/**
 * <p>Customer facade working as a bridge between {@link CustomerService} and Controller
 * It will take care about interacting with Service API and doing any pre and post processing
 * </p>
 *
 * @author Umesh Awasthi
 * @version 2.2.1
 *
 *
 */
public interface CustomerFacade
{

    public Customer getCustomerByUserName(final String userName, final MerchantStore store) throws Exception;

    public PersistableCustomer  registerCustomer(final PersistableCustomer customer, final MerchantStore merchantStore, final Language language) throws Exception;

    public void setCustomerModelDefaultProperties(Customer customer, MerchantStore store) throws Exception;

	Customer getCustomerModel(
        PersistableCustomer customer,
        MerchantStore merchantStore, Language language) throws Exception;

	/**
	 * Reset customer password
	 * @param customer
	 * @param store
	 * @param language
	 * @throws Exception
	 */
	void resetPassword(Customer customer, MerchantStore store, Language language) throws Exception;

}
