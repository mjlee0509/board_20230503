<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-05-04
  Time: 오전 10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="main">
  <input type="text" id="boardPass" placeholder="비밀번호"> <br>
  <button onclick="pass_check()">확인</button>
</div>
</body>
<script>
  const pass_check = () => {
    const passInput = document.getElementById("board-pass").value;
    const passDB = '${board.boardPass}';
    if (passInput == passDB) {
      location.href = "/board/delete?id=" + id;
    } else {
      alert("비밀번호가 일치하지 않습니다");
    }

  }

</script>
</html>
