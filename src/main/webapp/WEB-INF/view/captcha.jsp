<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Captcha Test</title>
</head>
<body>
<form action="/captcha/view", method="post">
    <img src="${pageContext.request.contextPath}/captcha/view?uuid=${uuid}"/>
    <input type="text" name="answer" value=""/>
    <input type="hidden" name="captchaID" value="${uuid}"/>
    <input type="submit" value="인증"/>
</form>
</body>
</html>
