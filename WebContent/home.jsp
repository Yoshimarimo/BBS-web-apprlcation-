<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet" href="./css/main.css" type="text/css">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>～わったい菜 掲示板～</title>

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
<div class="header">
	<div class="status">
		<c:out value="${loginUser.name}でログイン中"></c:out>
	</div>
	<a href="./">戻る</a>
	<a href="./logout" onclick="return check('ログアウト')">ログアウト</a>

	<h1>わったい菜BBS</h1>
	<c:if test="${loginUser.branchId == 1 && loginUser.departmentId == 1}">
		<a href="administer"> ユーザー管理 </a>
	</c:if>
	<br /> <a href="messagecreate"> 投稿する </a>
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

	<form action="./" method="get">
		<div class="category">
		カテゴリー指定 :
			<select name="category" id="category">
				<option value="">-指定なし-</option>
				<c:forEach items="${categoris}" var="category">
					<option value="${category.category}" <c:if test="${isCategory == category.category}">selected</c:if>>
						<c:out value="${category.category }" />
					</option>
					<c:out value="${category.category}" />
				</c:forEach>
			</select>
		</div>
		<div class="date">
		日付指定 :
			<input type="date" name = "stratnarrow" value = "${ min }" id = "stratnarrow" /> から
			<input type="date" name = "finishnarrow" value = "${ max }" id = "finishnarrow" /> までに
		</div>
		<input type="submit" value="絞り込む" />
	</form>
</div>
<%--投稿の表示 --%>

	<c:forEach items="${usermessages}" var="usermessage">
		<div class="allmessage">
			<div class="message">
				<div class="messagesubject">
					件名 : <c:out value="${usermessage.subject}" />
				</div>
				<div class="messagetext">
					<c:out value="${usermessage.text}" />
				</div>
				<div class="information">
					カテゴリー : <c:out value="${usermessage.category}" />   |
					名前 : <c:out value="${usermessage.name}" />   |
					投稿日時 : <fmt:formatDate value="${usermessage.createdAt}"
						pattern="yyyy/MM/dd HH:mm:ss" />
				</div>
				<div class="messagedelete">
					<form action="messagedelete" method="get">
						<input type="hidden" name="id" value="${usermessage.id}">
						<c:choose>
							<c:when test="${usermessage.userId == loginUser.id}"><input type="submit" value="削除" onclick="return check('削除')"></c:when>
							<c:when test="${loginUser.departmentId == 2}" ><input type="submit" value="削除" onclick="return check('削除')"></c:when>
							<c:when test="${loginUser.departmentId == 3 && usermessage.branchId == loginUser.branchId }">
							<input type="submit" value="削除" onclick="return check('削除')"></c:when>
						</c:choose>
					</form>
				</div>
			</div>
			<%--コメント入力フォーム --%>
			<div class="inputcomment">
				<form action="comment" method="post">
					<textarea name="text" value="${message.text}" cols=50 rows=10 id="text"></textarea>※最大500文字
					<input type="hidden" name="id" value="${usermessage.id}">
					<input type="submit" value="コメントする" />
				</form>
			</div>
			<%--コメントの表示 --%>
			<div class="comment">
				<c:forEach items="${comments}" var="comment">
					<c:if test="${comment.messageId == usermessage.id}">
						<div class="comment">
							<div class="commentsubject">
								<c:out value="${comment.text}" />
							</div>
							<div class="information">
								名前 : <c:out value="${comment.name}" />   |
								コメント日時 : <fmt:formatDate value="${comment.createdAt}" pattern="yyyy/MM/dd HH:mm:ss" />
							</div>
							<div class="commentdelete">
								<form action="commentdelete" method="get">
									<input type="hidden" name="id" value="${comment.id}">
									<c:choose>
										<c:when test="${comment.userId == loginUser.id}"><input type="submit" value="削除" onclick="return check('削除')"></c:when>
										<c:when test="${loginUser.departmentId == 2}" ><input type="submit" value="削除" onclick="return check('削除')"></c:when>
										<c:when test="${loginUser.departmentId == 3 && comment.branchId == loginUser.branchId }">
										<input type="submit" value="削除" onclick="return check('削除')"></c:when>
									</c:choose>
								</form>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</c:forEach>

<div class="copyright">Copyright(c)yoneda yoshiaki</div>
</body>
</html>