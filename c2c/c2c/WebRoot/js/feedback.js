$(function(){
    $(".fb").on('click',function () {

    	var area=document.getElementById("inarea").value;
		var phone=document.getElementById("ph").value;
		var email=document.getElementById("em").value;
    	if(area == '') {
    		alert("请输入您的建议/反馈");
    	} else {
			$.ajax({
	        	url:'fbaccept.do',
	    		dataType:'JSON',
	    		type:'post',
	    		data:{area: area, phone: phone, email: email},
	    		success:function(data) {
	    			if(data.result == 1) {
	    				alert("您的建议/问题已接收，等待处理");
	    			} else {
	    				alert("提交失败");
	    			}
	    		},
	    		error:function() {
	    			alert("请求服务异常");
	    		}
	        });
		}
    })
});