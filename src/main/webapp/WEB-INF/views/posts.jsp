<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
   href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>All Posts</title>
</head>
<body>

   <section class="container">
      <div class="row">
         <c:forEach items="${posts}" var="post">
            <div class="col-sm-6 col-md-3">
               <div class="thumbnail">
                  <div class="caption">
                     <h5>UserName: </h5>${post.userName}
                     <p>${post.content}</p>
                     <p><h6>Created Date: </h6>${post.createdDate}
                     <p><h6>Modified Date: </h6><p>${post.modifiedDate}
                  </div>
               </div>
            </div>
         </c:forEach>
      </div>
   </section>
</body>
</html>
