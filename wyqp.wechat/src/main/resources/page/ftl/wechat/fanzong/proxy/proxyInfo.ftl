<!DOCTYPE html>
<html>
  <head>
    <title>嘻哈纸牌</title>
    <meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	
	<meta name="description" content="Write an awesome description for your new site here. You can edit this line in _config.yml. It will appear in your document head meta (for Google search results) and in your feed.xml site description.
	">
	
	<link rel="stylesheet" href="/resources/jquery-weui-build/lib/weui.min.css">
	<link rel="stylesheet" href="/resources/jquery-weui-build/css/jquery-weui.css">
	<link rel="stylesheet" href="/resources/jquery-weui-build/demos/css/demos.css">
    </head>
    
    <body>
		
		<div class="weui-cells">
		  <div class="weui-cell">
		    <div class="weui-cell__hd"><img src=""></div>
		    <div class="weui-cell__bd">
		      <p>姓名</p>
		    </div>
		    <div class="weui-cell__ft">${realName}</div>
		  </div>
		  <div class="weui-cell">
		    <div class="weui-cell__hd"><img src=""></div>
		    <div class="weui-cell__bd">
		      <p>推广ID</p>
		    </div>
		    <div class="weui-cell__ft">${proxyId}</div>
		  </div>
		  <div class="weui-cell">
		    <div class="weui-cell__hd"><img src=""></div>
		    <div class="weui-cell__bd">
		      <p>游戏昵称</p>
		    </div>
		    <div class="weui-cell__ft">${nickName}</div>
		  </div>
		  <div class="weui-cell">
		    <div class="weui-cell__hd"><img src=""></div>
		    <div class="weui-cell__bd">
		      <p>手机号</p>
		    </div>
		    <div class="weui-cell__ft">${mobilePhone}</div>
		  </div>
		  <div class="weui-cell">
		    <div class="weui-cell__hd"><img src=""></div>
		    <div class="weui-cell__bd">
		      <p>微信号</p>
		    </div>
		    <div class="weui-cell__ft">${wechatNum}</div>
		  </div>
		</div>
		
		
		<div class="weui-cells">
		  <div class="weui-cell">
		    <div class="weui-cell__hd"><img src=""></div>
		    <div class="weui-cell__bd">
		      <p>累计收益(元)</p>
		    </div>
		    <div class="weui-cell__ft">${totalIncome}</div>
		  </div>
		  <div class="weui-cell">
		    <div class="weui-cell__hd"><img src=""></div>
		    <div class="weui-cell__bd">
		      <p>已提现金额(元)</p>
		    </div>
		    <div class="weui-cell__ft">${extractAmount}</div>
		  </div>
		  <div class="weui-cell">
		    <div class="weui-cell__hd"><img src=""></div>
		    <div class="weui-cell__bd">
		      <p>账户余额(元)</p>
		    </div>
		    <div class="weui-cell__ft">${remainderAmount}</div>
		  </div>
		  <div class="weui-cell">
		    <div class="weui-cell__hd"><img src=""></div>
		    <div class="weui-cell__bd">
		      <a href='/game/proxy/giveAwayRoomCards'>赠送房卡</a>
		    </div>
		  </div>
		</div>
		
		
		<script src="/resources/jquery-weui-build/lib/jquery-2.1.4.js"></script>
		<script src="/resources/jquery-weui-build/lib/fastclick.js"></script>
		<script>
		  $(function() {
		    FastClick.attach(document.body);
		  });
		</script>
		<script src="/resources/jquery-weui-build/js/jquery-weui.js"></script>
    </body>
</html>

