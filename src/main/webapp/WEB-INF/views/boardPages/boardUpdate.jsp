<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-05-03
  Time: 오후 4:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<%@include file="../component/header.jsp"%>
<%@include file="../component/nav.jsp"%>
<div class="main">
    <h2></h2>
    <form action="/board/update" method="post" name="updateForm">
        글쓴이 : <input type="text" name="boardWriter" id="board-writer" class="form-control" value="${board.boardWriter}" disabled>
        비밀번호 : <input type="text" name="boardPass" id="board-pass" class="form-control" placeholder="비밀번호">
        제목 : <input type="text" name="boardTitle" id="board-title" class="form-control" value="${board.boardTitle}">
        조회수 : <input type="text" name="boardHits" id="board-hits" class="form-control" value="${board.boardHits}" disabled>
        내용 : <textarea name="boardContents" id="board-contents" class="form-control"
                  placeholder="내용" cols="30" rows="10">${board.boardContents}</textarea>    <!--textarea는 그냥 태그 사이에 값을 넣어주면 된다--!>
        <div>
            <input type="button" onclick="update_req()" class="buttonGreen" value="수정">
        </div>
    </form>

</div>
<%@include file="../component/footer.jsp"%>

</body>
<script>
    document.getElementById("board-contents").innerHTML = "${board.boardContents}";

    const update_req = () => {
        const passInput = document.getElementById("board-pass").value;
        const passDB = '${board.boardPass}';
        if (passInput == passDB) {
            document.updateForm.submit();
        } else {
            alert("비밀번호가 일치하지 않습니다");
        }
    }
</script>
</html>
