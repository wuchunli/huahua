package com.huahua.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.huahua.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{
    /**
     * 推荐职位
     */
    public List<Recruit> findTop2AllByState(String state);
    /**
     * 最新的职位
     */
    @Query(nativeQuery = true,value = "select * from tb_recruit t where 1 = 1 and t.state <> 0 order by  t.createtime desc")
    public List<Recruit> queryByStateNewRecruitList(String state);
}
