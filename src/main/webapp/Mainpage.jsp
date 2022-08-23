<Html>
<Head>
  <title>
    Ecom website
  </title>
</Head>
<Body>
<p>
  <b> Welcome to the Ecommerce website"</b>
    <%
      String uname = (String) request.getAttribute("uname");

    %>

    <img src="/getQRCodeImgStream?uname=<%=uname%>" />

  </p>

</Body>
</Html>