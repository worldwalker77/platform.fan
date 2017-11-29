package cn.worldwalker.game.wyqp.wechat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.worldwalker.game.wyqp.wechat.domain.GameQuery;
import cn.worldwalker.game.wyqp.wechat.domain.Result;
import cn.worldwalker.game.wyqp.wechat.service.GameService;

@Controller
public class LoginController {
	@Autowired
	private GameService gameService;
	
	@RequestMapping("login/index")
	public ModelAndView index(String redirectUrl,HttpServletResponse response, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		mv.addObject("redirectUrl", redirectUrl);
		mv.setViewName("wechat/proxy/login");
		return mv;
	}
	
	@RequestMapping("login/doLogin")
	@ResponseBody
	public Result doLogin(String mobilePhone, String password){
		GameQuery gameQuery = new GameQuery();
		gameQuery.setMobilePhone(mobilePhone);
		gameQuery.setPassword(password);
		return gameService.doLogin(gameQuery);
		
	}
	
	
}
