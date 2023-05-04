<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-05-03
  Time: 오전 9:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/main.css"></link>
    <link rel="stylesheet" href="/resources/css/table.css">
</head>
<body>
<%@include file="../component/header.jsp"%>
<%@include file="../component/nav.jsp"%>
<div class="main">
    <h2>글목록</h2>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>제목</th>
            <th>글쓴이</th>
            <th>게시일자</th>
            <th>조회수</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="#{boardList}" var="board">
            <tr>
                <td>${board.id}</td>
                <td>
                    <a href="/board?id=${board.id}">${board.boardTitle}</a>
                </td>
                <td>${board.boardWriter}</td>
                <td>${board.boardCreatedDate}</td>
                <td>${board.boardHits}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>



</div>
<%@include file="../component/footer.jsp"%>
</body>
</html>
