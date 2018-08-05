package stephen.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import stephen.dao.ShopDao;
import stephen.dto.ShopExecution;
import stephen.entity.Shop;
import stephen.enums.ShopStateEnum;
import stephen.exceptions.ShopOperationException;
import stephen.service.ShopService;
import stephen.util.ImageUtil;
import stephen.util.PageIndexConvertUtil;
import stephen.util.PathUtil;

@Service
public  class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao ;
	
	
	/**TODO 如果图片生成成功，但在子啊一步的往数据库填路径出错，那么这个店铺将不会出现在数据库中，但图片却会残留在那目录下
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
			shop.setPriority(20);//店铺初始权重暂时先均为20
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			//往数据库插入该店铺信息,影响行数存于effectedNum，店铺id存于shop对象
			int effectedNum = shopDao.insertShop(shop);
			//如果影响行数小于等于0，则抛出运行时异常，终止事务
			if(effectedNum <= 0){
				throw new ShopOperationException("店铺提交失败");
			}else{//添加成功
				System.out.println("添加店铺数据成功");
				if(shopImg != null){
					//调用方法将该图片存储到机器上,并得到存储路径
					try{
						addShopImg(shop,shopImg);
					}catch(Exception e){
						//System.out.println("添加图片异常");
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
		
		//将该图片写到机器，得到返回的字符串即图片的相对存储路径（包括文件扩展名）
		String imgRelativePath = ImageUtil.generateThumbnail(shopImg, dest);
		//将图片地址存进shop，在调用函数那会更新数据库的该字段
		shop.setImg(imgRelativePath);
	}


	/**
	 * 根据店铺id查询该店铺信息
	 */
	@Override
	public ShopExecution getShopById(Long shopId) {
		ShopExecution shopExecution = new ShopExecution();
		//先判断shopId是否为空
		if(shopId == null){
			shopExecution.setStateAndInfo(ShopStateEnum.NULL_SHOPID);
			return shopExecution ;
		}
		//调用dao获取
		Shop shop = shopDao.queryShop(shopId);
		//如果Shop为空，及查询没有结果
		if(shop == null){
			shopExecution.setStateAndInfo(ShopStateEnum.NULL_SHOP);
			return shopExecution;
		}
		//存入shopExecution
		shopExecution.setShop(shop);
		//返回值设置成功标志
		shopExecution.setStateAndInfo(ShopStateEnum.SUCCESS);
		return shopExecution;
	}


	/**
	 * 修改店铺信息
	 */
	@Override
	@Transactional
	public ShopExecution modifyShop(Shop shop, CommonsMultipartFile shopImg) {
		ShopExecution shopExecution = new ShopExecution();
		
		//判断图片是否为空，空则不对图片做改动，否则就删除旧图片，创建新图片，然后写入数据库
		if(shopImg != null){
			try{
				//先根据原路径删除原图片
				Shop tempShop = shopDao.queryShop(shop.getId());
				String oldImg = tempShop.getImg();				
				ImageUtil.deleteImgOrPath(oldImg);
				//王目录新增图片,新路径保存在shop中
				addShopImg(shop,shopImg);
			}catch(Exception e){
				//有异常则抛出,终止事务，并回滚（当且仅当抛出RuntimeException或其子异常，事务才会终止并回滚）
				throw new ShopOperationException("add shopImg error!" + e.getMessage());
			}
		}
		try{
			int effectedNum = shopDao.updateShop(shop);
			if(effectedNum <= 0){
				throw new ShopOperationException("店铺修改失败");
			}
		}catch(Exception e){
			throw new ShopOperationException("modify shop fail!");
		}
		//返回操作成功标志
		shopExecution.setStateAndInfo(ShopStateEnum.SUCCESS);
		return shopExecution;
	}


	/**
	 * 根据参数查询店铺列表
	 */
	@Override
	public ShopExecution getShopList(Shop shopCondition , int pageIndex , int pageSize) {
		ShopExecution shopExecution = new ShopExecution();
		//调用dao获取
		try{
			//转换页码
			int rowIndex = PageIndexConvertUtil.pageIndexToRowIndex(pageIndex, pageSize);
			List<Shop> shopList = shopDao.queryShopList(shopCondition,rowIndex,pageSize);
			//得到全部店铺数量
			int count = shopDao.queryShopCount(shopCondition);
			//将结果写入返回对象
			shopExecution.setShopList(shopList);
			shopExecution.setCount(count);
			//写入成功标志
			shopExecution.setStateAndInfo(ShopStateEnum.SUCCESS);
			
		}catch(Exception e){
			//异常则返回内部错误提示
			shopExecution.setStateAndInfo(ShopStateEnum.INNER_ERROR);
		}
		return shopExecution;
	}

}
