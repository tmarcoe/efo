<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form id="vendor" method="post" action="/personnel/updateinvestor" modelAttribute="user">
	<sf:hidden id="selectedRoles" path="roleString" />
	<table class="fancy-table tableshadow">
		<thead>
			<tr>
				<td><sf:hidden path="user_id" /></td>
				<td><sf:hidden path="common.user_id" /></td>
				<td><sf:hidden path="investor.user_id" /></td>
				<td><sf:hidden path="password"/></td>
			</tr>
		</thead>
		<tr>
			<td><b>First Name</b><br>
				<sf:input class="fancy" path="investor.firstname" />
			</td>
			<td><b>Last Name</b><br>
				<sf:input class="fancy" path="investor.lastname" />
			</td>
			<td><b>Male/Female</b><br>
				<sf:select class="fancy" path="investor.male_female">
					<sf:option value="">--- Select ---</sf:option>
					<sf:option value="M">Male</sf:option>
					<sf:option value="F">Female</sf:option>
				</sf:select>
			</td>
		</tr>
		<tr>
			<td><sf:errors path="investor.firstname" class="error" /></td>
			<td><sf:errors path="investor.lastname" class="error" /></td>
			<td><sf:errors path="investor.male_female" class="error" /></td>	
		</tr>
		<tr>
			<td><b>Number of Shares</b><br>
				<sf:input class="fancy" type="number" step=".01" path="investor.shares"/>
			</td>
			<td><b>Started On</b><br>
				<sf:input id="since" class="fancy" type="text" path="investor.since" />
			</td>
			<td><b>Preferred Stock? </b>
				<sf:checkbox path="investor.preferred"/>
			</td>
		</tr>
		<tr>
			<td><sf:errors path="investor.shares" class="error" /></td>
			<td><sf:errors path="investor.since" class="error" /></td>
		</tr>
		<tr>
			<td><b>Address 1</b><br>
				<sf:input class="fancy" path="common.address1" />
			<td><b>Address 2</b><br>
				<sf:input class="fancy" path="common.address2" />
			<td><b>City</b><br>
				<sf:input class="fancy" path="common.city" /></td>
		</tr>
		<tr>
			<td><sf:errors path="common.address1" class="error" /></td>
			<td><sf:errors path="common.address2" class="error" /></td>
			<td><sf:errors path="common.city" class="error" /></td>
		</tr>
		<tr>
			<td><b>Region</b><br>
				<sf:input class="fancy" path="common.region" /></td>
			<td><b>Postal Code</b><br>
				<sf:input class="fancy" path="common.postalCode" /></td>
			<td><b>Country Code</b><br>
				<sf:input class="fancy" path="common.country" /></td>
		</tr>
		<tr>
			<td><sf:errors path="common.region" class="error" /></td>
			<td><sf:errors path="common.postalCode" class="error" /></td>
			<td><sf:errors path="common.country" class="error" /></td>
		</tr>
		<tr>
			<td><b>Email</b><br
				><sf:input class="fancy" path="username" autocomplete="false" /> <sf:errors path="username" class="error" /></td>
			<td><b>Enable Logins? </b> <sf:checkbox id="enabled" path="enabled" onclick="disableInput()" /></td>
		</tr>
		<tr>
			<td><b>Role(s):</b><br> <sf:select class="fancy-roles" path="roles" id="roles" multiselect="true">
					<sf:options items="${roles}" itemValue="id" itemLabel="role" />
				</sf:select></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="button" onclick="formSubmit()">
					<b>Save</b>
				</sf:button></td>
			<td><button class="fancy-button" type="button" onclick="window.history.back()">
					<b>Cancel</b>
				</button></td>
		</tr>
	</table>
</sf:form>
<script type="text/javascript">
	$(document).ready(
			function() {
				var ndx = $("#selectedRoles").val();
				var selectedOptions = ndx.split(";");
				for ( var i in selectedOptions) {
					var optionVal = selectedOptions[i];
					$("#roles").find("option[value=" + optionVal + "]").prop(
							"selected", "selected");
				}
				if ($("#enabled").prop('checked') == false) {
					$("#password").prop("readonly", true);
					$("#confirmpass").prop("readonly", true);
				}
			});

	function formSubmit() {

		if ($("#password").val() == "") {
			$("#password").val("password");
		}
		var opt = document.getElementById("roles");
		var userRoles = "";
		for (var i = 0; i < opt.options.length; i++) {
			if (opt.options[i].selected) {
				if (userRoles == "") {
					userRoles += opt.options[i].value;
				} else {
					userRoles += ";" + opt.options[i].value;
				}
			}
		}
		var rs = document.getElementById("selectedRoles");
		rs.value = userRoles;
		document.getElementById("vendor").submit();
	}

	function disableInput() {
		if ($("#enabled").prop('checked') == false) {
			$("#password").prop("readonly", true);
			$("#confirmpass").prop("readonly", true);
		} else {
			$("#password").prop("readonly", false);
			$("#confirmpass").prop("readonly", false);
		}
	}
	  $( function() {
		    $( "#since" ).datepicker({
		    	dateFormat: "yy-mm-dd",
		        changeMonth: true,
		        changeYear: true,
		        clickInput: true
		    	});
		  } );
	
</script>
