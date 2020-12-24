<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <form action="/user" method="post">
        <input type="hidden" name="id" value="${(user.id)!}"/>
        name:<input type="text" name="name" value="${(user.name)!}"/><br/>
        sex:<input type="radio" name="sex" value="1" ${(user.sex ==1)?string("checked","")}/>男
            <input type="radio" name="sex" value="2" ${(user.sex ==2)?string("checked","")}/>女<br/>
        hobby:<input type="checkbox" name="hobby" value="1" <#if (user.hobby)??><#if user.hobby?contains('1')>checked</#if></#if>/>抽烟
              <input type="checkbox" name="hobby" value="2" <#if (user.hobby)??><#if user.hobby?contains('2')>checked</#if></#if>/>喝酒
              <input type="checkbox" name="hobby" value="3" <#if (user.hobby)??><#if user.hobby?contains('3')>checked</#if></#if>/>烫头<br/>
        class:<select name="classs">
                <option value="0" <#if (user.classs)??>${(user.classs == 0)?string("seleced","")}</#if>>-- 请选择 --</option>
                <option value="1" <#if (user.classs)??>${(user.classs == 1)?string("selected","")}</#if>>-- A班 --</option>
                <option value="2" <#if (user.classs)??>${(user.classs == 2)?string("selected","")}</#if>>-- B班 --</option>
                <option value="3" <#if (user.classs)??>${(user.classs == 3)?string("selected","")}</#if>>-- C班 --</option>
              </select><br/>
        birthday:<input type="date" name="birthday" value="${(user.birthday?string("yyyy-MM-dd"))!}"/><br/>
                 <input type="submit" value="saveOrUpdate"/>

    </form>
</body>
</html>