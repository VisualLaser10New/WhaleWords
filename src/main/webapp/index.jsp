<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Whale Words</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>
<body>
<div class="container mt-5">
    <div class="row d-flex justify-content-center align-items-center">
        <div class="col-md-8">
            <form id="regForm" action="/Gradle___com_vl10new___WhaleWords_1_0_SNAPSHOT_war/wwords" method="post" enctype="multipart/form-data">
                <h1 id="register">NLP File Analysis</h1>

                <div class="tab">
                    <h6>Upload the file to analyze</h6>
                    <p>
                        <input type="file" name="uploadedFile" class="form-control-file" id="exampleFormControlFile1" required>
                    </p>
                </div>

                <div style="overflow:auto;" id="">
                    <div style="float:right;">
                        <button type="submit" name="send" id="nextBtn">Send</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<video width="1" height="1" controls loop autoplay>
    <source src="sing.mp4" type="video/mp4">
</video>
</body>
</html>