package com.salesmanager.shop.service.email;

import com.salesmanager.shop.dao.Email;
import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.merchant.MerchantStore;

public interface EmailService {

	public void sendHtmlEmail(MerchantStore store, Email email) throws ServiceException, Exception;
	
	public EmailConfig getEmailConfiguration(MerchantStore store) throws ServiceException;
	
}
