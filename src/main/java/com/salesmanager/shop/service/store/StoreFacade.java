package com.salesmanager.shop.service.store;

import com.salesmanager.shop.model.merchant.MerchantStore;
import javax.servlet.http.HttpServletRequest;

/**
 * Layer between shop controllers, services and API with sm-core
 * 
 * @author carlsamson
 *
 */
public interface StoreFacade {

  /**
   * Find MerchantStore model from store code
   * 
   * @param code
   * @return
   * @throws Exception
   */
  MerchantStore getByCode(HttpServletRequest request);

  MerchantStore get(String code);
  
}
