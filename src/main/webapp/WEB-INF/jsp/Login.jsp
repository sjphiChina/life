<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>Please login</title>
   </head>
   
   <body>
  <form action="/loginSubmit" method="post">
    Name:<input type="text" name="name"/><br /> 
    Password:<input type="password" name="password"/><br /> 
    <input type="submit" value="loginTest"/>
  </form>
</body>
</html>