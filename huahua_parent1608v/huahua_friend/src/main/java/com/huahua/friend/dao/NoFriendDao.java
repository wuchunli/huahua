package com.huahua.friend.dao;

import com.huahua.friend.entity.Friend;
import com.huahua.friend.entity.NOFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoFriendDao extends JpaRepository<NOFriend,String>, JpaSpecificationExecutor<NOFriend> {

}
