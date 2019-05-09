package com.huahua.friend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_friend")
@Data
@IdClass(NOFriend.class)
public class NOFriend implements Serializable {

    private static final long serialVersionUID = 1498912619597809794L;
    @Id
    private String userId;
    @Id
    private String friendid;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }


}
