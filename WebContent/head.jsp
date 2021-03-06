<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="bg-ghostwhite">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=500px, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
<script src="CocoaJS.js" type="text/javascript"></script>
<link rel="stylesheet" href="CocoaCSS.css">
<c:if test="${memberVO.theme eq '1'}">
<link rel="stylesheet" href="css/global-tier-one.css">
</c:if>
<c:if test="${memberVO.theme eq '2'}">
<link rel="stylesheet" href="css/global-tier-two.css">
</c:if>
<link rel="stylesheet" href="css/animate.css">
<c:if test="${memberVO==null}">
	<script type="text/javascript">
		location.href="login.jsp";
	</script>
</c:if>
<script>
function myFunction() {
    return "Write something clever here...";
}
</script>
</head>
<body class="bg-ghostwhite">
<div class="bg-ghostwhite" style="margin-top: 50px"></div>
	<div class="non-responsive-container">
		<nav class="navbar  navbar-expand bg-grad-pink rounded-bar" style="margin-bottom:30px;">

			<a href="DispatcherServlet?command=cal&year=${today.year}&month=${today.month}"><span class="navbar-brand mb-0 h1">cocoaNOTE</span></a>

			<!-- change icon color depending theme -->
			<div class="d-flex" style="width: 900px;">
			<c:if test = "${memberVO.theme eq '1' }">
			<a href="DispatcherServlet?command=cal&year=${today.year}&month=${today.month}"><img class="icon"  src="icon/calendar-cocoa.svg" width="40px" height="40px"></a> 
			<a href="DispatcherServlet?command=noteView&isCurr=true"><img class="icon" src="icon/notebook-cocoa.png" width="41px" height="41px"></a>
			<a href="DispatcherServlet?command=memoList"><img class="icon" src="icon/memo-cocoa.svg" width="40px" height="40px"></a>
			</c:if>
			
			<c:if test = "${memberVO.theme eq '2' }">
			<a href="DispatcherServlet?command=cal&year=${today.year}&month=${today.month}"><img class="icon"  src="icon/calendar-peach.svg" width="40px" height="40px"></a> 
			<a href="DispatcherServlet?command=noteView&isCurr=true"><img class="icon" src="icon/notebook-peach.png" width="41px" height="41px"></a>
			<a href="DispatcherServlet?command=memoList"><img class="icon " src="icon/memo-peach.svg" width="40px" height="40px"></a>
			</c:if>
			
			<div class="dropdown show ml-auto">
				<button type="button"
					class="btn bg-transwhite dropdown-toggle dropdown show rounded-bar"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					${memberVO.name }</button>
				<div class="dropdown-menu rounded-bar" style="left: -40%;">
					<a class="dropdown-item" href="updateMember.jsp">설정</a> 
					<div class="dropdown-divider"></div>
					<a class="dropdown-item" href="DispatcherServlet?command=logout">로그아웃</a>
				</div>
			</div>
			</div>
		</nav>
	