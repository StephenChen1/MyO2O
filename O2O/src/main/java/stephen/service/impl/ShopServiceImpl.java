package stephen.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import stephen.dao.ShopDao;
import stephen.dto.ShopExecution;
import stephen.entity.Shop;
import stephen.enums.ShopStateEnum;
import stephen.exceptions.ShopOperationException;
import stephen.service.ShopService;
import stephen.util.ImageUtil;
import stephen.util.PathUtil;

public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao ;
	
	
	/**
	 * 添加店铺事务，参数为店铺对象和店铺图片，大致步骤如下：
	 * 1、初始化店铺对象某些值2、往数据库插入该店铺
	 * 3、将图片保存到文件路径中4、往数据库填上该图片存储路径
	 */
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) {
		//先判断shop是否为空
		if(shop == null){
			//为空则返回shop为空的信息
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try{
			//不为空则给shop初始一些信息
			shop.setEnableStatus(0);//店铺初始状态为0，表示待审核
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			//往数据库插入该店铺信息,影响行数存于effectedNum，店铺id存于shop对象
			int effectedNum = shopDao.insertShop(shop);
			//如果影响行数小于等于0，则抛出运行时异常，终止事务
			if(effectedNum <= 0){
				throw new ShopOperationException("店铺提交失败");
			}else{//添加成功
		
				if(shopImg != null){
					//调用方法将该图片存储到机器上,并得到存储路径
					try{
						addShopImg(shop,shopImg);
					}catch(Exception e){
						//抛出异常
						throw new ShopOperationException("add shopImg error!"+e.getMessage());
					}
					//更新数据库该店铺的图片字段
					effectedNum = shopDao.updateShop(shop);
					if(effectedNum <= 0){
						//如果更新失败，抛出异常
						throw new ShopOperationException("更新店铺图片失败！");
					}
				}
			}
		}catch(Exception e){
			//有异常则抛出,终止事务，并回滚（当且仅当抛出RuntimeException或其子异常，事务才会终止并回滚）
			throw new ShopOperationException("add shop error!" + e.getMessage());
		}
		//一切正常返回CHECK状态的shop
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}
	
	
	//保存图片到机器上
	private void addShopImg(Shop shop , CommonsMultipartFile shopImg){
		//根据shopId获取店铺存储相对值路径
		String dest = PathUtil.getShopImagePath(shop.getId());
		//将该图片写到机器
		ImageUtil.generateThumbnail(shopImg, dest);
	}

}
