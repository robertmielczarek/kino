<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

<sql:setDataSource var="snapshot" driver='<%= application.getInitParameter("driver")%>'
     url='<%= application.getInitParameter("url")%>'
     user='<%= application.getInitParameter("user")%>'  password='<%= application.getInitParameter("password")%>'/>

<%
String search_value = request.getParameter("search");
String id_kina = request.getParameter("cinema_id");
String query = "select 'wyszykaj ponownie';";

if(search_value!="")
	query = "select * from film where Title LIKE '%" + search_value + "%';";	
if(search_value==null && id_kina==null)
	query = "select * from film;";
else 
	out.println(" ");

%>

<%
if(search_value==""){
	out.println("<section>");
	out.println("Wyszykaj ponownie");
	out.println("</section>");
}
if(search_value!=""){
	

%>



<c:set var="q" value="<%= query %>"/>

<sql:query dataSource="${snapshot}" var="result">
	${q}
</sql:query>

<c:forEach var="row" items="${result.rows}">
<section>
	<div class="film" style="padding-left: 20px; padding-right: 20px; ">
		<b>Tytuł:</b> <c:out value="${row.Title}" />
		<br><br>
		<b>Czas trwania:</b> <c:out value="${row.Lenght}" />
		<br><br>
		<b>Gatunek:</b> <c:out value="${row.Genre}" />
		<br><br>
		<b>Opis:</b> <c:out value="${row.Description}" />
		<br><br>
		<form action="cinemas.jsp" method="post">
			<input type='submit' value='wybierz kino' class='sub'>
			<input type="hidden" name="movie" value="${row.Title}">
		</form>
	</div>
</section>	   	
</c:forEach>

<%
}
%>




<footer>
<p>&copy; 2014 IO Projest Group. Wszystkie prawa zastrzeżone.</p>
</footer>

</body>
</html>