$(function () {
	//授予权限
    $('.accredit').click(function() {
    	
    	var phone = document.getElementById("phone");
    	
    	var r = confirm('确认授予 '+phone.value+' 管理员权限');
    	if(r == true) {
	    	$.ajax({
	    		url: 'accredit.do',
	    		dataType:'JSON',
	    		type:'post',
	    		data:{phone: phone.value},
	    		success:function(data) {
	    			if(data.result == 1) {
	    				alert("授权成功");
	    				
	    			} else {
	    				alert("授权失败！"+data.msg);
	    			}
	    		},
	    		error:function() {
	    			alert("请求服务异常");
	    		}
	    	})
    	}
    });
    
    //收回权限
    $('.unaccredit').click(function() {
    	
    	var phone = document.getElementById("phone");
    	
    	var r = confirm('确认撤销 '+phone.value+' 管理员权限');
    	
    	if(r == true) {
	    	$.ajax({
	    		url: 'unaccredit.do',
	    		dataType:'JSON',
	    		type:'post',
	    		data:{phone: phone.value},
	    		success:function(data) {
	    			if(data.result == 1) {
	    				alert("撤销成功");
	    			} else {
	    				alert("撤销失败！"+data.msg);
	    			}
	    		},
	    		error:function() {
	    			alert("请求服务异常");
	    		}
	    	})
    	}
    });
    
});