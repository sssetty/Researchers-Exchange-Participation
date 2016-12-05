<%-- 
    Document   : reset
    Created on : 13 Apr, 2016, 9:34:53 PM
    Author     : Sadhana
--%>

<%@include file="header.jsp"%>
<br>
<br>
<br>
<form class="form-horizontal" action="UserController" method="post">

	<input type="hidden" name="action" value="reset">
	<div class="form-group">
		<label class="col-sm-4 control-label">Enter Email Address *</label> 
		<div class="col-sm-4">
			<input type="email" class="form-control" name="email" required />
	</div>
	</div>
        
        <div class="form-group">
		<div class="col-sm-offset-4 col-sm-10">
			<input type="submit" value="Reset Password" class="btn btn-primary">
			<!-- <input type="submit" formaction="admin.jsp?user=Hello,Admin"
				class="btn btn-primary" value="Admin"> -->
		</div>
	</div>
</form>
<%@include file="footer.jsp"%>
