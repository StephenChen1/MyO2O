package stephen.service;

import java.util.List;

import stephen.dto.ProdCateExecution;
import stephen.entity.ProductCategory;

public interface ProductCategoryService {

	/**
	 * 根据店铺id查询该店铺的所有商品类别列表
	 * @param shopId
	 * @return
	 */
	ProdCateExecution getProdCateByShopId(Long shopId);
	
	/**
	 * 批量增加商品类别
	 * @param prodCateList
	 * @return
	 */
	ProdCateExecution batchAddProdCate(List<ProductCategory> prodCateList);
	
	/**
	 * 根据类别id和店铺id删除该商品类别
	 * @param prodCateId
	 * @param shopId
	 * @return
	 */
	ProdCateExecution deleteProdCate(Long prodCateId , Long shopId);
	
}
