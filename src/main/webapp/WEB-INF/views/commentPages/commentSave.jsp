<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-05-08
  Time: 오전 8:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="/resources/css/form.css">
    <link rel="stylesheet" href="/resources/css/table.css">
    <link rel="stylesheet" href="/resources/bootstrap.min.css">
    <script src="/resources/bootstrap.bundle.min.js"></script>
</head>
<body>
<h2>댓글창</h2>
<div class="main">
    <form action="/comment/save" method="post">
        <input type="text" name="commentWriter" class="form-control" placeholder="닉네임">
        <textarea name="commentContents" id="comment-contents" cols="10" rows="10"
                  class="form-control" placeholder="내용을 입력하세요">
        </textarea>
        <br>
            <input type="submit" value="댓글쓰기" class="button-green" style="float: right">
        </div>
    </form>
</div>

</body>
</html>
