## 3.基于现有的三层架构编写一个最简单的CRUD

### 	3.1controller

```asciiarmor
1、huike-admin模块中做为程序入口，放置了所有的controller
2、MybatisReviewController也已经创建过了，不要重复创建
```

```java
package com.huike.web.controller.review;

/**
 * 该Controller主要是为了复习三层架构以及Mybatis使用的，该部分接口已经放开权限，可以直接访问
 * 同学们在此处编写接口通过浏览器访问看是否能完成最简单的增删改查
 */
@RestController
@RequestMapping("/review")
public class MybatisReviewController extends BaseController {

    @Autowired
    private ReviewService reviewService;

    /**=========================================================新增数据============================================*/
    /**
     * 保存数据的方法
     * 同学需要将对应的方法补全，用于检验同学对三层架构的熟悉程度
     * 判断学员能否独立编写接口
     * @param name  姓名
     * @param age   年龄
     * @param name  性别（传入男,女）
     * @return
     */
    @GetMapping("/saveData/{name}/{age}/{sex}")
    public AjaxResult saveData(@PathVariable("name") String name,
                               @PathVariable("age") String age,
                               @PathVariable("sex")String sex){
        return AjaxResult.success(reviewService.saveData(name,age,sex));
    }

    /**
     * 保存数据的方法
     * 同学需要将对应的方法补全，用于检验同学对三层架构的熟悉程度
     * 判断学员能否独立编写接口
     * 注：
     * @param reviewVO 用户对象
     * @return
     */
    @PostMapping("/saveData")
    public AjaxResult saveData(@RequestBody MybatisReviewVO reviewVO){
        return AjaxResult.success(reviewService.saveData(reviewVO));
    }

    /**=========================================================删除数据=============================================*/
    /**
     * 删除数据的方法
     * @param id 传入主键id
     * @return
     */
    @DeleteMapping("/remove/{id}")
    public AjaxResult removeId(@PathVariable("id")Long id){
        return AjaxResult.success(reviewService.removeData(id));
    }

    /**=========================================================修改数据=============================================*/
    @PostMapping("/update")
    public AjaxResult update(@RequestBody MybatisReviewVO reviewVO){
        return AjaxResult.success(reviewService.updateData(reviewVO));
    }

    /**=========================================================查询数据=============================================*/
    /**
     * 根据id查询对应数据的方法
     * 同学需要将对应的方法补全，用于检验同学对三层架构的熟悉程度
     * 判断学员能否独立编写接口
     * @param id
     * @return
     */
    @GetMapping("/getById")
    //http://localhost:8094/review/getById?id=1
    public AjaxResult getDataById(Long id){
        return AjaxResult.success(reviewService.getDataById(id));
    }

    /**
     * 分页查询数据的方法
     * @return
     */
    @GetMapping("/getDataByPage")
    public TableDataInfo getDataByPage(){
        startPage();
        List<Review> list = reviewService.getDataByPage();
        return getDataTable(list);
    }
}
```

### 	3.2service

```java
package com.huike.review.service;

import com.huike.review.pojo.Review;
import com.huike.review.vo.MybatisReviewVO;

import java.util.List;

/**
 * mybatis复习接口层
 */
public interface ReviewService {

    /**
     * 根据id获取数据的方法
     * @param id
     * @return
     */
    public Review getDataById(Long id);


    /**
     * 根据传入的参数保存数据的方法
     * @param name
     * @param age
     * @param sex
     * @return
     */
    String saveData(String name, String age, String sex);

    /**
     * 方法重载：方法名相同 传递参数不同
     * @param user 前端传入对象
     * @return
     */
    String saveData(MybatisReviewVO user);


    /**
     * 删除数据的方法
     * @param id
     * @return
     */
    String removeData(Long id);

    /**
     * 修改数据的方法
     * @param reviewVO
     * @return
     */
    String updateData(MybatisReviewVO reviewVO);

    /**
     * 分页查询对应数据的方法
     * @return
     */
    List<Review> getDataByPage();
}

```

### 	3.3serviceimpl

