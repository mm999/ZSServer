package com.huishu.ait.service.ExpertOpinion;


import java.util.List;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huishu.ait.es.entity.AITInfo;
import com.huishu.ait.es.entity.ExpertOpinionDTO;
import com.huishu.ait.es.entity.dto.ArticleListDTO;

/**
 * @author yxq
 *
 */
public interface ExpertOpinionService {
	// 根基热度和发布时间对查询查询结果进行排序
	JSONArray getExertOpinionList(ExpertOpinionDTO expertOpinionDTO);

	// 根据作者查询专家观点
	JSONArray findExpertOpinionByAuthor(ExpertOpinionDTO requestParam);

	// 收藏专家观点文章
	public JSONObject expertOpinionCollect(String id, Long userId);

	// 取消收藏专家观点文章
	public JSONObject cancelExpertOpinionCollect(String id, Long userId);

	/**
	 * 获取专家论和百家论的文章
	 * @param param
	 * @return
	 */
	Page<AITInfo> findExpertOpinionArticleList(JSONObject param);

	/**
	 * 查询文章详情
	 * @param id
	 * @return
	 */
	AITInfo findDetail(String id);
}
