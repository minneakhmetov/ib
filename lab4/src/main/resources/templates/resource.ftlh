<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Resource</title>
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
        <div class="col-5">
            <h1>Resource: ${resource.name}</h1>
        </div>
        <div class="col-2">
            <h4>User: ${username}</h4>
        </div>
        <div class="col-2">
            <a href="/logout">
                <button style="text-align: right" type="button" class="btn btn-secondary">Logout</button>
            </a>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <img src="${resource.url}" height="200px">
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <h3>Available for:</h3>
            <ul class="list-group">
                <#list resource.availableRoles as role >
                    <li class="list-group-item">${role}</li>
                </#list>
            </ul>
        </div>
    </div>
</div>
</body>
</html>