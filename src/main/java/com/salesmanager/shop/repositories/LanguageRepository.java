package com.salesmanager.shop.repositories;

import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.reference.language.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Integer>
{
	
	Language findByCode(String code) throws ServiceException;

}
