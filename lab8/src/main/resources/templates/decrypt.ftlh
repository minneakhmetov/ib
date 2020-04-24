<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Encrypt</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-3">
            <a href="/"> <button type="button" class="btn btn-secondary">Back</button></a>
        </div>
        <div class="col-9">
            <h1>Decrypt</h1>
        </div>
    </div>
    <hr/>
    <div class="row">
        <div class="col-12">
            <h2>DES: </h2>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <h3>Upload file and key here: </h3>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <form enctype="multipart/form-data" method="post" action="/decrypt/des">
                <h5>Encrypted file: </h5>
                <input type="file" name="file">
                <h5>Key file: </h5>
                <input type="file" name="key">
                <button type="submit" class="btn btn-primary">Upload</button>
            </form>
        </div>
    </div>

    <hr/>
    <div class="row">
        <div class="col-12">
            <h2>RSA: </h2>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <h3>Upload file and key here: </h3>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <form enctype="multipart/form-data" method="post" action="/decrypt/rsa">
                <h5>Encrypted file: </h5>
                <input type="file" name="file">
                <h5>Private key file: </h5>
                <input type="file" name="key">
                <button type="submit" class="btn btn-primary">Upload</button>
            </form>
        </div>
    </div>

    <hr/>
    <div class="row">
        <div class="col-12">
            <h2>AUTO: </h2>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <h3>Upload file and key here: </h3>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <form enctype="multipart/form-data" method="post" action="/decrypt/auto">
                <h5>Encrypted file: </h5>
                <input type="file" name="file">
                <h5>Encrypted key file: </h5>
                <input type="file" name="encryptedKey">
                <h5>Private key file: </h5>
                <input type="file" name="privateKey">
                <button type="submit" class="btn btn-primary">Upload</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>