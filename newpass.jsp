<%-- 
    Document   : newpass
    Created on : 13 Apr, 2016, 10:30:43 PM
    Author     : SADHANA
--%>

<%@include file="header.jsp"%>
<br>
<br>
<br>
<form class="form-horizontal" action="UserController" method="post">

    
	<div class="form-group">
		<label class="col-sm-4 control-label">Email Address</label>
                
		<div class="col-sm-4">
                    <input type="text" class="form-control" name="email" value="<c:out value="${email}"/>" />

	</div>
	</div>
<input type="hidden" name="action" value="rpass">
	<div class="form-group">
		<label class="col-sm-4 control-label">Enter New Password *</label> 
		<div class="col-sm-4">
			<input type="password" class="form-control" name="password" required />
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
