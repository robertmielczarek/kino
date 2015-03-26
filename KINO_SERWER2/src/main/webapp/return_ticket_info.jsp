<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>


<link rel="stylesheet" type="text/css" href="mystyle.css">

<title>Cinema Site</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js">
</script>
<![endif]-->

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
  <a href="return_ticket.jps"><li class="option">Zwróć bilet</li></a>
</ul>
</nav>

<section>
Jakaś tam sekcja!!!<br>
<%= request.getParameter("resId")%>
<%= request.getParameter("email")%>


</select>


</section>

<section>
</section>



<footer>
<p>&copy; 2014 IO Projest Group. Wszystkie prawa zastrzeżone.</p>
</footer>

</body>
</html>