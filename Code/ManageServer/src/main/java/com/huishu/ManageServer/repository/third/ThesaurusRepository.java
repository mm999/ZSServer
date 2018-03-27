package com.huishu.ManageServer.repository.third;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.huishu.ManageServer.config.TargetDataSource;
import com.huishu.ManageServer.entity.dbThird.ThesaurusEntity;

/**
 * @author hhy
 * @date 2018年3月16日
 * @Parem
 * @return 
 */
@TargetDataSource(name="third")
public interface ThesaurusRepository extends CrudRepository<ThesaurusEntity,Long> {

}