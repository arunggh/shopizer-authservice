package com.salesmanager.shop.service.store;

import com.salesmanager.shop.constants.CoreConstants;
import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.exception.ServiceRuntimeException;
import com.salesmanager.shop.model.merchant.MerchantStore;
import com.salesmanager.shop.service.merchant.MerchantStoreService;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("storeFacade")
public class StoreFacadeImpl implements StoreFacade {

  @Inject
  private MerchantStoreService merchantStoreService;

  private static final Logger LOG = LoggerFactory.getLogger(StoreFacadeImpl.class);

  @Override
  public MerchantStore getByCode(HttpServletRequest request) {
    String code = request.getParameter("store");
    if (StringUtils.isEmpty(code)) {
      code = CoreConstants.DEFAULT_STORE;
    }
    return get(code);
  }

  @Override
  public MerchantStore get(String code) {
    try {
      return merchantStoreService.getByCode(code);
    } catch (ServiceException e) {
      LOG.error("Error while getting MerchantStore", e);
      throw new ServiceRuntimeException(e);
    }

  }

}
