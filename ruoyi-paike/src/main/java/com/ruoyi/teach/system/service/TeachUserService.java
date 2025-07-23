package com.ruoyi.teach.system.service;

import com.ruoyi.common.core.domain.entity.SysUser;

public interface TeachUserService {

    @Deprecated
    SysUser createUser(String userName, String nickName);
    @Deprecated
    SysUser createUser(String userName, String nickName, String password);

    SysUser createUser(SysUser user);

    int insertUser(SysUser user);

    int updateUser(SysUser user);

    SysUser selectUserByUserName(String username);

    void activeUserById(Long userId);

    void insertUserRole(SysUser user);

    SysUser selectUserById(Long userId);

    void updateUser(Long userId, String userName, String nickName);
}
