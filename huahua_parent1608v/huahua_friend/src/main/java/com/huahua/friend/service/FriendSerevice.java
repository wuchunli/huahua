package com.huahua.friend.service;

import com.huahua.friend.dao.FriendDao;
import com.huahua.friend.dao.NoFriendDao;
import com.huahua.friend.entity.Friend;
import com.huahua.friend.entity.NOFriend;
import eureka.UserEureka;
import huahua.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

@Service
public class FriendSerevice {
    @Autowired
    FriendDao friendDao;
    @Autowired
    NoFriendDao noFriendDao;
    @Autowired
    UserEureka userEureka;

    @Transactional
    public int addFriend(String userid, String friendid) {
        //A喜欢B  则不进行任何操作,并且返回0
        if (friendDao.selectByUserCount(userid, friendid) > 0) {
            return 0;
        }
        //如果没有添加  向喜欢表中添加
        Friend friend = new Friend();
        friend.setUserId(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        //判断b喜欢a  ----->  islike  1  ab互粉成功
        if (friendDao.selectByUserCount(friendid, userid) > 0) {//如果>0说明friendid关注了userid
            friendDao.updateLike(userid, friendid, "1");
            friendDao.updateLike(friendid, userid, "1");
        }
        //调用spring cloud 微服务,修改用户表的关注数,粉丝数
        //user A:  关注数:1      粉丝数:1
        //user B:  关注数:1      粉丝数:1
        //首先是关注数A+1 和粉丝数b+1
        //user 修改关注数
        userEureka.incfollowCount(userid, 1);
        //friendId修改是粉丝数
        userEureka.incfansCount(friendid, 1);
        return 1;
    }

    @Transactional
    public void nolikeFriend(String userid, String friendid) {
        friendDao.deleteByuseridAndFriendid(userid, friendid);
        //判断是否互相关注
        //如果  b喜欢a  则修改friendid用户中islike
        if (friendDao.selectByUserCount(friendid, userid) > 0) {
            friendDao.updateLike(friendid, userid, "0");
        }
        //调用springboot cloud 微服务 修改用户表的关注数, 粉丝数
        //user 修改关注数
        userEureka.incfollowCount(userid, -1);
        //friendId修改是粉丝数
        userEureka.incfansCount(friendid, -1);


    }

    @Transactional
    public void delete(String userid, String friendid) {
        //tb_nofriend  表中插入一条记录
        NOFriend noFriend = new NOFriend();
        noFriend.setFriendid(friendid);
        noFriend.setUserId(userid);
        noFriendDao.save(noFriend);
        //调用springboot cloud 微服务 修改用户表的关注数, 粉丝数
        nolikeFriend(userid, friendid);

    }
}
