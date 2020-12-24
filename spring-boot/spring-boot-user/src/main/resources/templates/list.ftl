<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <input type="button" onclick="toAdd()" value="新增"/>
    <table border="1">
        <tr>
            <td>id</td>
            <td>name</td>
            <td>sex</td>
            <td>hobby</td>
            <td>birthday</td>
            <td>classs</td>
            <td>operation</td>
        </tr>
        <#list userList as user>
            <tr>
                <td>${user.id!}</td>
                <td>${user.name!}</td>
                <td><#if (user.sex == 1)>男<#elseif (user.sex == 2)>女<#else>未知</#if></td>
                <td>
                    <#if (user.hobby?contains("1"))>抽烟</#if>
                    <#if (user.hobby?contains("2"))>喝酒</#if>
                    <#if (user.hobby?contains("3"))>烫头</#if>
                </td>
                <td>
                    ${user.birthday?string("yyyy-MM-dd")}
                </td>
                <td><#if (user.classs)??>
                        <#if (user.classs == 1)>A班</#if>
                        <#if (user.classs == 2)>B班</#if>
                        <#if (user.classs == 3)>C班</#if>
                    </#if>
                </td>
                <td>
                    <input type="button" onclick="delData(${user.id!})" value="删除"/>
                    <input type="button" onclick="update(${user.id!})" value="修改"/>
                </td>
            </tr>
        </#list>
    </table>
</body>
<script>
    function toAdd(){
        location.href="/user/toAdd";
    }
    function delData(id){
        location.href="/user/delData?id="+id;
    }
    function update(id){
        location.href="/user/update?id="+id;
    }
</script>
</html>