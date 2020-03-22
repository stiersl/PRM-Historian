<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<hr />
<footer>
	<jsp:useBean id="now" class="java.util.Date" />
	<p>
		&copy;
		<fmt:formatDate value="${now}" pattern="yyyy" />
		Stier Automation LLC
	</p>
</footer>
</div>
</body>
</html>