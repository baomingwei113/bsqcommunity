package com.bao.community.mapper;

import com.bao.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @auther Bao
 * @date 2019/12/3 13:49
 */
@Mapper
public interface UserMapper {

    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert (User user);

    /**
     * 查询数据库中的token
     * @param token
     * @return
     */
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

}