```java
package com.huike.review.service.impl;

import com.huike.common.core.domain.AjaxResult;
import com.huike.common.exception.CustomException;
import com.huike.common.utils.bean.BeanUtils;
import com.huike.review.pojo.Review;
import com.huike.review.service.ReviewService;
import com.huike.review.mapper.MybatisReviewMapper;
import com.huike.review.vo.MybatisReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * mybatis复习使用的业务层
 * 注意该业务层和我们系统的业务无关，主要是让同学们快速熟悉系统的
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private MybatisReviewMapper reviewMapper;




    /**=========================================================保存数据的方法=============================================*/
    /**
     * 保存数据的方法
     * 需要结合异常处理
     * @param name
     * @param age
     * @param sex
     * @return
     */
    @Override
    public String saveData(String name, String age, String sex) {
        try {
            int i = reviewMapper.saveData(name, age, sex);
            //这里演示全局异常处理 GlobalExceptionHandler 对指定异常的处理
//          throw new Exception();
            return "成功插入:" + i + "条数据";
        }catch (Exception e){
            throw new CustomException("数据插入失败");
        }
    }

    /**
     * 该方法使用对象进行保存数据
     * 在该方法位置可以通过BeanUtils.copyProperties的方式进行对象属性的填充
      * @param reviewVO 前端传入对象
     * @return
     */
    @Override
    public String saveData(MybatisReviewVO reviewVO) {
        try {
            Review review = new Review();
            BeanUtils.copyProperties(reviewVO,review);
            int i = reviewMapper.saveDataByPojo(review);
            //这里演示全局异常处理 GlobalExceptionHandler 对指定异常的处理
//          throw new Exception();
            return "成功插入:" + i + "条数据";
        }catch (Exception e){
            throw new CustomException("数据插入失败");
        }
    }

    /**=========================================================删除数据=============================================*/
    /**
     * 删除数据的方法
     * @param id
     * @return
     */
    @Override
    public String removeData(Long id) {
        int i = reviewMapper.removeData(id);
        return "成功删除:" + i + "条数据";
    }

    /**=========================================================修改数据=============================================*/
    /**
     * 修改数据的方法
     * @param reviewVO
     * @return
     */
    @Override
    public String updateData(MybatisReviewVO reviewVO) {
        try {
            Review review = new Review();
            BeanUtils.copyProperties(reviewVO,review);
            reviewMapper.updateData(review);
            return "修改成功";
        }catch (Exception e){
            throw new CustomException("修改失败");
        }
    }

    /**=========================================================查询数据的方法=============================================*/
    /**
     * 根据id查询数据的方法
     * @param id
     * @return
     */
    @Override
    public Review getDataById(Long id) {
        return reviewMapper.getById(id);
    }

    /**
     * 分页查询数据的方法
      * @return
     */
    @Override
    public List<Review> getDataByPage() {
        return reviewMapper.getDataByPage();
    }
}

```

### 	3.4mapper

```java
package com.huike.review.mapper;

import com.huike.review.pojo.Review;
import com.huike.review.vo.MybatisReviewVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Mybatis复习的Mapper层
 */
public interface MybatisReviewMapper {


    /**======================================================新增======================================================**/
    /**
     * 通过简单参数的方式保存对象的方法
     * @param name
     * @param age
     * @param sex
     * @return
     */
    int saveData(@Param("name") String name,@Param("age") String age,@Param("sex") String sex);


    /**
     * 通过实体类保存数据的方法
     * @param data 实体类
     * @return
     */
    int saveDataByPojo(Review data);



    /**======================================================删除======================================================**/
    /**
     * 根据id删除操作
     * @param id
     * @return
     */
    Integer removeData(@Param("id")Long id);


    /**======================================================修改======================================================**/
    /**
     * 修改数据的方法
     * @param review
     * @return
     */
    int updateData(Review review);

    /**======================================================简单查询===================================================**/
    /**
     * 根据id查询对应的数据
     * @param id
     * @return
     */
    Review getById(@Param("id") Long id);

    /**
     * 分页查询数据的方法
     * @return
     */
    List<Review> getDataByPage();
}

```

### 	3.5xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.huike.review.mapper.MybatisReviewMapper">

    <sql id="reviewSQL">
        SELECT id,name,age,sex,create_by as createBy,create_time as
            createTime,update_time ,update_user as updateBy FROM `mybatis_review`
    </sql>

    <insert id="saveDataByPojo" parameterType="com.huike.review.pojo.Review">
        INSERT INTO `mybatis_review` (`name`,`age`,`sex`) VALUES (#{name},#{age},#{sex})
    </insert>

    <insert id="saveData">
        INSERT INTO `mybatis_review` (`name`,`age`,`sex`) VALUES (#{name},#{age},#{sex})
    </insert>


    <update id="updateData" parameterType="com.huike.review.pojo.Review">
        UPDATE `mybatis_review` SET `name` = #{name},age = #{age},sex = #{sex} WHERE id = #{id}
    </update>

    <delete id="removeData">
        DELETE FROM `mybatis_review` WHERE id = #{id}
    </delete>

    <select id="getById" resultType="com.huike.review.pojo.Review" parameterType="Long">
       <include refid="reviewSQL"></include> WHERE id = #{id}
    </select>
    <select id="getDataByPage" resultType="com.huike.review.pojo.Review">
        <include refid="reviewSQL"></include> ORDER BY createTime DESC
    </select>
</mapper>
```
