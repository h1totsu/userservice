<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3" style="text-align: center">
            <div style="color: red; margin-top: 10px"><b>${error}</b></div>
            <form:form action="/user" method="post" modelAttribute="userForm" cssStyle="margin-top: 10px">
                <div class="form-group">
                    <form:label path="userName">User Name:</form:label>
                    <form:input cssClass="text-input" path="userName" />
                </div>
                <div class="form-group">
                    <form:label path="firstName">First Name:</form:label>
                    <form:input cssClass="text-input" path="firstName" />
                </div>
                <div class="form-group">
                    <form:label path="lastName">Last Name:</form:label>
                    <form:input cssClass="text-input" path="lastName" />
                </div>
                <div class="form-group">
                    <form:label path="password">Password:</form:label>
                    <form:input cssClass="text-input" path="password" />
                </div>
                <div class="form-group">
                    <form:label path="organizationUnit">Organization Unit:</form:label>
                    <form:input cssClass="text-input" path="organizationUnit" />
                </div>
                <button type="submit" class="btn btn-default">Submit</button>
            </form:form>
        </div>
    </div>
</div>

<script
        src="https://code.jquery.com/jquery-3.1.1.min.js"
        integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
        crossorigin="anonymous"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
</body>
</html>
