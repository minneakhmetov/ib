<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Main</title>
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
        <div class="col-8">
            <h1>Available resources:</h1>
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
            <div class="row">
                <div class="col-12">
                    <h3>Available for:</h3>
                    <ul class="list-group">
                        <#list resources as resource >
                            <li class="list-group-item">
                                <div class="container">
                                    <div class="row">
                                        <div class="col-3">
                                            <img src="${resource.url}" height="25px">
                                        </div>
                                        <div class="col-3">
                                            <a href="/resources/${resource.id}"><h4>${resource.name}</h4></a>
                                        </div>
                                        <#if isManager??>
                                            <div class="col-3">
                                                <a href="/init/modify/${resource.id}">
                                                    <button type="button" class="btn btn-secondary">Modify</button>
                                                </a>
                                            </div>
                                        </#if>
                                        <#if isAdmin??>
                                            <div class="col-3">
                                                <form action="/delete/${resource.id}" method="get">
                                                    <button type="submit" class="btn btn-danger">Delete</button>
                                                </form>
                                            </div>
                                        </#if>
                                    </div>
                                </div>
                            </li>
                        </#list>
                    </ul>
                    <#if isTeacher??>
                        <a href="/init/add">
                            <button type="button" class="btn btn-primary btn-lg btn-block">Add resource</button>
                        </a>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>