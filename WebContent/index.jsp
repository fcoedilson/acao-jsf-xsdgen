<%
	response.setStatus(301);

	response.setHeader( "Location", "pages/templates/template.jsf" );

	response.setHeader( "Connection", "close" );
%>