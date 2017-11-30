package cn.worldwalker.game.wyqp.wechat.common.utils.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.worldwalker.game.wyqp.wechat.common.utils.JsonUtil;
import cn.worldwalker.game.wyqp.wechat.domain.UserSession;

@Component
public class RedisOperationService {
//	@Autowired
	private JedisTemplate jedisTemplate;
	public UserSession getUserInfo(String token){
		String temp = jedisTemplate.get(token);
		if (StringUtils.isEmpty(temp)) {
			return JsonUtil.toObject(temp, UserSession.class);
		}
		return null;
	}
}
