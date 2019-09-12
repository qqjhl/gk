$( function() {
	//item默认关闭 (个人信息展示界面)
    $( "#accordion" ).accordion({active:999});
    $('.update_button').click(function () {
        var token = $('.token').val();
        var type = $(this).siblings(".first_info").children("input").attr("type");
        var which_update = $(this).siblings(".first_info").children("input").attr("class");
        var my_this = $(this);
        
        
        //性别控制按钮
        if (type=="radio") {
            var value=$('input:radio[name="sex_choose"]:checked').val();
            var arr="sex-"+value;
            
            //提交修改
            $.ajax({
                url:'certification.do',
                type:'post',
                dataType:'JSON',
                data:{arr: arr},
                success:function (data) {
                    var result = data.result;
                    if (result === 0) {
                        alert('更新失败，请检测信息格式');
                    } else if (result === 1) {
                        alert('更新成功');
                        updateText(value);
                    }
                    if (which_update === 'userName'){
                        $('.user_name_a').text(value);
                    }
                },
                error:function() {
                	alert("sex update error");
                }
            });
            
        }else {  
        	// first_info 兄弟元素(sibling) 的子元素(input) 的值
            var val = $(this).siblings(".first_info").children("input").val();
            if (val==undefined||val=='') { 
            	//first_info 的子元素 reqiure_enter 显示
                $(this).siblings(".first_info").children(".reqiure_enter").show(0);
            } else {
               //修改，修改类名或id，直接获取类名就可以
                var value =$(this).siblings(".first_info").children("input").val();
                var arr =   which_update + "-" + value;
                
                $.ajax({
                    url:'certification.do',
                    type:'post',
                    dataType:'JSON',
                    data:{arr: arr},
                    success:function (data) {
                        var result = data.result;
                        if (result === 0){
                            alert('更新失败，请检测信息格式');
                        } else if (result === 1) {
                            alert('更新成功');
                            updateText(value);
                        }
                        if (which_update === 'userName') {
                            $('.user_name_a').text(value);
                        }
                    },
                    error:function() {
                    	alert("update error");
                    }
                });
            }
        }
        function updateText(value) {
            my_this.parent().prev().children().html(value);
        }
    });
    
//  实时监听输入框的输入变化，当有输入值的时候，隐藏 <必须填写字段>
    $('.first_info input').bind("input propertychange change",function () {
        var val = $(this).val();
        if (val!=undefined&&val!='') {
            $(this).siblings(".reqiure_enter").hide(0);
        }
    });
    
    $('.first_header input').bind("input propertychange change",function () {
        var val = $(this).val();
        if (val!=undefined&&val!='') {
            $(this).siblings(".reqiure_enter").hide(0);
        }
    });
    
} );
$(function () {
    if ($('.show_tip').is(':hidden')){
        var show_tip = $('.show_tip').val();
        alert('请先认证真实信息！！！！！');
    }
});