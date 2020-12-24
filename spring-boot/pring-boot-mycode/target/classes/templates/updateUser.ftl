<html >
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <#list userList as user>
    <form action="/user/update" method="post">
        <input type="hidden" name="id" value="${user.id}"/>
        name:<input name="name" value="${user.name}"/><br/>
        sex:<input type="radio" name="sex" value="1" <#if (user.sex == 1)>checked</#if>/>男
            <input type="radio" name="sex" value="2" <#if (user.sex == 2)>checked</#if>/>女
        <br/>
        hobby:<input type="checkbox" name="hobby" value="1" <#if (user.hobby?contains("1"))>checked</#if>/>抽烟
              <input type="checkbox" name="hobby" value="2" <#if (user.hobby?contains("2"))>checked</#if>/>喝酒
              <input type="checkbox" name="hobby" value="3" <#if (user.hobby?contains("3"))>checked</#if>/>烫头
        <br/>
        <input type="date" name="birthday" value="${user.birthday?string("yyyy-MM-dd")}" />
        <br/>
        <input type="submit" value="修改"/>
    </form>
    </#list>
</body>
</html>