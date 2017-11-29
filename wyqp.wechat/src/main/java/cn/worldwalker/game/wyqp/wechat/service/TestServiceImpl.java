package cn.worldwalker.game.wyqp.wechat.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.worldwalker.game.wyqp.wechat.common.utils.redis.JedisTemplate;
import cn.worldwalker.game.wyqp.wechat.dao.TestDao;
import cn.worldwalker.game.wyqp.wechat.domain.TestModel;
@Service
public class TestServiceImpl implements TestService{
	@Autowired
	private TestDao testDao;
	@Autowired
	private JedisTemplate jedisTemplate;
	@Override
	public List<TestModel> getTest() {
		
		System.out.println(jedisTemplate.get("ddddd"));
		List<TestModel> list = testDao.test(new HashMap<String, Object>());
		return list;
	}
	
}
