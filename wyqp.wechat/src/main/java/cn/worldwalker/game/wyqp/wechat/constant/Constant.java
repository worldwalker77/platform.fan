package cn.worldwalker.game.wyqp.wechat.constant;

import cn.worldwalker.game.wyqp.wechat.common.utils.CustomizedPropertyConfigurer;

public class Constant {
	public final static String isUseRedis = CustomizedPropertyConfigurer.getContextProperty("is.use.redis");
	public static final String sendRedPack ="https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
}
