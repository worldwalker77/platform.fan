package cn.worldwalker.game.wyqp.common.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.worldwalker.game.wyqp.common.constant.Constant;
import cn.worldwalker.game.wyqp.common.dao.GameDao;
import cn.worldwalker.game.wyqp.common.exception.BusinessException;
import cn.worldwalker.game.wyqp.common.exception.ExceptionEnum;
import cn.worldwalker.game.wyqp.common.result.Result;
import cn.worldwalker.game.wyqp.common.utils.MD5Util1;
import cn.worldwalker.game.wyqp.common.utils.RequestUtil;

@Service
public class BackendServiceImpl implements BackendService{
	
	@Autowired
	private GameDao gameDao;
	@Autowired
	private BackendManager gameManager;
	
	private static Map<String, String> adminPhoneMap = new HashMap<String, String>();
	static{
		adminPhoneMap.put(Constant.adminMobile, Constant.adminMobile);
//		adminPhoneMap.put("13006339022", "13006339022");
	}
	@Override
	public boolean isAdmin(){
		String mobile = RequestUtil.getUserSession().getMobilePhone();
		return adminPhoneMap.containsKey(mobile);
	}
	@Override
	public Result doLogin(GameQuery gameQuery) {
		Result result = new Result();
		GameModel gameModel = gameDao.getProxyByPhoneAndPassword(gameQuery);
		if (gameModel == null) {
			result.setCode(1);
			result.setDesc("账号名或密码错误");
			return result;
		}
		UserSession userSession = new UserSession();
		userSession.setProxyId(gameModel.getProxyId());
		userSession.setPlayerId(gameModel.getPlayerId());
		userSession.setNickName(gameModel.getNickName());
		userSession.setRealName(gameModel.getRealName());
		userSession.setWechatNum(gameModel.getWechatNum());
		userSession.setMobilePhone(gameModel.getMobilePhone());
		RequestUtil.setUserSession(genToken(gameQuery.getMobilePhone()), userSession);
		return result;
	}
	public String genToken(String mobilePhone){
		String temp = mobilePhone + System.currentTimeMillis() + Thread.currentThread().getName();
		return MD5Util1.encryptByMD5(temp);
	}
	@Override
	public Result getProxyInfo(GameQuery gameQuery) {
		Result result = new Result();
		GameModel gameModel = null;
		try {
			gameModel = gameDao.getProxyInfo(gameQuery);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setDesc("系统异常");
			return result;
		}
		if (gameModel == null) {
			result.setCode(1);
			result.setDesc("代理信息不存在");
			return result;
		}
		result.setData(gameModel);
		return result;
	}

	@Override
	public Result getBillingDetails(GameQuery gameQuery) {
		if (!isAdmin()) {
			gameQuery.setProxyId(RequestUtil.getUserSession().getProxyId());
		}
		gameQuery.setStartDate(gameQuery.getStartDate() + " 00:00:00");
		gameQuery.setEndDate(gameQuery.getEndDate()  + " 23:59:59");
		Result result = new Result();
		List<GameModel> list = null;
		Long total = 0L;
		try {
			list = gameDao.getBillingDetails(gameQuery);
			total = gameDao.getBillingDetailsCount(gameQuery);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setDesc("系统异常");
			return result;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		result.setData(map);
		return result;
	}

	@Override
	public Result getMyMembers(GameQuery gameQuery) {
		if (!isAdmin()) {
			gameQuery.setProxyId(RequestUtil.getUserSession().getProxyId());
		}
		gameQuery.setStartDate(gameQuery.getStartDate() + " 00:00:00");
		gameQuery.setEndDate(gameQuery.getEndDate()  + " 23:59:59");
		Result result = new Result();
		List<GameModel> list = null;
		Long total = 0L;
		try {
			list = gameDao.getMyMembers(gameQuery);
			total = gameDao.getMyMembersCount(gameQuery);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setDesc("系统异常");
			return result;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		result.setData(map);
		return result;
	}

	@Override
	public Result getWithDrawalRecords(GameQuery gameQuery) {
		if (!isAdmin()) {
			gameQuery.setProxyId(RequestUtil.getUserSession().getProxyId());
		}
		gameQuery.setStartDate(gameQuery.getStartDate() + " 00:00:00");
		gameQuery.setEndDate(gameQuery.getEndDate()  + " 23:59:59");
		Result result = new Result();
		List<GameModel> list = null;
		Long total = 0L;
		try {
			list = gameDao.getWithDrawalRecords(gameQuery);
			total = gameDao.getWithDrawalRecordsCount(gameQuery);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setDesc("系统异常");
			return result;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		result.setData(map);
		return result;
	}
	@Override
	public Result getUserByCondition(GameQuery gameQuery) {
		List<GameModel> modelList = gameDao.getUserByCondition(gameQuery);
		Result result = new Result();
		result.setData(modelList);
		return result;
	}
	@Override
	public Result doGiveAwayRoomCards(Integer toPlayerId, Integer roomCardNum) {
		Result result = new Result();
		try {
			gameManager.giveAwayRoomCards(toPlayerId, roomCardNum);
		} catch (BusinessException e) {
			result.setCode(e.getBussinessCode());
			result.setDesc(e.getMessage());
		} catch (Exception e) {
			result.setCode(ExceptionEnum.SYSTEM_ERROR.index);
			result.setDesc(ExceptionEnum.SYSTEM_ERROR.description);
		}
		return result;
	}
	@Override
	public Result getWinProbability(GameQuery gameQuery) {
		Result result = new Result();
		/**如果不是管理员账号，则不让查看*/
		if (!isAdmin()) {
			result.setCode(1);
			result.setDesc("无权限");
			return result;
		}
		
		List<GameModel> list = null;
		Long total = 0L;
		try {
			list = gameDao.getUserByCondition(gameQuery);
			total = gameDao.getUserByConditionCount(gameQuery);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setDesc("系统异常");
			return result;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		result.setData(map);
		return result;
	}
	@Override
	public Result modifyWinProbability(GameQuery gameQuery) {
		Result result = new Result();
		/**如果不是管理员账号，则不让查看*/
		if (!isAdmin()) {
			result.setCode(1);
			result.setDesc("无权限");
			return result;
		}
		if (gameQuery.getWinProbability() < 0 || gameQuery.getWinProbability() > 100) {
			result.setCode(1);
			result.setDesc("概率范围为0-100");
			return result;
		}
		try {
			gameDao.updateProbabilityByPlayerId(gameQuery);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setDesc("系统异常");
			return result;
		}
		return result;
	}
	@Override
	public Result getProxys(GameQuery gameQuery) {
		if (!isAdmin()) {
			gameQuery.setProxyId(RequestUtil.getUserSession().getProxyId());
		}
		gameQuery.setStartDate(gameQuery.getStartDate() + " 00:00:00");
		gameQuery.setEndDate(gameQuery.getEndDate()  + " 23:59:59");
		Result result = new Result();
		List<GameModel> list = null;
		Long total = 0L;
		try {
			list = gameDao.getProxys(gameQuery);
			total = gameDao.getProxysCount(gameQuery);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setDesc("系统异常");
			return result;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		result.setData(map);
		return result;
	}

}
