package com.oraro.panoramicagriculture.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/2/7.
 */
@Entity
public class UserEntity {
    @Id(autoincrement = true)
    private Long userId;//用户id，自增
    private String userName;//用户名
    private String password;//密码
    private String nickName;//昵称，建议真实姓名
    private Long userRole;//用户角色；游客：0，农民：1，采购商：2，监管：3
    private String phoneNumber;//电话号码
    private String identityNumber;//身份证号
    private int sex;//性别；男：1，女：0
    private int userGroup;//用户组
    private String head;//用户头像
    private int points;//积分
    private String clientId;
    public int getPoints() {
        return this.points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public String getHead() {
        return this.head;
    }
    public void setHead(String head) {
        this.head = head;
    }
    public int getUserGroup() {
        return this.userGroup;
    }
    public void setUserGroup(int userGroup) {
        this.userGroup = userGroup;
    }
    public int getSex() {
        return this.sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public String getIdentityNumber() {
        return this.identityNumber;
    }
    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Long getUserRole() {
        return this.userRole;
    }
    public void setUserRole(Long userRole) {
        this.userRole = userRole;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getClientId() {
        return this.clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    @Generated(hash = 292492919)
    public UserEntity(Long userId, String userName, String password, String nickName,
            Long userRole, String phoneNumber, String identityNumber, int sex, int userGroup,
            String head, int points, String clientId) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.userRole = userRole;
        this.phoneNumber = phoneNumber;
        this.identityNumber = identityNumber;
        this.sex = sex;
        this.userGroup = userGroup;
        this.head = head;
        this.points = points;
        this.clientId = clientId;
    }
    @Generated(hash = 1433178141)
    public UserEntity() {
    }
    
}
