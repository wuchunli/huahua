package com.huahua.friend.dao;

import com.huahua.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendDao extends JpaRepository<Friend,String>, JpaSpecificationExecutor<Friend> {
    /**
     * 更新为互相喜欢
     */
    @Modifying
    @Query(value = "update tb_friend set islike=?3 where userid =?1 and friendid=?2",nativeQuery = true)
    public void updateLike(String userid,String friendid,String islike);
    /**
     * 根据用户的id查询被关注的总数量
     */
    /**
     * 校验用户是否关注了当前的用户
     * @param friendid
     * @param userid
     * @return
     */

    @Query(nativeQuery = true,value = "select count(1) from tb_friend where friendid=?1 and userid=?2")
    public int selectByUserCount(String friendid,String userid);

    @Modifying
    @Query(value = "delete from tb_friend where userid=?1 and friendid=?2",nativeQuery = true)
    void deleteByuseridAndFriendid(@Param("userid") String userid,@Param("friendid") String friendid);
}
