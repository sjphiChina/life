<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!-- 404 Error
    ================================================= -->
<div class="error-page">
  <div class="error-content">
    <div class="container">
      <img src="/life/resources/images/404.png" alt="" class="img-responsive" />
      <div class="error-message">
        <h1 class="error-title">Whoops!</h1>
        <p>Looks like you are lost. But don't worry there is plenty to see!!</p>
      </div>
      <div class="container">
         <p>${status}</p>
         <p>${errorMessage}</p>
         <p>${cause}</p>
      </div>
      <form class="search-form">
        <div class="form-group">
          <label for="search_content">Search Content!</label> <input id="search_content" type="text"
            class="form-control" value="" placeholder="Search what you want to find...">
        </div>
        <button type="submit" class="btn btn-primary">Search Now!</button>
      </form>
    </div>
  </div>
</div>
