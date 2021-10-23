<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2021/10/1
  Time: 12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    String path=request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%--必须放在整个html文档的第一行，所有浏览器都支持<!DOCTYPE>标签。
是用来告诉浏览器用哪个html版本解析当前的html页面。--%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<%--    meta是html语言head区的一个辅助性标签：
当前参数是设定页面使用的字符集--%>
    <title>登录</title>
</head>
<link rel="icon" href="images/search.gif" type="img/x-ico"/>
<%--加载一个图片-静态资源--%>
<link rel="stylesheet" href="css/login.css" type="text/css">
<%--引入一个css文件--%>
<%--link标签：rel此属性命名链接文档与当前文档的关系。href此属性指定被链接资源的URL.type这个属性被用于定义链接的内容的类型
HTML外部资源链接元素(<link>) 规定了当前文档与外部资源的关系。该元素最常用于链接样式表--%>
<%--css要写在页面的顶部，便于优先加载样式表。
css用于页面布局--%>
<script type="text/javascript" src="<%=path%>/js/jquery-3.3.1.min.js>"></script>
<body>
<div id="top"></div>
<div id="main">
    <img src="images/login.jpg" id="main_bg"/>
<%--    第一个图层的图片，下一个图层是table--%>
    <div id="login_block">
<%--        div就是样式表中的定位技术，div的全称就是division，有时候我们也习惯叫图层
(1).id在CSS中是以“#”开头的命名的；id具有唯一性，在一个网页中只能定义一次；
(2).class在css中是以“.”开头命名的；.class命名的类，可以出现多次。
--%>
        <form action="/login" method="post" id="loginForm">
             <table border="0">
                 <tr>
                    <td class="title">用户名：</td>
                    <td class="input"><input type="text" name="username" id="username" class="login_input"/></td>
                 </tr>
                <tr>
                    <td class="title">密码：</td>
                    <td class="input"><input type="password" name="password" id="password" class="login_input"/></td>
                 </tr>
                <tr>
                    <td class="title">用户类型：</td>
                    <td class="input">
                         <input type="radio" name="type" value="reader" checked="checked"/>&nbsp;&nbsp;读者&nbsp;&nbsp;
<%--            单选按钮< input type=" radio"/>
                带有一个预选定复选框的 HTML 表单 checked值为checked表示预先选定复选框或单选按钮。--%>
<%--                HTML中使用“&nbsp;”表示1个空格字符（英文的1个空格字符），但1个中文汉字占2个英文字符，--%>
                        <input type="radio" name="type" value="admin"/>&nbsp;&nbsp;管理员
                     </td>
                </tr>
                <tr>
                <td colspan="2">
<%--            colspan	设置单元格横跨的列数（用于合并水平方向的单元格
                rowspan	设置单元格竖跨的行数（用于合并竖直方向的单元格）--%>
                    <input type="submit" value="登录" class="btn"/>
                    <div  id="reset" class="btn">重&nbsp;&nbsp;置</div>
<%--                这里为什么使用图层标签--%>
                 </td>
                 </tr>
            </table>
        </form>
    </div>
</div>

<div id="footer">
    <div class="foot_content">
        CopyRight &copy; 2008 www.**********.com 西安市*****有限公司
    </div>
    <div class="foot_content">
        版权所有 Library V1.0
    </div>
</div>

</body>
</html>
