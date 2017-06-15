<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="./css/main.css" type="text/css">

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン</title>
</head>
<body>
	<div class="main-contents">
	<h1>ログイン画面</h1>

		<c:if test="${ not empty errorMessages }">
			<div class="errorMessages">
				<ul>
					<c:forEach items="${errorMessages}" var="message">
						<li><c:out value="${message}" />
					</c:forEach>
				</ul>
			</div>
			<c:remove var="errorMessages" scope="session" />
		</c:if>
		<form action="login" method="post" >
			<br />
			<label for="loginId">ログインID</label>
			<input name="loginId" value="${loginId}" id="loginId" />
			<br />
			<label for="password">パスワード</label>
			 <input name="password" type="password" id="password" />
			<br />
			<input type="submit" value="ログイン" />
			<br />
		</form>
		<div class="copyright">Copyright(c)yoneda yoshiaki</div>
	</div>
</body>
</html>
