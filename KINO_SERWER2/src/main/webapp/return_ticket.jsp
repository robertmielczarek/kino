<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
  <a href="return_ticket.jsp"><li class="option">Zwróć bilet</li></a>
</ul>
</nav>

<script>
$(document).ready(function() {

  $('input').blur(function() {

    // check if the input has any value (if we've typed into it)
    if ($(this).val())
      $(this).addClass('used');
    else
      $(this).removeClass('used');
  });
  
</script>


<section>

<form method="post" action="return_ticket_info.jsp">

    
  <div class="group">      
    <input class="field" type="text" required name="resId">
    <span class="highlight"></span>
    <span class="bar"></span>
    <label>ID rezerwacji</label>
  </div>
    
  <div class="group">      
    <input class="field" type="email" required name="email">
    <span class="highlight"></span>
    <span class="bar"></span>
    <label>Email</label>
  </div>

  <div class="group">
  <input class="sub" type="submit" value="Wykonaj" style="position: absolute; left: 155px; top: -5px;">
  </style>
  </div>

  <div><br><br></div>
    
</form>
</section>






<footer>
<p>&copy; 2014 IO Projest Group. Wszystkie prawa zastrzeżone.</p>
</footer>

</body>
</html>