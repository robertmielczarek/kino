<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList, java.lang.*" %>
<!DOCTYPE html>
<head>


<link rel="stylesheet" type="text/css" href="mystyle.css">

<title>Cinema Site</title>
</head>

<body>

<header class="bg">
	<div class="logo">
	</div>

	<h1 class="logo_text" >Cinema Site</h1>
 
</header>

<nav>
<ul>
  <li><form method="get" action="all_movies.jsp">
  	<input type="text" name="search" class="search">
	
  	<input type="submit" value="" style="width: 27px; height: 27px; background: url('./images/ic_search27.png') no-repeat; border:none;">
  </form></li>
  <a href="rep.jsp"><li class="option">Repertuar</li></a>
  <a href="all_movies.jsp"><li class="option">Co gramy</li></a>
  <a href="return_ticket.jsp"><li class="option">Zwróć bilet</li></a>
</ul>
</nav>

<section>
<font size="24">Formularz rezerwacji</font><br><br>

<%
String[] ids=request.getParameterValues("seats");
// this will get array of values of all checked checkboxes
%>
<div style="margin-left: 15px;">Wybrane miejsca: 
<b>
<%

   for(String id: ids)
     out.println(id + ", ");
   
	String seans = request.getParameter("show");
	String price = request.getParameter("price");
	out.println("<br>");
	String query = "select f.Title from seans s, film f where s.ID_Movie = f.ID_Movie and s.ID_Show = " + seans;
%>
</b>

<sql:setDataSource var="snapshot" driver='<%= application.getInitParameter("driver")%>'
     url='<%= application.getInitParameter("url")%>'
     user='<%= application.getInitParameter("user")%>'  password='<%= application.getInitParameter("password")%>'/>
     
<c:set var="q" value="<%= query %>"/>

<sql:query dataSource="${snapshot}" var="result">
	${q}
</sql:query>

Wybrany seans: 
<c:forEach var="row" items="${result.rows}">
	 <b><c:out value="${row.Title}" /></b>
</c:forEach>
     


<br><br><br>
<form method="post" action="check.jsp">
<input type="hidden" name="show" value="<%=seans%>">
<input type="hidden" name="price" value="<%=price%>">
<%
for(String id: ids) {
%>
    <input type="hidden" name="seats" value="<%=id %>" >

<%
}
%>

Wyberz rodzaj zniżki:
<select name="discountlist" style="height: 35px; border-color: #f4624a">
  <option value="Dzieci/Seniorzy">Dzieci/Seniorzy</option>
  <option value="Studenci">Studenci</option>
  <option value="Grupy">Grupy</option>
</select>
<br><br>

    
  <div id="seats">
    
  </div>

  <div class="group">      
    <input class="field" type="text" required name="name">
    <span class="highlight"></span>
    <span class="bar"></span>
    <label>Imię</label>
  </div>
    
  <div class="group">      
    <input class="field" type="text" required name="surname">
    <span class="highlight"></span>
    <span class="bar"></span>
    <label>Nazwisko</label>
  </div>

  <div class="group">      
    <input class="field" type="email" required name="email">
    <span class="highlight"></span>
    <span class="bar"></span>
    <label>E-mail</label>
  </div>



  <div class="group">
  <input class="sub" type="submit" value="Wyślij" style="position: absolute; left: 155px; top: -5px;">
  </style>
  </div>

    
</form>
<br>
</section>




<footer>
<p>&copy; 2014 IO Projest Group. Wszystkie prawa zastrzeżone.</p>
</footer>

</body>
</html>