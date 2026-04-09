package com.ruoyi.teach.system.service.impl;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.teach.system.service.TeachUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TeachUserServiceImpl implements TeachUserService {
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService sysRoleService;
    //    @Autowired
//    private CasClientService casClientService;
    @Value("${cas.enabled:false}")
    private Boolean casEnabled;

    @Override
    public int insertUser(SysUser user) {
        // 同步到cas
//        if (Boolean.TRUE.equals(casEnabled)) {
//            CasUser casUser = getCasUser(user);
//            casClientService.createUser(casUser);
//        }
        if (StringUtils.isEmpty(user.getPassword())) {
            user.setPassword("123456@Yz");
        }
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return sysUserService.insertUser(user);
    }

//    private CasUser getCasUser(SysUser user) {
//        CasUser casUser = new CasUser();
//        casUser.setUsername(user.getUserName());
//        casUser.setName(user.getNickName());
//        casUser.setPassword(user.getPassword());
//        if (user.getUserId() != null) {
//            SysUser sysUser = userService.selectUserById(user.getUserId());
//            casUser.setUsername_origin(sysUser.getUserName());
//        }
//        return casUser;
//    }

    @Override
    public int updateUser(SysUser user) {
        if (StringUtils.isNotEmpty(user.getPhonenumber())) {
            Assert.isTrue(sysUserService.checkPhoneUnique(user),
                    "修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        if (StringUtils.isNotEmpty(user.getEmail())) {
            Assert.isTrue(sysUserService.checkEmailUnique(user),
                    "修改用户'" + user.getEmail() + "'失败，邮箱账号已存在");
        }
        Assert.isTrue(sysUserService.checkUserNameUnique(user),
                "修改用户'" + user.getUserName() + "'失败，用户名已存在");
//        if (Boolean.TRUE.equals(casEnabled)) {
//            CasUser casUser = getCasUser(user);
//            casClientService.updateUser(casUser);
//        }
        return sysUserService.updateUser(user);
    }

    @Override
    public SysUser selectUserByUserName(String username) {
        return sysUserService.selectUserByUserName(username);
    }

    @Override
    public void activeUserById(Long userId) {
//        sysUserService.activeUserById(userId);
    }

    @Override
    public void insertUserRole(SysUser user) {
//        sysUserService.insertUserRole(user);
    }

    @Override
    public SysUser selectUserById(Long userId) {
        return sysUserService.selectUserById(userId);
    }

    @Override
    public SysUser createUser(String userName, String nickName) {
        return createUser(userName, nickName, null);
    }

    @Override
    public SysUser createUser(String userName, String nickName, String password) {
        SysUser user = new SysUser();
        user.setUserName(userName);
        user.setNickName(nickName);
        user.setPassword(password);
//        user.setType(SysUser.TYPE_TEACHER);
        return createUser(user);
    }

    @Override
    public SysUser createUser(SysUser user) {
        SysUser sysUser = selectUserByUserName(user.getUserName());
        if (sysUser != null) {
            if ("2".equals(sysUser.getDelFlag())) {
                activeUserById(sysUser.getUserId());
                user.setUserId(sysUser.getUserId());
                insertUserRole(user);
            }
//            if (SysUser.TYPE_PARENT.equals(user.getType())) {
//                sysUserService.addRole(sysUser.getUserId(), "parent");
//            }
            return sysUser;
        }
        SysRole role = null;
//        if (SysUser.TYPE_TEACHER.equals(user.getType())) {
//            role = sysRoleService.selectRoleByName("教师");
//        } else if (SysUser.TYPE_STUDENT.equals(user.getType())) {
//            role = sysRoleService.selectRoleByName("学生");
//        } else if (SysUser.TYPE_PARENT.equals(user.getType())) {
//            role = sysRoleService.selectRoleByName("家长");
//        }

        user.setRoleIds(new Long[]{role.getRoleId()});
        try {
            user.setCreateBy(SecurityUtils.getUsername());
        } catch (Exception e) {
        }
        insertUser(user);
        return user;
    }

    @Override
    public void updateUser(Long userId, String userName, String nickName) {
        // 修改用户名
        SysUser user = selectUserById(userId);
        if (!user.getUserName().equals(userName)
                || !user.getNickName().equals(nickName)) {
            user.setUserName(userName);
            user.setNickName(nickName);
            updateUser(user);
        }
        if ("2".equals(user.getDelFlag())) {
            activeUserById(user.getUserId());
        }
    }
}
