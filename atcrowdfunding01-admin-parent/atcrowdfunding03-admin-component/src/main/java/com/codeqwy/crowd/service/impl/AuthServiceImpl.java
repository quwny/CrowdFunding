package com.codeqwy.crowd.service.impl;

import com.codeqwy.crowd.entity.Auth;
import com.codeqwy.crowd.entity.AuthExample;
import com.codeqwy.crowd.mapper.AuthMapper;
import com.codeqwy.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author CodeQwy
 * @Date 2022/1/18 14:17
 * @Description AuthServiceImpl
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    @Override
    public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {
        // 1.获取roleId 的值
        List<Integer> roleIdList = map.get("roleId");
        Integer roleId = roleIdList.get(0);
        // 2.删除旧关联关系数据
        authMapper.deleteOldRelationship(roleId);
        // 3.获取authIdList
        List<Integer> authIdList = map.get("authIdList");
        // 4.判断authIdList 是否有效
        if (authIdList != null && authIdList.size() > 0) {
            authMapper.insertNewRelationship(roleId, authIdList);
        }
    }

    @Override
    public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
        return authMapper.selectAssignedAuthNameByAdminId(adminId);
    }
}
