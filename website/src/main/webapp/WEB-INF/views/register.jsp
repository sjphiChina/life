<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">Please fill out this form</h3>
        </div>
        <div class="panel-body">

          <form:form method="POST" modelAttribute="user" class="form-horizontal"
            enctype="multipart/form-data">
            <form:errors path="*" cssClass="alert alert-danger" element="div" />
            <fieldset>
              <legend>Create new post</legend>
              <div class="form-group">
                <label class="control-label col-lg-2" for="userName"><spring:message
                    code="register.form.userName.label" /></label>
                <div class="col-lg-10">
                  <form:input id="userName" path="userName" type="text" class="form:input-large" />
                </div>
              </div>

              <div class="form-group">
                <label class="control-label col-lg-2" for="email"><spring:message
                    code="register.form.email.label" /></label>
                <div class="col-lg-10">
                  <form:input id="email" path="email" type="text" class="form:input-large" />
                </div>
              </div>

              <div class="form-group">
                <label class="control-label col-lg-2" for="password"><spring:message
                    code="register.form.password.label" /></label>
                <div class="col-lg-10">
                  <form:input id="password" path="password" type="password" class="form:input-large" />
                </div>
              </div>

              <div class="form-group">
                <label class="control-label col-lg-2" for="firstName"><spring:message
                    code="register.form.firstName.label" /></label>
                <div class="col-lg-10">
                  <form:input id="firstName" path="firstName" type="text" class="form:input-large" />
                </div>
              </div>

              <div class="form-group">
                <label class="control-label col-lg-2" for="lastName"><spring:message
                    code="register.form.lastName.label" /></label>
                <div class="col-lg-10">
                  <form:input id="lastName" path="lastName" type="text" class="form:input-large" />
                </div>
              </div>

              <div class="form-group">
                <label class="control-label col-lg-2" for="profileImage"> <spring:message
                    code="register.form.profileImage.label" />
                </label>
                <div class="col-lg-10">
                  <form:input id="profileImage" path="profileImage" type="file"
                    class="form:input-large" />
                </div>
              </div>

              <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                  <input type="submit" id="btnAdd" class="btn btn-primary" value="Add" />
                </div>
              </div>
            </fieldset>
          </form:form>
        </div>
      </div>
    </div>
  </div>
</div>