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

<section>

<h1>Rezerwacja została wykonana</h1>
<br>

<b>Imię:</b> <%= request.getParameter("name")%>
<br><br>
<b>Nazwisko:</b> <%= request.getParameter("surname")%>
<br><br>
<b>E-mail:</b> <%= request.getParameter("email")%>
<br><br>



<sql:setDataSource var="snapshot" driver='<%= application.getInitParameter("driver")%>'
     url='<%= application.getInitParameter("url")%>'
     user='<%= application.getInitParameter("user")%>'  password='<%= application.getInitParameter("password")%>'/>
     
 
 <%
	 String discount = request.getParameter("discountlist");
	 String queryD = "select Value from znizka where Name = '" + discount +"'";
 %>
 <c:set var="qD" value="<%= queryD %>"/>
<sql:query dataSource="${snapshot}" var="resultDisc">
	${qD}
</sql:query>
<c:forEach var="rowDisc" items="${resultDisc.rows}">
 	<c:set var="rDisc" value="${rowDisc.Value}" />
</c:forEach>
 
 <%
	 Double discountValue =  (Double)pageContext.getAttribute("rDisc");
	 String[] ids=request.getParameterValues("seats");
	 String name = request.getParameter("name");
	 String surname = request.getParameter("surname");
	 String email = request.getParameter("email");
	 String show = request.getParameter("show");
	 //out.println(ids.length);
	 out.println("<b>Zniżka: </b>" + discount);
	 out.println("<br><br>");
	 String price = request.getParameter("price");
	 Double doubleP = Double.parseDouble(price);
	 Double cost = ids.length*doubleP*discountValue;
	 out.println("<b>Cena: </b>" + cost);
	 out.println("<br><br>");
	 String queryShow = "select f.Title from seans s, film f where s.ID_Movie = f.ID_Movie and s.ID_Show = " + show;
	 
	
	 out.println("<b>Wybrane miejsca</b>");
	 for(String idS: ids)
	     out.println(idS + ", ");
	 out.println("<br><br>");
 %>    
 
<sql:query dataSource="${snapshot}" var="result">
	SELECT  ID_Reservation FROM rezerwacja
	ORDER BY ID_Reservation DESC
	LIMIT 1
</sql:query>
<c:forEach var="row" items="${result.rows}">
 	<c:set var="r" value="${row.ID_Reservation}" />
</c:forEach>


<c:set var="qS" value="<%= queryShow %>"/>
<sql:query dataSource="${snapshot}" var="resultShow">
	${qS}
</sql:query>
<b>Wybrany seans:</b>
<c:forEach var="rowS" items="${resultShow.rows}">
	<c:out value="${rowS.Title}" />
</c:forEach>


<%
	Integer idRes = (Integer)pageContext.getAttribute("r");
	int id;
	if((idRes==null) || (idRes==0)){
		id = 1;
	} else id = idRes + 1;

%>
<br><br>
<b>Twój numer rezerwacji:</b> <%=id %>

<sql:update dataSource="${snapshot}" var="result_id">
	insert into rezerwacja values(<%= id %>, '<%= name %>', '<%= surname %>', '<%= email %>', 0);
</sql:update>


<sql:query dataSource="${snapshot}" var="result_idTicket">
	SELECT  ID_Ticket FROM bilet
	ORDER BY ID_Ticket DESC
	LIMIT 1
</sql:query>

<c:forEach var="row_Ticket" items="${result_idTicket.rows}">
 	<c:set var="rTicket" value="${row_Ticket.ID_Ticket}" />
</c:forEach>

<%
	Integer idTicket = (Integer)pageContext.getAttribute("rTicket");
	int idT;
	if((idTicket==null) || (idTicket==0)){
		idT = 1;
	} else
	idT = idTicket + 1;
%>

<%
	String querySeat = "";
	int idSeat = 1;
	int number = 1;
	for(String idS: ids){
		//out.println("<br>dokonano wpisu");
	    number = Integer.parseInt(idS);
	    querySeat = "select m.ID_Seat from miejsce m, sala s, seans where m.ID_Auditorium = s.ID_hall and seans.ID_Auditorium = m.ID_Auditorium and m.Number =" + number + " and seans.ID_Show = " +  show;    
%>

<c:set var="qSeat" value="<%= querySeat %>"/>

<sql:query dataSource="${snapshot}" var="resultSeat">
	${qSeat}
</sql:query>

<c:forEach var="rowSeat" items="${resultSeat.rows}">
	<c:set var="rSeat" value="${rowSeat.ID_Seat}" />
	
	<% 
		idSeat = (Integer)pageContext.getAttribute("rSeat");
	%>
		<br>
</c:forEach>
   
<sql:update dataSource="${snapshot}" var="result_id">
	insert into bilet values(<%= idT++ %>, <%= id %>, <%= show %>, <%= idSeat %>, 1, <%=doubleP*discountValue %>, now(),  'Booked');
</sql:update>   
    
<%   
	}
%>

</section>


<footer>
<p>&copy; 2014 IO Projest Group. Wszystkie prawa zastrzeżone.</p>
</footer>

</body>
</html>