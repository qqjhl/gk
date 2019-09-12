$(function(){
	
	$('.grab').click(function() {
		var count = document.getElementById("count").value;
		var consumer = document.getElementById("consumer").value;
		
		for(var id = 1; id<= consumer; id++) {
			$.ajax({
	        	url:'rush.do',
	    		dataType:'JSON',
	    		type:'post',
	    		data:{id: id},
	    		success:function(data) {
	    			//alert(data.msg);
	    		},
	    		error:function() {
	    			//alert("请求服务异常");
	    		}
	        });
		}
		alert(consumer+" 参与了本次活动");
	}); 

	$('.pub').click(function() {
		var count = document.getElementById("count").value;
		$.ajax({
        	url:'publishRush.do',
    		dataType:'JSON',
    		type:'post',
    		data:{count: count},
    		success:function(data) {
    			alert(data.msg);
    		},
    		error:function() {
    			alert("请求服务异常");
    		}
        });

	}); 
	
});