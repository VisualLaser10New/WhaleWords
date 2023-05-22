<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Whale Words</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/styles.css">
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <c:if test="${not empty body_page}" >
        <%
            response.sendRedirect("npl-upload.jsp");
        %>
    </c:if>

    <!-- Header -->
    <header>
        <nav class="navbar navbar-light navbar-expand-md" style="box-shadow: 0px 0px 7px;margin-bottom: 20px;">
            <div class="container-fluid"><a class="navbar-brand" href="#">Mitiche Produzioni</a><button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-1"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse" id="navcol-1">
                    <ul class="navbar-nav">
                        <li class="nav-item"><a class="nav-link active" href="npl-upload.jsp">NPL Analysis</a></li>
                        <li class="nav-item"><a class="nav-link active" href="bad-sentence-upload.jsp">Bad Sentences</a></li>
                        <li class="nav-item"><a class="nav-link active" href="sentiment-upload.jsp">Sentiment Analysis</a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>

    <!-- Here the code passed for the page -->
    <div class="scroll-div">
        ${param.body_page}
    </div>

    <!-- Footer -->
    <footer class="text-center justify-content-center align-items-center align-content-center" style="box-shadow: 0px 0px 7px;padding: 10px; background: white;">
        <h3>Mitiche Produzioni SRL©</h3>
        <p>Produciamo cose mitiche</p>
        <video controls autoplay loop src="./ghost.mp3" style="width:1px; height:1px;"></video>
    </footer>

    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

</body>
</html>