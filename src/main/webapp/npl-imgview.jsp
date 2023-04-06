<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="index.jsp">
  <jsp:param name="body_page" value="
    <div class='container'>
      <div class='row'>
        <div class='col-md-6 col-lg-8 offset-lg-0 text-center'>
          <img class='rounded' alt='whale-words' src='${cloud_image}'>
      </div>
      <div class='col-md-6 col-lg-4 text-start'>
        <ul class='list-unstyled list-group'>
            ${words_list}
        </ul>
      </div>
      </div>
      </div>"
  />
</jsp:include>