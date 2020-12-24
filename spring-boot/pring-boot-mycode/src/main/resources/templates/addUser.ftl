<html >
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <form action="/user" method="post">
        name:<input name="name"/><br/>
        sex:<input type="radio" name="sex" value="1" />男
            <input type="radio" name="sex" value="2"/>女
        <br/>
        hobby:<input type="checkbox" name="hobby" value="1"/>抽烟
              <input type="checkbox" name="hobby" value="2"/>喝酒
              <input type="checkbox" name="hobby" value="3"/>烫头
        <br/>
        <input type="date" name="birthday"/>
        <br/>
        <input type="submit"/>
    </form>
</body>
</html>