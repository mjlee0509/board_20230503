<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-05-03
  Time: 오전 9:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MJUNIVERSE::글쓰기</title>
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="/resources/css/form.css">
    <link rel="stylesheet" href="/resources/css/table.css">
    <link rel="stylesheet" href="/resources/bootstrap.min.css">
    <script src="/resources/bootstrap.bundle.min.js"></script>
</head>
<body>
<%@include file="../component/header.jsp" %>
<%@include file="../component/nav.jsp" %>
<div class="main">
    <h2>글쓰기</h2>
    <form action="/board/save" method="post" enctype="multipart/form-data"> <!--파일첨부시 enctype 필수-->
        <input type="text" name="boardWriter" id="board-writer" class="form-control" placeholder="닉네임"> <br>
        <input type="text" name="boardPass" id="board-pass" class="form-control" placeholder="비밀번호"> <br>
        <input type="text" name="boardTitle" id="board-title" class="form-control" placeholder="제목"> <br>
        <textarea type="text" name="boardContents" id="board-contents" class="form-control"
                  placeholder="내용" cols="30" rows="10"></textarea>
        <input type="file" name="boardFile"> <br>
        <div>
            <input type="submit" class="buttonGreen" value="업로드">
            <input type="button" class="buttonRed" onclick="go_index()" value="취소">
        </div>
    </form>
</div>
<%@include file="../component/footer.jsp" %>

</body>
<script>
    const go_index = () => {
        location.href = "/"

    }
</script>
</html>
