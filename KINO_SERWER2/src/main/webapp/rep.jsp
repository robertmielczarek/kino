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

	<h1 class="logo_text">Cinema Site</h1>
 
</header>

<nav>
<ul>
  <li><form method="get" action="all_movies.jsp">
  	<input type="text" name="search" class="search">
	

  	<input type="submit" value="" style="width: 27px; height: 27px; background: url('./images/ic_search27.png') no-repeat; border:none;">
  </form></li>
  <a href="rep.jsp"><li class="option">Repertuar</li></a>
  <a href="all_movies.jsp"><li class="option">Co gramy</li></a>
  <a href="return_ticket.jsp"><li class="option">Zwróć bilet</li></a>
</ul>
</nav>



<section>

<sql:setDataSource var="snapshot" driver='<%= application.getInitParameter("driver")%>'
     url='<%= application.getInitParameter("url")%>'
     user='<%= application.getInitParameter("user")%>'  password='<%= application.getInitParameter("password")%>'/>
	
	
	Wybierz kino<br><br>
	<sql:query dataSource="${snapshot}" var="result">
	SELECT * from kino;
	</sql:query>
	
	<table width="70%" style="text-align: left;">
		<tr>
		   <th>Miasto</th>
		   <th>Nazwa</th>
		</tr>
	<c:forEach var="row" items="${result.rows}">
		<tr>
		
		   	<td style="height: 70px;"><c:out value="${row.City}"/></td>
		  	<td style="height: 70px;">
			   	<form method="get" action="rep_info.jsp">
				   	<c:out value="${row.Name}" />
				   	
				   	<input type="hidden" name="cinema_id" value="${row.ID_Cinema}">
				 	<input type='submit' value='Pokaż repertuar' class='sub'>
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