<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="index.jsp">
    <jsp:param name="body_page" value="
    <div class='container-fluid'>
      <div class='row'>
        <div class='col-md-6 col-lg-4 text-start'>
          <ul class='list-unstyled list-group'>
              ${sentences_list}
          </ul>
        </div>
      </div>
    </div>"
    />
</jsp:include>