$(function () {
    $('.send_comment_button').click(function () {
        var value = $('.comment_textarea').val();
        var username = $('.wsk').val();
        var token = $('.token').val();
        var $comment = $('.comment_content');
        var id = $('.id').val();
        if (username == '0') {
            alert('请先登录！！！');
            return;
        }
        $.ajax({
            url: 'insertShopContext.do',
            type: 'post',
            dataType:'JSON',
            data:{id:id,context:value,token:token},
            success:function (data) {
                var result = data.result;
                if (result == 2){
                    alert('请先登录！！！');
                } else if (result == 0){
                    alert("发表留言失败，请先检查格式");
                } else if (result == 1){
                    var name = data.username;
                    var time = data.time;
                    var context = data.context;
                    var cc = "<div class='one_comment'><span class='username'>用户："+name+"</span><span class='time'>发表于："+time+"</span><p class='content'>"+context+"</p></div>";
                    $comment.append(cc);
                }
            }
        });
    });
    $('.buy_button').click(function () {
        var id = $(this).attr('value');
        $.ajax({
            url:'insertGoodsCar.do',
            dataType:'JSON',
            type:'post',
            data:{id:id},
            success:function (data) {
                var result = data.result;
                if (result == '2'){
                    alert('您还未登录，请先登录！！！');
                } else if (result == '1'){
                    alert('加入购物车成功');
                } else if (result == '0'){
                    alert('加入购物车失败');
                } else {
                    alert('发生了错误，请检测网络');
                }
            },
            error:function () {
            	alert("加购失败");
            }
        })
    });
    
    //弹出弹框
    $('.popBox').click(function() {
    	 var popBox = document.getElementById("popBox");
         var popLayer = document.getElementById("popLayer");
         popBox.style.display = "block";
         popLayer.style.display = "block";
    });
    
    //关闭弹窗
    $('.closeBox').click(function() {
    	var popBox = document.getElementById("popBox");
        var popLayer = document.getElementById("popLayer");
        popBox.style.display = "none";
        popLayer.style.display = "none";
    });
    
    //确定撤销
    $('.repeal_button').click(function () {
        
        //输入框的信息
        var reason = document.getElementById("reason").value;

        var id = $(this).attr('value');
        if(reason == '') {
        	alert("请填写撤销原因");
        } else {
	        $.ajax({
	            url:'deleteGoods.do',
	            dataType:'JSON',
	            type:'post',
	            data:{id: id, reason: reason},
	            success:function (data) {  
	                var result = data.result;
	                
	                if (result == '3'){
	                    alert('您没有权限撤销商品');
	                } else if (result == '2'){
	                    alert('您还未登录，请先登录！！！');
	                } else if (result == '1'){
	                    alert('撤销成功, 系统已记录本次操作');
	                    window.location.href='/c2c/';
	                } else if (result == '0'){
	                    alert('撤销失败');
	                } else {
	                    alert('发生了错误，请检测网络');
	                }
	            },
	            error:function () {
	            	alert("请求服务异常");
	            }
	        })
	        var popBox = document.getElementById("popBox");
	        var popLayer = document.getElementById("popLayer");
	        popBox.style.display = "none";
	        popLayer.style.display = "none";
        }
    });
});


$(function(){
    $(".send").on('click',function () {
    	//输入框的信息
    	var ly=document.getElementById("ly").value;
    	var id = $('#sss').val();
    	
    	if(ly == '') {
    		alert("请输入您的留言");
    	}else{
	        $.ajax({
	        	url:'sendP.do',
	    		dataType:'JSON',
	    		type:'post',
	    		data:{ly: ly, id: id},
	    		success:function(data) {
	    			if(data.result == 1) {
	    				alert("您的留言已送达");
	    			} else {
	    				alert(data.msg);
	    			}
	    		},
	    		error:function() {
	    			alert("请求服务异常");
	    		}
	        });
    	}
    	
    	var popBox = document.getElementById("sellPop");
        var popLayer = document.getElementById("sellLayer");
        popBox.style.display = "none";
        popLayer.style.display = "none";
    });
    
  //弹出弹框
    $('.sell').click(function() { 
    	 var popBox = document.getElementById("sellPop");
         var popLayer = document.getElementById("sellLayer");
         popBox.style.display = "block";
         popLayer.style.display = "block";
    });
    
    //关闭弹窗
    $('.close').click(function() {
    	var popBox = document.getElementById("sellPop");
        var popLayer = document.getElementById("sellLayer");
        popBox.style.display = "none";
        popLayer.style.display = "none";
    });
});



