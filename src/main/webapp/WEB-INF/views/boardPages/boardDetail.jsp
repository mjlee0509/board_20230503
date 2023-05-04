<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-05-03
  Time: 오후 2:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="/resources/css/form.css">
    <link rel="stylesheet" href="/resources/css/table.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/resources/bootstrap.min.css">
    <script src="/resources/bootstrap.bundle.min.js"></script>
</head>
<body>
<%@include file="../component/header.jsp" %>
<%@include file="../component/nav.jsp" %>
<div class="main">
    <form action="/board/update" method="post">
        <h2></h2>
        글쓴이 : <input type="text" name="boardWriter" id="board-writer" class="form-control" value="${board.boardWriter}"
                     disabled>
        비밀번호 : <input type="password" name="boardPass" id="board-pass" class="form-control" value="${board.boardPass}"
                      disabled>
        제목 : <input type="text" name="boardTitle" id="board-title" class="form-control" value="${board.boardTitle}"
                    disabled>
        조회수 : <input type="text" name="boardHits" id="board-hits" class="form-control" value="${board.boardHits}"
                     disabled>
        게시일자 : <fmt:formatDate value="${board.boardCreatedDate}" pattern="yyyy-MM-dd hh:mm:ss"></fmt:formatDate> <br>
        <br>
        내용 :
        <textarea name="boardContents" id="board-contents" class="form-control" cols="30" rows="10"
                       disabled>${board.boardContents}</textarea>

        <c:if test="${board.fileAttached == 1}">
            <div class="image-container">
                <c:forEach items="${boardFileList}" var="boardFile">
                <img src="${pageContext.request.contextPath}/upload/${boardFile.storedFileName}" alt="" width="100" height="100">
                </c:forEach>
            </div>
        </c:if> <br>

        <br>
        <div style="float: right">
            <input type="button" class="button-green" value="수정" onclick="go_update()">
            <input type="button" class="button-green" value="목록" onclick="go_list()">
            <input type="button" class="button-red" value="삭제" onclick="go_delete()">
        </div>
    </form>

</div>
<%@include file="../component/footer.jsp" %>
</body>
<script>
    <%--document.getElementById("board-contents").innerHTML = "${board.boardContents}";--%>
    const go_list = () => {
        location.href = "/board/list"
    }
    const go_update = () => {
        const id = '${board.id}';
        location.href = "/board/update?id=" + id;
    }
    const go_delete = () => {
        const id = '${board.id}';
        location.href = "/board/delete-check?id=" + id;
    }

</script>
</html>
