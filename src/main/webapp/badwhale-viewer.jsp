<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="index.jsp">
    <jsp:param name="body_page" value="
    <div class='container-fluid'>
      <div class='row'>
        <div class='col-md col-lg text-start'>
          <ol>
              ${sentences_list}
          </ol>
        </div>
      </div>
    </div>"
    />
</jsp:include>