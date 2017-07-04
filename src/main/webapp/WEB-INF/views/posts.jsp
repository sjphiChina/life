<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="page-contents">
  <div class="container">
    <div class="row">

      <c:if test="${loginedUser != null}">
        <!-- Newsfeed Common Side Bar Left
          ================================================= -->
        <div class="col-md-3 static">
          <div class="profile-card">
            <img src="http://placehold.it/300x300" alt="user" class="profile-photo" />
            <h5>
              <a href="<c:url value="/${loginedUser.userName}"></c:url>" class="text-white">${loginedUser.firstName}
                ${loginedUser.lastName}</a>
            </h5>
            <a href="#" class="text-white"><i class="ion ion-android-person-add"></i>
              ${numberOfFollower} followers</a>
          </div>
          <!--profile card ends-->
          <ul class="nav-news-feed">
            <li><i class="icon ion-ios-paper"></i>
            <div>
                <a href="<c:url value="/${loginedUser.userName}"></c:url>">Timeline</a>
              </div></li>
            <li><i class="icon ion-ios-people"></i>
            <div>
                <a href="newsfeed-people-nearby.html">People Nearby</a>
              </div></li>
            <li><i class="icon ion-ios-people-outline"></i>
            <div>
                <a href="newsfeed-friends.html">Friends</a>
              </div></li>
            <li><i class="icon ion-chatboxes"></i>
            <div>
                <a href="newsfeed-messages.html">Messages</a>
              </div></li>
            <li><i class="icon ion-images"></i>
            <div>
                <a href="newsfeed-images.html">Images</a>
              </div></li>
            <li><i class="icon ion-ios-videocam"></i>
            <div>
                <a href="newsfeed-videos.html">Videos</a>
              </div></li>
          </ul>
          <!--news-feed links ends-->
          <div id="chat-block">
            <div class="title">Chat online</div>
            <ul class="online-users list-inline">
              <li><a href="newsfeed-messages.html" title="Linda Lohan"><img
                  src="http://placehold.it/300x300" alt="user" class="img-responsive profile-photo" /><span
                  class="online-dot"></span></a></li>
              <li><a href="newsfeed-messages.html" title="Sophia Lee"><img
                  src="http://placehold.it/300x300" alt="user" class="img-responsive profile-photo" /><span
                  class="online-dot"></span></a></li>
              <li><a href="newsfeed-messages.html" title="John Doe"><img
                  src="http://placehold.it/300x300" alt="user" class="img-responsive profile-photo" /><span
                  class="online-dot"></span></a></li>
              <li><a href="newsfeed-messages.html" title="Alexis Clark"><img
                  src="http://placehold.it/300x300" alt="user" class="img-responsive profile-photo" /><span
                  class="online-dot"></span></a></li>
              <li><a href="newsfeed-messages.html" title="James Carter"><img
                  src="http://placehold.it/300x300" alt="user" class="img-responsive profile-photo" /><span
                  class="online-dot"></span></a></li>
              <li><a href="newsfeed-messages.html" title="Robert Cook"><img
                  src="http://placehold.it/300x300" alt="user" class="img-responsive profile-photo" /><span
                  class="online-dot"></span></a></li>
              <li><a href="newsfeed-messages.html" title="Richard Bell"><img
                  src="http://placehold.it/300x300" alt="user" class="img-responsive profile-photo" /><span
                  class="online-dot"></span></a></li>
              <li><a href="newsfeed-messages.html" title="Anna Young"><img
                  src="http://placehold.it/300x300" alt="user" class="img-responsive profile-photo" /><span
                  class="online-dot"></span></a></li>
              <li><a href="newsfeed-messages.html" title="Julia Cox"><img
                  src="http://placehold.it/300x300" alt="user" class="img-responsive profile-photo" /><span
                  class="online-dot"></span></a></li>
            </ul>
          </div>
          <!--chat block ends-->
        </div>
      </c:if>

      <div class="col-md-7">
        <c:if test="${loginedUser != null}">
          <!-- Post Create Box
            ================================================= -->
          <div class="create-post">
            <form:form method="POST" action="/life/posts/add" modelAttribute="post"
              enctype="multipart/form-data">
              <fieldset>
                <div class="row">
                  <div class="col-md-7 col-sm-7">
                    <div class="form-group">
                      <img src="http://placehold.it/300x300" alt="" class="profile-photo-md" />
                      <form:textarea name="texts" id="content" path="content" cols="30" rows="1"
                        class="form-control" placeholder="Anyting you want to remember"></form:textarea>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-5 col-sm-5">
                    <div class="tools">
                      <ul class="publishing-tools list-inline">
                        <li><a href="#"><i class="ion-compose"></i></a></li>
                        <!-- <li><a href="#"><i class="ion-images"></i></a></li> -->
                        <li><form:input id="contentImage" path="contentImage" type="file"
                            class="ion-images" /></li>
                        <li><a href="#"><i class="ion-ios-videocam"></i></a></li>
                        <li><a href="#"><i class="ion-map"></i></a></li>
                      </ul>
                      <button type="submit" id="btnAdd" class="btn btn-primary pull-right">Publish</button>
                    </div>
                  </div>
                </div>
              </fieldset>
            </form:form>
          </div>
          <!-- Post Create Box End-->
        </c:if>

        <c:forEach items="${posts}" var="post">
          <!-- Post Content
            ================================================= -->
          <div class="post-content">
            <img src="<c:url value="/img/${post.id}.png"></c:url>" alt="post-image"
              class="img-responsive post-image" />
            <div class="post-container">
              <img src="http://placehold.it/300x300" alt="user" class="profile-photo-md pull-left" />
              <div class="post-detail">
                <div class="user-info">
                  <h5>
                    <a href="<c:url value="/${post.userName}"></c:url>" class="profile-link">${post.userName}</a>
                    <span class="following">following</span>
                  </h5>
                  <p class="text-muted">${post.createdDate}</p>
                </div>
                <div class="reaction">
                  <a class="btn text-green"><i class="icon ion-thumbsup"></i> 13</a> <a
                    class="btn text-red"><i class="fa fa-thumbs-down"></i> 0</a>
                </div>
                <div class="line-divider"></div>
                <div class="post-text">
                  <p>${post.content}</p>
                </div>
                <div class="line-divider"></div>
                <!-- <div class="post-comment">
                      <img src="http://placehold.it/300x300" alt="" class="profile-photo-sm" />
                      <p><a href="timeline.html" class="profile-link">Diana </a><i class="em em-laughing"></i> Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud </p>
                    </div>
                    <div class="post-comment">
                      <img src="http://placehold.it/300x300" alt="" class="profile-photo-sm" />
                      <p><a href="timeline.html" class="profile-link">John</a> Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud </p>
                    </div> -->
              </div>
            </div>
          </div>
        </c:forEach>
      </div>

      <c:if test="${loginedUser != null}">
        <!-- Newsfeed Common Side Bar Right
          ================================================= -->
        <div class="col-md-2 static">
          <div class="suggestions" id="sticky-sidebar">
            <h4 class="grey">Who to Follow</h4>
            <div class="follow-user">
              <img src="http://placehold.it/300x300" alt="" class="profile-photo-sm pull-left" />
              <div>
                <h5>
                  <a href="timeline.html">Diana Amber</a>
                </h5>
                <a href="#" class="text-green">Add friend</a>
              </div>
            </div>
            <div class="follow-user">
              <img src="http://placehold.it/300x300" alt="" class="profile-photo-sm pull-left" />
              <div>
                <h5>
                  <a href="timeline.html">Cris Haris</a>
                </h5>
                <a href="#" class="text-green">Add friend</a>
              </div>
            </div>
            <div class="follow-user">
              <img src="http://placehold.it/300x300" alt="" class="profile-photo-sm pull-left" />
              <div>
                <h5>
                  <a href="timeline.html">Brian Walton</a>
                </h5>
                <a href="#" class="text-green">Add friend</a>
              </div>
            </div>
            <div class="follow-user">
              <img src="http://placehold.it/300x300" alt="" class="profile-photo-sm pull-left" />
              <div>
                <h5>
                  <a href="timeline.html">Olivia Steward</a>
                </h5>
                <a href="#" class="text-green">Add friend</a>
              </div>
            </div>
            <div class="follow-user">
              <img src="http://placehold.it/300x300" alt="" class="profile-photo-sm pull-left" />
              <div>
                <h5>
                  <a href="timeline.html">Sophia Page</a>
                </h5>
                <a href="#" class="text-green">Add friend</a>
              </div>
            </div>
          </div>
        </div>
      </c:if>
    </div>
  </div>
</div>
