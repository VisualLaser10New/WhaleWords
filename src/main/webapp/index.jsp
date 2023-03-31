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
            <h1 id="register">NLP File Analysis</h1>
            <form id="regForm" action="/Gradle___com_vl10new___WhaleWords_1_0_SNAPSHOT_war/wwords" method="post" enctype="multipart/form-data">
                <div class="form-row">
                    <div class="form-group col-sm-10">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">Upload the file to analyze</span>
                            </div>
                            <input type="file" name="uploadedFile" value="Explore" class="form-control" id="exampleFormControlFile1" required>
                        </div>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-sm-10">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">Limit to x words</span>
                            </div>
                            <input type="number" min="1"  name="limitNo" value="10" class="form-control" required>
                        </div>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-sm-10">
                        <div style="overflow:auto;" id="">
                            <div style="float:left;">
                                <button type="submit" name="send" id="nextBtn" class="btn btn-primary">Send</button>
                            </div>
                        </div>
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