
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@page import="java.awt.List"%>
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

<sql:setDataSource var="snapshot" driver='<%= application.getInitParameter("driver")%>'
     url='<%= application.getInitParameter("url")%>'
     user='<%= application.getInitParameter("user")%>'  password='<%= application.getInitParameter("password")%>'/>


<%
String id_show = request.getParameter("show_id");
String price = request.getParameter("price");
String query = "select m.Number from bilet b, miejsce m where b.ID_Seat = m.ID_Seat and b. ID_Show = " + id_show;
Boolean isFree = false;
String no = "disabled='disabled'";
%>

<c:set var="q" value="<%= query %>"/>

<sql:query dataSource="${snapshot}" var="result">
	${q}
</sql:query>

<%
ArrayList<Integer> listSeats = new ArrayList<Integer>();
%>

<c:forEach var="row" items="${result.rows}">
		<c:set var="r" value="${row.Number}" />
	
		<% 
		listSeats.add((Integer)pageContext.getAttribute("r"));
		%>

</c:forEach>

<center>
<div class="sc">Ekran</div>


<form action="form.jsp" method="post">

<h1>Wybierz miejsca</h1>
  	<div class="wrapper">

			<% if (listSeats.contains(1)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="1" disabled="disabled" />01</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="1" />01</label> <% }%>
			
			<% if (listSeats.contains(2)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="2" disabled="disabled" />02</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="2" />02</label> <% }%>
			
			<% if (listSeats.contains(3)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="3" disabled="disabled" />03</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="3" />03</label> <% }%>
			
			<% if (listSeats.contains(4)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="4" disabled="disabled" />04</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="4" />04</label> <% }%>
			
			<% if (listSeats.contains(5)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="5" disabled="disabled" />05</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="5" />05</label> <% }%>
			
			<% if (listSeats.contains(6)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="6" disabled="disabled" />06</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="6" />06</label> <% }%>
	</div>
	<div class="wrapper">
			<% if (listSeats.contains(7)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="7" disabled="disabled" />07</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="7" />07</label> <% }%>

			
			<% if (listSeats.contains(8)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="8" disabled="disabled" />08</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="8" />08</label> <% }%>
			
			
			<% if (listSeats.contains(9)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="9" disabled="disabled" />09</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="9" />09</label> <% }%>
			
			<% if (listSeats.contains(10)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="10" disabled="disabled" />10</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="10" />10</label> <% }%>
			
			<% if (listSeats.contains(11)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="11" disabled="disabled" />11</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="11" />11</label> <% }%>
			
			<% if (listSeats.contains(12)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="12" disabled="disabled" />12</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="12" />12</label> <% }%>
	</div>
	<div class="wrapper">
			<% if (listSeats.contains(13)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="13" disabled="disabled" />13</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="13" />13</label> <% }%>
			
			<% if (listSeats.contains(14)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="14" disabled="disabled" />14</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="14" />14</label> <% }%>
			
			<% if (listSeats.contains(15)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="15" disabled="disabled" />15</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="15" />15</label> <% }%>
			
			<% if (listSeats.contains(16)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="16" disabled="disabled" />16</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="16" />16</label> <% }%>
			
			<% if (listSeats.contains(17)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="17" disabled="disabled" />17</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="17" />17</label> <% }%>
			
			<% if (listSeats.contains(18)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="18" disabled="disabled" />18</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="18" />18</label> <% }%>
			
	</div>
	<div class="wrapper">
			<% if (listSeats.contains(19)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="19" disabled="disabled" />19</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="19" />19</label> <% }%>
			
			<% if (listSeats.contains(20)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="20" disabled="disabled" />20</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="20" />20</label> <% }%>
			
			<% if (listSeats.contains(21)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="21" disabled="disabled" />21</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="21" />21</label> <% }%>
			
			<% if (listSeats.contains(22)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="22" disabled="disabled" />22</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="22" />22</label> <% }%>
			
			<% if (listSeats.contains(23)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="23" disabled="disabled" />23</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="23" />23</label> <% }%>
			
			<% if (listSeats.contains(24)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="24" disabled="disabled" />24</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="24" />24</label> <% }%>
	</div>
	<div class="wrapper">
			<% if (listSeats.contains(25)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="25" disabled="disabled" />25</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="25" />25</label> <% }%>
			
			<% if (listSeats.contains(26)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="26" disabled="disabled" />26</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="26" />26</label> <% }%>
			
			<% if (listSeats.contains(27)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="27" disabled="disabled" />27</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="27" />27</label> <% }%>
			
			<% if (listSeats.contains(28)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="28" disabled="disabled" />28</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="28" />28</label> <% }%>
			
			<% if (listSeats.contains(29)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="29" disabled="disabled" />29</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="29" />29</label> <% }%> 
			
			<% if (listSeats.contains(30)) { %>
			<label><input class="checkbox" type="checkbox" name="seats" value="30" disabled="disabled" />30</label>
			<% } else {
			%> <label><input class="checkbox" type="checkbox" name="seats" value="30" />30</label> <% }%>
	</div>
<br><br>
<input type="hidden" name="show" value="<%=id_show%>">
<input type="hidden" name="price" value="<%=price%>">
<input type='submit' value='Dalej' class='sub'>

</center>
</form>



</section>

<footer>
<p>&copy; 2014 IO Projest Group. Wszystkie prawa zastrzeżone.</p>
</footer>

</body>
</html>