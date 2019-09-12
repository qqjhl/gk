//结算
$(function() {
	
	$('.pay_bt').click(function () {
		'WIDtotal_amount'
		alert(cont.innerText);
		
		if(cont.innerText == '00.00') {
			alert("无商品");
		} else {
			alert(cont.innerText);
			window.location.href='/c2c/pagePay.do';
		}
    })
	
});