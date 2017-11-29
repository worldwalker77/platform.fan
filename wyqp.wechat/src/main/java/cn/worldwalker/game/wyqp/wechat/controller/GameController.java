package cn.worldwalker.game.wyqp.wechat.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.worldwalker.game.wyqp.wechat.common.utils.CustomizedPropertyConfigurer;
import cn.worldwalker.game.wyqp.wechat.common.utils.DateUtil;
import cn.worldwalker.game.wyqp.wechat.common.utils.RequestUtil;
import cn.worldwalker.game.wyqp.wechat.domain.GameModel;
import cn.worldwalker.game.wyqp.wechat.domain.GameQuery;
import cn.worldwalker.game.wyqp.wechat.domain.Result;
import cn.worldwalker.game.wyqp.wechat.service.GameService;

@Controller
@RequestMapping("game/")
public class GameController {
	private final static String CUR_COMPANY = CustomizedPropertyConfigurer.getContextProperty("cur.company");
	@Autowired
	private GameService gameService;
	
	@RequestMapping("company/index")
	public ModelAndView gamePlayIndex(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/" + CUR_COMPANY + "/company/index");
		return mv;
	}
	
	@RequestMapping("download/index")
	public ModelAndView gameMallIndex(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/" + CUR_COMPANY + "/download/index");
		return mv;
	}
	
	@RequestMapping("proxy/index")
	public ModelAndView gameProxyIndex(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("isAdmin", gameService.isAdmin());
		mv.setViewName("wechat/" + CUR_COMPANY + "/proxy/index");
		return mv;
	}
	
	@RequestMapping("proxy/proxyInfo")
	public ModelAndView gameProxyHome(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("realName", RequestUtil.getUserSession().getRealName());
		mv.addObject("proxyId", RequestUtil.getUserSession().getProxyId());
		mv.addObject("nickName", RequestUtil.getUserSession().getNickName());
		mv.addObject("mobilePhone", RequestUtil.getUserSession().getMobilePhone());
		mv.addObject("wechatNum", RequestUtil.getUserSession().getWechatNum());
		GameQuery gameQuery = new GameQuery();
		gameQuery.setProxyId(RequestUtil.getProxyId());
		Result result = gameService.getProxyInfo(gameQuery);
		if (result.getCode() == 0) {
			GameModel gameModel = (GameModel)result.getData();
			mv.addObject("totalIncome", gameModel.getTotalIncome());
			mv.addObject("extractAmount", gameModel.getExtractAmount());
			mv.addObject("remainderAmount", gameModel.getRemainderAmount());
		}
		mv.setViewName("wechat/" + CUR_COMPANY + "/proxy/proxyInfo");
		return mv;
	}
	
	@RequestMapping("proxy/getProxyInfo")
	@ResponseBody
	public Result getProxyInfo(){
		GameQuery gameQuery = new GameQuery();
		gameQuery.setProxyId(RequestUtil.getProxyId());
		return gameService.getProxyInfo(gameQuery);
	}
	
	@RequestMapping("proxy/billingDetails")
	public ModelAndView gameProxyBillingDetails(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/" + CUR_COMPANY + "/proxy/billingDetails");
		Date endDate = new Date();
		Date startDate = DateUtil.getNDayBefore(endDate, 30);
		mv.addObject("startDate", DateUtil.getDateFormat(startDate));
		mv.addObject("endDate", DateUtil.getDateFormat(endDate));
		return mv;
	}
	@RequestMapping("proxy/getBillingDetails")
	@ResponseBody
	public Result getBillingDetails(@RequestBody GameQuery gameQuery){
		return gameService.getBillingDetails(gameQuery);
	}
	
	@RequestMapping("proxy/myMembers")
	public ModelAndView gameProxyMyMembers(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/" + CUR_COMPANY + "/proxy/myMembers");
		Date endDate = new Date();
		Date startDate = DateUtil.getNDayBefore(endDate, 30);
		mv.addObject("startDate", DateUtil.getDateFormat(startDate));
		mv.addObject("endDate", DateUtil.getDateFormat(endDate));
		return mv;
	}
	@RequestMapping("proxy/getMyMembers")
	@ResponseBody
	public Result getMyMembers(@RequestBody GameQuery gameQuery){
		return gameService.getMyMembers(gameQuery);
	}
	/**
	 * 提现记录
	 * @return
	 */
	@RequestMapping("proxy/withDrawalRecords")
	public ModelAndView gameWithDrawalRecords(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/" + CUR_COMPANY + "/proxy/withDrawalRecords");
		Date endDate = new Date();
		Date startDate = DateUtil.getNDayBefore(endDate, 30);
		mv.addObject("startDate", DateUtil.getDateFormat(startDate));
		mv.addObject("endDate", DateUtil.getDateFormat(endDate));
		return mv;
	}
	@RequestMapping("proxy/getWithDrawalRecords")
	@ResponseBody
	public Result getWithDrawalRecords(@RequestBody GameQuery gameQuery){
		return gameService.getWithDrawalRecords(gameQuery);
	}
	
	/**
	 * 概率控制
	 * @param gameQuery
	 * @return
	 */
	@RequestMapping("proxy/getWinProbability")
	@ResponseBody
	public Result getWinProbability(@RequestBody GameQuery gameQuery){
		return gameService.getWinProbability(gameQuery);
	}
	
	@RequestMapping("proxy/winProbability")
	public ModelAndView winProbability(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/" + CUR_COMPANY + "/proxy/winProbability");
		return mv;
	}
	
	@RequestMapping("proxy/modifyWinProbability")
	@ResponseBody
	public Result modifyWinProbability(@RequestBody GameQuery gameQuery){
		return gameService.modifyWinProbability(gameQuery);
	}
	
	/**
	 * 赠送房卡页面
	 * @return
	 */
	@RequestMapping("proxy/giveAwayRoomCards")
	public ModelAndView giveAwayRoomCards(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/proxy/giveAwayRoomCards");
		return mv;
	}
	
	@RequestMapping("proxy/doGiveAwayRoomCards")
	@ResponseBody
	public Result doGiveAwayRoomCards(Integer toPlayerId, Integer roomCardNum){
		return gameService.doGiveAwayRoomCards(toPlayerId, roomCardNum);
	}
	
	@RequestMapping("proxy/getUserByPlayerId")
	@ResponseBody
	public Result getUserByPlayerId(Integer playerId){
		GameQuery gameQuery = new GameQuery();
		gameQuery.setPlayerId(playerId);
		return gameService.getUserByCondition(gameQuery);
	}
}
