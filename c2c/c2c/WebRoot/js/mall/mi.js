$(function(){
    $(".send").on('click',function () {
    	//输入框的信息
    	var ly=document.getElementById("ly").value;
    	var id = $('#sss').val();
    	
    	if(ly == '') {
    		alert("请输入您的留言");
    	}else{
	        $.ajax({
	        	url:'send.do',
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
	        var popBox = document.getElementById("popBox");
	        var popLayer = document.getElementById("popLayer");
	        popBox.style.display = "none";
	        popLayer.style.display = "none";
    	}
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
});