package com.baidu.shop.status;
/**
 * * @ClassName HTTPStatus
* @Description: TODO
* @Author shenyaqi
* @Date 2020/8/17
* @Version V1.0
**/

public class HTTPStatus {

    public static final int OK = 200;//成功

    public static final int ERROR = 500;//失败

    public static final int OPERATION_ERROR = 5001;//失败

    public static final int PARAMS_VALIDATE_ERROR = 5002;//参数校验失败

    public static final int USER_NOT_LOGIN = 301;//用户没有登录

}