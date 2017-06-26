<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<section class="container">
  <form:form method="POST" modelAttribute="post" class="form-horizontal"
    enctype="multipart/form-data">
    <form:errors path="*" cssClass="alert alert-danger" element="div" />
    <fieldset>
      <legend>Create new post</legend>
      <div class="form-group">
        <label class="control-label col-lg-2" for="content"><spring:message
            code="addPost.form.content.label" /></label>
        <div class="col-lg-10">
          <form:textarea id="content" path="content" rows="2" />
        </div>
      </div>

      <div class="form-group">
        <label class="control-label col-lg-2" for="contentImage"> <spring:message
            code="addPost.form.contentImage.label" />
        </label>
        <div class="col-lg-10">
          <form:input id="contentImage" path="contentImage" type="file" class="form:input-large" />
        </div>
      </div>

      <div class="form-group">
        <div class="col-lg-offset-2 col-lg-10">
          <input type="submit" id="btnAdd" class="btn btn-primary" value="Add" />
        </div>
      </div>
    </fieldset>
  </form:form>
</section>