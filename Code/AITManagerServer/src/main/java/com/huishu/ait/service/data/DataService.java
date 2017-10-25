package com.huishu.ait.service.data;

import com.huishu.ait.entity.common.AjaxResult;
import com.huishu.ait.es.entity.AITInfo;

/**
 * 管理文章数据
 * @author yindq
 * @date 2017/10/25
 */
public interface DataService {
	/**
     * 添加文章
     * @param info
     * @return
     */
    AjaxResult addData(AITInfo info);
}
