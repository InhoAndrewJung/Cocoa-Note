<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="head.jsp"></jsp:include>
<title>코코아노트</title>
<script>
function defaultTheme(){
	if(${memberVO.theme}==1){
		document.getElementById('cocoaTheme').click();
	}else if(${memberVO.theme}==2){
		document.getElementById('peachTheme').click();
	}else{
	}
}
function chgTheme(radioBtn){
	if(radioBtn.value=="코코아"){
		/* alert(); */
		document.getElementById("peachTheme").className ='chgThemeBtn lastBtn';
		radioBtn.className ='chgThemeBtn cocoaTheme';
		document.updateMemberFrm.theme.value="코코아";
	}else if(radioBtn.value=="피치"){
		document.getElementById("cocoaTheme").className ='chgThemeBtn';
		radioBtn.className ='chgThemeBtn lastBtn peachTheme';
		document.updateMemberFrm.theme.value="피치";
	}
}
</script>
<title>회원정보 수정</title>
<div class="row" style="margin-bottom:100px;">
	<div class="col-6 d-flex justify-content-end" >
		<div class="card rounded-content textcenter" style="width: 25rem; height: 30rem;">
			<div class="card-body" >
				<h4 class="card-title" style="text-align:center;">Mypage</h4>
				<br />
				<form name="updateMemberFrm" action="DispatcherServlet" method="post">
					<input class="form-control" type="text" name="id" readonly="readonly" value="${memberVO.id}" /><br /> 
					<input class="form-control" type="password" name="password"	placeholder="현재 비밀번호" required="required" /><br /> 
					<input class="form-control" type="password" name="new_password"	placeholder="변경할 비밀번호" /><br /> 
					<input class="form-control" type="text" name="name" placeholder="이름" required="required" value="${memberVO.name}" /><br /> 
					<span>
					 	<input type="button" class="chgThemeBtn" id="cocoaTheme" value="코코아" onclick="chgTheme(this)" checked="checked" >
						<input type="button" class="chgThemeBtn lastBtn" id="peachTheme" value="피치" onclick="chgTheme(this)">
					</span>
					<br/>
					<br/>
						<input type="hidden" name="theme" />
						<input type="hidden" name="command" value="updateMember" />
						<input type="submit" class="updateBtn" value="저장" />
				</form>
			</div>
		</div>
	</div>

	<div class="col-6">
		<div class="card rounded-content textcenter" style="width: 25rem; height: 30rem;">
			<div class="card-body" >
			
				<h4 class="card-title" style="text-align:center;">Keyboard Shortcuts</h4>
				<br/><br/>
				<div class="row">
				<div class="col-4">
				<h6><Strong>ALT + A</Strong></h6>
				<h6><Strong>ALT + Q</Strong></h6>
				<h6><Strong>ALT + N</Strong></h6>
				<hr>
				<h6><Strong>ALT + N</Strong></h6>
				<h6><Strong>ALT + A</Strong></h6>
				<h6><Strong>ALT + S</Strong></h6>
				<hr>
				<h6><Strong>ALT + Z</Strong></h6>
				<h6><Strong>ALT + X</Strong></h6>
				</div>
				<div class="col-8">
				 <h6>함께할 친구 추가</h6>
				 <h6>일정 등록</h6>
				 <h6>새 일정</h6>
				 <hr>
				 <h6>새 노트</h6>
				 <h6>노트 저장</h6>
				 <h6>노트 삭제</h6>
				 <hr>
				 <h6>검색</h6>
				 <h6>빠른 메모</h6>
				</div>
				</div>
				</div>
				</div>
				
	</div>

</div>


	
			<script type="text/javascript">
				defaultTheme();
			</script>
		


<jsp:include page="foot.jsp"></jsp:include>