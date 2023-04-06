<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="index.jsp">
    <jsp:param name="body_page" value="<div class='container mt-5'>
    <div class='row d-flex justify-content-center'>
        <div class='col-md-8'>
            <h1 id='register'>NLP File Analysis</h1>
            <form id='regForm' action='/Gradle___com_vl10new___WhaleWords_1_0_SNAPSHOT_war/wwords' method='post' enctype='multipart/form-data'>
    <div class='form-row'>
        <div class='form-group col-sm-10'>
            <div class='input-group'>
                <div class='input-group-prepend'>
                    <span class='input-group-text'>Upload the file to analyze</span>
                </div>
                <input type='file' name='uploadedFile' value='Explore' accept='text/plain' class='form-control' id='exampleFormControlFile1' required>
            </div>
        </div>
    </div>
    <div class='form-row'>
        <div class='form-group col-sm-10'>
            <div class='input-group'>
                <div class='input-group-prepend'>
                    <span class='input-group-text'>Limit to x words</span>
                </div>
                <input type='number' min='1'  name='limitNo' value='10' class='form-control' required>
            </div>
        </div>
    </div>
    <div class='form-row'>
        <div class='form-group col-sm-10'>
            <div style='overflow:auto;' id=''>
                <div style='float:left;'>
                    <button type='submit' name='send' id='nextBtn' class='btn btn-primary'>Send</button>
                </div>
            </div>
        </div>
    </div>
    </form>
    </div>
    </div>
    </div>
    <video width='1' height='1' controls loop autoplay>
        <source src='sing.mp4' type='video/mp4'>
    </video>"/>
</jsp:include>
