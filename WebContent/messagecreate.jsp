<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<script type="text/javascript">
function setSel(sel) {
if (sel.value == "0") {
sel.form.category.style.display = 'none';
} else {
sel.form.category.style.display = 'block';
}
}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>～わったい菜 掲示板#新規投稿#～</title>
</head>
<body>
	<div class="main-contents">
	<div class="status">
		<c:out value="${loginUser.name}でログイン中"></c:out>
			</div>
		<a href="./">戻る</a>
		<a href="./logout" onclick="return check('ログアウト')">ログアウト</a>

	<h1>新規投稿</h1>

			<br />
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

			<%--文字数をJSP内で制限できればチェック項目を減らすことができる、後ほど見直す。 --%>
			<form name="message" action="messagecreate" method="post">
				<label for="subject">件名</label>
				<br />
				<input name="subject"	value="${isMessage.subject}" id="subject">※最大50文字
				<br />

				<label	for="text">本文</label>
				<br />
				<textarea name="text" cols=100 rows=20 wrap="hard" id="text">${isMessage.text}</textarea>
				※最大1000文字
				<br />


				<label for="category">カテゴリー</label>
				<br />
				<input type="text" value="${isMessage.category}" name="category" value=""/>
				<br />
				<br />
			<input type="submit" value="投稿する" />
			</form>
	</div>

	<div class="copyright">Copyright(c)yoneda yoshiaki</div>

</body>
</html>