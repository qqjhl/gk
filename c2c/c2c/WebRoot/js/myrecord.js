$(function () {
	$('.p1').click(function () {
		
		var txt = document.getElementById("a1").value;
		var acc = document.getElementById("sendid1").value;   //留言接收id

		if(txt == '') {
			alert("请输入回复内容");
		}else {
			$.ajax({
				url:'recordback.do',
				dataType:'JSON',
				type:'post',
				data:{txt:txt, acc:acc},
				success:function (data) {
					if(data.result == '1') {
						alert("回复已送达");
						window.location.href='/c2c/my_record.do';
					}
				}
			})
		}
	});
	
	$('.p2').click(function () {
		var txt = document.getElementById("a2").value;
		var acc = document.getElementById("sendid2").value;   //留言接收id

		if(txt == '') {
			alert("请输入回复内容");
		}else {
			$.ajax({
				url:'recordback.do',
				dataType:'JSON',
				type:'post',
				data:{txt:txt, acc:acc},
				success:function (data) {
					if(data.result == '1') {
						alert("回复已送达");
						window.location.href='/c2c/my_record.do';
					}
				}
			})
		}
	});
	
	$('.p3').click(function () {
		
		var txt = document.getElementById("a3").value;
		var acc = document.getElementById("sendid3").value;   //留言接收id

		if(txt == '') {
			alert("请输入回复内容");
		}else {
			$.ajax({
				url:'recordback.do',
				dataType:'JSON',
				type:'post',
				data:{txt:txt, acc:acc},
				success:function (data) {
					if(data.result == '1') {
						alert("回复已送达");
						window.location.href='/c2c/my_record.do';
					}
				}
			})
		}
	});
	
	$('.p4').click(function () {
		
		var txt = document.getElementById("a4").value;
		var acc = document.getElementById("sendid4").value;   //留言接收id

		if(txt == '') {
			alert("请输入回复内容");
		}else {
			$.ajax({
				url:'recordback.do',
				dataType:'JSON',
				type:'post',
				data:{txt:txt, acc:acc},
				success:function (data) {
					if(data.result == '1') {
						alert("回复已送达");
						window.location.href='/c2c/my_record.do';
					}
				}
			})
		}
	});
	
	$('.p5').click(function () {
		
		var txt = document.getElementById("a5").value;
		var acc = document.getElementById("sendid5").value;   //留言接收id

		if(txt == '') {
			alert("请输入回复内容");
		}else {
			$.ajax({
				url:'recordback.do',
				dataType:'JSON',
				type:'post',
				data:{txt:txt, acc:acc},
				success:function (data) {
					if(data.result == '1') {
						alert("回复已送达");
						window.location.href='/c2c/my_record.do';
					}
				}
			})
		}
	});
	
	$('.p6').click(function () {
		
		var txt = document.getElementById("a6").value;
		var acc = document.getElementById("sendid6").value;   //留言接收id

		if(txt == '') {
			alert("请输入回复内容");
		}else {
			$.ajax({
				url:'recordback.do',
				dataType:'JSON',
				type:'post',
				data:{txt:txt, acc:acc},
				success:function (data) {
					if(data.result == '1') {
						alert("回复已送达");
						window.location.href='/c2c/my_record.do';
					}
				}
			})
		}
	});
	
	$('.p7').click(function () {
		
		var txt = document.getElementById("a7").value;
		var acc = document.getElementById("sendid7").value;   //留言接收id

		if(txt == '') {
			alert("请输入回复内容");
		}else {
			$.ajax({
				url:'recordback.do',
				dataType:'JSON',
				type:'post',
				data:{txt:txt, acc:acc},
				success:function (data) {
					if(data.result == '1') {
						alert("回复已送达");
						window.location.href='/c2c/my_record.do';
					}
				}
			})
		}
	});
})