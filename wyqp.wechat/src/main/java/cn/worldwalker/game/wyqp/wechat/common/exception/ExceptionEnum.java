package cn.worldwalker.game.wyqp.wechat.common.exception;

public enum ExceptionEnum {
	
	SYSTEM_ERROR(-1, "系统异常"),
    SUCCESS(0, "成功"),
    NEED_LOGIN(1, "需要登录"),
    PARAMS_ERROR(2, "参数为空或者错误"),
    NEED_ROOM_ID(3, "需要创建房间后才能发语音"),
    ROOM_CARD_NOT_ENOUGH(4, "您的房卡不足"),
    GIVE_AWAY_ROOM_CARD_FAIL(5, "赠送房卡失败");

    public int  code;
    public String desc;

    private ExceptionEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
