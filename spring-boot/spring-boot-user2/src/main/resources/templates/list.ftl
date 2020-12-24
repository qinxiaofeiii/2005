<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <input type="button" value="新增" onclick="toSave()"/>
    <table border="1">
        <tr>
            <td>id</td>
            <td>name</td>
            <td>sex</td>
            <td>type</td>
            <td>food</td>
            <td>birthday</td>
            <td>operation</td>
        </tr>
        <#list dogList as dog>
            <tr>
                <td>${dog.dogId!}</td>
                <td>${dog.dogName!}</td>
                <td><#if (dog.dogSex == 1)>公<#elseif (dog.dogSex == 2)>母<#else >未知</#if></td>
                <td>
                    <#if (dog.dogType)??>
                        <#if (dog.dogType==1)>中华田园犬</#if>
                        <#if (dog.dogType==2)>金毛</#if>
                        <#if (dog.dogType==3)>哈士奇</#if>
                    </#if>
                </td>
                <td>
                    <#if (dog.dogFood)??>
                        <#if (dog.dogFood)?contains("1")>狗粮</#if>
                        <#if (dog.dogFood)?contains("2")>草</#if>
                        <#if (dog.dogFood)?contains("3")>肉</#if>
                    </#if>
                </td>
                <td>${(dog.dogBir)?string("yyyy-MM-dd")}</td>
                <td>
                    <input type="button" value="删除" onclick="delData(${dog.dogId})"/>
                    <input type="button" value="修改" onclick="updateData(${dog.dogId})"/>
                </td>
            </tr>
        </#list>
    </table>
<script>
    function toSave(){
        location.href="/dog/toSave";
    }
    function delData(id){
        location.href="/dog/delData?id="+id;
    }
    function updateData(id){
        location.href="/dog/findById?id="+id;
    }
</script>
</body>
</html>