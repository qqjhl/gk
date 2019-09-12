$(function () {

    calSum();
    var which = 0;
    $('.shipping_address').click(function () {
        var id = $(this).attr('id');
        $('.shipping_address').each(function () {
            if ($(this).attr('id')==id) {
                $(this).css({"border-color": "slateblue"});
                which = id;
            }else {
                $(this).css({"border-color": "rgba(0,0,0,0.1)"});
            }
        });
    });
//            -号，不能低于 1
    $('span.minus').click(function () {
        var num = $(this).siblings(".number").html();
        if (num>=2) {
            num--;
        }
        $(this).siblings(".number").html(num);
        var cost = $(this).parent().siblings(".cost").children("span").html();
        $(this).parent().siblings(".per_sum").children("span").html(returnFloat(cost*num));
        calSum();
    });
    $('span.add').click(function () {
        var num = $(this).siblings(".number").html();
        num++;
        $(this).siblings(".number").html(num);
        var cost = $(this).parent().siblings(".cost").children("span").html();
        $(this).parent().siblings(".per_sum").children("span").html(returnFloat(cost*num));
        calSum();
    });
    $('.table_content td.input_checkbox input').change(function () {
        calSum();
    });
    $('#all').change(function () {
        if($(this).is(":checked")) {
            $("input[name='checkbox']").attr("checked","true");
        }else {
            $("input[name='checkbox']").removeAttr("checked");
        }
        calSum();
    });
//            保留两位小数
    function returnFloat(value){
        var value=Math.round(parseFloat(value)*100)/100;
        var xsd=value.toString().split(".");
        if(xsd.length==1){
            value=value.toString()+".00";
            return value;
        }
        if(xsd.length>1){
            if(xsd[1].length<2){
                value=value.toString()+"0";
            }
            return value;
        }
    }
//            计算总金额
    function calSum() {
        var sum = 0;
        $('.cart_content table tr.table_content').each(function () {
            var isCheck = $(this).children("td.input_checkbox").children("input").is(":checked");
            if(isCheck){
                sum  +=  parseFloat($(this).children(".per_sum").children("span").html());
            }
        });
        sum = returnFloat(sum);
        $('.end_pay').children(".all_sum").children("span").html(sum);
    }

    $('.deleteShopCar').click(function () {
        var r = confirm('确定删除？？？？');
        if (r == true) {
            var id = $(this).attr('value');
            var sid = $(this).parent().siblings(".show_img").children().attr("value");
            // alert(sid);
            $.ajax({
                url:'deleteShopCar.do',
                dataType:'JSON',
                type:'post',
                data:{id:id,sid:sid},
                success:function (data) {
                    var result = data.result;
                    if (result==2){
                        alert('您还没有登录，请先登录');
                    }  else if (result==1) {
                        alert("删除成功");
                        window.location.href='shopping_cart.do?result=删除成功';
                    } else if (result==0){
                        alert('删除失败，请检测网络');
                    } else {
                        alert('删除失败，请检测网络');
                    }
                }
            })
        }
    })
});
//地址修改
$(function () {
	//确认修改
	$('.repeal').click(function () {
		//输入框的信息
        var address = document.getElementById("address");
        if(address.value === '') {
        	alert("地址为空");
        }else {
			$.ajax({
				url:'address.do',
				dataType:'JSON',
	            type:'post',
	            data:{address:address.value},
	            success:function (data) {
	            	if(data.result == 1) {
	            		
	            		document.getElementById("addr").innerText = address.value;
	            		alert("地址修改成功");
	            		var popBox = document.getElementById("popBox");
	                    var popLayer = document.getElementById("popLayer");
	                    popBox.style.display = "none";
	                    popLayer.style.display = "none";
	                    
	            	} else {
	            		alert("地址修改失败");
	            		var popBox = document.getElementById("popBox");
	                    var popLayer = document.getElementById("popLayer");
	                    popBox.style.display = "none";
	                    popLayer.style.display = "none";
	                    alert("地址修改失败");
	            	}
	            }
			})
        }
		var popBox = document.getElementById("popBox");
        var popLayer = document.getElementById("popLayer");
        popBox.style.display = "none";
        popLayer.style.display = "none";
		
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

// detail
$(function() {
	
    $('.detail').click(function () {
		var id = $(this).attr('value');  //sid
		if(id == '') {
			alert("找不到商品id啦");
		} else {
			window.location.href='/c2c/selectById.do?id='+id;
		}
    })
});

//结算
$(function() {
	
	$('.pay_button').click(function () {
		var cont=document.getElementById("pay_sum");
		
		var c=String.fromCharCode(cont.innerText.charCodeAt(0)+1);  
	    for(var i=1;i<cont.innerText.length;i++){        
	        c+=String.fromCharCode(cont.innerText.charCodeAt(i)+i+1);  
	    } 
	    var c2=String.fromCharCode(cont.innerText.charCodeAt(0)+2);  
	    for(var i=1;i<cont.innerText.length;i++){        
	        c2+=String.fromCharCode(cont.innerText.charCodeAt(i)+i+2);  
	    } 
	    
	    var vc =c + "n" + c2;

		if(cont.innerText == '0.00') {
			alert("无商品");
		} else {
			window.location.href='/c2c/pay.do?number='+vc;
		}
    })
	
});

