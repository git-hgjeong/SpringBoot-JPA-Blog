/**
 * 
 */
 let index = {
	init: function(){
		$("#btn-save").on("click", ()=>{
			this.save();
		});
	},
	save:function(){
		//alert("user의 save함수가 호출됨.");
		let data = {
			"username": $("#username").val()
			,"password": $("#password").val()
			,"email": $("#email").val()
		}
		
		//console.log(data);
		
		$.ajax({
			type:"POST",
			url: "/blog/api/user",
			data: JSON.stringify(data),
			contentType:"application/json;charset=utf-8",	//body data타입
			dataType:"json"	//요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 문자열인데 javascript Object로 변경함.
		}).done(function(response){
			console.log(this.parent);
			console.log(response);
			if(response.status == 200){
				alert("회원가입이 완료되었습니다.");
				location.href = "/blog";				
			}else{
				var msg = response.data;
				if(msg.indexOf("Duplicate") != -1){
					alert("중복된 사용자가 존재합니다.");	
				}else{
					alert("장애가 발생하였습니다.");
				}
				
			}
		}).fail(function(error){
			console.log(error);
			alert(error.responseText);
		});
	}
}

index.init();