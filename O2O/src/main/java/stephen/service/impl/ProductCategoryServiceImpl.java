package stephen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stephen.dao.ProdCateDao;
import stephen.dto.ProdCateExecution;
import stephen.entity.ProductCategory;
import stephen.enums.ProdCateStateEnum;
import stephen.service.ProductCategoryService;


@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProdCateDao prodCateDao ;
	
	
	@Override
	public ProdCateExecution getProdCateByShopId(Long shopId) {
		ProdCateExecution prodCateExecution = new ProdCateExecution();
		//判断shopId是否为空
		if(shopId == null){
			prodCateExecution.setStateEnum(ProdCateStateEnum.EMPTY_SHOPID);
			return prodCateExecution ;
		}
		//调用dao
		try{
			List<ProductCategory> list = prodCateDao.queryProdCateByShopId(shopId);
			System.out.println("类别长度："+ list.size());
			prodCateExecution.setProductCategoryList(list);
			prodCateExecution.setStateEnum(ProdCateStateEnum.SUCCESS);
		}catch(Exception e){
			System.out.println("service出现异常！");
			prodCateExecution.setStateEnum(ProdCateStateEnum.INNER_ERROR);
			return prodCateExecution ;
		}
		return prodCateExecution;
	}


	@Override
	public ProdCateExecution batchAddProdCate(List<ProductCategory> prodCateList) {
		ProdCateExecution prodCateExecution = new ProdCateExecution();
		//对参数进行非空和长度判断
		if(prodCateList == null || prodCateList.size() < 1){
			prodCateExecution.setStateEnum(ProdCateStateEnum.EMPTY_PRODCATE);
			return prodCateExecution;
		}
		try {
			// 将list传到dao层，由mybatis实现遍历插入
			int effectedNum = prodCateDao.batchInsertProdCate(prodCateList);
			if(effectedNum <= 0){
				prodCateExecution.setStateEnum(ProdCateStateEnum.INNER_ERROR);
			}else{
				prodCateExecution.setStateEnum(ProdCateStateEnum.SUCCESS);
			}
		} catch (Exception e) {
			prodCateExecution.setStateEnum(ProdCateStateEnum.INNER_ERROR);
			return prodCateExecution ;
		}
		return prodCateExecution;
	}


	@Override
	public ProdCateExecution deleteProdCate(Long prodCateId, Long shopId) {
		ProdCateExecution prodCateExecution = new ProdCateExecution();
		//对参数做空值判断
		if(prodCateId == null || shopId == null){
			prodCateExecution.setStateEnum(ProdCateStateEnum.EMPTY_SHOPID);
			return prodCateExecution ;
		}
		//调用dao
		try{
			int effectedNum = prodCateDao.deleteProdCate(prodCateId, shopId);
			if(effectedNum <= 0){
				prodCateExecution.setStateEnum(ProdCateStateEnum.INNER_ERROR);
			}else{
				prodCateExecution.setStateEnum(ProdCateStateEnum.SUCCESS);
			}
		}catch(Exception e){
			prodCateExecution.setStateEnum(ProdCateStateEnum.INNER_ERROR);
			return prodCateExecution ;
		}
		return prodCateExecution;
	}

}
