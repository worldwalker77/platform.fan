package cn.worldwalker.game.wyqp.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.worldwalker.game.wyqp.wechat.common.utils.CustomizedPropertyConfigurer;
import cn.worldwalker.game.wyqp.wechat.service.GameService;

@Controller
public class BindController {
	private final static String CUR_COMPANY = CustomizedPropertyConfigurer.getContextProperty("cur.company");
	@Autowired
	private GameService gameService;
	
	@RequestMapping("bind")
	public ModelAndView index(String code,String state){
		System.out.println("code:" + code + ", state:" + state);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/" + CUR_COMPANY + "/company/index");
		return mv;
	}
	
}
