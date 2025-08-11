package com.sky.order.constants;

public final class TextConstants {

    // 私有构造方法，防止实例化
    private TextConstants() {
        throw new AssertionError("禁止实例化常量类");
    }

    // 通用提示信息
    public static final String SUCCESS = "操作成功";
    public static final String FAIL = "操作失败";
    public static final String SYSTEM_ERROR = "系统异常，请稍后重试";
    public static final String PARAM_ERROR = "参数错误";
    public static final String NOT_NULL = "不能为空";
    public static final String QUERY_SUCCESS = "查询成功";
    public static final String QUERY_EMPTY = "查询结果为空";
    public static final String ORDER_NULL = "订单不能为空";
    public static final String USER_ID_NULL = "用户ID不能为空";
    public static final String PRODUCT_NULL = "商品列表不能为空";
    public static final String MOBILE_NULL = "手机号不能为空";
    public static final String NICK_NAME_NULL = "昵称不能为空";
    public static final String ADDRESS_NULL = "地址不能为空";

    // 按钮文字
    public static final String BTN_SUBMIT = "提交";
    public static final String BTN_CANCEL = "取消";
    public static final String BTN_SAVE = "保存";
    public static final String BTN_DELETE = "删除";
    public static final String BTN_EDIT = "编辑";
    public static final String BTN_VIEW = "查看";
    public static final String BTN_BACK = "返回";

    // 状态信息
    public static final String STATUS_ENABLE = "启用";
    public static final String STATUS_DISABLE = "禁用";
    public static final String STATUS_PENDING = "待处理";
    public static final String STATUS_COMPLETED = "已完成";
    public static final String STATUS_PROCESSING = "处理中";
    public static final Integer PAY_STATUS_UNPAID = 0;
    public static final Integer PAY_STATUS_PAID = 1;
    public static final Integer PAY_STATUS_REFUND = 2;
    public static final Integer SHIP_STATUS_UNSHIPPED = 3;
    public static final Integer SHIP_STATUS_SHIPPED = 4;

    // 提示框标题
    public static final String TITLE_WARNING = "警告";
    public static final String TITLE_CONFIRM = "确认";
    public static final String TITLE_INFO = "提示";
    public static final String TITLE_ERROR = "错误";

    // 确认信息
    public static final String CONFIRM_DELETE = "确定要删除吗？";
    public static final String CONFIRM_SUBMIT = "确定要提交吗？";
    public static final String CONFIRM_LOGOUT = "确定要退出登录吗？";
}
