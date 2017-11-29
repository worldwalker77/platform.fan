package cn.worldwalker.game.wyqp.wechat.dao;

import java.util.List;
import java.util.Map;

import cn.worldwalker.game.wyqp.wechat.domain.TestModel;

public interface TestDao {
	
	public List<TestModel> test(Map<String, Object> map);
	
}
