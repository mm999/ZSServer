package com.huishu.ait.repository.garden;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.huishu.ait.entity.GardenData;

/**
 * @author ydw
 * @date 2017年7月29日
 * @Parem
 * @return
 * 
 */
public interface GardenRepository extends CrudRepository<GardenData, Integer> {

	Page<GardenData> findByAreaLikeAndIndustryLikeOrderByIdDesc(String area, String industry, Pageable pageable);

	/**
	 * 按照地域查询园区列表
	 * 
	 * @param area
	 * @return
	 */
	List<GardenData> findByAddressLikeAndIndustryLike(String area, String industry);

	GardenData findByGardenName(String gardenName);

}