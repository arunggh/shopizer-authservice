package com.salesmanager.shop.model.product.image;

import com.salesmanager.shop.constants.SchemaConstant;
import com.salesmanager.shop.model.common.description.Description;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="PRODUCT_IMAGE_DESCRIPTION", schema=SchemaConstant.SALESMANAGER_SCHEMA, uniqueConstraints={
		@UniqueConstraint(columnNames={
			"PRODUCT_IMAGE_ID",
			"LANGUAGE_ID"
		})
	}
)
public class ProductImageDescription extends Description
{
	private static final long serialVersionUID = 247514890386076337L;
	
	@ManyToOne(targetEntity = ProductImage.class)
	@JoinColumn(name = "PRODUCT_IMAGE_ID", nullable = false)
	private ProductImage productImage;
	
	@Column(name="ALT_TAG", length=100)
	private String altTag;

	public ProductImage getProductImage() {
		return productImage;
	}

	public void setProductImage(ProductImage productImage) {
		this.productImage = productImage;
	}

	public String getAltTag() {
		return altTag;
	}

	public void setAltTag(String altTag) {
		this.altTag = altTag;
	}


}
