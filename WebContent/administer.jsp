<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="./css/main.css" type="text/css">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理画面</title>

<script type="text/javascript">
	function check(str) {

		if (window.confirm('本当に' + str + 'してよろしいですか？')) { // 確認ダイアログを表示

			return true; // 「OK」時は送信を実行

		} else { // 「キャンセル」時の処理

			window.alert('キャンセルされました'); // 警告ダイアログを表示
			return false; // 送信を中止

		}

	}
</script>
</head>
<body>
	<div class="status">
		<c:out value="${loginUser.name}でログイン中"></c:out>
			</div>
		<a href="./">戻る</a>
		<a href="./logout" onclick="return check('ログアウト')">ログアウト</a>

	<h1>ユーザー管理</h1>
	<br />
	<a href="signup">ユーザー新規登録</a>
	<div class="main-contents">
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
	</div>
	<div>
	<c:forEach items="${users}" var="user">
	<div class=allmessage>
		<div class="name">
			名前[
			<c:out value="${user.name}" />
			] <br />
		</div>
		<div class="loginId">
			ログインID[
			<c:out value="${user.loginId}" />
			] <br />
		</div>
		<div class="branchId">
			支店[
			<c:forEach items="${branches}" var="branch">
			<c:if test="${user.branchId == branch.id }" >
			<c:out value="${branch.name}"/>
			 </c:if>
			</c:forEach>
			] <br />
		</div>
		<div class="departmentId">
			役職[
			<c:forEach items="${departments}" var="department">
				<c:if test="${user.departmentId == department.id }" >
					<c:out value="${department.name}" />
				</c:if>
			</c:forEach>
			] <br />
		</div>
		<div class="isStopped">
			利用可[
			<c:if test="${user.isStopped == 0}" >
				<c:out value="利用可能" />
			</c:if>
			<c:if test="${user.isStopped == 1}" >
				<c:out value="停止中" />
			</c:if>
			]
			<c:if test="${loginUser.id != user.id }">
				<form action="administer" method="post" onSubmit="return check('実行')">
					<input type="hidden" name="id" value="${user.id}">
					<input type="radio" name="decision" value="1" <c:out value="${user.isStopped}" />/>停止
					<input type="hidden" name="id" value="${user.id}">
					<input type="radio" name="decision" value="0" <c:out value="${user.isStopped}" />/>復活
					<input type="submit" value="実行" />
				</form>
			</c:if>
		</div>
		<form action="useredit" method="get" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${user.id}">
			<input type="submit" value="編集">
		</form>
		</div>
	</c:forEach>
	</div>
	<div class="copyright">Copyright(c)yoneda yoshiaki</div>
</body>
</html>
