package stephen.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import stephen.entity.ProductCategory;

public interface ProdCateDao {

	/**
	 * 根据店铺id查询该店铺下的商品类别列表
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProdCateByShopId(Long shopId);
	
	/**
	 * 批量插入商品类别，得到插入的行数
	 * @param prodCateList
	 * @return
	 */
	int batchInsertProdCate(List<ProductCategory> prodCateList);
	
	/**
	 * 根据类别id和店铺id删除商品类别（虽然只由类别id就可以唯一确定，但为了防止删错店铺）
	 * @param id
	 * @param shopId
	 * @return
	 */
	int deleteProdCate(@Param("pcId")Long id , @Param("shopId") Long shopId);
}
