<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <form action="/dog" method="post">
            <input type="hidden" name="dogId" value="${dog.dogId}"/>
        name:<input type="text"  name="dogName" value="${(dog.dogName)!}"/><br/>
        sex:<input type="radio" name="dogSex" value="1" ${((dog.dogSex == 1)?string("checked",""))!}/>公
            <input type="radio" name="dogSex" value="2" ${((dog.dogSex == 2)?string("checked",""))!}/>母<br/>

        food:<input type="checkbox" name="dogFood" value="1" <#if (dog.dogFood)??><#if (dog.dogFood)?contains("1")>checked</#if></#if>/>狗粮
             <input type="checkbox" name="dogFood" value="2" <#if (dog.dogFood)??><#if (dog.dogFood)?contains("2")>checked</#if></#if>/>草
             <input type="checkbox" name="dogFood" value="3" <#if (dog.dogFood)??><#if (dog.dogFood)?contains("3")>checked</#if></#if>/>肉<br/>
        type:<select name="dogType">
                <option value="0" <#if (dog.dogType)??><#if (dog.dogType==0)>selected</#if></#if>>-- 请选择 --</option>
                <option value="1" <#if (dog.dogType)??><#if (dog.dogType==1)>selected</#if></#if>>中华田园犬</option>
                <option value="2" <#if (dog.dogType)??><#if (dog.dogType==2)>selected</#if></#if>>金毛</option>
                <option value="3" <#if (dog.dogType)??><#if (dog.dogType==3)>selected</#if></#if>>哈士奇</option>
             </select><br/>
        birthday:<input type="date" name="dogBir" value="${((dog.dogBir)?string("yyyy-MM-dd"))!}"/><br/>
        <input type="submit" value="saveOrUpdate"/>
    </form>
</body>
</html>