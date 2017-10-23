<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="head.jsp"></jsp:include>
<script>
function window.onload(){
	if('${selected}'== $("#cocoaTheme").attr("value")){
		$("#cocoaTheme").prop("checked", true);
	}else if('${selected}' == $("#peachTheme").attr("value")){
		$("#peachTheme").prop("checked", true);
	}
}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원정보 수정</title>
</head>
<body>
<form action="DispatcherServlet" method="post">
회원정보 수정<br />
아이디 : <input type="text" name="id" readonly="readonly" value="${memberVO.id}" /><br />
현재 비밀번호 : <input type="password" name="password" required="required" /><br />
변경할 비밀번호 : <input type="password" name="new_password" /><br />
이름 : <input type="text" name="name" required="required" value="${memberVO.name}"/><br />
테마 : <input type="radio" name="theme" id=cocoaTheme value="cocoa">코코아
		<input type="radio" name="theme" id=peachTheme value="peach">피치<br/>
<input type="submit" value="회원정보 수정"/>
<input type="hidden" name="command" value="updateMember" />
</form>
<jsp:include page="foot.jsp"></jsp:include>