<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="./css/main.css" type="text/css">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー登録</title>
</head>
<body>
<div class="status">
		<c:out value="${loginUser.name}でログイン中"></c:out>
			</div>
		<a href="./administer">戻る</a>
		<a href="./logout" onclick="return check('ログアウト')">ログアウト</a>

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
		<form action="signup" method="post">
			<h1> ユーザー新規登録 </h1>

			<label for="loginId">ログインID</label>
			<input	name="loginId" value="${user.loginId}" id="loginId" />
			<br />※(必須) 半角英数字6文字～20文字
			<br />


			<label	for="password">パスワード</label>
			<input name="password" type="password"	id="password" />
			<br />※(必須) 半角英数字＋記号6文字～225文字
			<br />


			<label for="passwordConfirmation">パスワード(確認用)</label>
			<input name="passwordConfirmation" type="password"	id="passwordConfirmation" />
			<br />※(必須) パスワードと同じものを入力してください
			<br />


			<label for="name">名前</label>
			<input	name="name" value="${user.name}" id="name" />
			<br />※(必須) ～10文字
			<br />



			<label for="branchId">所属支店</label>
				<select name="branchId" id="branchId">
				<c:forEach items="${branches}" var="branch">
					<option  value="${branch.id}"
					<c:if test="${user.branchId == branch.id}">selected</c:if>
					>
					<c:out value="${branch.name}"  />
					</option>
				</c:forEach>
				</select>
				<br />※(必須)
			<br />


			<label for="departmentId">役職</label>
				<select name="departmentId" id="departmentId">
				<c:forEach items="${departments}" var="department">
					<option  value="${department.id}"
					<c:if test="${user.departmentId == department.id}">selected</c:if>
					>
					<c:out value="${department.name}" />
					</option>
				</c:forEach>
				</select>
				<br />
				※(必須)
			<br />

			<input	type="submit" value="登録" /> <br />
		</form>
		<div class="copyright">Copyright(c)yoneda yoshiaki</div>
	</div>
</body>
</html>