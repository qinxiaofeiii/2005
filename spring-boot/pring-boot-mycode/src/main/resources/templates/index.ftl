<html>
    <head>
        <title>Welcome!!!</title>
        <mate charset="utf-8"/>
    </head>
    <body>
        <button onclick="add()">新增</button>
        <table border="1">
            <tr>
                <td>id</td>
                <td>name</td>
                <td>sex</td>
                <td>hobby</td>
                <td>birthday</td>
                <td>操作</td>
            </tr>
            <#list userList as user>
                <tr>
                    <td>${user.id!}</td>
                    <td>${user.name!}</td>
                    <td><#if (user.sex == 1)>男 <#elseif (user.sex == 2)>女 <#else>未知</#if></td>
                    <td>
                        <#if (user.hobby?contains("1"))>抽烟</#if>
                        <#if (user.hobby?contains("2"))>喝酒</#if>
                        <#if (user.hobby?contains("3"))>烫头</#if>
                    </td>
                    <td>${user.birthday?string("yyyy-MM-dd")}</td>
                    <td>
                        <a href="/user/delete?id=${user.id}">删除</a>
                        <a href="/user/queryById?id=${user.id}">修改</a>
                    </td>
                </tr>
            </#list>
        </table>
    </body>
    <script>
        function add(){
            location.href="user/jumpAdd";
        }
    </script>
</html>