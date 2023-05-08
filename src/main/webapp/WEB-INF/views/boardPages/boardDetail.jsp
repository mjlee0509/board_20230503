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
    <script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
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
        게시일자 : <input type="text" name="boardCreatedDate" id="board-created-date" class="form-control"
                      value="<fmt:formatDate value="${board.boardCreatedDate}" pattern="yyyy-MM-dd hh:mm:ss"></fmt:formatDate>"
                        disabled> <br>
        <br>
        내용 :
        <textarea name="boardContents" id="board-contents" class="form-control" cols="30" rows="10"
                       disabled>${board.boardContents}</textarea> <br>
        이미지 :
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
        <br>
        <div id="comment-write-area">
            <input type="text" id="comment-writer" placeholder="글쓴이">
            <input type="text" id="comment-contents" placeholder="내용을 입력하세요">
            <input type="button" onclick="comment_write()" value="댓글작성" class="button-green">
        </div>
        <div id="comment-list">
            <!-- 댓글기능구현 Step 7. -->
            <c:choose>
                <c:when test="${commentList == null}">
                    <h2>작성된 댓글이 없습니다</h2>
                </c:when>
                <c:otherwise>
                    <table>
                        <tr>
                            <th>id</th>
                            <th>작성자</th>
                            <th>내용</th>
                            <th>작성시간</th>
                        </tr>
                        <c:forEach items="${commentList}" var="comment">
                            <tr>
                                <td>${comment.id}</td>
                                <td>${comment.commentWriter}</td>
                                <td>${comment.commentContents}</td>
                                <td>
                                    <fmt:formatDate value="${comment.commentCreatedDate}" pattern="yyyy-MM-dd hh:mm:ss"></fmt:formatDate>
                                </td>
                            </tr>
                        </c:forEach>

                    </table>
                </c:otherwise>
            </c:choose>
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

    // 댓글기능구현1. js 쿼리 작성
    const comment_write = () => {
        // location.href = "/comment/save"
        const commentWriter = document.getElementById("comment-writer").value;
        const commentContents = document.getElementById("comment-contents").value;
        const boardId = '${board.id}';
        const result = document.getElementById("comment-list");
        $.ajax({
            type: "post",
            url: "/comment/save",
            data: {
                "commentWriter": commentWriter,
                "commentContents": commentContents,
                "boardId": boardId
            },
            success: function (res) {
                console.log(res);
                // 댓글기능구현 Step 4. 댓글 출력을 어떻게할지
                let output = "<table>";
                output += "<tr>"
                output += "<th>id</th>"
                output += "<th>작성자</th>"
                output += "<th>내용</th>"
                output += "<th>작성시간</th>"
                output += "</th>"
                for(let i in res) {
                    output += "<tr>"
                    output += "<td>" + res[i].id + "</td>";
                    output += "<td>" + res[i].commentWriter + "</td>";
                    output += "<td>" + res[i].commentContents + "</td>";
                    output += "<td>" + moment(res[i].commentCreatedDate).format("YYYY-MM-DD HH:mm:ss") + "</td>";
                }
                output += "</table>"
                result.innerHTML = output;
                document.getElementById("comment-writer").value = "";
                document.getElementById("comment-contents").value = "";
            },
            error: function () {
                console.log("실패")
            }
        });



        <%--const commentWriter = ${commentWriter};--%>
        <%--const commentContents = ${commentContents};--%>
        <%--const commentCreatedDate = ${commentCreatedDate};--%>
        <%--$.ajax ({--%>
        <%--    type: "post";--%>
        <%--    url: "/comment/save";--%>
        <%--    data: {--%>
        <%--        "commentWriter": commentWriter;--%>
        <%--        "commentContents": commentContents;--%>
        <%--        "commentCreatedDate": commentCreatedDate;--%>
        <%--    },--%>
        <%--    success: fuction(res) {--%>

        <%--}--%>
        <%--})--%>
    }

</script>
</html>
