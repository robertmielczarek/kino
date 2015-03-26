<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<section>

<sql:setDataSource var="snapshot" driver='<%= application.getInitParameter("driver")%>'
     url='<%= application.getInitParameter("url")%>'
     user='<%= application.getInitParameter("user")%>'  password='<%= application.getInitParameter("password")%>'/>

<%
String id_kina = request.getParameter("cinema_id");
%>


<sql:query dataSource="${snapshot}" var="result">
	select s.ID_Auditorium, f.Title, s.Date, s.ID_Show, s.Price from seans s, kino k, film f, sala sala
	where s.ID_Movie = f.ID_Movie
	and s.ID_Auditorium = sala.ID_hall
	and sala.ID_Cinema = k.ID_Cinema
	and k.ID_Cinema = '<%= id_kina %>';
	</sql:query>

<table width="100%" style="text-align: left;">

	<tr>
		   <th>Sala</th>
		   <th>Tytuł</th>
		   <th>Data</th>
		   <th>Cena</th>
		</tr>
		
	<c:forEach var="row" items="${result.rows}">
		<tr>
		
		   	<td style="height: 70px;"><c:out value="${row.ID_Auditorium}"/></td>
		  	<td style="height: 70px;">
				   	<c:out value="${row.Title}" />
		   	</td>
		   	<td style="height: 70px;">
				   	<c:out value="${row.Date}" />
		   	</td>
		   	
		   	<td style="height: 70px;">
				<c:set var="balance" value="${row.Price}" />
				<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${balance}" /> zł
		   	</td>
		   	
		   	<td align="center">
		   	<form method="post" action="rezerwuj.jsp">
		   	 <input type="hidden" name="show_id" value="${row.ID_Show}">
		   	 <input type="hidden" name="price" value="${row.Price}">
		   	<input type='submit' value='rezerwuj' class='sub'>
		   	</form>
		   	
		   	</td>
		   	
		</tr>
	</c:forEach>
	</table>



</section>

<footer>
<p>&copy; 2014 IO Projest Group. Wszystkie prawa zastrzeżone.</p>
</footer>

</body>
</html>